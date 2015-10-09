function resetDatabase() {
	swal({
		title: "Proceed with resetting entire database?",
		text: "This action will reset the entire database. Do you want to continue?",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: '#DD6B55',
		confirmButtonText: 'Yes, reset database!',
		cancelButtonText: "No, don't do anything!",
		closeOnConfirm: true,
		closeOnCancel: false
	},
	function(isConfirm) {
	    if (isConfirm) {
	    	ajaxRequest("resetDatabase", {}, resetDatabaseCallBack, {});	
		} else {
			swal("Cancelled", "Reset Database operation has been cancelled.", "info");
	    }
	});
}

function resetDatabaseCallBack(data, options) {
	if(data == "true") {
    	initialize(); // Function in googleMaps.js
		loadHolon = true; // Global variable
		swal("Database Reset!", "Database has been successfully reset to initial state and map has been cleared.", "info");
	} else if(data == "resetAlreadyCompleted") {
		swal("Database Already Cleaned!", "Database has already been successfully reset to initial state.", "info");
	} else {
		swal("Database Reset Error!", data, "info");
	}
}