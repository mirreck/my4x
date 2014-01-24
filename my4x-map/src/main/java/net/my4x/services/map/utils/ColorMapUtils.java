package net.my4x.services.map.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.my4x.services.map.model.Color;
import net.my4x.services.map.model.ColorMap;
import net.my4x.services.map.model.HeightMap;
import net.my4x.services.map.model.WaterMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorMapUtils {

   private static final Logger LOGGER = LoggerFactory.getLogger(ColorMapUtils.class);
   
   public static void exportMapImage(ColorMap colorMap, File file) {
      try {
         BufferedImage image = new BufferedImage(colorMap.getWidth(), colorMap.getHeight(), BufferedImage.TYPE_INT_ARGB);
         Graphics2D g2d = (Graphics2D) image.getGraphics();
         g2d.setColor(new java.awt.Color(0, 0, 0, 0));
         g2d.fillRect(0, 0, 100, 100);
         for (int i = 0; i < colorMap.getWidth(); i++) {
             for (int j = 0; j < colorMap.getHeight(); j++) {
                image.setRGB(i, j, colorMap.getRgb(i, j));
             }
         }
         ImageIO.write(image, "png", file);
     } catch (IOException e) {
         e.printStackTrace();
     }
   }
   
   public static ColorMap colorize(WaterMap waterMap) {
      LOGGER.debug("colorize W");
      ColorMap colorMap = new ColorMap(waterMap.getWidth(), waterMap.getHeight());
      for (int i = 0; i < waterMap.getWidth(); i++) {
         for (int j = 0; j < waterMap.getHeight(); j++) {
            colorMap.setValue(i, j, 0,0,255,waterMap.getFinalFlow(i,j));
         }
      }
      return colorMap;
   }


   public static ColorMap colorize(HeightMap heightMap) {
      LOGGER.debug("colorize");
//      ColorMap colorMap = new ColorMap(heightMap.getWidth(), heightMap.getHeight());
//      for (int i = 0; i < heightMap.getWidth(); i++) {
//         for (int j = 0; j < heightMap.getHeight(); j++) {
//            Color color = ColorProfile.mapColor(heightMap.getValue(i, j), 150);
//            colorMap.setValue(i, j, color );
//         }
//      }
//      return colorMap;
//   }
//   
//   private static ColorMap computeMapColors(HeightMap heightMap) {
      ColorMap colorMap = new ColorMap(heightMap.getWidth(), heightMap.getHeight());
      for (int i = 0; i < heightMap.getWidth(); i++) {
         for (int j = 0; j < heightMap.getHeight(); j++) {
            float value = heightMap.getValue(i, j);
            Color color = ColorProfile.mapColor(value, 150);
            int limit = ((int) (value / 200))*200;
            float remain = Math.abs(value % 200);
            if(value > 0){
                if(remain <15 && hasNeighborUnderLimit(heightMap,i,j, limit)){
                    color = color.darker(0.95) ;
                } 
            } else {
                if(remain <15 && hasNeighborOverLimit(heightMap,i,j, limit)){
                    color = color.darker(0.95) ;
                }
            }
            colorMap.setValue(i, j, color );
         }
      }
      return colorMap;
   }

    private static boolean hasNeighborOverLimit(HeightMap heightMap, int i,
            int j, int limit) {

        if (i <= 1 || j <= 1 || i >= heightMap.getWidth() - 1
                || j >= heightMap.getHeight() - 1) {
            return false;
        }
        if (heightMap.getValue(i - 1, j - 1) >= limit
                || heightMap.getValue(i - 1, j) >= limit
                || heightMap.getValue(i - 1, j - 1) >= limit
                || heightMap.getValue(i, j - 1) >= limit
                || heightMap.getValue(i, j + 1) >= limit
                || heightMap.getValue(i + 1, j - 1) >= limit
                || heightMap.getValue(i + 1, j) >= limit
                || heightMap.getValue(i + 1, j + 1) >= limit) {
            return true;
        }

        return false;
    }
   
    private static boolean hasNeighborUnderLimit(HeightMap heightMap, int i,
            int j, int limit) {

        if (i <= 1 || j <= 1 || i >= heightMap.getWidth() - 1
                || j >= heightMap.getHeight() - 1) {
            return false;
        }
        if (heightMap.getValue(i - 1, j - 1) <= limit
                || heightMap.getValue(i - 1, j) <= limit
                || heightMap.getValue(i - 1, j - 1) <= limit
                || heightMap.getValue(i, j - 1) <= limit
                || heightMap.getValue(i, j + 1) <= limit
                || heightMap.getValue(i + 1, j - 1) <= limit
                || heightMap.getValue(i + 1, j) <= limit
                || heightMap.getValue(i + 1, j + 1) <= limit) {
            return true;
        }

        return false;
    }
   
}
