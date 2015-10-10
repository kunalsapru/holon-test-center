/**
 * This javascript file is used to connect Power source to Line.
 */
$(document).ready(function() {
	
	// Click event for Connect Power Source to  Main Power Line
	$('#connectPowerSource').click(function(evt) {
		if (addPowerSourceToLineMode==false){
			addPowerSourceToLineMode=true;
			$(this).css("background-color", "rgb(153,153,0)");
		} else {
			$(this).css("background-color", "rgb(26, 26, 26)");
			addPowerSourceToLineMode=false;
		}
	})

	// Click event for Create Disaster Mode in the left menu.
	$("#disasterMode").click(function(){
		disasterModeSelected();
	});
	
	//Click event for Remove Selected Disaster item on the left menu
	$("#removeSelectedDisaster").click(function(){
		deleteSelectedDisaster();
	});
	
	//Click event for Remove All Disasters item on the left menu.
	$("#removeAllDisaster").click(function(){
		deleteAllDisaster();
	});
	
})

