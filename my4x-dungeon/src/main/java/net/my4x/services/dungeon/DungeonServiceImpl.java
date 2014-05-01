package net.my4x.services.dungeon;

import net.my4x.services.dungeon.digger.Digger;
import net.my4x.services.dungeon.model.Dungeon;

import org.springframework.stereotype.Service;

@Service
public class DungeonServiceImpl implements DungeonService {
   
   private Dungeon instance;
   
   public DungeonServiceImpl(){
      init();
   }
   

   public void init(){
      instance = new Dungeon(0, 2, 20, 20);
      Digger digger = new Digger(instance);
      digger.work();
   }

   public Dungeon load() {
      return instance;
   }
}
