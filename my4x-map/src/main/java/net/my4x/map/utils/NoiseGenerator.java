package net.my4x.map.utils;

import net.my4x.map.model.HeightMap;
import net.my4x.map.model.ZoneMap;
import net.my4x.map.utils.noise.MyPerlinNoise;
import net.my4x.map.utils.noise.NoiseAlgorithm;
import net.my4x.map.utils.noise.PerlinNoise;
import net.my4x.map.utils.noise.SimplexNoiseImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NoiseGenerator {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(ColorMapUtils.class);
   
   //private static final NoiseAlgorithm algo = new PerlinNoise();
   private static final NoiseAlgorithm algo = new SimplexNoiseImpl();
   
   
   public static void generateZones(ZoneMap map, double frequency) {
      for (int i = 0; i < map.getWidth(); i++) {
         for (int j = 0; j < map.getHeight(); j++) {
            //float d1 = 10.0f;//(float) noise(frequency * i / (float) map.getWidth(), frequency * j / (float) map.getHeight(), 0);
            double x = (frequency) * i / map.getWidth();
            double y = (frequency) * j / map.getHeight();
            //LOGGER.debug("noisefor x="+x+" y="+y);
            double d1 =  algo.noise(x, y);
            double d2 =  algo.noise(x+ 555, y+555);
            double d3 =  algo.noise(x, y+555);
            double d4 =  algo.noise(x+555, y);
            double max = MathUtils.max(d1,d2,d3,d4);
            int res =d1 == max?1:d2 == max?2:d3 == max?3:4;
            map.setValue(i, j, res);
         }
      }
   }
   
   public static void addPerlinNoise(HeightMap map, float frequency, float min, float max) {
      addPerlinNoise(map, frequency, min, max, new AddMode());
   }
   
   public static void addPerlinNoise(HeightMap map, float frequency, float min, float max, NoiseMode mode) {
      LOGGER.debug("addPerlinNoise x = {}, y = {}, zoom={}",new Object[]{map.getInitialx(),map.getInitialy(),map.getZoom()});
      for (int i = 0; i < map.getWidth(); i++) {
         for (int j = 0; j < map.getHeight(); j++) {
            // -1.0 < diff <1.0
            float x = frequency * (i / map.getZoom() / (float) map.getWidth()+ map.getInitialx());
            float y = frequency * (j / map.getZoom() / (float) map.getHeight()+ map.getInitialy());
            float diff = (float) algo.noise(x, y);
            diff = min + (float) ((diff + 1.0) / 2.0 * (max - min));
            map.setValue(i, j,(int) mode.compute(map.getHeight(i, j), diff));
         }
      }
   }
   
   public static void addPerlinNoise(float[][] map, float frequency){
      for (int i = 0; i < map.length; i++)
      {
         float[] row = map[i];
       for (int j = 0; j < row.length; j++)
       {
          row[j] += algo.noise(frequency * i / (float)map.length, frequency * j / (float)row.length);
       }
      }
   }
   
   public static interface NoiseMode {
      float compute(float oldValue, float diff);
   }
   public static class AddMode implements NoiseMode{
      public float compute(float oldValue, float diff) {
         return oldValue + diff;
      }
   }
   public static class MergeMode implements NoiseMode{
      public float compute(float oldValue, float diff) {
         return Math.max(oldValue,diff);
      }
   }
   
   public static class ScaleMode implements NoiseMode {
      float minAlt;
      float maxAlt;
      public ScaleMode(float minAlt, float maxAlt) {
         this.minAlt = minAlt;
         this.maxAlt = maxAlt;
      }
      public float compute(float oldValue, float diff) {
         if(oldValue < minAlt){
            return oldValue;
         } else {
            return oldValue + (diff * (oldValue -minAlt) / (maxAlt -minAlt));
         }
         
      }
   }

   

}
