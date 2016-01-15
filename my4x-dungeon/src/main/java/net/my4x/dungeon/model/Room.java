package net.my4x.dungeon.model;

import java.util.Map;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(exclude="tiles")
public class Room {
   private int index;
   private Box box;
   private String name;
   private  Map<Position,TileType> tiles;

}
