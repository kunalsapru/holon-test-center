/**
 * This code in this file creates a power source
 */
var psShape;
$(document).ready(function() {
	var psDrawingManager;
	$('#addPowerSource').click(function(evt) {
		if (addPowerSourceMode==false){
			addPowerSourceMode=true;
			$(this).css("background-color", "rgb(153,153,0)");
			psDrawingManager = new google.maps.drawing.DrawingManager({
				drawingMode :google.maps.drawing.OverlayType.CIRCLE ,
				drawingControl : false,
				circleOptions : {
					 strokeColor:'#FF0000',
				     strokeOpacity: 0.8,
				     strokeWeight: 2,
				     fillColor: '#FF0000',
				     fillOpacity: 0.35,
				},
			});
			psDrawingManager.setMap(map);
			
		google.maps.event.addListener(psDrawingManager, 'overlaycomplete', function(event) {
				psShape = event.overlay; // Object
				psShape.type = event.type;	
				createdPowerSourceObject=psShape;
		    	var center=psShape.getCenter();
		    	var radius=psShape.getRadius();		  	
		    	 $("#hiddenPowerObjectCenterLat").text(center.lat());
		    	 $("#hiddenPowerObjectCenterLng").text(center.lng());
		    	 $("#hiddenPowerObjectRad").text(radius);
		    	 getHolonCoordinatorFromDatabase("","pwholonCoordinatorId","powerObjectDetail");
		   
		});
			
		} else {
			$(this).css("background-color", "rgb(26, 26, 26)");
			psDrawingManager.setMap(null);
			addPowerSourceMode=false;
		}
	})

	$("#savePowerObject").click(function(event){
		savePowerObject();						
	});
	
	$("#cancelPowerObject").click(function(event){
		if(createdPowerSourceObject!=null && typeof createdPowerSourceObject != 'undefined')
			{
			createdPowerSourceObject.setMap(null);
			}
		closeDiv("powerObjectDetail");
	});
})


function createPowerSource(event)
{
	var pSource = new google.maps.Circle({
		 strokeColor:'#FF0000',
	     strokeOpacity: 0.8,
	     strokeWeight: 2,
	     fillColor: '#FF0000',
	     fillOpacity: 0.35,
	     map: map,
	     center: event.latlng,
	     radius: 3
	});
	
	
}