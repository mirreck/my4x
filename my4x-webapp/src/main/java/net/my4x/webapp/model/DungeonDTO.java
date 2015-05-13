package net.my4x.webapp.model;

import java.util.Map;

import net.my4x.dungeon.model.Pos;

public class DungeonDTO {

	
	
	   private int minLevel;
	   private int maxLevel;
	   
	   private Pos entrace;
	   
	   private Map<Integer, LevelDTO> levels;

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public Pos getEntrace() {
		return entrace;
	}

	public void setEntrace(Pos entrace) {
		this.entrace = entrace;
	}

	public Map<Integer, LevelDTO> getLevels() {
		return levels;
	}

	public void setLevels(Map<Integer, LevelDTO> levels) {
		this.levels = levels;
	}
	


}
