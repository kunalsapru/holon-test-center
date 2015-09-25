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
	var response= data.split("*");
	var disasterCircleID= response[0];
	var diasaterCircleLatitude= response[1];
	var disasterCircleLongitude= response[2];
	var disasterColor= '#888888';
	createdDisasterCircle.setOptions({strokeColor:disasterColor,fillColor: disasterColor});
	showInfoWindowForDisaster(disasterCircleID,diasaterCircleLatitude,disasterCircleLongitude,createdDisasterCircle);
	globalDisasterList.set(disasterCircleID,createdDisasterCircle);
	
}

function showSavedDisasters(){
	ajaxRequest("getAllSavedDisasters", {}, getAllSavedDisastersCallback, {});
}

function getAllSavedDisastersCallback(data,options){
	if(data != "[]"){
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
			showInfoWindowForDisaster(disasterCircleId,disasterValuesLatitude,disasterValuesLongitude,disasterMarker);
		}
		
	});
	}

}

function showInfoWindowForDisaster(disasterId,disasterCircleLatitude,disasterCircleLongitude,createdDisasterCircle){
	google.maps.event.addListener(createdDisasterCircle, 'click', function(event) {
		var id= disasterId.replace(","," ");
		if(deleteSelectedDisasterMode){
			var dataAttributes={
					disasterId: parseInt(id)
			}
			ajaxRequest("deleteDisasterCircleFromDatabase", dataAttributes, deleteDisasterCircleFromDatabaseCallback, {});
			
		}else{/*
		var contentString=
			"<div class='table'><table>"+
			"<tr><td colspan='1' style='text-decoration: underline;'>Disaster Details</td></tr>"+
			"<tr><td><b>Disaster Id: "+disasterId.replace(","," ") +"</td></tr></table></div>"+
			
		closeOtherInfoWindows();
		var infowindowDisaster = new google.maps.InfoWindow({
		      content: contentString,
		      position:new google.maps.LatLng(disasterCircleLatitude,disasterCircleLongitude)
		  });
		infowindowDisaster.open(map,map);	
		*/}
   });
	
	
}


function deleteSelectedDisaster(){
	if(deleteSelectedDisasterMode == false){
		deleteSelectedDisasterMode =true;
		$("#removeSelectedDisaster").css("background-color", "rgb(153,153,0)");
	}else{
		$("#removeSelectedDisaster").css("background-color", "rgb(26, 26, 26)");
		deleteSelectedDisasterMode=false;
	}
}

function deleteAllDisaster(){
	if(deleteAllDisasterMode== false){
		deleteAllDisasterMode=true;
		$("#removeAllDisaster").css("background-color", "rgb(153,153,0)");
		ajaxRequest("deleteAllDisasterCircleFromDatabase", {}, deleteAllDisasterCircleFromDatabaseCallback, {});
	}else{
		$("#removeAllDisaster").css("background-color", "rgb(26, 26, 26)");
		deleteAllDisasterMode=false;
	}
}

function deleteAllDisasterCircleFromDatabaseCallback(data,option){
	//Get all ids from database and remove from the global list
	if(data!= undefined && data!= null && data!="failure"){
		var disasterIds=data.split("*");
		$.each(disasterIds,function(key,value){
			if(value!= null && value != ""){
				deleteDisasterMarkerFromGlobalList(value);
			}
		});
	}
	
}

function deleteDisasterCircleFromDatabaseCallback(data,options){
	$("#removeSelectedDisaster").css("background-color", "rgb(26, 26, 26)");
	deleteSelectedDisasterMode=false;
	var disasterId= data.split("*");
	if(data!="failure" && disasterId[0] != undefined && disasterId[0] != null){
		deleteDisasterMarkerFromGlobalList(disasterId[0]);	
	}
}

function deleteDisasterMarkerFromGlobalList(disasterId){
	var disasterCircleMarker= globalDisasterList.get(disasterId);
	if(disasterCircleMarker!= null && disasterCircleMarker!= undefined && disasterCircleMarker!= ""){
		disasterCircleMarker.setVisible(false);
		delete globalDisasterList[disasterId];
	}
}