package net.my4x.services.dungeon;

import org.springframework.stereotype.Service;

import net.my4x.services.dungeon.model.Pos;
import net.my4x.services.dungeon.model.realtime.Action;
import net.my4x.services.dungeon.model.realtime.Status;

@Service
public class RealtimeDungeonServiceImpl implements RealtimeDungeonService{

   private Status currentStatus = null;
   
   private void initialize(){
      currentStatus = new Status();
   }
   
   public Status getStatus() {
      if(currentStatus == null){
         initialize();
      }
      return currentStatus;
   }

   public void performAction(Action action, String performer, Pos target) {
      // TODO Auto-generated method stub
      
   }

}
