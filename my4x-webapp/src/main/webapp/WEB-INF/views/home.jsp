<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html>
<html> 
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			
		<link href="resources/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
		<link href="resources/lib/font-extend/css/font-extend.css" rel="stylesheet">
		<link rel="stylesheet" href="resources/lib/leaflet/leaflet.css" />
		<link href="resources/css/main.css" rel="stylesheet">
		<script data-main="resources/js/main" src="resources/lib/jquery/require-jquery.js"></script>
		<title>HTML5 DEMO</title>
		<style type="text/css">
		#map { height: 180px; }
		
		div {
			-webkit-transition: all .2s;
			-moz-transition: all .2s;
			-o-transition: all .2s;
			transition: all .2s;
		}
		
		div.indoorback{
			transform: scale(0.75);
			-moz-transform: scale(0.75);
			-webkit-transform: scale(0.75);
			-ms-transform: scale(0.75);
			opacity:0.3;
			filter: alpha(opacity=30);
		}
		div.indoorcurrent{
			transform: scale(1.0);
			-moz-transform: scale(1.0);
			-webkit-transform: scale(1.0);
			-ms-transform: scale(1.0);
			opacity:1.0;
			filter: alpha(opacity=100);
		}
		div.indoorfront{
			transform: scale(1.25);
			-moz-transform: scale(1.25);
			-webkit-transform: scale(1.25);
			-ms-transform: scale(1.25);
			opacity:0.0;
			filter: alpha(opacity=0);
		}
		
		div.indoortile{
			-moz-user-select: none;
			height: 100px; 
			width: 100px;
			font-size: 100px;
			line-height: 100px;
			
		}
		div.indoortile img{
			max-width: none !important;
		}
		div.indoormap {
			position:relative;
		}
		div.indoormap, div.indoorfloor{
			height: 700px; 
			width: 700px;
			display:block;
			overflow: hidden;
		}
		
		
		div.indoortile, div.indoorfloor{
			position: absolute;
			left: 0px;
			top: 0px;
		}
		div.currenttile{
			background-color : #F00 !important;
		}
		div.tile_floor {
			background-color : #AAE;
		}
		div.tile_room {
			background-color : #BBF;
		}
		div.tile_wall {
			background-color : #999;
		}
		div.tile_rock {
			background-color : #555;
		}
		</style>
	</head>
	<body>
		<h2>OK, this app is running.<i class="icon-door"></i><i class="icon-stairs-up"></i><i class="icon-stairs-down"></i></h2>
		
		
		

			<p>Lorem Elsass ipsum knepfle hoplageiss Chulien condimentum habitant bredele commodo Miss Dahlias ullamcorper rhoncus Mauris Wurschtsalad rossbolla Morbi leverwurscht hopla consectetur flammekueche kuglopf Salu bissame Hans turpis météor Heineken turpis, so quam. amet, gewurztraminer ch'ai ac sit Salut bisamme suspendisse adipiscing porta schneck hopla Huguette schpeck et Racing. blottkopf, Gal ! libero, merci vielmols salu hopla Oberschaeffolsheim quam, placerat ornare geht's ac wie amet tristique mamsell wurscht.</p>
		
		
		
		<div id="indoormap" class="indoormap" style="width: 700px; height: 700px;"></div>
		<div id="map" style="width: 1200px; height: 800px"></div>

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