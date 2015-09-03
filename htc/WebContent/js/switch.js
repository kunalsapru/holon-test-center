
$(document).ready(function() {	
	
	$("#switchOnPowerLine").click(function(event){		
		if (addSwitchonPowerLineMode==false) {
			$(this).css("background-color", "rgb(153,153,0)");
			addSwitchonPowerLineMode=true;
		}
		else
		{
			$(this).css("background-color", "rgb(26, 26, 26)");	
			addSwitchonPowerLineMode=false;
		}
	});
	
})



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
		var switchStatus="#FF0000";		
		if(status==1){
			switchStatus="#0B6121";
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
	closeOtherInfoWindows();
	var individualData= data.split("^");
	var switchLat=individualData[0].replace("[","").replace(",","");
	var switchLong=individualData[1];
	var powerSwitchId=individualData[2].trim();
	var powerLineAId=individualData[3];
	var powerLineBId=individualData[4];
	var status=individualData[5].trim();
	var switchStatus="Off";
	var btnText="Switch On";
	if(status=="1")
		{
		switchStatus="On";
		btnText="Switch Off";
		}
		var contentString= "<div id='contentId' class='table'><table>"+
							"<tr><td colspan='2' style='text-decoration: underline;'>Switch Details</td></tr>" +
							"<tr><td><b>Switch Id: </b>"+powerSwitchId +"</td>"+
							"<td><b>Switch Status: </b>"+switchStatus+"</td></tr>"+
							"<tr><td><b>Connected Power Line A: </b>"+powerLineAId+"</td>"+
							"<td><b>Connected Power Line B : </b>"+powerLineBId+"</td></tr>"+
							"<tr><td colspan='2' style='text-align: center;'>" +
							"<span class='button' id='togglePowerSwitch'><i class='fa fa-circle-o-notch'></i>&nbsp;&nbsp;Turn "+btnText+"</span></td></tr></table></div>";

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

		var dataAttributes = {    			
    			powerSwitchId:powerSwitchId,
    			};	
		options = {
				circleSwitch:circleSwitch,
				infowindowHolonObject:infowindowHolonObject,
				powerSwitchId:powerSwitchId,
    			};
	
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
/*	var hk1=data.split("*")[1];
	var hk2= data.split("*")[2];*/
	
	if(newSwitchStatus== 1)
		{
		//alert("Abhinav");
		circleSwitch.setOptions({strokeColor:'#0B6121',fillColor: '#0B6121'});
		var newContent=content.replace("<b>Switch Status: </b>Off","<b>Switch Status: </b>On").replace("Switch On","Switch Off");
		//alert("newSwitchStatus "+newSwitchStatus+" "+newContent);
		infowindowHolonObject.setContent(newContent);
		infowindowHolonObject.close();	
	
		}
	else{
		//alert("Abhinava");
		circleSwitch.setOptions({strokeColor:'#FF0000', fillColor: '#FF0000'});
		var newContent=content.replace("<b>Switch Status: </b>On","<b>Switch Status: </b>Off").replace("Switch Off","Switch On");
		infowindowHolonObject.setContent(newContent);
		infowindowHolonObject.close();		
		}
	
	/*if(hk1 != undefined ){
		var holonObject = globalHoList.get(hk1);
		var coordinatorLocation=holonObject.getBounds().getNorthEast();
		//Returns marker of the image
		var coordinatorIcon=createCoIcon(coordinatorLocation);
		globalHKList.set(hk1,coordinatorIcon);
		
	}
	
	if(hk2 != undefined ){
		var holonObject = globalHoList.get(hk2);
		var coordinatorLocation=holonObject.getBounds().getNorthEast();
		//Returns marker of the image
		var coordinatorIcon=createCoIcon(coordinatorLocation);
		globalHKList.set(hk2,coordinatorIcon);
		
		
	}*/
	infowindowHolonObject.open(map,circleSwitch);
	$('#togglePowerSwitch').click(function() {
		SwitchOnOff(circleSwitch,powerSwitchId,infowindowHolonObject);			
	})
	showHolonCoIcons();
}


