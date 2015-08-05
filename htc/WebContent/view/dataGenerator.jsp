<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data Generator</title>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-2.1.4.js"></script>
<script type="text/javascript">
	function dataGenerator() {
		var attr1 = $("#attr1").val();
		var attr2 = $("#attr2").val();
		var attr3 = $("#attr3").val();
		var attr4 = $("#attr4").val();
		var attr5 = $("#attr5").val();
		var dataAttributes = {
			attr1 : attr1,
			attr2 : attr2,
			attr3 : attr3,
			attr4 : attr4,
			attr5 : attr5			
		};
		ajaxRequest("dataGenerator", dataAttributes,
				dataGeneratorCallBack, {})
	}

	function dataGeneratorCallBack(data, options) {
		
		alert(data);
		
	}
</script>

</head>
<body>
	<div style="height: 60%; width: 50%; text-align: center; background-color: aqua;">
		<form  action="dataGenerator" method="Post" id="dataGeneratorFormID">
			<table border="1">
				<tbody>
					<tr>
						<td width="100%">Number of Holons:</td>
						<td><input type="text" id="attr1" /></td>
					</tr>
					<tr>
						<td width="50%">Weight for Hospitals:</td>
						<td><input type="text" id="attr2" /></td>
					</tr>
					<tr>
						<td width="50%">Weight for Govt Infrastructure:</td>
						<td><input type="text" id="attr3" /></td>
					</tr>
					<tr>
						<td width="50%">Weight for Physician Buildings: </td>
						<td><input type="text" id="attr4" /></td>
					</tr>
					<tr>
						<td width="50%">Weight for Police Buildings:</td>
						<td><input type="text" id="attr5" /></td>
					</tr>
					<tr>
						<td width="50%">Weight for Fire Department:</td>
						<td><input type="text" id="attr6" /></td>
					</tr>
					<tr>
						<td width="50%">Weight for Industries:</td>
						<td><input type="text" id="attr7" /></td>
					</tr>
					<tr>
						<td width="50%">Weight for Household Buildings:</td>
						<td><input type="text" id="attr8" /></td>
					</tr>
					<tr>
						<td width="50%">Weight for PowerPlant:</td>
						<td><input type="text" id="attr8" /></td>
					</tr>
					<tr>
						<td width="50%">Weight for Transformers:</td>
						<td><input type="text" id="attr8" /></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="button"
							style="cursor: pointer;" value="Send to data generator"
							onclick="dataGenerator()" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>