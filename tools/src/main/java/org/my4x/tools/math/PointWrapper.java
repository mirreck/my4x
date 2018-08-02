package org.my4x.tools.math;

import java.util.function.BiFunction;

public class PointWrapper<T> {
    private final BiFunction<T,Double,T> multiplication;
    private final BiFunction<T,T,T> addition;
    public PointWrapper(BiFunction<T,Double,T> multiplication, BiFunction<T,T,T> addition){
        this.multiplication = multiplication;
        this.addition = addition;
    }

    public Wraped wrap(T src){
        return new Wraped(src);
    }

    public class Wraped {
        private T val;
        private Wraped(T src){
            this.val = src;
        }
        public Wraped mult(Double d){
            return wrap(multiplication.apply(this.val,d));
        }
        public Wraped add(T toAdd){
            return wrap(addition.apply(val, toAdd));
        }
        public Wraped minus(T toAdd){
            return wrap(addition.apply(val, multiplication.apply(toAdd,-1.0)));
        }
        public T val(){return val;}
    }
}
