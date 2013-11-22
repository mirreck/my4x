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

require(["jquery","modules/module1","modules/module2","Leaflet"], function($) {
    //the jquery.alpha.js and jquery.beta.js plugins have been loaded.
    $(function() {
        $('body').append('<p>REQUIREJS INITIALIZED</p>');
    });
});