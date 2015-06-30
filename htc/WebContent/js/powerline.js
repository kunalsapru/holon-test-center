/**
 * This JS file contains code related to drawing a power line on the map.
 * 
 * 
 * 
 */
var newShape;
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
			
				newShape = event.overlay; // Object
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
		    	ajaxRequest("drawPowerLine", dataAttributes, drawPoweLineCallBack, {});	
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
	var dataArray = data.split("!");
	var powerLineId = dataArray[0];
	var isConnected= dataArray[1];
	var maxCapacity= dataArray[2];	
	var currentCapacity= dataArray[3];
	var powerLineType= dataArray[4];
	var resaonDown= dataArray[5];
	var latStart= dataArray[6];
	var lngStart= dataArray[7];
	var latEnd= dataArray[8];
	var lngEnd= dataArray[9];
	
	contentString="<b>Power Line Type: </b>"+powerLineType+"<br>"+
			"<b>Power Line Id: </b>"+powerLineId +"<br>"+
			"<b>Connected : </b>"+isConnected+"<br>"+
			"<b>Max Capacity: </b>"+maxCapacity+"<br>"+
			"<b>Current Capacity: </b>"+currentCapacity+"<br>"+
			"<b>Start Location: </b>"+"("+latStart+")"+",("+lngStart+")"+"<br>"+
			"<b>End Location: </b>"+"("+latEnd+")"+",("+lngEnd+")"+"<br>";
		var infowindowHolonObject = new google.maps.InfoWindow({
	      content: contentString,
	      position:new google.maps.LatLng(latEnd,lngEnd)
	  });
	attachMessage(contentString, newShape, new google.maps.LatLng(latEnd,lngEnd));
	infowindowHolonObject.open(map,map);
}

