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
			createdDisasterCircle=disasterObjectShape;
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
	var disasterCircleID= data;
	var disasterColor= '#888888';
	createdDisasterCircle.setOptions({strokeColor:disasterColor,fillColor: disasterColor});
	attachMessage(disasterCircleID,createdDisasterCircle);
	globalDisasterList.set(disasterCircleID,createdDisasterCircle);
	
}

function showSavedDisasters(){
	ajaxRequest("getAllSavedDisasters", {}, getAllSavedDisastersCallback, {});
}

function getAllSavedDisastersCallback(data,options){
	var response=data.split("*");
	var disasterColor='#888888';
	$.each(response,function(index,value){
			disasterValues=value.replace("[","").replace("]","").split("^");
			if(disasterValues!= null || disasterValues!= ""){
			disasterCircleId=disasterValues[0];
			disasterCircleRadius=disasterValues[1];
			disasterValuesLatitude=disasterValues[2];
			disasterValuesLongitude=disasterValues[3];
			var disasterMarker= new google.maps.Circle({
				 strokeColor: disasterColor,
			     strokeOpacity: 1,
			     strokeWeight: 1,
			     fillColor: disasterColor,
			     fillOpacity: 0.35,
			     map: map,
			     center: new google.maps.LatLng(disasterValuesLatitude, disasterValuesLongitude),
			     radius: parseInt(disasterCircleRadius)
			    });	
		}
		
	});
}