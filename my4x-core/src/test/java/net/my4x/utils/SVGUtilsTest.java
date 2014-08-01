package net.my4x.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class SVGUtilsTest {

   


   @Test
   public void test() throws IOException, TranscoderException {
       File outSvgFile = File.createTempFile("out", "svg");
      InputStream is = SVGUtils.class.getResourceAsStream("in.svg");
      OutputStream out = new FileOutputStream(outSvgFile); 
      SVGUtils.svgToPng(is,out);
      IOUtils.closeQuietly(is);
      IOUtils.closeQuietly(out);
   }
   
   

   
   

   
 
}
