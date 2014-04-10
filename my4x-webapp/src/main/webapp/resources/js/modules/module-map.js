
require(["jquery","Leaflet","modules/jquery-keyboard-plugin"], function($,L) {
	if ($("#map").length) {
	
        $('body').append('<p>MODULE2 - MAP - INITIALIZED:<a id="map-reset" href="#">Reset Map</a></p>');
		var map = L.map('map').setView([0.0, 0.0], 4);
		L.tileLayer(
				'rest/maptiles/{z}/{x}/{y}'
				, {
			minZoom: 2,
			maxZoom: 7,
			//continuousWorld: true,
			noWrap:true,
			maxBounds:[
			           [1.0, -1.0],
			           [1.0, -1.0]
			       ],
			attribution: '&copy; <a href="http://mywebsite.org">M4X </a>'
		}).addTo(map);
		
//		L.tileLayer(
//				'rest/maptiles-water/{z}/{x}/{y}'
//				, {
//			minZoom: 2,
//			maxZoom: 7,
//			//continuousWorld: true,
//			noWrap:true,
//			maxBounds:[
//			           [1.0, -1.0],
//			           [1.0, -1.0]
//			       ],
//			attribution: '&copy; <a href="http://mywebsite.org">M4X </a>'
//		}).addTo(map);
		
		
		$(".leaflet-control-attribution").html('&copy; <a href="http://mywebsite.org">M4X </a>');
		$("#map-reset").click(function(){
			$.get( "rest/resetmap", function( data ) {
				$( ".result" ).html( data );
					window.location.reload();
				});
		});
//		function hexCoordinates(x, y, size){
//			var posx = size*(x*1.5);
//			var posy = size*(y*2*0.866+0.866*(x%2));
//			return [
//		            [posx-size*1.0, posy+size*0.0],
//		            [posx-size*0.5, posy+size*0.866],
//		            [posx+size*0.5, posy+size*0.866],
//		            [posx+size*1.0, posy+size*0.0],
//		            [posx+size*0.5, posy-size*0.866],
//		            [posx-size*0.5, posy-size*0.866]
//		        ];
//		}
//		
//		var states = [
////		              {
////		    "type": "Feature",
////		    "properties": {"party": "Republican"},
////		    "geometry": {
////		        "type": "Polygon",
////		        "coordinates": [hexCoordinates(0.0,0.0,1.0)]
////		    }
////		}, {
////		    "type": "Feature",
////		    "properties": {"party": "Democrat"},
////		    "geometry": {
////		        "type": "Polygon",
////		        "coordinates": [hexCoordinates(1.0,0.0,1.0)]
////		    }
////		}
//		];
//		for ( var i = 0; i < 10; i++) {
//			for ( var j = 0; j < 10; j++) {
//				states[states.length] = {
//					    "type": "Feature",
//					    "properties": {"party": "Democrat"},
//					    "geometry": {
//					        "type": "Polygon",
//					        "coordinates": [hexCoordinates(i,j,2.0)]
//					    }
//					};
//			}
//		}
//		function highlightFeature(e) {
//		    var layer = e.target;
//
//		    layer.setStyle({
//		        weight: 1,
//		        color: '#666',
//		        dashArray: '',
//		        fillOpacity: 0.7
//		    });
//
//		    if (!L.Browser.ie && !L.Browser.opera) {
//		        layer.bringToFront();
//		    }
//		}
//		function focusFeature(e) {
//		    var layer = e.target;
//
//		    layer.setStyle({
//		        weight: 1,
//		        color: '#456',
//		        dashArray: '',
//		        fillOpacity: 0.1
//		    });
//
//		    if (!L.Browser.ie && !L.Browser.opera) {
//		        layer.bringToFront();
//		    }
//		}
//		function onEachFeature(feature, layer) {
//		    layer.on({
//		        mouseover: highlightFeature,
//		        mouseout: resetHighlight,   
//		        click: focusFeature
//		    });
//		}
//		var geojson = L.geoJson(states, {
//		    style: function(feature) {
//		        switch (feature.properties.party) {
//		            case 'Republican': return {color: "#ff0000", weight: 1, fillOpacity: 0.2};
//		            case 'Democrat':   return {color: "#0000ff", weight: 1, fillOpacity: 0.2};
//		        }
//		    },
//		    onEachFeature: onEachFeature
//		}).addTo(map);
//
//		function resetHighlight(e) {
//		    geojson.resetStyle(e.target);
//		}
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
		}
});