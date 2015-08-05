<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Holon Test Center</title>
<link rel="shortcut icon" href="css/images/favicon.ico" />
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/sweetalert.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=drawing"></script>
<script type="text/javascript" src="js/googleMaps.js"></script>
<script type="text/javascript" src="js/toggle.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/powerline.js"></script>
<script type="text/javascript" src="js/distanceCalc.js"></script>
<script type="text/javascript" src="js/holonObject.js"></script>
<script type="text/javascript" src="js/holonElement.js"></script>
<script type="text/javascript" src="js/switch.js"></script>
<script type="text/javascript" src="js/connectToPowerSource.js"></script>
<script src="js/sweetalert.min.js"></script>
</head>

<body>
<!-- menu vertical -->
<nav id="menu" class="left">
  <ul>
    <li><a href="#" class="active">Holon Test Center</a></li>
    <li><a href="#"><i class="fa fa-header"></i>Holon<i class="fa fa-caret-down"></i></a>
    <ul>
        <li><a href="#" id="showHolonObjects"><i class="fa fa-info-circle"></i>Show Holons</a></li>
    </ul>
    </li>
    <li><a href="#"><i class="fa fa-institution"></i>Holon Object<i class="fa fa-caret-down"></i></a>
    <ul>
        <li><a href="#" id="addHolonObject"><i class="fa fa-plus"></i>Add New Holon Object</a></li>
       <li><a href="#" id="connectToPowerSource"><i class="fa fa-plug"></i>Connect to Power Source</a></li>
        <li><a href="#" id="consumptionGraph"><i class="fa fa-line-chart"></i>Show Consumption</a></li>
      </ul></li>
    <li> <a href="#" ><i class="fa fa-flash"></i>PowerLine<i class="fa fa-caret-down"></i></a>
      <ul>
        <li><a href="#" id="addPowerLine"><i class="fa fa-arrows-h"></i>Draw PowerLine</a></li>
        <li><a href="#" id="calcDistance"><i class="fa fa-arrows-h"></i>Calculate Distance</a></li>
        </ul>
    </li>
    <li><a href="#"><i class="fa fa-toggle-on"></i>Switch<i class="fa fa-caret-down"></i></a>
    <ul>
    	<li><a href="#" id="switchOnPowerLine"><i class=" fa fa-life-saver "></i>Switch on Power Line</a></li>
    </ul>
    </li>
		<li><a href="#" id="clear"><i class="fa fa-eraser"></i>Clear Map</a></li> 
	</ul>
  <a href="#" id="showmenu"> <i class="fa fa-align-justify"></i> </a> </nav>
<!-- /menu vertical --> 

<!-- contenido de pagina, realmente no importa -->
<div id="container">
  <div id="googleMap"></div>
  <div id="elementInfo">
  <input type="hidden" id="hiddenHolonElementId" value="" />
				<table>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Holon Element Type:</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<select id="holonElementType"></select></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Holon Element State:</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<select id="holonElementState"></select></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Current Usage/Production:</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<input id="currentCapacity" type="text"></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<input id="cancelElementInfo" onclick="closeDiv('elementInfo')" type='button' value='Cancel' class="button"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<input id="saveElementInfo" type='button' value='Save' class="button"/></td>
					</tr>
				</table>
			</div>
			<div id="holonObjectDetail">
				<input type="hidden" id="holonObjectActionState" value="" /> 
				<input type="hidden" id="hiddenHolonObjectId" value="" />
				<table id="holonObjectTable">
					<tr>
						<td>Holon Object Type:</td>
						<td><select name="holonObjectType" id="holonObjectType"></select></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Holon Manager Name:</td>
						<td><input id="holonManagerName" type="text"></td>
					</tr>
					<tr>
						<td>Holon Coordinator:</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<select name="holonCoordinatorId" id="holonCoordinatorId"></select></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Can Communicate:</td>
						<td><select name="canCommunicate" id="canCommunicate">
							<option value=1>Yes</option>
							<option value=0>No</option>
						</select></td>
					</tr>
					<tr>
						<td>Latitude NE</td>
						<td><label id="holonObjectLatitudeNE"></label></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Longtitude NE</td>
						<td><label id="holonObjectLongitudeNE"></label></td>
					</tr>
					<tr>
						<td>Latitude SW</td>
						<td><label id="holonObjectLatitudeSW"></label></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Longtitude SW</td>
						<td><label id="holonObjectLongitudeSW"></label></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="cancelHolonObject" type='button' class="button"  value='Cancel' /></td>
						<td><input id="saveHolonObject" type='button' class="button"  value='Save Holon Object' /></td>
					</tr>
				</table>
			</div>


	<div id="lineObjectDetail">
				<input type="hidden" id="powerLineObjectActionState" value="" /> 
				<input type="hidden" id="powerLineStartLat" value="" /> 
				<input type="hidden" id="powerLineStartLng" value="" /> 
				<input type="hidden" id="powerLineEndLat" value="" />
				<input type="hidden" id="powerLineEndLng" value="" />
				<input type="hidden" id="powerLineIdHidden" value="" />
				<input type="hidden" id="powerLineHolonObjectIdHidden" value="" />
				<input type="hidden" id="powerLineType" value="" />
				<input type="hidden" id="powerLineIdForSubLine" value="" />
				<table id="powerLineObjectTable">
					<tr>
						<td>Power Line Capacity:</td>
						<td><input id="powerLineCapacity" type="text"></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="cancelPowerLine" type='button' class=btn  value='Cancel' /></td>
						<td><input id="savePowerLineObject" type='button' class="btn"  value='Save Power Line' /></td>
					</tr>
				</table>
			</div>
			
			
			<div id="subLineObjectDetail">
				<input type="hidden" id="subLineLineObjectActionState" value="" /> 
				<input type="hidden" id="subLineStartLat" value="" /> 
				<input type="hidden" id="subLineStartLng" value="" /> 
				<input type="hidden" id="subLineEndLat" value="" />
				<input type="hidden" id="subLineEndLng" value="" />
				<input type="hidden" id="subLineIdHidden" value="" />
				<table id="subLineObjectTable">
					<tr>
						<td>Sub Line Capacity:</td>
						<td><input id="subLineCapacity" type="text"></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="cancelSubLine" type='button' class="button"  value='Cancel' /></td>
						<td><input id="saveSubLineObject" type='button' class="button"  value='Save Sub-Line' /></td>
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
 	<div id="divHolonElementsDetail">
 	<input type="hidden" id="holonElementActionState" value="Add" />
 	<fieldset>
 	<legend >Holon Element List</legend>
 	<table>
 	<thead>
 		<tr>
	 		<th>&nbsp;Type&nbsp;</th>
	 		<th>&nbsp;Producer&nbsp;</th>
	 		<th>&nbsp;Max. Capacity&nbsp;</th>
	 		<th>&nbsp;Min. Capacity&nbsp;</th>
	 		<th>&nbsp;State&nbsp;</th>
	 		<th>&nbsp;Current Usage/Production&nbsp;</th>
	 		<th colspan="3" id="addHolonElementTableHeader"><i class="fa fa-plus-circle"></i>Add Holon Element</th>
 		</tr>
 	</thead>
 	<tbody id="holonElementsListBody" ></tbody>
 	</table> 	 	
 	</fieldset>
 	<input type="button" id="closeElementList" onclick="closeDiv('divHolonElementsDetail')" value="X"  />
 	</div>
</div>
<script type="text/javascript" src="js/trigger.js"></script>
</body>
</html>
