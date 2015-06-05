package org.my4x.utilities.geometry;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class GeometryUtilsTest {
	
	private static Polygon polygon = new Polygon().addPoint(10, 10).addPoint(0, -10).addPoint(-10, 10);

	
	@Test
	public void should_be_inside() {
		boolean insidePolygon = GeometryUtils.pointInsidePolygon(
				new Point(0,0), 
				polygon);
		Assertions.assertThat(insidePolygon).isTrue();
	}
	
	@Test
	public void should_not_be_inside() {
		boolean insidePolygon = GeometryUtils.pointInsidePolygon(
				new Point(0,20), 
				polygon);
		Assertions.assertThat(insidePolygon).isFalse();
	}
	
	@Test
	public void should_compute_convex_hull() {
		Point[] pointArray = GeometryUtils.asPointArray(
				0,50,
				0,100,
				100,100,
				100,0,
				50,0,
				50,50);
		Point[] convexHull = GeometryUtils.convexHull(pointArray);
		Assertions.assertThat(convexHull).hasSize(5);
	}
	

}
