/**
 * Java Script file for functions related to holon objects
 */

function saveHolonObject(){
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
	alert(data);
}
function createHolonObjectCallBack(data, options) {
	var dataArray = data.split("!");
	var holonObjectId = dataArray[0];
	var holonCoordinatorName_Holon= dataArray[1];	
	var holonObjectTypeName= dataArray[2];
	var ne_location= dataArray[3];
	var sw_location= dataArray[4];
	var lineConnectedState= dataArray[5];
	var holonManagerName= dataArray[6];
	var lat=ne_location.split("~");
	contentString="<b>Holon Object Id: </b>"+holonObjectId +"<br>"+
			"<b>Holon Object Type: </b>"+holonObjectTypeName+"<br>"+
			"<b>Holon Manager: </b>"+holonManagerName+"<br>"+
			"<b>North East Location: </b>"+ne_location+"<br>"+
			"<b>South West Location: </b>"+sw_location+"<br><br>"+
			"<input type='button' id='editHolonObject' name='editHolonObject' value='Edit Holon Object' onclick='editHolonObject("+holonObjectId+")'/>&nbsp;&nbsp;"+
			"<input type='button' id='deleteHolonObject' name='deleteHolonObject' value='Delete Holon Object'/>&nbsp;&nbsp;"+
			"<input type='button' id='addHolonElement' name='addHolonElement' value='Add Holon Element' onclick='addHolonElement("+holonObjectId+")'/>&nbsp;&nbsp;"+
			"<input type='button' id='showHolonElements' name='showHolonElements' value='Show Holon Elements' onclick='showHolonElements("+holonObjectId+")'/>";
	var infowindowHolonObject = new google.maps.InfoWindow({
	      content: contentString,
	      position:new google.maps.LatLng(lat[0],lat[1])
	  });
	infowindowHolonObject.open(map,map);
}

function showHolonObjects() {
	ajaxRequest("showHolonObjects", {}, showHolonObjectsCallBack, {});
	showSavedPowerLines();
	showSavedPowerSwitches();
}

function showHolonObjectsCallBack(data, options){
	var hoObjectsList = data.split("*");
	var rectangles=[];
	for (var i=0; i<hoObjectsList.length-1; i++) {
		var holonObject = hoObjectsList[i].split("!");
		var location = holonObject[0];
		var contentString = holonObject[1];
		var ne_location_lat = location.split("^")[0].split("~")[0].replace("[","").replace(",","");
		var ne_location_lng = location.split("^")[0].split("~")[1];
		var sw_location_lat = location.split("^")[1].split("~")[0];
		var sw_location_lng = location.split("^")[1].split("~")[1];
	    var rectangleFromFactory = new google.maps.Rectangle({
		      map: map,
		      bounds: new google.maps.LatLngBounds(
		    	      new google.maps.LatLng(sw_location_lat, sw_location_lng),
		    	      new google.maps.LatLng(ne_location_lat, ne_location_lng))
		    });
		 attachMessage(contentString, rectangleFromFactory, new google.maps.LatLng(ne_location_lat, sw_location_lng),"rectangle",1);
	}	
}

function attachMessage(contentString, marker, position,text,id) {
	 
	  var infowindow = new google.maps.InfoWindow({
	    content: contentString,
	    position:position
	  });

	  google.maps.event.addListener(marker, 'click', function(event) {
		  
		if(text=="line" && clickedToDrawSwitch=="switchOnPowerLine") {
			$("#switchOnPowerLine").css("background-color", "rgb(26, 26, 26)");
			clickedToDrawSwitch="";
			var switchMarker="";
				switchMarker = new google.maps.Marker({
			        position: event.latLng,
			        draggable: false,
			        icon:"css/images/on.png",
			        map: map
			    });	
				var newId= id.replace(" ","");
				createPowerSwitch(event.latLng.lat(),event.latLng.lng(),newId);
		} else if(text=="switch"){

			var currentImage=marker.icon.url;
				switch (currentImage){
				case "css/images/switch-on.png":
					marker.setIcon("css/images/switch-off.png");
					break;	
				}
				
			if(currentImage==undefined)
				{
				currentImage=marker.icon;
				switch (currentImage){
				case "css/images/on.png":
					marker.setIcon("css/images/off.png");
					break;	
				case "css/images/off.png" :
					marker.setIcon("css/images/on.png");
					break;	
				}
				
				}
		
		}
		 else {
			infowindow.open(marker.get('map'), marker);
			}
	    
	  });
}

function closeDiv(id) {
	$("#"+id).slideUp(100);
}

function openDiv(id) {
	$("#"+id).slideDown(100);
}
