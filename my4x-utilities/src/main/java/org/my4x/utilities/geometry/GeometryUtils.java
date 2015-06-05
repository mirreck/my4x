package org.my4x.utilities.geometry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;


public class GeometryUtils {
	public static boolean pointInsidePolygon(Point pt, Polygon poly) {
		boolean inside = false;
		for (Segment seg : poly.edges()) {
			if (((seg.a.y > pt.y) != (seg.b.y > pt.y))
					&& (pt.x < (seg.b.x - seg.a.x) * (pt.y - seg.a.y)
							/ (seg.b.y - seg.a.y) + seg.a.x)) {
				inside = !inside;
				System.out.println("intersect :" + seg);
			}
		}
		System.out.println("inside ("+pt+"):" + inside);
		return inside;
	}
	
	public static Point[] asPointArray(int ... coord){
		if(coord.length %2 != 0){
			throw new IllegalArgumentException("number of coorinates must ne even");
		}
		List<Point> points = Lists.newArrayList();
		for (int i = 0; i < coord.length; i+= 2) {
			points.add(new Point(coord[i], coord[i+1]));
		}
		
		return points.toArray(new Point[]{});
	}
	
	public static long cross(Point O, Point a, Point b) {
		return (a.x - O.x) * (b.y - O.y) - (a.y - O.y) * (b.x - O.x);
	}
	
	public static Point[] convexHullPoints(List<Point> points) {
		return convexHull(points.toArray(new Point[]{}));
	}
 
	public static Point[] convexHull(Point[] points) {
 
		if (points.length > 1) {
			int n = points.length, k = 0;
			Point[] hull = new Point[2 * n];
			sortByX(points);
 
			// Build lower hull
			for (int i = 0; i < n; ++i) {
				while (k >= 2 && cross(hull[k - 2], hull[k - 1], points[i]) <= 0) {
					k--;
				}
				hull[k++] = points[i];
			}
 
			// Build upper hull
			for (int i = n - 2, t = k + 1; i >= 0; i--) {
				while (k >= t && cross(hull[k - 2], hull[k - 1], points[i]) <= 0) {
					k--;
				}
				hull[k++] = points[i];
			}
			if (k > 1) {
				hull = Arrays.copyOfRange(hull, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
			}
			return hull;
		} else if (points.length <= 1) {
			return points;
		} else{
			return null;
		}
	}

	private static void sortByX(Point[] points) {
		Arrays.sort(points, new Comparator<Point>(){
			@Override
			public int compare(Point p1, Point p2) {
				if (p1.x == p2.x) {
					return p1.y - p2.y;
				} else {
					return p1.x - p2.x;
				}
			}});
	}
	
	
	public static Polygon convexHull(final List<Point> srcPoints){

		Polygon poly = new Polygon();
		Point[] convexHullPoints = convexHullPoints(srcPoints);
		for (Point point : convexHullPoints) {
			poly.addPoint(point);
		}
		return poly;
	}
	
	
	
	public static Point minX(List<Point> points) {
		Point min = points.get(0);
		for (Point point : points) {
			if(point.x < min.x){
				min = point;
			}
		}
		return min;
	}

	public static Point maxX(List<Point> points) {
		Point max = points.get(0);
		for (Point point : points) {
			if(point.x > max.x){
				max = point;
			}
		}
		return max;
	}
}
