package net.my4x.services.map.model;

import java.util.Arrays;

public class ZoneMap extends AbstractMap {

   private int[] zone;

   
   public ZoneMap(int width, int height){
      super(width,height);
      zone = new int[width*height];
      Arrays.fill(zone, 0);
   }

   
   public int getValue(int x, int y){
      this.checkCoordinates(x, y);
      return zone[x*height+y];
   }
   public void setValue(int x, int y, int value){
      zone[x*height+y] = value;
   }
}
