package net.my4x.services.dungeon.model;

public enum Direction {
   N, S, E, O;
   public Direction left() {
      switch (this) {
         case N:
            return O;
         case S:
            return E;
         case E:
            return N;
         case O:
         default:
            return S;
      }
   }

   public Direction right() {
      switch (this) {
         case N:
            return E;
         case S:
            return O;
         case E:
            return S;
         case O:
         default:
            return N;
      }
   }

   public Pos next(Pos current, int diffa, int difft) {
      switch (this) {
         case N:
            return new Pos(current.x+difft, current.y+diffa);
         case S:
            return new Pos(current.x-difft, current.y-diffa);
         case E:
            return new Pos(current.x+diffa, current.y-difft);
         case O:
         default:
            return new Pos(current.x-diffa, current.y+difft);
      }
   }
   
   public Pos next(Pos current) {
      switch (this) {
         case N:
            return new Pos(current.x, current.y+1);
         case S:
            return new Pos(current.x, current.y-1);
         case E:
            return new Pos(current.x+1, current.y);
         case O:
         default:
            return new Pos(current.x-1, current.y);
      }
   }

}
