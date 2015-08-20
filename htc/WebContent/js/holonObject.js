/**
 * Java Script file for functions related to holon objects
 */

var holonEditOptions = {};
var isConnectedFirst="";
var isConnectedSecond="";
var hoShape;

$(document).ready(function() {

	var holonDrawingManager;
	
	$('#areConnected').click(function(evt) {
		if (areConnectedMode==false){
			areConnectedMode=true;
			$(this).css("background-color", "rgb(153,153,0)");
		} else {
			$(this).css("background-color", "rgb(26, 26, 26)");
			areConnectedMode=false;
			isConnectedFirst="";
			isConnectedSecond="";
		}
	});
	
	$('#addHolonObject').click(function() {
	if (drawHolonObjectMode==false) {
			$(this).css("background-color", "rgb(153,153,0)");
			drawHolonObjectMode=true;
			//Creates a new drawing manager object for first time
			holonDrawingManager = new google.maps.drawing.DrawingManager({
    	    drawingMode: google.maps.drawing.OverlayType.RECTANGLE,
    	    drawingControl: false,
    	    rectangleOptions: {
                geodesic:true,
                clickable: true,
                strokeColor:"black"
            }
    	    });
     // Setting the layout on the map 
			holonDrawingManager.setMap(map);
     // Event when the overlay is complete 
      google.maps.event.addListener(holonDrawingManager, 'overlaycomplete', function(event) {
    	  hoShape = event.overlay; // Object
    	  hoShape.type = event.type;	// Rectangle
    	  createdHolonObject=hoShape;
    	  var latNorthEast = hoShape.getBounds().getNorthEast().lat(); //get lat of northeast
    	  var lngNorthEast = hoShape.getBounds().getNorthEast().lng();	//get longitude of north east
    	  var latSouthWest = hoShape.getBounds().getSouthWest().lat();
    	  var lngSouthWest = hoShape.getBounds().getSouthWest().lng();
    	  $("#holonObjectLatitudeNE").text(latNorthEast);
    	  $("#holonObjectLongitudeNE").text(lngNorthEast);
    	  $("#holonObjectLatitudeSW").text(latSouthWest);
    	  $("#holonObjectLongitudeSW").text(lngSouthWest);
    	  $("#hoObjTitle").text("Add Holon Object");
    	  $("#holonObjectActionState").val("Add");
    	  getHolonObjectTypeFromDatabase();
    	  getHolonCoordinatorFromDatabase("","holonCoordinatorId","holonObjectDetail");
    	 
  	});
     //END of overlay Complete 
	}
	else
	{
		$(this).css("background-color", "rgb(26, 26, 26)");
		drawHolonObjectMode=false;
		holonDrawingManager.setMap(null);
		
	}
	
	})

	$("#showHolonObjects").click(showHolonObjects);
	
	$("#saveHolonObject").click(function(event){
		saveHolonObject();						
	});
	
	$("#cancelHolonObject").click(function(event){
		if(createdHolonObject!=null && typeof createdHolonObject != 'undefined')
			{
			createdHolonObject.setMap(null);
			}
		closeDiv("holonObjectDetail");
	});	
	
	$('#showHolonObjects').hover(function() {
		$('#showHolonObjects').css('cursor','pointer');
			  });
	
	$("#close").click(function(){
		$(this).parent().fadeOut("slow", function(c) {
        });
	})
})

function saveHolonObject(){
	//alert("saveHolonObject");
	var holonObjectPriority=$("#holonObjectPriority").val();
	var holonObjectType=$("#holonObjectType option:selected").val();
	var holonCoordinatorId=$("#holonCoordinatorId option:selected").val();
	var canCommunicate=$("#canCommunicate option:selected").val();
	var latNE=$("#holonObjectLatitudeNE").text();
	var lngNE=$("#holonObjectLongitudeNE").text();
	var latSW=$("#holonObjectLatitudeSW").text();
	var lngSW=$("#holonObjectLongitudeSW").text();
	var holonObjectActionState = $("#holonObjectActionState").val();
	var hiddenHolonObjectId = $("#hiddenHolonObjectId").val();
	$( "#holonObjectDetail" ).slideUp(100);
	var dataAttributes = {
			holonObjectType : holonObjectType,
			holonCoordinatorId : holonCoordinatorId,
			canCommunicate:canCommunicate,
			latNE : latNE,
			lngNE : lngNE,
			latSW : latSW,
			lngSW : lngSW,
			holonObjectPriority : holonObjectPriority,
			hiddenHolonObjectId : hiddenHolonObjectId
		};
	if(holonObjectActionState == "Edit"){
		ajaxRequest("editHolonObject", dataAttributes, editHolonObjectCallBack, holonEditOptions);
	} else {
		ajaxRequest("createHolonObject", dataAttributes, createHolonObjectCallBack, {});							
	}
}

