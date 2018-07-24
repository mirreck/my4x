package net.my4x.dungeon.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import net.my4x.dungeon.model.Direction;
import net.my4x.dungeon.model.Position;
import net.my4x.dungeon.model.Room;
import net.my4x.dungeon.model.Tile;
import net.my4x.dungeon.model.TileType;
import net.my4x.dungeon.utils.RoomUtils;

import org.springframework.stereotype.Service;

import com.github.mirreck.RandomUtils;
import com.google.common.collect.Lists;

@Service
@Slf4j
public class RoomService {

	public enum DigStrategy {
		CAVE, SQUARE, ROUND, GROW;
	}


	public Room create(Options opts) {

		// ArrayList<Tile> tiles = Lists.<Tile>newArrayList();
		Map<Position, TileType> tiles = new HashMap<>();
		Direction dir = opts.dir;
		Position door = opts.start.to(dir);
		tiles.put(door, TileType.DOOR);
		tiles.put(door.to(dir.left()), TileType.WALL);
		tiles.put(door.to(dir.right()), TileType.WALL);
		ArrayList<Direction> dirs = Lists.newArrayList(dir, dir.left(),
				dir.right());
		Random rnd = new Random();
		switch(opts.strategy){
		case SQUARE : createSquare(tiles, opts ); break;
		case CAVE :
		default:
			createCave(opts, tiles, dir, door, dirs, rnd); 
			break;
		}
		tiles = surroundWithWalls(tiles);
		return Room.builder().name("Room 1").tiles(tiles)
				.box(RoomUtils.box(tiles.keySet())).build();
	}
	private void createCave(Options opts, Map<Position, TileType> tiles,
			Direction dir, Position door, ArrayList<Direction> dirs, Random rnd) {
		if(opts.exits == null || opts.exits.isEmpty()){
			for (int i = 0; i < 20; i++) {
				door = dig(tiles, dir, door);
				dir = RandomUtils.randomElement(rnd, dirs);
				log.debug("Dir = {}", dir);
			}
		} else {
			Position exit = opts.exits.get(0);
			int i = 0;
			while(!door.equals(exit) && i <20) {
				door = dig(tiles, dir, door);
				dir = directionTo(door, exit);
				log.debug("Dir = {} Pos={}", dir, door);
				i++;
			}
			tiles.put(exit, TileType.DOOR);
		}
	}
	private void createSquare(Map<Position, TileType> tiles, Options opts) {
		// TODO Auto-generated method stub
		
	}
	private Map<Position, TileType> surroundWithWalls(Map<Position, TileType> tiles){
		Set<Entry<Position, TileType>> entrySet = tiles.entrySet();
		Map<Position, TileType> res = new HashMap<Position, TileType>(tiles);
		for (Entry<Position, TileType> entry : entrySet) {
			if(entry.getValue() == TileType.FLOOR){
				for (Direction direction : Direction.values()) {
					Position position = entry.getKey().to(direction);
					Position position2 = position.to(direction.left());
					if(tiles.get(position) == null){
						res.put(position, TileType.WALL);
					}
					if(tiles.get(position2) == null){
						res.put(position2, TileType.WALL);
					}
				}
				
			}
		}
		return res;
	}
	public Direction directionTo(Position from, Position to){
		int dx = to.getX() - from.getX();
		int dy = to.getY() - from.getY();
		log.debug("from = {} to={}", from, to);
		if(Math.abs(dx) > Math.abs(dy)){
			return dx>0 ?Direction.E:Direction.O;
		} else {
			return dy>0 ?Direction.N:Direction.S;
		}
	}
	private Position dig(Map<Position, TileType> tiles, Direction dir,
			Position init) {
		Position floor = init.to(dir);
		setTile(tiles, floor, TileType.FLOOR);
//		setTile(tiles, floor.to(dir.left()), TileType.WALL);
//		setTile(tiles, floor.to(dir.right()), TileType.WALL);
//		Position wall = floor.to(dir);
//		setTile(tiles, wall, TileType.WALL);
//		setTile(tiles, wall.to(dir.left()), TileType.WALL);
//		setTile(tiles, wall.to(dir.right()), TileType.WALL);
		return floor;
	}

	private void setTile(Map<Position, TileType> tiles, Position pos,
			TileType type) {
		TileType tileType = tiles.get(pos);
		if (tileType != TileType.FLOOR && tileType != TileType.DOOR) {
			tiles.put(pos, type);
		}

	}

}
