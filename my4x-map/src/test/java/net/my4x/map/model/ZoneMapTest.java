package net.my4x.map.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.my4x.map.utils.ColorMapUtils;
import net.my4x.map.utils.NoiseGenerator;
import org.junit.Test;

public class ZoneMapTest {

   @Test
   public void test() throws IOException {
      
      for (int frequency = 1; frequency < 10; frequency++) {
         ZoneMap map = new ZoneMap(150, 150);
         NoiseGenerator.generateZones(map,frequency);

         Path path = Files.createTempDirectory("GEN_MAP");
         Path tempFile = Files.createTempFile(path, "test_map", ".png");

         ColorMapUtils.exportMapImage(ColorMapUtils.colorize(map), tempFile.toFile());
      }
      


      
   }

}
