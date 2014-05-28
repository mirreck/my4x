package net.my4x.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
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
   
}
