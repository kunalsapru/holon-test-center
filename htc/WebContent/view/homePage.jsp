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
<script type="text/javascript" src="js/holonObject.js"></script>
<script type="text/javascript" src="js/jquery.flot.js"></script>
<script type="text/javascript" src="js/jquery.flot.time.js"></script>
<script type="text/javascript" src="js/drawChart.js"></script>
<script src="js/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/chart.css">
<link rel="stylesheet" type="text/css" href="css/sweetalert.css">
<LINK href="css/contextMenu.css" rel="stylesheet" type="text/css" />
<LINK href="css/style.css" rel="stylesheet" type="text/css" />
<LINK href="css/rightPanel.css" rel="stylesheet" type="text/css" />

</head>
<body>
	<!-- For Right Panel -->
	<div data-role="page" id="pageone">
		<div data-role="main" class="ui-content">
			<div id="displayHolonDetails" data-role="panel"
				data-display="overlay" style="display: none; top: auto;"
				data-swipe-close="false">
				<div data-role="collapsible" id="displayHolonDetailsData">
					<h3 title="Holon Elements">Holon Elements</h3>
				</div>
			</div>
			<div id="nav">

				<ul data-role="listview" data-inset="true">
					<li data-role="list-divider" id="addHolonCoordinator"
						title="Add New Holon Coordinator">Add New Holon Coordinator</li>
				</ul>

				<ul data-role="listview" data-inset="true">
					<li data-role="list-divider" id="addHolonObject"
						title="Add New Holon Object">Add New Holon Object</li>
				</ul>

				<ul data-role="listview" data-inset="true">
					<li data-role="list-divider" id="consumptionGraph"
						title="Consumption Graph">Consumption Graph</li>
				</ul>

				<ul data-role="listview" data-inset="true">
					<li data-role="list-divider" id="showHolonObjects"
						title="Show Holon Objects">Show Holon Objects</li>
				</ul>

				<ul data-inset="true" style="padding-left: 0px;">
					<li>
						<div data-role="collapsible">
							<h4 title="Modes">Modes</h4>
							<ul data-role="listview">
								<li><a href="#" id="connectToPowerSource"
									title="Connect to PowerSource">Connect to PowerSource</a></li>
								<li><a href="#" id="switch" title="Switch">Switch</a></li>
								<li><a href="#" id="calcDistance"
									title="Calculate Distance">Calculate Distance</a></li>
								<li><a href="#" id="addPowerLine"
									title="Add New Power Line">Add New Power Line</a></li>
							</ul>
						</div>
					</li>
				</ul>

				<ul data-inset="true" style="padding-left: 0px;">
					<li>
						<div data-role="collapsible">
							<h4 title="Master Tables">Master Tables</h4>
							<ul data-role="listview">
								<li><a href="#" id="holonObjectTypeforMasterTables"
									title="Holon Object Type">Holon Object Type</a></li>
								<li><a href="#" id="holonElementStateforMasterTables"
									title="Holon Element State">Holon Element State</a></li>
								<li><a href="#" id="holonElementTypeforMasterTables"
									title="Holon Element Type">Holon Element Type</a></li>
								<li><a href="#" id="holonforMasterTables" title="Holon">Holon</a></li>
							</ul>
						</div>
					</li>
				</ul>
				<ul data-role="listview" data-inset="true">
					<li data-role="list-divider" id="clear" title="Clear Map">Clear
						Map</li>
				</ul>

			</div>
			<div id="googleMap"></div>

			<div id="elementInfo" class="ui-content" data-transition="flip"
				data-overlay-theme="b" data-theme="a" data-content-theme="d">
				<a href="#" data-rel="back" data-role="button" data-theme="a"
					data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
				<table>
					<tr>
						<td>Holon Element Type:</td>
						<td><div data-role="fieldcontain">
								<select name="elementType" class="elementType"
									id="selectForHolonElementType">
								</select>
							</div></td>
						<td>Holon Element State:</td>
						<td><div data-role="fieldcontain">
								<select name="elementState">
									<option value="overSupply">OverSupply</option>
									<option value="normalSupply">NormalSupply</option>
									<option value="minimumSupply">MinimumSupply</option>
									<option value="brownOut">BrownOut</option>
									<option value="heartBeat">HeartBeat</option>
									<option value="blackOut">BlackOut</option>
								</select>
							</div></td>
					</tr>
					<tr>
						<td>Holon Manager:</td>
						<td><div data-role="fieldcontain">
								<select name="holonManager">
									<option value="holonManager1">Holon Manager 1</option>
									<option value="holonManager2">Holon Manager 2</option>
									<option value="holonManager3">Holon Manager 3</option>
									<option value="holonManager4">Holon Manager 4</option>
								</select>
							</div></td>

						<td>Holon Coordinator:</td>
						<td><input id="holonCoordinator" type="text"></td>
					</tr>
					<tr>
						<td>Max Capacity:</td>
						<td><input id="maxCapacity" type="text"></td>

						<td>Min Capacity:</td>
						<td><input id="minCapacity" type="text"></td>
					</tr>
					<tr>
						<td>Current Capacity:</td>
						<td><input id="currentCapacity" type="text"></td>

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
			<div id="holonObjectDetail" class="ui-content" data-transition="flip"
				data-overlay-theme="b" data-theme="a" data-content-theme="d">
				<a href="#" data-rel="back" data-role="button" data-theme="a"
					data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
				<input type="hidden" id="holonObjectActionState" value="Add" /> <input
					type="hidden" id="hiddenHolonObjectId" value="" />
				<table id="holonObjectTable">
					<tr>
						<td>Holon Object Type:</td>
						<td><div data-role="fieldcontain">
								<select name="holonObjectType" id="holonObjectType">
									<option value="1">Hospitals</option>
									<option value="2">House</option>
									<option value="3">School</option>
									<option value="4">PowerPlant</option>
									<option value="1">Transformer</option>
								</select>
							</div></td>
						<td>Holon Manager Name:</td>
						<td><input id="holonManagerName" type="text"></td>
					</tr>
					<tr>
						<td>Priority:</td>
						<td><input id="holonObjectPriority" type="text"></td>

						<td>Holon Coordinator:</td>
						<td><div data-role="fieldcontain">
								<select name="holonCoordinatorId" id="holonCoordinatorId">
									<option value="1">HK1-Red Holon</option>
									<option value="2">HK2-Yellow Holon</option>
								</select>
							</div></td>
					</tr>
					<tr>
						<td>Latitude NE</td>
						<td><label id="holonObjectLatitudeNE"></label></td>
						<td>Longtitude NE</td>
						<td><label id="holonObjectLongitudeNE"></label></td>
					</tr>
					<tr>
						<td>Latitude SW</td>
						<td><label id="holonObjectLatitudeSW"></label></td>
						<td>Longtitude SW</td>
						<td><label id="holonObjectLongitudeSW"></label></td>
					</tr>
					<tr>
						<td></td>
						<td><input id="saveHolonObject" type='button' class="button"
							value='Save Holon Object' /></td>
					</tr>
				</table>

			</div>
			<div id="chartContainer" style="height: 300px; width: 30%;"></div>
			<div id="holonCoordinatorInformation" class="ui-content"
				data-transition="flip" data-overlay-theme="b" data-theme="a"
				data-content-theme="d">
				<a href="#" data-rel="back" data-role="button" data-theme="a"
					data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
				<table>
					<tr>
						<td><label>Name:</label></td>
						<td><input type="text" name="nameCoordinator"
							id="nameCoordinator" /></td>
					</tr>
					<tr>
						<td><label>Holon:</label></td>
						<td><div data-role="fieldcontain">
								<select name="holon" id="holon">
									<option value="holon1">Red Holon</option>
									<option value="holon2">Yellow Holon</option>
									<option value="holon3">Blue Holon</option>
									<option value="holon4">Green Holon</option>
								</select>
							</div></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="button" name="saveCoordinator"
							id="saveCoordinator" value="Save"></td>
					</tr>
				</table>
			</div>
			<div id="masterTableHolonObjectsTypes" class="ui-content"
				data-transition="flip" data-overlay-theme="b" data-theme="a"
				data-content-theme="d">
				<a href="#" data-rel="back" data-role="button" data-theme="a"
					data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
			</div>
			<div id="addMasterHolonObjDetail">
				<label>Object Type:</label><input type="text" id="textObjTypeMaster"></input>
				<input type="button" id="buttonObjTypeMaster" value="Save" />
			</div>
			<div id="addMasterHolonDetail" class="ui-content"
				data-transition="flip" data-overlay-theme="b" data-theme="a"
				data-content-theme="d">
				<a href="#" data-rel="back" data-role="button" data-theme="a"
					data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
				<label>Holon Name:</label><input type="text" id="textHolonMaster"></input>
				<input type="button" id="buttonHolonMaster" value="Save" />
			</div>
			<div id="addMasterHolonElementTypeDetail" class="ui-content"
				data-transition="flip" data-overlay-theme="b" data-theme="a"
				data-content-theme="d">
				<a href="#" data-rel="back" data-role="button" data-theme="a"
					data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
				<label>Holon Element Type:</label><input type="text"
					id="textHolonElementTypeMaster"></input> <input type="button"
					id="buttonHolonElementTypeMaster" value="Save" />
			</div>
		</div>

	</div>


</body>
</html>