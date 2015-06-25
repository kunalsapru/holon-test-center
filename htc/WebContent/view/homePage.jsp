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
<script type="text/javascript" src="js/powerline.js"></script>
<script type="text/javascript" src="js/distanceCalc.js"></script>
<script type="text/javascript" src="js/addHolonObject.js"></script>
<script src="js/sweetalert.min.js"></script> 
<link rel="stylesheet" type="text/css" href="css/sweetalert.css">
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
					<li data-role="list-divider" id="addHolonObject" title="Add New Holon Object">Add New Holon Object</li>
				</ul>

				<ul data-inset="true" style="padding-left: 0px;">
					<li>
						<div data-role="collapsible">
							<h4 title="Holon Object">Holon Object</h4>
							<ul data-role="listview">
								<li><a href="#" id="editHolonElement" title="Edit Holon Elements">Edit Holon Elements</a></li>
								<li><a href="#" id="connect" title="Connect to PowerSource">Connect to PowerSource</a></li>
								<li><a href="#" id="switch" title="Switch">Switch</a></li>
								<li><a href="#" id="showHolonElements" title="Show Holon Elements">Show Holon Elements</a></li>
								<li><a href="#" id="consumption" title="Consumption">Consumption</a></li>
							</ul>
						</div>
					</li>
				</ul>

				<ul data-count-theme="c" data-inset="true"
					style="padding-left: 0px;">
					<li>
						<div data-role="collapsible">
							<h4 title="Switch">Switch</h4>
							<ul data-role="listview">
								<li><a href="#" id="addSwitch" title="Add new Switch">Add new Switch</a></li>
							</ul>
						</div>
					</li>
				</ul>
				<ul data-count-theme="c" data-inset="true"
					style="padding-left: 0px;">
					<li class="custom-li">
						<div data-role="collapsible">
							<h4 title="Power Line">Power Line</h4>
							<ul data-role="listview">
								<li><a href="#" id="addPowerLine" title="Add new Power Line">Add new Power Line</a></li>

							</ul>
						</div>
					</li>
				</ul>

				<ul data-count-theme="c" data-inset="true"
					style="padding-left: 0px;">
					<li class="custom-li">
						<div data-role="collapsible">
							<h4 title="Map Options">Map Options</h4>
							<ul data-role="listview">
								<li><a href="#" id="calcDistance" title="Distance Calculator">Distance Calculator</a></li>
								<li><a href="#" id="clear" title="Clear Map">Clear Map</a></li>

							</ul>
						</div>
					</li>
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