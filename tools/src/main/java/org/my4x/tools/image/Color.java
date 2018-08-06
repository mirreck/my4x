package org.my4x.tools.image;

public class Color {
    public int red,green,blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    public static final Color RED = new Color(255,0,0);
    public static final Color WHITE = new Color(255,255,255);
}
