package net.my4x.services.map.model;

import java.util.Arrays;

public class ColorMap extends AbstractMap {

   private int[] red;
   private int[] green;
   private int[] blue;
   
   private int[] alpha;

   
   public ColorMap(int width, int height){
      super(width,height);
      red = new int[width*height];
      green = new int[width*height];
      blue = new int[width*height]; 
      alpha = new int[width*height]; 
      Arrays.fill(red, 0);
      Arrays.fill(blue, 0);
      Arrays.fill(red, 0);
      Arrays.fill(alpha, 255);
   }
   
   public int getRgb(int x, int y){
      this.checkCoordinates(x, y);
      return ((alpha[x*height+y] & 0xFF) << 24) |
             ((red  [x*height+y] & 0xFF) << 16) |
             ((green[x*height+y] & 0xFF) << 8)  |
             ((blue [x*height+y] & 0xFF) << 0);
   }
   
   public Color getValue(int x, int y){
      this.checkCoordinates(x, y);
      return new Color(red[x*height+y],green[x*height+y],blue[x*height+y]);
   }
   public void setValue(int x, int y, Color color){
      red[x*height+y] = color.getRed();
      green[x*height+y] = color.getGreen();
      blue[x*height+y] = color.getBlue();
   }
   
   public void setValue(int x, int y, int r, int g, int b, int a){
      red[x*height+y] = r;
      green[x*height+y] = g;
      blue[x*height+y] = b;
      alpha[x*height+y] = a;
   }
   
   public ColorMap appendLayer(ColorMap layer){
      for (int i = 0; i < red.length; i++) {
         if(layer.alpha[i] > 250){
            red[i] = layer.red[i];
            green[i] = layer.green[i];
            blue[i] = layer.blue[i];
         }
      }
      return this;
   }
}
