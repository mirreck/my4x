package net.my4x.map.utils;

import net.my4x.map.model.Color;


public class ColorProfile {

   public static float[] limits ={-10000.0f, -3900.0f, -2400.0f, -0.1f, 0.0f, 903.0f, 1206.0f, 1416.0f, 2884.0f, 9000.0f};
   public static int[]   red    ={        0,       30,      131,   229,   66,    255,     255,     255,     206,     255};
   public static int[]   green  ={       76,      226,      255,   255,  209,    255,     255,     236,     150,     255};
   public static int[]   blue   ={      255,      255,      255,   255,  127,    232,     192,     167,      73,     255};
   
   public static final Color BLACK = new Color(0,0,0);
   
   public static Color mapColor(float value) {
//	   if(value >= -5.0 && value <= 5.0){
//		   return BLACK;
//	   }
      Color color = new Color(getR(value) , getG(value), getB(value));
//      if(Math.abs(value % 200) <15){
//    	  return color.darker(0.9);
//      }
	return color;
   }
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
