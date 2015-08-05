function ajaxRequest(actionName,dataAttributes, callBack,options){
	$.ajax({
		  url: actionName,
		  type: "POST",
		  cache: false,
		  data:dataAttributes,
		  dataType:"text",
		  context: document.body,
		  beforeSend:function (){
		 // alert("Inside beforeSend");
		 // window.ajaxReqStatus=true;
		  },
		  complete:function (){
		  //alert("Inside complete");
		 // window.ajaxReqStatus=false;
		  },
		  error: function(){
			  ajaxRequestCount = 0;
			  alert("There is a problem with Ajax request.");
		  },
		  success: function(data) {
		  	  callBack(data,options);	
		  }
	});	
}