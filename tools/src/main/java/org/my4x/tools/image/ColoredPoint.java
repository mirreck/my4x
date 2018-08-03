package org.my4x.tools.image;

public class ColoredPoint {
    public int x,y;
    // TODO simply add a Color element
    int red = 0,green = 0,blue = 0, alpha = 255;

    public ColoredPoint (int x, int y){
        this.x = x;
        this.y = y;
    }
    public ColoredPoint toRed(){
        this.red = 255;
        return this;
    }
    public ColoredPoint toColor(Color color){
        this.red = color.red;
        this.green = color.green;
        this.blue = color.blue;
        return this;
    }

    public ColoredPoint toWhite(){
        this.red = 255;
        this.green = 255;
        this.blue = 255;
        return this;
    }

    public int getRgb(){
          return ((alpha & 0xFF) << 24) |
            ((red   & 0xFF) << 16) |
            ((green & 0xFF) << 8)  |
            ((blue  & 0xFF) << 0);
    }
}
