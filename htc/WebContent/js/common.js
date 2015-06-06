function ajaxRequest(actionName,dataAttributes, callBack,options){
	$.ajax({
		  url: actionName,
		  type: "POST",
		  cache: false,
		  data:dataAttributes,
		  dataType:"text",
		  context: document.body,
		  beforeSend:function (){
//			  alert("Inside beforeSend");
		  },
		  complete:function (){
///			  alert("Inside complete");
		  },
		  error: function(){
			  ajaxRequestCount = 0;
			  alert("Error!!");
		  },
		  success: function(data) {
		  	  callBack(data,options);	
		  }
	});	
}