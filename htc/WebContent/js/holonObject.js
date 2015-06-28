/**
 * Java Script file for adding new holon objects
 */

function saveHolonObject(){

	//START KUNAL CODE>>>DONT EDIT/REMOVE IT
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
	$( "#holonObjectDetail" ).popup( "close" );
	
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

	//END KUNAL CODE>>>DONT EDIT/REMOVE IT
}

// START KUNAL CODE --> CRITICAL SECTION DO NOT ENTER
function editHolonObjectCallBack(data, options){
	alert(data);
}
function createHolonObjectCallBack(data, options) {
	alert(data);
	var dataArray = data.split("!");
	var holonObjectId = dataArray[0];
	var holonName= dataArray[1];
	var holonCoordinatorName_Holon= dataArray[2];	
	var holonObjectTypeName= dataArray[3];
	var ne_location= dataArray[4];
	var sw_location= dataArray[5];
	var lineConnectedState= dataArray[6];
	var priority= dataArray[7];
	var holonManagerName= dataArray[8];
	
	var lat=ne_location.split("~");
	
	contentString="<b>Priority: </b>"+priority+"<br>"+
			"<b>Holon Object Id: </b>"+holonObjectId +"<br>"+
			"<b>Holon Name: </b>"+holonName+"<br>"+
			"<b>Holon Manager: </b>"+holonManagerName+"<br>"+
			"<b>North East Location: </b>"+ne_location+"<br>"+
			"<b>South West Location: </b>"+sw_location+"<br><br>"+
			"<input type='button' id='editHolonObject' name='editHolonObject' value='Edit Holon Object' onclick='editHolonObject("+holonObjectId+")'/>&nbsp;&nbsp;"+
			"<input type='button' id='deleteHolonObject' name='deleteHolonObject' value='Delete Holon Object'/>&nbsp;&nbsp;"+
			"<input type='button' id='addHolonElement' name='addHolonElement' value='Add Holon Element' onclick='addHolonElement("+lat[0]+","+lat[1]+")'/>&nbsp;&nbsp;"+
			"<input type='button' id='showHolonElement' name='showHolonElement' value='Show Holon Elements' onclick='showHolonElementsForHolon("+lat[0]+","+lat[1]+")'/>";
	var infowindowHolonObject = new google.maps.InfoWindow({
	      content: contentString,
	      position:new google.maps.LatLng(lat[0],lat[1])
	  });
	infowindowHolonObject.open(map,map);
	//alert(contentString);
}
//END KUNAL CODE

