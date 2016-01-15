package net.my4x.dungeon.services;

import lombok.extern.slf4j.Slf4j;
import net.my4x.dungeon.model.Dungeon;

import org.junit.Before;
import org.junit.Test;

@Slf4j
public class DungeonServiceTest {

   
   private DungeonService service;
   
   @Before
   public void init(){
      service = new DungeonServiceImpl();
   }
   
   @Test
   public void test() {
      Dungeon dun = service.load();
      log.debug(dun.toString());
   }

}
