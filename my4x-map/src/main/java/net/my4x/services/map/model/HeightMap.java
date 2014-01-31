package net.my4x.services.map.model;

import java.util.Arrays;

public class HeightMap extends AbstractMap{
   
   private int[] h;

   private float initialx;
   private float initialy;
   private float zoom = 1.0f;
   
   
   public HeightMap(int width, int height, float initialx, float initialy){
      super(width,height);
      h = new int[width*height];
      Arrays.fill(h, 0);
      this.initialx = initialx;
      this.initialy = initialy;
   }
   
   public HeightMap(int width, int height, float initialx, float initialy, float zoom){
      this(width,  height,  initialx,  initialy);
      this.zoom = zoom;
   }
   
   public int getValue(int x, int y){
      this.checkCoordinates(x, y);

      return h[x*height+y];
   }
   public void setValue(int x, int y, int value){
      h[x*height+y] = value;
   }

   public int[] getData() {
      return h;
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
