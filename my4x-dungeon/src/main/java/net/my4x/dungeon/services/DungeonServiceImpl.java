package net.my4x.dungeon.services;

import net.my4x.dungeon.model.Dungeon;
import net.my4x.dungeon.services.digger.Digger;

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
