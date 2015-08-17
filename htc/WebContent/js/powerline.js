/**
 * This JS file contains code related to drawing a power line on the map.
 * 
 * 
 * 
 */
var lineShape;
$(document).ready(function() {

	var lineDrawingManager;
	var lineArray=[];
	

	$('#addPowerLine').click(function() {
		//alert("abhinav");
		var bgColor=$(this).css("background-color");
		if (drawPowerLineMode==false){
			drawPowerLineMode=true;
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
			
				lineShape = event.overlay; // Object
				lineShape.type = event.type;	
		    	var start=lineShape.getPath().getAt(0);
		    	var end=lineShape.getPath().getAt(1);
		    	var latLangArr=[start,end];
		       	lineArray.push(latLangArr);		    	
		    	createdPowerLineObject=lineShape;
		    	var snappedStart= findSnappedLocation(start);		    	
		    	var snappedEnd= findSnappedLocation(end);		    	
		    	 $("#powerLineStartLat").text(snappedStart.lat());
		    	 $("#powerLineStartLng").text(snappedStart.lng());
		    	 $("#powerLineEndLat").text(snappedEnd.lat());
		    	 $("#powerLineEndLng").text(snappedEnd.lng());
		    	  $("#powerLineTitle").text("Add Power Line");
		    	 $("#powerLineType").text("MAINLINE");
		    	 getListHolonFromDatabase();
		    	 
		    	
			});

		} else {
			$(this).css("background-color", "rgb(26, 26, 26)");
			lineDrawingManager.setMap(null);
			drawPowerLineMode=false;
		}

	})
	
	$("#savePowerLineObject").click(function(event){
		savePowerLineObject();						
	});
	
	$("#cancelPowerLine").click(function(event){
		if(createdPowerLineObject!=null && typeof createdPowerLineObject != 'undefined')
			{
			createdPowerLineObject.setMap(null);
			}
		closeDiv("lineObjectDetail");
	});

})

function savePowerLineObject()
{
	var startLat=$("#powerLineStartLat").text();
	var startLng=$("#powerLineStartLng").text();
	var endLat=$("#powerLineEndLat").text();
	var endLng=$("#powerLineEndLng").text();
	var maxCapacity=$("#powerLineCapacity").val();
	var powerLineObjectActionState = $("#powerLineObjectActionState").text();
	var powerLineId = $("#powerLineIdHidden").text();
	var HolonObjectId = $("#powerLineHolonObjectIdHidden").text();
	var powerLineForSubLine=$("#powerLineIdForSubLine").text();
	var powerLineType=$("#powerLineType").text();
	var holonForPowerLine = $("#holonForPowerLine option:selected").val();
	$( "#lineObjectDetail" ).slideUp(100);
	 $("#powerLineObjectActionState").text("ADD");
	 $("#powerLineIdHidden").text("");
	 $("#powerLineCapacity").text("");
	 $("#powerLineType").text("");
	 $("#powerLineHolonObjectIdHidden").text("");
	 $("#powerLineIdForSubLine").text("");
	var dataAttributes = {
		powerLineType : powerLineType,
		maxCapacity : maxCapacity,
		latStart : startLat,
		lngStart : startLng,
		latEnd : endLat,
		lngEnd : endLng,
		isConnected :false,
		reasonDown : "",
		powerLineId:powerLineId,
		HolonObjectId:HolonObjectId,
		powerLineForSubLine:powerLineForSubLine,
		holonForPowerLine : holonForPowerLine
	};		    	
	var options = {
		lineShape:createdPowerLineObject,
		path:[new google.maps.LatLng(startLat, startLng),new google.maps.LatLng(endLat,endLng)],
	};	
	if(powerLineObjectActionState=="Edit"){
		ajaxRequest("editPowerLine", dataAttributes, editPowerLineObjectCallBack,{});
	}else {
			ajaxRequest("drawPowerLine", dataAttributes, drawPoweLineCallBack,options);
	}
}

function editPowerLineObjectCallBack(data, options) {
	var powerLineId =data.split("*")[1];
	var content = getLineInfoWindowContent(data);
	var color = data.split("*")[8];
	var newLineShape=globalPlList.get(powerLineId.toString());
	newLineShape.setOptions({strokeColor:color});
	currentLineInfoWindowObject.setContent(content);
	$('#editPowerLineObject').click(function() {
		editPowerLine(powerLineId);			
	})
	globalPlList.set(powerLineId, newLineShape);
}


function drawPoweLineCallBack(data, options) {
	var newLineShape = options["lineShape"];
	var path = options["path"];
	var dataArray = data.split("!");
	var powerLineId = dataArray[0];
	var color = dataArray[1];
	newLineShape.setOptions({strokeColor:color,path: path});
	addMessageWindow(newLineShape,powerLineId)
	globalPlList.set(powerLineId, newLineShape);
}

function editPowerLine(powerLineId)
{
		var dataAttributes= {
			powerLineId : powerLineId,
			}
	ajaxRequest("updatePowerLine", dataAttributes, editPowerLineCallBack, {});	
}

