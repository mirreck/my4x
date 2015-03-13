package net.my4x.services.map.utils;

/**
 * Created by thomas.decoster on 22/09/2014.
 */
public class MathUtils {

    public class Coord3D{
        public final double x,y,z;
        public Coord3D(double x,double y,double z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static double max(double ... values) {
        if (values == null || values.length <= 0) {
            throw new IllegalArgumentException();
        }
        double max = values[0];
        for (double value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public Coord3D plan2Cylinder(double x, double y, double minX, double maxX, double minY, double maxY){
        //minX = 0 deg
        //maxX = 2 Pi
        double angleRad = (x-minX)/(maxX-minX)*2*Math.PI;
        double radius = (maxX -minX)/(2*Math.PI);
        return new Coord3D(Math.cos(angleRad) * radius,Math.sin(angleRad) * radius,y);
    }
}
