function ajaxRequest(actionName, dataAttributes, callBack, options){
	$.ajax({
		  url: actionName,
		  type: "POST",
		  cache: false,
		  data:dataAttributes,
		  dataType:"text",
		  context: document.body,
		  beforeSend:function (){
			  $("#spinner").show();
		  },
		  complete:function (){
			  $("#spinner").hide();
		  },
		  error: function(){
			  swal("Server Error!", "There is a problem with AJAX request. " +
			  		"Please check tomcat server status and database accessibility.", "info");
			  $("#spinner").hide();
		  },
		  success: function(data) {
		  	  callBack(data,options);	
		  }
	});	
}