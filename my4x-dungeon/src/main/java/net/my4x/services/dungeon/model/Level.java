package net.my4x.services.dungeon.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Level {
   
   private int index;
   
   private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);
   
   
   
   
   private int width;
   private int height;
   private int level;
   private TileType[][] tileMap;
   
   private List<Room> rooms = new ArrayList<Room>();

   private int roomcount = 0;
   
   
   public Level(int width, int height, int level, int index){
      LOGGER.debug("width :"+width);
      LOGGER.debug("height :"+height);
      this.width = width;
      this.height = height;
      tileMap = new TileType[height][width];
      for (int i = 0; i < height; i++) {
         Arrays.fill(tileMap[i], TileType.ROCK);
      }
      this.level = level;
      fillWithRooms();
//      setValue(new Pos(width/2,height-1), TileType.NORTH);
//      setValue(new Pos(width/2,0), TileType.SOUTH);
//      //randomize();
//      for (int i = 0; i < height; i++) {
//         LOGGER.debug("line :"+lineToString(tileMap[i]));
//      }
   }
   

   
   private void randomize(){
      for (int i = 1; i < width -1; i++) {
         for (int j = 1; j < height -1; j++) {
            if(RandomUtils.nextInt(10) > 6){
               setValue(i, j, TileType.FLOOR);
            }
         }
      }    
   }
   public void setValue(Pos pos, TileType v){
      if(reachable(pos)){
         setValue(pos.x, pos.y,v);
      }
   }
   
   private void setValue(int x, int y, TileType v) {
      tileMap[y][x] = v;
   }
   
   public TileType getValue(Pos pos){
      if(!reachable(pos)){
         throw new IllegalArgumentException("Not reachable "+pos);
      }
      return getValue(pos.x, pos.y);
   }
   public TileType getValue(int x, int y){
      return tileMap[y][x];
   }
   
   public boolean isType(int x, int y, TileType t){
      return reachable(new Pos(x,y)) && tileMap[y][x] == t; 
   }
   
   private boolean isWall(Pos pos){
      if(!reachable(pos)){
         return false;
      }
      TileType tileType = tileMap[pos.y][pos.x];
      return tileType == TileType.WALL ||tileType == TileType.SOLIDWALL;
   }
   
   private WallShape shape(Pos pos){
      boolean n = isWall(pos.north())  ;
      boolean s = isWall(pos.south())  ;
      boolean e = isWall(pos.east())  ;
      boolean w = isWall(pos.west())  ;
      return WallShape.fromCode((n?"N":"")+(s?"S":"")+(e?"E":"")+(w?"W":""));
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
      return isBorder(pos.x,pos.y);
   }
   private boolean isBorder(int x,int y){
      return y == 0 || y == height-1 || x == 0 || x  == width-1;
   }
   
   public boolean reachable(Pos pos){
      return pos.y >= 0 && pos.y <height && pos.x >= 0 && pos.x  < width;
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
         //LOGGER.info("digable size= {} ",size);
         maxsize = size;
         size ++;
      }
      return maxsize;
   }

   private class Dims {
      int xmin,xmax,ymin,ymax;
      Dims(int x, int y){
         xmin = xmax = x;
         ymin = ymax = y;
      }
      
      int xsize(){
         return (xmax-xmin);
      }
      int ysize(){
         return (ymax-ymin);
      }
      
      boolean eligible(){
         return xsize() >= 3 && ysize() >= 3;
      }
      @Override
      public String toString() {
         return "Dims [xmin=" + xmin + ", xmax=" + xmax + ", ymin=" + ymin + ", ymax=" + ymax + "]";
      }
   }
   
   public void fillWithRooms(){
      LOGGER.debug("fillWithRooms");

      boolean roomcreated = true;
      int index= 0;
      while(roomcreated){
         roomcreated = false;
         LOGGER.debug("ITER = {}",index++);
         for (int i = 1; i < width -1; i++) {
            for (int j = 1; j < height -1; j++) {
               if(isType(i, j, TileType.ROCK)){
                  Dims dims = growAsSquare(new Dims(i,j));
                  if(dims.eligible()){
                     dims = reduce(dims,5);
                     createRoom(dims);
                     roomcreated = true;
                  } else if(index > 1){
                     LOGGER.debug("x={} y={} no dims = {}",new Object[]{i,j,dims});
                  }
               }
            }
         }
      }
       
   }
   

   private Dims reduce(Dims init, int max){
      LOGGER.debug("reduce dims={}",init);
      Dims res = init;
      
      int width = init.xmax - init.xmin;
      int newwidth = 2 + RandomUtils.nextInt(width-2);
      newwidth = newwidth > max?max:newwidth;
      res.xmin = init.xmin + RandomUtils.nextInt(width-newwidth);
      res.xmax = res.xmin+newwidth;
      
      int height = init.ymax - init.ymin;
      int newheight = 2 + RandomUtils.nextInt(height-2);
      newheight = newheight > max?max:newheight;
      res.ymin = init.ymin + RandomUtils.nextInt(height-newheight);
      res.ymax = res.ymin+newheight;
      
      return res;
   }
   
   private boolean roomAllowed(int x, int y){
      return isType(x, y, TileType.ROCK) && !isBorder(x, y);
   }
   
   private Dims growAsSquare(Dims init){
      Dims res = init;
      boolean left = true,
            right = true,
            up = true,
            down = true;
      while(left || right ||up|| down){
         if(left){
            for (int i = res.ymin; i <= res.ymax; i++) {
               left &= roomAllowed(res.xmin-1, i);
            }
            if(left){
               res.xmin--;
            }
         }
         
         if(right){
            for (int i = res.ymin; i <= res.ymax; i++) {
               right &= roomAllowed(res.xmax+1, i);
            }
            if(right){
               res.xmax++;
            }
         }
         
         if(up){
            for (int i = res.xmin; i <= res.xmax; i++) {
               up &= roomAllowed(i,res.ymax+1);
            }
            if(up){
               res.ymax++;;
            }
         }
         if(down){
            for (int i = res.xmin; i <= res.xmax; i++) {
               down &= roomAllowed(i,res.ymin-1);
            }
            if(down){
               res.ymin--;
            }
         }
         
      }
      return res;
   }
   public void createRoom(Dims dims){
      LOGGER.debug("createRoom dims={}",dims);
      createRoom(dims.xmin, dims.xmax, dims.ymin, dims.ymax);
   }
   
   public void createRoom(int xmin, int xmax, int ymin, int ymax){
      
      roomcount++;
      for (int i = xmin; i <= xmax; i++) {
         for (int j = ymin; j <= ymax; j++) {
            setValue(i,j, TileType.ROOM);
         }
      }
      for (int i = xmin-1; i <= xmax+1; i++) {
         setValue(i, ymin-1, TileType.WALL);
         setValue(i, ymax+1, TileType.WALL);
      }
      
      for (int i = ymin-1; i <= ymax+1; i++) {
         setValue(xmin-1,i, TileType.WALL);
         setValue(xmax+1,i, TileType.WALL);
      }
      rooms.add(new Room(roomcount, xmin, xmax, ymin, ymax, "Test"));
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

   
   public String toString(){
      StringBuilder sb = new StringBuilder();
      for (int y = height-1; y >=0; y--) {
         sb.append("\n[");
         for (int x = 0; x < width; x++) {
            Pos pos = new Pos(x,y);
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
   
   private String lineToString(TileType[] line) {
      StringBuilder sb = new StringBuilder();
      for (TileType tile : line) {
         sb.append(tile.code);
      }
      return sb.toString();
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
   
   public int getWidth() {
      return width;
   }

   public int getHeight() {
      return height;
   }
   
   public int getLevel() {
      return level;
   }



   public List<Room> getRooms() {
      return rooms;
   }



   public int getIndex() {
      return index;
   }


}
