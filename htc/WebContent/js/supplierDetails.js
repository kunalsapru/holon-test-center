/**
 * 
 */
$(document).ready(function() {
	$("#cancelShowSupply").click(function(event){
		closeDiv("supplierDetailsBody");
	});	
})

function showSupplierDetails(holonObjectId) {
	var dataAttributes= {
			holonObjectId : holonObjectId
	}
	ajaxRequest("getDataForSupplierDetails", dataAttributes, getDataForSupplierDetailsCallBack, dataAttributes);
}

function getDataForSupplierDetailsCallBack(data,options) {
	var tableStr="";
	$("#pwSuppTitle").text("Power Supply Details for Holon Object Id: "+options.holonObjectId);
	tableStr = tableStr.concat("<tr><td colspan='4' style='text-align: center;width: 33%;'>No Power is being supplied.</td></tr>");
	$("#suppLierDetailList").html(tableStr);
	closeDiv('consumptionGraphBody');
	openDiv("supplierDetailsBody");
}

function sendMessageToAllProducers(holonObjectId, currentEnergyRequired) {
	var dataAttributes= {
			holonObjectId : holonObjectId,
			currentEnergyRequired : currentEnergyRequired
	}
	ajaxRequest("sendMessageToAllProducers", dataAttributes, sendMessageToAllProducersCallBack, {});
}

function sendMessageToAllProducersCallBack(data, options) {
	alert(data);
}

function checkInbox(holonObjectId) {
	var dataAttributes= {
			holonObjectId : holonObjectId
	}
	ajaxRequest("checkInbox", dataAttributes, checkInboxCallBack, {});
}

function checkInboxCallBack(data, options) {
	alert(data);
}