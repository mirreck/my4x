package org.my4x.tools.math;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.my4x.tools.image.Color;
import org.my4x.tools.image.ColoredPoint;
import org.my4x.tools.image.Image2D;
import org.my4x.tools.io.FileUtils;

import static org.fest.assertions.Assertions.assertThat;
import static org.my4x.tools.image.ColoredPoint.*;


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
        inter.addPoint(20.0,-20);
        inter.addPoint(30.0,20);
        inter.addPoint(40.0,10);
        inter.addPoint(50.0,20);

        Interpolator<Color> interc = new HermiteInterpolator<>(
                (c,f)-> new Color((int)(c.red*f),(int)(c.green*f),(int)(c.blue*f)),
                (c1,c2) -> new Color(c1.red+c2.red,c1.green+c2.green,c1.blue+c2.blue));

        interc.addPoint(-80.0, new Color(255,255,255));
        interc.addPoint(-20.0,new Color(255,100,255));
        interc.addPoint(30.0,new Color(0,255,0));
        interc.addPoint(60.0,new Color(0,255,255));


        File tempFile = File.createTempFile("test_curves", ".png");


        Stream<ColoredPoint> axisX = IntStream.range(0, 100).boxed().map(i -> new ColoredPoint(i, 50).toWhite());
        Stream<ColoredPoint> axisY = IntStream.range(0, 100).boxed().map(i -> new ColoredPoint(50, i).toWhite());

        Stream<ColoredPoint> coloredPointStream = IntStream.range(-50, 50).boxed()
                .map(i -> new ColoredPoint(50+i, 50-inter.eval(i * 1.0)).toColor(interc.eval(i * 1.0)));

        Image2D.withSize(100,100)
                .withPoints(axisX)
                .withPoints(axisY)
                .withPoints(coloredPointStream)
                .writeTo(tempFile);

        String absolutePath = tempFile.getAbsolutePath();
        System.out.println("absolutePath = "+absolutePath);

    }


    private int intval(Double d){
        return d.intValue();
    }

    private Stream<Double> dStream(Double min, Double max, Double step){
        return DoubleStream.iterate(min, d -> d + step).limit(new Double(max/step).longValue()).boxed();
    }

    @Test
    public void should_draw_nice_2D_curves() throws IOException {
        Interpolator<ColoredPoint> inter
                //=new LinearInterpolator<>(
                = new HermiteInterpolator<>(
                (p, f)-> new ColoredPoint(intval(p.x*f), intval(p.y*f)).toWhite(),
                (a, b) -> new ColoredPoint(a.x+b.x, a.y+b.y).toWhite());

        inter.addPoint(-100.0,new ColoredPoint(30,10).toWhite());
        inter.addPoint(00.0,new ColoredPoint(10,10).toWhite());
        inter.addPoint(25.0,new ColoredPoint(10,30).toWhite());
        inter.addPoint(50.0,new ColoredPoint(30,30).toWhite());
        inter.addPoint(75.0,new ColoredPoint(30,10).toWhite());
        inter.addPoint(100.0,new ColoredPoint(10,10).toWhite());
        inter.addPoint(125.0,new ColoredPoint(10,30).toWhite());

        Stream<ColoredPoint> ps = FileUtils.loadObjects("/points.txt", t -> new ColoredPoint(Integer.valueOf(t[0]), Integer.valueOf(t[1])).toRed());

        File tempFile = File.createTempFile("test_2D", ".png");


        Stream<ColoredPoint> axisX = IntStream.range(0, 100).boxed().map(i -> new ColoredPoint(i, 50).toWhite());
        Stream<ColoredPoint> axisY = IntStream.range(0, 100).boxed().map(i -> new ColoredPoint(50, i).toWhite());
        Stream<ColoredPoint> pointStream =
                dStream(0.0, 100.0,0.5)
                .map(inter::eval);

        Image2D.withSize(100,100)
                .withPoints(axisX)
                .withPoints(axisY)
                .withPoints(pointStream)
                .withPoints(ps)
                .writeTo(tempFile);

        String absolutePath = tempFile.getAbsolutePath();
        System.out.println("absolutePath = "+absolutePath);

    }


}