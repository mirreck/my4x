package net.my4x.dungeon.services;

import net.my4x.dungeon.model.Dungeon;
import net.my4x.dungeon.services.DungeonService;
import net.my4x.dungeon.services.DungeonServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DungeonServiceTest {

   private static final Logger LOGGER = LoggerFactory.getLogger(DungeonServiceTest.class);
   
   private DungeonService service;
   
   @Before
   public void init(){
      service = new DungeonServiceImpl();
   }
   
   @Test
   public void test() {
      Dungeon dun = service.load();
      LOGGER.debug(dun.toString());
   }

}
