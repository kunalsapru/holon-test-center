$(document).ready(function(){
	window.globalPowerLineListSimulation = new Map(); //Power Line
	window.currentInfoWindowObjectSimulation = null;
	window.currentLineInfoWindowObjectSimulation = null;
	window.currentSwitchInfoWindowSimulation = null;
	window.currentPsInfoWindowObjectSimulation = null;
	var map1;
	initializeMap();
	openDiv('simulationDiv');
	clearAllSimulations();

});
function closeDiv(id) {
	$("#"+id).slideUp(100);
}

function openDiv(id) {
	console.log("Opening Div -- "+id);
	$("#"+id).slideDown(100);
}

function initializeMap() {
	mapProperties = {
		center : new google.maps.LatLng(49.860976, 8.664139),
		zoom : 18,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map1 = new google.maps.Map(document.getElementById("googleMaps"), mapProperties);
	directionsService = new google.maps.DirectionsService();
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map1);
}

function abortSimulationRequests(id) {
	closeDiv(id);
	//More code will go here to cancel all subsequent requests
}

function clearAllSimulations() {
	var contentString = "<tr><td>Clear Simulation:</td><td>Clearing previous simulations!</td>";
	$("#simulationDivTable").append(contentString);
	ajaxRequest("clearAllSimulations", {}, clearAllSimulationsCallBack, {});
}

function clearAllSimulationsCallBack(data, options) {
	if(data == 'true') {
		var contentString = "<tr><td>Clear Simulation:</td><td>Previous simulations cleared!</td>";
	} else {
		var contentString = "<tr><td>Clear Simulation:</td><td>Error in clearing previous simulations. Please check application logs.</td>";
	}
	$("#simulationDivTable").append(contentString);
	drawPowerLinesSimulation();	
}

function drawPowerLinesSimulation() {
	var contentString = "<tr><td>Creating Main lines:</td><td>Creating Main lines in Database!</td>";
	$("#simulationDivTable").append(contentString);
	ajaxRequest("drawPowerLinesSimulation", {}, drawPowerLinesSimulationCallBack, {});
}

function drawPowerLinesSimulationCallBack(data, options) {
	var contentString = "<tr><td>Drawing Power Lines:</td><td>Drawing Main Power Lines in Map!</td>";
	$("#simulationDivTable").append(contentString);
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
			       map: map1
		});
		addMessageWindowSimulation(line,powerLineId);
		globalPowerLineListSimulation.set(powerLineId, line);
	}
}

function addMessageWindowSimulation(line,powerLineId){
	google.maps.event.addListener(line, 'click', function(event) {
				var dataAttributes= {
				powerLineId : powerLineId,
			}
			var options= {
					position:event.latLng,
					powerLineId : powerLineId
			}
			ajaxRequest("getPowerLineInfo", dataAttributes, getPowerLineInfoCallBackSimulation, options);		
	});
}

function closeOtherInfoWindowsSimulation(){
	if(typeof currentSwitchInfoWindowSimulation != 'undefined' && currentSwitchInfoWindowSimulation != null){
		currentSwitchInfoWindowSimulation.close();
	}
	if(typeof currentInfoWindowObjectSimulation != 'undefined' && currentInfoWindowObjectSimulation != null){
		currentInfoWindowObjectSimulation.close();
	}
	if(typeof currentLineInfoWindowObjectSimulation != 'undefined' && currentLineInfoWindowObjectSimulation != null){
		currentLineInfoWindowObjectSimulation.close();
	}
	if(typeof currentPsInfoWindowObjectSimulation != 'undefined' && currentPsInfoWindowObjectSimulation != null){
		currentPsInfoWindowObjectSimulation.close();
	}
}

function getPowerLineInfoCallBackSimulation(data,options){
	closeOtherInfoWindowsSimulation();
	var position=options["position"];
	var powerLineId =options["powerLineId"];
	var content= getLineInfoWindowContentSimulation(data);	
	var infoWindowPowerLine = new google.maps.InfoWindow({
		content: content,		    
	});
	infoWindowPowerLine.setOptions({position:position});
	infoWindowPowerLine.open(map1,map1);
	currentLineInfoWindowObjectSimulation=infoWindowPowerLine;
}

