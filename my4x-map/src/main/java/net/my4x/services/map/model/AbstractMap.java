package net.my4x.services.map.model;

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
   
}
