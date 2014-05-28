package net.my4x.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class SVGFontTasks {
   public static String applyScale(String input, double scale){
      Pattern p = Pattern.compile("(\\d+\\.\\d+)");
      Matcher m = p.matcher(input);
      StringBuffer sb = new StringBuffer();
      while (m.find()) {
         String group = m.group();
         Double d = new Double(group);
         //System.out.println("Group = "+group);
          m.appendReplacement(sb, ""+d*scale);
      }
      m.appendTail(sb);
     
      return sb.toString();
   }
   
   public static void applyScaleToFile(String inputFile) throws IOException{
      InputStream is = new FileInputStream(new File(inputFile));
      String content = IOUtils.toString(is);
      IOUtils.closeQuietly(is);
      
      //System.out.println(applyScale(content,2.0));
      
      OutputStream out = new FileOutputStream(new File(inputFile+".2.svg"));
      IOUtils.write(applyScale(content,20.83), out);
      IOUtils.closeQuietly(out);
      
   }
   
   
   public static void makeGlyphs(String inputFile) throws IOException{
      InputStream is = new FileInputStream(new File(inputFile));
      String content = IOUtils.toString(is);
      IOUtils.closeQuietly(is);
      
      OutputStream out = new FileOutputStream(new File(inputFile+".g.svg"));
      IOUtils.write(extractGlyphs(content), out);
      IOUtils.closeQuietly(out);
      
   }
   public static String extractGlyphs(String input){
      String letters="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏàáâãäåæçèéêëìíîï";
      
      Pattern p = Pattern.compile("(id=\"[^\"]*\"\\s*d=\"[Mm][^\"]*\")");
      Matcher m = p.matcher(input);
      StringBuffer sb = new StringBuffer();
      int i= 0;
      while (m.find()) {
         sb.append("<glyph glyph-name=\"R"+(100+i)+"\" unicode=\""+letters.charAt(i)+"\" ");
         i++;
         String group = m.group();
         System.out.println("Group = "+group);
          sb.append(group);
          sb.append("/>\n");
      }
      return sb.toString();
   }
}
