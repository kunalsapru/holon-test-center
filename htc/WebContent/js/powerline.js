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
				drawingMode :null,
				drawingControl : true,
				drawingControlOptions : {
					position : google.maps.ControlPosition.TOP_CENTER,
					drawingModes : [ google.maps.drawing.OverlayType.POLYLINE ]
				},
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
		    	//alert("line "+lineArray);
			});

		} else {
			//alert("abhinav 2");
			$(this).css("background-color", "rgb(237,237,237)");
			lineDrawingManager.setMap(null);
		}

	})

})