package net.my4x.services.map.model;


public class Point  extends Pos{
   int h;
   public Point(Pos pos, int h) {
      this(pos.x,pos.y,h);
   }
   public Point(int x, int y, int h) {
      super(x,y);
      this.h = h;
   }
}