$(document).ready(function(){
	var map1;
	initializeMap();
	openDiv('simulationDiv');
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
		center : new google.maps.LatLng(49.86116093408584,8.66295576095581),
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