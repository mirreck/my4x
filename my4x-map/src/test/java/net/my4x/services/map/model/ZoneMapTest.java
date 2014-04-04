package net.my4x.services.map.model;

import java.io.File;

import net.my4x.services.map.utils.ColorMapUtils;
import net.my4x.services.map.utils.NoiseGenerator;

import org.junit.Test;

public class ZoneMapTest {

   @Test
   public void test() {
      
      for (int frequency = 1; frequency < 10; frequency++) {
         ZoneMap map = new ZoneMap(150, 150);
         NoiseGenerator.generateZones(map,frequency);

         File file = new File("C:\\tmp\\GEN\\test_map_2z"+frequency+".png");
         ColorMapUtils.exportMapImage(ColorMapUtils.colorize(map), file);
      }
      


      
   }

}
