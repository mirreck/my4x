package net.my4x.tasks;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.my4x.utils.SVGUtils;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class SVGDrawBackgroundTaskTest {

   
   
   private static final String TMP_OUT_SVG = "C:\\tmp\\GEN\\out.svg";
   private static final String OUT = "C:\\PROJETS\\PERSO\\TEST\\my4x\\my4x-webapp\\src\\main\\webapp\\resources\\images\\indoor-tiles.png";
   
   @Test
   public void test() throws IOException, TranscoderException {
      
      SVGDrawBackgroundTask.generateSVG(TMP_OUT_SVG);
      InputStream is = new FileInputStream(TMP_OUT_SVG);
      OutputStream out = new FileOutputStream(OUT); 
      SVGUtils.svgToPng(is,out);
      IOUtils.closeQuietly(is);
      IOUtils.closeQuietly(out);
   }

}
