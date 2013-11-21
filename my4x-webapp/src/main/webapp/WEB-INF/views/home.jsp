<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html>
<html> 
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			
		<link href="resources/css/font-awesome/css/font-awesome.css" rel="stylesheet">
		<link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.6.4/leaflet.css" />
 <!--[if lte IE 8]>
     <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.6.4/leaflet.ie.css" />
 <![endif]-->
		<link href="resources/css/main.css" rel="stylesheet">
		<script data-main="resources/js/main" src="resources/js/require-jquery.js"></script>
		<script src="http://cdn.leafletjs.com/leaflet-0.6.4/leaflet.js"></script>
		<title>HTML5 DEMO</title>
		<style type="text/css">
		#map { height: 180px; }
		</style>
	</head>
	<body>
		<h2>OK, this app is running.</h2>
		<div id="map" style="width: 600px; height: 400px"></div>

	<script src="http://cdn.leafletjs.com/leaflet-0.6.4/leaflet.js"></script>
	<script>

		var map = L.map('map').setView([90.0, -180.0], 13);

		L.tileLayer(
				//'http://{s}.tile.cloudmade.com/BC9A493B41014CAABB98F0471D759707/997/256/{z}/{x}/{y}.png'
				'http://localhost:8080/my4x-webapp/rest/maptiles/{z}/{x}/{y}'
				, {
			maxZoom: 18,
			attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a>'
		}).addTo(map);
		/*

		L.marker([51.5, -0.09]).addTo(map)
			.bindPopup("<b>Hello world!</b><br />I am a popup.").openPopup();

		L.circle([51.508, -0.11], 500, {
			color: 'red',
			fillColor: '#f03',
			fillOpacity: 0.5
		}).addTo(map).bindPopup("I am a circle.");

		L.polygon([
			[51.509, -0.08],
			[51.503, -0.06],
			[51.51, -0.047]
		]).addTo(map).bindPopup("I am a polygon.");


		var popup = L.popup();

		function onMapClick(e) {
			popup
				.setLatLng(e.latlng)
				.setContent("You clicked the map at " + e.latlng.toString())
				.openOn(map);
		}

		map.on('click', onMapClick);
*/
	</script>
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