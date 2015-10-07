$(document).ready(function(){
	window.globalPowerLineListSimulation = new Map(); //Power Line Map
	window.globalPowerSwitchList = new Map(); //Power Switch Map
	window.globalHKListSimulation = new Map(); //Holon Coordinator Map
	window.globalHoListSimulation = new Map(); //Holon Object Map 
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

function drawSwitchesSimulation() {
	var contentString = "<tr><td>Creating Switches:</td><td>Creating switches in Database!</td>";
	$("#simulationDivTable").append(contentString);
	ajaxRequest("drawSwitchesSimulation", {}, drawSwitchesSimulationCallBack, {});
}

function drawSwitchesSimulationCallBack(data, options) {
	var contentString = "<tr><td>Drawing Switches:</td><td>Drawing switches on map!</td>";
	$("#simulationDivTable").append(contentString);
	var powerSwitchList= data.split("*");
	for(var i=0;i<powerSwitchList.length-1;i++) {
		var individualData = powerSwitchList[i].split("^");
		var switchLat = individualData[0].replace("[","").replace(",","");
		var switchLong = individualData[1];
		var powerSwitchId = individualData[2].trim();
		var status = individualData[3];
		var switchStatus = "#FF0000";		
		if(status == 1) {
			switchStatus="#0B6121";
		}
		var circleSwitch = new google.maps.Circle({
			 strokeColor: switchStatus,
		     strokeOpacity: 1,
		     strokeWeight: 8,
		     fillColor: switchStatus,
		     fillOpacity: 0.35,
		     map: map1,
		     center: new google.maps.LatLng(switchLat, switchLong),
		     radius: 2
		    });
		addSwitchInfoSimulation(circleSwitch, powerSwitchId);
		globalPowerSwitchList.set(powerSwitchId,circleSwitch);
	}
}

function addSwitchInfoSimulation(circleSwitch, powerSwitchId) {
	console.log(circleSwitch);
	console.log(powerSwitchId);
	
   google.maps.event.addListener(circleSwitch, 'click', function(event) {
		var dataAttributes= {
				powerSwitchId : powerSwitchId,
				}
		var options= {
				position:event.latLng,
				circleSwitch:circleSwitch,
				}
		ajaxRequest("getSwitchInfo", dataAttributes, getSwitchInfoCallBackSimulation, options);		   
	   
   });
}

function getSwitchInfoCallBackSimulation(data, options) {
	closeOtherInfoWindowsSimulation();
	var individualData = data.split("^");
	var switchLat = individualData[0].replace("[","").replace(",","");
	var switchLong = individualData[1];
	var powerSwitchId = individualData[2].trim();
	var powerLineAId = individualData[3];
	var powerLineBId = individualData[4];
	var status = individualData[5].trim();
	var switchStatus = "Off";
	var btnText = "Switch On";
	if(status == "1") {
		switchStatus = "On";
		btnText = "Switch Off";
	}
	var contentString= "<div id='contentId' class='table'><table>"+
		"<tr><td colspan = '2' style='text-decoration: underline;'>Switch Details</td></tr>" +
		"<tr><td><b>Switch Id: </b>"+powerSwitchId +"</td>"+
		"<td><b>Switch Status: </b>"+switchStatus+"</td></tr>"+
		"<tr><td><b>Connected Power Line A: </b>"+powerLineAId+"</td>"+
		"<td><b>Connected Power Line B : </b>"+powerLineBId+"</td></tr>"+
		"<tr><td colspan='2' style='text-align: center;'>" +
		"<span class = 'button' id='togglePowerSwitch'><i class='fa fa-circle-o-notch'></i>&nbsp;&nbsp;Turn "+btnText+"</span></td></tr></table></div>";
	var position = options["position"];
	var circleSwitch = options["circleSwitch"];
	var infowindowPowerSwitch = new google.maps.InfoWindow({
	      content: contentString		    
	  });
	infowindowPowerSwitch.setOptions({position:position});
	infowindowPowerSwitch.open(map1,circleSwitch);	
	$('#togglePowerSwitch').click(function() {
		switchOnOffSimulation(circleSwitch,powerSwitchId,infowindowPowerSwitch);			
	})
	currentSwitchInfoWindowSimulation = infowindowPowerSwitch;
}

function switchOnOffSimulation(circleSwitch,powerSwitchId,infowindowPowerSwitch) {
		var dataAttributes = {    			
    			powerSwitchId:powerSwitchId,
    			};	
		options = {
				circleSwitch:circleSwitch,
				infowindowPowerSwitch:infowindowPowerSwitch,
				powerSwitchId:powerSwitchId,
    			};
	
		ajaxRequest("powerSwitchOnOff", dataAttributes, powerSwitchOnOffCallBackSimulation,options);
		globalPowerSwitchList.set(powerSwitchId,circleSwitch);
}

function powerSwitchOnOffCallBackSimulation(data,options){
	var circleSwitch = options["circleSwitch"];
	var infowindowPowerSwitch = options["infowindowPowerSwitch"];
	var powerSwitchId = options["powerSwitchId"];
	var content = infowindowPowerSwitch.getContent();
	var newSwitchStatus = data.split("*")[0];
	var newCoordinatorIds = undefined;
	if(data.split("*")[1] != undefined) {
		newCoordinatorIds = data.split("*")[1].split("!");
	}
	var oldCoordinatorIds = undefined;
	if(data.split("*")[2] != undefined) {
		oldCoordinatorIds=data.split("*")[2].split("!");
	}
	if(newSwitchStatus== 1) {
		circleSwitch.setOptions({strokeColor:'#0B6121',fillColor: '#0B6121'});
		var newContent=content.replace("<b>Switch Status: </b>Off","<b>Switch Status: </b>On").replace("Switch On","Switch Off");
		infowindowPowerSwitch.setContent(newContent);
		infowindowPowerSwitch.close();
	} else {
		circleSwitch.setOptions({strokeColor:'#FF0000', fillColor: '#FF0000'});
		var newContent=content.replace("<b>Switch Status: </b>On","<b>Switch Status: </b>Off").replace("Switch Off","Switch On");
		infowindowPowerSwitch.setContent(newContent);
		infowindowPowerSwitch.close();		
	}
	if(oldCoordinatorIds != undefined ){
		for(var i=0;i< oldCoordinatorIds.length-1; i++){
			var oldCoordinatorId= oldCoordinatorIds[i];
			removeIconFromMapSimulation(oldCoordinatorId);
		}
	}
	
	if (newCoordinatorIds != undefined){
		for(var i=0;i< newCoordinatorIds.length-1;i++){
			var newCoordinatorId= newCoordinatorIds[i];
			createIconOnMapSimulation(newCoordinatorId);
		}
	}
	infowindowPowerSwitch.open(map1,circleSwitch);
	$('#togglePowerSwitch').click(function() {
		switchOnOffSimulation(circleSwitch,powerSwitchId,infowindowPowerSwitch);			
	});
}

function removeIconFromMapSimulation(objectId) {
	var holonObjectMarkerIcon = globalHKListSimulation.get(objectId);
	console.log(holonObjectMarkerIcon.icon);
	var holonObject= globalHoListSimulation.get(objectId);
	if(holonObjectMarkerIcon.icon="../css/images/coordinator.png"){
		holonObjectMarkerIcon.setVisible(false);
	}
	holonObjectMarkerIcon.setIcon("../css/images/no_coordinator.png");
	holonObjectMarkerIcon.setTitle("No Coordinator");
}

function createIconOnMapSimulation(objectId) {
	var holonObject= globalHoListSimulation.get(objectId);
	var holonObjectPosition= holonObject.getBounds().getNorthEast();
	if(globalHKListSimulation.get(objectId)== null) {
		var coordinatorIcon= new Marker({
			map: map1,
			title: 'Holon Coordinator',
			position: holonObjectPosition,
			zIndex: 9,
			icon: {
				path: ROUTE,
				fillColor: '#0E77E9',
				fillOpacity: 0,
				strokeColor: '',
				strokeWeight: 0,
				scale: 1/100
			},
			icon : '../css/images/coordinator.png'
		});
		globalHKListSimulation.set(objectId,coordinatorIcon);
	}
	if(globalHKListSimulation.get(objectId).visible) {
		//Do nothing
	} else {
		globalHKListSimulation.get(objectId).visible=true;
		globalHKListSimulation.get(objectId).setVisible(true);
		globalHKListSimulation.get(objectId).setIcon("../css/images/coordinator.png");
		globalHKListSimulation.get(objectId).setTitle("Holon Coordinator");
	}
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
	drawSwitchesSimulation();
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

