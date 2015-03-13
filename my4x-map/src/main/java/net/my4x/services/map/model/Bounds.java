package net.my4x.services.map.model;

/**
 * Created by thomas.decoster on 01/10/2014.
 */
public class Bounds {
    public int minX,maxX,minY,maxY;

    public Bounds(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
}
