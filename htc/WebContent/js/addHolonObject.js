/**
 * Java Script file for adding new holon objects
 */

$(document).ready(function() {	
		var drawingHolonManager;
		var markerslat = [];
		var markerslng = [];
		var infowindowArray = [];
		var infowindow = "";
	$('#addHolonObject').click(function(){
				//alert($(this).css("background-color") );
		if ($(this).css("background-color") == "rgb(233, 233, 233)") {
			$(this).css("background-color", "rgb(153,255,255)");
	
		$("#displayHolonDetails").hide();

	//	To check If the layout is already present ;
		
		if (drawingHolonManager== null){
			//Creates a new drawing manager object for first time
			
			drawingHolonManager = new google.maps.drawing.DrawingManager({
    	    drawingMode:null,
    	    drawingControl: true,
    	    drawingControlOptions: {
    	      position: google.maps.ControlPosition.TOP_CENTER,
    	      drawingModes: [google.maps.drawing.OverlayType.RECTANGLE,google.maps.drawing.OverlayType.POLYLINE]
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
		drawingHolonManager.setMap(map);
     // Event when the overlay is complete 
      google.maps.event.addListener(drawingHolonManager, 'overlaycomplete', function(event) {
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
	 
		}
		else
		{
			$(this).css("background-color", "rgb(233,233,233)");		
		
		}		
		
	
	});
	
	
	
	
})