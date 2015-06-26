$(document)
		.ready(
				function() {
					// call initialize function
					initialize();
					var drawingManager;
					var switchMarker;
					var switchMarkerImage = {
						url : 'css/images/on.png',
						size : new google.maps.Size(300, 300),
						origin : new google.maps.Point(0, 0),
						anchor : new google.maps.Point(0, 32)
					};
					var clickedValuePanel;
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
					$("#displayHolonDetails").hide();
					$("#holonObjectDetail").hide();
					$("#holonCoordinatorInformation").hide();
					$("#elementInfo").hide();
					$(document).on("click","#delete",function(){
						alert("ready");
					});
					$("#holonObjectTypeforMasterTables").click(function(){
						getHolonObjectTypesFromDatabase();});
					$("#switch").on('click',function(){
						addNewSwitch=true;
						//console.log($(this).find("a").attr("id"));
					});
					$("#saveHolonObject").click(function(event){

						//START KUNAL CODE>>>DONT EDIT/REMOVE IT
						var holonObjectPriority=$("#holonObjectPriority").val();
						var holonObjectType=$("#holonObjectType option:selected").val();
						var holonCoordinatorId=$("#holonCoordinatorId option:selected").val();
						var holonManager=$("#holonManagerName").val();
						var latNE=$("#holonObjectLatitudeNE").text();
						var lngNE=$("#holonObjectLongitudeNE").text();
						var latSW=$("#holonObjectLatitudeSW").text();
						var lngSW=$("#holonObjectLongitudeSW").text();
						var holonObjectActionState = $("#holonObjectActionState").val();
						var hiddenHolonObjectId = $("#hiddenHolonObjectId").val();
						var hiddenHolonManagerId = $("#hiddenHolonManagerId").val();
						$( "#holonObjectDetail" ).popup( "close" );
						
						var dataAttributes = {
								holonObjectType : holonObjectType,
								holonCoordinatorId : holonCoordinatorId,
								holonManager : holonManager,
								latNE : latNE,
								lngNE : lngNE,
								latSW : latSW,
								lngSW : lngSW,
								holonObjectPriority : holonObjectPriority,
								hiddenHolonObjectId : hiddenHolonObjectId,
								hiddenHolonManagerId : hiddenHolonManagerId
							};
						if(holonObjectActionState == "Edit"){
							ajaxRequest("editHolonObject", dataAttributes, editHolonObjectCallBack, {});
						} else {
							ajaxRequest("createHolonObject", dataAttributes, createHolonObjectCallBack, {});							
						}

						//END KUNAL CODE>>>DONT EDIT/REMOVE IT
					});
					
					
					/*$("#addHolonCoordinator").click(function(){
						$("#holonCoordinatorInformation").show();
						$("#holonCoordinatorInformation").popup();
						$("#holonCoordinatorInformation").popup("open");
					});*/
					
					$('#addHolonObject').click(function(){
						clickedValuePanel=this.id;
						overlayTool(this.id);
					});
					
					$("#addHolonCoordinator").click(function(){
						clickedValuePanel=this.id;
						overlayTool(this.id);
					});
					$("#edit").click(function(){
						alert("Hellooo");
					});
					
					$(document).on('click', '#edit', function(){ 
					     alert();
					 });
					$("#showHolonObjects").click(showHolonObjects);
					
					function overlayTool(clickedValue)
					{
						//alert($("#"+clickedValuePanel).css("background-color"));
						if ($("#"+clickedValuePanel).css("background-color") == "rgb(233, 233, 233)") {
							$("#"+clickedValuePanel).css("background-color", "rgb(153,255,255)");
					
						$("#displayHolonDetails").hide();

					//	To check If the layout is already present ;
						
						if (drawingManager== null){
							//Creates a new drawing manager object for first time
							var rectangleColor="black";
							if(clickedValuePanel=="addHolonCoordinator"){
								rectangleColor="red";
							}
							if(clickedValuePanel=="addHolonObject"){
								rectangleColor="black";
							}
						 drawingManager = new google.maps.drawing.DrawingManager({
				    	    drawingMode: google.maps.drawing.OverlayType.POLYGON,
				    	    drawingControl: true,
				    	    drawingControlOptions: {
				    	      position: google.maps.ControlPosition.TOP_CENTER,
				    	      drawingModes: [
				    	   		 google.maps.drawing.OverlayType.RECTANGLE,
				    	   		google.maps.drawing.OverlayType.POLYLINE
				   	   		 
				    	      ]
				    	    },
				            rectangleOptions: {
				                geodesic:true,
				                clickable: true,
				                strokeColor:rectangleColor
				            },
				            polylineOptions: {
				                geodesic:true,
				                clickable: true,
				                strokeColor:"yellow",
				                strokeOpacity: 2.0,
				                strokeWeight: 4.0
				            }
				    	    });}
				     // Setting the layout on the map 
				      drawingManager.setMap(map);
				     // Event when the overlay is complete 
				      google.maps.event.addListener(drawingManager, 'overlaycomplete', function(event) {
				    	  var newShape = event.overlay; // Object
				    	  newShape.type = event.type;	// Rectangle
				    	 // alert(newShape.getBounds());
				    	  var latNorthEast = newShape.getBounds().getNorthEast().lat(); //get lat of northeast
				    	  var lngNorthEast = newShape.getBounds().getNorthEast().lng();	//get longitude of north east
						//START KUNAL CODE>>>DONT EDIT/REMOVE IT
				    	  var latSouthWest = newShape.getBounds().getSouthWest().lat();
				    	  var lngSouthWest = newShape.getBounds().getSouthWest().lng();

				    	//END KUNAL CODE>>>DONT EDIT/REMOVE IT
				    	  markerslat.push(latNorthEast); //add data to array
				    	  markerslng.push(lngNorthEast); //add data to array
						//START KUNAL CODE>>>DONT EDIT/REMOVE IT

				    	  $("#holonObjectLatitudeNE").text(latNorthEast);
				    	  $("#holonObjectLongitudeNE").text(lngNorthEast);
				    	  $("#holonObjectLatitudeSW").text(latSouthWest);
				    	  $("#holonObjectLongitudeSW").text(lngSouthWest);

				    	  //END KUNAL CODE>>>DONT EDIT/REMOVE IT

				    	  infowindowArray.push("");
				    	  if(clickedValuePanel=="addHolonObject"){
				    		  $("#holonObjectDetail").show();
					    	  $("#holonObjectDetail").popup();
					    	  $("#holonObjectDetail").popup("open");
				    	  }
				    	  else
				    		  {
				    		  $("#holonCoordinatorInformation").show();
								$("#holonCoordinatorInformation").popup();
								$("#holonCoordinatorInformation").popup("open");
				    		  }
				      
				    	  //When Rectangle is clicked
				    	  google.maps.event.addListener(newShape, 'click', function() {
				    		  $("#displayHolonDetails").empty();
				    	 
				    	//to check this event for each newshape clicked
				    		 var latValueofRectangle = newShape.getBounds().getNorthEast().lat();
				    		for(var i=0;i<markerslat.length;i++)
				    		if(latValueofRectangle==markerslat[i])	//Traverse array for the clicked rectangle
				    		{
				    			//No element Added
				    			var clicked = i;
				    			if(infowindowArray[i]=="" ||infowindowArray[i]== undefined || infowindowArray[i]== null )
				    				{
				    					//alert("No element for this Holon Object");
				    				}
				    			else{
				    				
				    				//Data exist in the array
				    				//Code to show data in infoWindow 
				    				if(infowindow)
									{infowindow.close();}
				    				//Content for the info window separated by : then ,
				    				var holonDetail=infowindowArray[i].split("@");
				    				var data="";
				    				var dataForInfoWindow="";
				    				for(var k=0;k<holonDetail.length-1;k++)
				    					{
				    						var individualElementDetail=holonDetail[k].split("#");
				    						for(var l=0;l<individualElementDetail.length;l++)
				    							{
				    								if(k==0){
				    									dataForInfoWindow = dataForInfoWindow+"</br>"+individualElementDetail[l];
				    								}
				    							    
				    								data= data+"</br>"+individualElementDetail[l];
				    							}
				    					/*var content=	"<div data-role='collapsible' id='displayHolonDetails" + k + "'><h3>Holon Element " + (k+1) + "</h3><p>"+data+"</p></div>";
				    						$("#displayHolonDetails").append( content ).collapsibleset('refresh');*/
				    						data="";
				    					}
				    				if(k>1)
				    					{
				    					dataForInfoWindow=dataForInfoWindow+"</br></br>"+"<a href='#' id='moreInfo' value="+i+">More Information</a>";
				    					}
				    				
				    				infowindow = new google.maps.InfoWindow({
				    				      content: dataForInfoWindow,
				    				      position: newShape.getBounds().getNorthEast()
				    				  });
				    				
				    				infowindow.open(map,newShape);
				    			}
				    		}
				    	 
				    	  });
				    	 
				  	});
				     //END of overlay Complete 
					  // Select the data in the info window
					  $("#saveElementInfo").click(function(){
						  //$('#dropDownId :selected').text();
						  var textvalue= "<b>Element Type-</b>"+$("#elementType").find('option:selected').text();
						  $(".elementType").change(function() {
							        alert($('.elementType option:selected').html());
							    });
						 
						 var elementState= "<b>Element State-</b>"+"overSupply";
						 $('#elementState').on('change', function() {
							  textvalue="<b>Element State-</b>"+$(this).val(); // or $(this).val()
							});
						 var holonManager="<b>Holon Manager-</b>"+"Manager1";
						 $('#holonManager').on('change', function() {
							  textvalue="<b>Holon Manager-</b>"+$(this).val(); // or $(this).val()
							});
						 var holonCoordinator="<b>Holon Coordinator-</b>"+$("#holonCoordinator").val();
						 var minCapacity="<b>Min Capacity-</b>"+ $("#minCapacity").val();
						 var maxCapacity= "<b>Max. Capacity-</b>"+$("#maxCapacity").val();
						 var usage= "<b>Usage-</b>"+$("#usage").val();
						 var currentCapacity="<b>Current Capacity-</b>"+$("#currentCapacity").val();
						 var currentEnergyStatus="<b>Current Energy Status-</b>"+$("#currentEnergyStatus").val();
						 var result=textvalue+"#"+elementState+"#"+holonManager+"#"+holonCoordinator+"#"+minCapacity+"#"+maxCapacity+"#"+usage+"#"+currentCapacity+"#"+currentEnergyStatus;
						 
						for(var j=0;j<markerslat.length;j++)
							{
								if(clickedToAddElements==markerslat[j])
									{
											//get value of the previous from the array
											var valueFromArray=result+"@"+infowindowArray[j];
											infowindowArray[j]=valueFromArray;
											$("#elementName").html("");
											
									}
							}
						console.log(infowindowArray);
						console.log(infowindowArray.length);
						 //Clear the value and hide the div
						// $("#elementName").val("");
						 $("#minCapacity").val("");
						 $("#maxCapacity").val("");
						 $("#usage").val("");
						 $("#holonCoordinator").val("");
						 $("#currentCapacity").val("");
						 $("#currentEnergyStatus").val("");
						 $("#displayHolonDetails").empty();						  
					  });
					  //End of Save Button
					  
					  
					  
					 
					}
					else
					{
						$("#"+clickedValuePanel).css("background-color", "rgb(233,233,233)");
						drawingManager.setMap(null);
						
					}
					}
					
					$('#addHolonCoordinator').hover(function() {
						$('#addHolonCoordinator').css('cursor','pointer');
							  });
					
					$('#showHolonObjects').hover(function() {
						$('#showHolonObjects').css('cursor','pointer');
							  });
					
					$("#close").click(function(){
						$(this).parent().fadeOut("slow", function(c) {
                        });
					})
					
					// START KUNAL CODE --> CRITICAL SECTION DO NOT ENTER
					function editHolonObjectCallBack(data, options){
						alert(data);
					}
					function createHolonObjectCallBack(data, options) {
						var dataArray = data.split("!");
						var holonObjectId = dataArray[0];
						var holonName= dataArray[1];
						var holonCoordinatorName_Holon= dataArray[2];	
						var holonObjectTypeName= dataArray[3];
						var ne_location= dataArray[4];
						var sw_location= dataArray[5];
						var lineConnectedState= dataArray[6];
						var priority= dataArray[7];
						var holonManagerName= dataArray[8];
						
						var lat=ne_location.split("~");
						
						contentString="<b>Priority: </b>"+priority+"<br>"+
								"<b>Holon Object Id: </b>"+holonObjectId +"<br>"+
								"<b>Holon Name: </b>"+holonName+"<br>"+
								"<b>Holon Manager: </b>"+holonManagerName+"<br>"+
								"<b>North East Location: </b>"+ne_location+"<br>"+
								"<b>South West Location: </b>"+sw_location+"<br><br>"+
								"<input type='button' id='editHolonObject' name='editHolonObject' value='Edit Holon Object' onclick='editHolonObject("+holonObjectId+")'/>&nbsp;&nbsp;"+
								"<input type='button' id='deleteHolonObject' name='deleteHolonObject' value='Delete Holon Object'/>&nbsp;&nbsp;"+
								"<input type='button' id='addHolonElement' name='addHolonElement' value='Add Holon Element' onclick='addHolonElement("+lat[0]+","+lat[1]+")'/>&nbsp;&nbsp;"+
								"<input type='button' id='showHolonElement' name='showHolonElement' value='Show Holon Elements' onclick='showHolonElementsForHolon("+lat[0]+","+lat[1]+")'/>";
						var infowindowHolonObject = new google.maps.InfoWindow({
						      content: contentString,
						      position:new google.maps.LatLng(lat[0],lat[1])
						  });
						infowindowHolonObject.open(map,map);
						//alert(contentString);
					}
					//END KUNAL CODE
					
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

					$("#clear").click(function() {
						swal({
							title: "Are you sure?",
							text: "Do you really want to clear the holon Map?",
							type: "warning",
							showCancelButton: true,
							confirmButtonColor: '#DD6B55',
							confirmButtonText: 'Yes,clear it!',
							cancelButtonText: "No, Don't clear!",
							closeOnConfirm: false,
							closeOnCancel: false
						},
						function(isConfirm){
					    if (isConfirm){
					    	$("#displayHolonDetails").hide();
							initialize();
							swal("Cleared", "Map has been cleared", "info");
					    } else {
					      swal("Cancelled", "Map has not been cleared", "info");
					    }
						});
						
					});
					// Map click event
					google.maps.event.addListener(map, 'click',
							function(event) {
								// $("#elementInfo").toggle("slide",{direction:"right"});
						var switchMarker="";
						if(addNewSwitch==true){
							switchMarker = new google.maps.Marker({
						        position: event.latLng,
						        draggable: false,
						        icon:switchMarkerImage,
						        map: map,
						        id:"switchon"
						    });	
						}
						addNewSwitch=false;
						
						google.maps.event.addListener(switchMarker, 'click', function(event) {
							console.log(switchMarker);
							var currentImage=switchMarker.icon.url;
								switch (currentImage){
								case "css/images/on.png":
									switchMarker.setIcon("css/images/off.png");
									break;	
								}
								
							if(currentImage==undefined)
								{
								currentImage=switchMarker.icon;
								switch (currentImage){
								case "css/images/on.png":
									switchMarker.setIcon("css/images/off.png");
									break;	
								case "css/images/off.png" :
									switchMarker.setIcon("css/images/on.png");
									break;	
								}
								
								}
						});
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
		center : new google.maps.LatLng(49.863772, 8.552666),
		zoom : 19,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
	directionsService = new google.maps.DirectionsService();
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map);

}

function editHolonObject(holonObjectId)
{
/*	if(infowindowHolonObject == ""){} 
	else {
		infowindowHolonObject.close();
	}
*/	$("#holonObjectActionState").val("Edit");
	$("#hiddenHolonObjectId").val(holonObjectId);
	$("#holonObjectDetail").show();	
	$("#holonObjectDetail").popup();
	$("#holonObjectDetail").popup("open");
}

function addHolonElement(holonObjectId){
	$("#elementInfo").show();
	$("#elementInfo").popup();
	$("#elementInfo").popup("open");
}

function showHolonObjects()
{ 
	ajaxRequest("showHolonObjects", {}, showHolonObjectsCallBack, {});
}

function showHolonObjectsCallBack(data, options){
	console.log(data);
	var hoObjectsList = data.split("*");
	console.log(hoObjectsList);
	var rectangles=[];
	for (var i=0; i<hoObjectsList.length-1; i++) {
		var holonObject = hoObjectsList[i].split("!");
		var location = holonObject[0];
		var contentString = holonObject[1];
		var ne_location_lat = location.split("^")[0].split("~")[0].replace("[","").replace(",","");
		var ne_location_lng = location.split("^")[0].split("~")[1];
		var sw_location_lat = location.split("^")[1].split("~")[0];
		var sw_location_lng = location.split("^")[1].split("~")[1];
	    var rectangleFromFactory = new google.maps.Rectangle({
		      map: map,
		      bounds: new google.maps.LatLngBounds(
		    	      new google.maps.LatLng(sw_location_lat, sw_location_lng),
		    	      new google.maps.LatLng(ne_location_lat, ne_location_lng))
		    });
		 attachMessage(contentString, rectangleFromFactory, new google.maps.LatLng(ne_location_lat, sw_location_lng));
	}	
}

function attachMessage(contentString, marker, position) {
	  var infowindow = new google.maps.InfoWindow({
	    content: contentString,
	    position:position
	  });

	  google.maps.event.addListener(marker, 'click', function() {
	    infowindow.open(marker.get('map'), marker);
	  });
}

function showHolonElementsForHolon (holonObjectId){
	$("#displayHolonDetails").show();
	$("#displayHolonDetails").empty();
	var holonElementType=["Fridge","Washing Machine"];
	var holonManager=["HolonManager1","HolonManager4"];
	var maxCapacity=["300W","2500W"];
	var data="";
	for (var k = 0; k < holonElementType.length; k++) {
		
		var individualElementDetail = "<div data-role='collapsible'><h3>Holon Element :"+(k+1)+"</h3><button onclick='delete()' id='delete' class='ui-btn ui-btn-inline ui-icon-delete ui-btn-icon-notext ui-corner-all'>No text</button>" +
				"<button onclick='edit()' class='ui-btn ui-btn-inline ui-icon-edit ui-btn-icon-notext ui-corner-all' id='edit'>No text</button>"+holonElementType[k]+"</div>"
		$("#displayHolonDetails").append(individualElementDetail);
	}
	console.log($("#displayHolonDetailsData"));
    $('#displayHolonDetails').panel({ position: "right"});       
    $('#displayHolonDetails').panel("open");   	
		
	
}

function edit()
{
	alert("Clicked Edit");
}

function getHolonObjectTypesFromDatabase()
{
var types=["Fridge","Washing Machine"];
var data="";
for(var i=0;i<types.length;i++)
	{
		data=data+"<tr><td>"+types[i]+"</td><td><button id='editMasterHolonObjectType'>Edit</button></td><td><button id='deleteMasterHolonObjectType'>Delete</button></td></tr>"
	}
data="<table border='1'><th>Data Type</th><th></th><th></th>"+data+"</table>";
$("#masterTableHolonObjectsTypes").empty();
$("#masterTableHolonObjectsTypes").append("<a href='#' data-rel='back' data-role='button' data-theme='a' data-icon='delete' data-iconpos='notext' class='ui-btn-right'>X</a>"+data);
$("#masterTableHolonObjectsTypes").append("<br><button id='addMasterHolonObjectType' onclick='addHolonObjectTypeInMaster()'>Add</button>");
$("#masterTableHolonObjectsTypes").show();
$("#masterTableHolonObjectsTypes").popup();
$("#masterTableHolonObjectsTypes").popup("open");

}