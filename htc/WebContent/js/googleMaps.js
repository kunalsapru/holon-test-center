$(document)
		.ready(
				function() {
					// call initialize function
					initialize();
					// Global Variables for distance calculation
					var dPointA;// =new google.maps.LatLng(49.863915, 8.555046);
					var dPointB;// =new google.maps.LatLng(49.861304, 8.554177);
					var dist;
					var distMarkers = [];
					var infowinDist;
					var ro;
					// Global Variables for distance calculation
					var drawingManager;
					var switchMarker;
					var switchMarkerImage = {
						url : 'css/images/switch-on.png',
						size : new google.maps.Size(300, 300),
						origin : new google.maps.Point(0, 0),
						anchor : new google.maps.Point(0, 32)
					};
					var startPowerLine;
					var next = 0;
					var infowindow = "";
					// Array of Markers and Infowindow
					var infowindowArray = [];
					var markerslat = [];
					var markerslng = [];
					// Variable to get the old content from the info window
					var contentInfoWindow = "";
					var clickedToAddElements = "";
					// Div to add details about the new elements inserted
					$("#elementInfo").hide();
					$("#displayHolonDetails").hide();
					$("#close").click(function() {
						$(this).parent().fadeOut("slow", function(c) {
						});
					});

					$("#addSwitch").on(
							'click',
							function() {
								google.maps.event.addListener(map, 'click',
										function(event) {
											addSwitchMarker(event.latLng);
										});
							});

					function addSwitchMarker(pos) {
						var marker = new google.maps.Marker({
							position : pos,
							draggable : false,
							icon : switchMarkerImage,
							map : map,
							id : 'switchOn'
						});
						map.setCenter(marker.getPosition())
					}

					$(document)
							.on(
									"click",
									"#moreInfo",
									function() {
										var clicked = $("#moreInfo").attr(
												"value");
										var holonDetail = infowindowArray[clicked]
												.split("@");
										if (infowindow) {
											infowindow.close();
										}
										var data = "";
										var dataForInfoWindow = "";
										for (var k = 0; k < holonDetail.length - 1; k++) {
											var individualElementDetail = holonDetail[k]
													.split("#");
											for (var l = 0; l < individualElementDetail.length; l++) {
												data = data
														+ "</br>"
														+ individualElementDetail[l]
											}
											var content = "<div data-role='collapsible' id='displayHolonDetails"
													+ k
													+ "'><h3>Holon Element "
													+ (k + 1)
													+ "</h3><p>"
													+ data + "</p></div>";
											$("#displayHolonDetails").show();
											$("#displayHolonDetails").append(
													content).collapsibleset(
													'refresh');
											data = "";
										}

									});

					// Holon object Button click event

					/*
					 * $("#addHolonFactory").click(function(){
					 * $("#displayHolonDetails").hide();
					 * 
					 * var dataAttr1 = "Ein"; var dataAttr2 = "Zwei"; var
					 * dataAttr3 = "Drei"; var dataAttr4 = "Vier"; var
					 * dataAttributes={dataAttr1:dataAttr1,dataAttr2:dataAttr2,dataAttr3:dataAttr3,dataAttr4:dataAttr4};
					 * 
					 * ajaxRequest("createHolonsFromFactory", dataAttributes,
					 * callBackCreateHolons, {}); });
					 * 
					 * function callBackCreateHolons(dataFromFactory, options){
					 *  // alert("TEST --"+dataFromFactory); var
					 * long=[8.555232882499695,8.555393815040588,8.554060757160187,8.55423241853714,8.555128276348114,8.555310666561127];
					 * var
					 * lat=[49.86342249762294,49.86356427407353,49.863567732030546,49.86368184447307,49.86372506881273,49.863795956646065];
					 * var data= dataFromFactory.split("##"); for(var i=0;i<2;) {
					 * var rectangle1 = new google.maps.Rectangle({ map: map,
					 * bounds: new google.maps.LatLngBounds( new
					 * google.maps.LatLng(lat[i], long[i]), new
					 * google.maps.LatLng(lat[i+1], long[i+1])) });
					 * 
					 * i=i+2;
					 *  } for(var i=2;i<4;) { var rectangle2 = new
					 * google.maps.Rectangle({ map: map, bounds: new
					 * google.maps.LatLngBounds( new google.maps.LatLng(lat[i],
					 * long[i]), new google.maps.LatLng(lat[i+1], long[i+1]))
					 * });
					 * 
					 * i=i+2;
					 *  } for(var i=4;i<6;) { var rectangle3 = new
					 * google.maps.Rectangle({ map: map, bounds: new
					 * google.maps.LatLngBounds( new google.maps.LatLng(lat[i],
					 * long[i]), new google.maps.LatLng(lat[i+1], long[i+1]))
					 * });
					 * 
					 * i=i+2;
					 *  } var infowindowrect1;
					 * 
					 * google.maps.event.addListener(rectangle1, 'click',
					 * function(){ if(infowindowrect1)
					 * {infowindowrect1.close();} infowindowrect1 = new
					 * google.maps.InfoWindow({ content: data[0], position: new
					 * google.maps.LatLng(lat[1], long[1]) });
					 * infowindowrect1.open(map,rectangle1); });
					 * google.maps.event.addListener(rectangle2, 'click',
					 * function(){ if(infowindowrect1)
					 * {infowindowrect1.close();} infowindowrect1 = new
					 * google.maps.InfoWindow({ content: data[1], position: new
					 * google.maps.LatLng(lat[3], long[3]) });
					 * infowindowrect1.open(map,rectangle2); });
					 * google.maps.event.addListener(rectangle3, 'click',
					 * function(){ if(infowindowrect1)
					 * {infowindowrect1.close();} infowindowrect1 = new
					 * google.maps.InfoWindow({ content: data[2], position: new
					 * google.maps.LatLng(lat[5], long[5]) });
					 * infowindowrect1.open(map,rectangle3); });
					 *  }
					 */

					$("#clear").click(function() {
						// location.reload();
						$("#displayHolonDetails").hide();
						// $("#nav").hide();
						initialize();
					});
					// Map click event
					google.maps.event.addListener(map, 'click',
							function(event) {
								// $("#elementInfo").toggle("slide",{direction:"right"});
							});

					$("#addPowerLine").click(function() {
						if (next == 1) {
							var start = startPowerLine[0];
							var end = startPowerLine[1];
						}
						next = 0;
						// cursorCrossHair();

						/*
						 * alert(startPowerLine); alert(endPowerLine);
						 */
						drawPowerLine(start, end)
					});

					/*
					 * google.maps.event.addListener(map, 'click',
					 * function(event) { startPowerLine[next]= event.latLng;
					 * alert(startPowerLine[next]); next++;
					 * 
					 * });
					 */

					$("#calcDistance")
							.on(
									'click',
									function() {

										if (dPointA == undefined
												&& dPointB == undefined) {

											// alert("Please select two points
											// on the Map to calculate the
											// distance.")
											swal({
												title : "Which are the Points?",
												text : "Please select two points on the Map to calculate the distance.",
												type : "error",
												confirmButtonText : "Sure!"
											});
										} else if ((dPointA != undefined && dPointB == undefined)
												|| (dPointA == undefined && dPointB != undefined)) {
											swal({
												title : "Which is the second point?",
												text : "Please select second point on the Map to calculate the distance.",
												type : "error",
												confirmButtonText : "Sure!"
											});
										} else {
											// alert("abhinav1");
											dist = google.maps.geometry.spherical
													.computeDistanceBetween(
															dPointA, dPointB);
											infowinDist= new google.maps.InfoWindow({
													content : "The distance between selected points is "
															+ dist + " meters."
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

					google.maps.event.addListener(map, 'click',
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

					function placeMarker(location) {
						// alert("AbhinavMark Place"+ location);
						var clickedLocation = new google.maps.LatLng(location);
						var marker = new google.maps.Marker({
							position : location,
							map : map
						});
						distMarkers.push(marker);
					}

					function cursorCrossHair() {
						$("#googleMap").css('cursor', 'pointer');
					}
				});

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

function drawPowerLine(start, end, infowindow) {

	var request = {
		origin : start,
		destination : end,
		travelMode : google.maps.TravelMode.DRIVING,
	};
	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);

		}

	});

}

function initialize() {
	mapProp = {
		center : new google.maps.LatLng(49.863393, 8.554789),
		zoom : 19,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
	directionsService = new google.maps.DirectionsService();
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map);

}
