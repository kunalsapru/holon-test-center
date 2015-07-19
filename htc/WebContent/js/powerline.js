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
		    	//alert("lat lang  "+latLangArr);
		    	lineArray.push(latLangArr);
		    	//alert("line "+lineArray);
		    	var powerLineType="MAINLINE";
		    	
		    	var dataAttributes = {
		    			powerLineType : "MAINLINE",
		    			currentCapacity : 300,
		    			maxCapacity : 300,
		    			latStart : start.lat(),
		    			lngStart : start.lng(),
		    			latEnd : end.lat(),
		    			lngEnd : end.lng(),
		    			isConnected :false,
		    			reasonDown : "",
		    			powerSourceId:1,
		    				};
		    	
		    	
		    	
		    	if(powerLineType=="MAINLINE"){
		    	ajaxRequest("drawPowerLine", dataAttributes, drawPoweLineCallBack, {newShape:newShape});
		    	}
			});

		} else {
			$(this).css("background-color", "rgb(26, 26, 26)");
			lineDrawingManager.setMap(null);
		}

	})

})

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
	 
	    addMessageWindow(line,powerLineId)
	}	
}




function drawPoweLineCallBack(data, options) {
	var newLineShape = options["newShape"];
	var dataArray = data.split("!");
	var powerLineId = dataArray[0];
	var color = dataArray[1];
	newLineShape.setOptions({strokeColor:color});
	addMessageWindow(newLineShape,powerLineId)
		
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
	var content= data;
	var position=options["position"];
	var infowindowHolonObject = new google.maps.InfoWindow({
    content: content,		    
	});
	infowindowHolonObject.setOptions({position:position});
	infowindowHolonObject.open(map,map);	
	}
