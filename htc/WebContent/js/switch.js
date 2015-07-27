function drawSwitchOnPowerLine(clickedValue){
	clickedToDrawSwitch=clickedValue;
	if ($("#"+clickedValue).css("background-color") == "rgb(26, 26, 26)") {
		$("#"+clickedValue).css("background-color", "rgb(153,153,0)");
	}
	else
	{
		$("#"+clickedValue).css("background-color", "rgb(26, 26, 26)");	
		clickedToDrawSwitch="";
	}
}



function createPowerSwitch(latLng,powerLineId){
	var circleSwitch = new google.maps.Circle({
		 strokeColor: '#FF0000',
	     strokeOpacity: 1,
	     strokeWeight: 8,
	     fillColor: '#FF0000',
	     fillOpacity: 0.35,
	     map: map,
	     center: latLng,
	     radius: 2
	    });	
	var dataAttributes= {
			switchPositionLat : latLng.lat(),
			switchPositionLng : latLng.lng(),
			powerLineId : powerLineId
	};
	var options= {
			circleSwitch:circleSwitch,			
	};
	ajaxRequest("createPowerSwitch", dataAttributes, createPowerSwitchCallBack, options);
}

function createPowerSwitchCallBack(data,options)
{
	var respData=data.split("*");
	var powerSwitchId=respData[0].trim();	
	var powerLineAId=respData[1].trim();
	var powerLineBId=respData[2].trim();
	updateGlobalPowerLineList(powerLineAId,true);
	updateGlobalPowerLineList(powerLineBId,false);
	var circleSwitch = options["circleSwitch"]; 
	addSwitchInfo(circleSwitch, powerSwitchId);
	//globalPsList[powerSwitchId]=circleSwitch;
	globalPsList.set(powerSwitchId,circleSwitch);
}

function showSavedPowerSwitches(){
	
	ajaxRequest("getListPowerSwitch", {}, getListPowerSwitchCallBack, {});
}

function getListPowerSwitchCallBack(data,options){
	var powerSwitchList= data.split("*");
	for(var i=0;i<powerSwitchList.length-1;i++)
		{		
		var individualData= powerSwitchList[i].split("^");
		var switchLat=individualData[0].replace("[","").replace(",","");
		var switchLong=individualData[1];
		var powerSwitchId=individualData[2].trim();
		var status=individualData[3];
		var switchStatus="#0B6121";		
		if(status==1){
			switchStatus="#FF0000";
		}
		var circleSwitch = new google.maps.Circle({
			 strokeColor: switchStatus,
		     strokeOpacity: 1,
		     strokeWeight: 8,
		     fillColor: switchStatus,
		     fillOpacity: 0.35,
		     map: map,
		     center: new google.maps.LatLng(switchLat, switchLong),
		     radius: 2
		    });	
		addSwitchInfo(circleSwitch, powerSwitchId);
		//globalPsList[powerSwitchId]=circleSwitch;
		globalPsList.set(powerSwitchId,circleSwitch);
		
		}
	
}

function addSwitchInfo(circleSwitch, powerSwitchId)
{
	
   google.maps.event.addListener(circleSwitch, 'click', function(event) { 
	   
		var dataAttributes= {
				powerSwitchId : powerSwitchId,
				}
		var options= {
				position:event.latLng,
				circleSwitch:circleSwitch,
				}
		ajaxRequest("getSwitchInfo", dataAttributes, getSwitchInfoCallBack, options);		   
	   
   });
}

