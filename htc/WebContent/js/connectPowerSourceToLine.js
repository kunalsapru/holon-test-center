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


})

