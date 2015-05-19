package net.my4x.map.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HeightMap extends AbstractMap implements Iterable<Point>{
   
   private static final Logger LOGGER = LoggerFactory.getLogger(HeightMap.class);
   
   private int[] h;
   private Direction[] directions;
   
   private float initialx;
   private float initialy;
   private float zoom = 1.0f;
   
   
   public HeightMap(int width, int height, float initialx, float initialy){
      super(width,height);
      
      h = new int[width*height];
      directions = new Direction[width*height];
      
      Arrays.fill(h, 0);
      Arrays.fill(directions, Direction.NONE);
      
      this.initialx = initialx;
      this.initialy = initialy;
   }
   
   public HeightMap(int width, int height, float initialx, float initialy, float zoom){
      this(width,  height,  initialx,  initialy);
      this.zoom = zoom;
   }
   
   public Direction direction(int x, int y){
      return directions[x*height+y];
   }
   
   public void computeDirections(){
      LOGGER.debug("computeDirections ...");
      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            directions[i*height+j] = lowestNeighborDirection(i, j);
            //LOGGER.debug("direction ...{} : {} : "+directions[i*height+j],i,j);
         }
      }
   }
   
   public void computeDirections2(){
      LOGGER.debug("computeDirections ...");
      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            
            Map<Direction, Pos> pts = neighboursWithDirection(i, j);
            int facteur = heightAtDirection(pts,Direction.N) + heightAtDirection(pts,Direction.NE) + heightAtDirection(pts,Direction.NO)
                  - heightAtDirection(pts,Direction.S) + heightAtDirection(pts,Direction.SE) + heightAtDirection(pts,Direction.SO);
            
            directions[i*height+j] = lowestNeighborDirection(i, j);
            //LOGGER.debug("direction ...{} : {} : "+directions[i*height+j],i,j);
         }
      }
   }
   
   public int heightAtDirection( Map<Direction, Pos> pts, Direction  d){
      Pos pos = pts.get(d);
      if(pos == null){
         return 0;
      } else {
         return this.getHeight(pos.x, pos.y);
      }
   }
   
   
   
   public void setBaseHeight(int initialHeight){
      Arrays.fill(h, initialHeight);
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

   public int xgradient(int x, int y) {
      if(!isInside(x, y)){
         return 0;
      }
      if(getHeight(x, y) <=0){
         return 0;
      }
      return getHeight(x+1, y-1)+2*getHeight(x+1, y)+getHeight(x+1, y+1)
            -getHeight(x-1, y-1)-2*getHeight(x-1, y)-getHeight(x-1, y+1);
      
   }
   
   public int ygradient(int x, int y) {
      if(!isInside(x, y)){
         return 0;
      }
      if(getHeight(x, y) <=0){
         return 0;
      }
      return getHeight(x+1, y+1)+2*getHeight(x, y+1)+getHeight(x-1, y+1)
            -getHeight(x+1, y-1)-2*getHeight(x, y-1)-getHeight(x-1, y-1);
      
   }
   
   
   public int pente(int x, int y, Direction dir) {
      int res = 0;
      Pos pos = Direction.N.nextPoint(x, y);
      if(!posExists(pos)){
         return 0;
      }
      res += getHeight(pos.x, pos.y);
      pos = Direction.S.nextPoint(x, y);
      if(!posExists(pos)){
         return 0;
      }
      res -= getHeight(pos.x, pos.y);
//      Map<Direction, Pos> pts = neighboursWithDirection(x, y);
//      for (Entry<Direction,Pos> entry : pts.entrySet()) {
//         if(entry.getKey().same(dir)){
//            Pos pos = entry.getValue();
//            res += getHeight(pos.x, pos.y);
//         } else if(entry.getKey().opposite(dir)){
//            Pos pos = entry.getValue();
//            res -= getHeight(pos.x, pos.y);
//         }
//      }
      return res;
   }
   
}
