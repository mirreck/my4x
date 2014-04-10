package net.my4x.services.dungeon.model;


public enum TileType {
   ROCK('R'), SOLIDWALL('Z'), WALL('W'), FLOOR(' '), DOOR('D'), ENTRACE('E'), STAIRS('S'), ROOM(' ')
    , NORTH('N'), SOUTH('S')
   ;
   public final char code;

   TileType(char code) {
      this.code = code;
   }

   public static TileType fromCode(char charAt) {
      for (TileType type : TileType.values()) {
         if (type.code == charAt) {
            return type;
         }
      }
      return null;
   }
}
