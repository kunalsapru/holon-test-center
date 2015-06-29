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
					$("#addMasterHolonElementTypeDetail").hide();
					$("#addMasterHolonDetail").hide();
					$(document).on("click","#delete",function(){
						alert("ready");
					});
					$("#holonObjectTypeforMasterTables").click(function(){
						getHolonObjectTypesFromDatabase();});
					$("#switch").on('click',function(){
						addNewSwitch=true;
						//console.log($(this).find("a").attr("id"));
					});
					$("#addMasterHolonObjDetail").hide(); 
					$("#masterTableHolonObjectsTypes").hide();
					
					$("#holonforMasterTables").click(function(){
						openHolonforMasterTables();
					});
					
					$("#buttonHolonMaster").click(function(){
						var textHolonMaster= $("#textHolonMaster").val();
						var dataAttributes = {
								name:textHolonMaster
						}
						
						$("#addMasterHolonDetail").popup("close");
						ajaxRequest("addHolon", dataAttributes, addHolonCallBack, {});
					});
					
					$("#holonElementTypeforMasterTables").click(function(){
						openHolonElementTypeforMasterTables();
					
					});
					
					$("#buttonHolonElementTypeMaster").click(function(){
						var textHolonElementTypeMaster= $("#textHolonElementTypeMaster").val();
						var dataAttributes= {
								holonElementTypeName : textHolonElementTypeMaster
						}
						
						$("#addMasterHolonElementTypeDetail").popup("close");
						ajaxRequest("createHolonElementType", dataAttributes, createHolonElementTypeCallBack, {});
					});
					
					$("#saveHolonObject").click(function(event){
						saveHolonObject();
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
					
					
					// Callback for add a holonName 
					function addHolonCallBack(data, options)
					{
						alert("Holon Name added succesfully");
					}
					
					// Callback for add a holonElemnt Type
					function createHolonElementTypeCallBack(data,options)
					{
						alert("Holon Element Type added succesfully");
					}
										
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
	//Code to retrieve list from database and add in select
	getListOfAllHolonElementForElementInfo()
	$("#elementInfo").show();
	$("#elementInfo").popup();
	$("#elementInfo").popup("open");
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

function getHolonObjectTypesFromDatabase() {
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

function openHolonforMasterTables() {
	$("#addMasterHolonDetail").show();
	$("#addMasterHolonDetail").popup();
	$("#addMasterHolonDetail").popup("open");
}

function openHolonElementTypeforMasterTables() {
	$("#addMasterHolonElementTypeDetail").show();
	$("#addMasterHolonElementTypeDetail").popup();
	$("#addMasterHolonElementTypeDetail").popup("open");
}

function getListOfAllHolonElementForElementInfo() {
	ajaxRequest("getListHolonElementType", {}, showListHolonElementTypeCallBack, {});
}

function showListHolonElementTypeCallBack(data,options) {
	var listHolonElementTypeMaster= data.split("*");
	$("#selectForHolonElementType").empty();
	for(var i=0;i<listHolonElementTypeMaster.length-1;i++)
		{
		var options= "<option value="+listHolonElementTypeMaster[i]+"id="+listHolonElementTypeMaster[i].split("-")[0]+">"+listHolonElementTypeMaster[i]+"</option>";
		$("#selectForHolonElementType").append(options);
		}
	$("#selectForHolonElementType").selectmenu('refresh', true);
}