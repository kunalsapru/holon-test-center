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
	var resp =data.split("*");
	if(resp[0] == "true"){
		var holonObjectId = options['holonObjectId'];
		showHolonElements(holonObjectId);
		showPowerCircles(holonObjectId);
		updateCoordinator(resp[1],resp[2]);		
		
	} else {
		alert("Error in deleting Holon Element. Please check application logs.")
	}
}

function getListHolonElementType(holonElementTypeId) {
	var options={};
	if(typeof holonElementTypeId != "undefined")
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
		if((typeof holonElementTypeId != "undefined")&&(holonElementTypeId==listHolonElementTypeMaster[i].split("-")[0])){
		var options= "<option value="+listHolonElementTypeMaster[i].split("-")[0]+"id="+listHolonElementTypeMaster[i].split("-")[0]+" selected>"+listHolonElementTypeMaster[i].split("-")[1]+"</option>";
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
	if(typeof state != "undefined")
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
		if((typeof state != "undefined")&&(state==listHolonElementState[i].split("-")[0])){
			var options= "<option value="+listHolonElementState[i].split("-")[0]+" id= "+listHolonElementState[i].split("-")[0]+" selected>"+listHolonElementState[i].split("-")[1]+"</option>";
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
	var resp =data.split("*");
	if(resp[0] == "true"){
		var holonObjectId = options['holonObjectId'];
		closeDiv('elementInfo');
		updateCoordinator(resp[1],resp[2]);
		showHolonElements(holonObjectId);
		showPowerCircles(holonObjectId);
		
	} else {
		alert("Error in adding Holon Element. Please check application logs.")
	}
}

function editHolonElementCallBack(data, options) {
	$("#holonElementActionState").val("");
	var resp =data.split("*");
	if(resp[0]  == "true"){
		var holonObjectId = options['holonObjectId'];
		closeDiv('elementInfo');
		updateCoordinator(resp[1],resp[2]);
		showHolonElements(holonObjectId);
		showPowerCircles(holonObjectId);
		
	} else {
		alert("Error in editing Holon Element. Please check application logs.")
	}
}

function editHolonElement(holonElementId,holonElementTypeId,state,currentCapacity, holonObjectId) {
	$("#hiddenHolonObjectId").val(holonObjectId);
	$("#hiddenHolonElementId").val(holonElementId);
	$("#holonElementActionState").val("Edit");	
	$("#currentCapacity").val(currentCapacity);
	getListHolonElementType(holonElementTypeId);
	getListHolonElementState(state);
	openDiv('elementInfo');
}

function updateCoordinator(holonObjectId,hoCoObjIdOld)
{
	
	var dataAttributes = {
			holonObjectId : holonObjectId,
			hoCoObjIdOld : hoCoObjIdOld,
			
		};
	ajaxRequest("updateCoordinator", dataAttributes, updateCoordinatorCallBack, {});

}

function updateCoordinatorCallBack(data,options)
{
	var coObjIdBlue=data.split("*")[0];
	var coObjIdGreen=data.split("*")[1];
	var coObjIdYellow=data.split("*")[2];
	var coObjIdRed=data.split("*")[3];
	var itsOwnCoStatusChanged=data.split("*")[4];
	var changedToCoord=data.split("*")[5];
	 updateInfoWindow(itsOwnCoStatusChanged,changedToCoord);
	//alert(coObjIdBlue+coObjIdGreen+coObjIdYellow+coObjIdRed);
	var blueObj=globalHoList.get(coObjIdBlue.toString());
	if(typeof blueObj != "undefined")
	{		
		//alert("1");
		var cLocation=blueObj.getBounds().getNorthEast();
		var blueCObject=globalHKList.get("blue");
		if(typeof blueCObject != "undefined")
		{		
			//alert("11");
			blueCObject.setOptions({center:cLocation});
			globalHKList.set("blue",blueCObject);
		}
	}
	
	var greenObj=globalHoList.get(coObjIdGreen.toString());
	if(typeof greenObj != "undefined")
	{		
		//alert("2");
		var cLocation=greenObj.getBounds().getNorthEast();
		var greenCObject=globalHKList.get("green");
		if(typeof greenCObject != "undefined")
		{		
			//alert("22");
			greenCObject.setOptions({center:cLocation});
			globalHKList.set("green",greenCObject);
		}
	}

	var yellowObj=globalHoList.get(coObjIdYellow.toString());
	if(typeof yellowObj != "undefined")
	{		
		//alert("3");
		var cLocation=yellowObj.getBounds().getNorthEast();
		var yellowCObject=globalHKList.get("yellow");
		if(typeof yellowCObject != "undefined")
		{		
			//alert("33");
			yellowCObject.setOptions({center:cLocation});
			globalHKList.set("yellow",yellowCObject);
		}
	}
	
	var redObj=globalHoList.get(coObjIdRed.toString());
	if(typeof redObj != "undefined")
	{		
		//alert("4");
		var cLocation=redObj.getBounds().getNorthEast();
		var redCObject=globalHKList.get("red");
		if(typeof redCObject != "undefined")
		{		
			//alert("44");
			redCObject.setOptions({center:cLocation});
			globalHKList.set("red",redCObject);
		}
	}

	
}

function updateInfoWindow(itsOwnCoStatusChanged,changedToCoord)
{
	
	var infoWindow = currentInfoWindowObject;	
	var content = infoWindow.getContent();
	var finalCont="";
	if(itsOwnCoStatusChanged=="true")
		{
			if(changedToCoord)
				{
				finalCont=content.concat("<h3 align=\"center\">Holon Details</h3>");			
				}
			else
				{
				finalCont=content.replace("<h3 align=\"center\">Holon Details</h3>","");	
				}
			infoWindow.setContent(finalCont);
		
		}
	
}
