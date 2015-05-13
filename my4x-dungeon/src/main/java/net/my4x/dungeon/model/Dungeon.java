package net.my4x.services.dungeon.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Dungeon {
   
   private int minLevel;
   private int maxLevel;
   
   private Pos entrace;
   
   private Map<Integer, Level> levels;
   
   public Dungeon(int minLevel, int maxLevel, int width, int height) {
      super();
      this.minLevel = minLevel;
      this.maxLevel = maxLevel;
      levels = new HashMap<Integer, Level>();
      entrace = new Pos(width/2,0,0);
      int index = 1;
      for (int i = minLevel; i <= maxLevel; i++) {
         levels.put(new Integer(i), new Level(this,width, width,i,index++));
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

   public Collection<Level> getLevels() {
      return levels.values();
   }

   public Pos getEntrace() {
      return entrace;
   }

   @Override
   public String toString() {
      return "Dungeon [minLevel=" + minLevel + ", maxLevel=" + maxLevel + ", entrace=" + entrace + ", levels=" + levels + "]";
   }
}
