<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Holon Element Type</title>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-2.1.4.js"></script>
<script type="text/javascript">
	function createHolonElement() {
		var holonElementTypeName = $("#holonElementTypeNameID").val();
		var dataAttributes = {
			holonElementTypeName : holonElementTypeName
		};
		ajaxRequest("createHolonElementType", dataAttributes,
				createHolonElementCallBack, {})
	}

	function createHolonElementCallBack(data, options) {
		$("#listHolonElementTypes").append(data);
	}
	
	function editHolonElement(holonElementTypeId) {
		alert(holonElementTypeId);
		var dataAttributes = {
			holonElementTypeId : holonElementTypeId
		};
		ajaxRequest("editHolonElementType", dataAttributes,
				createHolonElementCallBack, {})
	}

	function editHolonElementCallBack(data, options) {
		alert(data);
	}

	function deleteHolonElement(holonElementTypeId) {
		alert(holonElementTypeId);
		var dataAttributes = {
			holonElementTypeId : holonElementTypeId
		};
		ajaxRequest("deleteHolonElementType", dataAttributes,
				deleteHolonElementCallBack, {})
	}

	function deleteHolonElementCallBack(data, options) {
		alert(data);
	}
	
</script>

</head>
<body>
	<div
		style="height: 60%; width: 50%; text-align: center; background-color: aqua;">
		<table border="1">
			<thead style="text-decoration: underline;">
				<tr>
					<td width="30%">Primary Key(From DB)</td>
					<td width="70%">Holon Element Type Name</td>
				</tr>
			</thead>
			<tbody id="listHolonElementTypes"></tbody>
		</table>
		<form  action="createHolonElementType" method="Post"
			id="createHolonElementTypeFormID">
			<br><br><br><br>
			<table border="1">
				<tbody>
					<tr>
						<td width="50%">HE Type Name</td>
						<td><input type="text" id="holonElementTypeNameID" /></td>
					</tr>
					<tr>
						<td align="center"><input type="button"
							style="cursor: pointer;" value="Save Holon Element Type"
							onclick="createHolonElement()" /></td>
						<td align="center"><input type="button"
							style="cursor: pointer;" value="Edit Holon Element Type"
							onclick="editHolonElement()" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>