function editHolonObject(holonObjectId,infowindowHolonObject) {
	//alert("editHolonObject");
	var dataAttributes= {
			holonObjectId : holonObjectId
	}
	var options= {
			infowindowHolonObject : infowindowHolonObject
	}
	ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonDetailCallBack, options);
}

function getHolonDetailCallBack(data, option) {
	holonEditOptions = option;
	var dataArray = data.split("!");
	var holonObjectId = dataArray[0];
	var holonCoordinatorName_Holon= dataArray[1];	
	var holonObjectTypeName= dataArray[2];
	var ne_location= dataArray[3];
	var sw_location= dataArray[4];
	var lineConnectedState= dataArray[5];
	var holonColor= dataArray[6];
	var holonManagerName= dataArray[7];
	var canCommunicate=dataArray[24];
	var ne_latlng=ne_location.split("~");
	var sw_latlng=sw_location.split("~");
	 $("#holonObjectLatitudeNE").text(ne_latlng[0]);
	 $("#holonObjectLongitudeNE").text(ne_latlng[1]);
	 $("#holonObjectLatitudeSW").text(sw_latlng[0]);
	 $("#holonObjectLongitudeSW").text(sw_latlng[1]);
	 $("#holonObjectActionState").val("Edit");
	 $("#hoObjTitle").text("Edit Holon Object");
	 $("#hiddenHolonObjectId").val(holonObjectId);
	 $("#holonManagerName").val(holonManagerName);
	 $("#canCommunicate").empty();
	 var selOptions= "<option value=1 selected>Yes</option><option value=0>No</option>"
		 if(canCommunicate=="No")
			{
			 selOptions= "<option value=1 >Yes</option><option value=0 selected>No</option>"
			}
		$("#canCommunicate").append(selOptions);
	 getHolonObjectTypeFromDatabase(holonObjectTypeName);
	 getHolonCoordinatorFromDatabase(holonCoordinatorName_Holon.trim(),"holonCoordinatorId","holonObjectDetail");	
}

function editHolonObjectCallBack(data, options){
	
	var resp=data.split("!");
	var holonColor= resp[0];
	var holonObjectId=resp[1];
	var holonCoordinatorName_Holon= resp[2];	
	var holonObjectTypeName= resp[3];
	var ne_location= resp[4];
	var sw_location= resp[5];
	var lineConnectedState= resp[6];
	var coordHolonId= resp[7];
	var isCoord= resp[8];
	var hc_ne= resp[9];
	var canComunicate= resp[10];
	var lat=ne_location.split("~");
	var kc_lat=hc_ne.split("~");
	
	var dataAttributes= {
			  holonObjectId : holonObjectId,
			}
	 ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});
	 showHolonCoIcons();
	var editedHolonObject=globalHoList.get(holonObjectId.toString());
	editedHolonObject.setOptions({strokeColor:holonColor,fillColor: holonColor});
	attachMessage(holonObjectId,editedHolonObject);
	globalHoList.set(holonObjectId,editedHolonObject);

}
function createHolonObjectCallBack(data, options) {
	var dataArray = data.split("!");
	var holonObjectId = dataArray[0];	
	var isCoord= dataArray[1];
	var hc_ne_location= dataArray[2];
	var noOfHolons= dataArray[3];
	var holonColor= dataArray[4];
	createdHolonObject.setOptions({strokeColor:holonColor,fillColor: holonColor});
	 //When Rectangle is clicked
	 attachMessage(holonObjectId, createdHolonObject);
		showHolonCoIcons();
		globalHoList.set(holonObjectId,createdHolonObject);
	}

