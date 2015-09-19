function startDynamicHolon(holonObjectId) {
	alert("Dynamic Holon ID --> "+holonObjectId);
}

function dissolveHolon(currentEnergyRequirementHolon, holonCoordinatorId) {
	if(currentEnergyRequirementHolon > 0) {
		alert("Dissolve Holon Coordinator ID --> "+holonCoordinatorId);		
	} else {
		swal("Cannot dissolve!", "This option works only when flexibility of Holon is zero and current energy requirement is greater than zero", "info");
	}
}

function dontDissolveHolon() {
	swal("Cannot dissolve!", "This option works only when flexibility of Holon is zero and current energy requirement is greater than zero", "info");
}