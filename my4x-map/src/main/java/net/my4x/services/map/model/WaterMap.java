package net.my4x.services.map.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaterMap extends AbstractMap {

   private int[] flow;
   private int[] totalflow;
   private float[] level;
   private HeightMap heightMap;

   private static final Logger LOGGER = LoggerFactory.getLogger(WaterMap.class);
   
   public WaterMap(HeightMap hMap){
      super(hMap.getWidth(),hMap.getHeight());
      this.heightMap = hMap;
      flow = new int[width*height];
      totalflow = new int[width*height];
      level = new float[width*height];
      Arrays.fill(flow, 1);
      Arrays.fill(totalflow, 0);
      Arrays.fill(level, 0);
   }
   
   public void compute(){
      LOGGER.debug("compute water w="+width+" h="+width);
      int count = 1;
      while(resolveFlow()){
         LOGGER.debug("map resolveflow occurence "+count++);
      }
   }
   
   private boolean resolveFlow(){
      boolean change = false;
      for (int i = 1; i < width-1; i++) {
         for (int j = 1; j < height-1; j++) {
            change |= resolveFlow(i, j);
         }
      }
      return change;
   }
   
   private boolean resolveFlow(int x, int y){
      this.checkCoordinates(x, y);
      
      float height = heightMap.getValue(x, y);
      
      if( getFlow(x, y) == 0 || height <= 0.0f){
         return false;
      } else if(getLevel(x, y) > 0){
         //fall in a lake
         this.resetFlow(x, y);
         return false;
      } else {
         Point p = lowestNeighbor(x, y);
         if(p.h < height){
            this.addFlow(p.x, p.y, this.getFlow(x, y));
            this.resetFlow(x, y);
            return true;
         } else {
            createLake(x,y);
            return true;
            // lake, increase level
         }
         
      }
   }
   private void createLake(int x, int y) {
      // TODO Auto-generated method stub
      float height = heightMap.getValue(x, y);
      height ++;
      LOGGER.debug("Lake height : "+height);
      Point pt = raiseNeighboursTo(x,y,height);
   }

   private Point raiseNeighboursTo(int x, int y, float f) {
      Point lowest = new Point(x, y);
      LOGGER.debug("raiseNeighboursTo x= {}, y={}, f={}, p.h={}",new Object[] {x,y,f,lowest.h+getLevel(x,y)});
      if(lowest.h+getLevel(x,y)-f < -0.01){
         setLevel(x, y, f-lowest.h);
//         List<Point> pts = neighbours(x, y);
//         for (Point point : pts) {
//            Point lower = raiseNeighboursTo(point.x, point.y, f);
//            if(lower.h < lowest.h){
//               lowest = lower;
//            }
//         }
      }
      return lowest;
   }

//   private Point lowestLakeNeighbor(int x, int y){
//      
//   }

   
   private Point lowestNeighbor(int x, int y){
      Point lowest = new Point(x, y);
      List<Pos> pts = neighbours(x, y);
      for (Pos pos : pts) {
         Point point = new Point(pos);
         if(point.h < lowest.h){
            lowest = point;
         }
      }
      return lowest;
   }
   
   
   private class Point extends Pos{
      float h;
      private Point(int x, int y, float h) {
         super(x,y);
         this.h = h;
      }
      private Point(Pos pos) {
         this(pos.x,pos.y);
      }
      private Point(int x, int y) {
         super(x,y);
         this.h = heightMap.getValue(x, y);
      }
   }

   private int getFlow(int x, int y){
      this.checkCoordinates(x, y);
      return flow[x*height+y];
   }
   public float getLevel(int x, int y){
      return level[x*height+y];
   }
   public void setLevel(int x, int y, float level){
      this.level[x*height+y] = level;
   }
   public void addFlow(int x, int y,int flow){
      this.flow[x*height+y] += flow;
      this.totalflow[x*height+y] += flow;
   }
   public void resetFlow(int x, int y){
      this.flow[x*height+y] = 0;
   }
   
   public int getFinalFlow(int x, int y) {
      return totalflow[x*height+y];
   }
   
   public int[] getFinalFlowMap() {
      return totalflow;
   }

   
}
