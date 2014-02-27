package net.my4x.services.map.model;

public enum Direction {
   NONE {
      @Override
      Pos nextPoint(int x, int y) {
         return null;
      }
   },
   N {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x - 1, y);
      }
   },
   NE {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x - 1, y + 1);
      }
   },
   E {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x, y + 1);
      }
   },
   SE {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x + 1, y + 1);
      }
   },
   S {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x + 1, y);
      }
   },
   SO {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x + 1, y - 1);
      }
   },
   O {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x, y - 1);
      }
   },
   NO {
      @Override
      Pos nextPoint(int x, int y) {
         return new Pos(x - 1, y - 1);
      }
   };
   abstract Pos nextPoint(int x, int y);
}
