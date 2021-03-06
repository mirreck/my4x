require(["jquery","jqueryui"], function($,$ui) {
	
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
		buildTree(json);
		for (var i=0;i<json.personnages.length;i++){
			var perso = json.personnages[i];
			var leaf = $('<div class="bordered leaf" id="'+perso.uuid+'"></div>');
			if(perso.blason != null){
				leaf.append(buildEcu(perso.blason));
			}			
			if(perso.deathDate == null){
				leaf.append('<a href="#">' + perso.firstName+' '+perso.lastName+'</a>');
				leaf.append('<br /><span class="smalltext">' + perso.strBirthDate+'</span>');
			} else {
				leaf.addClass("deceased");
				leaf.append('<span>' + perso.firstName+' '+perso.lastName+'<span>');
				leaf.append('<br /><span class="smalltext">' + perso.strBirthDate+'- &dagger; '+perso.strDeathDate+'</span>');
			}
			
			var container = $('<div class="leaf-container"></div>');
			if(perso.surname != null){
				container.attr("title",perso.surname);
				//leaf.append('<br /><span class="smalltext">' + perso.surname+'<span>');
			}
			
			container.append(leaf);
			
			if(perso.coupleUuid != null){
				var couple = $("div.couple-container div#"+perso.coupleUuid);
				var couplecontainer = $('<div class="couple-container"></div>');
				if(couple.length > 0){
					couplecontainer = couple.parent().parent();
				} else {
					getGenerationdiv(perso.generation,treediv).append(couplecontainer);
				}
				couplecontainer.append(container);
			} else {
				var brotherhood = $("div#brotherhood"+perso.fatherUuid);
				var brotherhoodcontainer = $('<div class="brotherhood-container" id="brotherhood'+perso.fatherUuid+'"></div>');
				if(brotherhood.length > 0){
					brotherhoodcontainer = brotherhood;
				} else {
					getGenerationdiv(perso.generation,treediv).append(brotherhoodcontainer);
				}
				brotherhoodcontainer.append(container);
			}
			console.log( "Perso: " + perso.firstName+" "+perso.lastName );
		}
		$( window ).resize({"json" : json, "treediv" :treediv},function(event) {
			console.log("resize canvas");
			initCanvas(json,treediv);
		});
		initCanvas(json,treediv);
		// for debugging
		//$( treediv ).tooltip({ hide: {duration: 1000000 } });
		$( treediv ).tooltip();
	};
	function buildEcu(blason){
		
		var ecu = $('<span class="fa-stack"></span>');
		for (var i=0;i<blason.length;i++){
			var part = blason[i];
			ecu.append('<i class="icon-ex-ecu'+part.figure.toLowerCase()+' '+part.color.toLowerCase()+'"></i>');
		}
		return ecu;
	}
	function buildTree(json){
		var familyTree = {"originaldata" : json, "root":{}};
		for (var i=0;i<json.personnages.length;i++){
			var perso = json.personnages[i];
			if(perso.fatherUuid == null){
				if(perso.gender == "M"){
					familyTree.root.father = perso;
				}else{
					familyTree.root.mother = perso;
				}
			}
		}
		window.familyTree = familyTree;
	}
	
	function getGenerationdiv(gen,treediv){
		if($("#generation_"+gen).length > 0){
			return $("#generation_"+gen);
		} else {
			//console.log("add generation layer*"+gen+"*");
			if(gen > 0){
				prev = getGenerationdiv(gen-1,treediv);
				console.log("gen found:"+prev.attr("id")+"*");
				var generation = $('<div id="generation_'+gen+'" class="generation"></div>');
				generation.insertBefore(prev);
				return generation;
			} else {
				var generation = $('<div id="generation_'+gen+'" class="generation"></div>');
				treediv.append(generation);
				return generation;
			}
			
		}
	}
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
		var canvas = treediv.find("canvas");
		if(canvas.length == 0){
			console.log("new canvas");
			newcanvas = $('<canvas></canvas>');
			treediv.prepend(newcanvas);
			newcanvas.zIndex(treediv.zIndex()-1);
			 var w = treediv.outerWidth(true);
			 var h = treediv.outerHeight(true);
			 newcanvas.css("width",w+"px").attr("width",w);
			 newcanvas.css("height",h+"px").attr("height",h); 
			return newcanvas;
		} else {
			return canvas;	
		}
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
		if(pos == undefined){
			console.debug('erreur de recherche de '+eltid);
		}
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
		    lines(ctx, [{x:boxa.x + boxa.w,			y:boxa.y+boxa.h/2},
		                {x:boxb.x,			y:boxa.y+boxa.h/2}]);
		}
	}
	
	function connectChild(parenta, parentb, child, treediv){
		var boxa = box(parenta);
		var boxb = box(parentb);
		var boxc = box(child);
		var startx = boxa.x+boxa.w + (boxb.x- boxa.x-boxa.w)/2;
		var inty = (boxc.y+boxa.y+boxa.h)/2;
		
	    var ctx=treeCanvasCtx(treediv);
	    lines(ctx, [{x:startx,			y:boxa.y+boxa.h/2},
	                {x:startx,			y:inty},
	                {x:boxc.x+boxc.w/2,	y:inty},
	                {x:boxc.x+boxc.w/2,	y:boxc.y}]);
	}
	
	function lines(ctx, tab){
		 ctx.save();
	    ctx.strokeStyle = 'rgba(0,0,0,1.0)';
	    ctx.lineWidth = 1;
    	ctx.beginPath();
    	// round and +0.5 are used to avoid anti-aliasing
        ctx.moveTo(Math.round(tab[0].x) + 0.5,Math.round(tab[0].y) + 0.5);
        for ( var int = 1; int < tab.length; int++) {
        	ctx.lineTo(Math.round(tab[int].x) + 0.5,Math.round(tab[int].y) + 0.5);
		}
        ctx.stroke();
        ctx.restore();
	}
	
});