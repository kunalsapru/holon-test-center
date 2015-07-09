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

function createPowerSwitch(switchPositionLat,switchPositionLng,powerLineId){
	var dataAttributes= {
			switchPositionLat : switchPositionLat,
			switchPositionLng : switchPositionLng,
			powerLineId : powerLineId
	}
	ajaxRequest("createPowerSwitch", dataAttributes, createPowerSwitchCallBack, {});
}

function createPowerSwitchCallBack(data,options)
{
	console.log("Switch Added in Database Succesfully");
	
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
		var marker = new google.maps.Marker({
	        position: new google.maps.LatLng(switchLat, switchLong),
	        draggable: false,
	        icon:"css/images/on.png",
	        map: map
	    });	
		var contentString="Power Line Id : "+powerLineId+"</br>"+"Switch Id: "+switchId+"</br></br></br>"+" <input type='button' value='Toggle Switch' id='togglePowerSwitch' onclick='toggleSwitch("+marker+")' />";
		 attachMessage(contentString, marker, new google.maps.LatLng(switchLat, switchLong),"switch",switchId);
		
		}
	console.log("Done"+data);
}