function getLineInfoWindowContentSimulation(data){
	var respStr= data.split("*");
	var isConnected=respStr[0];
	var powerLineId=respStr[1];
	var maximumCapacity=respStr[2];
	var currentCapacity=respStr[3];
	var lineType=respStr[4];
	var source=respStr[5];
	var dest=respStr[6];
	var holonObjectIdForSubline=respStr[7];
	var powerSrcIdForSubline=respStr[8];
	var content= "<div class='table'><table>"+
	"<tr><td colspan='2' style='text-decoration: underline;'>Power Line Detail</td></tr>" +
	"<tr><td><b>PowerLine Id: </b>"+powerLineId +"</td>"+
	"<td><b>Connected: </b>"+isConnected+"</td></tr>"+
	"<tr><td><b>Maximum Capacity: </b>"+maximumCapacity+"</td>"+
	"<td><b>Current Capacity: </b>"+currentCapacity+"</td></tr>"+
	"<tr><td><b>PowerLine Type: </b>"+lineType+"</td>";
	if(lineType==="SUBLINE"){
		content = content.concat("<td><b>Connected Holon Object Id: </b>"+holonObjectIdForSubline+"</td></tr></div>");
	} else if(lineType==="POWERSUBLINE"){
		content = content.concat("<td><b>Connected Power Source Id: </b>"+powerSrcIdForSubline+"</td></tr></div>");
	} else{
		content = content.concat("<td></td></td></tr></div>");
	}
	content = content.concat("<div class = 'table'><table><tr><td colspan='2'>Connected Power Lines</td></tr>")
	var powerLineIds = respStr[10];
	var powerLineIdsArray = [];
	if(powerLineIds != "") {
		powerLineIdsArray = powerLineIds.split("~");
	}
	var powerLineLocationLatitude;
	var powerLineLocationLongitude;
	var connectedPowerLineId;
	for(var i = 0; i < powerLineIdsArray.length; i+=2) {
		connectedPowerLineId = powerLineIdsArray[i].split("!")[0];
		powerLineLocationLatitude = powerLineIdsArray[i].split("!")[1].split("^")[0];
		powerLineLocationLongitude = powerLineIdsArray[i].split("!")[1].split("^")[1];
		content = content.concat("<tr><td>Power Line: <a href='#' onclick='zoomToPowerLineSimulation("+connectedPowerLineId+","+powerLineLocationLatitude+","+powerLineLocationLongitude+")' id='connectedPowerLineId_"+connectedPowerLineId+"'>"+connectedPowerLineId+"</a></td>");
		if(typeof powerLineIdsArray[i+1] != 'undefined') {
			connectedPowerLineId = powerLineIdsArray[i+1].split("!")[0];
			powerLineLocationLatitude = powerLineIdsArray[i+1].split("!")[1].split("^")[0];
			powerLineLocationLongitude = powerLineIdsArray[i+1].split("!")[1].split("^")[1];
			content = content.concat("<td>Power Line: <a href='#' onclick='zoomToPowerLineSimulation("+connectedPowerLineId+","+powerLineLocationLatitude+","+powerLineLocationLongitude+")' id='connectedPowerLineId_"+connectedPowerLineId+"'>"+connectedPowerLineId+"</a></td></tr>");
		} else {
			content = content.concat("<td>&nbsp;</td></tr>");
		}
	}
	content = content.concat("</div>");
	return content;
}

function zoomToPowerLineSimulation(powerLineId, powerLineLocationLatitude, powerLineLocationLongitude) {
	var location = new google.maps.LatLng(powerLineLocationLatitude, powerLineLocationLongitude);
	var dataAttributes= {
			powerLineId : powerLineId,
		}
	var options= {
			position:location,
			powerLineId : powerLineId
	}
	ajaxRequest("getPowerLineInfo", dataAttributes, getPowerLineInfoCallBackSimulation, options);		
	map1.setCenter(location);
	map1.setZoom(18);
	
}

