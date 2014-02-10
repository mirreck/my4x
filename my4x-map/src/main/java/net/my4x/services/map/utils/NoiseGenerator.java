package net.my4x.services.map.utils;

import net.my4x.services.map.model.HeightMap;
import net.my4x.services.map.model.ZoneMap;
import net.my4x.services.map.utils.noise.MyPerlinNoise;
import net.my4x.services.map.utils.noise.NoiseAlgorithm;
import net.my4x.services.map.utils.noise.PerlinNoise;
import net.my4x.services.map.utils.noise.SimplexNoiseImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// FROM : http://mrl.nyu.edu/~perlin/noise/
// AND http://www.float4x4.net/index.php/2010/06/generating-realistic-and-playable-terrain-height-maps/
//JAVA REFERENCE IMPLEMENTATION OF IMPROVED NOISE - COPYRIGHT 2002 KEN PERLIN.
public class NoiseGenerator {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(ColorMapUtils.class);
   
   private static final NoiseAlgorithm algo = new PerlinNoise();
   //private static final NoiseAlgorithm algo = new SimplexNoiseImpl();
   
   
   public static void generateZones(ZoneMap map, double frequency) {
      for (int i = 0; i < map.getWidth(); i++) {
         for (int j = 0; j < map.getHeight(); j++) {
            //float d1 = 10.0f;//(float) noise(frequency * i / (float) map.getWidth(), frequency * j / (float) map.getHeight(), 0);
            double x = frequency * i / map.getWidth();
            double y = frequency * j / map.getHeight();
            //LOGGER.debug("noisefor x="+x+" y="+y);
            double d2 =  algo.noise(x, y);
            double d1 =  algo.noise(x+555, y+555);
            int res = d1>0?(d2>0?1:2):(d2>0?3:4);
            map.setValue(i, j, res);
         }
      }
   }
   
   public static void addPerlinNoise(HeightMap map, float frequency, float min, float max) {
      addPerlinNoise(map, frequency, min, max, new AddMode());
   }
   
   public static void addPerlinNoise(HeightMap map, float frequency, float min, float max, NoiseMode mode) {
      for (int i = 0; i < map.getWidth(); i++) {
         for (int j = 0; j < map.getHeight(); j++) {
            // -1.0 < diff <1.0
            float diff = (float) algo.noise(frequency * ((i / map.getZoom()) + map.getInitialx()) / (float) map.getWidth(), frequency
                  * ((j / map.getZoom()) + map.getInitialy()) / (float) map.getHeight());
            diff = min + (float) ((diff + 1.0) / 2.0 * (max - min));
            map.setValue(i, j,(int) mode.compute(map.getValue(i, j), diff));
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
