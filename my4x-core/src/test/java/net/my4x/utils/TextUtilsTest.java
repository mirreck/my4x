package net.my4x.utils;

import org.junit.Test;

public class TextUtilsTest {

   private static final String ABCDEFGH = "abcdefgh";

   @Test
   public void test() {
      String translate = TextUtils.translate("my text is quite short to understand", ABCDEFGH, 1.0);
      String translate2 = TextUtils.translate("my text is quite clear now but not shorter", ABCDEFGH, 1.0);
      System.out.println(translate);
      System.out.println(translate2);
   }

}