function getHolonInfoWindowCallBack(data,options)
{
	var dataArray = data.split("!");
	var holonObjectId = dataArray[0];
	var holonCoordinatorName_Holon= dataArray[1];	
	var holonObjectTypeName= dataArray[2];
	var ne_location= dataArray[3];
	var sw_location= dataArray[4];
	var lineConnectedState= dataArray[5];
	var holonColor= dataArray[6];
	var coordHolonId= dataArray[7];
	var nOfElmInHolon=dataArray[8];
	var minEnergyHoObj=dataArray[9];
	var maxEnergyHoObj=dataArray[10];
	var originalEnergyHoObj=dataArray[11];
	var minEnergyProdObj=dataArray[12];
	var maxEnergyProdObj=dataArray[13];
	var cuEnergyProdObj=dataArray[14];	
	var nOfHolonObj=dataArray[15];
	var minEnergyHo=dataArray[16];
	var maxEnergyHo=dataArray[17];
	var cuEnergyHo=dataArray[18];
	var minEnergyProd=dataArray[19];
	var maxEnergyProd=dataArray[20];
	var cuEnergyProd=dataArray[21];
	var hoList=dataArray[22];
	var canCommunicate=dataArray[23];
	var coOrd_ne_location=dataArray[24];
	var createdFromFactory = dataArray[25];
	var currentEnergyRequired = dataArray[26];
	var flexibility = dataArray[27];
	var originalEnergyRequiredHolon = dataArray[28];
	var flexibilityHolon = dataArray[29];
	var lat=ne_location.split("~");
	//alert(holonColor);
	var contentString=
			"<div class='table'><table>"+
			"<tr><td colspan='2' style='text-decoration: underline;'>Holon Object Details</td></tr>"+
			"<tr><td><b>Holon Object Id: "+holonObjectId +"</td>"+
			"<td>Holon Object Type: "+holonObjectTypeName+"</td></tr>"+
			"<tr><td colspan='2'>Line Connected State: "+lineConnectedState+"</td></tr>";
	console.log(contentString);
	if(coordHolonId==0) {
		contentString=contentString.concat("<tr><td>Coordinator Id: No Coordinator</td>");
	} else if(coordHolonId==-1) {
		contentString=contentString.concat("<tr><td style='color:red'>Coordinator Id: Not connected</td>");
	} else {
		contentString=contentString.concat("<tr><td>Coordinator Id: <a href='#' id='hoCoId'>"+coordHolonId+"</a></td>");
	}
			
	contentString=contentString.concat("<td>Minimum Energy Req: "+minEnergyHoObj+"</td></tr>"+
			"<tr><td>Maximum Energy Req: "+maxEnergyHoObj+"</td>"+
			"<td>Original Energy Req: "+originalEnergyHoObj+"</td></tr>"+
			"<tr><td>Minimum Production capacity: "+minEnergyProdObj+"</td>"+
			"<td>Maximum Production Capacity: "+maxEnergyProdObj+"</td></tr>"+
			"<tr><td>Current Production: "+cuEnergyProdObj+"</td>" +
			"<td>Can Communicate: "+canCommunicate+"</td></tr>"+
			"<tr><td>Flexibility: "+flexibility+"</td>");
	if(currentEnergyRequired > 0) {
		contentString = contentString.concat("<td style='color:red'>Current Energy Required: "+currentEnergyRequired+"</td></tr>");
	} else {
		contentString = contentString.concat("<td>Current Energy Required: "+currentEnergyRequired+"</td></tr>");
	}
	contentString = contentString.concat(
			"<tr><td>Created from factory: "+createdFromFactory+"</td></tr>" +
			"</table>"+
			"<br /><hr>"+
			"<table><tr><td colspan='2' style='text-align: center;'>"+
			"<span class='button' id='supplierDetails' title='Show Supplier Details' onclick='showSupplierDetails("+holonObjectId+")'><i class='fa fa-industry'>&nbspShow Supplier Details</i></span>"+
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			"<span class='button' id='consumptionGraph' title='Show Consumption' onclick='showConsumptionGraph("+holonObjectId+","+holonObjectId+","+'"ho"'+")'><i class='fa fa-line-chart'>&nbspShow Consumption</i></span>"+
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			"<span class='button' id='editHolonObject' title='Edit Holon Object'><i class='fa fa-pencil-square-o'>&nbspEdit Holon Object</i></span>"+
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			"<span class='button' id='showHolonElement' title='Show Holon Elements' onclick='showHolonElements("+holonObjectId+")'><i class='fa fa-info'>&nbspShow Holon Elements</i></span>" +
			"</td></tr></table><hr>");
	if(coordHolonId===holonObjectId) {
		contentString = contentString.concat(
				"<table>"+
				"<tr><td colspan='2' style='text-decoration: underline;'>Holon Details</td></tr>" +
				"<tr><td>Number of Holon Objects: "+nOfHolonObj +"</td>"+
				"<td>Minimum Energy Req:"+minEnergyHo +"</td></tr>"+
				"<tr><td>Maximum Energy Req: "+maxEnergyHo +"</td>");
		if(cuEnergyHo > 0) {
			contentString = contentString.concat("<td style='color:red'>Current Energy Req: "+cuEnergyHo +"</td></tr>");
		} else {
			contentString = contentString.concat("<td>Current Energy Req: "+cuEnergyHo +"</td></tr>");
		}
		contentString = contentString.concat(
				"<tr><td>Minimum Production capacity: "+minEnergyProd +"</td>"+
				"<td>Maximum Production Capacity: "+maxEnergyProd +"</td></tr>"+
				"<tr><td>Current Production: "+cuEnergyProd +"</td><td></td></tr>"+
				"<tr><td>Original Energy Required: "+originalEnergyRequiredHolon +"</td>"+
				"<td>Flexibility: "+flexibilityHolon +"</td></tr>"+
				"</table>"+
				"<hr><table><tr><td colspan='2' style='text-align: center;'>Other Holon Objects:&nbsp;&nbsp;"+
				"<select align = \"center\" name=\"infoWindowHolonList\" id=\"infoWindowHolonList\">"+hoList+"</select></td></tr>"+
				"</table>"
		);
	}
	contentString.concat("</div>");

	closeOtherInfoWindows();
	var infowindowHolonObject = new google.maps.InfoWindow({
	      content: contentString,
	      position:new google.maps.LatLng(lat[0],lat[1])
	  });
	infowindowHolonObject.open(map,map);
	$('#editHolonObject').click(function() {
		editHolonObject(holonObjectId,infowindowHolonObject);			
	})
		$('#hoCoId').click(function() {
			zoomToHolon(coordHolonId,coOrd_ne_location,"Holon Object");			
	})
	
	$('#infoWindowHolonList').change(function(){
      if(jQuery("#infoWindowHolonList option:selected").val()!='Select Holon')
    	  {
    	  	zoomToHolon(jQuery("#infoWindowHolonList option:selected").val(),ne_location,"Holon Object");
    	  }
    });
	
	currentInfoWindowObject=infowindowHolonObject;
}

