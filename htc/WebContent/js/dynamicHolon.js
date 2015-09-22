var intervalFlag = 0;
var myDynamicTimer;
var timeInMilliSeconds = 15000;
function startDynamicHolon(currentEnergyRequired, holonObjectId) {
	if(currentEnergyRequired > 0) {
		myDynamicTimer = setInterval(function () {checkTimerDynamicHolon(holonObjectId)}, timeInMilliSeconds);
	} else {
		swal("Cannot Start Dynamic Holon!", "Current energy required must be greater than zero for this module to work.", "info");
	}
}

function checkTimerDynamicHolon(holonObjectId) {
	var dataAttributes= {
			holonObjectId : holonObjectId
	}
	ajaxRequest("checkDynamicCurrentEnergyRequired", dataAttributes, checkDynamicCurrentEnergyRequiredCallBack, dataAttributes);
}

function checkDynamicCurrentEnergyRequiredCallBack(data, options) {
	intervalFlag++;
	console.log("Call number --> "+intervalFlag);
	var holonObjectId = options["holonObjectId"];
	var dynamicCurrentEnergyRequired = data.split("~")[0];
	var originalEnergyRequiredAfterCurrentProduction = data.split("~")[1];
	var dataAttributes= {
			holonObjectId : holonObjectId,
			dynamicCurrentEnergyRequired : dynamicCurrentEnergyRequired,
			originalEnergyRequiredAfterCurrentProduction : originalEnergyRequiredAfterCurrentProduction
	}
	if(dynamicCurrentEnergyRequired > 0 && intervalFlag <= 5) {
		ajaxRequest("sendMessageToAllProducers", options, sendMessageToAllProducersCallBack, options);
	} else if(intervalFlag == 6) {
		intervalFlag = 0;//Re-initializing interval timer for new requests.
		clearTimeout(myDynamicTimer);
		//Code to start dynamic holon merger
		swal({
			title: "Proceed with finding a new holon for this holon object?",
			text: "This holon object could not find a supplier after 5 attempts. Do you want to find a new holon for this holon object?",
			type: "warning",
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Yes, find a new holon!',
			cancelButtonText: "No, don't do anything!",
			closeOnConfirm: false,
			closeOnCancel: false
		},
		function(isConfirm) {
		    if (isConfirm) {
		    	ajaxRequest("startDynamicHolonMerger", dataAttributes, startDynamicHolonMergerCallBack, dataAttributes);	
			} else {
		      swal("Cancelled", "Dynamic Holon merger has been cancelled.", "info");
		    }
		});
	} else {
		clearTimeout(myDynamicTimer);
		swal("Energy supplied!", "Either, required energy has been provided to the holon object or timer has expired.", "info");
	}
}

function startDynamicHolonMergerCallBack(data, options) {
	if(data == "false") {
		swal("Cannot find a new holon!", "Could not find another holon with sufficient energy requirements.", "info");
	} else {
		var startDynamicHolonMergerResponse = data.split("!");
		var oldCoordinatorId = startDynamicHolonMergerResponse[0];
		var newHolonColor = startDynamicHolonMergerResponse[1];
		var holonObjectId = startDynamicHolonMergerResponse[2];
		if(oldCoordinatorId > 0) {
			removeIconFromMap(oldCoordinatorId);
		}
		if(holonObjectId > 0) {
			var holonObject= globalHoList.get(holonObjectId);
			holonObject.setOptions({strokeColor:newHolonColor,fillColor:newHolonColor});
			swal("New holon assigned successfully!", "This holon object has been succesfully assigned to a new holon with sufficient energy.", "info");			
		}
	}
}

function dissolveHolon(currentEnergyRequirementHolon, holonCoordinatorId) {
	if(currentEnergyRequirementHolon > 0) {
		var dataAttributes= {
				holonCoordinatorId : holonCoordinatorId
		}
		ajaxRequest("dissolveHolon", dataAttributes, dissolveHolonCallBack, dataAttributes);
	} else {
		swal("Cannot dissolve!", "This option works only when flexibility of Holon is zero and current energy requirement is greater than zero.", "info");
	}
}

function dissolveHolonCallBack(data, options) {
	if(data == "false") {
		swal("Server response:Cannot dissolve!", "Either flexibility is greater than zero or current energy requirement is zero.", "info");
	} else if(data == "noOtherHolonFound") {
		swal("Server response:Cannot dissolve!", "No other holon found with sufficient energy requirements.", "info");
	} else {
		var oldCoordinatorId = data.split("*")[0].split("!")[0];
		var newHolonColor = data.split("*")[0].split("!")[1];
		var holonObjectsList;
		removeIconFromMap(oldCoordinatorId);
		if(data.split("*")[1] != undefined) {
			holonObjectsList = data.split("*")[1].split("~");
		}
		if(holonObjectsList.length > 0) {
			for(var i=0;i< holonObjectsList.length-1; i++){
				var holonObjectId = holonObjectsList[i];
				var holonObject= globalHoList.get(holonObjectId);
				holonObject.setOptions({strokeColor:newHolonColor,fillColor:newHolonColor});
			}
		}
		swal("Holons dissolved successfully!", "Holons have been dissolved successfully. Check the coordinator details for more information", "info");
	}
}

function dontDissolveHolon() {
	swal("Cannot dissolve!", "This option works only when flexibility of Holon is zero and current energy requirement is greater than zero", "info");
}