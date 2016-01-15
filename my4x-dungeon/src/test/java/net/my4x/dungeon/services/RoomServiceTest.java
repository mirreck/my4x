package net.my4x.dungeon.services;

import lombok.extern.slf4j.Slf4j;
import net.my4x.dungeon.model.Direction;
import net.my4x.dungeon.model.Position;
import net.my4x.dungeon.model.Room;
import net.my4x.dungeon.services.RoomService.Options;
import net.my4x.dungeon.utils.RoomUtils;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

@Slf4j
public class RoomServiceTest {

	   private RoomService service;
	   
	   @Before
	   public void init(){
	      service = new RoomService();
	   }
	   
	   @Test
	   public void should_create_room() {
	      Room room = service.create(Options.builder(new Position(0,0), Direction.N).build());
	      log.debug("\n"+RoomUtils.toDisplayString(room));
	   }
	   
	   @Test
	   public void should_create_room_with_exit() {
	      Room room = service.create(Options
	    		  .builder(new Position(0,0), Direction.N)
	    		  .exits(Lists.newArrayList(new Position(5,5)))
	    		  .build());
	      log.debug("\n"+RoomUtils.toDisplayString(room));
	   }

}
