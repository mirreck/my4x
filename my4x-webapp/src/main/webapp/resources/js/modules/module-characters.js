require(["jquery"], function($) {
	
	$(".family-tree").each(function(){
		var treediv = $(this);
		init(treediv);
	});
	
	
	function init(treediv){
		console.log( "Family tree init: " + treediv );
		$.getJSON( "rest/family/1" )
		.done(function( json ) {
			console.log( "JSON data: " + json );
			init_callback(json, treediv);
		})
		.fail(function( jqxhr, textStatus, error ) {
			var err = textStatus + ", " + error;
			console.log( "Request Failed: " + err );
		});
	}
	
	function init_callback(json, treediv){
		for (var i=0;i<json.personnages.length;i++){
			var perso = json.personnages[i];
			var leaf = $('<div class="bordered leaf" id="'+perso.uuid+'"></div>');
			var ecu = $('<span class="fa-stack"><i class="icon-ex-ecu sinople"></i><i class="icon-ex-ecup azur"></i></span>');
			leaf.append(ecu);
			
			if(perso.deathDate == null){
				leaf.append('<a href="#">' + perso.firstName+' '+perso.lastName+'</a>');
				leaf.append('<br /><span class="smalltext">' + perso.strBirthDate+'</span>');
			} else {
				leaf.addClass("deceased");
				leaf.append('<span>' + perso.firstName+' '+perso.lastName+'<span>');
				leaf.append('<br /><span class="smalltext">' + perso.strBirthDate+'- &dagger; '+perso.strDeathDate+'</span>');
			}
			var container = $('<div class="leaf-container"></div>');
			container.append(leaf);
			$("#generation_"+perso.generation).append(container);
			
			console.log( "Perso: " + perso.firstName+" "+perso.lastName );
		}
		$( window ).resize({"json" : json, "treediv" :treediv},function(event) {
			console.log("resize canvas");
			initCanvas(json,treediv);
		});
		initCanvas(json,treediv);
	};
	function initCanvas(json,treediv){
		resizeCanvas(treediv);
		for (var i=0;i<json.personnages.length;i++){
			var perso = json.personnages[i];
			if(perso.coupleUuid != null){
				connectParents(perso.uuid, perso.coupleUuid,treediv);
			}
			if(perso.fatherUuid != null){
				connectChild(perso.fatherUuid, perso.motherUuid, perso.uuid,treediv);
			}
		}
	}
	
	function treeCanvas(treediv){
		return treediv.find("canvas");
	}
	function treeCanvasCtx(treediv){
		return treeCanvas(treediv)[0].getContext("2d");
	}
	
	
	function resizeCanvas(treediv){
		var w = treediv.outerWidth(true);
		var h = treediv.outerHeight(true);
		treeCanvas(treediv).css("width",w+"px").attr("width",w);
		treeCanvas(treediv).css("height",h+"px").attr("height",h);
	}
	function box(eltid){
		element = $("#"+eltid);
		var pos = element.position();
		return {x : pos.left,
			y : pos.top,
			w : element.outerWidth(),
			h : element.outerHeight(),
			totalw : element.outerWidth(true),
			totalh : element.outerHeight(true),
			marginw : (element.outerWidth(true) - element.outerWidth())/2
			};
	}
	function connectParents(parenta, parentb, treediv){
		var boxa = box(parenta);
		var boxb = box(parentb);
		if(boxb.x > boxa.x) {
		    var ctx=treeCanvasCtx(treediv);
            ctx.beginPath();
            ctx.moveTo(boxa.x + boxa.w,boxa.y+boxa.h/2);
            ctx.lineTo(boxb.x,boxa.y+boxa.h/2);
            ctx.stroke();
		}
	}
	
	function connectChild(parenta, parentb, child, treediv){
		var boxa = box(parenta);
		var boxb = box(parentb);
		var boxc = box(child);
		
	    var ctx=treeCanvasCtx(treediv);
        ctx.beginPath();
        var startx = boxa.x+boxa.w + (boxb.x- boxa.x-boxa.w)/2;
        var inty = (boxc.y+boxa.y+boxa.h)/2;
        ctx.moveTo(startx,boxa.y+boxa.h/2);
        ctx.lineTo(startx,inty);
        ctx.lineTo(boxc.x+boxc.w/2,inty);
        ctx.lineTo(boxc.x+boxc.w/2,boxc.y);
        ctx.stroke();
	}
	
});