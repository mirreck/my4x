package org.my4x.tools.math;

import java.util.function.BiFunction;

public class LinearInterpolator<T> extends AbstractInterpolator<T> {

    public LinearInterpolator(BiFunction<T,Double,T> multiplication, BiFunction<T,T,T> addition){
        super(multiplication,addition);
    }

    protected T interpolate(T p1, T dp1, T p2, T dp2, Double f){
        System.out.println("interpolate = "+p1+" "+p2+" "+f);
        return wrapper.wrap(p2).minus(p1).mult(f).add(p1).val();
    }
}