function getSwitchInfoCallBack(data, options)
{
	if(typeof currentSwitchInfoWindow != 'undefined' && currentSwitchInfoWindow != null)
	{
		currentSwitchInfoWindow.close();
	}
	if(typeof currentInfoWindowObject != 'undefined' &&currentInfoWindowObject != null)
	{
	currentInfoWindowObject.close();
	}
	if(typeof currentLineInfoWindowObject != 'undefined' &&currentLineInfoWindowObject != null)
	{
		currentLineInfoWindowObject.close();
	}
	var individualData= data.split("^");
	var switchLat=individualData[0].replace("[","").replace(",","");
	var switchLong=individualData[1];
	var powerSwitchId=individualData[2].trim();
	var powerLineAId=individualData[3];
	var powerLineBId=individualData[4];
	var status=individualData[5].trim();
	var switchStatus="Off";
	var contentString="<div id='contentId'>Switch Id: "+powerSwitchId+"</br>"+"Switch Status : "+switchStatus+"</br>"+"Connected Power Line A : "+powerLineAId+"</br>"+"Connected Power Line B : "+powerLineBId+"</br>"+"</br></br></br>"+
	" <input type='button' value='Switch On' id='togglePowerSwitch' /> </div>";
	if(status=="1")
		{
		switchStatus="On";
		contentString="<div id='contentId'>Switch Id: "+powerSwitchId+"</br>"+"Switch Status : "+switchStatus+"</br>"+"Connected Power Line A : "+powerLineAId+"</br>"+"Connected Power Line B : "+powerLineBId+"</br>"+"</br></br></br>"+
		" <input type='button' value='Switch Off' id='togglePowerSwitch' /></div>";
		}
	var position=options["position"];
	var circleSwitch=options["circleSwitch"];
	var infowindowHolonObject = new google.maps.InfoWindow({
	      content: contentString		    
	  });
	 	infowindowHolonObject.setOptions({position:position});
		infowindowHolonObject.open(map,circleSwitch);	
		$('#togglePowerSwitch').click(function() {
			SwitchOnOff(circleSwitch,powerSwitchId,infowindowHolonObject);			
		})
	currentSwitchInfoWindow=infowindowHolonObject;
	
}


function SwitchOnOff(circleSwitch,powerSwitchId,infowindowHolonObject)
{	 

	var switchStatusColor = circleSwitch.get('fillColor');
	var dataAttributes;
	if(switchStatusColor=="#FF0000")
		{
		dataAttributes = {
    			newSwitchStatus :0,
    			powerSwitchId:powerSwitchId,
    			};	
		options = {
				circleSwitch:circleSwitch,
				infowindowHolonObject:infowindowHolonObject,
				powerSwitchId:powerSwitchId,
    			};
		}else
		{
			dataAttributes = {
					newSwitchStatus :1,
					powerSwitchId:powerSwitchId,
	    			};
			options = {
					circleSwitch:circleSwitch,
					infowindowHolonObject:infowindowHolonObject,
					powerSwitchId:powerSwitchId,
	    			};
		}
		ajaxRequest("powerSwitchOnOff", dataAttributes, powerSwitchOnOffCallBack,options);
		//globalPsList[powerSwitchId]=circleSwitch;
		globalPsList.set(powerSwitchId,circleSwitch);
	
}

function powerSwitchOnOffCallBack(data,options){	
	var circleSwitch = options["circleSwitch"];
	var infowindowHolonObject = options["infowindowHolonObject"];
	var powerSwitchId = options["powerSwitchId"];
	var content = infowindowHolonObject.getContent();
	var newSwitchStatus = data;
	
	if(newSwitchStatus== 1)
		{
		//alert("Abhinav");
		circleSwitch.setOptions({strokeColor:'#FF0000',fillColor: '#FF0000'});
		var newContent=content.replace("Switch Status : Off","Switch Status : On").replace("Switch On","Switch Off");
		//alert("newSwitchStatus "+newSwitchStatus+" "+newContent);
		infowindowHolonObject.setContent(newContent);
		infowindowHolonObject.close();	
	
		}
	else{
		//alert("Abhinava");
		circleSwitch.setOptions({strokeColor:'#0B6121', fillColor: '#0B6121'});
		var newContent=content.replace("Switch Status : On","Switch Status : Off").replace("Switch Off","Switch On");
		infowindowHolonObject.setContent(newContent);
		infowindowHolonObject.close();		
		}
	infowindowHolonObject.open(map,circleSwitch);
	$('#togglePowerSwitch').click(function() {
		SwitchOnOff(circleSwitch,powerSwitchId,infowindowHolonObject);			
	})
}


