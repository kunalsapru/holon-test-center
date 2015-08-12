/**
 * 
 */
$(document).ready(function() {
	
	$("#cancelShowSupply").click(function(event){
		closeDiv("supplierDetailsBody");
	});	
})
function showSupplierDetails(holonObjectId)
{
	var dataAttributes= {
			holonObjectId : holonObjectId
	}
	ajaxRequest("getDataForSupplierDetails", dataAttributes, getDataForSupplierDetailsCallBack, dataAttributes);
}

function getDataForSupplierDetailsCallBack(data,options)
{
	var powerDetObjList = data.split("*");
	var powerDetObjListSize=powerDetObjList.length;
	var tableStr="";
	alert(powerDetObjListSize);
	if(powerDetObjListSize>0){
	for(var i=0;i<powerDetObjListSize-1;i++) {
		var pSrcObj=powerDetObjList[i];
		var pSrcObjDetail=pSrcObj.split("!");
		var loc= pSrcObjDetail[2]+"~"+pSrcObjDetail[3];
		tableStr = tableStr.concat("<td>"+pSrcObjDetail[4]+"</td><td><a href='#' onclick='zoomToHolon("+pSrcObjDetail[0]+","+loc+","+pSrcObjDetail[4]+")'>"+pSrcObjDetail[0]+"</a></td><td>"+pSrcObjDetail[1]+"</td>");
	}
	}
	else
		{
		tableStr = tableStr.concat("<td>No Power is being supplied.</td>");
		}
	alert(tableStr);
	$("#suppLierDetailList").html(tableStr);
	closeDiv('consumptionGraphBody');
	openDiv("supplierDetailsBody");

}