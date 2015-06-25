$(document)
		.ready(
				function() {
					// call initialize function
					initialize();
					var switchMarker;
					var switchMarkerImage = {
						url : 'css/images/switch-on.png',
						size : new google.maps.Size(300, 300),
						origin : new google.maps.Point(0, 0),
						anchor : new google.maps.Point(0, 32)
					};
					var startPowerLine;
					var next = 0;
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
					
					
					
					function cursorCrossHair() {
						$("#googleMap").css('cursor', 'pointer');
					}
				});


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
