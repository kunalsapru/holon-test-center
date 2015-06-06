<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HTC Home Page</title>
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?libraries=drawing"></script>
<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="js/googleMaps.js"></script>
<script type="text/javascript" src="js/gmaps.js"></script>
<script type="text/javascript" src="js/jquery.fancybox.js"></script>
<script type="text/javascript" src="js/jquery.fancybox.pack.js"></script>
<LINK href="css/contextMenu.css" rel="stylesheet" type="text/css">
<LINK href="css/jquery.fancybox.css" rel="stylesheet" type="text/css" media="screen">

</head>
<body> 
	<input type="button" id="addHolonObject" value="Add Holon Object" class="button"/>
	<input type="button" id="addHolonFactory" value="Add Holon from Factory" class="button"/>
	<input type="button" id="clear" value="Clear Map" class="button"/><br/>
	<div id="googleMap" style="width: 900px; height: 500px;"></div>
	<div id="elementInfo" class="fancybox">
	<table>
	<tr><td><b>Type::</b></td><td><input id="elementName" type="text"></td></tr>
	<tr><td><b>Max Capacity::</b></td><td><input id="minCapacity" type="text"></td></tr>
	<tr><td><b>Min Capacity::</b></td><td><input id="maxCapacity" type="text"></td></tr>
	<tr><td><b>Usage::</b></td><td><input id="usage" type="text"></td></tr>
	<tr><td></td><td><input id="saveElementInfo" type='button' value='Save'/></td></tr>
	</table>
	</div>
</body>
</html>