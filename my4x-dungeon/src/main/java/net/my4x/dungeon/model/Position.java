package net.my4x.dungeon.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class Position {
	int x;
	int y;
	public Position to(Direction dir){
		return to(dir,1);
	}
	public Position to(Direction dir, int dist){
		switch(dir){
		 	case N:
	            return new Position(x,y+dist);
	         case S:
	        	 return new Position(x,y-dist);
	         case E:
	        	 return new Position(x+dist,y);
	         case O:
	         default:
	        	 return new Position(x-dist,y);
		}
	}
	
	public String stringVal(){
		return x+"_"+y;
	}
}
