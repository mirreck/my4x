package org.my4x.tools.math;

public abstract class Interpolable<T> {
    public abstract Interpolable<T> add(Interpolable<T> toAdd);
    public abstract Interpolable<T> multiply(Double factor);
    public abstract Interpolable<T> normalize();
}
