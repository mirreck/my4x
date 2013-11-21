require(["jquery","modules/module1","modules/module2"], function($) {
    //the jquery.alpha.js and jquery.beta.js plugins have been loaded.
    $(function() {
        $('body').append('<p>REQUIREJS INITIALIZED</p>');
    });
});