/**
 * Java Script file for functions related to holon objects
 */

function saveHolonObject(){
	//alert("saveHolonObject");
	var holonObjectPriority=$("#holonObjectPriority").val();
	var holonObjectType=$("#holonObjectType option:selected").val();
	var holonCoordinatorId=$("#holonCoordinatorId option:selected").val();
	var holonManager=$("#holonManagerName").val();
	var latNE=$("#holonObjectLatitudeNE").text();
	var lngNE=$("#holonObjectLongitudeNE").text();
	var latSW=$("#holonObjectLatitudeSW").text();
	var lngSW=$("#holonObjectLongitudeSW").text();
	var holonObjectActionState = $("#holonObjectActionState").val();
	var hiddenHolonObjectId = $("#hiddenHolonObjectId").val();
	var hiddenHolonManagerId = $("#hiddenHolonManagerId").val();
	$( "#holonObjectDetail" ).slideUp(100);
	var dataAttributes = {
			holonObjectType : holonObjectType,
			holonCoordinatorId : holonCoordinatorId,
			holonManager : holonManager,
			latNE : latNE,
			lngNE : lngNE,
			latSW : latSW,
			lngSW : lngSW,
			holonObjectPriority : holonObjectPriority,
			hiddenHolonObjectId : hiddenHolonObjectId,
			hiddenHolonManagerId : hiddenHolonManagerId
		};
	if(holonObjectActionState == "Edit"){
		ajaxRequest("editHolonObject", dataAttributes, editHolonObjectCallBack, {});
	} else {
		ajaxRequest("createHolonObject", dataAttributes, createHolonObjectCallBack, {});							
	}
}

function saveCoordinator(){
	
	var nameCoordinator =$("#nameCoordinator").val();
	var latNE=$("#holonObjectLatitudeNE").text();
	var lngNE=$("#holonObjectLongitudeNE").text();
	var latSW=$("#holonObjectLatitudeSW").text();
	var lngSW=$("#holonObjectLongitudeSW").text();
	var coordinatorHolon=$("#holon").val();
	var dataAttributes= {
			latNE : latNE,
			lngNE : lngNE,
			latSW : latSW,
			lngSW : lngSW,
			coordinatorHolon : coordinatorHolon,
			nameCoordinator : nameCoordinator
	}
	ajaxRequest("createHolonCoordinator", dataAttributes, createHolonCoordinatorCallBack, {});
}

function createHolonCoordinatorCallBack(data,options){
	
	$("#holonCoordinatorInformation").popup("close");
	alert("Coordinator added Sucessfully");
}

function editHolonObjectCallBack(data, options){
	var holonColor= data;
	//alert(createdHolonObject);
	createdHolonObject.setOptions({strokeColor:holonColor,fillColor: holonColor});
	//createdHolonObject=null;
	//alert(data);
}
function createHolonObjectCallBack(data, options) {
	var dataArray = data.split("!");
	var holonObjectId = dataArray[0];
	var holonColor= dataArray[1];
	createdHolonObject.setOptions({strokeColor:holonColor,fillColor: holonColor});
	 //When Rectangle is clicked
	  google.maps.event.addListener(createdHolonObject, 'click', function() {
		  var dataAttributes= {
				  holonObjectId : holonObjectId,
				}
		 ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});		  
		
	  });
	//createdHolonObject=null;
}

function getHolonInfoWindowCallBack(data,options)
{
	var dataArray = data.split("!");
	var holonObjectId = dataArray[0];
	var holonCoordinatorName_Holon= dataArray[1];	
	var holonObjectTypeName= dataArray[2];
	var ne_location= dataArray[3];
	var sw_location= dataArray[4];
	var lineConnectedState= dataArray[5];
	var holonColor= dataArray[6];
	var holonManagerName= dataArray[7];
	var lat=ne_location.split("~");
	//alert(holonColor);
	contentString="<b>Holon Object Id: </b>"+holonObjectId +"<br>"+
			"<b>Holon Object Type: </b>"+holonObjectTypeName+"<br>"+
			"<b>Holon Manager: </b>"+holonManagerName+"<br>"+
			"<b>North East Location: </b>"+ne_location+"<br>"+
			"<b>South West Location: </b>"+sw_location+"<br><br>"+
			"<span class='button'><i class='fa fa-plug'></i>&nbsp;&nbsp;Connect to Power Source</span>&nbsp;&nbsp;&nbsp;&nbsp;"+
			"<span class='button' id='consumptionGraph'><i class='fa fa-line-chart'></i>&nbsp;&nbsp;Show Consumption</span>&nbsp;&nbsp;&nbsp;&nbsp;"+
			"<span class='button' id='showHolonElement' onclick='showHolonElements("+holonObjectId+")'><i class='fa fa-info'></i>&nbsp;&nbsp;Show Holon Elements</span>";
	var infowindowHolonObject = new google.maps.InfoWindow({
	      content: contentString,
	      position:new google.maps.LatLng(lat[0],lat[1])
	  });
	infowindowHolonObject.open(map,map);	
}

function showHolonObjects() {
	if(loadHolon){
	ajaxRequest("showHolonObjects", {}, showHolonObjectsCallBack, {});
	showSavedPowerLines();
	showSavedPowerSwitches();
	loadHolon=false;
	}else{
		
		swal({
			title : "Holon already loaded.",
			text : "The Holon has already been loaded. Please clear the map first to reload it.",
			type : "info",
			confirmButtonText : "Sure!"
		});
		
	}
}

function showHolonObjectsCallBack(data, options){
	//alert(data);
	var res = data.replace("[", "").replace("]", "").split(',').join("");
	//alert(res);
	var hoObjectsList = res.split("*");
	for (var i=0; i<hoObjectsList.length-1; i++) {
		var holonObject = hoObjectsList[i];
		var holonObjectId =  holonObject.split("#")[0].trim();
		var color = holonObject.split("#")[1].trim();	
		var location = holonObject.split("#")[2];
		//alert(color);
		var ne_location_lat = location.split("^")[0].split("~")[0].replace("[","").replace(",","");
		var ne_location_lng = location.split("^")[0].split("~")[1];
		var sw_location_lat = location.split("^")[1].split("~")[0];
		var sw_location_lng = location.split("^")[1].split("~")[1];
		//alert("a+"+color+"+b")
	    var rectangleFromFactory = new google.maps.Rectangle({
		      map: map,
		      strokeColor :color,
		      fillColor:color,
		      bounds: new google.maps.LatLngBounds(
		    	      new google.maps.LatLng(sw_location_lat, sw_location_lng),
		    	      new google.maps.LatLng(ne_location_lat, ne_location_lng))
		    });
	    var dataAttributes= {
				  holonObjectId : holonObjectId
				};
	    attachMessage(dataAttributes, rectangleFromFactory);
	}	
}

function attachMessage(dataAttributes, rectangleFromFactory) {
	google.maps.event.addListener(rectangleFromFactory, 'click', function(event) {		
		  ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});		
		
	  });
}

function closeDiv(id) {
	$("#"+id).slideUp(100);
}

function openDiv(id) {
	$("#"+id).slideDown(100);
}

function connectToPowerSource(holonObjectId) {
	alert("Holon Object ID = "+holonObjectId);
}

function deleteHolonObject(holonObjectId) {
	alert("Holon Object ID = "+holonObjectId);
	
}

function editHolonObject(holonObjectId) {
	$("#holonObjectActionState").val("Edit");
	$("#hiddenHolonObjectId").val(holonObjectId);
	getHolonObjectTypeFromDatabase();
	getHolonCoordinatorFromDatabase();
}
