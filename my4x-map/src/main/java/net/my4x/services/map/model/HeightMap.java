package net.my4x.services.map.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class HeightMap extends AbstractMap implements Iterable<Point>{
   
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
   
   
   public Iterator<Point> iterator(){
      return new Iterator<Point>() {
          private int curX=0;
          private int curY=0;
          public boolean hasNext(){
              return curX*height+curY < h.length;
          }
          public Point next(){
              Point point = new Point(curX,curY,getValue(curX, curY));
              curX++;
              if(curX >= width){
                 curX = 0;
                 curY++;
              }
            return point; 
          }

          public void remove(){
              throw new UnsupportedOperationException();
          }
      };
  }
   
   public Point lowestNeighbor(int x, int y){
      List<Pos> pts = neighbours(x, y);
      Point lowest = null;
      for (Pos pos : pts) {
         Point point = new Point(pos, getValue(x, y));
         if(lowest == null || point.h < lowest.h){
            lowest = point;
         }
      }
      return lowest;
   }

   public void removeLakeUnder(int min){
      for (Point point : this) {
         
      }
   }
   
}
