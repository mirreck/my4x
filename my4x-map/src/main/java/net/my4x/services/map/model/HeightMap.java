package net.my4x.services.map.model;

import java.util.Arrays;

public class HeightMap extends AbstractMap{
   
   private float[] data;

   private float initialx;
   private float initialy;
   private float zoom = 1.0f;
   
   
   public HeightMap(int width, int height, float initialx, float initialy){
      super(width,height);
      data = new float[width*height];
      Arrays.fill(data, 0.0f);
      this.initialx = initialx;
      this.initialy = initialy;
   }
   
   public HeightMap(int width, int height, float initialx, float initialy, float zoom){
      this(width,  height,  initialx,  initialy);
      this.zoom = zoom;
   }
   
   public float getValue(int x, int y){
      this.checkCoordinates(x, y);
      return data[x*height+y];
   }
   public void setValue(int x, int y, float value){
      data[x*height+y] = value;
   }

   public float[] getData() {
      return data;
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
