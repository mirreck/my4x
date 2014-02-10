package net.my4x.services.map.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

import net.my4x.services.map.model.HeightMap;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HeightMapUtils {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(HeightMapUtils.class);
   
   public static void save(HeightMap map, File infile) throws IOException {
      LOGGER.debug("HeightMapUtils save  FILE {} ", infile.getName());
      FileOutputStream out = null;
        out = new FileOutputStream(infile);
        FileChannel file = out.getChannel();
        ByteBuffer buf = file.map(FileChannel.MapMode.READ_WRITE, 0, 4 * map.getData().length);
        buf.putInt(map.getWidth());
        buf.putInt(map.getHeight());
        for (int i : map.getData()) {
          buf.putInt(i);
        }
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(file);
   }
   
   public static HeightMap load(File infile) throws IOException {
      LOGGER.debug("HeightMapUtils load  FILE {} ", infile.getName());
      Scanner scanner = new Scanner(infile);
      int width = 0;
      int height = 0;
      if(scanner.hasNextInt()){
         width = scanner.nextInt();
      } else {
         throw new IOException();
      }
      if(scanner.hasNextInt()){
         height = scanner.nextInt();
      }
      HeightMap map = new HeightMap(width, height,0,0);
      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            map.setValue(i, j, scanner.nextInt());
         }
      }
      return map;
   }
}
