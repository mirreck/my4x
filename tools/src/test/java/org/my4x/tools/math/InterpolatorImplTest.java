package org.my4x.tools.math;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class InterpolatorImplTest {


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

        assertThat(inter.eval(2.5)).isEqualTo(20);

        assertThat(inter.eval(0.0)).isEqualTo(0);

        assertThat(inter.eval(-100.0)).isEqualTo(-1000);

        assertThat(inter.eval(5.0)).isEqualTo(0);

        assertThat(inter.eval(105.0)).isEqualTo(-1000);
    }

}