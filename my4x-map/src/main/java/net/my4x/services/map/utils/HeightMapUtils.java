package net.my4x.services.map.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
   
   public static void save(HeightMap map, File file) {
      try {
         if(!file.exists()){
            file.mkdirs();
            file.createNewFile();
         }
         LOGGER.debug("HeightMapUtils save  FILE {} ", file.getName());
         FileOutputStream fos = new FileOutputStream(file);
         
         DataOutputStream dos = new DataOutputStream(fos);
         dos.writeInt(map.getWidth());
         dos.writeInt(map.getHeight());
         for (int i : map.getData()) {
            dos.writeInt(i);
         }
         IOUtils.closeQuietly(dos);
         IOUtils.closeQuietly(fos);
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }

   }
   
   public static HeightMap load(File file) {
      try {
         LOGGER.debug("HeightMapUtils load  FILE {} ", file.getName());
         
         
         FileInputStream fis = new FileInputStream(file);
         DataInputStream dis = new DataInputStream(fis);
         
        
            int width = dis.readInt();
            int height = dis.readInt();
         LOGGER.debug("loading HeightMap widht = {} height = {}", width, height);
         HeightMap map = new HeightMap(width, height, 0, 0);
         for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
               map.setValue(i, j, dis.readInt());
            }
         }
         IOUtils.closeQuietly(dis);
         return map;
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
