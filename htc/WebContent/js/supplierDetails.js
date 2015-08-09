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
	ajaxRequest("showHolonElements", dataAttributes, getDataForSupplierDetailsCallBack, dataAttributes);
}

function getDataForSupplierDetailsCallBack()
{
	closeDiv('consumptionGraphBody');
	openDiv("supplierDetailsBody");

}