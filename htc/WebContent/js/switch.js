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
	ajaxRequest("createPowerSwitch", dataAttributes, createPowerSwitchCallBack, {circleSwitch:circleSwitch});
}

function createPowerSwitchCallBack(data,options)
{
	var powerSwitchId=data.trim();	
	var circleSwitch = options["circleSwitch"]; 
	addSwitchInfo(circleSwitch, powerSwitchId);
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
		var powerLineId=individualData[3];
		var status=individualData[4];
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
	var individualData= data.split("^");
	var switchLat=individualData[0].replace("[","").replace(",","");
	var switchLong=individualData[1];
	var powerSwitchId=individualData[2].trim();
	var powerLineId=individualData[3];
	var status=individualData[4];
	var contentString="Power Line Id : "+powerLineId+"</br>"+"Switch Id: "+powerSwitchId+"</br></br></br>"+
						" <input type='button' value='Toggle Switch' id='togglePowerSwitch' />";;
	var position=options["position"];
	var circleSwitch=options["circleSwitch"];
	var infowindowHolonObject = new google.maps.InfoWindow({
	      content: contentString		    
	  });
	 	infowindowHolonObject.setOptions({position:position});
		infowindowHolonObject.open(map,circleSwitch);	
		$('#togglePowerSwitch').click(function() {
			SwitchOnOff(circleSwitch,powerSwitchId);			
		})
		
	
}


function SwitchOnOff(marker,switchId)
{	 

	var switchStatusColor = marker.get('fillColor');
	var dataAttributes;
	if(switchStatusColor=="#FF0000")
		{
		dataAttributes = {
    			newSwitchStatus :0,
    			switchId:switchId,
    			};			
		}else
		{
			dataAttributes = {
					newSwitchStatus :1,
					switchId:switchId,
	    			};
		}
		ajaxRequest("powerSwitchOnOff", dataAttributes, powerSwitchOnOffCallBack,{marker:marker});	
	
}

function powerSwitchOnOffCallBack(data,options){	
	var marker = options["marker"];
	var newSwitchStatus = data;
	
	if(newSwitchStatus== 1)
		{
		marker.setOptions({strokeColor:'#FF0000',fillColor: '#FF0000'});
		}
	else{
		marker.setOptions({strokeColor:'#0B6121', fillColor: '#0B6121'});
			}
}


