/**
 * Abhinav
 */

var lineLocationForSubline="";
var lineIdForSubline="";
var hoLocationForSubline="";
var hoIdForSubline="";
$(document).ready(function() {

	$('#connectToPowerSource').click(function() {
		//alert("abhinav");
		if (connectToPowerSourceMode==false){
			connectToPowerSourceMode=true;
			$(this).css("background-color", "rgb(153,153,0)");

		} else {
			$(this).css("background-color", "rgb(26, 26, 26)");
			connectToPowerSourceMode=false;
		}

	})


})

function connectToPowerSource(latLng,objectId,objectType)
{
	if(objectType=="PowerLine" )
	{
		if(lineIdForSubline.toString().length==0)
		{
			lineIdForSubline=objectId;
			lineLocationForSubline=latLng;
		}
		else 
		{
			alert("line clicked already");
		}

	}
	else if(objectType=="HolonObject")
	{
		if(hoIdForSubline.toString().length==0)
		{
			hoIdForSubline=objectId;
			hoLocationForSubline=latLng;
		}
		else 
		{
			alert("Holon Object clicked already");

		}
	}
	if(hoIdForSubline.toString().length!=0 && lineIdForSubline.toString().length!=0)
	{

		openDivForCapacity(lineLocationForSubline,hoLocationForSubline);
		var line = new google.maps.Polyline({
			path: [
			       lineLocationForSubline, 
			       hoLocationForSubline
			       ],
			       strokeColor: "rgb(0, 0, 0)",
			       strokeOpacity: 2.0,
			       strokeWeight: 4.0,
			       map: map
		});
		createdSubLineObject=line;
		lineLocationForSubline="";
		lineIdForSubline="";
		hoLocationForSubline="";
		hoIdForSubline="";

	}
	/*alert("The Location of the Clicked point is  "+latLng);
					alert("The Id of the Clicked Object is "+objectId);
					alert("The Type of Object is "+objectType);*/

}

function openDivForCapacity(lineLocationForSubline,hoLocationForSubline)
{
	$("#subLineStartLat").text(hoLocationForSubline.lat());
	$("#subLineStartLng").text(hoLocationForSubline.lng());
	$("#subLineEndLat").text(lineLocationForSubline.lat());
	$("#subLineEndLng").text(lineLocationForSubline.lng());
	openDiv('subLineObjectDetail');
}



function saveSubLineObject()
{
	var startLat=$("#subLineStartLat").text();
	var startLng=$("#subLineStartLng").text();
	var endLat=$("#subLineEndLat").text();
	var endLng=$("#subLineEndLng").text();
	var maxCapacity=$("#subLineCapacity").val();
	var subLineObjectActionState = $("#subLineObjectActionState").text();
	var subLineId = $("#subLineIdHidden").text();
	$( "#subLineObjectDetail" ).slideUp(100);
	var lineType="SUBLINE";
	var dataAttributes = {
			lineType : "SUBLINE",
			currentCapacity : 300,
			maxCapacity : maxCapacity,
			latStart : startLat,
			lngStart : startLng,
			latEnd : endLat,
			lngEnd : endLng,
			isConnected :false,
			reasonDown : "",
			powerSourceId:1,
			powerLineId:powerLineId,
				};		    	
	var options = {
			newShape:createdSubLineObject,
			path:[new google.maps.LatLng(startLat, startLng),new google.maps.LatLng(endLat,endLng)],
			};	
	if(subLineObjectActionState=="Edit"){
		var option={
				subLineId:subLineId
		}
		ajaxRequest("editSubLine", dataAttributes, editSubLineObjectCallBack,option);
	}else
		{
		ajaxRequest("drawSubLine", dataAttributes, drawSubLineCallBack,options);
		}
}


function drawSubLineCallBack(data,options)
{

}

function editSubLineObjectCallBack(data,options)
{
	

}
