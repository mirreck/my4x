package net.my4x.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
   public static void main( String[] args )
   {
       BufferedImage image = null;
       try {

          File file = new File("C:\\tmp\\GEN\\out.png");
           image = ImageIO.read(file);
           
           for (int i = 0; i < 5; i++) {
            
           }
           ImageIO.write(image.getSubimage(0, 0, 100, 100), "png",new File("C:\\tmp\\GEN\\out2.png"));

       } catch (IOException e) {
          throw new RuntimeException(e);
       }
       System.out.println("Done");
   }
}
