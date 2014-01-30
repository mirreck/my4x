package net.my4x.services.map.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import net.my4x.services.map.model.AbstractMap.Pos;
import net.my4x.services.map.model.Color;
import net.my4x.services.map.model.ColorMap;
import net.my4x.services.map.model.HeightMap;
import net.my4x.services.map.model.WaterMap;
import net.my4x.services.map.model.ZoneMap;

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
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static ColorMap colorize(ZoneMap zoneMap) {
      LOGGER.debug("colorize ZoneMap");
      ColorMap colorMap = new ColorMap(zoneMap.getWidth(), zoneMap.getHeight());
      for (int i = 0; i < zoneMap.getWidth(); i++) {
         for (int j = 0; j < zoneMap.getHeight(); j++) {
            switch (zoneMap.getValue(i, j)) {
               case 1:
                  colorMap.setValue(i, j, 255, 0, 0, 255);
                  break;
               case 2:
                  colorMap.setValue(i, j, 0, 255, 0, 255);
                  break;
               case 3:
                  colorMap.setValue(i, j, 0, 0, 255, 255);
                  break;
               case 4:
                  colorMap.setValue(i, j, 255, 255, 0, 255);
                  break;
               default:
                  colorMap.setValue(i, j, 0, 0, 0, 255);
                  break;
            }
         }
      }
      return colorMap;
   }

   public static ColorMap colorize(WaterMap waterMap) {
      LOGGER.debug("colorize WaterMap");
      ColorMap colorMap = new ColorMap(waterMap.getWidth(), waterMap.getHeight());
      for (int i = 0; i < waterMap.getWidth(); i++) {
         for (int j = 0; j < waterMap.getHeight(); j++) {
            if (waterMap.getLevel(i, j) > 0) {
               colorMap.setValue(i, j, 0, 255, 0, 255);
            }
            else {
               colorMap.setValue(i, j, 0, 0, 255, waterMap.getFinalFlow(i, j));
            }
         }
      }
      return colorMap;
   }

   public static ColorMap colorize(HeightMap heightMap) {
      LOGGER.debug("colorize HeightMap");

      ColorMap colorMap = new ColorMap(heightMap.getWidth(), heightMap.getHeight());
      for (int i = 0; i < heightMap.getWidth(); i++) {
         for (int j = 0; j < heightMap.getHeight(); j++) {
            float value = heightMap.getValue(i, j);
            Color color = ColorProfile.mapColor(value);
            int limit = ((int) (value / 200)) * 200;
            if (value > 0) {
               if (hasNeighborUnderLimit(heightMap, i, j, limit)) {
                  color = color.darker(0.95);
               }
            }
            else {
               if (hasNeighborOverLimit(heightMap, i, j, limit)) {
                  color = color.darker(0.95);
               }
            }
            colorMap.setValue(i, j, color);
         }
      }
      return colorMap;
   }

   private static boolean hasNeighborOverLimit(HeightMap heightMap, int i, int j, int limit) {
      List<Pos> pts = heightMap.neighbours(i, j);
      for (Pos pos : pts) {
         if (heightMap.getValue(pos.x, pos.y) >= limit) {
            return true;
         }
      }
      return false;
   }

   private static boolean hasNeighborUnderLimit(HeightMap heightMap, int i, int j, int limit) {

      List<Pos> pts = heightMap.neighbours(i, j);
      for (Pos pos : pts) {
         if (heightMap.getValue(pos.x, pos.y) <= limit) {
            return true;
         }
      }

      return false;
   }

}
