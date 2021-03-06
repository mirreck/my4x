
require(["jquery","modules/jquery-keyboard-plugin"], function($,K) {
	
	var currentPosition ={"x":0,"y":0,"z":0};
	var mapx = 0;
	var mapy = 0;
	var localmapx = 1;
	var localmapy = 1;
	
	var jsonData = null;

	$(".indoormap").each(function(){
		var map = $(this);
		init(map);
	});
	
	
	function init(mapdiv){
		$.getJSON( "rest/dungeon/5", { id: "5" } )
			.done(function( json ) {
				//console.log( "JSON Level: " + Object.keys(json.levels).length );
				init_callback(json);
			})
			.fail(function( jqxhr, textStatus, error ) {
				var err = textStatus + ", " + error;
				console.log( "Request Failed: " + err );
			});
	}
	
	function init_callback(json){
		setData(json);
		init_map(json);
		init_minimap(json);
		updateToPosition({"x":json.entrace.x,"y":json.entrace.y,"z":json.entrace.z});
		setCurrentFloor(json.entrace.z);
		init_key_events();
	}
	
	
	function init_map(json){
	debugger;
		var pnjindex= 1;
		for (var i=json.minLevel;i<=json.maxLevel;i++){
			var level = json.levels[i];
			level.index = i;
			$( "#indoormap" ).append( '<div class="indoorfloor indoorcurrent" id="floor_'+i+'"><div class="bglayer"></div><div id="bottomlayer" class="container"></div><div id="middlelayer" class="container"></div><div id="toplayer" class="container"></div></div>' );
			$( "#floor_"+i ).data( "level", i);
			$( "#floor_"+i ).attr( "data-level", level.level);
			//console.log( " WIDTH: " + level.width);
			//console.log( " H: " + level.height);
			
			for (var y=level.height-1;y>=0;y--)
			{
				for (var x=0; x<level.width;x++)
				{	
					var tile = level.tiles[y*level.width+x];
					var styleclass = tileStyle(tile);
					var content = tileBottomContent(tile);
					var topcontent = tileTopContent(tile);
					var shape = tileShape(level, x, y);
					$( "#floor_"+i+" #bottomlayer" ).append( '<div id="tile_'+x+'_'+y+'_'+i+'" class="'+styleclass+' sh'+shape+'" style="left: '+x*100+'px;top: '+(level.height-y)*100+'px;">'+content+'</i></div>' );
					if(topcontent != ''){
						$( "#floor_"+i+" #toplayer" ).append( '<div id="tile_'+x+'_'+y+'_'+i+'" class="door sh'+shape+'" style="left: '+x*100+'px;top: '+(level.height-y)*100+'px;"></i></div>' );
					}
					if(tile == ' ' && Math.floor((Math.random() * 10) + 1) > 8){
						var pnj = $('<div id="pnj_'+pnjindex+'" class="pnj" ><span class="fa-stack"><i class="icon-ex-pj_p sable"></i><i class="icon-ex-pj_h argent"></i><i class="icon-ex-pj_f sable"></i><i class="icon-ex-pj_hair orange"></i></span></div>');
						
						$( "#floor_"+i+" #middlelayer" ).append(pnj);
						$('#pnj_'+pnjindex).css("transform","translate( "+(x*100)+"px,"+((level.height-y)*100)+"px)");
						pnjindex++;
					}
				}
			}
			
			var pj = $('<div id="pj" ><span class="fa-stack"><i class="icon-ex-pj_p sable"></i><i class="icon-ex-pj_h argent"></i><i class="icon-ex-pj_f sable"></i><i class="icon-ex-pj_hair or"></i></span></div>');
			$( "#floor_"+i+" #middlelayer" ).append(pj);
		}
		
		$('#indoormap > div ').sort(function(a, b){
			return parseInt($(a).attr( "data-level")) > parseInt($(b).attr("data-level")) ? 1 : -1;
		}).appendTo('#indoormap');
	};
	function tileShape(level, x, y){
		var res = "";
		if(reachablexy(level,x+1,y)){
			res += "E";
		}
		if(reachablexy(level,x-1,y)){
			res += "O";
		}
		if(reachablexy(level,x,y+1)){
			res += "N";
		}
		if(reachablexy(level,x,y-1)){
			res += "S";
		}
		if(res == ""){
			if(reachablexy(level,x+1,y+1)){
				res += "ne";
			}
			if(reachablexy(level,x-1,y+1)){
				res += "no";
			}
			if(reachablexy(level,x+1,y-1)){
				res += "se";
			}
			if(reachablexy(level,x-1,y-1)){
				res += "so";
			}
		}
		return res;
	}
	function init_minimap(json){
		for (var i=json.minLevel;i<=json.maxLevel;i++){
			var level = json.levels[i];
			$( "#indoorminimap" ).append( '<div id="minifloor_'+i+'">'+level.level+'</div>' );
			$( "#minifloor_"+i ).attr( "data-level", level.level);
			var table = $('<table>').addClass('mini');
			for (var y=level.height-1;y>=0;y--)
			{
				var row = $('<tr>');
				for (var x=0; x<level.width;x++)
				{
					var style = tileStyle(level.tiles[y*level.width+x]);
					var content = tileMiniContent(level.tiles[y*level.width+x]);
					var td = $('<td>').addClass(style).attr("id","mini_"+x+"_"+y+"_"+i);
					td.append(content);
					row.append(td);
				}
				table.append(row);
			}	
			$( "#minifloor_"+i ).append(table); 
		}
		$('#indoorminimap > div ').sort(function(a, b){
		    return parseInt($(a).attr( "data-level")) < parseInt($(b).attr("data-level")) ? 1 : -1;
		}).appendTo('#indoorminimap');
	};
	
	function bumpAnim(element, name){
		element.bind('animationend webkitAnimationEnd oAnimationEnd', function(){
			element.removeClass(name+"_anim").css('animation-name', '');;
		});
		
		element.addClass(name+"_anim").css('animation-name', name);
	}

	// SET key events
	function init_key_events(){
		$( "body" ).keyboardEvent($.KeyCodes.UP, function(){
				var pos = checkPosition(movePos(currentPosition, 0,1, 0));
				if(pos.x == currentPosition.x && pos.y == currentPosition.y && pos.z == currentPosition.z){
					bumpAnim($(".indoorcurrent #pj span"), 'bumpn');
				} else {
					updateToPosition(pos);
				}
			}
		);
		$( "body" ).keyboardEvent($.KeyCodes.DOWN, function(){
			var pos = checkPosition(movePos(currentPosition, 0,-1, 0));
			if(pos.x == currentPosition.x && pos.y == currentPosition.y && pos.z == currentPosition.z){
				bumpAnim($(".indoorcurrent #pj span"), 'bumps');
			} else {
				updateToPosition(pos);
			}
			}
		);
		$( "body" ).keyboardEvent($.KeyCodes.LEFT, function(){
			updateToPosition(checkPosition(movePos(currentPosition, -1,0, 0)));
			}
		);
		$( "body" ).keyboardEvent($.KeyCodes.RIGHT, function(){
			updateToPosition(checkPosition(movePos(currentPosition, 1,0, 0)));
			}
		);
		$( "body" ).keyboardEvent($.KeyCodes.PLUS, function(){
			updateToPosition(checkPosition(movePos(currentPosition, 0,0, 1)));
			}
		);
		$( "body" ).keyboardEvent($.KeyCodes.MINUS, function(){
			updateToPosition(checkPosition(movePos(currentPosition, 0,0, -1)));
			}
		);
	}
	
	function setData(data){
		jsonData = data;
	}
	
	function movePos(pos, diffx,diffy, diffz){
		
		return {"x" : pos.x+diffx, "y": pos.y +diffy, "z": pos.z +diffz};
	}
	
	function checkPosition(pos){
		if(pos.z < jsonData.minLevel
			|| pos.z > jsonData.maxLevel){
			//console.log("rejectz:"+pos.x+" "+pos.y+" "+pos.z);
			return currentPosition;
		}
		level = getLevel(pos.z);
		if(pos.x < 0 || pos.x >= level.width){
			//console.log("rejectx:"+pos.x+" "+pos.y+" "+pos.z);
			return currentPosition;
		}
		if(pos.y < 0 || pos.y >= level.height){
			//console.log("rejecty:"+pos.x+" "+pos.y+" "+pos.z);
			return currentPosition;
		}
		var targettile = level.tiles[pos.y*level.width+pos.x];
		var currenttile = level.tiles[currentPosition.y*level.width+currentPosition.x];
		if( reachable(currenttile) && !reachable(targettile)){
			console.log("reject bad tile:"+pos.x+" "+pos.y+" "+pos.z);
			return currentPosition;
		}
		//console.log( " OK: "+pos.x+" "+pos.y+" "+pos.z+"=>"+targettile);
		return pos;
	}
	function getLevel(z){
		var level = jsonData.levels[z];
		if(level){
			return level;
		}
		for (var i=0;i<jsonData.levels.length;i++){
			var level = jsonData.levels[i];
			if(level.level == z){
				return level;
			}
		}
		return null;
	}
	function setCurrentFloor(newfloor){
		for (var i=jsonData.minLevel;i<=jsonData.maxLevel;i++){
			var level = jsonData.levels[i];
			if(level.level < newfloor){
				$( "#floor_"+i ).addClass( "indoorback" ).removeClass( "indoorfront indoorcurrent" );
			} else if(level.level == newfloor){
				$( "#floor_"+i ).addClass( "indoorcurrent" ).removeClass( "indoorfront indoorback" );
			} else if(level.level > newfloor){
				$( "#floor_"+i ).addClass( "indoorfront" ).removeClass( "indoorback indoorcurrent" );
			}
		}
		updateToPosition(currentPosition);
		currentfloor = newfloor;
	}
	function inlimits(x,min,max){
		if(x < min){
			return min;
		}
		if(x> max){
			return max;
		}
		return x;
	}

	
	function updateToPosition(nextPosition){
		console.log("updateToPosition nextPosition:"+nextPosition.x+" "+nextPosition.y+" "+nextPosition.z);
		level = getLevel(nextPosition.z);
		if(nextPosition.z != currentPosition.z){
			setCurrentFloor(nextPosition.z);
		}
		var diffx = nextPosition.x - currentPosition.x;
		var diffy = nextPosition.y - currentPosition.y;
		localmapx +=diffx;
		localmapy +=diffy;
		
		if(localmapx < 1){
			localmapx = 1;
			mapx = nextPosition.x - localmapx;
		} else if(localmapx > 5){
			localmapx = 5;
			mapx = nextPosition.x - localmapx;
		}
		
		if(localmapy < 1){
			localmapy = 1;
			mapy = nextPosition.y - localmapy;
		} else if(localmapy > 5){
			localmapy = 5;
			mapy = nextPosition.y - localmapy;
		}
		 
		 $( "#indoormap .indoorfloor .container").css("transform","translate( "+(mapx*-100)+"px,"+((level.height-6-mapy)*-100)+"px)");
		 
		updateMini(nextPosition);
		updateMain(nextPosition);
		
		currentPosition = nextPosition;
	};
	
	function updateMini(pos, index){
		$("td.currenttile").removeClass( "currenttile" );
		$("#mini_"+pos.x+"_"+pos.y+"_"+getLevel(pos.z).index).addClass( "currenttile" );
	};
	function updateMain(pos, index){
		// compute map move
		//$( '#indoormap .currenttile #user').remove();
		//$( '#indoormap .currenttile').removeClass( "currenttile" );
		
		//var pj = $('<span id="user" class="fa-stack"><i class="icon-ex-pj_p sable"></i><i class="icon-ex-pj_h argent"></i><i class="icon-ex-pj_f sable"></i><i class="icon-ex-pj_hair sable"></i></span>');
		//$( '#indoormap #tile_'+pos.x+"_"+pos.y+"_"+getLevel(pos.z).index).addClass( "currenttile" ).prepend(pj);
		$(".indoorcurrent #pj").css("transform","translate( "+(pos.x*100)+"px,"+((getLevel(pos.z).height-pos.y)*100)+"px)");
	};
	
	function reachablexy(level,x,y){
		if(x<1 || y<1|| x>level.width-1 || y>level.height-1){
			return false;
		}
		var tile = level.tiles[y*level.width+x];
		return tile ==' ' || tile =='X'|| tile =='S';
	}
	
	function reachable(tile){
		return tile ==' ' || tile =='X'|| tile =='S'|| tile =='D';
	}
	function tileStyle(tile){
		var styleclass = "tile_wall";
		if(tile == 'R'){
			styleclass = "tile_rock";
		}
		if(tile == ' '){
			styleclass = "tile_floor";
		}
		if(tile == 'S'){
			styleclass = "tile_floor";
		}
		if(tile == 'X'){
			styleclass = "tile_room";
		} 
		if(tile == 'D'){
			styleclass = 'tile_wall door';
		}
		if(tile == 'E'){
			styleclass = 'tile_wall door';
		}
		return styleclass;
	}
	
	function tileBottomContent(tile){
		if(tile == 'S'){
			return '<i class="icon-ex-stairs-down"></i>';
		}
		return "";
	}
	function tileTopContent(tile){
		if(tile == 'D'|| tile == 'E'){
			return '<div class="door"></div>';
		}
		return "";
	}
	
	function tileMiniContent(tile){
		var content = "";
		if(tile == 'S'){
			content = '<i class="icon-ex-stairs-down"></i>';
		}
		if(tile == 'D'){
			content = '<i class="icon-ex-door"></i>';
		}
		if(tile == 'E'){
			content = '<i class="icon-ex-door gueules"></i>';
		} 
		return content;
	}
	
});