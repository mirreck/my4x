package org.my4x.tools.image;

import java.util.function.BiFunction;

import static org.my4x.tools.math.NumberUtils.asInt;

public class Color {
    public int red,green,blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    public static final Color RED = new Color(255,0,0);
    public static final Color WHITE = new Color(255,255,255);
    public static final Color BLACK = new Color(0,0,0);

    public static Color add(Color a, Color b){
        return  new Color(a.red+b.red, a.green+b.green, a.blue+b.blue);
    }

    public static Color multiply(Color t, Double f) {
        return new Color(asInt(t.red *f),asInt(t.green * f), asInt(t.blue * f));
    }
}
