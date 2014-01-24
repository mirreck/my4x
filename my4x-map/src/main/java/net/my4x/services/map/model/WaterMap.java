package net.my4x.services.map.model;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaterMap extends AbstractMap {

   private int[] flow;
   private int[] totalflow;
   private int[] level;

   private static final Logger LOGGER = LoggerFactory.getLogger(WaterMap.class);
   
   public WaterMap(HeightMap hMap){
      this(hMap.getWidth(),hMap.getHeight());
   }
   
   public WaterMap(int width, int height){
      super(width,height);
      flow = new int[width*height];
      totalflow = new int[width*height];
      level = new int[width*height];
      Arrays.fill(flow, 1);
      Arrays.fill(totalflow, 0);
      Arrays.fill(level, 0);
   }
   
   public void compute(HeightMap hMap){
      LOGGER.debug("compute water w="+width+" h="+width);
      int count = 1;
      while(resolveFlow(hMap) && count < 50){
         LOGGER.debug("map resolveflow occurence "+count++);
      }
   }
   
   private boolean resolveFlow(HeightMap hMap){
      boolean change = false;
      for (int i = 1; i < width-1; i++) {
         for (int j = 1; j < height-1; j++) {
            change |= resolveFlow(i, j, hMap);
         }
      }
      return change;
   }
   
   private boolean resolveFlow(int x, int y, HeightMap hMap){
      this.checkCoordinates(x, y);
      float height = hMap.getValue(x, y);
      if( getFlow(x, y) == 0 || height <= 0.0f){
         return false;
      } else {
         // find lower level.
         if(height > hMap.getValue(x-1, y)){
            this.addFlow(x-1, y, this.getFlow(x, y));
            this.resetFlow(x, y);
            return true;
         } else if(height > hMap.getValue(x-1, y-1)){
            this.addFlow(x-1, y-1, this.getFlow(x, y));
            this.resetFlow(x, y);
            return true;
         } else if(height > hMap.getValue(x-1, y+1)){
            this.addFlow(x-1, y+1, this.getFlow(x, y));
            this.resetFlow(x, y);
            return true;
         } 
         else if(height > hMap.getValue(x+1, y)){
            this.addFlow(x+1, y, this.getFlow(x, y));
            this.resetFlow(x, y);
            return true;
         } else if(height > hMap.getValue(x+1, y-1)){
            this.addFlow(x+1, y-1, this.getFlow(x, y));
            this.resetFlow(x, y);
            return true;
         } else if(height > hMap.getValue(x+1, y+1)){
            this.addFlow(x+1, y+1, this.getFlow(x, y));
            this.resetFlow(x, y);
            return true;
         } 
         else if(height > hMap.getValue(x, y-1)){
            this.addFlow(x, y-1, this.getFlow(x, y));
            this.resetFlow(x, y);
            return true;
         } else if(height > hMap.getValue(x, y+1)){
            this.addFlow(x, y+1, this.getFlow(x, y));
            this.resetFlow(x, y);
            return true;
         } else{
            // lake, increase level
         }
         
      }
      return false;
   }
//   private lowestNeighbor(int x, int y, HeightMap hMap){
//      float height = hMap.getValue(x, y);
//      
//      return null;
//   }
   private class Point {
      int x,y;
   }

   private int getFlow(int x, int y){
      this.checkCoordinates(x, y);
      return flow[x*height+y];
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
