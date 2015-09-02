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
	var contentString = "<tr><td colspan='7' style='text-decoration: underline;'>Power Supply Details for Holon Object Id: "+options.holonObjectId+"</td></tr>"+
	"<tr>"+
		"<td>Request#ID</td>"+
		"<td>Type of Supplier</td>"+
		"<td>Id of Supplier</td>"+
		"<td>Power Requested</td>"+
		"<td>Power Granted</td>"+
		"<td>Message Status</td>"+
		"<td>Connected</td>"+
	"<tr>";
	var supplierRow = data.split("!");
	var supplierColumn = "";
	var requestId = "N/A";
	var typeSupplier = "N/A";
	var idSupplier = "N/A";
	var powerRequested = "N/A";
	var powerGranted = "N/A";
	var messageStatus = "N/A";
	var isConnected = "N/A";
	for(var i = 0; i<supplierRow.length;i++) {
		if(supplierRow != "") {
			supplierColumn = supplierRow[i].split("~");
		}
		if(supplierColumn != "") {
			typeSupplier = supplierColumn[0];
			idSupplier = supplierColumn[1];
			powerRequested = supplierColumn[2];
			powerGranted = supplierColumn[3];
			messageStatus = supplierColumn[4]
			isConnected = supplierColumn[5];
			requestId = supplierColumn[6];
		}
		contentString = contentString.concat("<tr><td>"+requestId+"</td><td>"+typeSupplier+"</td><td>"+idSupplier+"</td><td>"+
				powerRequested+"</td><td>"+powerGranted+"</td><td>"+messageStatus+"</td><td>"+isConnected+"</td></tr>");
	}
	if(contentString == "") {
		contentString = "<tr><td colspan='7' style='text-align: center;'>No Power is being supplied.</td></tr>";
	}
	$("#suppLierDetailList").html(contentString);
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
	if(data == "SUCCESS") {
		swal("Message sent", "Message has been sent to all connected producers", "info");
	} else if (data == "FAILURE") {
		swal("Cannot communicate!", "Message has not been sent. Set the 'Can Communicate' field to 'Yes' to enable communication.", "info");
	}
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
	var contentString = "<tr><td colspan='7' style='text-decoration: underline;'>Messages Received</td></tr>"+
		"<tr>"+
		"<td>Request#ID</td>"+
		"<td>Consumer ID</td>"+
		"<td>Requestor Type (Priority)</td>"+
		"<td>Power Requested</td>"+
		"<td>Power Granted</td>"+
		"<td>Message Status</td>"+
		"<td>Connected</td>"+
		"<tr>";
	var inboxColumn = "";
	var consumerId = "N/A";
	var requestorTypePriority = "N/A";
	var powerRequested = "N/A";
	var powerGranted = "N/A";
	var messageStatus = "N/A";
	var isConnected = "N/A";
	var requestId = "N/A";
	var canCommunicate = "SUCCESS";
	
	for(var i = 0; i<inboxRow.length;i++) {
		if(inboxRow != "") {
			inboxColumn = inboxRow[i].split("~");
		}
		if(inboxColumn != "") {
			consumerId = inboxColumn[0];
			requestorTypePriority = inboxColumn[1];
			powerRequested = inboxColumn[2];
			powerGranted = inboxColumn[3];
			messageStatus = inboxColumn[4];
			isConnected = inboxColumn[5];
			requestId = inboxColumn[6];
			canCommunicate = inboxColumn[7];
		}
		contentString = contentString.concat("<tr><td>"+requestId+"</td><td>"+consumerId+"</td><td>"+requestorTypePriority+"</td><td>"+
				powerRequested+"</td><td>"+powerGranted+"</td><td>"+messageStatus+"</td><td>"+isConnected+"</td></tr>");
	}
	if(inboxColumn == "") {
		contentString = contentString.concat("<tr><td>"+consumerId+"</td><td>"+requestorTypePriority+"</td><td>"+
				powerRequested+"</td><td>"+powerGranted+"</td><td>"+messageStatus+"</td><td>"+isConnected+"</td></tr>");
	}
	$("#producerInboxList").html(contentString);
	openDiv("producerInboxId");
	if(canCommunicate == "FAILURE") {
		swal("Cannot communicate!", "Necessary action can not be taken. Set the 'Can Communicate' field to 'Yes' to continue" +
				" taking actions on pending requests.", "info");
	}
}