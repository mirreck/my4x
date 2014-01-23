package net.my4x.services.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.my4x.services.map.model.Color;
import net.my4x.services.map.model.ColorMap;
import net.my4x.services.map.model.HeightMap;
import net.my4x.services.map.utils.ColorProfile;
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
      File file = new File(DEFAULT_DIRECTORY + zoom + "/tilex" + x + "y" + y + ".png");
      float mapZoom = (float) Math.pow(2.0, zoom);
      try {
         if (!file.exists() || !file.isFile()) {
            file.mkdirs();
            file.createNewFile();
            exportMapImage(file, x/mapZoom, y/mapZoom, mapZoom);
         }
         return new FileInputStream(file);
      }
      catch (IOException e) {
         e.printStackTrace();
         throw new RuntimeException("could not create new tile",e);
      }
      
   }

   private void exportMapImage(File file,float x, float y, float zoom) {

      HeightMap heightMap = computeHeightMap(x*SIZE,y*SIZE, zoom);
      ColorMap colorMap = computeMapColors(heightMap);
      exportMapImage(colorMap, file);
   }

   private void exportMapImage(ColorMap colorMap, File file) {
      try {
         BufferedImage image = new BufferedImage(colorMap.getWidth(), colorMap.getHeight(), BufferedImage.TYPE_INT_ARGB);
         Graphics2D g2d = (Graphics2D) image.getGraphics();
         g2d.setColor(new java.awt.Color(0, 0, 0, 0));
         g2d.fillRect(0, 0, 100, 100);
         for (int i = 0; i < colorMap.getWidth(); i++) {
             for (int j = 0; j < colorMap.getHeight(); j++) {
                image.setRGB(i, j, colorMap.getValue(i, j).getRGB());
             }
         }
         ImageIO.write(image, "png", file);
     } catch (IOException e) {
         e.printStackTrace();
     }
   }

   private static ColorMap computeMapColors(HeightMap heightMap) {
      ColorMap colorMap = new ColorMap(heightMap.getWidth(), heightMap.getHeight());
      for (int i = 0; i < heightMap.getWidth(); i++) {
         for (int j = 0; j < heightMap.getHeight(); j++) {
            Color color = ColorProfile.mapColor(heightMap.getValue(i, j), 150);
            colorMap.setValue(i, j, color );
         }
      }
      return colorMap;
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
   
}
