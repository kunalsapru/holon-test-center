/**
 * This JS file contains code related to drawing a power line on the map.
 * 
 * 
 * 
 */
var newShape;
$(document).ready(function() {

	var lineDrawingManager;
	var lineArray=[];
	

	$('#addPowerLine').click(function() {
		//alert("abhinav");
		var bgColor=$(this).css("background-color");
		if (bgColor == "rgb(26, 26, 26)"){
			$(this).css("background-color", "rgb(153,153,0)");

			lineDrawingManager = new google.maps.drawing.DrawingManager({
				drawingMode :google.maps.drawing.OverlayType.POLYLINE ,
				drawingControl : false,
//				drawingControlOptions : {
//					position : google.maps.ControlPosition.TOP_CENTER,
//					drawingModes : [ google.maps.drawing.OverlayType.POLYLINE ]
//				},
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
			
				newShape = event.overlay; // Object
		    	newShape.type = event.type;	
		    	var start=newShape.getPath().getAt(0);
		    	var end=newShape.getPath().getAt(1);
		    	var latLangArr=[start,end];
		       	lineArray.push(latLangArr);		    	
		    	createdPowerLineObject=newShape;
		    	var snappedStart= findSnappedLocation(start);		    	
		    	var snappedEnd= findSnappedLocation(end);		    	
		    	 $("#powerLineStartLat").text(snappedStart.lat());
		    	 $("#powerLineStartLng").text(snappedStart.lng());
		    	 $("#powerLineEndLat").text(snappedEnd.lat());
		    	 $("#powerLineEndLng").text(snappedEnd.lng());
		    	 alert("Abhinav")
		    	 openDiv('lineObjectDetail');
		    	
			});

		} else {
			$(this).css("background-color", "rgb(26, 26, 26)");
			lineDrawingManager.setMap(null);
		}

	})

})

function savePowerLineObject()
{
	var startLat=$("#powerLineStartLat").text();
	var startLng=$("#powerLineStartLng").text();
	var endLat=$("#powerLineEndLat").text();
	var endLng=$("#powerLineEndLng").text();
	var maxCapacity=$("#powerLineCapacity").val();
	$( "#lineObjectDetail" ).slideUp(100);
	var powerLineType="MAINLINE";
	var dataAttributes = {
			powerLineType : "MAINLINE",
			currentCapacity : 300,
			maxCapacity : maxCapacity,
			latStart : startLat,
			lngStart : startLng,
			latEnd : endLat,
			lngEnd : endLng,
			isConnected :false,
			reasonDown : "",
			powerSourceId:1,
				};		    	
	var options = {
			newShape:createdPowerLineObject,
			path:[new google.maps.LatLng(startLat, startLng),new google.maps.LatLng(endLat,endLng)],
			};	
	if(powerLineType=="MAINLINE"){
	ajaxRequest("drawPowerLine", dataAttributes, drawPoweLineCallBack,options);
	}
}


function drawPoweLineCallBack(data, options) {
	var newLineShape = options["newShape"];
	var path = options["path"];
	var dataArray = data.split("!");
	var powerLineId = dataArray[0];
	var color = dataArray[1];
	newLineShape.setOptions({strokeColor:color,path: path});
	addMessageWindow(newLineShape,powerLineId)
	globalPlList.set(powerLineId, newLineShape);
}

function findSnappedLocation(lineLocation)
{
	var finalLocation=lineLocation;
	var listSize=globalPlList.size;
	for(var i=1;i<=listSize;i++)
		{
			var line = globalPlList.get(i.toString());
			if(line!=undefined)
				{
				var startPath=line.getPath().getAt(0);
				var endPath=line.getPath().getAt(1);
				var tempCircleStart=new google.maps.Circle({
					 center: startPath,
				     radius: 10
				    });	
				var tempCircleEnd=new google.maps.Circle({
					 center: endPath,
				     radius: 10,
				    });	
				//alert("Abhiav");
				var isStartPath = google.maps.Circle.prototype.contains(lineLocation, tempCircleStart);
				//alert("Abhiav1");
				var isEndPath = google.maps.Circle.prototype.contains(lineLocation, tempCircleEnd);
				//alert("Abhiav2");
				if(isStartPath)
					{
					finalLocation=startPath;
					return finalLocation;
					}
				else if(isEndPath)
					{
					finalLocation=endPath;
					return finalLocation;
					}
				//alert("isEndPath"+isEndPath);
				}
			else
				{
				//alert("line does not have value"+line);
				listSize++; //To increase the size if a number is missing from id seq like 1,2,4,5,7 . Here Ids 3 and 6 are missing
				}
		
		}

	return finalLocation;


}


