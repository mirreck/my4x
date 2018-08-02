package org.my4x.tools.math;

public interface Interpolator<T> {
    Interpolator<T> addPoint(Double key, T value);
    T eval(Double key);
}
