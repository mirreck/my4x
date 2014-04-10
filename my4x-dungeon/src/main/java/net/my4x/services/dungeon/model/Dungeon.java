package net.my4x.services.dungeon.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Dungeon {
   
   private int minLevel;
   private int maxLevel;
   private int minX;
   private int maxX;
   private int minY;
   private int maxY;
   
   private Pos entrace;
   
   private Map<Integer, Level> levels;
   
   public Dungeon(int minLevel, int maxLevel, int minX, int maxX, int minY, int maxY) {
      super();
      this.minLevel = minLevel;
      this.maxLevel = maxLevel;
      this.minX = minX;
      this.maxX = maxX;
      this.minY = minY;
      this.maxY = maxY;
      levels = new HashMap<Integer, Level>();
      entrace = new Pos(0,0);
      for (int i = minLevel; i <= maxLevel; i++) {
         levels.put(new Integer(i), new Level(maxX - minX, maxY - minY,i));
      }
   }

   public Level getLevel(int i){
      return levels.get(i);
   }
   
   public int getMinLevel() {
      return minLevel;
   }

   public int getMaxLevel() {
      return maxLevel;
   }

   public int getMinX() {
      return minX;
   }

   public int getMaxX() {
      return maxX;
   }

   public int getMinY() {
      return minY;
   }

   public int getMaxY() {
      return maxY;
   }



   @Override
   public String toString() {
      return "Dungeon [minLevel=" + minLevel + ", maxLevel=" + maxLevel + ", minX=" + minX + ", maxX=" + maxX + ", minY=" + minY
            + ", maxY=" + maxY + ", levels=" + levels + "]";
   }

   public Collection<Level> getLevels() {
      return levels.values();
   }

   public Pos getEntrace() {
      return entrace;
   }
}