function zoomToHolon(holonObjectId,neLoc, type) {
	var location = new google.maps.LatLng(neLoc.split("~")[0], neLoc.split("~")[1]);
	if(type=="Holon Object") {
		var dataAttributes= {
				  holonObjectId : holonObjectId,
				};
		 ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});
	} else {
		var dataAttributes= {
				  psId : holonObjectId,
				};
		  var option= {
				  powerSrc : globalPSrcList.get(holonObjectId.toString()),
				};
		 ajaxRequest("getPsObjectInfoWindow", dataAttributes, getPsObjectInfoWindowCallBack, option);			
		}
	map.setCenter(location);
	map.setZoom(18);
}

function showHolonObjects() {
	if(loadHolon){
	ajaxRequest("showHolonObjects", {}, showHolonObjectsCallBack, {});
	showSavedPowerLines();
	showSavedPowerSwitches();
	showSavedPowerSources();
	loadHolon=false;
	}else{
		
		swal({
			title : "Holon already loaded.",
			text : "The Holon has already been loaded. Please clear the map first to reload it.",
			type : "info",
			confirmButtonText : "Sure!"
		});
		
	}
}

function showHolonObjectsCallBack(data, options){
	//alert(data);
	var res = data.replace("[", "").replace("]", "").split(',').join("");
	//alert(res);
	var hoObjectsList = res.split("*");
	for (var i=0; i<hoObjectsList.length-1; i++) {
		var holonObject = hoObjectsList[i];
		var holonObjectId =  holonObject.split("#")[0].trim();
		var color = holonObject.split("#")[1].trim();	
		var location = holonObject.split("#")[2];
		var isCoord=holonObject.split("#")[3];
		var hasPower =holonObject.split("#")[4];
		var hasPowerOn=holonObject.split("#")[5];
		var ne_location_lat = location.split("^")[0].split("~")[0].replace("[","").replace(",","");
		var ne_location_lng = location.split("^")[0].split("~")[1];
		var sw_location_lat = location.split("^")[1].split("~")[0];
		var sw_location_lng = location.split("^")[1].split("~")[1];
		//alert("a+"+color+"+b")
	    var rectangleFromFactory = new google.maps.Rectangle({
		      map: map,
		      strokeColor :color,
		      fillColor:color,
		      bounds: new google.maps.LatLngBounds(
		    	      new google.maps.LatLng(sw_location_lat, sw_location_lng),
		    	      new google.maps.LatLng(ne_location_lat, ne_location_lng))
		    });
	    showPowerCircles(holonObjectId);
	    attachMessage(holonObjectId, rectangleFromFactory);
	 	globalHoList.set(holonObjectId,rectangleFromFactory);
	}
	

	 showHolonCoIcons();
}


