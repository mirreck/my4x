package net.my4x.services.dungeon.digger;

import net.my4x.services.dungeon.model.Level;
import net.my4x.services.dungeon.model.Level.TileType;
import net.my4x.services.dungeon.model.Pos;


public enum DiggerAction {
   DIG(100) {
      @Override
      public boolean actionAvailable(Digger dig) {
         return dig.currentLevel().digable(dig.getPosition(), dig.getDir());
      }

      @Override
      public void performAction(Digger dig) {
         Pos ahead = dig.currentLevel().dig(dig.getPosition(), dig.getDir());
         dig.setPosition(ahead);
         
      }
   },  DIG180(10) {
      @Override
      public boolean actionAvailable(Digger dig) {
         return dig.currentLevel().digable(dig.getPosition(), dig.getDir().left().left());
      }

      @Override
      public void performAction(Digger dig) {
         Pos ahead = dig.currentLevel().dig(dig.getPosition(), dig.getDir().left().left());
         dig.setPosition(ahead);
         
      }
   }, DIGROOM(100) {
      @Override
      public boolean actionAvailable(Digger dig) {
         return dig.currentLevel().roomdigable(dig.getPosition(), dig.getDir());
      }
      @Override
      public void performAction(Digger dig) {
         Pos ahead = dig.currentLevel().digroom(dig.getPosition(), dig.getDir());
         dig.setPosition(ahead);
      }
   },DIGLEFT(30) {
      @Override
      public boolean actionAvailable(Digger dig) {
         return dig.currentLevel().digable(dig.getPosition(), dig.getDir().left());
      }

      @Override
      public void performAction(Digger dig) {
         Pos ahead = dig.currentLevel().dig(dig.getPosition(), dig.getDir().left());
         dig.setPosition(ahead);
         
      }
   }, DIGRIGHT(30) {
      @Override
      public boolean actionAvailable(Digger dig) {
         return dig.currentLevel().digable(dig.getPosition(), dig.getDir().right());
      }

      @Override
      public void performAction(Digger dig) {
         Pos ahead = dig.currentLevel().dig(dig.getPosition(), dig.getDir().right());
         dig.setPosition(ahead);
         
      }
   }, DIGDOWN(0) {
      @Override
      public boolean actionAvailable(Digger dig) {
         Level lowerLevel = dig.lowerLevel();
         
         return lowerLevel != null 
               && dig.currentLevel().getValue(dig.getPosition()) == TileType.FLOOR
               && lowerLevel.getValue(dig.getPosition()) == TileType.ROCK
               && lowerLevel.digable(dig.getPosition());
      }

      @Override
      public void performAction(Digger dig) {
         dig.currentLevel().stairs(dig.getPosition());
         dig.lowerLevel().stairs(dig.getPosition());
         dig.setZ(dig.getZ() -1);
         
      }
   }, DIGUP(0) {
      @Override
      public boolean actionAvailable(Digger dig) {
         Level upperLevel = dig.upperLevel();
         return upperLevel != null 
               && dig.currentLevel().getValue(dig.getPosition()) == TileType.FLOOR
               && upperLevel.getValue(dig.getPosition()) == TileType.ROCK
               && upperLevel.digable(dig.getPosition());
      }
      @Override
      public void performAction(Digger dig) {
         dig.currentLevel().stairs(dig.getPosition());
         dig.upperLevel().stairs(dig.getPosition());
         dig.setZ(dig.getZ() +1);
         
      }

   }, STOP(0) {
      @Override
      public boolean actionAvailable(Digger dig) {
         return false;
      }

      @Override
      public void performAction(Digger dig) {
        // nothing to do here
      }
   };
   
   public int defaultOccurence;
   
   private DiggerAction(int defaultOccurence) {
      this.defaultOccurence = defaultOccurence;
   }

   public abstract boolean actionAvailable(Digger dig);
   
   public abstract void performAction(Digger dig);

   public int getDefaultOccurence() {
      return defaultOccurence;
   }

   
}
