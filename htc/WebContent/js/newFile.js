/**
 * 
 */

function initialize() {
  var mapProp = {
			center : new google.maps.LatLng(49.863772, 8.552666),
			zoom : 17,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
  var map=new google.maps.Map(document.getElementById("googleMap"), mapProp);
	directionsService = new google.maps.DirectionsService();
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map);
}
google.maps.event.addDomListener(window, 'load', initialize);