package net.my4x.services.dungeon.model;

import java.util.Arrays;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Level {
   
   public enum TileType { ROCK('R'), SOLIDWALL('W'), WALL('w'), FLOOR(' '), DOOR('D'),ENTRACE('E'), STAIRS('S'), ROOM('X'); 
      public final char code;  
      TileType(char code){
         this.code = code;
      }
      public static TileType fromCode(char charAt) {
         for (TileType type : TileType.values()) {
            if(type.code == charAt){
               return type;
            }
         }
         return null;
      }
   }
   
   private int width;
   private int height;
   
   private TileType[][] tileMap;
   
   private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);
   
   public Level(int width, int height){
      this.width = width;
      this.height = height;
      tileMap = new TileType[width][height];
      for (int i = 0; i < height; i++) {
         Arrays.fill(tileMap[i], TileType.ROCK);
      }
      randomize();
   }
   
   // for json serialization
   public void randomize(){
      for (int i = 1; i < height-1; i++) {
         for (int j = 1; j < width-1; j++) {
            if(RandomUtils.nextInt(10) > 6){
               setValue(i, j, TileType.FLOOR);
            }
         }
      }    
   }
   
   public TileType getValue(Pos pos){
      if(!reachable(pos)){
         throw new IllegalArgumentException("Not reachable "+pos);
      }
      return tileMap[pos.x][pos.y];
   }
   
   public void setValue(int x, int y, TileType v){
      //LOGGER.info("set {} {} {}",new Object[]{x,y,v});
      tileMap[x][y] = v;
   }
   
   public void setValue(Pos pos, TileType v){
      if(reachable(pos)){
         setValue(pos.x, pos.y,v);
      }
   }
   
   
   public Pos dig(Pos pos, Direction dir){
      Pos ahead = dir.next(pos);
      setValue(ahead, TileType.FLOOR);
      wallsAround(ahead);
      return ahead;
   }
   public void wallsAround(Pos pos){
      Pos[] neighbors = pos.neighbors();
      for (Pos position : neighbors) {
         if(reachable(position) && getValue(position)  == TileType.ROCK){
            wall(position);
         }
         
      }
   }
   public void wall(Pos pos){
      TileType type = TileType.WALL;
      Pos[] neighbors2 = {pos.plusXY(1,1), pos.plusXY(-1,1), pos.plusXY(1,-1), pos.plusXY(-1,-1)};
      for (Pos p : neighbors2) {
         if(reachable(p) && getValue(p)  != TileType.ROCK){
            type = TileType.SOLIDWALL;
         }
      }
      boolean ns = reachable(pos.plusX(1)) && getValue(pos.plusX(1))  != TileType.ROCK;
      ns &= reachable(pos.plusX(-1)) && getValue(pos.plusX(-1))  != TileType.ROCK;
      boolean oe = reachable(pos.plusY(1)) && getValue(pos.plusY(1))  != TileType.ROCK;
      oe &= reachable(pos.plusY(-1)) && getValue(pos.plusY(-1))  != TileType.ROCK;
      
      if(ns && oe){
         type = TileType.SOLIDWALL;
      }
      setValue(pos, type);
   }
   
   public void stairs(Pos position) {
      setValue(position, TileType.STAIRS);
      wallsAround(position);
   }
   
   public boolean digable(Pos pos, Direction dir){
      //LOGGER.info("digable {} {} ",new Object[]{pos,dir});
      Pos ahead = dir.next(pos);
      Pos left = dir.left().next(ahead);
      Pos right = dir.right().next(ahead);
      Pos next = dir.next(ahead);
      //LOGGER.info("digable {} {} A={}, L={}, R={}",new Object[]{pos,dir,ahead,left,right});
      return   digable(ahead) 
            && isNotFloor(left) 
            && isNotFloor(right) 
            && isNotFloor(next);
      
   }
   
   public boolean isNotFloor(Pos pos){
      return  !reachable(pos) || getValue(pos)  != TileType.FLOOR;
   }
   
   public boolean digable(Pos pos){
      //LOGGER.info("digable {} => {} / {}",new Object[]{pos, reachable(pos), getValue(pos)});
      return reachable(pos) 
            && (getValue(pos)  == TileType.ROCK || getValue(pos) == TileType.WALL);
   }
   
   public boolean reachable(Pos pos){
      return pos.y >= 0 && pos.y <width && pos.x >= 0 && pos.x  < height;
   }
   
   // for json serialization
   public String getTiles(){
      StringBuilder sb = new StringBuilder();
      for (TileType[] line : tileMap) {
         for (TileType tile : line) {
            sb.append(tile.code);
         }
      }
      return sb.toString();
   }
   
   public String toString(){
      StringBuilder sb = new StringBuilder();
      for (TileType[] line : tileMap) {
         sb.append("\n[");
         for (TileType tile : line) {
            sb.append(tile.code);
         }
         sb.append("]");
      }
      sb.append("\n");
      return sb.toString();
   }

   public int getWidth() {
      return width;
   }

   public int getHeight() {
      return height;
   }

   public boolean roomdigable(Pos position, Direction dir) {
      for (int i = 1; i <=4; i++) {
         for (int j = -2; j <= 2; j++) {
            if(!digable(dir.next(position, i, j))){
               return false;
            };
         }
      }
     
      return true;
   }

   public Pos digroom(Pos position, Direction dir) {
      for (int i = 1; i <= 3; i++) {
         for (int j = -1; j <= 1; j++) {
            Pos pos = dir.next(position, i, j);
            setValue(pos, TileType.ROOM);
            wallsAround(pos);
         }
      }

      return dir.next(position, 3, 0);
   }


}
