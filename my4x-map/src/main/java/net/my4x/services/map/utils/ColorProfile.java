package net.my4x.services.map.utils;

import net.my4x.services.map.model.Color;


public class ColorProfile {

   public static float[] limits ={-10000.0f, -3900.0f, -2400.0f, -0.1f, 0.0f, 903.0f, 1206.0f, 1416.0f, 2884.0f, 9000.0f};
   public static int[]   red    ={        0,       30,      131,   229,   66,    255,     255,     255,     206,     255};
   public static int[]   green  ={       76,      226,      255,   255,  209,    255,     255,     236,     150,     255};
   public static int[]   blue   ={      255,      255,      255,   255,  127,    232,     192,     167,      73,     255};
   
   public static Color mapColor(float value, float maxvalue) {
      return new Color(getR(value) , getG(value), getB(value));
   }
   // -10000 : 0 76 255
   // -3900 :30 226 255
   // -2400 :131 255 255
   // -1 :229 255 255
   // 0  :66 209 127
   
   
   
   // 903 255 255 232
   // 1206 255 255 192
   // 1416 255 236 167
   // 2884 206 150 73
   // 9000 255 255 255
   public static int getR(float value) {
      return getVal(value, red);
   }
   public static int getG(float value) {
      return getVal(value, green);
   }
   public static int getB(float value) {
      return getVal(value, blue);
   }
   public static int getVal(float value, int[]   tab) {
      if(value < limits[0]){
         return tab[0];
      } else {
         for (int i = 1; i < limits.length; i++) {
            if(value < limits[i]){
               return tab[i-1]+(int)((tab[i]-tab[i-1])*(value-limits[i-1])/(limits[i]-limits[i-1]));
            }
         }
      }
      return 0;
   }
   

}
