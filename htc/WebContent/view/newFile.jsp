<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Holon Test Center</title>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/newFile.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="http://maps.googleapis.com/maps/api/js"></script>
<script src="js/newFile.js"></script>

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
    <li><a href="#"><i class="fa fa-institution"></i>Holon Object<i class="fa fa-caret-down"></i></a>
    <ul>
        <li><a href="#"><i class="fa fa-plus"></i>Add New Holon Object</a></li>
        <li><a href="#"><i class="fa fa-wrench"></i>Edit Holon Object<i class="fa fa-caret-down"></i></a>
        <ul>
        
        <li><a href="#"><i class="fa fa-plus-circle"></i>Add Holon Elements</a></li>
        <li><a href="#"><i class="fa fa-edit"></i>Edit Holon Elements</a></li>
        <li><a href="#"><i class="fa fa-remove"></i>Delete Holon Elements</a></li>
        <li><a href="#"><i class="fa fa-info"></i>Show Holon Elements</a></li>
        </ul>
        </li>
        <li><a href="#"><i class="fa fa-plug"></i>Connect to Power Source</a></li>
        <li><a href="#"><i class="fa fa-line-chart"></i>Show Consumption</a></li>
      </ul></li>
    <li> <a href="#"><i class="fa fa-flash"></i>PowerLine<i class="fa fa-caret-down"></i></a>
      <ul>
        <li><a href="#"><i class="fa fa-arrows-h"></i>Draw PowerLine</a></li>
        </ul>
    </li>
    <li><a href="#"><i class="fa fa-toggle-on"></i>Switch<i class="fa fa-caret-down"></i></a>
    <ul>
    	<li><a href="#"><i class=" fa fa-circle-o-notch"></i>Place Switch</a></li>
    </ul>
    </li>
  </ul>
  <a href="#" id="showmenu"> <i class="fa fa-align-justify"></i> </a> </nav>
<!-- /menu vertical --> 

<!-- contenido de pagina, realmente no importa -->
<div id="container">
  <div id="googleMap"></div>
 
</div>

 
<script src="js/trigger.js"></script>
</body>
</html>
