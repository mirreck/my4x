package net.my4x.webapp.model;

import java.util.ArrayList;
import java.util.List;

import net.my4x.dungeon.model.Room;

public class LevelDTO {
		private int index;

	   private int width;
	   private int height;
	   private int level;
	   
	   private List<Room> rooms = new ArrayList<Room>();
	   
	   private String tiles;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public String getTiles() {
		return tiles;
	}

	public void setTiles(String tiles) {
		this.tiles = tiles;
	}
	   
	   
	   
	   
}
