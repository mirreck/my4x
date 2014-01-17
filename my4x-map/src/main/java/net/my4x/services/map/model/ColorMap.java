package net.my4x.services.map.model;

import java.util.Arrays;

public class ColorMap {

   private int[] red;
   private int[] green;
   private int[] blue;

   private int height;
   private int width;
   
   public ColorMap(int width, int height){
      red = new int[width*height];
      green = new int[width*height];
      blue = new int[width*height];      
      Arrays.fill(red, 0);
      Arrays.fill(blue, 0);
      Arrays.fill(red, 0);
      this.width = width;
      this.height = height;
   }
   
   public Color getValue(int x, int y){
      if(x <0 || x >= width || y <0 || y >= height){
         throw new IllegalArgumentException("erreur dans les coordonn�es");
      }
      return new Color(red[x*height+y],green[x*height+y],blue[x*height+y]);
   }
   public void setValue(int x, int y, Color color){
      red[x*height+y] = color.getRed();
      green[x*height+y] = color.getGreen();
      blue[x*height+y] = color.getBlue();
   }

   public int getHeight() {
      return height;
   }

   public int getWidth() {
      return width;
   }
}
