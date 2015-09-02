/**
 * 
 */
$(document).ready(function() {
	$("#closeShowSupply").click(function(event){
		closeDiv("supplierDetailsBody");
	});	
	$("#closeProducerInbox").click(function(event){
		closeDiv("producerInboxId");
	});
	$("#takeActionProducerInbox").click(function(event){
		closeDiv("producerInboxId");
		takeActionProducerInbox();
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

function sendMessageToAllProducers(holonObjectId) {
	var dataAttributes= {
			holonObjectId : holonObjectId
	}
	ajaxRequest("sendMessageToAllProducers", dataAttributes, sendMessageToAllProducersCallBack, {});
}

function sendMessageToAllProducersCallBack(data, options) {
	swal("Message sent", "Message has been sent to all connected producers", "info");
}

function checkInbox(holonObjectId) {
	var dataAttributes= {
			holonObjectId : holonObjectId
	}
	ajaxRequest("checkInbox", dataAttributes, checkInboxCallBack, dataAttributes);
}

function takeActionProducerInbox() {
	var holonObjectId = $("#hiddenSupplierProducerId").val();
	var takeAction = "yes"; 
	var dataAttributes= {
			holonObjectId : holonObjectId,
			takeAction : takeAction
	}
	ajaxRequest("checkInbox", dataAttributes, checkInboxCallBack, dataAttributes);

}

function checkInboxCallBack(data, options) {
	var supplierProducerHolonObjectId = options["holonObjectId"];
	var dataAttributes= {
			  holonObjectId : supplierProducerHolonObjectId
			};
	ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});
	$("#hiddenSupplierProducerId").val(supplierProducerHolonObjectId);
	var inboxRow = "";
	if(data != "") {
		inboxRow = data.split("!");
	}
	var contentString = "<tr><td colspan='6' style='text-decoration: underline;'>Messages Received</td></tr>"+
		"<tr>"+
		"<td>Requestor ID</td>"+
		"<td>Requestor Type (Priority)</td>"+
		"<td>Power Requested</td>"+
		"<td>Power Granted</td>"+
		"<td>Message Status</td>"+
		"<td>Connected</td>"+
		"<tr>";
	var inboxColumn = "";
	var requestorId = "N/A";
	var requestorTypePriority = "N/A";
	var powerRequested = "N/A";
	var powerGranted = "N/A";
	var messageStatus = "N/A";
	var isConnected = "N/A";
	
	for(var i = 0; i<inboxRow.length;i++) {
		if(inboxRow != "") {
			inboxColumn = inboxRow[i].split("~");
		}
		if(inboxColumn != "") {
			requestorId = inboxColumn[0];
			requestorTypePriority = inboxColumn[1];
			powerRequested = inboxColumn[2];
			powerGranted = inboxColumn[3];
			messageStatus = inboxColumn[4];
			isConnected = inboxColumn[5];
		}
		contentString = contentString.concat("<tr><td>"+requestorId+"</td><td>"+requestorTypePriority+"</td><td>"+
				powerRequested+"</td><td>"+powerGranted+"</td><td>"+messageStatus+"</td><td>"+isConnected+"</td><td></td></tr>");
	}
	$("#producerInboxList").html(contentString);
	openDiv("producerInboxId");
}