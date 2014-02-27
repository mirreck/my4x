package net.my4x.services.map.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


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
   
   public int getHeight(int x, int y){
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
              Point point = new Point(curX,curY,getHeight(curX, curY));
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
         Point point = new Point(pos, getHeight(x, y));
         if(lowest == null || point.h < lowest.h){
            lowest = point;
         }
      }
      return lowest;
   }
   
   public Direction lowestNeighborDirection(int x, int y){
      Map<Direction, Pos> pts = neighboursWithDirection(x, y);
      int min = getHeight(x,y);
      Direction res = Direction.NONE;
      for (Entry<Direction,Pos> entry : pts.entrySet()) {
         Pos pos = entry.getValue();
         int value = getHeight(pos.x, pos.y);
         if(value < min){
            min = value;
            res = entry.getKey();
         }
      }
      return res;
   }

   public void removeLakeUnder(int min){
      for (Point point : this) {
         
      }
   }

   public Point point(Pos pos) {
      if(pos == null){
         return null;
      }
      return new Point(pos,getHeight(pos.x, pos.y));
   }
   
}