function showSavedPowerLines()
{
	ajaxRequest("showPowerLines", {}, showPowerLinesCallBack, {});
}

function showPowerLinesCallBack(data, options){
	var powerLineList = data.split("*");
	var powerLines=[];
	for (var i=0; i<powerLineList.length-1; i++) {
		var powerLine = powerLineList[i].split("!");
		var location = powerLine[0];
		var color=powerLine[1];
		var powerLineId=powerLine[2].trim();
		var startLat = location.split("^")[0].split("~")[0].replace("[","").replace(",","");
		var startLng = location.split("^")[0].split("~")[1];
		var endLat = location.split("^")[1].split("~")[0];
		var endLng = location.split("^")[1].split("~")[1];
	    var line = new google.maps.Polyline({
	        path: [
	            new google.maps.LatLng(startLat, startLng), 
	            new google.maps.LatLng(endLat, endLng)
	        ],
	        strokeColor:color,
	        strokeOpacity: 2.0,
	        strokeWeight: 4,
	        map: map
	    });
	     addMessageWindow(line,powerLineId);
	     globalPlList.set(powerLineId, line);
	}	
}



function addMessageWindow(line,powerLineId)
{		
	 
    google.maps.event.addListener(line, 'click', function(event) {
    	if(clickedToDrawSwitch=="switchOnPowerLine")
		{
			createPowerSwitch(event.latLng,powerLineId.trim());
		}
	else{		
		var dataAttributes= {
				powerLineId : powerLineId,
				}
		
		ajaxRequest("getPowerLineInfo", dataAttributes, getPowerLineInfoCallBack, {position:event.latLng});		
	}
    });
    

}

function getPowerLineInfoCallBack(data,options)
{
	if(typeof currentSwitchInfoWindow != 'undefined' && currentSwitchInfoWindow != null)
	{
		currentSwitchInfoWindow.close();
	}
	if(typeof currentInfoWindowObject != 'undefined' &&currentInfoWindowObject != null)
	{
	currentInfoWindowObject.close();
	}
	if(typeof currentLineInfoWindowObject != 'undefined' &&currentLineInfoWindowObject != null)
	{
		currentLineInfoWindowObject.close();
	}
	var content= data;
	var position=options["position"];
	var infowindowHolonObject = new google.maps.InfoWindow({
    content: content,		    
	});
	infowindowHolonObject.setOptions({position:position});
	infowindowHolonObject.open(map,map);
	currentLineInfoWindowObject=infowindowHolonObject;
	}


function updateGlobalPowerLineList(powerLineId,toDelete)
{
	var dataAttributes= {
			powerLineId : powerLineId,
			}	
	ajaxRequest("updatePowerLine", dataAttributes, updatePowerLineCallBack, {toDelete:toDelete});

}

function updatePowerLineCallBack(data, options)
{
	//alert(data);
	var toDelete=options["toDelete"];
	var powerLine = data.split("!");
	var location = powerLine[0];
	var color=powerLine[1];
	var powerLineId=powerLine[2].trim();
	var startLat = location.split("^")[0].split("~")[0].replace("[","").replace(",","");
	var startLng = location.split("^")[0].split("~")[1];
	var endLat = location.split("^")[1].split("~")[0];
	var endLng = location.split("^")[1].split("~")[1];
	if(toDelete)
		{
		//globalPlList[powerLineId].setMap(null);
		globalPlList.get(powerLineId).setMap(null);
		}
	var line = new google.maps.Polyline({
        path: [
            new google.maps.LatLng(startLat, startLng), 
            new google.maps.LatLng(endLat, endLng)
        ],
        strokeColor:color,
        strokeOpacity: 2.0,
        strokeWeight: 4,
        map: map
    });
     addMessageWindow(line,powerLineId);
    // globalPlList[powerLineId]=line;
     globalPlList.set(powerLineId,line);
     
}
