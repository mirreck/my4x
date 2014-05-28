package net.my4x.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class SVGUtilsTest {

   


   private static final String OUT = "C:\\PROJETS\\PERSO\\TEST\\my4x\\my4x-webapp\\src\\main\\webapp\\resources\\images\\indoor-tiles.png";
   @Test
   public void test() throws IOException, TranscoderException {
      
      InputStream is = SVGUtils.class.getResourceAsStream("in.svg");
      OutputStream out = new FileOutputStream(OUT); 
      SVGUtils.svgToPng(is,out);
      IOUtils.closeQuietly(is);
      IOUtils.closeQuietly(out);
   }
   
   

   
   

   
 
}