function showPowerCircles(holonObjectId)
{
	var dataAttributes= {
			  holonObjectId : holonObjectId
			};
	var options=dataAttributes;
	ajaxRequest("getDetailForPowerSourceIcon", dataAttributes, getDetailForPowerSourceIconCallBack, options);
}

function getDetailForPowerSourceIconCallBack(data,options)
{
	var resp = data.split("*");
	var hasPower = resp[0];
	var hasPowerOn=resp[1];
	var ne_location_lat=resp[2];
	var sw_location_lng=resp[3];
	var holonObjectId = options["holonObjectId"];	
	var powerColor = '#FF0000';
	//alert(hasPowerOn);
	   if(hasPowerOn=='true')
		   {
		   powerColor = '#336600';
		   }
	 var labelTxt=  '<i style="color:'+powerColor+';" class="map-icon-electrician"></i>';
	var currecntPC=globalPCList.get(holonObjectId.toString());
	if(typeof(currecntPC) === "undefined")
		{
		if(hasPower=="true")
		 {
			//alert("has power");
			  
			  /* currecntPC=new google.maps.Circle({
					 strokeColor: powerColor,
				     strokeOpacity: 1,
				     strokeWeight: 1,
				     fillColor: powerColor,
				     fillOpacity: 0.35,
				     map: map,
				     center: new google.maps.LatLng(ne_location_lat, sw_location_lng),
				     radius: 3
				    });	*/  
			    
			    currecntPC = new Marker({
					map: map,
					title: 'Power Producer',
					position: new google.maps.LatLng(ne_location_lat, sw_location_lng),
					zIndex: 9,
					icon: {
						path: ROUTE,
						fillColor: '#0E77E9',
						fillOpacity: 0,
						strokeColor: '',
						strokeWeight: 0,
						scale: 1/100
					},
					custom_label: labelTxt
				});
			   
			    }
			
			}else
				{
			
					if(hasPower=="true"){
						//alert(powerColor);
						currecntPC = new Marker({
							map: map,
							title: 'Power Producer',
							position: new google.maps.LatLng(ne_location_lat, sw_location_lng),
							zIndex: 9,
							icon: {
								path: ROUTE,
								fillColor: '#0E77E9',
								fillOpacity: 0,
								strokeColor: '',
								strokeWeight: 0,
								scale: 1/100
							},
							custom_label: labelTxt
						});
						
						//currntPC.setOptions({strokeColor:powerColor, fillColor:powerColor,center:new google.maps.LatLng(ne_location_lat, sw_location_lng)});
					}else
						{
							currecntPC.setMap(null);
						
						}
					
				}
	globalPCList.set(holonObjectId,currecntPC);	
}

