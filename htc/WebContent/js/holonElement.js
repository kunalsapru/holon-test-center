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
		$("#holonElementActionState").val("Add");
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
	swal({
		title: "Are you sure?",
		text: "Do you really want to remove the Holon Element?",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: '#DD6B55',
		confirmButtonText: 'Yes, remove!',
		cancelButtonText: "No, Don't remove!",
		closeOnConfirm: false,
		closeOnCancel: false
	},
	function(isConfirm){
    if (isConfirm){
    	ajaxRequest("deleteHolonElement", dataAttributes, deleteHolonElementCallBack, options);	
    	 swal("Removed", "Holon Element has been removed", "info");
    	} else
    	{
      swal("Cancelled", "Map has not been cleared", "info");
    }
	});
	
}

function deleteHolonElementCallBack(data, options) {
	if(data == "true"){
		var holonObjectId = options['holonObjectId'];
		showHolonElements(holonObjectId);
	} else {
		alert("Error in deleting Holon Element. Please check application logs.")
	}
}

function getListHolonElementType(holonElementTypeId) {
	var options={};
	if(typeof holonElementTypeId != undefined)
		{
		options={holonElementTypeId:holonElementTypeId};
		}
	
	
	ajaxRequest("getListHolonElementType", {}, getListHolonElementTypeCallBack, options);
}

function getListHolonElementTypeCallBack(data,options) {
	var holonElementTypeId =options['holonElementTypeId'];
	var listHolonElementTypeMaster= data.split("*");
	$("#holonElementType").empty();
	for(var i=0;i<listHolonElementTypeMaster.length-1;i++)
		{
		if((typeof holonElementTypeId != undefined)&&(holonElementTypeId==listHolonElementTypeMaster[i].split("-")[0])){
		var options= "<option value="+listHolonElementTypeMaster[i].split("-")[0]+"id="+listHolonElementTypeMaster[i].split("-")[0]+"selected>"+listHolonElementTypeMaster[i].split("-")[1]+"</option>";
		$("#holonElementType").append(options);
		}
		else
		{
			var options= "<option value="+listHolonElementTypeMaster[i].split("-")[0]+"id="+listHolonElementTypeMaster[i].split("-")[0]+">"+listHolonElementTypeMaster[i].split("-")[1]+"</option>";
			$("#holonElementType").append(options);
			}
}
}

function getListHolonElementState(state)
{
	var options={};
	if(typeof state != undefined)
		{
		options={state:state};
		}
	
	ajaxRequest("getListHolonElementState", {}, getListHolonElementStateCallBack,options);
}

function getListHolonElementStateCallBack(data,options) {
	var state =options['state'];
	var listHolonElementState= data.split("*");
	$("#holonElementState").empty();
	for(var i=0;i<listHolonElementState.length-1;i++) {
		if((typeof state != undefined)&&(state==listHolonElementState[i].split("-")[0])){
			var options= "<option value="+listHolonElementState[i].split("-")[0]+" id= "+listHolonElementState[i].split("-")[0]+"selected>"+listHolonElementState[i].split("-")[1]+"</option>";
			$("#holonElementState").append(options);
			}
			else
			{
				var options= "<option value="+listHolonElementState[i].split("-")[0]+" id= "+listHolonElementState[i].split("-")[0]+">"+listHolonElementState[i].split("-")[1]+"</option>";
				$("#holonElementState").append(options);
				}
	
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
	$("#holonElementActionState").val("");
	if(data == "true"){
		var holonObjectId = options['holonObjectId'];
		closeDiv('elementInfo');
		showHolonElements(holonObjectId);
	} else {
		alert("Error in editing Holon Element. Please check application logs.")
	}
}

function editHolonElement(holonElementId,holonElementTypeId,state,currentCapacity, holonObjectId) {
	alert("holonElementId "+holonElementId);
	$("#hiddenHolonObjectId").val(holonObjectId);
	$("#hiddenHolonElementId").val(holonElementId);
	$("#holonElementActionState").val("Edit");	
	$("#currentCapacity").val(currentCapacity);
	getListHolonElementType(holonElementTypeId);
	getListHolonElementState(state);
	openDiv('elementInfo');
}
