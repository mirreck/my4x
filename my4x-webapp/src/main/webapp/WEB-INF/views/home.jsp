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
.family-tree canvas{
	position:absolute;top:0;left:0;
}


 .bordered{
	border: 1px solid #ccc;
	padding:10px;
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
}
.leaf{
	position:absolute;
	top:0;
	left:0;
}

		</style>
		
		<script>
		function box(eltid){
			element = $("#"+eltid);
			return {x : element.position().left,
				y : element.position().top,
				w : element.outerWidth(),
				h : element.outerHeight()};
		}
		function connectParents(parenta, parentb){
			var boxa = box(parenta);
			var boxb = box(parentb);
			var canvas=document.getElementById("canvas");
		    var ctx=canvas.getContext("2d");
		    
		    //ctx.fillStyle="green";
            //ctx.strokeStyle="green";
            ctx.beginPath();
            ctx.moveTo(boxa.x+boxa.w,boxa.y+boxa.h/2);
            ctx.lineTo(boxb.x,boxa.y+boxa.h/2);
            ctx.stroke();
		}
		
		function connectChild(parenta, parentb, child){
			var boxa = box(parenta);
			var boxb = box(parentb);
			
			var boxc = box(child);
			
			var canvas=document.getElementById("canvas");
		    var ctx=canvas.getContext("2d");
		    
		    //ctx.fillStyle="green";
            //ctx.strokeStyle="green";
            ctx.beginPath();
            var startx = boxa.x+boxa.w + (boxb.x- boxa.x-boxa.w)/2;
            var inty = (boxc.y+boxa.y+boxa.h)/2;
            ctx.moveTo(startx,boxa.y+boxa.h/2);
            ctx.lineTo(startx,inty);
            ctx.lineTo(boxc.x+boxc.w/2,inty);
            ctx.lineTo(boxc.x+boxc.w/2,boxc.y);
            ctx.stroke();
		}
		
		
		$(document).ready(function(){
			$(".family-tree").each(function(){
				var w = $(this).width();
				var h = $(this).height();
				$(this).find("canvas").css("width",w+"px").attr("width",w);
				$(this).find("canvas").css("height",h+"px").attr("height",h);
			});
			connectParents("parent1", "parent2");
			connectChild("parent1", "parent2","child1");
			connectChild("parent1", "parent2","child2");
			
		});
		</script>
		
	</head>
	<body>
		<h2>OK, this app is running.<i class="icon-ex-ecu sinople"></i><i class=icon-ex-door></i></h2>
		<div id="family-tree-1" class="family-tree" style="width:800px;height:400px;">
			<canvas id="canvas" style=""></canvas>
			<div class="bordered leaf" id="parent1" style="transform:translate( 100px,100px)">
				<span class="fa-stack">		
				  <i class="icon-ex-ecu sinople"></i>
				  <i class="icon-ex-ecup azur"></i>
				</span>
				<a href="#">Parentaaaa</a>
			</div>
			<div class="bordered leaf" id="parent2" style="transform:translate( 300px,100px)">
				<span class="fa-stack">		
	  				<i class="icon-ex-ecu sanguine"></i>
	  				<i class="icon-ex-ecuc or"></i>
				</span>
				<a href="#">Parent</a>
			</div>
			
			
			<div class="bordered leaf" id="child1" style="transform:translate( 100px,200px)">
				<span class="fa-stack">		
	  				<i class="icon-ex-ecu sanguine"></i>
	  				<i class="icon-ex-ecuc azur"></i>
				</span>
				<a href="#">Child1</a>
			</div>
			
			<div class="bordered leaf" id="child2" style="transform:translate( 300px,200px)">
				<span class="fa-stack">		
	  				<i class="icon-ex-ecu sanguine"></i>
	  				<i class="icon-ex-ecuc azur"></i>
				</span>
				<a href="#">Child2</a>
			</div>
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