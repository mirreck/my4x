package org.my4x.utilities.geometry;

public class Point {
	public final int x, y;

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
}
