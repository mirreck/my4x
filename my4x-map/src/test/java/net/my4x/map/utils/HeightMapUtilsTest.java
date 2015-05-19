package net.my4x.map.utils;

import java.io.File;

import net.my4x.map.model.HeightMap;
import net.my4x.map.utils.HeightMapUtils;
import net.my4x.map.utils.NoiseGenerator;

import org.junit.Test;

public class HeightMapUtilsTest {

   @Test
   public void testWrite() {
      HeightMap hmap = new HeightMap(100, 100, 0.0f, 0.0f);
      NoiseGenerator.addPerlinNoise(hmap, 5.0f,-50,1250);
      File file = new File("C:\\tmp\\GEN\\test_map.data");
      HeightMapUtils.save(hmap, file);
   }
   
   @Test
   public void testRead() {
      File file = new File("C:\\tmp\\GEN\\test_map.data");
      HeightMapUtils.load(file);
   }
   

}
