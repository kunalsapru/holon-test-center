<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data Factory (htc)</title>
<link rel="shortcut icon" href="../css/images/favicon.ico" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		ajaxRequest("factoryListHolonObjectType", {},factoryListHolonObjectTypeCallBack, {});
		ajaxRequest("factoryListHolonElementType", {},factoryListHolonElementTypeCallBack, {});
	});
	
	function factoryListHolonObjectTypeCallBack(data, options) {
		var holonObjectList = data.split("*");
		var innerHtmlHolonObject = "";
		var holonObjectTypesIDs = "";
		var holonObjectTypesPriorities = "";
		for (var i=0; i<holonObjectList.length; i++) {
			var holonObjectTypeId = holonObjectList[i].split("~")[0];
			var holonObjectTypeName = holonObjectList[i].split("~")[1];
			var holonObjectTypePriority = holonObjectList[i].split("~")[2];
			
			var htmlId = "holonObjectType_"+holonObjectTypeId;
			if(i == holonObjectList.length-1) {
				holonObjectTypesIDs += htmlId;
				holonObjectTypesPriorities += holonObjectTypePriority;
			} else {
				holonObjectTypesIDs += htmlId+"~";
				holonObjectTypesPriorities += holonObjectTypePriority+"~";
			}
			innerHtmlHolonObject +=	"<tr><td>"+holonObjectTypeName+" (Priority = "+holonObjectTypePriority+"):</td><td>"+
			"<input type=\"text\" id=\""+htmlId+"\" /></td></tr>";
		}
		$("#holonObjectTypesIDs").val(holonObjectTypesIDs);
		$("#holonObjectTypesPriorities").val(holonObjectTypesPriorities);
		
		$("#totalHolonObjectTypes").val(holonObjectList.length);
		$("#holonObjectTypeListFactory").html(innerHtmlHolonObject);
	}

	function factoryListHolonElementTypeCallBack(data, options) {
		var holonElementTypeListFactoryHtml = "<tr><td colspan=\"2\"><b><u>Holon Element Types</u></b></td></tr>";
		var holonElementTypeList = data.split("~~");
		for(var i=0; i<holonElementTypeList.length;i++) {
			holonElementTypeListFactoryHtml += "<tr><td colspan=\"2\">"+holonElementTypeList[i]+"</td></tr>";			
		}
		$("#holonElementTypeListFactory").html(holonElementTypeListFactoryHtml);
	}

	function factoryDataGenerator() {
		var holonObjectTypesPriorities = $("#holonObjectTypesPriorities").val();
		var totalHolonObjectTypes = $("#totalHolonObjectTypes").val();
		var htmlIdHolonObjectTypes = "";
		var htmlValuesHolonObjectTypes = "";
		var holonObjectTypesIds = $("#holonObjectTypesIDs").val().split("~");
		for(var i=0; i<totalHolonObjectTypes; i++){
			if(i == totalHolonObjectTypes-1){
				htmlValuesHolonObjectTypes += $("#"+holonObjectTypesIds[i]).val();
				htmlIdHolonObjectTypes += holonObjectTypesIds[i];
			} else {
				htmlValuesHolonObjectTypes += $("#"+holonObjectTypesIds[i]).val()+"~~";
				htmlIdHolonObjectTypes += holonObjectTypesIds[i]+"~~";
			}
		}
		var dataAttributes = {
			totalHolonObjectTypes : totalHolonObjectTypes,
			htmlIdHolonObjectTypes : htmlIdHolonObjectTypes,
			htmlValuesHolonObjectTypes : htmlValuesHolonObjectTypes,
			holonObjectTypesPriorities : holonObjectTypesPriorities
		};
		ajaxRequest("factoryDataGenerator", dataAttributes,
				factoryDataGeneratorCallBack, {})
	}

	function factoryDataGeneratorCallBack(data, options) {
		alert(data);
	}
</script>
<link rel="stylesheet" href="../css/style.css">
<style type="text/css">
.table tr td:first-child {
	text-align: right;
	padding-right: 2%;
}
.table tr td:last-child {
	text-align: left;
	padding-left: 2%;
}
</style>
</head>
<body>
<div style="float: left;width: 44%;margin-left: 5%;">
	<div  class="table">
	<span style="font-family: sans-serif;"><b><u>Data Factory</u></b><i>(Enter weight for the following Holon Object Types in percentage)</i></span>
			<table border="1">
				<tbody id="holonObjectTypeListFactory"></tbody>
			</table>
			<input type="hidden" id="totalHolonObjectTypes" value="" />
			<input type="hidden" id="holonObjectTypesIDs" value="" />
			<input type="hidden" id="holonObjectTypesPriorities" value="" />
			<table border="2">
				<tr>
					<td colspan="2" style="text-align: center;"><input class="button" type="button"
						style="cursor: pointer;" value="Send to data generator"
						onclick="factoryDataGenerator()" /></td>
				</tr>
			</table>
	</div>
</div>
<div style="float: right;width: 44%;margin-right: 5%;">
	<div class="table">
	<span style="font-family: sans-serif;"><i>(Each Holon Object will have a maximum of 10 randomly selected Holon Elements)</i></span>
			<table border="1">
				<tbody id="holonElementTypeListFactory"></tbody>
			</table>
	</div>
</div>
<div>
	<table>
		<tbody id="dataFactoryOutput"></tbody>
	</table>
</div>
</body>
</html>