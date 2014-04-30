define(['jquery'], function($) {

  $.KeyCodes = {
		UP : 38,
		DOWN : 40,
		LEFT : 37,
		RIGHT : 39,
		PAGEUP  : 33,
		PAGEDOWN  : 34 ,
		PLUS :107,
		MINUS:109
  };
  $.fn.keyboardEvent = function(keyCode, callback) {
	  
	  return this.each(function() {
		  $( this ).keydown(function(event){
			  //console.log('keydown :'+event.which);
			  if (event.which == keyCode && typeof callback == 'function') {
				  event.preventDefault();
			    }
		  });
		  $( this ).keyup(function(event){
			  //console.log('keyup :'+event.which);
			  if (event.which == keyCode && typeof callback == 'function') {
				  callback.call(this);
				  event.preventDefault();
			    }
		  });
		  $( this ).keypress(function(event){
			  //console.log('keypress( :'+event.which);
			  if (event.which == keyCode && typeof callback == 'function') {
			    event.preventDefault();
			  }
		  });
	  });
	  

  };
});