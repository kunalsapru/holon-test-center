$(document).ready(function() {
	//call initialize function
	initialize();
	var drawingManager;
	var infowindow="";
	//Array of Markers and Infowindow
	var infowindowArray=[];
	var markerslat=[];
	var markerslng=[];
	//Variable to get the old content from the info window
	var contentInfoWindow="";
	var clickedToAddElements="";
	//Div to add details about the new elements inserted
	$("#elementInfo").hide();
	$("#displayHolonDetails").hide();
	$("#close").click(function(){
		$(this).parent().fadeOut("slow",function(c){});
	});
	
	 $(document).on("click", "#moreInfo", function(){
		 var clicked= $("#moreInfo").attr("value");
		 var holonDetail=infowindowArray[clicked].split("@");
		 if(infowindow)
			{infowindow.close();}
			var data="";
			var dataForInfoWindow="";
			for(var k=0;k<holonDetail.length-1;k++)
				{
					var individualElementDetail=holonDetail[k].split("#");
					for(var l=0;l<individualElementDetail.length;l++)
						{   
							data= data+"</br>"+individualElementDetail[l]
						}
				var content="<div data-role='collapsible' id='displayHolonDetails" + k + "'><h3>Holon Element " + (k+1) + "</h3><p>"+data+"</p></div>";
				$("#displayHolonDetails").show();	
				$("#displayHolonDetails").append( content ).collapsibleset('refresh');
					data="";
				}
		 
				
			
		});
	 
	//Holon object Button click event
	$('#addHolonObject').click(function(){
		$("#displayHolonDetails").hide();

	//	To check If the layout is already present ;
		
		if (drawingManager== null){
			//Creates a new drawing manager object for first time
			
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
                color:"red"
            },
            polylineOptions: {
                geodesic:true,
                clickable: true,
                strokeColor:"yellow",
                strokeOpacity: 2.0,
                strokeWeight: 4.0
            }
    	    });
     // Setting the layout on the map 
      drawingManager.setMap(map);
     // Event when the overlay is complete 
      google.maps.event.addListener(drawingManager, 'overlaycomplete', function(event) {
    	  var newShape = event.overlay; // Object
    	  newShape.type = event.type;	// Rectangle
    	  console.log(newShape.getBounds()); 
    	 // alert(newShape.getBounds());
    	  var latNorthEast=newShape.getBounds().getNorthEast().lat(); //get lat of northeast
    	  var lngNorthEast=newShape.getBounds().getNorthEast().lng();	//get longitude of north east
    	  markerslat.push(latNorthEast); //add data to array
    	  markerslng.push(lngNorthEast); //add data to array
    	  infowindowArray.push("");
      
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
    	 
//    		create the ContextMenuOptions object
    		var contextMenuOptions={};
    		contextMenuOptions.classNames={menu:'context_menu', menuSeparator:'context_menu_separator'};
    		
    		//	create an array of ContextMenuItem objects
    		var menuItems=[];
    		menuItems.push({className:"ui-btn ui-btn-inline", eventName:'addHoloneElement_on_click', label:'Add Holon Elements'});
    		//	a menuItem with no properties will be rendered as a separator
    		menuItems.push({});
    		contextMenuOptions.menuItems=menuItems;
    		
    		//	create the ContextMenu object
    		var contextMenu=new ContextMenu(map, contextMenuOptions);
    	  
    		//Right Click
    	  google.maps.event.addListener(newShape, 'rightclick', function(mouseEvent) {
    		  contextMenu.show(mouseEvent.latLng);
    		  clickedToAddElements=newShape.getBounds().getNorthEast().lat();
    	  });
    	  
    	  
    	  
    	  google.maps.event.addListener(contextMenu, 'menu_item_selected', function(latLng, eventName){
    		 // Show Popup menu 
    		//  $("#elementInfo").show();
    		  $("#elementInfo").toggle("slide",{direction:"left"});
    		  
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
		 $("#elementInfo").toggle("slide",{direction:"right"});
		  
	  });
	  //End of Save Button
	  
	  
	  
	 
	}	
		
	});
	
	 $("#addHolonFactory").click(function(){
		 $("#displayHolonDetails").hide();

			var dataAttr1 = "Ein";
			var dataAttr2 = "Zwei";
			var dataAttr3 = "Drei";
			var dataAttr4 = "Vier";
			var dataAttributes={dataAttr1:dataAttr1,dataAttr2:dataAttr2,dataAttr3:dataAttr3,dataAttr4:dataAttr4};

			ajaxRequest("createHolonsFromFactory", dataAttributes, callBackCreateHolons, {}); 
	  });
	 
	 function callBackCreateHolons(dataFromFactory, options){
			
//		 alert("TEST --"+dataFromFactory);
		 var long=[8.555232882499695,8.555393815040588,8.554060757160187,8.55423241853714,8.555128276348114,8.555310666561127];
		 var lat=[49.86342249762294,49.86356427407353,49.863567732030546,49.86368184447307,49.86372506881273,49.863795956646065];
		 var data= dataFromFactory.split("##"); 
		  for(var i=0;i<2;)
			  {
			  var rectangle1 = new google.maps.Rectangle({
				    map: map,
				    bounds: new google.maps.LatLngBounds(
				      new google.maps.LatLng(lat[i], long[i]),
				      new google.maps.LatLng(lat[i+1], long[i+1]))
				  });
			  
			  i=i+2;
			
			  }
		  for(var i=2;i<4;)
		  {
		  var rectangle2 = new google.maps.Rectangle({
			    map: map,
			    bounds: new google.maps.LatLngBounds(
			      new google.maps.LatLng(lat[i], long[i]),
			      new google.maps.LatLng(lat[i+1], long[i+1]))
			  });
		  
		  i=i+2;
		
		  }
		  for(var i=4;i<6;)
		  {
		  var rectangle3 = new google.maps.Rectangle({
			    map: map,
			    bounds: new google.maps.LatLngBounds(
			      new google.maps.LatLng(lat[i], long[i]),
			      new google.maps.LatLng(lat[i+1], long[i+1]))
			  });
		  
		  i=i+2;
		
		  }
		  var  infowindowrect1;
		  
		  google.maps.event.addListener(rectangle1, 'click', function(){
			  if(infowindowrect1)
				{infowindowrect1.close();}
			  infowindowrect1 = new google.maps.InfoWindow({
			      content: data[0],
			      position: new google.maps.LatLng(lat[1], long[1])
			  });
			infowindowrect1.open(map,rectangle1);
		  });
		  google.maps.event.addListener(rectangle2, 'click', function(){
			  if(infowindowrect1)
				{infowindowrect1.close();}
			 infowindowrect1 = new google.maps.InfoWindow({
			      content: data[1],
			      position: new google.maps.LatLng(lat[3], long[3])
			  });
			infowindowrect1.open(map,rectangle2);
		  });
		  google.maps.event.addListener(rectangle3, 'click', function(){
			  if(infowindowrect1)
				{infowindowrect1.close();}
			  infowindowrect1 = new google.maps.InfoWindow({
			      content: data[2],
			      position: new google.maps.LatLng(lat[5], long[5])
			  });
			infowindowrect1.open(map,rectangle3);
		  });

}
	
	$("#clear").click(function(){
		 //location.reload();
		$("#displayHolonDetails").hide();
		 //$("#nav").hide();
		initialize();
	  });
	//Map click event
	google.maps.event.addListener(map, 'click', function(event) {
		// $("#elementInfo").toggle("slide",{direction:"right"});
	});
	

	
});


function initialize() {
	  mapProp = {
	    center:new google.maps.LatLng(49.863393, 8.554789),
	    zoom:19,
	    mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	  map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
	  directionsService = new google.maps.DirectionsService();
      directionsDisplay = new google.maps.DirectionsRenderer();
      directionsDisplay.setMap(map);
      
      
	}



