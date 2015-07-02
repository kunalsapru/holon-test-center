<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Holon Test Center</title>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/newFile.css">
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="http://maps.googleapis.com/maps/api/js"></script> -->
<!-- <script src="js/newFile.js"></script> -->
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
<link href="css/contextMenu.css" rel="stylesheet" type="text/css" />
<!-- <link href="css/style.css" rel="stylesheet" type="text/css" /> -->
<link href="css/rightPanel.css" rel="stylesheet" type="text/css" />

</head>

<body>
<!-- menu vertical -->
<nav id="menu" class="left">
  <ul>
    <li><a href="#" class="active">Holon Test Center</a></li>
    <li><a href="#"><i class="fa fa-header"></i>Holon<i class="fa fa-caret-down"></i></a>
    <ul>
        <li><a href="#"><i class="fa fa-info-circle"></i>Show Holons</a></li>
    </ul>
    </li>
    <li><a href="#"  id="showHolonObjects"><i class="fa fa-institution"></i>Holon Object<i class="fa fa-caret-down"></i></a>
    <ul>
        <li><a href="#" id="addHolonObject"><i class="fa fa-plus"></i>Add New Holon Object</a></li>
        <li><a href="#"><i class="fa fa-wrench"></i>Edit Holon Object<i class="fa fa-caret-down"></i></a>
        <ul>
        
        <li><a href="#"><i class="fa fa-plus-circle"></i>Add Holon Elements</a></li>
        <li><a href="#"><i class="fa fa-edit"></i>Edit Holon Elements</a></li>
        <li><a href="#"><i class="fa fa-remove"></i>Delete Holon Elements</a></li>
        <li><a href="#"><i class="fa fa-info"></i>Show Holon Elements</a></li>
        </ul>
        </li>
        <li><a href="#" ><i class="fa fa-plug"></i>Connect to Power Source</a></li>
        <li><a href="#" id="consumptionGraph"><i class="fa fa-line-chart"></i>Show Consumption</a></li>
      </ul></li>
    <li> <a href="#" id="connectToPowerSource"><i class="fa fa-flash"></i>PowerLine<i class="fa fa-caret-down"></i></a>
      <ul>
        <li><a href="#" id="addPowerLine"><i class="fa fa-arrows-h"></i>Draw PowerLine</a></li>
        <li><a href="#" id="calcDistance"><i class="fa fa-arrows-h"></i>Calculate Distance</a></li>
        </ul>
    </li>
    <li><a href="#"><i class="fa fa-toggle-on"></i>Switch<i class="fa fa-caret-down"></i></a>
    <ul>
    	<li><a href="#"><i class=" fa fa-circle-o-notch"></i>Switch on Holon Object</a></li>
    	<li><a href="#"><i class=" fa fa-life-saver "></i>Switch on Power Line</a></li>
    </ul>
    </li>
  </ul>
  <a href="#" id="showmenu"> <i class="fa fa-align-justify"></i> </a> </nav>
<!-- /menu vertical --> 

<!-- contenido de pagina, realmente no importa -->
<div id="container">
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
								<select name="elementState" id="elementState">
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
								<select name="holonObjectType" id="holonObjectType"></select>
							</div></td>
						<td>Holon Manager Name:</td>
						<td><input id="holonManagerName" type="text"></td>
					</tr>
					<tr>
						<td>Priority:</td>
						<td><input id="holonObjectPriority" type="text"></td>

						<td>Holon Coordinator:</td>
						<td><div data-role="fieldcontain">
								<select name="holonCoordinatorId" id="holonCoordinatorId"></select>
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
								<select name="holon" id="holon"></select>
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
			<div id="addMasterHolonElementStateDetail" class="ui-content"
				data-transition="flip" data-overlay-theme="b" data-theme="a"
				data-content-theme="d">
				<a href="#" data-rel="back" data-role="button" data-theme="a"
					data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
				<label>Holon Element State:</label><input type="text"
					id="textHolonElementStateMaster"></input> <input type="button"
					id="buttonHolonElementStateMaster" value="Save" />
			</div>		
 
</div>

 
<script type="text/javascript" src="js/trigger.js"></script>
</body>
</html>
