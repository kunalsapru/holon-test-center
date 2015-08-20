function areHolonObjectsConnected(firstHolonObject,secondHolonObject)
{
	var dataAttributes= {
			firstHolonObject : firstHolonObject,
			secondHolonObject : secondHolonObject
			};
	var options ={
			firstHolonObject : firstHolonObject,
			secondHolonObject : secondHolonObject
	};
	ajaxRequest("getConnectedStatusForHolons", dataAttributes, getConnectedStatusForHolonsCallback, options);
	
}

function getConnectedStatusForHolonsCallback(data,options) {
	areConnectedMode=false;
	var value="";
	if(data=="Success"){
		value="Yes";
	}else{
		value="No";
	}
	swal({
		title : "Connected::"+value,
		type : "info",
		confirmButtonText : "Okay"
	});
	$("#areConnected").css("background-color", "rgb(26, 26, 26)");
	areConnectedMode=false;
	isConnectedFirst="";
	isConnectedSecond="";
}