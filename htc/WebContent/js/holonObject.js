/**
 * Java Script file for functions related to holon objects
 */

var editOptions = {};
function saveHolonObject(){
	//alert("saveHolonObject");
	var holonObjectPriority=$("#holonObjectPriority").val();
	var holonObjectType=$("#holonObjectType option:selected").val();
	var holonCoordinatorId=$("#holonCoordinatorId option:selected").val();
	var canCommunicate=$("#canCommunicate option:selected").val();
	var holonManager=$("#holonManagerName").val();
	var latNE=$("#holonObjectLatitudeNE").text();
	var lngNE=$("#holonObjectLongitudeNE").text();
	var latSW=$("#holonObjectLatitudeSW").text();
	var lngSW=$("#holonObjectLongitudeSW").text();
	var holonObjectActionState = $("#holonObjectActionState").val();
	var hiddenHolonObjectId = $("#hiddenHolonObjectId").val();
	var hiddenHolonManagerId = $("#hiddenHolonManagerId").val();
	$( "#holonObjectDetail" ).slideUp(100);
	var dataAttributes = {
			holonObjectType : holonObjectType,
			holonCoordinatorId : holonCoordinatorId,
			holonManager : holonManager,
			canCommunicate:canCommunicate,
			latNE : latNE,
			lngNE : lngNE,
			latSW : latSW,
			lngSW : lngSW,
			holonObjectPriority : holonObjectPriority,
			hiddenHolonObjectId : hiddenHolonObjectId,
			hiddenHolonManagerId : hiddenHolonManagerId
		};
	if(holonObjectActionState == "Edit"){
		ajaxRequest("editHolonObject", dataAttributes, editHolonObjectCallBack, editOptions);
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
	editOptions = option;
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
	 getHolonCoordinatorFromDatabase(holonCoordinatorName_Holon.trim());	
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
	var holonManagerName= resp[10];
	var canComunicate= resp[11];
	var lat=ne_location.split("~");
	var kc_lat=hc_ne.split("~");
	
	var dataAttributes= {
			  holonObjectId : holonObjectId,
			}
	 ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});
	
	var newCoordCircle = globalHKList.get(holonColor);
	if(typeof newCoordCircle == "undefined")
		{		
			showCoordCircles(holonColor,true,kc_lat[0],kc_lat[1]);
		 
		}else
			{
			newCoordCircle.setOptions({center:new google.maps.LatLng(kc_lat[0],kc_lat[1])});
			globalHKList.set(holonColor,newCoordCircle);
			}	

	var editedHolonObject=globalHoList.get(holonObjectId.toString());
	editedHolonObject.setOptions({strokeColor:holonColor,fillColor: holonColor});
	google.maps.event.addListener(editedHolonObject, 'click', function(event) {
		addEventActionToObject(holonObjectId,event.latLng);
		});
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
	  google.maps.event.addListener(createdHolonObject, 'click', function(event) {
		  addEventActionToObject(holonObjectId,event.latLng)
		  });
	  if(noOfHolons=="1")
		 {
		  showCoordCircles(holonColor,true,createdHolonObject.getBounds().getNorthEast().lat(),createdHolonObject.getBounds().getNorthEast().lng());
		  }else
			 {
			 var newCoordCircle = globalHKList.get(holonColor);
			 newCoordCircle.setOptions({center:new google.maps.LatLng(hc_ne_location.split("~")[0],hc_ne_location.split("~")[1])});
			 globalHKList.set(holonColor,newCoordCircle);
			 }
	  
	  //globalHoList[holonObjectId]=createdHolonObject;
	  globalHoList.set(holonObjectId,createdHolonObject);
	//createdHolonObject=null;
}

