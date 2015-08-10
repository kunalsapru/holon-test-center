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
		    	 $("#hiddenPowerObjectCenterLat").val(center.lat());
		    	 $("#hiddenPowerObjectCenterLng").val(center.lng());
		    	 $("#hiddenPowerObjectRad").val(radius);
		    	 $("#powerObjectActionState").val("Add");
		    	 $("#powerObjTitle").text("Add Power Source");
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

function savePowerObject()
{
	var psMaxProdCap=$("#psMaxProdCap").val();
	var psCurrentPord=$("#psCurrentPord").val();
	var psStatus=$("#psStatus option:selected").val();
	var psCoordinatorId=$("#pwholonCoordinatorId option:selected").val();
	var radius=$("#hiddenPowerObjectRad").val();
	var latCenter=$("#hiddenPowerObjectCenterLat").val();
	var lngCenter=$("#hiddenPowerObjectCenterLng").val();
	var psActionState = $("#powerObjectActionState").val();
	var hiddenPowerObjectId = $("#hiddenPowerObjectId").val();
	$( "#powerObjectDetail" ).slideUp(100);
	var dataAttributes = {
			psMaxProdCap : psMaxProdCap,
			psCurrentPord : psCurrentPord,
			psStatus : psStatus,
			psCoordinatorId:psCoordinatorId,
			radius : radius,
			latCenter : latCenter,
			lngCenter : lngCenter,
			hiddenPowerObjectId : hiddenPowerObjectId
		};
	if(psActionState == "Edit"){
		ajaxRequest("editPowerSourceObject", dataAttributes, editPowerSourceObjectCallBack, {});
	} else {
		ajaxRequest("createPowerSourceObject", dataAttributes, createPowerSourceObjectCallBack, {});							
	}
}

function createPowerSourceObjectCallBack(data,options)
{
var resp = data.split("!");
	var psId=resp[0];
	var status=resp[1];
	var psStatusColor="red";
	if(status==1)
		{
		psStatusColor="green";
		}
createdPowerSourceObject.setOptions({strokeColor:psStatusColor,fillColor: psStatusColor});
google.maps.event.addListener(createdPowerSourceObject, 'click', function(event) {
	  addEventActionToPsObject(psId,event.latLng)
	  });
}

function addEventActionToPsObject(psId,latLng)
{
	 if(addPowerSourceToLineMode==true)
		{
		 addPowerSourceToLine(latLng,psId,"PSObject");
		}else{
	  var dataAttributes= {
			  psId : psId,
			}
	  ajaxRequest("getPsObjectInfoWindow", dataAttributes, getPsObjectInfoWindowCallBack, {});
		}
}

function getPsObjectInfoWindowCallBack(data,option)
{
	var resp =data.split("!");
	var powerSrcId=resp[0];
	var maxProd=resp[1];
	var currProd=resp[2];
	var CoHolonId=resp[3];
	var coHoLocation=resp[4];
	var powerStatus=resp[5];
	var minProd=resp[6];
	var latCenter=resp[7];
	var lngCenter=resp[8];
	
	var powerStatusVal="Power On";
	if(powerStatus=="false")
		{
		powerStatusVal="Power Off";
		}
	
	var contentString=
		"<div class='table'><table>"+
		"<tr><td colspan='2' style='text-decoration: underline;'>Power Source Details</td></tr>"+
		"<tr><td><b>PowerSource Id: "+powerSrcId +"</td>"+
		"<td>Status: "+powerStatusVal+"</td></tr>"+
		"<tr><td>Maximum Production Capacity: "+maxProd+"</td>"+
		"<td>Current Production: "+currProd+"</td></tr>";
		if(CoHolonId==0)
		{
			contentString=contentString.concat("<tr><td>Coordinator Id: Not Part of any Holon</td>");
		}else
		{
			contentString=contentString.concat("<tr><td>Coordinator Id: <a href='#' id='CoHolonId'>"+CoHolonId+"</a></td>");
		}
		
		contentString=contentString.concat("<td>Minimum Production Capacity: "+minProd+"</td></tr>"+
				"</table>"+
				"<br /><hr>"+
				"<table><tr><td colspan='2' style='text-align: center;'>"+
				"<span class='button' id='editPowerSource' title='Edit Power Source'><i class='fa fa-pencil-square-o'>&nbspEdit Power Source</i></span>"+
				"</td></tr></table><hr>");
		
		closeOtherInfoWindows();
		var infowindowPsObject = new google.maps.InfoWindow({
		      content: contentString,
		      position:new google.maps.LatLng(latCenter,lngCenter)
		  });
		infowindowPsObject.open(map,map);
		$('#editPowerSource').click(function() {
			editPowerSource(powerSrcId,infowindowPsObject);			
		})
			$('#CoHolonId').click(function() {
				zoomToHolon(CoHolonId,coHoLocation);			
		})
				
		currentPsInfoWindowObject=infowindowPsObject;
}


function editPowerSourceObjectCallBack(data,options)
{
alert(data);	

}

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