/**
 * Abhinav
 */
$(document).ready(function() {

	$('#connectPowerSource').click(function(evt) {
		if (addPowerSourceToLineMode==false){
			addPowerSourceToLineMode=true;
			$(this).css("background-color", "rgb(153,153,0)");
		} else {
			$(this).css("background-color", "rgb(26, 26, 26)");
			addPowerSourceToLineMode=false;
		}
	})

	$("#disasterMode").click(function(){
		
		/*swal({
			title: "Are you sure?",
			text: "Do you really want to create the Disaster Mode?",
			type: "warning",
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Yes,create it!',
			cancelButtonText: "No, Don't create it!",
			closeOnConfirm: false,
			closeOnCancel: false
		},
		function(isConfirm){
	    if (isConfirm){
	    	disasterModeSelected();
	    } else {
	      swal("Cancelled", "Disaster Mode not created", "info");
	      disasterModeSelected();
	    }
		});
*/
		disasterModeSelected();
	});
	
})

