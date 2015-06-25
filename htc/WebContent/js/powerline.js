/**
 * This JS file contains code related to drawing a power line on the map.
 * 
 * 
 * 
 */
$(document).ready(function() {

	var lineDrawingManager;
	var lineArray;

	$('#addPowerLine').click(function() {
		if ($(this).css("background-color") == "rgb(237, 237, 237)") {
			$(this).css("background-color", "rgb(153,255,255)");

			lineDrawingManager = new google.maps.drawing.DrawingManager({
				drawingMode : google.maps.drawing.OverlayType.POLYGON,
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
		    	alert("abhinav 3"+newShape.type);
		    	alert(newShape.getPaths().getAt(0));
		    	
		    	
				
				
			});

		} else {
			//alert("abhinav 2");
			$(this).css("background-color", "rgb(237,237,237)");
			lineDrawingManager.setMap(null);
		}

	})

})