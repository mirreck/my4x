package net.my4x.tasks;

import java.io.File;
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

   @Test
   public void test() throws IOException, TranscoderException {
       File outSvgFile = File.createTempFile("out", "svg");
      SVGDrawBackgroundTask.generateSVG(outSvgFile);
      InputStream is = new FileInputStream(outSvgFile);
      
      File outPngFile = File.createTempFile("out", "png");
      OutputStream out = new FileOutputStream(outPngFile); 
      SVGUtils.svgToPng(is,out);
      IOUtils.closeQuietly(is);
      IOUtils.closeQuietly(out);
   }

}
