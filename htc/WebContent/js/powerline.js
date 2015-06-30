/**
 * This JS file contains code related to drawing a power line on the map.
 * 
 * 
 * 
 */
$(document).ready(function() {

	var lineDrawingManager;
	var lineArray=[];

	$('#addPowerLine').click(function() {
		if ($(this).css("background-color") == "rgb(237, 237, 237)") {
			$(this).css("background-color", "rgb(153,255,255)");

			lineDrawingManager = new google.maps.drawing.DrawingManager({
				drawingMode :google.maps.drawing.OverlayType.POLYLINE ,
				drawingControl : false,
//				drawingControlOptions : {
//					position : google.maps.ControlPosition.TOP_CENTER,
//					drawingModes : [ google.maps.drawing.OverlayType.POLYLINE ]
//				},
				polylineOptions : {
					geodesic : true,
					clickable : true,
					strokeColor : "rgb(0, 0, 0)",
					strokeOpacity : 2.0,
					strokeWeight : 4.0
				}
			});
			// Setting the layout on the map 
			lineDrawingManager.setMap(map);
			
			google.maps.event.addListener(lineDrawingManager, 'overlaycomplete', function(event) {
			
				var newShape = event.overlay; // Object
		    	newShape.type = event.type;	// Polygon
		    	var start=newShape.getPath().getAt(0);
		    	var end=newShape.getPath().getAt(1);
		    	var latLangArr=[start,end];
		    	//alert("lat lang  "+latLangArr);
		    	lineArray.push(latLangArr);
		    	alert("line "+lineArray);
		    	var powerLineType="MAINLINE";
		    	
		    	var dataAttributes = {
		    			powerLineType : "MAINLINE",
		    			currentCapacity : 300,
		    			maxCapacity : 300,
		    			latStart : start.lat(),
		    			lngStart : start.lng(),
		    			latEnd : end.lat(),
		    			lngEnd : end.lng(),
		    			isConnected :false,
		    			resaonDown : "",
		    		};
		    	
		    	if(powerLineType=="MAINLINE"){
		    	ajaxRequest("drawMainLine", dataAttributes, drawPoweLineCallBack, {});	
		    	}
			});

		} else {
			//alert("abhinav 2");
			$(this).css("background-color", "rgb(237,237,237)");
			lineDrawingManager.setMap(null);
		}

	})

})

function drawPoweLineCallBack(data, options) {
//	var dataArray = data.split("!");
//	var holonObjectId = dataArray[0];
//	var holonName= dataArray[1];
//	var holonCoordinatorName_Holon= dataArray[2];	
//	var holonObjectTypeName= dataArray[3];
//	var ne_location= dataArray[4];
//	var sw_location= dataArray[5];
//	var lineConnectedState= dataArray[6];
//	var priority= dataArray[7];
//	var holonManagerName= dataArray[8];
//	var lat=ne_location.split("~");
//	contentString="<b>Priority: </b>"+priority+"<br>"+
//			"<b>Holon Object Id: </b>"+holonObjectId +"<br>"+
//			"<b>Holon Name: </b>"+holonName+"<br>"+
//			"<b>Holon Manager: </b>"+holonManagerName+"<br>"+
//			"<b>North East Location: </b>"+ne_location+"<br>"+
//			"<b>South West Location: </b>"+sw_location+"<br><br>"+
//			"<input type='button' id='editHolonObject' name='editHolonObject' value='Edit Holon Object' onclick='editHolonObject("+holonObjectId+")'/>&nbsp;&nbsp;"+
//			"<input type='button' id='deleteHolonObject' name='deleteHolonObject' value='Delete Holon Object'/>&nbsp;&nbsp;"+
//			"<input type='button' id='addHolonElement' name='addHolonElement' value='Add Holon Element' onclick='addHolonElement("+lat[0]+","+lat[1]+")'/>&nbsp;&nbsp;"+
//			"<input type='button' id='showHolonElement' name='showHolonElement' value='Show Holon Elements' onclick='showHolonElementsForHolon("+lat[0]+","+lat[1]+")'/>";
//	var infowindowHolonObject = new google.maps.InfoWindow({
//	      content: contentString,
//	      position:new google.maps.LatLng(lat[0],lat[1])
//	  });
//	infowindowHolonObject.open(map,map);
}