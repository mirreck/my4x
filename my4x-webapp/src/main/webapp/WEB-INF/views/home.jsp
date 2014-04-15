<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html>
<html> 
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			
		<link href="resources/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
		<link href="resources/lib/font-extend/css/font-extend.css" rel="stylesheet">
		<link rel="stylesheet" href="resources/lib/leaflet/leaflet.css" />
		<link href="resources/css/main.css" rel="stylesheet">
		<link href="resources/css/map-indoor.css" rel="stylesheet">
		<script data-main="resources/js/main" src="resources/lib/jquery/require-jquery.js"></script>
		<title>HTML5 DEMO</title>
		<style type="text/css">
.family-tree {
	position:relative;
}
.family-tree canvas {
	position:absolute;
	top:0;
	left:0;
	z-index:-1;
}



 .bordered{
	border: 1px solid #ccc;
	padding:5px;
	
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
}
.leaf-container{
	display:inline-block;
	margin : 20px;
}
.leaf{
}
.smalltext{
	font-size:10px;
}
.generation{
	width:100%;
	text-align: center;
}
.deceased{
	background-color:#DDD;
}
		</style>
		
		
	</head>
	<body>
		<h2>OK, this app is running.<i class="icon-ex-ecu sinople"></i><i class=icon-ex-door></i></h2>
		<div id="family-tree-1" class="family-tree">
			<canvas id="canvas" style=""></canvas>

			
		<div id="generation_3" class="generation"></div>
		<div id="generation_2" class="generation"></div>
		<div id="generation_1" class="generation"></div>
		<div id="generation_0" class="generation"></div>
		</div>
		
		
		<a href="indoor"><i class="icon-door"></i>Indoor</a>
		<a href="map"><i class="icon-door"></i>Map</a>
		<div id="w-system">
			<div id="w-icon"><i class="icon-gear"></i></div>
			<div class="panel-content">Informations about the system</div>
		</div>
		
		<div id="sys2">
			<div class="panel-content">Informations about the system</div>
			<div id="w-icon"><i class="icon-gear"></i></div>
		</div>
	</body>
</html> 