/**
 * This JS is used to calculate distance between two points
 */

/**
 * This JS file contains code related to drawing a power line on the map.
 * 
 * 
 * 
 */
$(document)
		.ready(
				function() {
					// Global Variables for distance calculation
					var dPointA;// =new google.maps.LatLng(49.863915, 8.555046);
					var dPointB;// =new google.maps.LatLng(49.861304, 8.554177);
					var dist;
					var distMarkers = [];
					var infowinDist;
					var distListener;
					var ro;
					// Global Variables for distance calculation


					$("#calcDistance").on('click',function() {
						
						if ($(this).css("background-color") == "rgb(237, 237, 237)") {
							$(this).css("background-color", "rgb(153,255,255)");
							swal({
								title : "Select Points",
								text : "Please select two points on the Map and click on the button again to calculate the distance.",
								type : "info",
								confirmButtonText : "Sure!"
							});

							distListener=google.maps.event.addListener(map, 'click',
									function(event) {

										if (dPointA == undefined) {
											// alert("AbhinavMark A "+event.latLng);
											dPointA = event.latLng;
											placeMarker(event.latLng);
										} else {
											// alert("AbhinavMark B"+event.latLng);
											dPointB = event.latLng;
											placeMarker(event.latLng);

										}
									});

						}
						else
							{
							
							if (dPointA == undefined && dPointB == undefined) {

								// alert("Please select two points
								// on the Map to calculate the
								// distance.")
								swal({
									title : "Which are the Points?",
									text : "Please select two points on the Map before calculating the distance.",
									type : "error",
									confirmButtonText : "Sure!"
								});
							} else if ((dPointA != undefined && dPointB == undefined)
									|| (dPointA == undefined && dPointB != undefined)) {
								swal({
									title : "Which is the second point?",
									text : "Please select second point on the Map before calculating the distance.",
									type : "error",
									confirmButtonText : "Sure!"
								});
							} else {
								// alert("abhinav1");
								dist = google.maps.geometry.spherical
										.computeDistanceBetween(
												dPointA, dPointB);
								infowinDist = new google.maps.InfoWindow(
										{
											content : "The distance between selected points is "
													+ dist
													+ " meters."
										});
								drawRoute(dPointA, dPointB,
										infowinDist, map);
								// alert("The distance between
								// selected points is "+dist+"m.");
								swal({
									title : "Distance",
									text : "The distance between selected points is "
											+ dist + " meters.",
									type : "success",
									confirmButtonText : "Nice!"
								});
								dPointA = undefined;
								dPointB = undefined;
								deleteMarkers();
							}
							
							google.maps.event.removeListener(distListener);
							$(this).css("background-color", "rgb(237,237,237)");
							}

									});
					
					function clearMarkers() {
						setAllMap(null);
					}

					function deleteMarkers() {
						clearMarkers();
						distMarkers = [];
					}
					
					function setAllMap(map) {
						for (var i = 0; i < distMarkers.length; i++) {
							distMarkers[i].setMap(map);
						}
					}


					function placeMarker(location) {
						// alert("AbhinavMark Place"+ location);
						var clickedLocation = new google.maps.LatLng(location);
						var marker = new google.maps.Marker({
							position : location,
							map : map
						});
						distMarkers.push(marker);
					}
					
					function drawRoute(start, end, infowindow, map) {
						//alert("infowin "+infowindow+" "+"map "+map+" this "+this)

						var request = {
							origin : start,
							destination : end,
							travelMode : google.maps.TravelMode.DRIVING,
							
						};
						ro = directionsService.route(request, function(response, status) {
							if (status == google.maps.DirectionsStatus.OK) {
								directionsDisplay.setDirections(response);
							}
							
							response= {
									
									click:infowindow.open(map,ro)
							};
							
							

						});
						
					}

				})