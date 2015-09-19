function disasterModeSelected(){
	
	
	
	if (disasterMode==false){
		disasterMode=true;
		$("#disasterMode").css("background-color", "rgb(153,153,0)");
		disasterModeDrawingManager = new google.maps.drawing.DrawingManager({
			drawingMode :google.maps.drawing.OverlayType.CIRCLE ,
			drawingControl : false,
			circleOptions : {
				 strokeColor:'#888888',
			     strokeOpacity: 1,
			     strokeWeight: 2,
			     fillColor: '#888888',
			     fillOpacity: 0.35,
			},
		});
		disasterModeDrawingManager.setMap(map);
		google.maps.event.addListener(disasterModeDrawingManager, 'overlaycomplete', function(event) {
			var disasterObjectShape = event.overlay; // Object
			disasterObjectShape.type = event.type;	// Circle
			//event.setVisible(false);
			var lat=event.overlay.center.lat();
			var lng=event.overlay.center.lng();
			var radius=event.overlay.radius;
			var dataAttributes= {
					latitude : lat,
					longitude : lng,
					radius : radius
					};
			ajaxRequest("getAllPointsInsideCircle", dataAttributes, getAllPointsInsideCircleCallback, {});
			
	   
	});
	} else {
		$("#disasterMode").css("background-color", "rgb(26, 26, 26)");
		disasterModeDrawingManager.setMap(null);
		disasterMode=false;
	}

}

function getAllPointsInsideCircleCallback(data,options){
	console.log("Data is"+data);
}