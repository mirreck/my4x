package net.my4x.game.map;

import org.my4x.tools.image.Color;
import org.my4x.tools.io.FileUtils;
import org.my4x.tools.math.HermiteInterpolator;
import org.my4x.tools.math.Interpolator;

public class ColorProfile {

    private Interpolator<Color> intepolator
            = new HermiteInterpolator<>(
            Color::multiply,
            Color::add);


    public ColorProfile(String filePath) {
        FileUtils.points(FileUtils.inputStream(this, filePath)).map(obj -> intepolator.addPoint(key(obj), color(obj)));
    }

    private Color color(String[] obj) {
        return new Color(Integer.parseInt(obj[1]), Integer.parseInt(obj[2]), Integer.parseInt(obj[3]));
    }

    private Double key(String[] obj) {
        return Double.parseDouble(obj[0]);
    }

    public Color colorAt(Double height){
        return Color.BLACK;
    }
}
