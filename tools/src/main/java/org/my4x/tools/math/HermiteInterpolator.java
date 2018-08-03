package org.my4x.tools.math;

import java.util.function.BiFunction;

public class HermiteInterpolator<T> extends AbstractInterpolator<T> {

    public HermiteInterpolator(BiFunction<T,Double,T> multiplication, BiFunction<T,T,T> addition){
        super(multiplication,addition);
    }
/*
f(x) = a x^3 + b x^2 + c x + d
f'(x) = 3a x^2 + 2b x + c

f(0) = p1 = d
f(1) = p2 = a + b + c +d
f'(0) = dp1 = c
f'(1) = dp2 = 3a + 2b + c

=>
> d = p1
> c = dp1

a + b = p2 - p1 - dp1

b = p2 - p1 - dp1 - a

3a + 2b = dp2 - dp1

3a = dp2 - dp1 - 2 (p2 - p1 - dp1 - a)
3a = dp2 - dp1 - 2 p2 +2p1 +2dp1 +2a

a =dp2 + dp1 -2p2 +2p1
> a =dp2 + dp1 + 2 (p1-p2)

b = p2 - p1 - dp1 - (dp2 + dp1 + 2 (p1-p2))
b = p2 - p1 - dp1 - dp2 - dp1 + 2 (p2-p1)
> b = 3 ( p2 - p1) - 2 dp1 - dp2



 */
    protected T interpolate(T p1, T dp1, T p2, T dp2, Double f){
        System.out.println("p1="+p1+" p2="+p2+" f="+f);
        T p2mp1 = wrapper.wrap(p2).minus(p1).val();

        T a = wrapper.wrap(dp2).add(dp1).minus(2.0,p2mp1).val();
        T b = wrapper.wrap(p2mp1).mult(3.0).minus(2.0, dp1).minus(dp2).val();
        T c = dp1;
        T d = p1;
        System.out.println("a="+a+" b="+b+" c="+c+" d="+d+" f="+f);
        System.out.println("f(0)="+wrapper.wrap(d).add(0.0,c).add(0.0,b).add(0.0,a).val());
        System.out.println("f(1)="+wrapper.wrap(d).add(c).add(b).add(a).val());
        return wrapper.wrap(d).add(f,c).add(f*f,b).add(f*f*f,a).val();
    }


}