function addEventActionToObject(holonObjectId,latLng)
{
	 if(connectToPowerSourceMode==true)
		{
		  connectToPowerSource(latLng,holonObjectId,"HolonObject");
		}else{
	  var dataAttributes= {
			  holonObjectId : holonObjectId,
			}
	 ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});
		}
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
	var holonManagerName= dataArray[8];
	var nOfElmInHolon=dataArray[9];
	var minEnergeyHoObj=dataArray[10];
	var maxEnergeyHoObj=dataArray[11];
	var cuEnergeyHoObj=dataArray[12];
	var minEnergeyProdObj=dataArray[13];
	var maxEnergeyProdObj=dataArray[14];
	var cuEnergeyProdObj=dataArray[15];	
	var nOfHolonObj=dataArray[16];
	var minEnergeyHo=dataArray[17];
	var maxEnergeyHo=dataArray[18];
	var cuEnergeyHo=dataArray[19];
	var minEnergeyProd=dataArray[20];
	var maxEnergeyProd=dataArray[21];
	var cuEnergeyProd=dataArray[22];
	var hoList=dataArray[23];
	var canCommunicate=dataArray[24];
	var coOrd_ne_location=dataArray[25];
	var lat=ne_location.split("~");
	//alert(holonColor);
	var contentString="<h3 align=\"center\">Holon Object Details</h3>" +
			"<table>"+
			"<tr><td><b>Holon Object Id: </b>"+holonObjectId +"<br> </td>"+
			"<td><b>Holon Object Type: </b>"+holonObjectTypeName+"<br></td></tr>"+
			"<tr><td><b>Holon Manager: </b>"+holonManagerName+"<br> </td>"+
			"<td><b>Line Connected State: </b>"+lineConnectedState+"<br></td></tr>";
	if(coordHolonId==0)
		{
		contentString=contentString.concat("<tr><td><b>Coordinator Id: </b>No Coordinator<br></td>");
		}else
		{
		contentString=contentString.concat("<tr><td><b>Coordinator Id: </b> <a href='#' id='hoCoId'>"+coordHolonId+"</a><br></td>");
		}
			
	contentString=contentString.concat("<td><b>Minimum Energey Req: </b>"+minEnergeyHoObj+"<br></td></tr>"+
			"<tr><td><b>Maximum Energey Req: </b>"+maxEnergeyHoObj+"<br></td>"+
			"<td><b>Current Energey Req: </b>"+cuEnergeyHoObj+"<br></td></tr>"+
			"<tr><td><b>Minimum Production capacity: </b>"+minEnergeyProdObj+"<br></td>"+
			"<td><b>Maximum Production Capacity: </b>"+maxEnergeyProdObj+"<br></td></tr>"+
			"<tr><td><b>Current Production: </b>"+cuEnergeyProdObj+"<br></td>" +
			"<td><b>Can Communicate: </b>"+canCommunicate+"<br></td></tr>"+
			"</table>"+
			"<span class='button'><i class='fa fa-plug'></i>&nbsp;&nbsp;Connect to Power Source</span>&nbsp;&nbsp;&nbsp;&nbsp;"+
			"<span class='button' id='consumptionGraph'><i class='fa fa-line-chart'></i>&nbsp;&nbsp;Show Consumption</span>&nbsp;&nbsp;&nbsp;&nbsp;"+
			"<span class='button' id='editHolonObject'><i class='fa fa-pencil-square-o'></i>&nbsp;&nbsp;Edit Holon Object</span>&nbsp;&nbsp;&nbsp;&nbsp;"+
			"<span class='button' id='showHolonElement' onclick='showHolonElements("+holonObjectId+")'><i class='fa fa-info'></i>&nbsp;&nbsp;Show Holon Elements</span><hr>");
	if(coordHolonId===holonObjectId)
	{
		contentString = contentString.concat(
				"<h3 align=\"center\">Holon Details</h3>" +
				"<table>"+
				"<tr><td><b>Number of Holon Objects: </b>"+nOfHolonObj +"<br></td><td></td>"+
				"<td><b>Minimum Energey Req:</b>"+minEnergeyHo +"<br></td></tr>"+
				"<tr><td><b>Maximum Energey Req: </b>"+maxEnergeyHo +"<br></td><td></td>"+
				"<td><b>Current Energey Req: </b>"+cuEnergeyHo +"<br></td></tr>"+
				"<tr><td><b>Minimum Production capacity: </b>"+minEnergeyProd +"<br></td><td></td>"+
				"<td><b>Maximum Production Capacity: </b>"+maxEnergeyProd +"<br></td></tr>"+
				"<tr><td><b>Current Production: </b>"+cuEnergeyProd +"<br></td><td></td><td></td></tr>"+
				"</table>"+
				"<select name=\"infoWindowHolonList\" id=\"infoWindowHolonList\">" +
				hoList+
				"</select></td>"
				
		);
	}

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
			zoomToHolon(coordHolonId,coOrd_ne_location);			
	})
	
	$('#infoWindowHolonList').change(function(){
      if(jQuery("#infoWindowHolonList option:selected").val()!='Select Holon')
    	  {
    	  	zoomToHolon(jQuery("#infoWindowHolonList option:selected").val(),ne_location);
    	  }
    });
	
	currentInfoWindowObject=infowindowHolonObject;
}

