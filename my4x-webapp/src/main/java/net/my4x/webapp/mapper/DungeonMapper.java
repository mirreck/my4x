package net.my4x.webapp.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.my4x.dungeon.model.Dungeon;
import net.my4x.dungeon.model.Level;
import net.my4x.webapp.model.DungeonDTO;
import net.my4x.webapp.model.LevelDTO;

public class DungeonMapper {

	public static DungeonDTO map(Dungeon dun) {
		DungeonDTO dto = new DungeonDTO();
		dto.setEntrace(dun.getEntrace());
		dto.setLevels(mapLevels(dun.getLevels()));
		dto.setMaxLevel(dun.getMaxLevel());
		dto.setMinLevel(dun.getMinLevel());
		return dto;
	}

	private static Map<Integer, LevelDTO> mapLevels(Collection<Level> levels) {
		
		 Map<Integer, LevelDTO> levelMap = new HashMap<Integer, LevelDTO>();
		 for (Level level : levels) {
			 levelMap.put(level.getLevel(), mapLevel(level));
			}
		return levelMap;
	}

	private static LevelDTO mapLevel(Level level) {
		LevelDTO dto = new LevelDTO();
		dto.setHeight(level.getHeight());
		dto.setIndex(level.getIndex());
		dto.setRooms(level.getRooms());
		dto.setTiles(level.getTiles());
		dto.setWidth(level.getWidth());
		return dto;
	}

}
