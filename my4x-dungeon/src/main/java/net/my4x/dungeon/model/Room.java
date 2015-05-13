package net.my4x.dungeon.model;

public class Room {
   private int index;
   private int xmin,xmax,ymin,ymax;
   private String name;
   
   
   
   public Room(int index, int xmin, int xmax, int ymin, int ymax, String name) {
      super();
      this.index = index;
      this.xmin = xmin;
      this.xmax = xmax;
      this.ymin = ymin;
      this.ymax = ymax;
      this.name = name;
   }
   public int getIndex() {
      return index;
   }
   public int getXmin() {
      return xmin;
   }
   public int getXmax() {
      return xmax;
   }
   public int getYmin() {
      return ymin;
   }
   public int getYmax() {
      return ymax;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
}
