package net.my4x.dungeon.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Box {
	private int minx, maxx, miny, maxy;
}
