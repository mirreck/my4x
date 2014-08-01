package net.my4x.tasks;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

public class SVGFontTasksTest {

   private static final String SRC_PATH = "C:\\tmp\\TESTME\\mix.svg";

   @Ignore
   @Test
   public void test() throws IOException {
      SVGFontTasks.makeGlyphs(SRC_PATH);
   }

}
