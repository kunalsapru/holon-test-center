function startDynamicHolon(holonObjectId) {
	alert("Dynamic Holon ID --> "+holonObjectId);
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