package org.my4x.tools.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.stream.IntStream;


public abstract class AbstractInterpolator<T> implements Interpolator<T> {




    private class KeyVal<T> {
        Double key;
        T val;
        public KeyVal(Double key, T val){
            this.key = key;
            this.val = val;
        }
    }
    private class PointList<T> extends ArrayList<KeyVal<T>> {
        public int floorIndex(Double key){
            int index = 0;
            while(index <this.size()){
                if(this.get(index).key > key){
                    return index -1;
                }
                index++;
            }
            return index;
        }

        public void newPoint(Double key, T value) {
            this.add(new KeyVal<>(key, value));
            Collections.sort(this, Comparator.comparing(kv -> kv.key));
        }
    }


    private PointList<T> list  = new PointList<>();


    protected PointWrapper<T> wrapper;

    public AbstractInterpolator(BiFunction<T,Double,T> multiplication, BiFunction<T,T,T> addition){

        this.wrapper = new PointWrapper<>(multiplication,addition);
    }

    @Override
    public AbstractInterpolator<T> addPoint(Double key, T value) {
        list.newPoint(key,value);
        return this;
    }

    @Override
    public T eval(Double key) {
        if(list.size() < 4){
            throw new RuntimeException("At least 4 points required for interpolation");
        }

        int index = list.floorIndex(key);
        if(index <1){
            // first points
            System.out.println("first points index = "+index);
            return interpolateFirst(key);
        }

        if(index > list.size()-3){
            // last points
            System.out.println("last points = "+index);
            return interpolateLast(key);
        }

        T p0 = list.get(index-1).val;
        T p1 = list.get(index).val;
        T p2 = list.get(index+1).val;
        T p3 = list.get(index+2).val;

        T dp1 = d(p0, p2);
        T dp2 = d(p1, p3);

        Double k1 = list.get(index).key;
        Double k2 = list.get(index+1).key;
        Double f = (key - k1) / (k2 - k1);
        return interpolate(p1, dp1,p2,dp2,f);
    }

    protected T interpolateFirst(Double key){
        KeyVal<T> kv1 = list.get(0);
        KeyVal<T> kv2 = list.get(1);
        T p3 = list.get(2).val;
        T dp1 = d(kv1.val, kv2.val);
        T dp2 = d(kv1.val, p3);
        Double f = (key - kv1.key) / (kv2.key - kv1.key);
        return interpolate(kv1.val, dp1,kv2.val,dp2,f);
    }

    protected T interpolateLast(Double key){
        T p0 = list.get(list.size()-3).val;
        KeyVal<T> kv1 = list.get(list.size()-2);
        KeyVal<T> kv2 = list.get(list.size()-1);

        T dp1 = d(p0, kv2.val);
        T dp2 = d(kv1.val, kv2.val);
        Double f = (key - kv1.key) / (kv2.key - kv1.key);
        return interpolate(kv1.val, dp1,kv2.val,dp2,f);
    }

    private T d(T p1, T p2){
        return wrapper.wrap(p2).minus(p1).val();
    }


    protected abstract T interpolate(T p1, T dp1, T p2, T dp2, Double f);
}
