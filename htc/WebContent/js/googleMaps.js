$(document)
		.ready(
				function() {
					// call initialize function
					initialize();
					window.loadHolon=true;
					window.clickedMarkerChangeImage="";
					window.createdHolonObject=null; //Save object on overlaycomplete Action to use it later to set its holon color
					window.createdPowerLineObject=null;
					window.createdSubLineObject=null;
					window.createdPowerSourceObject=null;
					window.drawPowerLineMode=false;
					window.drawHolonObjectMode=false;
					window.calculateDistanceMode=false;
					window.addSwitchonPowerLineMode=false;
					window.connectToPowerSourceMode=false;
					window.addPowerSourceMode=false;
					window.globalHoList=new Map();
					window.globalPlList=new Map();
					window.globalPsList=new Map();
					window.globalHKList=new Map();
					window.globalHPList=new Map();
					window.globalPCList=new Map();
					window.globalPSList=new Map();
					window.currentInfoWindowObject=null;
					window.currentLineInfoWindowObject=null;
					window.currentSwitchInfoWindow=null;
					window.ajaxReqStatus=false;
					$("#holonCoordinatorInformation").hide();
					$("#addMasterHolonElementTypeDetail").hide();
					$("#addMasterHolonElementStateDetail").hide();
					$("#addMasterHolonDetail").hide();
					$("#holonObjectTypeforMasterTables").click(function(){
						getHolonObjectTypesFromDatabase();
						});
					
					$("#addMasterHolonObjDetail").hide(); 
					$("#masterTableHolonObjectsTypes").hide();
					
					$("#holonforMasterTables").click(function(){
						openHolonforMasterTables();
					});
					
					$("#buttonHolonMaster").click(function(){
						var textHolonMaster= $("#textHolonMaster").val();
						var dataAttributes = {
								holonName:textHolonMaster
						}
						
						$("#addMasterHolonDetail").popup("close");
						ajaxRequest("addHolon", dataAttributes, addHolonCallBack, {});
					});
					
					$("#holonElementTypeforMasterTables").click(function(){
						openHolonElementTypeforMasterTables();
					
					});
					
					$("#holonElementStateforMasterTables").click(function(){
						openHolonElementStateforMasterTables();
					
					});
					
					
					$("#buttonHolonElementTypeMaster").click(function(){
						var textHolonElementTypeMaster= $("#textHolonElementTypeMaster").val();
						var dataAttributes= {
								holonElementTypeName : textHolonElementTypeMaster
						}
						
						$("#addMasterHolonElementTypeDetail").popup("close");
						ajaxRequest("createHolonElementType", dataAttributes, createHolonElementTypeCallBack, {});
					});
					
					$("#buttonHolonElementStateMaster").click(function(){
						var textHolonElementStateMaster= $("#textHolonElementStateMaster").val();
						var dataAttributes= {
								holonElementStateName : textHolonElementStateMaster
						}
						
						$("#addMasterHolonElementStateDetail").popup("close");
						ajaxRequest("createHolonElementState", dataAttributes, createHolonElementStateCallBack, {});
					});
					
									
					// Callback for add a holonName 
					function addHolonCallBack(data, options)
					{
						alert("Holon Name added succesfully");
					}
					
					// Callback for add a holonElemnt Type
					function createHolonElementTypeCallBack(data,options)
					{
						alert("Holon Element Type added succesfully");
					}
					
					function createHolonElementStateCallBack(data,options)
					{
						alert("Holon Element State added succesfully");
					}
					
					$("#clear").click(function() {
						swal({
							title: "Are you sure?",
							text: "Do you really want to clear the holon Map?",
							type: "warning",
							showCancelButton: true,
							confirmButtonColor: '#DD6B55',
							confirmButtonText: 'Yes,clear it!',
							cancelButtonText: "No, Don't clear!",
							closeOnConfirm: false,
							closeOnCancel: false
						},
						function(isConfirm){
					    if (isConfirm){
					    	initialize();
							loadHolon=true;
							swal("Cleared", "Map has been cleared", "info");
					    } else {
					      swal("Cancelled", "Map has not been cleared", "info");
					    }
						});
						
					});
					//function to check if a point falls in a circle overlay for snapping never remove
					google.maps.Circle.prototype.contains = function(latLng,theCircle) {
						  return theCircle.getBounds().contains(latLng) && google.maps.geometry.spherical.computeDistanceBetween(theCircle.getCenter(), latLng) <= theCircle.getRadius();
						}
				});

function initialize() {
	mapProp = {
		center : new google.maps.LatLng(49.865888, 8.658592),
		zoom : 18,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
	directionsService = new google.maps.DirectionsService();
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map);

}

function getHolonObjectTypesFromDatabase() {
var types=["Fridge","Washing Machine"];
var data="";
for(var i=0;i<types.length;i++)
	{
		data=data+"<tr><td>"+types[i]+"</td><td><button id='editMasterHolonObjectType'>Edit</button></td><td><button id='deleteMasterHolonObjectType'>Delete</button></td></tr>"
	}
data="<table border='1'><th>Data Type</th><th></th><th></th>"+data+"</table>";
$("#masterTableHolonObjectsTypes").empty();
$("#masterTableHolonObjectsTypes").append("<a href='#' data-rel='back' data-role='button' data-theme='a' data-icon='delete' data-iconpos='notext' class='ui-btn-right'>X</a>"+data);
$("#masterTableHolonObjectsTypes").append("<br><button id='addMasterHolonObjectType' onclick='addHolonObjectTypeInMaster()'>Add</button>");
$("#masterTableHolonObjectsTypes").show();
$("#masterTableHolonObjectsTypes").popup();
$("#masterTableHolonObjectsTypes").popup("open");

}

