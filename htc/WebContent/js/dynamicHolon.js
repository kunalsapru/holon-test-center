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
	} else {
		var holonObjectId = options["holonCoordinatorId"];
		swal("dissolveHolon Action response for "+holonObjectId+"!", data, "info");
	}
}

function dontDissolveHolon() {
	swal("Cannot dissolve!", "This option works only when flexibility of Holon is zero and current energy requirement is greater than zero", "info");
}