$(document)
		.ready(
				function() {
					// call initialize function
					initialize();
					window.clickedToDrawSwitch="";
					window.loadHolon=true;
					window.clickedMarkerChangeImage="";
					window.createdHolonObject=null; //Save object on overlaycomplete Action to use it later to set its holon color
					window.globalHoList=new Map();
					window.globalPlList=new Map();
					window.globalPsList=new Map();
					window.globalHKList=new Map();
					window.globalHPList=new Map();
					window.globalPCList=new Map();
					window.currentInfoWindowObject=null;
					window.currentLineInfoWindowObject=null;
					window.currentSwitchInfoWindow=null;
					window.ajaxReqStatus=false;
					var drawingManager;
					var clickedValuePanel;
					var startPowerLine;
					var next = 0;
					var infowindow = "";
					// Array of Markers and Infowindow
					var infowindowArray = [];
					var markerslat = [];
					var markerslng = [];
					// Variable to get the old content from the info window
					var contentInfoWindow = "";
					var clickedToAddElements = "";
					// Div to add details about the new elements inserted
					$("#displayHolonDetails").hide();
					/*$("#holonObjectDetail").hide();*/
					$("#holonCoordinatorInformation").hide();
					$("#addMasterHolonElementTypeDetail").hide();
					$("#addMasterHolonElementStateDetail").hide();
					$("#addMasterHolonDetail").hide();
					$(document).on("click","#delete",function(){
						alert("ready");
					});
								
					$("#switchOnPowerLine").click(function(event){
						drawSwitchOnPowerLine(this.id);
					});
					
					$("#holonObjectTypeforMasterTables").click(function(){
						getHolonObjectTypesFromDatabase();});
					$("#switch").on('click',function(){
						addNewSwitch=true;
						//console.log($(this).find("a").attr("id"));
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
					
					
					$("#saveHolonObject").click(function(event){
						saveHolonObject();						
					});
					
					$("#cancelHolonObject").click(function(event){
						if(createdHolonObject!=null && typeof createdHolonObject != undefined)
							{
							createdHolonObject.setMap(null);
							}
						closeDiv("holonObjectDetail");
					});
					
					
					$("#saveCoordinator").click(function(event){
						saveCoordinator();
					});
					
					
					/*$("#addHolonCoordinator").click(function(){
						$("#holonCoordinatorInformation").show();
						$("#holonCoordinatorInformation").popup();
						$("#holonCoordinatorInformation").popup("open");
					});*/
					
					$('#addHolonObject').click(function(){
						clickedValuePanel=this.id;
						overlayTool(this.id);
					});
					
					$("#addHolonCoordinator").click(function(){
						clickedValuePanel=this.id;
						overlayTool(this.id);
					});
					$("#edit").click(function(){
						alert("Hellooo");
					});
					
					$(document).on('click', '#edit', function(){ 
					     alert();
					 });
					$("#showHolonObjects").click(showHolonObjects);
					
					function overlayTool(clickedValue)
					{
						//alert($("#"+clickedValuePanel).css("background-color"));
						if ($("#"+clickedValuePanel).css("background-color") == "rgb(26, 26, 26)") {
							$("#"+clickedValuePanel).css("background-color", "rgb(153,153,0)");
					
						$("#displayHolonDetails").hide();

					//	To check If the layout is already present ;
						
						if (drawingManager== null){
							//Creates a new drawing manager object for first time
							var rectangleColor="black";
							if(clickedValuePanel=="addHolonCoordinator"){
								rectangleColor="red";
							}
							if(clickedValuePanel=="addHolonObject"){
								rectangleColor="black";
							}
						 drawingManager = new google.maps.drawing.DrawingManager({
				    	    drawingMode: google.maps.drawing.OverlayType.RECTANGLE,
				    	    drawingControl: false,
//				    	    drawingControlOptions: {
//				    	      position: google.maps.ControlPosition.TOP_CENTER,
//				    	      drawingModes: [
//				    	   		 google.maps.drawing.OverlayType.RECTANGLE,
//				    	   		google.maps.drawing.OverlayType.POLYLINE
//				   	   		 
//				    	      ]
//				    	    },
				            rectangleOptions: {
				                geodesic:true,
				                clickable: true,
				                strokeColor:rectangleColor
				            }
				    	    });}
				     // Setting the layout on the map 
				      drawingManager.setMap(map);
				     // Event when the overlay is complete 
				      google.maps.event.addListener(drawingManager, 'overlaycomplete', function(event) {
				    	  var newShape = event.overlay; // Object
				    	  newShape.type = event.type;	// Rectangle
				    	  createdHolonObject=newShape;
				    	  var latNorthEast = newShape.getBounds().getNorthEast().lat(); //get lat of northeast
				    	  var lngNorthEast = newShape.getBounds().getNorthEast().lng();	//get longitude of north east
				    	  var latSouthWest = newShape.getBounds().getSouthWest().lat();
				    	  var lngSouthWest = newShape.getBounds().getSouthWest().lng();
				    	  $("#holonObjectLatitudeNE").text(latNorthEast);
				    	  $("#holonObjectLongitudeNE").text(lngNorthEast);
				    	  $("#holonObjectLatitudeSW").text(latSouthWest);
				    	  $("#holonObjectLongitudeSW").text(lngSouthWest);
				    	  if(clickedValuePanel=="addHolonObject"){
				    		  $("#holonObjectActionState").val("Add");
				    		  getHolonObjectTypeFromDatabase();
				    		  getHolonCoordinatorFromDatabase();
				    	  } else {
				    		  getHolonFromDatabase();
				    		  }	
				  	});
				     //END of overlay Complete 
					}
					else
					{
						$("#"+clickedValuePanel).css("background-color", "rgb(26, 26, 26)");
						drawingManager.setMap(null);
						
					}
					}
					
					$('#addHolonCoordinator').hover(function() {
						$('#addHolonCoordinator').css('cursor','pointer');
							  });
					
					$('#showHolonObjects').hover(function() {
						$('#showHolonObjects').css('cursor','pointer');
							  });
					
					$("#close").click(function(){
						$(this).parent().fadeOut("slow", function(c) {
                        });
					})
					
					
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
					    	$("#displayHolonDetails").hide();
							initialize();
							loadHolon=true;
							swal("Cleared", "Map has been cleared", "info");
					    } else {
					      swal("Cancelled", "Map has not been cleared", "info");
					    }
						});
						
					});
					
					
					function cursorCrossHair() {
						$("#googleMap").css('cursor', 'pointer');
					}
					
					//function to check if a point falls in a circle overlay
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

