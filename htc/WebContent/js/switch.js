function drawSwitchOnPowerLine(clickedValue){
	clickedToDrawSwitch=clickedValue;
	if ($("#"+clickedValue).css("background-color") == "rgb(26, 26, 26)") {
		$("#"+clickedValue).css("background-color", "rgb(153,153,0)");
	}
	else
	{
		$("#"+clickedValue).css("background-color", "rgb(26, 26, 26)");		
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
	ajaxRequest("createPowerSwitch", dataAttributes, createPowerSwitchCallBack, {powerLineId : powerLineId,circleSwitch:circleSwitch});
}

function createPowerSwitchCallBack(data,options)
{
	var powerSwitchId=data;	
	var powerLineId = options["powerLineId"];
	var circleSwitch = options["circleSwitch"]; 
	var contentString="Power Line Id : "+powerLineId+"</br>"+"Switch Id: "+powerSwitchId+"</br></br></br>"+" <input type='button' value='Toggle Switch' id='togglePowerSwitch' />";
	addSwitchInfo(contentString, circleSwitch, powerSwitchId);
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
		var switchId=individualData[2];
		var powerLineId=individualData[3];
		var circleSwitch = new google.maps.Circle({
			 strokeColor: '#FF0000',
		     strokeOpacity: 1,
		     strokeWeight: 8,
		     fillColor: '#FF0000',
		     fillOpacity: 0.35,
		     map: map,
		     center: new google.maps.LatLng(switchLat, switchLong),
		     radius: 2
		    });	
		var contentString="Power Line Id : "+powerLineId+"</br>"+"Switch Id: "+switchId+"</br></br></br>"+" <input type='button' value='Toggle Switch' id='togglePowerSwitch' />";
		addSwitchInfo(contentString, circleSwitch, switchId);
		
		}
	console.log("Done"+data);
}

function addSwitchInfo(contentString, marker, id)
{
	 var infowindowHolonObject = new google.maps.InfoWindow({
	      content: contentString		    
	  });
   google.maps.event.addListener(marker, 'click', function(event) {   	
		infowindowHolonObject.setOptions({position:event.latLng});
		infowindowHolonObject.open(map,marker);	
		$('#togglePowerSwitch').click(function() {
			SwitchOnOff(marker);			
		})
		
   });
}

function SwitchOnOff(marker)
{	 

	var switchStatusColor = marker.get('fillColor');
	if(switchStatusColor=="#FF0000")
		{
		//alert("Color Red");
		marker.setOptions({strokeColor:'#0B6121', fillColor: '#0B6121'});
		
		}else
		{
			//alert("Color Green");
			marker.setOptions({strokeColor:'#FF0000',fillColor: '#FF0000'});
		}
	

	
	
}

