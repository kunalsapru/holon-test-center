/**
 * Abhinav
 */

var lineLocationForSubline="";
var lineIdForSubline="";
var hoLocationForSubline="";
var hoIdForSubline="";
var pSrcObjInd=false;
var hoObjInd=false;

$(document).ready(function() {

	$('#connectToPowerSource').click(function(evt) {
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
	else if(objectType=="HolonObject" || objectType=="PSObject")
	{
		if( objectType=="PSObject")
		{
			pSrcObjInd=true;
		}
		if( objectType=="HolonObject")
		{
			hoObjInd=true;
		}
		if(hoIdForSubline.toString().length==0)
		{
			hoIdForSubline=objectId;
			hoLocationForSubline=latLng;
		}
		else 
		{
			alert("Holon Object/Power Source clicked already");

		}
	}
	if(hoIdForSubline.toString().length!=0 && lineIdForSubline.toString().length!=0)
	{
		var lineType="";
		if(pSrcObjInd)
			{
			lineType="POWERSUBLINE";
			}else
				{
				lineType="SUBLINE";
				}
		pSrcObjInd=false;
		hoObjInd=false;
		openDivForCapacity(lineLocationForSubline,hoLocationForSubline,hoIdForSubline,lineIdForSubline,lineType);
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
		createdPowerLineObject=line;
		lineLocationForSubline="";
		lineIdForSubline="";
		hoLocationForSubline="";
		hoIdForSubline="";

	}
	/*alert("The Location of the Clicked point is  "+latLng);
					alert("The Id of the Clicked Object is "+objectId);
					alert("The Type of Object is "+objectType);*/

}

function openDivForCapacity(lineLocationForSubline,hoLocationForSubline,hoIdForSubline,lineIdForSubline,lineType)
{
	$("#powerLineStartLat").text(hoLocationForSubline.lat());
	$("#powerLineStartLng").text(hoLocationForSubline.lng());
	$("#powerLineEndLat").text(lineLocationForSubline.lat());
	$("#powerLineEndLng").text(lineLocationForSubline.lng());
	$("#powerLineHolonObjectIdHidden").text(hoIdForSubline);
	$("#powerLineIdForSubLine").text(lineIdForSubline);
	$("#powerLineType").text(lineType);
	openDiv('lineObjectDetail');
}

