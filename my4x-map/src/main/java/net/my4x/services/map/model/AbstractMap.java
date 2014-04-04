package net.my4x.services.map.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMap {
   protected final int height;
   protected final int width;
   public AbstractMap(int height, int width) {
      super();
      this.height = height;
      this.width = width;
   }
   public int getHeight() {
      return height;
   }
   public int getWidth() {
      return width;
   }
   public boolean isInside(int x, int y){
      return x > 0 && x < width-1 && y > 0 && y < height-1;
   }
   
   public void checkCoordinates(int x, int y){
      if(x <0 || x >= width || y <0 || y >= height){
         throw new IllegalArgumentException("erreur dans les coordonn�es");
      }
   }
   
   public List<Pos> neighbours(int x,int y){
      List<Pos> res = new ArrayList<Pos>();
      int[] nx = {x-1, x-1, x-1, x  , x  , x+1, x+1, x+1};
      int[] ny = {y-1, y,   y+1, y-1, y+1, y-1,   y, y+1};
      for (int i = 0; i < ny.length; i++) {
         if(this.pointExists(nx[i], ny[i])){
            res.add( new Pos(nx[i], ny[i]));
         }
      }
      return res;
   }
   
   public Map<Direction, Pos> neighboursWithDirection(int x,int y){
      Map<Direction, Pos> res = new HashMap<Direction, Pos>();
      for (Direction direction : Direction.values()) {
         Pos next = direction.nextPoint(x, y);
         if(posExists(next)){
            res.put(direction, next);
         }
      }
      return res;
   }
   
   public boolean pointExists(int x, int y){
      return x >= 0 && x < width && y >= 0 && y < height;
   }
   public boolean posExists(Pos pos){
      return pos != null && pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height;
   }
   public boolean isBorder(Pos pos){
      return pos.x == 0 
          || pos.x == width-1 
          || pos.y == 0 
          || pos.y == height-1;
   }
   
}
