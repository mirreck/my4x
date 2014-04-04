package net.my4x.services.map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MapTileServiceImplTest {

   private MapTileService service;
   
   @Before
   public void init(){
      service = new MapTileServiceImpl();
   }
   
   
   @Test
   public void testGetHeightTile() {
      service.resetAllTiles();
      service.getHeightTile(0, 0, 1);
      service.getHeightTile(0, 0, 2);
      service.getHeightTile(1, 0, 2);
      service.getHeightTile(2, 0, 2);
   }

   @Ignore
   @Test
   public void testGetWaterTile() {
      service.resetAllTiles();
      service.getHeightTile(0, 0, 4);
      service.getWaterTile(0, 0, 4);
   }


}
