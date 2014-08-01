package net.my4x.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class SVGDrawBackgroundTask {
   public static void generateSVG(File outputFile) throws IOException{
      InputStream is = SVGDrawBackgroundTask.class.getResourceAsStream("tpl.svg");
      String filecontent = IOUtils.toString(is);
      String content = "";
      int w = 200;
      int[][] points = new int[][]{{200,100},{200+w,100},{200+w,200},{300+w,200},{300+w,200+w},{200+w,200+w},{200+w,300+w},{200,300+w}
      ,{200,200+w},{100,200+w},{100,200},{200,200}};
      int[][] add = new int[][]{{-1,-1},{1,-1},{1,-1},{1,-1},{1,1},{1,1},{1,1},{-1,1}
      ,{-1,1},{-1,1},{-1,-1},{-1,-1}};
      String[] fills = new String[]{
            "url(#sn)","url(#oe)","url(#sn)","url(#oe)",
            "url(#ns)","url(#oe)","url(#ns)","url(#eo)",
            "url(#ns)","url(#eo)","url(#sn)","url(#eo)"};
      
      int fact1 = 20;
      int fact2 = 80;
      
      content += computePath(95, points, add,"#DDDDDD").toString();
      content += computePath(fact2, points, add,"#FFFFFF").toString();
      content += computePath(fact1, points, add,"#777777").toString();
      //content += computePath(5, points, add).toString();
      
      int[][] computePointList1 = computePointList(points, add,fact1);
      int[][] computePointList2 = computePointList(points, add,fact2);
      
      for (int i = 0; i < computePointList2.length-1; i++) {
         content += "<path d=\"M"+computePointList1[i][0]+","+computePointList1[i][1]
               +"L"+computePointList2[i][0]+","+computePointList2[i][1]
               +"L"+computePointList2[i+1][0]+","+computePointList2[i+1][1]
               +"L"+computePointList1[i+1][0]+","+computePointList1[i+1][1]
               +"Z\" style=\"stroke:black;fill:"+fills[i]+"\"/>\n";
      }
      int j = computePointList2.length -1;
      content += "<path d=\"M"+computePointList1[j][0]+","+computePointList1[j][1]
            +"L"+computePointList2[j][0]+","+computePointList2[j][1]
            +"L"+computePointList2[0][0]+","+computePointList2[0][1]
            +"L"+computePointList1[0][0]+","+computePointList1[0][1]
            +"Z\" style=\"stroke:black;fill:"+fills[j]+"\"/>\n";
      
      
      
      for (int i = 0; i < computePointList2.length; i++) {
         content += "<path d=\"M"+computePointList1[i][0]+","+computePointList1[i][1]+"L"+computePointList2[i][0]+","+computePointList2[i][1]+"Z\" style=\"stroke:black;fill:none\"/>";
      }
      
      
//      for (int i = 0; i <= 5; i++) {
//         content += "<path d=\"M0,"+i*100+"L500,"+i*100+"Z\" style=\"stroke:red;fill:none\"/>";
//      }
//      for (int i = 0; i <= 5; i++) {
//         content += "<path d=\"M"+i*100+",0L"+i*100+",500Z\" style=\"stroke:red;fill:none\"/>";
//      }
      String defs ="<clipPath id=\"clipPathDoor\">" +
            "<path d=\"M20,80L10,40A80,80 0 0,1 90,40L80,80Z\" style=\"stroke:black;fill:none\"/>"
            +"</clipPath>\n";
      defs += "<g id=\"door\">";
      // DOOR
      defs += "<path d=\"M20,65L10,25A80,80 0 0,1 90,25L80,65Z\" style=\"stroke-width:2;stroke:black;fill:url(#sn);clip-path: url(#clipPathDoor);\"/>";
      defs += "<path d=\"M20,80L10,40A80,80 0 0,1 90,40L80,80Z\" style=\"stroke-width:2;stroke:black;fill:none\"/>";
      defs += "<path d=\"M20,80L80,80L80,65L20,65Z\" style=\"stroke-width:2;stroke:black;fill:#777777\"/>\n";
      defs += "</g>\n";
      
      
      content += "<use xlink:href=\"#door\" x=\"0\" y=\"0\" />\n";
      content += "<use xlink:href=\"#door\" x=\"500\" y=\"0\"  transform = \"rotate(90 550 50)\"/>\n";
      content += "<use xlink:href=\"#door\" x=\"0\" y=\"500\"  transform = \"rotate(180 50 550)\"/>\n";
      content += "<use xlink:href=\"#door\" x=\"500\" y=\"500\"  transform = \"rotate(-90 550 550)\"/>\n";
      filecontent= filecontent.replaceAll("##content##", content);
      filecontent= filecontent.replaceAll("##defs##", defs);
      System.out.println("RES="+content);
      FileOutputStream output = new FileOutputStream(outputFile);
      IOUtils.write(filecontent, output);
      IOUtils.closeQuietly(output);
      IOUtils.closeQuietly(is);
   }

   private static int[][] computePointList(int[][] points, int[][] add, int fact){
      int[][] res = new int[points.length][2];
      for (int i = 0; i < points.length; i++) {
         res[i][0] = points[i][0]+fact*add[i][0];
         res[i][1] = points[i][1]+fact*add[i][1];
      }
      return res;
   }

   private static StringBuilder computePath(int fact, int[][] points, int[][] add, String fill) {
      StringBuilder sb = new StringBuilder("<path d=\"");
      char act = 'M';
      for (int i = 0; i < points.length; i++) {
         int[] pt = points[i];
         sb.append(act).append(pt[0]+fact*add[i][0]).append(",").append(pt[1]+fact*add[i][1]);
         act = 'L';
      }
      sb.append("Z\" style=\"stroke:black;fill:"+fill+"\"/>");
      return sb;
   }

}
