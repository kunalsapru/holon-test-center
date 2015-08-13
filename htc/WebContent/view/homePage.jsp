<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Holon Test Center</title>
<link rel="shortcut icon" href="css/images/favicon.ico" />
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="css/map-icons.css">
<link rel="stylesheet" type="text/css" href="css/icon-style.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=drawing"></script>
<script type="text/javascript" src="js/googleMaps.js"></script>
<script type="text/javascript" src="js/map-icons.js"></script>
<script type="text/javascript" src="js/toggle.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/powerline.js"></script>
<script type="text/javascript" src="js/distanceCalc.js"></script>
<script type="text/javascript" src="js/holonObject.js"></script>
<script type="text/javascript" src="js/holonElement.js"></script>
<script type="text/javascript" src="js/switch.js"></script>
<script type="text/javascript" src="js/powerSource.js"></script>
<script type="text/javascript" src="js/connectToPowerSource.js"></script>
<script type="text/javascript" src="js/connectPowerSourceToLine.js"></script>
<script type="text/javascript" src="js/consumptionGraph.js"></script>
<script type="text/javascript" src="js/supplierDetails.js"></script>
<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/highcharts_exporting.js"></script>
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
        <li><a href="#" id="addHolonObject"><i class="fa fa-plus"></i>Add Holon Object</a></li>
        <li><a href="#" id="connectToPowerSource"><i class="fa fa-plug"></i>Connect to Main Line</a></li>
    </ul>
      </li>
      <li><a href="#"><i class="fa  fa-certificate"></i>Power Source<i class="fa fa-caret-down"></i></a>
    <ul>
           <li><a href="#" id="addPowerSource"><i class="fa fa-plus"></i>Add Power Source</a></li>
       	   <li><a href="#" id="connectPowerSource"><i class="fa fa-plug"></i>Connect Power Source</a></li>
      </ul>
      </li>
      
    <li> <a href="#" ><i class="fa fa-flash"></i>PowerLine<i class="fa fa-caret-down"></i></a>
      <ul>
        <li><a href="#" id="addPowerLine"><i class="fa fa-long-arrow-right"></i>Draw PowerLine</a></li>
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
  <div id="elementInfo" class='table'>
 				 <input type="hidden" id="hiddenHolonElementId" value="" />
				<table>
				<tr>
						<td id='elmTitle' style="text-decoration: underline;"></td>
				</tr>
					<tr>
						<td >Holon Element Type:&nbsp;&nbsp;<select id="holonElementType"></select></td>						
					</tr>
					<tr>
						<td>Holon Element State:&nbsp;&nbsp;<select id="holonElementState"></select></td>
					</tr>
					<tr>
						<td>Current Usage/Production:&nbsp;&nbsp;<input id="currentCapacity" type="text"></td>
					</tr>
					</table>
					<hr>
					<table>
					<tr>
						<td colspan="2"><input id="cancelElementInfo" onclick="closeDiv('elementInfo')"  type='button' class="button"  value='Cancel' />&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="saveElementInfo" type='button' class="button"  value='Save Element' /></td>					
					</tr> 
					</table>	
					<hr>					
			</div>
			<div id="holonObjectDetail" class='table'>
				<input type="hidden" id="holonObjectActionState" value="" /> 
				<input type="hidden" id="hiddenHolonObjectId" value="" />
				
				<table>
					<tr>
						<td id='hoObjTitle' colspan="2"  style="text-decoration: underline;"></td>
					</tr>	
					
					<tr>
						<td>Holon Object Type:
						<select name="holonObjectType" id="holonObjectType"></select></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Holon Manager Name:
						<input id="holonManagerName" type="text"></td>
					</tr>
					<tr>
						<td>Holon Coordinator:
						&nbsp;&nbsp;&nbsp;&nbsp;<select name="holonCoordinatorId" id="holonCoordinatorId"></select></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Can Communicate:
						<select name="canCommunicate" id="canCommunicate">
							<option value=1>Yes</option>
							<option value=0>No</option>
						</select></td>
					</tr>
					<tr>
						<td>Latitude NE:
						<label id="holonObjectLatitudeNE"></label></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Longtitude NE:
						<label id="holonObjectLongitudeNE"></label></td>
					</tr>
					<tr>
						<td>Latitude SW:
						<label id="holonObjectLatitudeSW"></label></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Longtitude SW:
						<label id="holonObjectLongitudeSW"></label></td>
					</tr> 
					</table><hr>
					<table>
					<tr>
						<td colspan="2"><input id="cancelHolonObject" type='button' class="button"  value='Cancel' />&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="saveHolonObject" type='button' class="button"  value='Save Holon Object' /></td>					
					</tr> 
					</table>	
					<hr>					
			</div>
			
			<div id="powerObjectDetail" class='table'>
				<input type="hidden" id="powerObjectActionState" value="" /> 
				<input type="hidden" id="hiddenPowerObjectId" value="" />
				<input type="hidden" id="hiddenPowerObjectCenterLat" value="" />
				<input type="hidden" id="hiddenPowerObjectCenterLng" value="" />
				<input type="hidden" id="hiddenPowerObjectRad" value="" />
				<table class='table'>
					<tr>
						<td id='powerObjTitle' colspan="2" style="text-decoration: underline;"></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Holon:&nbsp;&nbsp;&nbsp;&nbsp;<select name="pwholonCoordinatorId" id="pwholonCoordinatorId"></select></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Status:&nbsp;&nbsp;&nbsp;&nbsp;<select name="psStatus" id="psStatus">
							<option value=0>OFF</option>
							<option value=1>ON</option>
							</select></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Maximum Production Capacity:&nbsp;&nbsp;&nbsp;&nbsp;<input id="psMaxProdCap" type="text"></td>	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;Current Production:&nbsp;&nbsp;&nbsp;&nbsp;<input id="psCurrentPord" type="text"></td>						
					</tr>
					</table><hr>
					<table >
					<tr>
						<td colspan="2"><input id="cancelPowerObject" type='button' class="button"  value='Cancel' />&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="savePowerObject" type='button' class="button"  value='Save Power Source' /></td>	
					</tr>					
					</table><hr>
						
			</div>
			


	<div id="lineObjectDetail" class='table'>
				<input type="hidden" id="powerLineObjectActionState" value="" /> 
				<input type="hidden" id="powerLineStartLat" value="" /> 
				<input type="hidden" id="powerLineStartLng" value="" /> 
				<input type="hidden" id="powerLineEndLat" value="" />
				<input type="hidden" id="powerLineEndLng" value="" />
				<input type="hidden" id="powerLineIdHidden" value="" />
				<input type="hidden" id="powerLineHolonObjectIdHidden" value="" />
				<input type="hidden" id="powerLineIdForSubLine" value="" />
				<input type="hidden" id="powerLineType" value="" />
				<!-- <table id="powerLineObjectTable"> -->
				<table>
				<tr>
					<td id='powerLineTitle' colspan="2" style="text-decoration: underline; "></td>
				</tr>
					<tr>
						<td>Power Line Capacity:</td>
						<td><input id="powerLineCapacity" type="text"></td>
					</tr>
					</table><hr>
					<table>
					<tr >
						<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="cancelPowerLine" type='button' class="button"  value='Cancel' />
						<input id="savePowerLineObject" type='button' class="button"  value='Save Power Line' /></td>
					</tr>
				</table><hr>
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
			
 	<div id="divHolonElementsDetail" class='table'>
 	<input type="hidden" id="holonElementActionState" value="Add" />
 	<fieldset>
 	<legend >Holon Element List</legend>
 	<table>
 	<thead>
 		<tr>
	 		<th>Type</th>
	 		<th>Producer</th>
	 		<th>Max. Capacity</th>
	 		<th>Min. Capacity</th>
	 		<th>State</th>
	 		<th>Current Usage/Production</th>
	 		<th colspan="2" id="addHolonElementTableHeader"><i class="fa fa-plus-circle"></i>Add Holon Element</th>
 		<tr>
 	</thead>
 	<tbody id="holonElementsListBody" ></tbody>
 	</table> 	 	
 	</fieldset>
 	<input type="button" id="closeElementList" onclick="closeDiv('divHolonElementsDetail')" value="X"  />
 	</div>
 	
 	<div id="consumptionGraphBody"></div>
 	
 	<div id="supplierDetailsBody"  class='table'>
 	<table>
 	<tr>
	<td id='pwSuppTitle' colspan="3" style="text-decoration: underline;width: 33%; "></td>
	</tr>
	<tr>
	 		<td style="text-decoration: underline;font-weight:bold;text-align:center;width: 33%;">Type of Supplier</td>
	 		<td style="text-decoration: underline;font-weight:bold;text-align: center;width: 33%;">Id of Supplier</td>
	 		<td style="text-decoration: underline;font-weight:bold;text-align: center;width: 33%;">Power Supplied</td>	 		
 		<tr>
  	<tbody id="suppLierDetailList"></tbody>
 	</table><hr>
 	<table>	
 	<tr>
	<td colspan="3" style="text-align: center;width: 33%;"><input id="cancelShowSupply" type='button' class="button"  value='Close' />
	</td>
	</tr>
	</table><hr> 	
 	</div>
</div>
<script type="text/javascript" src="js/trigger.js"></script>
</body>
</html>
