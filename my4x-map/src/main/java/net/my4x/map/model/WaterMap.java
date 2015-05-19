package net.my4x.map.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaterMap extends AbstractMap {


   
   private int[] flow;
   private int[] totalflow;
   private int[] level;
   private boolean[] flag;
   private HeightMap heightMap;
   
   //private Direction[] directions;

   private static final Logger LOGGER = LoggerFactory.getLogger(WaterMap.class);
   
   public WaterMap(HeightMap hMap){
      super(hMap.getWidth(),hMap.getHeight());
      this.heightMap = hMap;
      flow = new int[width*height];
      totalflow = new int[width*height];
      level = new int[width*height];
      flag = new boolean[width*height];
      //directions = new Direction[width*height];
      Arrays.fill(flow, 1);
      Arrays.fill(totalflow, 0);
      Arrays.fill(level, 0);
      Arrays.fill(flag, false);
      //Arrays.fill(directions, Direction.NONE);
   }
   
//   private Direction direction(int x, int y){
//      return directions[x*height+y];
//   }
   
   private boolean hasFlag(int x, int y){
      return flag[x*height+y];
   }
   private void setFlag(int x, int y){
      flag[x*height+y] = true;
   }
   private void resetFlags(){
      Arrays.fill(flag, false);
   }
   
   public void compute(){
      heightMap.computeDirections();
      computeFlows();
   }
   
   
//   private void computeDirections(){
//      LOGGER.debug("computeDirections ...");
//      for (int i = 0; i < width; i++) {
//         for (int j = 0; j < height; j++) {
//            directions[i*height+j] = heightMap.lowestNeighborDirection(i, j);
//            //LOGGER.debug("direction ...{} : {} : "+directions[i*height+j],i,j);
//         }
//      }
//   }
   
   private void computeFlows(){
      LOGGER.debug("computeFlows ...");
      
      //computeFlow(width/2, height/2);
      Random rnd = new Random();
      
      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            if(rnd.nextInt(100)> 95){
               computeFlow(i, j);
            }
         }
      }
   }
   private void computeFlow(int x, int y){
      Direction dir = heightMap.direction(x,y);
      Pos nextPos = dir.nextPoint(x, y);
      Point nextPoint = heightMap.point(nextPos);
      //System.out.print("Flow "+x+" "+y);
      while( dir != Direction.NONE && !heightMap.isBorder(nextPos) && nextPoint.h > 0){
         this.totalflow[nextPos.x*height+nextPos.y] += 1;
         dir = heightMap.direction(nextPos.x,nextPos.y);
         if(dir != Direction.NONE){
            nextPos = dir.nextPoint(nextPos.x,nextPos.y);
            nextPoint = heightMap.point(nextPos);
         }
         //System.out.print(".");
      }
      if(dir == Direction.NONE && nextPos != null && getLevel(nextPos.x, nextPos.y) == 0){
         //System.out.println("LAKE");
         createLake(nextPos.x,nextPos.y);
      }
      //System.out.println("#");
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
      //this.checkCoordinates(x, y);
      
      int height = heightMap.getHeight(x, y);
      
      if( height <= 0){
         // SEA
         return false;
      } else if( getFlow(x, y) == 0){
         // DRY
         return false;
      } else if(getLevel(x, y) > 0){
         //fall in a lake
         this.resetFlow(x, y);
         return false;
      } else {
         // 
         Point p = heightMap.lowestNeighbor(x, y);
         if(p.h < height){
            this.addFlow(p.x, p.y, this.getFlow(x, y));
            this.resetFlow(x, y);
            return true;
         } 
         else if(p.h > height) {
            createLake(x,y);
            //setLevel(x, y, p.h - height);
            //this.resetFlow(x, y);
            return false;
            // lake, increase level
         } else {
            this.resetFlow(x, y);
            return false;
         }
         
      }
   }

   
   private void createLake(int x, int y) {
      resetFlags();
      int h = heightMap.getHeight(x, y);
      int lvl = h+1;
      while(lakeAtHeight(x, y, lvl)){
         raiseLakeLevel(lvl);
         resetFlags();
         lvl++;
         //LOGGER.debug("Lake at: "+x+" "+y+" h="+heightMap.getValue(x, y)+" to:"+lvl);
      }
      LOGGER.debug("Lake at: "+x+" "+y+"from :"+heightMap.getHeight(x, y)+" to:"+lvl);
   }
   
   private boolean lakeAtHeight(int x, int y, int h){
      setFlag(x, y);
      List<Pos> pts = neighbours(x, y);
      if(pts.size() < 8){
         return false;
      }
      for (Pos pos : pts) {
         if(!hasFlag(pos.x, pos.y) &&!(heightMap.getHeight(pos.x, pos.y) > h)){
            if(!lakeAtHeight(pos.x, pos.y, h)){
               return false;
            }
         }
      }
      return true;
   }
   
   private void raiseLakeLevel(int h){
      for (int i = 1; i < width-1; i++) {
         for (int j = 1; j < height-1; j++) {
            if(hasFlag(i, j)){
               setLevel(i, j, h- heightMap.getHeight(i, j));
            }
         }
      }
   }


   
   

   
   


   private int getFlow(int x, int y){
      this.checkCoordinates(x, y);
      return flow[x*height+y];
   }
   public int getLevel(int x, int y){
      return level[x*height+y];
   }
   public void setLevel(int x, int y, int level){
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
