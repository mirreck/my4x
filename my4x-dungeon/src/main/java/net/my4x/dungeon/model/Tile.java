package net.my4x.dungeon.model;

import lombok.Value;

@Value
public class Tile {
	private TileType type;
	private Position position;
}
