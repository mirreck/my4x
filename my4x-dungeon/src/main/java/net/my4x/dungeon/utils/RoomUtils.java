package net.my4x.dungeon.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import net.my4x.dungeon.model.Box;
import net.my4x.dungeon.model.Position;
import net.my4x.dungeon.model.Room;
import net.my4x.dungeon.model.TileType;

@Slf4j
public class RoomUtils {
	   
		public static String toDisplayString(Room room){
			Map<Position, TileType> tiles = room.getTiles();
			Box box = room.getBox();
			StringBuilder sb = new StringBuilder();
			for (int i = box.getMaxy(); i >= box.getMiny(); i--) {
			//for (int i =5; i >= -5; i--) {
				for (int j = box.getMinx(); j <= box.getMaxx(); j++) {
				//for (int j = -5; j <= 5; j++) {
					TileType tileType = tiles.get(new Position(j,i));
					sb.append(tileType== null?" ":tileType.code);
				}
				sb.append("\n");
			}
		   return sb.toString();
	   }
		public static Box box(Set<Position> positions){
			int maxx = Integer.MIN_VALUE;
			int minx = Integer.MAX_VALUE;
			int maxy = Integer.MIN_VALUE;
			int miny = Integer.MAX_VALUE;
			for (Position position : positions) {
				if(position.getX()> maxx){
					maxx = position.getX();
				}
				if(position.getX()< minx){
					minx = position.getX();
				}
				if(position.getY()> maxy){
					maxy = position.getY();
				}
				if(position.getY()< miny){
					miny = position.getY();
				}
			}
			return Box.builder().maxx(maxx).minx(minx).maxy(maxy).miny(miny).build();
		}
}
