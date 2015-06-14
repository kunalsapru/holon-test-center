<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HTC Home Page</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?libraries=drawing"></script>
<script type="text/javascript" src="js/googleMaps.js"></script>
<script type="text/javascript" src="js/gmaps.js"></script>
<script type="text/javascript" src="js/toggle.js"></script>
<script type="text/javascript" src="js/rightPanel.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<LINK href="css/contextMenu.css" rel="stylesheet" type="text/css" />
<LINK href="css/style.css" rel="stylesheet" type="text/css" />
<LINK href="css/rightPanel.css" rel="stylesheet" type="text/css" />

</head>
<body>
	<!-- For Right Panel -->
	<div data-role="page" id="pageone">

		<!-- End Right Panel -->
		<div data-role="main" class="ui-content">

			<div id="nav">

				<ul data-role="listview" data-inset="true">
					<li data-role="list-divider" role="heading" id="addHolonObject">Add
						New Holon Object</li>
				</ul>
				<ul data-role="listview" data-inset="true" data-theme="a">
					<li data-role="list-divider" role="heading">Holon Object</li>
					<li class="button1"><a href="#" id="editHolonElements">Edit
							Holon Elements</a></li>
					<li><a href="#" id="connect">Connect to PowerSource</a></li>
					<li><a href="#" id="switch">Switch</a></li>
					<li><a href="#" id="showHolonElements">Show Holon Elements</a></li>
					<li><a href="#" id="consumption">Consumption</a></li>
				</ul>
				<ul data-role="listview" data-inset="true" data-theme="a">
					<li data-role="list-divider" role="heading">Switch</li>
					<li><a href="#" id="addSwitch">Add new Switch</a></li>
				</ul>
				<ul data-role="listview" data-inset="true" data-theme="a">
					<li data-role="list-divider" role="heading">Power Line</li>
					<li><a href="#" id="addPowerLine">Add new Power Line</a></li>
				</ul>
				<ul data-role="listview" data-inset="true" data-theme="a">
					<li data-role="list-divider" role="heading">Map Options</li>
					<li><a href="#" id="addSwitch">Distance Calculator</a></li>
					<li><a href="#" id="clear">Clear Map</a></li>
				</ul>
			</div>
			<div id="googleMap"></div>
			<div id="elementInfo">
				<span id="close">x</span><br>
				<table>
					<tr>
						<td>Holon Element Type:</td>
						<td><select name="elementType" class="elementType">
								<option value="fridge">Fridge</option>
								<option value="tv">TV</option>
								<option value="washingMachine">WashingMachine</option>
								<option value="light">Light</option>
								<option value="battery">Battery</option>
								<option value="generator">Generator</option>
								<option value="powerLines">PowerLines</option>
						</select></td>
					</tr>
					<tr>
						<td>Holon Element State:</td>
						<td><select name="elementState">
								<option value="overSupply">OverSupply</option>
								<option value="normalSupply">NormalSupply</option>
								<option value="minimumSupply">MinimumSupply</option>
								<option value="brownOut">BrownOut</option>
								<option value="heartBeat">HeartBeat</option>
								<option value="blackOut">BlackOut</option>
						</select></td>
					</tr>
					<tr>
						<td>Holon Manager:</td>
						<td><select name="holonManager">
								<option value="holonManager1">Holon Manager 1</option>
								<option value="holonManager2">Holon Manager 2</option>
								<option value="holonManager3">Holon Manager 3</option>
								<option value="holonManager4">Holon Manager 4</option>
						</select></td>
					</tr>
					<tr>
						<td>Holon Coordinator:</td>
						<td><input id="holonCoordinator" type="text"></td>
					</tr>
					<tr>
						<td>Max Capacity:</td>
						<td><input id="maxCapacity" type="text"></td>
					</tr>
					<tr>
						<td>Min Capacity:</td>
						<td><input id="minCapacity" type="text"></td>
					</tr>
					<tr>
						<td>Current Capacity:</td>
						<td><input id="currentCapacity" type="text"></td>
					</tr>
					<tr>
						<td>Current Energy Status:</td>
						<td><input id="currentEnergyStatus" type="text"></td>
					</tr>
					<tr>
						<td>Usage:</td>
						<td><input id="usage" type="text"></td>
					</tr>
					<tr>
						<td></td>
						<td><input id="saveElementInfo" type='button' value='Save' /></td>
					</tr>
				</table>
			</div>
			<div id="displayHolonDetails" data-role="collapsible-set"
				data-content-theme="d"></div>
		</div>

	</div>


</body>
</html>