package net.my4x.map.model;

public enum Direction {
   NONE {
      @Override
      Pos nextPoint(int x, int y) {
         return null;
      }

      @Override
      boolean opposite(Direction dir) {
         return false;
      }
      @Override
      boolean same(Direction dir) {
         return false;
      }
   },
   N {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x - 1, y);
      }
      @Override
      boolean opposite(Direction dir) {
         switch(dir){
            case S:
            case SO:
            case SE:
               return true;
            default : return false;
         }
      }
      @Override
      boolean same(Direction dir) {
         switch(dir){
            case N:
            case NO:
            case NE:
               return true;
            default : return false;
         }
      }
   },
   NE {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x - 1, y + 1);
      }
      @Override
      boolean opposite(Direction dir) {
         switch(dir){
            case S:
            case SO:
            case O:
               return true;
            default : return false;
         }
      }
      @Override
      boolean same(Direction dir) {
         switch(dir){
            case N:
            case E:
            case NE:
               return true;
            default : return false;
         }
      }
   },
   E {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x, y + 1);
      }
      @Override
      boolean opposite(Direction dir) {
         switch(dir){
            case O:
            case SO:
            case NO:
               return true;
            default : return false;
         }
      }
      @Override
      boolean same(Direction dir) {
         switch(dir){
            case E:
            case SE:
            case NE:
               return true;
            default : return false;
         }
      }
   },
   SE {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x + 1, y + 1);
      }
      @Override
      boolean opposite(Direction dir) {
         switch(dir){
            case S:
            case E:
            case SE:
               return true;
            default : return false;
         }
      }
      @Override
      boolean same(Direction dir) {
         switch(dir){
            case N:
            case NO:
            case O:
               return true;
            default : return false;
         }
      }
   },
   S {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x + 1, y);
      }

      @Override
      boolean opposite(Direction dir) {
         switch(dir){
            case N:
            case NO:
            case NE:
               return true;
            default : return false;
         }
      }
      @Override
      boolean same(Direction dir) {
         switch(dir){
            case S:
            case SO:
            case SE:
               return true;
            default : return false;
         }
      }
   },
   SO {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x + 1, y - 1);
      }
      @Override
      boolean opposite(Direction dir) {
         switch(dir){
            case N:
            case E:
            case NE:
               return true;
            default : return false;
         }
      }
      @Override
      boolean same(Direction dir) {
         switch(dir){
            case S:
            case SO:
            case O:
               return true;
            default : return false;
         }
      }
   },
   O {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x, y - 1);
      }
      @Override
      boolean opposite(Direction dir) {
         switch(dir){
            case E:
            case SE:
            case NE:
               return true;
            default : return false;
         }
      }
      @Override
      boolean same(Direction dir) {
         switch(dir){
            case O:
            case NO:
            case SO:
               return true;
            default : return false;
         }
      }
   },
   NO {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x - 1, y - 1);
      }
      @Override
      boolean opposite(Direction dir) {
         switch(dir){
            case S:
            case SE:
            case E:
               return true;
            default : return false;
         }
      }
      @Override
      boolean same(Direction dir) {
         switch(dir){
            case O:
            case NO:
            case N:
               return true;
            default : return false;
         }
      }
   };
   abstract Pos nextPoint(int x, int y);

   abstract boolean opposite(Direction dir);

   abstract boolean same(Direction dir);
}
