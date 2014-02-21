package net.my4x.services.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.my4x.services.map.model.ColorMap;
import net.my4x.services.map.model.HeightMap;
import net.my4x.services.map.model.WaterMap;
import net.my4x.services.map.utils.ColorMapUtils;
import net.my4x.services.map.utils.HeightMapUtils;
import net.my4x.services.map.utils.NoiseGenerator;

import org.apache.commons.io.IOUtils;
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

   private class Projection {
      private int x,y,zoom;
      float mapX,mapY,mapZoom;
      private Projection(int x, int y, int zoom){
         this.x = x;
         this.y = y;
         this.zoom = zoom;
         this.mapZoom = (float) Math.pow(2.0, zoom);
         this.mapX = x / mapZoom;
         this.mapY = y / mapZoom;
      }
   }
   
   public InputStream getTileAsStream(int x, int y, int zoom) {
      LOGGER.debug("Load TILE :x={} y={}", x, y);
      Projection p = new Projection(x,y,zoom);
      
      return exportMapImage(tilesDir(zoom), fileName(x, y), p);

   }

   public InputStream getWaterTileAsStream(int x, int y, int zoom) {
      LOGGER.debug("Load TILE :x={} y={}", x, y);
      Projection p = new Projection(x,y,zoom);
      HeightMap heightMap = loadHeightMap(p);
      WaterMap waterMap = computeWater(heightMap);
      ColorMap waterColorMap = ColorMapUtils.colorize(waterMap);
      float mapZoom = (float) Math.pow(2.0, zoom);
      return exportMapImage(tilesDir(zoom), waterfileName(x, y), p);
   }
 
   private File tilesDir(int zoom){
      return  new File(DEFAULT_DIRECTORY + zoom + "/");
   }
   
   private String fileName(int x, int y) {
      return "tile-x" + x + "y" + y + ".png";
   }

   private String dataFileName(int x, int y) {
      return "tile-data-x" + x + "y" + y + ".data";
   }
   
   private String waterfileName(int x, int y) {
      return "tile-water-x" + x + "y" + y + ".png";
   }

   private InputStream exportMapImage(File dir, String filename, Projection p) {
      //return new FileInputStream(new File(dir,filename));
      
      
      File file = new File(dir, filename);
      if (!file.exists() || !file.isFile()) {
         LOGGER.debug("Generate TILE :x={} y={}", p.mapX, p.mapY);
         file.mkdirs();
         HeightMap heightMap = computeHeightMap(p.mapX * SIZE, p.mapY * SIZE, p.mapZoom);
         ColorMap colorMap = ColorMapUtils.colorize(heightMap);
         ColorMapUtils.exportMapImage(colorMap, file);
         File waterfile = new File(dir, "water-" + filename);
         WaterMap waterMap = computeWater(heightMap);
         ColorMap waterColorMap = ColorMapUtils.colorize(waterMap);
         ColorMapUtils.exportMapImage(waterColorMap, waterfile);
      }
      try {
         return new FileInputStream(file);
      }
      catch (FileNotFoundException e) {
         e.printStackTrace();
         throw new RuntimeException("could not create new tile", e);
      }

   }

   private WaterMap computeWater(HeightMap heightMap) {
      LOGGER.debug("computeWater");
      WaterMap wmap = new WaterMap(heightMap);
      wmap.compute();
      return wmap;
   }

   private  HeightMap loadHeightMap(Projection p) {
      String fileName = dataFileName(p.x, p.y);
      File file = new File(tilesDir(p.zoom), fileName);
      if (!file.exists() || !file.isFile()) {
         HeightMap heightMap = computeHeightMap(p.mapX * SIZE, p.mapY * SIZE, p.mapZoom);
         HeightMapUtils.save(heightMap, file);
         return heightMap;
      }
      return HeightMapUtils.load(file);
   }
   
   private HeightMap computeHeightMap(float x, float y, float zoom) {
      HeightMap map = new HeightMap(SIZE, SIZE, x, y, zoom);

      NoiseGenerator.addPerlinNoise(map, FREQUENCY, -4000, 2000);
      NoiseGenerator.addPerlinNoise(map, FREQUENCY2, 0, 1500);
      NoiseGenerator.addPerlinNoise(map, 80.0f, 0, 1500, new NoiseGenerator.ScaleMode(1000.0f, 1200.0f));
      NoiseGenerator.addPerlinNoise(map, 100.0f, 0, 200);
      NoiseGenerator.addPerlinNoise(map, 500.0f, 0, 50);
      return map;
   }

   public void resetAllTiles() {
      LOGGER.debug("resetAllTiles");
      File file = new File(DEFAULT_DIRECTORY);
      deleteFiles(file);
   }

   private void deleteFiles(File dir) {
      if (dir.isDirectory()) {
         for (File file : dir.listFiles()) {
            deleteFiles(file);
         }
      }
      LOGGER.debug("delete file:" + dir.getAbsolutePath());
      dir.delete();
   }

}
