
require(["jquery","modules/jquery-keyboard-plugin"], function($,L) {
	
	var currentfloor = 0;
	var currentx = 0;
	var currenty = 0;
	var mapx = 0;
	var mapy = 0;
	var jsonData = null;
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
	function moveTo(x,y){
		
		level = jsonData.levels[currentfloor];
		if(x < 1){
			x = currentx = 1;
			mapx = 0;
		}
		if(y < 1){
			y = currenty = 1;
			mapy = 0;
		}
		if(x - mapx < 0){
			mapx = x;
		} else if(x - mapx > 5){
			mapx = x-5;
		}
		
		if(y - mapy < 0){
			mapy = y;
		} else if(y - mapy > 5){
			mapy = y-5;
		}
		
		console.log( " MOVE TO: " +x+" "+y);
		console.log( " MAP MOVE: " +mapx+" "+mapy);
		$( "#indoormap .indoorfloor .container").css("transform","translate( "+(mapy*-100)+"px,"+(mapx*-100)+"px)");
		console.log( " TILE TO: " +'#indoormap #tile_'+currentfloor+'_'+y+'_'+x);
		$( '#indoormap .currenttile').removeClass( "currenttile" );
		$( '#indoormap #tile_'+currentfloor+'_'+y+'_'+x).addClass( "currenttile" );
		currentx = x;
		currenty = y;
	}
	
	
	$( "body" ).keyboardEvent($.KeyCodes.UP, function(){
		currentx--;
		moveTo(currentx,currenty);
		}
	);
	$( "body" ).keyboardEvent($.KeyCodes.DOWN, function(){
		currentx++;
		moveTo(currentx,currenty);
		}
	);
	$( "body" ).keyboardEvent($.KeyCodes.LEFT, function(){
		currenty--;
		moveTo(currentx,currenty);
		}
	);
	$( "body" ).keyboardEvent($.KeyCodes.RIGHT, function(){
		currenty++;
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
	
	$.getJSON( "rest/dungeon/5", { id: "5" } )
		.done(function( json ) {
			console.log( "JSON Level: " + Object.keys(json.levels).length );
			setData(json);
			for (var i=0;i<json.levels.length;i++){
			//var i = 0;
			//for (key in json.levels) {
				console.log( " KEY: " +i);
				var level = json.levels[i];
				$( "#indoormap" ).append( '<div class="indoorfloor indoorcurrent" id="floor_'+i+'"><div class="container"></div></div>' );
				$( "#floor_"+i ).data( "level", i);
				console.log( " WIDTH: " + level.width);
				console.log( " H: " + level.height);
				for (var x=0; x<level.width;x++)
				{
					for (var y=0;y<level.height;y++)
					{
						var styleclass = "tile_wall";
						if(level.tiles[x*level.width+y] == 'R'){
							styleclass = "tile_rock";
						}
						if(level.tiles[x*level.width+y] == ' '){
							styleclass = "tile_floor";
						}
						if(level.tiles[x*level.width+y] == 'X'){
							styleclass = "tile_room";
						} 
						var content = "";
						if(level.tiles[x*level.width+y] == 'S'){
							content = '<i class="icon-stairs-down"></i>';
						}
						if(level.tiles[x*level.width+y] == 'D'){
							content = '<i class="icon-door"></i>';
						}
						
						$( "#floor_"+i+" .container" ).append( '<div id="tile_'+i+'_'+x+'_'+y+'" class="indoortile '+styleclass+'" style="left: '+x*100+'px;top: '+y*100+'px;">'+content+'</i></div>' );
					}
				}
			}
			setCurrentFloor(0);
		})
		.fail(function( jqxhr, textStatus, error ) {
			var err = textStatus + ", " + error;
			console.log( "Request Failed: " + err );
		});
	
	
	
});