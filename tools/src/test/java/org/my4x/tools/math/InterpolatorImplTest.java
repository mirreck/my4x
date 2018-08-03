package org.my4x.tools.math;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;
import org.my4x.tools.image.ColoredPoint;
import org.my4x.tools.image.Image2D;

import static org.fest.assertions.Assertions.assertThat;


public class InterpolatorImplTest {
    //private static final Logger LOGGER = LoggerFactory.getLogger(InterpolatorImplTest.class);

    @Test
    public void should_interpolate_line(){
        Interpolator<Integer> inter = new LinearInterpolator<>((i, f)-> new Double(i*f).intValue(), (a, b) -> a+b);
        inter.addPoint(1.0,10);
        inter.addPoint(2.0,20);
        inter.addPoint(3.0,30);
        inter.addPoint(4.0,40);

        assertThat(inter.eval(2.5)).isEqualTo(25);

        assertThat(inter.eval(0.0)).isEqualTo(0);

        assertThat(inter.eval(-100.0)).isEqualTo(-1000);

        assertThat(inter.eval(100.0)).isEqualTo(1000);
    }


    @Test
    public void should_interpolate_curve(){
        Interpolator<Integer> inter = new HermiteInterpolator<>((i, f)-> new Double(i*f).intValue(), (a, b) -> a+b);
        inter.addPoint(1.0,10);
        inter.addPoint(2.0,20);
        inter.addPoint(3.0,20);
        inter.addPoint(4.0,10);

        assertThat(inter.eval(1.0)).isEqualTo(10);
        assertThat(inter.eval(2.0)).isEqualTo(20);
        assertThat(inter.eval(3.0)).isEqualTo(20);
        assertThat(inter.eval(4.0)).isEqualTo(10);

        assertThat(inter.eval(2.5)).isEqualTo(23);

        assertThat(inter.eval(0.0)).isEqualTo(0);

        assertThat(inter.eval(-100.0)).isEqualTo(-1000);

        assertThat(inter.eval(5.0)).isEqualTo(0);

        assertThat(inter.eval(105.0)).isEqualTo(-1000);
    }

    @Test
    public void should_draw_nice_curves() throws IOException {

        Interpolator<Integer> inter = new HermiteInterpolator<>((i, f)-> new Double(i*f).intValue(), (a, b) -> a+b);
        inter.addPoint(-60.0,-20);
        inter.addPoint(-30.0,30);
        inter.addPoint(10.0,10);
        inter.addPoint(20.0,20);
        inter.addPoint(30.0,20);
        inter.addPoint(40.0,10);
        inter.addPoint(50.0,20);


        File tempFile = File.createTempFile("test_curves", ".png");


        Stream<ColoredPoint> axisX = IntStream.range(-95, 95).boxed().map(i -> new ColoredPoint(i, 50).toWhite());
        Stream<ColoredPoint> axisY = IntStream.range(-95, 95).boxed().map(i -> new ColoredPoint(50, i).toWhite());

        Stream<ColoredPoint> coloredPointStream = IntStream.range(-50, 50).boxed().map(i -> new ColoredPoint(50+i, 50+inter.eval(i * 1.0)).toRed());

        Image2D.withSize(100,100)
                .withPoints(axisX)
                .withPoints(axisY)
                .withPoints(coloredPointStream)
                .writeTo(tempFile);

        String absolutePath = tempFile.getAbsolutePath();
        System.out.println("absolutePath = "+absolutePath);

    }
}