function editPowerLineCallBack(data,options)
{
	var powerLine = data.split("!");
	var powerLineId=powerLine[2].trim();
	var maxCapacity=powerLine[3].trim();
	 $("#powerLineIdHidden").text(powerLineId);
	 $("#powerLineCapacity").val(maxCapacity);
	  $("#powerLineTitle").text("Edit Power Line");
	 $("#powerLineObjectActionState").text("Edit");
	 openDiv('lineObjectDetail');
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
    	if(connectToPowerSourceMode==true || addPowerSourceToLineMode==true)
    		{
    			connectToPowerSource(event.latLng,powerLineId.trim(),"PowerLine");
    		}
    else if(addSwitchonPowerLineMode==true)
		{
			createPowerSwitch(event.latLng,powerLineId.trim());
		}
	else{		
		var dataAttributes= {
				powerLineId : powerLineId,
				}
		var options= {
				position:event.latLng,
				powerLineId : powerLineId
				}
		
		ajaxRequest("getPowerLineInfo", dataAttributes, getPowerLineInfoCallBack, options);		
	}
    });
    

}

function closeOtherInfoWindows()
{
	if(typeof currentSwitchInfoWindow != 'undefined' && currentSwitchInfoWindow != null)
	{
		currentSwitchInfoWindow.close();
	}
	if(typeof currentInfoWindowObject != 'undefined' && currentInfoWindowObject != null)
	{
	currentInfoWindowObject.close();
	}
	if(typeof currentLineInfoWindowObject != 'undefined' && currentLineInfoWindowObject != null)
	{
		currentLineInfoWindowObject.close();
	}
	if(typeof currentPsInfoWindowObject != 'undefined' && currentPsInfoWindowObject != null)
	{
		currentPsInfoWindowObject.close();
	}
}
function getPowerLineInfoCallBack(data,options)
{
	closeOtherInfoWindows();
	var position=options["position"];
	var powerLineId =options["powerLineId"];
	var content= getLineInfoWindowContent(data);	
	
	var infowindowHolonObject = new google.maps.InfoWindow({
    content: content,		    
	});
	infowindowHolonObject.setOptions({position:position});
	infowindowHolonObject.open(map,map);
	$('#editPowerLineObject').click(function() {
		editPowerLine(powerLineId);			
	})
	currentLineInfoWindowObject=infowindowHolonObject;
	}


function getLineInfoWindowContent(data)
{
	var respStr= data.split("*");
	var isConnected=respStr[0];
	var powerLineId=respStr[1];
	var maximumCapacity=respStr[2];
	var currentCapacity=respStr[3];
	var lineType=respStr[4];
	var source=respStr[5];
	var dest=respStr[6];
	var holonIdForSubline=respStr[7];
	var powerSrcIdForSubline=respStr[8];
	var content= "<div class='table'><table>"+
			"<tr><td colspan='2' style='text-decoration: underline;'>Power Line Detail</td></tr>" +
			"<tr><td><b>PowerLine Id: </b>"+powerLineId +"</td>"+
			"<td><b>Connected: </b>"+isConnected+"</td></tr>"+
			"<tr><td><b>Maximum Capacity: </b>"+maximumCapacity+"</td>"+
			"<td><b>Current Capacity: </b>"+currentCapacity+"</td></tr>"+
			"<tr><td><b>PowerLine Type: </b>"+lineType+"</td>";
	if(lineType==="SUBLINE")
		{
		content = content.concat("<td><b>Connected Holon Object Id: </b>"+holonIdForSubline+"</td></tr>"+
				"<tr><td colspan='2' style='text-align: center;'>" +
				"<span class='button' id='editPowerLineObject'><i class='fa fa-pencil-square-o'></i>&nbsp;&nbsp;Edit Power Line</span></td></tr>" +
				"</div>");
		}else if(lineType==="POWERSUBLINE")
			{
			content = content.concat("<td><b>Connected Power Source Id: </b>"+powerSrcIdForSubline+"</td></tr>"+
					"<tr><td colspan='2' style='text-align: center;'>" +
					"<span class='button' id='editPowerLineObject'><i class='fa fa-pencil-square-o'></i>&nbsp;&nbsp;Edit Power Line</span></td></tr>" +
					"</div>");
			}
		else
		{
			content = content.concat("<td></td></td></tr>"+
					"<tr><td colspan='2' style='text-align: center;'>" +
					"<span class='button' id='editPowerLineObject'><i class='fa fa-pencil-square-o'></i>&nbsp;&nbsp;Edit Power Line</span></td></tr>" +
					"</div>");
		}
	return content;

}



function updateGlobalPowerLineList(powerLineId,toDelete)
{
	var dataAttributes= {
			powerLineId : powerLineId,
			}	
	var options= {
			toDelete : toDelete,
			}	
	ajaxRequest("updatePowerLine", dataAttributes, updatePowerLineCallBack, options);

}

function updatePowerLineCallBack(data, options)
{
	var toDelete=options['toDelete'];
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
     globalPlList.set(powerLineId,line);
     
}

function getListHolonFromDatabase()
{
	ajaxRequest("getListHolon", "", getListHolonPowerLineCallBack, "");
}

function getListHolonPowerLineCallBack(data,options)
{
	$("#holonForPowerLine").empty();
	var listHolonForPower = data.split("*");
	for(var i=0;i< listHolonForPower.length-1;i++)
		{
		var options= "<option value="+listHolonForPower[i].split("-")[0]+" id= "+listHolonForPower[i].split("-")[0]+" >"+listHolonForPower[i].split("-")[1]+"</option>";
		$("#holonForPowerLine").append(options);
		}
	openDiv('lineObjectDetail');
	
}