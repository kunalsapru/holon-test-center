<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HTC Home Page</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=drawing"></script>
<script type="text/javascript" src="js/googleMaps1.js"></script>
<script type="text/javascript" src="js/gmaps.js"></script>
<script type="text/javascript" src="js/toggle.js"></script>
<script type="text/javascript" src="js/rightPanel.js"></script>
<LINK href="css/contextMenu1.css" rel="stylesheet" type="text/css"/>
<LINK href="css/style1.css" rel="stylesheet" type="text/css"/>
<LINK href="css/rightPanel.css" rel="stylesheet" type="text/css"/>
<script>
  $(function() {
    $( "#elementInfo" ).dialog();
  });
  </script>
</head>
<body>
<!-- For Right Panel -->
<div data-role="page" id="pageone">
  <div data-role="panel" id="overlayPanel" data-display="overlay"> 
    <h2>Options</h2>
    <input type="button" id="addHolonObject" value="Add Holon Object" class="button1"/>
	<input type="button" id="addHolonFactory" value="Add Holon from Factory" class="button1"/>
	<input type="button" id="clear" value="Clear Map" class="button1"/><br/><br/><br/>
    <a href="#pageone" data-rel="close" class="ui-btn ui-btn-inline ui-shadow ui-corner-all ui-btn-a ui-icon-delete ui-btn-icon-left">Close panel</a>
  </div> 
  
  <!-- End Right Panel -->
    <div data-role="main" class="ui-content">
    <a href="#overlayPanel" class="ui-btn ui-btn-inline ui-corner-all ui-shadow">Overlay Panel</a>
    <div id="googleMap" style="height:610px;width:1060px;float:right;"></div>
	<div id="elementInfo">
	<table>
	<tr><td>Holon Element Type:</td>
	<td>
	<select name="elementType" class="elementType">
  	<option value="fridge">Fridge</option>
 	 <option value="tv">TV</option>
  	<option value="washingMachine">WashingMachine</option>
 	 <option value="light">Light</option>
 	 <option value="battery">Battery</option>
 	 <option value="generator">Generator</option>
 	 <option value="powerLines">PowerLines</option>
	</select>
	</td>
	</tr>
	<tr><td>Holon Element State:</td>
	<td>
	<select name="elementState">
  	<option value="overSupply">OverSupply</option>
 	 <option value="normalSupply">NormalSupply</option>
  	<option value="minimumSupply">MinimumSupply</option>
 	 <option value="brownOut">BrownOut</option>
 	 <option value="heartBeat">HeartBeat</option>
 	 <option value="blackOut">BlackOut</option>
	</select>
	</td></tr>
	<tr><td>Holon Manager:</td>
	<td>
	<select name="holonManager">
  	<option value="holonManager1">Holon Manager 1</option>
  	<option value="holonManager2">Holon Manager 2</option>
  	<option value="holonManager3">Holon Manager 3</option>
  	<option value="holonManager4">Holon Manager 4</option>
	</select>
	</td></tr>
	<tr><td>Holon Coordinator:</td><td><input id="holonCoordinator" type="text"></td></tr>
	<tr><td>Max Capacity:</td><td><input id="maxCapacity" type="text"></td></tr>
	<tr><td>Min Capacity:</td><td><input id="minCapacity" type="text"></td></tr>
	<tr><td>Current Capacity:</td><td><input id="currentCapacity" type="text"></td></tr>
	<tr><td>Current Energy Status:</td><td><input id="currentEnergyStatus" type="text"></td></tr>
	<tr><td>Usage:</td><td><input id="usage" type="text"></td></tr>
	<tr><td></td><td><input id="saveElementInfo" type='button' value='Save'/></td></tr>
	</table>
	</div>
	<div id="displayHolonDetails" data-role="collapsible-set" data-content-theme="d"></div>
  </div>
  
</div> 


</body>
</html>