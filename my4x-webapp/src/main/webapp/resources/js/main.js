requirejs.config({
  //In order for proper loading of depenencies in Terraformer modules set the path up in requirejs.config
  paths: {
	    "Leaflet" : "../lib/leaflet/leaflet"
  },
   shim: {
    'Leaflet': {
      exports: 'L'
    }
  }
});

require(["jquery","modules/module-characters","modules/module-map","modules/module-indoor-map","Leaflet"], function($) {
    $(function() {
        //$('body').append('<p>REQUIREJS INITIALIZED</p>');
        console.log('REQUIREJS INITIALIZED');
    });
});