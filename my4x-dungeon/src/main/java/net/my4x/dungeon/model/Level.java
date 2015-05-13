package net.my4x.services.dungeon.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Level {
   
   private int index;
   
   private Dungeon dungeon;
   
   private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);
   
   
   
   
   private int width;
   private int height;
   private int level;
   private TileType[][] tileMap;
   
   private List<Room> rooms = new ArrayList<Room>();

   private int roomcount = 0;
   
   
   public Level(Dungeon dungeon,int width, int height, int level, int index){
      LOGGER.debug("width :"+width);
      LOGGER.debug("height :"+height);
      this.width = width;
      this.height = height;
      tileMap = new TileType[height][width];
      for (int i = 0; i < height; i++) {
         Arrays.fill(tileMap[i], TileType.ROCK);
      }
      this.level = level;
      this.dungeon = dungeon;
      //fillWithRooms();
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
                     //LOGGER.debug("x={} y={} no dims = {}",new Object[]{i,j,dims});
                  }
               }
            }
         }
      }
       
   }
   
   public void fillWithDoors(){
      
         for (int i = 0; i < width -5; i++) {
            for (int j = 0; j < height; j++) {
               if(isType(i, j, TileType.ROOM) && isType(i+1, j, TileType.WALL) && isType(i+2, j, TileType.WALL) && isType(i+3, j, TileType.ROOM)){
                  //if(RandomUtils.nextInt(4)>2){
                     setValue(i+1, j, TileType.DOOR);
                     setValue(i+2, j, TileType.DOOR);
                  //}
               }
            }
         }
         for (int i = 0; i < width; i++) {
            for (int j = 0; j < height -5; j++) {
               if(isType(i, j, TileType.ROOM) && isType(i, j+1, TileType.WALL) && isType(i, j+2, TileType.WALL) && isType(i, j+3, TileType.ROOM)){
                  //if(RandomUtils.nextInt(4)>1){
                  setValue(i, j+1, TileType.DOOR);
                  setValue(i, j+2, TileType.DOOR);
                  //}
               }
            }
         }
       
   }
   

   private Dims reduce(Dims init, int max){
      LOGGER.debug("reduce dims={}",init);
      Dims res = init;
      
      int width = init.xmax - init.xmin;
      //int newwidth = 2 + RandomUtils.nextInt(width-2);
      int newwidth = width;
      newwidth = newwidth > max?max:newwidth;
      if(newwidth < width){
         res.xmin = init.xmin + RandomUtils.nextInt(width-newwidth);
      } else {
         res.xmin = init.xmin;
      }
     
      res.xmax = res.xmin+newwidth;
      
      int height = init.ymax - init.ymin;
      //int newheight = 2 + RandomUtils.nextInt(height-2);
      int newheight = height;
      newheight = newheight > max?max:newheight;
      if(newheight < height){
         res.ymin = init.ymin + RandomUtils.nextInt(height-newheight);
      } else {
         res.ymin = init.ymin;
      }
      res.ymax = res.ymin+newheight;
//      res.xmax = res.xmin+2;
//      res.ymax = res.ymin+2;
      
      return res;
   }
   
   private boolean roomAllowed(int x, int y){
      boolean res = isType(x, y, TileType.ROCK);
      return res;
   }
   
   private Dims growAsSquare(Dims init){
      Dims res = init;
      boolean left = true,
            right = true,
            up = true,
            down = true;
      while(right ||up|| left|| down){
         if(left){
            for (int i = res.ymin; i <= res.ymax; i++) {
               
               boolean roomAllowed = roomAllowed(res.xmin-1, i);
               left &= roomAllowed;
            }
            if(left){
               res.xmin--;
            }
            if(res.xmax -res.xmin > 5){
               left = false;
               right= false;
            }
         }
         
         if(right){
            for (int i = res.ymin; i <= res.ymax; i++) {
               right &= roomAllowed(res.xmax+1, i);
            }
            if(right){
               res.xmax++;
            }
            if(res.xmax -res.xmin > 5){
               left = false;
               right= false;
            }
         }
         
         if(up){
            for (int i = res.xmin; i <= res.xmax; i++) {
               up &= roomAllowed(i,res.ymax+1);
            }
            if(up){
               res.ymax++;;
            }
            if(res.ymax -res.ymin > 5){
               up = false;
               down= false;
            }
         }
         if(down){
            for (int i = res.xmin; i <= res.xmax; i++) {
               down &= roomAllowed(i,res.ymin-1);
            }
            if(down){
               res.ymin--;
            }
            if(res.ymax -res.ymin > 5){
               up = false;
               down= false;
            }
         }
         
      }
      return res;
   }
   public void createRoomRecursive(Dims dims) {
      //LOGGER.debug("createRoomRecursive dims={}",dims);
      createRoomRecursive(dims.xmin, dims.xmax, dims.ymin, dims.ymax);
   }
   public void createRoomRecursive(int xmin, int xmax, int ymin, int ymax) {
      createRoom(xmin, xmax, ymin, ymax);
      roomFrom(xmin + (xmax-xmin)/2, ymax, xmin + (xmax-xmin)/2, ymax+1);
      roomFrom(xmin + (xmax-xmin)/2, ymin, xmin + (xmax-xmin)/2, ymin-1);
      roomFrom(xmin, ymin+(ymax-ymin)/2, xmin-1, ymin+(ymax-ymin)/2);
      roomFrom(xmax, ymin+(ymax-ymin)/2, xmax+1, ymin+(ymax-ymin)/2);
      if(upperLevel() != null){
         roomFromLevel(xmin + (xmax-xmin)/2, ymin+(ymax-ymin)/2, upperLevel());
      } else if(lowerLevel() != null){
         roomFromLevel(xmin + (xmax-xmin)/2, ymin+(ymax-ymin)/2, lowerLevel());
      }
   }
   private void roomFrom(int doorx, int doory, int x, int y) {
      if(isType(x, y, TileType.ROCK)){
         Dims dims = growAsSquare(new Dims(x, y));
         if(dims.eligible()){
            //dims = reduce(dims,5);
            createRoomRecursive(dims);
            setValue(doorx, doory, TileType.DOOR);
            setValue(x, y, TileType.DOOR);
            
//            for (int i = 0; i < width; i++) {
//               for (int j = 0; j < height -5; j++) {
//                  if(isType(i, j, TileType.ROOM) && isType(i, j+1, TileType.WALL) && isType(i, j+2, TileType.WALL) && isType(i, j+3, TileType.ROOM)){
//                     //if(RandomUtils.nextInt(4)>1){
//                     setValue(i, j+1, TileType.DOOR);
//                     setValue(i, j+2, TileType.DOOR);
//                     //}
//                  }
//               }
//            }
            
         }
      }
   }

   private void roomFromLevel(int x, int y, Level level) {
      if(level.isType(x, y, TileType.ROCK)){
         Dims dims = level.growAsSquare(new Dims(x, y));
         if(dims.eligible()){
            //dims = reduce(dims,5);
            level.createRoomRecursive(dims);
            setValue(x, y, TileType.STAIRS);
            level.setValue(x, y, TileType.STAIRS);
         }
      }
   }

   private Level upperLevel(){
      return this.dungeon.getLevel(this.level +1);
   }
   
   private Level lowerLevel(){
      return this.dungeon.getLevel(this.level +1);
   }
   

   public void createRoom(Dims dims){
      LOGGER.debug("createRoom dims={}",dims);
      createRoom(dims.xmin, dims.xmax, dims.ymin, dims.ymax);
   }
   
   public void createRoom(int xmin, int xmax, int ymin, int ymax){
      
      roomcount++;
      for (int i = xmin+1; i <= xmax-1; i++) {
         for (int j = ymin+1; j <= ymax-1; j++) {
            setValue(i,j, TileType.ROOM);
         }
      }
      for (int i = xmin; i <= xmax; i++) {
         if(getValue(i, ymin)!= TileType.ROOM){
            setValue(i, ymin, TileType.WALL);
         }
         if(getValue(i, ymax)!= TileType.ROOM){
            setValue(i, ymax, TileType.WALL);
         }
      }
      
      for (int i = ymin; i <= ymax; i++) {
         if(getValue(xmin, i)!= TileType.ROOM){
            setValue(xmin,i, TileType.WALL);
         }
         if(getValue(xmax, i)!= TileType.ROOM){
            setValue(xmax,i, TileType.WALL);
         }
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
