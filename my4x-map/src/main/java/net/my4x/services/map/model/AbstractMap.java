package net.my4x.services.map.model;

import java.util.ArrayList;
import java.util.List;

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
   public void checkCoordinates(int x, int y){
      if(x <0 || x >= width || y <0 || y >= height){
         throw new IllegalArgumentException("erreur dans les coordonn�es");
      }
   }
   public boolean pointExists(int x, int y){
      return x >= 0 && x < width && y >= 0 && y < height;
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
   
   
}
