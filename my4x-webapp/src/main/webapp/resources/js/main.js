requirejs.config({
  //In order for proper loading of depenencies in Terraformer modules set the path up in requirejs.config
  paths: {
	    "Leaflet" : "../lib/leaflet/leaflet",
	    "jquery": "lib/jquery-1.8.3.min",
        "jquery.bootstrap": "../lib/bootstrap/js/bootstrap.min"
  },
   shim: {
    'Leaflet': {
      exports: 'L'
    },
    "jquery.bootstrap": {
        deps: ["jquery"]
    }
  }
});

require(["jquery", "jquery.bootstrap","modules/module-characters","modules/module-map","modules/module-indoor-map","Leaflet"], function($) {
    $(function() {
        //$('body').append('<p>REQUIREJS INITIALIZED</p>');
        console.log('REQUIREJS INITIALIZED');
    });
});