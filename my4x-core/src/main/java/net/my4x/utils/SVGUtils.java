package net.my4x.utils;

import java.io.*;

import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.commons.io.IOUtils;

public class SVGUtils {
   public static void svgToPng(InputStream svgIn, OutputStream pngOut) throws TranscoderException, IOException{
      PNGTranscoder t = new PNGTranscoder();

      // Create the transcoder input.
      TranscoderInput input = new TranscoderInput(svgIn);

      // Create the transcoder output.
      TranscoderOutput output = new TranscoderOutput(pngOut);

      // Save the image.
      t.transcode(input, output);
   }
   public static void main(String[] args) throws IOException, TranscoderException {
      InputStream is = SVGUtils.class.getResourceAsStream("in.svg");
      OutputStream out = new FileOutputStream("C:\\tmp\\GEN\\out.png"); 
      svgToPng(is,out);
      IOUtils.closeQuietly(is);
      IOUtils.closeQuietly(out);
      
   }
}
