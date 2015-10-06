<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Holon Simulation</title>
<link rel="shortcut icon" href="../css/images/favicon.ico" />
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=drawing"></script>
<script type="text/javascript" src="../js/simulation.js"></script>

</head>
<body>
<div id="googleMaps" style="height:100%; width:100%;"></div>
<div id="simulationDiv">
	<fieldset style="display: inline;">
	<legend id="simulationLegend">Simulation HTC (All messages from server will be displayed here.)</legend>
		<table id="simulationDivTable"><tr style="display: none"><td colspan="2"></td></tr></table>
	</fieldset>
	<input type="button" id="closeSimluationDiv" style="float: right;" onclick="abortSimulationRequests('simulationDiv')" value="X" title="This will abort subsequent requests." />
</div>

</body>
</html>