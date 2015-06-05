package org.my4x.utilities.geometry;

public class Segment {
	public final Point a,b;

	public Segment(Point a, Point b) {
		super();
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return "Segment [a=" + a + ", b=" + b + "]";
	}
	
}
