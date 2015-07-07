function showHolonElements(holonObjectId) {
	var dataAttributes= {
			holonObjectId : holonObjectId
	}
	ajaxRequest("showHolonElements", dataAttributes, showHolonElementsCallBack, dataAttributes);
}

function showHolonElementsCallBack(data, options) {
	var holonObjectId = options['holonObjectId'];
	$("#addHolonElementTableHeader").click(function() {
		$("#hiddenHolonObjectId").val(holonObjectId);
		getListHolonElementType();
		getListHolonElementState();
		openDiv('elementInfo');
	});
	$("#saveElementInfo").click(function(){
		  addHolonElement();
	  });
	$("#holonElementsListBody").html(data);
	openDiv("divHolonElementsDetail");
}

function deleteHolonElement(holonElementId, holonObjectId) {
	var dataAttributes= {
			holonElementId : holonElementId
	}
	var options = {
			holonObjectId : holonObjectId
	}
	if(confirm("Are you sure?")) {
		ajaxRequest("deleteHolonElement", dataAttributes, deleteHolonElementCallBack, options);			
	}
}

function deleteHolonElementCallBack(data, options) {
	if(data == "true"){
		var holonObjectId = options['holonObjectId'];
		showHolonElements(holonObjectId);
	} else {
		alert("Error in deleting Holon Element. Please check application logs.")
	}
}

function getListHolonElementType() {
	ajaxRequest("getListHolonElementType", {}, getListHolonElementTypeCallBack, {});
}

function getListHolonElementTypeCallBack(data,options) {
	var listHolonElementTypeMaster= data.split("*");
	$("#holonElementType").empty();
	for(var i=0;i<listHolonElementTypeMaster.length-1;i++)
		{
		var options= "<option value="+listHolonElementTypeMaster[i].split("-")[0]+"id="+listHolonElementTypeMaster[i].split("-")[0]+">"+listHolonElementTypeMaster[i].split("-")[1]+"</option>";
		$("#holonElementType").append(options);
		}
}

function getListHolonElementState()
{
	ajaxRequest("getListHolonElementState", {}, getListHolonElementStateCallBack, {});
}

function getListHolonElementStateCallBack(data,options) {
	var listHolonElementState= data.split("*");
	$("#holonElementState").empty();
	for(var i=0;i<listHolonElementState.length-1;i++) {
		var options= "<option value="+listHolonElementState[i].split("-")[0]+" id= "+listHolonElementState[i].split("-")[0]+">"+listHolonElementState[i].split("-")[1]+"</option>";
		$("#holonElementState").append(options);
	}
}

function addHolonElement(){
	var holonElementTypeId=$("#holonElementType option:selected").val();
	var holonElementStateId=$("#holonElementState option:selected").val();
	var currentCapacity=$("#currentCapacity").val();
	var usage=$("#usage").val();

	var holonObjectId = $("#hiddenHolonObjectId").val();
	var holonElementActionState = $("#holonElementActionState").val();
	var holonElementId = $("#hiddenHolonElementId").val();
	var dataAttributes = {
			holonElementTypeId : holonElementTypeId,
			holonElementStateId : holonElementStateId,
			currentCapacity : currentCapacity,
			usage : usage,
			holonObjectId : holonObjectId,
			holonElementId : holonElementId
		};
	if(holonElementActionState == "Edit") {
		ajaxRequest("editHolonElement", dataAttributes, editHolonElementCallBack, dataAttributes);
	} else {
		ajaxRequest("createHolonElement", dataAttributes, addHolonElementCallBack, dataAttributes);
	}
}

function addHolonElementCallBack(data, options) {
	if(data == "true"){
		var holonObjectId = options['holonObjectId'];
		closeDiv('elementInfo');
		showHolonElements(holonObjectId);
	} else {
		alert("Error in adding Holon Element. Please check application logs.")
	}
}

function editHolonElementCallBack(data, options) {
	if(data == "true"){
		var holonObjectId = options['holonObjectId'];
		closeDiv('elementInfo');
		showHolonElements(holonObjectId);
	} else {
		alert("Error in editing Holon Element. Please check application logs.")
	}
}

function editHolonElement(holonElementId, holonObjectId) {
	$("#hiddenHolonObjectId").val(holonObjectId);
	$("#hiddenHolonElementId").val(holonElementId);
	$("#holonElementActionState").val("Edit");
	getListHolonElementType();
	getListHolonElementState();
	openDiv('elementInfo');
}
