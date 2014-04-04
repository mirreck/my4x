package net.my4x.services.dungeon.model;

import java.util.Arrays;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Level {
   
   
   private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);
   
   public enum TileType { ROCK('R'), SOLIDWALL('Z'), WALL('W'), FLOOR(' '), DOOR('D'),ENTRACE('E'), STAIRS('S'), ROOM('X'); 
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
   
//   public enum WallShape{NONE('?'),A('A'),B('B'),C('C'),D('D'),AB('a'),AC('b'),BD('c'),CD('d'),ABD('╔'),ABC('╚'),ACD('╝'),BCD('╗'),ABCD('O');
//      public final char code;  
//      WallShape(char code){
//         this.code = code;
//      }
//      public static WallShape fromCode(String val) {
//         for (WallShape type : WallShape.values()) {
//            if(type.name().equals(val)){
//               return type;
//            }
//         }
//         LOGGER.debug("code not found :"+val);
//         return NONE;
//      }
//   }
   
   public enum WallShape{NONE('?'),N('═'),S('═'),E('║'),W('║'),NS('═'),NE('╔'),NW('╚'),SE('╗'),SW('╝'),EW('║'),NSE('╦'),NSW('╩'),NEW('╠'),SEW('╣'),NSEW('╬');
   public final char code;  
   WallShape(char code){
      this.code = code;
   }
   public static WallShape fromCode(String val) {
      for (WallShape type : WallShape.values()) {
         if(type.name().equals(val)){
            return type;
         }
      }
      LOGGER.debug("code not found :"+val);
      return NONE;
   }
}
   
   
   private int width;
   private int height;
   private int level;
   private TileType[][] tileMap;
   
   
   public Level(int width, int height, int level){
      this.width = width;
      this.height = height;
      tileMap = new TileType[width][height];
      for (int i = 0; i < height; i++) {
         Arrays.fill(tileMap[i], TileType.ROCK);
      }
      this.level = level; 
      //randomize();
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
   public TileType getValue(int x, int y){
      return tileMap[x][y];
   }
   
   private boolean isWall(Pos pos){
      if(!reachable(pos)){
         return false;
      }
      TileType tileType = tileMap[pos.x][pos.y];
      return tileType == TileType.WALL ||tileType == TileType.SOLIDWALL;
   }
   
   private WallShape shape(Pos pos){
      boolean n = isWall(pos.north())  ;
      boolean s = isWall(pos.south())  ;
      boolean e = isWall(pos.east())  ;
      boolean w = isWall(pos.west())  ;
      return WallShape.fromCode((n?"N":"")+(s?"S":"")+(e?"E":"")+(w?"W":""));
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
      wallsAround(pos);
      wallsAround(ahead);
      return ahead;
   }

   public void wallsAround(Pos pos) {
      Pos[] neighbors = pos.neighbors();
      for (Pos position : neighbors) {
         if (reachable(position)) {
            TileType value = getValue(position);
            if (value == TileType.ROCK || value == TileType.SOLIDWALL || value == TileType.WALL) {
               wall(position);
            }
         }

      }
   }
   public void wall(Pos pos){
      TileType type = TileType.WALL;
      WallShape shape = shape(pos);
      if(shape == WallShape.N
         || shape == WallShape.S
         || shape == WallShape.E
         || shape == WallShape.W
         || shape == WallShape.SEW
         || shape == WallShape.NEW
         || shape == WallShape.NSE
         || shape == WallShape.NSW
         ) {
         type = TileType.WALL;
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
      //LOGGER.info("digable {} {} A={}:{}, L={}, R={}",new Object[]{pos,dir,ahead,digable(ahead),isNotFloor(left),isNotFloor(right)});
      return   digable(ahead) 
            && !isFloor(left) 
            && !isFloor(right);
      
   }
   
   public boolean isFloor(Pos pos){
      return  reachable(pos) && getValue(pos)  == TileType.FLOOR;
   }
   private boolean isWallCorner(Pos pos){
      if(!reachable(pos)){
         return false;
      }
      WallShape shape = shape(pos);
      if(shape == WallShape.NW
            || shape == WallShape.NE
            || shape == WallShape.SE
            || shape == WallShape.SW
            ) {
            return true;
         }
      return false;
   }
   public boolean digable(Pos pos){
      if(isWallCorner(pos)){
            return false;
         //LOGGER.info("digable {} => {} / {}",new Object[]{pos, reachable(pos), getValue(pos)});
      }
      return reachable(pos)
            && !isBorder(pos)
            && (getValue(pos)  == TileType.ROCK || getValue(pos) == TileType.WALL);
   }
   
   private boolean isBorder(Pos pos){
      return pos.y == 0 || pos.y == width-1 || pos.x == 0 || pos.x  == height-1;
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
      for (int i = 0; i < height; i++) {
         sb.append("\n[");
         for (int j = 0; j < width; j++) {
            Pos pos = new Pos(i,j);
            //if(isWall(pos)) {
            if(getValue(pos) == TileType.WALL) {
               sb.append(shape(pos).code);
            } else{
               sb.append(getValue(pos).code);
            }
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

   public int roomdigable(Pos position, Direction dir) {
      int size = 3;
      int maxsize = 0;
      for(;maxsize < 5; maxsize++){
         for (int i = 1; i <=size+1; i++) {
            for (int j = -1*(size/2)-1; j <= size/2+1; j++) {
               if(!digable(dir.next(position, i, j))){
                  return maxsize;
               };
            }
         }
         LOGGER.info("digable size= {} ",size);
         maxsize = size;
         size ++;
      }
      
     
      return maxsize;
   }

   public Pos digroom(Pos position, Direction dir) {
      setValue(position, TileType.DOOR);
      setValue(dir.next(position, 0, 1), TileType.SOLIDWALL);
      setValue(dir.next(position, 0, -1), TileType.SOLIDWALL);
      int size = roomdigable(position, dir);
      int halfsize = size/2;
      for (int i = 1; i <= size; i++) {
         for (int j = -1*halfsize; j <= halfsize; j++) {
            Pos pos = dir.next(position, i, j);
            setValue(pos, TileType.ROOM);
            wallsAround(pos);
         }
      }
      
      Pos exit = dir.next(position,halfsize+1, halfsize+1);
      if(roomdigable(exit, dir.right()) > 2){
         setValue(exit, TileType.DOOR);
         return exit;
      }
      exit = dir.next(position, halfsize+1, -1-halfsize);
      if(roomdigable(exit, dir.left()) >2){
         setValue(exit, TileType.DOOR);
         return exit;
      }
     exit = dir.next(position, size+1, 0);
     setValue(exit, TileType.DOOR);
     return exit;
      
   }

   public int getLevel() {
      return level;
   }


}
