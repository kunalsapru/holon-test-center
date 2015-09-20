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
		$("#elmTitle").text("Add Holon Element");
		getListHolonElementType();
		getListHolonElementState();
		openDiv('elementInfo');
	});
	$("#saveElementInfo").click(function(event){
		//This code will prevent event from firing more than once
		if(event.handled !== true) {
		  event.handled = true;
		  addHolonElement();
		}
	  });
	$("#holonElementsListBody").html(data);
	if(data.indexOf("noData") >= 0){
		var currecntPC=globalPCList.get(holonObjectId.toString());
		if(currecntPC != undefined){
			//All items have been removed.
			currecntPC.setVisible(false);
		}
	}
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
		} else {
	      swal("Cancelled", "Holon Element has not been deleted.", "info");
	    }
	});
}

function deleteHolonElementCallBack(data, options) {
	var resp =data.split("*");
	if(resp[0] == "true"){
		var holonObjectId = options['holonObjectId'];
		showHolonElements(holonObjectId);
		swal("Holon Element Removed!", "Holon Element has been removed", "info");
		showHolonCoIcons();
	} else {
		swal("Server Error!", "Error in deleting holon element, please check applciation logs.", "info");
	}
}

function getListHolonElementType(holonElementTypeId) {
	var options={};
	if(typeof holonElementTypeId != "undefined") {
		options={holonElementTypeId:holonElementTypeId};
	}
	ajaxRequest("getListHolonElementType", {}, getListHolonElementTypeCallBack, options);
}

function getListHolonElementTypeCallBack(data,options) {
	var holonElementTypeId =options['holonElementTypeId'];
	var listHolonElementTypeMaster= data.split("*");
	$("#holonElementType").empty();
	for(var i=0;i<listHolonElementTypeMaster.length-1;i++) {
		if((typeof holonElementTypeId != "undefined")&&(holonElementTypeId==listHolonElementTypeMaster[i].split("-")[0])){
			var options= "<option value="+listHolonElementTypeMaster[i].split("-")[0]+"id="+listHolonElementTypeMaster[i].split("-")[0]+" selected>"+listHolonElementTypeMaster[i].split("-")[1]+"</option>";
			$("#holonElementType").append(options);
		} else {
			var options= "<option value="+listHolonElementTypeMaster[i].split("-")[0]+"id="+listHolonElementTypeMaster[i].split("-")[0]+">"+listHolonElementTypeMaster[i].split("-")[1]+"</option>";
			$("#holonElementType").append(options);
		}
	}
}

function getListHolonElementState(state) {
	var options={};
	if(typeof state != "undefined") {
		options={state:state};
	}
	ajaxRequest("getListHolonElementState", {}, getListHolonElementStateCallBack,options);
}

function getListHolonElementStateCallBack(data,options) {
	var state =options['state'];
	var listHolonElementState= data.split("*");
	$("#holonElementState").empty();
	for(var i=0;i<listHolonElementState.length-1;i++) {
		if((typeof state != "undefined")&&(state==listHolonElementState[i].split("-")[0])) {
			var options= "<option value="+listHolonElementState[i].split("-")[0]+" id= "+listHolonElementState[i].split("-")[0]+" selected>"+listHolonElementState[i].split("-")[1]+"</option>";
			$("#holonElementState").append(options);
		} else {
			var options= "<option value="+listHolonElementState[i].split("-")[0]+" id= "+listHolonElementState[i].split("-")[0]+">"+listHolonElementState[i].split("-")[1]+"</option>";
			$("#holonElementState").append(options);
			}
		}
}

function addHolonElement() {
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
	var resp =data.split("*");
	if(resp[0] == "true"){
		var holonObjectId = options['holonObjectId'];
		closeDiv('elementInfo');
		updateCoordinator(resp[1]);
		showHolonCoIcons();
		showHolonElements(holonObjectId);
		showPowerCircles(holonObjectId);
	} else {
		swal("Server Error!", "Error in adding Holon Element. Please check application logs.", "info");
	}
}

function editHolonElementCallBack(data, options) {
	$("#holonElementActionState").val("");
	var resp =data.split("*");
	if(resp[0]  == "true"){
		var holonObjectId = options['holonObjectId'];
		closeDiv('elementInfo');
		updateCoordinator(resp[1]);
		showHolonCoIcons();
		showHolonElements(holonObjectId);
		showPowerCircles(holonObjectId);
	} else {
		swal("Server Error!","Error in editing Holon Element. Please check application logs.","info");
	}
}

function editHolonElement(holonElementId,holonElementTypeId,state,currentCapacity, holonObjectId) {
	$("#hiddenHolonObjectId").val(holonObjectId);
	$("#hiddenHolonElementId").val(holonElementId);
	$("#holonElementActionState").val("Edit");	
	$("#currentCapacity").val(currentCapacity);
	$("#elmTitle").text("Edit Holon Element");
	getListHolonElementType(holonElementTypeId);
	getListHolonElementState(state);
	openDiv('elementInfo');
}

function updateCoordinator(holonObjectId) {
	var options= {
			  holonObjectId : holonObjectId,
			}
	ajaxRequest("updateCoordinator", {}, updateCoordinatorCallBack, options);
}

function updateCoordinatorCallBack(data,options) {
	var holonObjectId=options['holonObjectId'];
	var dataAttributes= {
			  holonObjectId : holonObjectId,
			}
	 ajaxRequest("getHolonObjectInfoWindow", dataAttributes, getHolonInfoWindowCallBack, {});
}