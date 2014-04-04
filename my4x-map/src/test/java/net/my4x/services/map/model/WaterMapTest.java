package net.my4x.services.map.model;

import java.io.File;

import net.my4x.services.map.utils.ColorMapUtils;
import net.my4x.services.map.utils.NoiseGenerator;

import org.junit.Test;

public class WaterMapTest {

   @Test
   public void test() {
      HeightMap hmap = new HeightMap(100, 100, 0.0f, 0.0f);
      for (int i = 0; i < hmap.width; i++) {
         for (int j = 0; j < hmap.height; j++) {
            int h = (int) Math.sqrt((i-hmap.width/2)*(i-hmap.width/2) + (j-hmap.width/2)*(j-hmap.width/2));
            h = 35 -h;
            hmap.setValue(i, j, 10*h);
         }
      }
      //NoiseGenerator.addPerlinNoise(hmap, 15.0f,0,250);
      WaterMap wmap = new WaterMap(hmap);
      wmap.compute();
      
      File file = new File("C:\\tmp\\GEN\\test_map.png");
      ColorMapUtils.exportMapImage(ColorMapUtils.colorize(hmap), file);
      
      File waterfile = new File("C:\\tmp\\GEN\\test_water.png");
      ColorMap waterColorMap = ColorMapUtils.colorize(wmap);
      ColorMapUtils.exportMapImage(waterColorMap, waterfile);
      
      
      file = new File("C:\\tmp\\GEN\\test_map_merged.png");
      ColorMapUtils.exportMapImage(ColorMapUtils.colorize(hmap).appendLayer(waterColorMap), file);
      
   }

}
