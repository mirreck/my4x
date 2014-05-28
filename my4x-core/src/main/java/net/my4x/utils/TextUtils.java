package net.my4x.utils;

import java.util.Random;

import com.google.common.base.Joiner;

public class TextUtils {
   public static String translate(String original, String charset, double inflatefactor){
      String[] words = original.split(" ");
      String[] newwords = new String[words.length];
      for (int i = 0; i < words.length; i++) {
         newwords[i] = translateWord(words[i], charset, inflatefactor);
      }
      
      return Joiner.on(" ").join(newwords);
   }

   private static String translateWord(String word, String charset, double inflatefactor) {
      //System.out.println(word);
      Random rnd = new Random(charset.hashCode() + word.hashCode());
      byte[] bytes = word.getBytes();
      String inter = "";
      for (byte b : bytes) {
         inter += charset.charAt(rnd.nextInt(charset.length())); 
      }
      
      return inter;
   }
}
