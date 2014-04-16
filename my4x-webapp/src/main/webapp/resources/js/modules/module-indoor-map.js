
require(["jquery","modules/jquery-keyboard-plugin"], function($,K) {
	
	var currentfloor = 0;
	var currentx = 3;
	var currenty = 3;
	var mapx = 0;
	var mapy = 0;
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
	
	function init_map(json){
		for (var i=0;i<json.levels.length;i++){
			var level = json.levels[i];
			$( "#indoormap" ).append( '<div class="indoorfloor indoorcurrent" id="floor_'+i+'"><div class="container"></div></div>' );
			$( "#floor_"+i ).data( "level", i);
			//console.log( " WIDTH: " + level.width);
			//console.log( " H: " + level.height);
			
			for (var y=level.height-1;y>=0;y--)
			{
				for (var x=0; x<level.width;x++)
				{
					var styleclass = tileStyle(level.tiles[y*level.width+x]);
					var content = tileContent(level.tiles[y*level.width+x]);
					
					$( "#floor_"+i+" .container" ).append( '<div id="tile_'+i+'_'+x+'_'+y+'" class="indoortile '+styleclass+'" style="left: '+x*100+'px;top: '+(level.height-y)*100+'px;">'+content+'</i></div>' );
				}
			}
		}
	};
	function init_minimap(json){
		for (var i=0;i<json.levels.length;i++){
			var level = json.levels[i];
			$( "#indoorminimap" ).append( '<div id="minifloor_'+i+'">'+level.level+'</div>' );
			var table = $('<table>').addClass('mini');
			for (var y=level.height-1;y>=0;y--)
			{
				
				var row = $('<tr>');
				
				for (var x=0; x<level.width;x++)
				{
					
					var style = tileStyle(level.tiles[y*level.width+x]);
					var content = tileContent(level.tiles[y*level.width+x]);
					var td = $('<td>').addClass(style);
					td.append(content);
					row.append(td);
				}
				table.append(row);
			}	
			$( "#minifloor_"+i ).append(table); 
		}
	};
	
	
	function init_callback(json){
		setData(json);
		init_map(json);
		init_minimap(json);
		
		setCurrentFloor(0);
		init_key_events();
	}
	// SET key events
	function init_key_events(){
		$( "body" ).keyboardEvent($.KeyCodes.UP, function(){
			currentx = inlimits(currentx-1,0,level.width-1);
			moveTo(currentx,currenty);
			}
		);
		$( "body" ).keyboardEvent($.KeyCodes.DOWN, function(){
			currentx = inlimits(currentx+1,0,level.width-1);
			moveTo(currentx,currenty);
			}
		);
		$( "body" ).keyboardEvent($.KeyCodes.LEFT, function(){
			currenty = inlimits(currenty-1,0,level.height-1);
			moveTo(currentx,currenty);
			}
		);
		$( "body" ).keyboardEvent($.KeyCodes.RIGHT, function(){
			currenty = inlimits(currenty+1,0,level.height-1);
			moveTo(currentx,currenty);
			}
		);
		$( "body" ).keyboardEvent($.KeyCodes.PLUS, function(){
			currentfloor++;
			setCurrentFloor(currentfloor);
			}
		);
		$( "body" ).keyboardEvent($.KeyCodes.MINUS, function(){
			currentfloor--;
			setCurrentFloor(currentfloor);
			}
		);
	}
	
	function setData(data){
		jsonData = data;
	}
	function setCurrentFloor(newfloor){
		console.log( " FLOOR: " +newfloor);
		$( "#indoormap .indoorfloor" ).each(function(){
			var floorlevel = parseInt($(this).data( "level" ));
			if(floorlevel < newfloor){
				$(this).addClass( "indoorback" );
				$(this).removeClass( "indoorfront indoorcurrent" );
			} else if(floorlevel == newfloor){
				$(this).addClass( "indoorcurrent" );
				$(this).removeClass( "indoorfront indoorback" );
			} else {
				$(this).addClass( "indoorfront" );
				$(this).removeClass( "indoorback indoorcurrent" );
			}
		});
		moveTo(currentx,currenty);
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
	
	function moveTo(x,y){
		
		level = jsonData.levels[currentfloor];
		targettile = level.tiles[x*level.width+y];
		currenttile = level.tiles[currentx*level.width+currenty];
		if(reachable(currenttile) && !reachable(targettile)){
			return;
		}
		//x = inlimits(x,0,level.width-1);
		//y = inlimits(y,0,level.height-1);
		 //y = level.height -y;
		 mapx = inlimits(x,0,level.width-1);
		 mapy = inlimits(y,0,level.height-1);
		
//		if(x - mapx < 0){
//			mapx = x;
//		} else if(x - mapx > 5){
//			mapx = x-5;
//		}
//		
//		if((level.height-y) - mapy < 0){
//			mapy = (level.height-y);
//		} else if((level.height-y) - mapy > 5){
//			mapy = (level.height-y)-5;
//		}
		console.log( " currenttile: " +currenttile);
		console.log( " MOVE TO: " +x+" "+y);
		console.log( " targettile: " +targettile);
		console.log( " MAP MOVE: " +mapx+" "+mapy);
		$( "#indoormap .indoorfloor .container").css("transform","translate( "+(mapy*-100)+"px,"+(mapx*-100)+"px)");
		console.log( " TILE TO: " +'#indoormap #tile_'+currentfloor+'_'+y+'_'+x);
		$( '#indoormap .currenttile').removeClass( "currenttile" );
		$( '#indoormap #tile_'+currentfloor+'_'+y+'_'+x).addClass( "currenttile" );
		currentx = x;
		currenty = y;
	};
	function reachable(tile){
		return tile ==' ' || tile =='X';
	}
	function tileStyle(tile){
		var styleclass = "tile_wall";
		if(tile == 'R'){
			styleclass = "tile_rock";
		}
		if(tile == ' '){
			styleclass = "tile_floor";
		}
		if(tile == 'X'){
			styleclass = "tile_room";
		} 
		return styleclass;
	}
	
	function tileContent(tile){
		var content = "";
		if(tile == 'S'){
			content = '<i class="icon-ex-stairs-down"></i>';
		}
		if(tile == 'D'){
			content = '<i class="icon-ex-door"></i>';
		} 
		return content;
	}
	
});