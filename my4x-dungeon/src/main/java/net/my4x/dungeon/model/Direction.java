package net.my4x.dungeon.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public enum Direction {
   N, S, E, O;
   static Map<Direction, Direction> counterclockwise = ImmutableMap.of(
		   N, O, 
		   S, E, 
		   E, N, 
		   O, S);
   static Map<Direction, Direction> clockwise = ImmutableMap.of(
		   N, E, 
		   S, O, 
		   E, S, 
		   O, N);
   public Direction left() {
	  return counterclockwise.get(this);
   }

   public Direction right() {
	   return clockwise.get(this);
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
