package net.my4x.map.utils.noise;

import java.util.Collections;
import java.util.Stack;

import net.my4x.map.utils.ColorMapUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//FROM : http://mrl.nyu.edu/~perlin/noise/
//AND http://www.float4x4.net/index.php/2010/06/generating-realistic-and-playable-terrain-height-maps/
//JAVA REFERENCE IMPLEMENTATION OF IMPROVED NOISE - COPYRIGHT 2002 KEN PERLIN.

public class PerlinNoise implements NoiseAlgorithm{
   private static final Logger LOGGER = LoggerFactory.getLogger(PerlinNoise.class);
   
   public double noise(double x, double y){
      return noise(x,y,0);
   }
   

   
   
   static public double noise(double x, double y, double z) {
      int X = (int)Math.floor(x) & 255,                  // FIND UNIT CUBE THAT
          Y = (int)Math.floor(y) & 255,                  // CONTAINS POINT.
          Z = (int)Math.floor(z) & 255;
      x -= Math.floor(x);                                // FIND RELATIVE X,Y,Z
      y -= Math.floor(y);                                // OF POINT IN CUBE.
      z -= Math.floor(z);
      double u = fade(x),                                // COMPUTE FADE CURVES
             v = fade(y),                                // FOR EACH OF X,Y,Z.
             w = fade(z);
      
      int A  = p[X]+Y,
          AA = p[A]+Z,
          AB = p[A+1]+Z,      // HASH COORDINATES OF

          B  = p[X+1]+Y,
          BA = p[B]+Z,
          BB = p[B+1]+Z;      // THE 8 CUBE CORNERS,

      return interpolate(w, interpolate(v, interpolate(u, grad(p[AA  ], x  , y  , z   ),  // AND ADD
                                     grad(p[BA  ], x-1, y  , z   )), // BLENDED
                             interpolate(u, grad(p[AB  ], x  , y-1, z   ),  // RESULTS
                                     grad(p[BB  ], x-1, y-1, z   ))),// FROM  8
                     interpolate(v, interpolate(u, grad(p[AA+1], x  , y  , z-1 ),  // CORNERS
                                     grad(p[BA+1], x-1, y  , z-1 )), // OF CUBE
                             interpolate(u, grad(p[AB+1], x  , y-1, z-1 ),
                                     grad(p[BB+1], x-1, y-1, z-1 ))));
   }
   static double fade(double t) { return t * t * t * (t * (t * 6 - 15) + 10); }
   static double interpolate(double t, double a, double b) { return a + t * (b - a); }
   static double grad(int hash, double x, double y, double z) {
      int h = hash & 15;                      // CONVERT LO 4 BITS OF HASH CODE
      double u = h<8 ? x : y,                 // INTO 12 GRADIENT DIRECTIONS.
             v = h<4 ? y : h==12||h==14 ? x : z;
      return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
   }
   
   
   static final int p[] = new int[512], permutation[] = { 116,160,137,91,90,15,
      131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
      190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
      88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
      77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
      102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
      135,130,151,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
      5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
      223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
      129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
      251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
      49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
      138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
   };
   
//   static final int p[] = new int[512], permutation[] = {225,155,210,108,175,199,221,144,203,116, 70,213, 69,158, 33,252,
//   5, 82,173,133,222,139,174, 27,  9, 71, 90,246, 75,130, 91,191,
//   169,138,  2,151,194,235, 81,  7, 25,113,228,159,205,253,134,142,
//   248, 65,224,217, 22,121,229, 63, 89,103, 96,104,156, 17,201,129,
//   36,  8,165,110,237,117,231, 56,132,211,152, 20,181,111,239,218,
//   170,163, 51,172,157, 47, 80,212,176,250, 87, 49, 99,242,136,189,
//   162,115, 44, 43,124, 94,150, 16,141,247, 32, 10,198,223,255, 72,
//   53,131, 84, 57,220,197, 58, 50,208, 11,241, 28,  3,192, 62,202,
//   18,215,153, 24, 76, 41, 15,179, 39, 46, 55,  6,128,167, 23,188,
//   106, 34,187,140,164, 73,112,182,244,195,227, 13, 35, 77,196,185,
//   26,200,226,119, 31,123,168,125,249, 68,183,230,177,135,160,180,
//   12,  1,243,148,102,166, 38,238,251, 37,240,126, 64, 74,161, 40,
//   184,149,171,178,101, 66, 29, 59,146, 61,254,107, 42, 86,154,  4,
//   236,232,120, 21,233,209, 45, 98,193,114, 78, 19,206, 14,118,127,
//   48, 79,147, 85, 30,207,219, 54, 88,234,190,122, 95, 67,143,109,
//   137,214,145, 93, 92,100,245,  0,216,186, 60, 83,105, 97,204, 52};
   static {
      for (int i=0; i < 256 ; i++) p[256+i] = p[i] = permutation[i];
//      Stack<Integer> stack = new Stack<Integer>(); 
//      for (int i=1; i < 256 ; i++) {
//         stack.add(i);
//      }
//      stack.add(1);
//      Collections.shuffle(stack);
//      
//      for (int i=0; i < 256 ; i++) {
//         p[i] = stack.pop();
//         p[256+i] = 256 - p[i];
//      }
   }
   

}
