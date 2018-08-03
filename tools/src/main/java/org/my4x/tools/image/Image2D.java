package org.my4x.tools.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

public class Image2D {

    private final BufferedImage image;
    private int width, height;

    private Image2D(int width, int height){
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(new java.awt.Color(0, 0, 0, 255));
        g2d.fillRect(0, 0, width, height);

    }
    public static Image2D withSize(int width, int height){
        return new Image2D(width, height);

    }
    public Image2D withPoints(Stream<ColoredPoint> points){
        points.filter(this::inlimits).forEach(p ->image.setRGB(p.x, p.y, p.getRgb()));
        return this;
    }

    private boolean inlimits(ColoredPoint point) {
        return point.x > 0 &&
                point.y > 0 &&
                point.x < width &&
                point.y < height;
    }

    public void writeTo(File file){
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }
}