function attachMessage(holonObjectId, rectangleFromFactory) {
	google.maps.event.addListener(rectangleFromFactory, 'click', function(event) {	
		if(connectToPowerSourceMode==true)
		{
		connectToPowerSource(event.latLng,holonObjectId,"HolonObject");
		}else if(areConnectedMode == true){
			if(isConnectedFirst=="" && isConnectedSecond=="")
				{
				isConnectedFirst=holonObjectId;
				}
			else if(isConnectedFirst!="" && isConnectedSecond=="")
				{
				 isConnectedSecond=holonObjectId;
				 areHolonObjectsConnected(isConnectedFirst,isConnectedSecond);
				 isConnectedFirst="";
				 isConnectedSecond="";
				}
			else 
				{
				alert("Please close the isConnected Mode" );
				}
			
		}
		
		else{
			var dataAttributes={
					holonObjectId:holonObjectId	
			};
  ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});		
		}
		
	  });
}

//Function to show holon coordinator Icons after any change
function showHolonCoIcons()
{
	var redIcon= globalHKList.get("red");
	var blueIcon= globalHKList.get("blue");
	var greenIcon= globalHKList.get("green");
	var yellowIcon= globalHKList.get("yellow");
	if(typeof redIcon != "undefined")
	{		
		redIcon.setMap(null);
	}
	if(typeof blueIcon != "undefined")
	{		
		blueIcon.setMap(null);
	}
	if(typeof greenIcon != "undefined")
	{		
		greenIcon.setMap(null);
	}
	if(typeof yellowIcon != "undefined")
	{		
		yellowIcon.setMap(null);
	}
	ajaxRequest("getHoCoIcons", {}, getHoCoIconsCallBack, {});	

}

function getHoCoIconsCallBack(data,options)
{
	
	var result=data.split("*");
	var hoCoObIdBlue=result[0];
	var hoCoObIdGreen=result[1];
	var hoCoObIdYellow=result[2];
	var hoCoObIdRed=result[3];
	if(hoCoObIdBlue!=0)
	{		
		var hoObj=globalHoList.get(hoCoObIdBlue.toString());
		var cLocation=hoObj.getBounds().getNorthEast();
		var coIcon= createCoIcon(cLocation);
		globalHKList.set("blue",coIcon);
	}
	if(hoCoObIdGreen!=0)
	{		
		var hoObj=globalHoList.get(hoCoObIdGreen.toString());
		var cLocation=hoObj.getBounds().getNorthEast();
		var coIcon= createCoIcon(cLocation);
		globalHKList.set("green",coIcon);
	}
	if(hoCoObIdYellow!=0)
	{		
		var hoObj=globalHoList.get(hoCoObIdYellow.toString());
		var cLocation=hoObj.getBounds().getNorthEast();
		var coIcon= createCoIcon(cLocation);
		globalHKList.set("yellow",coIcon);
	}
	if(hoCoObIdRed!=0)
	{		
		var hoObj=globalHoList.get(hoCoObIdRed.toString());
		var cLocation=hoObj.getBounds().getNorthEast();
		var coIcon= createCoIcon(cLocation);
		globalHKList.set("red",coIcon);
	}
	
}

function createCoIcon(cLocation)
{
	 var coOrdCircle= new Marker({
			map: map,
			title: 'Holon Coordinator',
			position: cLocation,
			zIndex: 9,
			icon: {
				path: ROUTE,
				fillColor: '#0E77E9',
				fillOpacity: 0,
				strokeColor: '',
				strokeWeight: 0,
				scale: 1/100
			},
			custom_label: '<i class="map-icon-lawyer"></i>'
		});
	 
	 return coOrdCircle;

}
function deleteHolonObject(holonObjectId) {
	
}

function closeDiv(id) {
	$("#"+id).slideUp(100);
}

function openDiv(id) {
	$("#"+id).slideDown(100);
}