function showHolonElementsForHolon (holonObjectId){
	$("#displayHolonDetails").show();
	$("#displayHolonDetails").empty();
	var holonElementType=["Fridge","Washing Machine"];
	var holonManager=["HolonManager1","HolonManager4"];
	var maxCapacity=["300W","2500W"];
	var data="";
	for (var k = 0; k < holonElementType.length; k++) {
		
		var individualElementDetail = "<div data-role='collapsible'><h3>Holon Element :"+(k+1)+"</h3><button onclick='delete()' id='delete' class='ui-btn ui-btn-inline ui-icon-delete ui-btn-icon-notext ui-corner-all'>No text</button>" +
				"<button onclick='edit()' class='ui-btn ui-btn-inline ui-icon-edit ui-btn-icon-notext ui-corner-all' id='edit'>No text</button>"+holonElementType[k]+"</div>"
		$("#displayHolonDetails").append(individualElementDetail);
	}
	console.log($("#displayHolonDetailsData"));
    $('#displayHolonDetails').panel({ position: "right"});       
    $('#displayHolonDetails').panel("open");   	
		
	
}

function edit()
{
	alert("Clicked Edit");
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

function getHolonCoordinatorFromDatabase(holonCoordinatorName)
{
	var options={};
	if(typeof holonCoordinatorName != "undefined")
		{
		options={holonCoordinatorName:holonCoordinatorName};
		}
	ajaxRequest("getListHolonCoordinator", {}, getHolonCoordinatorFromDatabaseCallBack,options);
}

function getHolonCoordinatorFromDatabaseCallBack(data,option)
{
	var holonCoordinatorName="undefined";
	var holonCoordinatorNameFromOption =option['holonCoordinatorName'];
	if(typeof holonCoordinatorNameFromOption !="undefined")
	{
		holonCoordinatorName=holonCoordinatorNameFromOption.split("_")[0].trim();
	}
	//alert(holonCoordinatorName);
	var listHolonCoordinator= data.split("*");
	$("#holonCoordinatorId").empty();
	for(var i=0;i<listHolonCoordinator.length-1;i++)
	{
		//alert(listHolonCoordinator[i].split("-")[1].split(" ")[0].trim());
		if((typeof holonCoordinatorName != "undefined")&&(holonCoordinatorName==listHolonCoordinator[i].split("-")[1].split(" ")[0].trim())){			
			var options= "<option value="+listHolonCoordinator[i].split("-")[0]+" id= "+listHolonCoordinator[i].split("-")[0]+" selected>"+listHolonCoordinator[i].split("-")[1]+"</option>";
			$("#holonCoordinatorId").append(options);
			}
			else
			{				
				var options= "<option value="+listHolonCoordinator[i].split("-")[0]+" id= "+listHolonCoordinator[i].split("-")[0]+">"+listHolonCoordinator[i].split("-")[1]+"</option>";
				$("#holonCoordinatorId").append(options);
				}
		
	}
//	$("#holonCoordinatorId").selectmenu('refresh', true);

	openDiv('holonObjectDetail');
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

