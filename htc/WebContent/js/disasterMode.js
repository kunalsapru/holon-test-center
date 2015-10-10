function disasterModeSelected() {
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
					radius : radius,
					zIndex:-1
					};
			ajaxRequest("createDisasterCircle", dataAttributes, createDisasterCircleCallback, {});
	});
	} else {
		$("#disasterMode").css("background-color", "rgb(26, 26, 26)");
		disasterModeDrawingManager.setMap(null);
		disasterMode=false;
	}

}

function createDisasterCircleCallback(data,options){
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
			     radius: parseInt(disasterCircleRadius),
			     zIndex:-1
			    });	
			showInfoWindowForDisaster(disasterCircleId,disasterValuesLatitude,disasterValuesLongitude,disasterMarker);
			globalDisasterList.set(disasterCircleId,disasterMarker);
		}
	});
	}
}

function showInfoWindowForDisaster(disasterId,disasterCircleLatitude,disasterCircleLongitude,createdDisasterCircle){
	google.maps.event.addListener(createdDisasterCircle, 'click', function(event) {
		if(deleteSelectedDisasterMode){
			var dataAttributes={
					disasterId: disasterId
			}
			ajaxRequest("deleteDisasterCircleFromDatabase", dataAttributes, deleteDisasterCircleFromDatabaseCallback, {});
			
		}
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
		swal({
			title: "This will remove all disasters permanently!",
			text: "This action will remove all disasters from database. Do you want to continue?",
			type: "warning",
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Yes, remove all disasters!',
			cancelButtonText: "No, don't do anything!",
			closeOnConfirm: true,
			closeOnCancel: true
		},
		function(isConfirm) {
		    if (isConfirm) {
		    	ajaxRequest("deleteAllDisasterCircleFromDatabase", {}, deleteAllDisasterCircleFromDatabaseCallback, {});	
			} else {
				$("#removeAllDisaster").css("background-color", "rgb(26, 26, 26)");
				deleteAllDisasterMode=false;
			}
		});
	} else {
		$("#removeAllDisaster").css("background-color", "rgb(26, 26, 26)");
		deleteAllDisasterMode=false;
	}
}

function deleteAllDisasterCircleFromDatabaseCallback(data,option){
	$("#removeAllDisaster").css("background-color", "rgb(26, 26, 26)");
	deleteAllDisasterMode=false;
	//Remove all disaster IDs from the global list
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