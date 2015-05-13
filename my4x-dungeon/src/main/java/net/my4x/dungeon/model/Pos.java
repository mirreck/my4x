package net.my4x.dungeon.model;

public class Pos {
   
   public Pos(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   
   public Pos(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public int x = 0,y  = 0, z = 0;
   
   public Pos[] neighbors(){
      return new Pos[]{
            new Pos(x-1,y-1),
            new Pos(x-1,y),
            new Pos(x-1,y+1),
            
            new Pos(x,y-1),
            new Pos(x,y+1),
            
            new Pos(x+1,y-1),
            new Pos(x+1,y),
            new Pos(x+1,y+1),
      };
   }

   public Pos north(){
      return plusY(1);
   }
   public Pos south(){
      return plusY(-1);
   }
   public Pos west(){
      return plusX(-1);
   }
   public Pos east(){
      return plusX(1);
   }
   
   public Pos plusX(int x){
      return new Pos(this.x+x,this.y);
   }
   public Pos plusY(int y){
      return new Pos(this.x,this.y+y);
   }
   public Pos plusXY(int x,int y){
      return new Pos(this.x+x,this.y+y);
   }
   @Override
   public String toString() {
      return "Pos [x=" + x + ", y=" + y + "]";
   }
}
