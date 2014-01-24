package net.my4x.services.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.my4x.services.map.model.ColorMap;
import net.my4x.services.map.model.HeightMap;
import net.my4x.services.map.model.WaterMap;
import net.my4x.services.map.utils.ColorMapUtils;
import net.my4x.services.map.utils.PerlinNoise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MapTileServiceImpl implements MapTileService {
   
   private static final int SIZE = 255;
   private static final float FREQUENCY = 7.0f;
   private static final float FREQUENCY2 = 15.0f;
   private static final String DEFAULT_DIRECTORY = "C:/tmp/GEN/TILES/";
   private static final Logger LOGGER = LoggerFactory.getLogger(MapTileServiceImpl.class);
   
   @Value("${service.map.tiles.directory:" + DEFAULT_DIRECTORY + "}")
   private String tilesDirectory;
   
   public InputStream getTileAsStream(int x, int y, int zoom) {
      LOGGER.debug("Generate TILE :x={} y={}", x, y);
      String filename = "tilex" + x + "y" + y + ".png";
      File dir = new File(DEFAULT_DIRECTORY + zoom +"/");
      float mapZoom = (float) Math.pow(2.0, zoom);
      return exportMapImage(dir, filename, x/mapZoom, y/mapZoom, mapZoom);
      
   }

   private InputStream exportMapImage(File dir, String filename,float x, float y, float zoom) {

      File file = new File(dir,filename);
      if (!file.exists() || !file.isFile()) {
         file.mkdirs();
         
         HeightMap heightMap = computeHeightMap(x*SIZE,y*SIZE, zoom);
         ColorMap colorMap = ColorMapUtils.colorize(heightMap);
         ColorMapUtils.exportMapImage(colorMap, file);
         File waterfile = new File(dir,"water_"+filename);
         WaterMap waterMap = computeWater(heightMap);
         ColorMap waterColorMap = ColorMapUtils.colorize(waterMap);
         ColorMapUtils.exportMapImage(waterColorMap, waterfile);
      }
      
      try {
         return new FileInputStream(file);
      }
      catch (FileNotFoundException e) {
         e.printStackTrace();
         throw new RuntimeException("could not create new tile",e);
      }
      
   }



   private WaterMap computeWater(HeightMap heightMap) {
      LOGGER.debug("computeWater");
      WaterMap wmap = new WaterMap(heightMap);
      wmap.compute(heightMap);
      return wmap;
   }




private static HeightMap computeHeightMap(float x,float y, float zoom) {
      HeightMap map = new HeightMap(SIZE, SIZE,x,y, zoom);
      
      PerlinNoise.addPerlinNoise(map, FREQUENCY,-4000,2000);
      PerlinNoise.addPerlinNoise(map, FREQUENCY2,0,1500);
      PerlinNoise.addPerlinNoise(map, 80.0f,0,1500, new  PerlinNoise.ScaleMode(1000.0f, 1200.0f));
      PerlinNoise.addPerlinNoise(map, 100.0f,0,200);
      PerlinNoise.addPerlinNoise(map, 500.0f,0,50);
      return map;
   }

	public void resetAllTiles() {
		LOGGER.debug("resetAllTiles");
		File file = new File(DEFAULT_DIRECTORY);
		deleteFiles(file);
	}
	
	private void deleteFiles(File dir){
		if(dir.isDirectory()){
			for (File file : dir.listFiles()) {
				deleteFiles(file);
			}
		}
		LOGGER.debug("delete file:"+dir.getAbsolutePath());
		dir.delete();
	}
	
   
}
