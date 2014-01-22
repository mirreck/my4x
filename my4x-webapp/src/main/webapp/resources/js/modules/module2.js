
require(["jquery","Leaflet"], function($,L) {
        $('body').append('<p>MODULE2 - MAP - INITIALIZED:<a id="map-reset" href="#">Reset Map</a></p>');
		var map = L.map('map').setView([0.0, 0.0], 4);

		L.tileLayer(
				'rest/maptiles/{z}/{x}/{y}'
				, {
			minZoom: 2,
			maxZoom: 7,
			attribution: '&copy; <a href="http://mywebsite.org">M4X </a>'
		}).addTo(map);
		
		$(".leaflet-control-attribution").html('&copy; <a href="http://mywebsite.org">M4X </a>');
		$("#map-reset").click(function(){
			$.get( "rest/resetmap", function( data ) {
				$( ".result" ).html( data );
					window.location.reload();
				});
		});
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