
require(["jquery","Leaflet"], function($,L) {
        $('body').append('<p>MODULE2 - MAP - INITIALIZED</p>');
		var map = L.map('map').setView([0.0, 0.0], 2);

		L.tileLayer(
				//'http://{s}.tile.cloudmade.com/BC9A493B41014CAABB98F0471D759707/997/256/{z}/{x}/{y}.png'
				'http://localhost:8880/my4x-webapp/rest/maptiles/{z}/{x}/{y}'
				, {
			maxZoom: 5,
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
});