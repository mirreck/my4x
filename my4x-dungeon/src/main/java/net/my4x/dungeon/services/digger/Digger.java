package net.my4x.dungeon.services.digger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.my4x.dungeon.model.Direction;
import net.my4x.dungeon.model.Dungeon;
import net.my4x.dungeon.model.Level;
import net.my4x.dungeon.model.Pos;
import net.my4x.dungeon.model.TileType;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Digger {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(Digger.class);
   
   private Pos position = new Pos(2,2);
   
   private int z = 0;
   private Direction dir = Direction.S;

   private DiggerAction previousAction = DiggerAction.DIG_NORTH;
   private int occurence = 0;
   
   private Dungeon dungeon;
  
   
   
   public Digger(Dungeon d){
      this.dungeon = d;
      this.position = d.getEntrace();
      dir = Direction.O;
   }
   
   
   public void work() {
     
//      
//      currentLevel().setValue(position, TileType.ENTRACE);
////      while(DiggerAction.DIGROOM.actionAvailable(this)){
////         DiggerAction.DIGROOM.performAction(this);
////      }
//      
//      DiggerAction action = nextAction();
//      //LOGGER.debug("nextAction="+action);
//      while (action != DiggerAction.STOP) {
//         action.performAction(this);
//         action = nextAction();
//         //LOGGER.debug("nextAction="+action);
//      }
//      
      currentLevel().createRoomRecursive(position.x-2, position.x+2,0,5);
//      Collection<Level> levels = dungeon.getLevels();
//      for (Level level : levels) {
//         level.fillWithDoors();
//      }
      currentLevel().setValue(position, TileType.ENTRACE);
      LOGGER.debug("digger finished at ="+this.getPosition());
   }
   
   public Level currentLevel(){
      return dungeon.getLevel(z);
   }
   public Level lowerLevel(){
      return dungeon.getLevel(z-1);
   }
   public Level upperLevel(){
      return dungeon.getLevel(z+1);
   }
   
   
   public Pos getPosition(){
      return position;
   }
   
  
   

   
   private DiggerAction nextAction(){
      
      if(DiggerAction.DIGROOM.actionAvailable(this)){
         return DiggerAction.DIGROOM;
      }
      Direction curdir = this.dir;
      this.dir = curdir.left();
      if(DiggerAction.DIGROOM.actionAvailable(this)){
         return DiggerAction.DIGROOM;
      }
      this.dir = curdir.right();
      if(DiggerAction.DIGROOM.actionAvailable(this)){
         return DiggerAction.DIGROOM;
      }
      this.dir = curdir;
      List<DiggerAction> list = actions();
      //LOGGER.debug("nextActions="+list.size());
      if(list.size() == 0){
         return DiggerAction.STOP;
      } else {
         int random = RandomUtils.nextInt(list.size());
         return list.get(random);
      }
      
      
//      LOGGER.debug("nextActions="+list.toArray());
//      int sum = 0;
//      for (DiggerOrder order : list) {
//         sum += order.getOccurence();
//      }
//      if(sum == 0){
//         return DiggerAction.STOP;
//      }
//      int random = RandomUtils.nextInt(sum);
//      
//      for (DiggerOrder order : list) {
//         random -= order.getOccurence();
//         if(random < 0){
//            previousAction = order.getAction();
//            occurence = 0;
//            return order.getAction();
//         }
//      }
//      return DiggerAction.STOP;
   }
   
   private List<DiggerAction> actions() {
      DiggerAction[] values = DiggerAction.values();
      List<DiggerAction> li = new ArrayList<DiggerAction>();
      for (DiggerAction action : values) {
         if(action.getDefaultOccurence() > 0 && action.actionAvailable(this)){
            li.add(action);
         }
      }
      if(li.isEmpty() && DiggerAction.DIGDOWN.actionAvailable(this)){
         li.add(DiggerAction.DIGDOWN);
      }
      
      if(li.isEmpty() && DiggerAction.DIGUP.actionAvailable(this)){
         li.add(DiggerAction.DIGUP);
      }
      return li;
   }

   
   
   private List<DiggerOrder> orders() {
      DiggerAction[] values = DiggerAction.values();
      List<DiggerOrder> li = new ArrayList<DiggerOrder>();
      for (DiggerAction action : values) {
         if(action.getDefaultOccurence() > 0 && action.actionAvailable(this)){
            li.add(new DiggerOrder(action));
         }
      }
      if(li.isEmpty() && DiggerAction.DIGDOWN.actionAvailable(this)){
         li.add(new DiggerOrder(DiggerAction.DIGDOWN,10));
      }
      
      if(li.isEmpty() && DiggerAction.DIGUP.actionAvailable(this)){
         li.add(new DiggerOrder(DiggerAction.DIGUP,10));
      }
      return li;
   }




   public int getZ() {
      return z;
   }


   public Direction getDir() {
      return dir;
   }


   public void setPosition(Pos position) {
      this.position = position;
   }


   public void setZ(int z) {
      this.z = z;
   }


   public void setDir(Direction dir) {
      this.dir = dir;
   }

}
