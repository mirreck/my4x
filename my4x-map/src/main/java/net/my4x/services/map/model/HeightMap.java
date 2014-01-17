package net.my4x.services.map.model;

import java.util.Arrays;

public class HeightMap {
   
   private float[] data;

   private int height;
   private int width;
   private float initialx;
   private float initialy;
   private float zoom = 1.0f;
   
   
   public HeightMap(int width, int height, float initialx, float initialy){
      data = new float[width*height];
      Arrays.fill(data, 0.0f);
      this.width = width;
      this.height = height;
      this.initialx = initialx;
      this.initialy = initialy;
   }
   
   public HeightMap(int width, int height, float initialx, float initialy, float zoom){
      this(width,  height,  initialx,  initialy);
      this.zoom = zoom;
   }
   
   public float getValue(int x, int y){
      if(x <0 || x >= width || y <0 || y >= height){
         throw new IllegalArgumentException("erreur dans les coordonn�es");
      }
      return data[x*height+y];
   }
   public void setValue(int x, int y, float value){
      data[x*height+y] = value;
   }

   public float[] getData() {
      return data;
   }

   public int getHeight() {
      return height;
   }

   public int getWidth() {
      return width;
   }

   public float getInitialx() {
      return initialx;
   }

   public float getInitialy() {
      return initialy;
   }

   public float getZoom() {
      return zoom;
   }

}
