package net.my4x.map.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.my4x.map.model.ColorMap;
import net.my4x.map.model.HeightMap;
import net.my4x.map.model.WaterMap;
import net.my4x.map.utils.ColorMapUtils;
import net.my4x.map.utils.HeightMapUtils;
import net.my4x.map.utils.NoiseGenerator;

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

   private enum TileType { HEIGHT("png"), WATER("png"), DATA("data"); 
      String extension; 
      TileType(String ext){
         extension = ext;
      }
   } 
   
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
      @Override
      public String toString() {
         return "Projection [x=" + x + ", y=" + y + ", zoom=" + zoom + ", mapX=" + mapX + ", mapY=" + mapY + ", mapZoom=" + mapZoom
               + "]";
      }
   }
   
   public File getHeightTile(int x, int y, int zoom) {
      LOGGER.debug("Load TILE :x={} y={}", x, y);
      Projection p = new Projection(x,y,zoom);
      File file = tileFile(TileType.HEIGHT, p);
      if(file != null && file.exists()){
         return file;
      }
      try {
         return generateHeightTile(file, p);
      }
      catch (IOException e) {
        throw new MapException(e);
      }
   }

   public File getWaterTile(int x, int y, int zoom) {
      LOGGER.debug("Load TILE :x={} y={}", x, y);
      Projection p = new Projection(x, y, zoom);
      File file = tileFile(TileType.WATER, p);
      if (file != null && file.exists()) {
         return file;
      }
      try {
         return generateWaterTile(file, p);
      }
      catch (IOException e) {
         throw new MapException(e);
      }
   }
   
   private HeightMap getHeightData(Projection p) throws IOException {
      File file = tileFile(TileType.DATA, p);
      if(file != null && file.exists()){
         return HeightMapUtils.load(file);
      }
      return generateHeightData(file, p);
   }
   
   


   private File generateWaterTile(File file, Projection p) throws IOException {
      LOGGER.debug("generateWaterTile TILE :x={} y={}", p.x, p.y);
      HeightMap heightMap = getHeightData(p);
      WaterMap waterMap = computeWater(heightMap);
      ColorMap waterColorMap = ColorMapUtils.colorize(waterMap);
      saveColorMap(file, waterColorMap);
      return file;
   }

   private File generateHeightTile(File file, Projection p) throws IOException {
      LOGGER.debug("generateHeightTile TILE :x={} y={}", p.x, p.y);
      HeightMap heightMap = getHeightData(p);
      ColorMap waterColorMap = ColorMapUtils.colorize(heightMap);
      saveColorMap(file, waterColorMap);
      return file;
   }

   
   private File tileFile(TileType type, Projection p){
      return  new File(new File(DEFAULT_DIRECTORY + p.zoom + "/"),"tile-"+type.name()+"-x" + p.x + "y" + p.y + "."+type.extension);
   }
 
   private File tilesDir(int zoom){
      return  new File(DEFAULT_DIRECTORY + zoom + "/");
   }
   

   private String dataFileName(int x, int y) {
      return "tile-data-x" + x + "y" + y + ".data";
   }
   
   private String waterfileName(int x, int y) {
      return "tile-water-x" + x + "y" + y + ".png";
   }

   private void saveColorMap(File file, ColorMap colorMap) throws IOException {
      if (!file.exists() || !file.isFile()) {
         file.getParentFile().mkdirs();
         file.createNewFile();
         ColorMapUtils.exportMapImage(colorMap, file);
      }
   }
   
   
   private InputStream exportAsStream(File file, ColorMap colorMap) {
      if (!file.exists() || !file.isFile()) {
         file.getParentFile().mkdirs();
         ColorMapUtils.exportMapImage(colorMap, file);
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

   private HeightMap generateHeightData(File file, Projection p) {
      LOGGER.debug("generateHeightData TILE :x={} y={}", p.x, p.y);
      HeightMap map = computeHeightMap(p.mapX, p.mapY, p.mapZoom);
      HeightMapUtils.save(map, file);
      return map;
   }
   
   
   private HeightMap computeHeightMap(float x, float y, float zoom) {
      HeightMap map = new HeightMap(SIZE, SIZE, x, y, zoom);
      map.setBaseHeight(-5000);
      int facteur = 6000;
      int frequency = 3;
      for (int i = 0; i < 4; i++) {
         NoiseGenerator.addPerlinNoise(map, frequency, 0, facteur);
         facteur /= 2;
         frequency *=2;
      }
//      NoiseGenerator.addPerlinNoise(map, FREQUENCY, -4000, 2000);
//      NoiseGenerator.addPerlinNoise(map, FREQUENCY2, 0, 1500);
//      NoiseGenerator.addPerlinNoise(map, 80.0f, 0, 500, new NoiseGenerator.ScaleMode(1000.0f, 1200.0f));
//      NoiseGenerator.addPerlinNoise(map, 100.0f, 0, 200);
      //NoiseGenerator.addPerlinNoise(map, 500.0f, 0, 50);
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