function openHolonforMasterTables() {
	$("#addMasterHolonDetail").show();
	$("#addMasterHolonDetail").popup();
	$("#addMasterHolonDetail").popup("open");
}

function openHolonElementTypeforMasterTables() {
	$("#addMasterHolonElementTypeDetail").show();
	$("#addMasterHolonElementTypeDetail").popup();
	$("#addMasterHolonElementTypeDetail").popup("open");
}

function openHolonElementStateforMasterTables(){
	$("#addMasterHolonElementStateDetail").show();
	$("#addMasterHolonElementStateDetail").popup();
	$("#addMasterHolonElementStateDetail").popup("open");
}

function getHolonFromDatabase()
{
	ajaxRequest("getListHolon", {}, getHolonFromDatabaseCallBack, {});
}

function getHolonFromDatabaseCallBack(data,options)
{
	var listHolonMaster= data.split("*");
	$("#holon").empty();
	for(var i=0;i<listHolonMaster.length-1;i++)
	{
	var options= "<option value="+listHolonMaster[i].split("-")[0]+" id= "+listHolonMaster[i].split("-")[0]+">"+listHolonMaster[i].split("-")[1]+"</option>";
	$("#holon").append(options);
	}
$("#holon").selectmenu('refresh', true);
$("#holonCoordinatorInformation").show();
$("#holonCoordinatorInformation").popup();
$("#holonCoordinatorInformation").popup("open");
		
}

function getHolonCoordinatorFromDatabase(holonCoordinatorName,elementId,divId)
{
	var options={};
	if(holonCoordinatorName.trim().length==0 )
	{
	options={
			elementId:elementId,
			divId:divId
			};
	}
	else if(typeof holonCoordinatorName != "undefined")
		{
		options={
				holonCoordinatorName:holonCoordinatorName,
			};
		}
	
	
	ajaxRequest("getListHolonCoordinator", {}, getHolonCoordinatorFromDatabaseCallBack,options);
}

function getHolonCoordinatorFromDatabaseCallBack(data,option)
{
	var holonCoordinatorName="undefined";
	var elementId ="#".concat(option['elementId']);
	alert(elementId);
	var holonCoordinatorNameFromOption =option['holonCoordinatorName'];
	var divId =option['divId'];
	alert(divId);
	if(typeof holonCoordinatorNameFromOption !="undefined")
	{
		holonCoordinatorName=holonCoordinatorNameFromOption.split("_")[0].trim();
	}
	//alert(holonCoordinatorName);
	var listHolonCoordinator= data.split("*");
	$(elementId).empty();
	$(elementId).append("<option value=\"0\" id= \"0\" >No Holon</option>");
	for(var i=0;i<listHolonCoordinator.length-1;i++)
	{
		//alert(listHolonCoordinator[i].split("-")[1].split(" ")[0].trim());
		if((holonCoordinatorName==listHolonCoordinator[i].split("-")[1].split(" ")[0].trim())){			
			var options= "<option value="+listHolonCoordinator[i].split("-")[0]+" id= "+listHolonCoordinator[i].split("-")[0]+" selected>"+listHolonCoordinator[i].split("-")[1]+"</option>";
			$(elementId).append(options);
			}
			else
			{				
				var options= "<option value="+listHolonCoordinator[i].split("-")[0]+" id= "+listHolonCoordinator[i].split("-")[0]+">"+listHolonCoordinator[i].split("-")[1]+"</option>";
				$(elementId).append(options);
				}
		
	}
//	$("#holonCoordinatorId").selectmenu('refresh', true);

	openDiv(divId);
}

function getHolonObjectTypeFromDatabase(holonObjectTypeName)
{
	var options={};
	if(typeof holonObjectTypeName != "undefined")
		{
		options={holonObjectTypeName:holonObjectTypeName};
		}
	
	ajaxRequest("getListHolonObjectType", {}, getHolonObjectTypeFromDatabaseCallBack, options);
}

function getHolonObjectTypeFromDatabaseCallBack(data,option)
{
	var holonObjectTypeName =option['holonObjectTypeName'];
	var listHolonObjectType= data.split("*");
	$("#holonObjectType").empty();
	for(var i=0;i<listHolonObjectType.length-1;i++)
	{
		if((typeof holonObjectTypeName != "undefined")&&(holonObjectTypeName==listHolonObjectType[i].split("-")[1].trim())){
			var options= "<option value="+listHolonObjectType[i].split("-")[0]+" id= "+listHolonObjectType[i].split("-")[0]+" selected>"+listHolonObjectType[i].split("-")[1]+"</option>";
			$("#holonObjectType").append(options);
			}
			else
			{
				var options= "<option value="+listHolonObjectType[i].split("-")[0]+" id= "+listHolonObjectType[i].split("-")[0]+">"+listHolonObjectType[i].split("-")[1]+"</option>";
				$("#holonObjectType").append(options);
				}
		
	}
//	$("#holonObjectType").selectmenu('refresh', true);
}

