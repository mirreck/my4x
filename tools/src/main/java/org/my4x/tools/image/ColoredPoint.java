package org.my4x.tools.image;

public class ColoredPoint {
    public int x,y;
    // TODO simply add a Color element
    public Color color;
    int alpha = 255;

    public ColoredPoint (int x, int y){
        this.x = x;
        this.y = y;
    }
    public static ColoredPoint of(int x, int y){
        return new ColoredPoint(x,y);
    }
    public ColoredPoint toRed(){
        this.color = Color.RED;
        return this;
    }
    public ColoredPoint toColor(Color color){
        this.color = color;
        return this;
    }

    public ColoredPoint toWhite(){
        this.color = Color.WHITE;
        return this;
    }

    public int getRgb(){
          return ((alpha & 0xFF) << 24) |
            ((color.red   & 0xFF) << 16) |
            ((color.green & 0xFF) << 8)  |
            ((color.blue  & 0xFF) << 0);
    }
}