function zoomToHolon(holonObjectId,neLoc)
{
	var location = new google.maps.LatLng(neLoc.split("~")[0], neLoc.split("~")[1])
	var dataAttributes= {
			  holonObjectId : holonObjectId,
			}
	 ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});
	map.setCenter(location);
	map.setZoom(18);
	
}

function showHolonObjects() {
	if(loadHolon){
	ajaxRequest("showHolonObjects", {}, showHolonObjectsCallBack, {});
	showSavedPowerLines();
	showSavedPowerSwitches();
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
	 showCoordCircles(color,isCoord,ne_location_lat,ne_location_lng);
	 showPowerCircles(holonObjectId);
		    var dataAttributes= {
				  holonObjectId : holonObjectId
				};
	    attachMessage(dataAttributes, rectangleFromFactory);
	   // globalHoList[holonObjectId]=rectangleFromFactory;
	    globalHoList.set(holonObjectId,rectangleFromFactory);
	}	
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
	var currecntPC=globalPCList.get(holonObjectId.toString());
	if(typeof(currecntPC) === "undefined")
		{
		if(hasPower=="true")
		 {
			//alert("has power");
		  
		    currecntPC=new google.maps.Circle({
				 strokeColor: powerColor,
			     strokeOpacity: 1,
			     strokeWeight: 1,
			     fillColor: powerColor,
			     fillOpacity: 0.35,
			     map: map,
			     center: new google.maps.LatLng(ne_location_lat, sw_location_lng),
			     radius: 3
			    });	  
		    }
		
		}else
			{
				//alert("not null"+holonObjectId);
				currecntPC.setOptions({map:null});
				if(hasPower=="true"){
				currecntPC.setOptions({map:map,fillColor: powerColor,strokeColor: powerColor});
				}
				
			}
	  globalPCList.set(holonObjectId,currecntPC);	
}


function showCoordCircles(color,isCoord,ne_location_lat,ne_location_lng){
	
	 if(isCoord=="true")
	 {
	   var coOrdCircle=new google.maps.Circle({
			 strokeColor: '#000000',
		     strokeOpacity: 1,
		     strokeWeight: 1,
		     fillColor: '#000000',
		     fillOpacity: 1,
		     map: map,
		     center: new google.maps.LatLng(ne_location_lat, ne_location_lng),
		     radius: 2
		    });
	   globalHKList.set(color,coOrdCircle);
	 }
}

function attachMessage(dataAttributes, rectangleFromFactory) {
	google.maps.event.addListener(rectangleFromFactory, 'click', function(event) {	
			if(connectToPowerSourceMode==true)
				{
				connectToPowerSource(event.latLng, dataAttributes["holonObjectId"],"HolonObject");
				}else{
		  ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});		
				}
		
	  });
}

function closeDiv(id) {
	$("#"+id).slideUp(100);
}

function openDiv(id) {
	$("#"+id).slideDown(100);
}


function deleteHolonObject(holonObjectId) {
	alert("Holon Object ID = "+holonObjectId);
	
}
