package org.my4x.utilities.geometry.delaunay;

import java.util.List;

import org.my4x.utilities.geometry.Point;
import org.my4x.utilities.geometry.Polygon;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class Delaunay{
	private List<Point> convexHull;
	public List<Point> getConvexHull() {
		return convexHull;
	}
	private List<DTriangle> triangles;
	public Delaunay(Point a, Point b, Point c){
		this.convexHull = Lists.newArrayList(a,b,c);
		this.triangles = Lists.newArrayList(new DTriangle(a, b, c));
	}
	public void insertPoint(Point p){
		for (DTriangle triangle : triangles) {
			if(triangle.contains(p)){
				triangles.addAll(triangle.split(p));
				triangles.remove(triangle);
				break;
			}
		}
		addExternalPoint(p);
	}
	private void flipTriangles(DTriangle a, DTriangle b) {
		markUnflipped(triangles);
		recursiveFlipTriangles(a,b);
		
	}
	
	private int distsquare(Point a, Point b){
		return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
	}
	
	private void markUnflipped(List<DTriangle> triangles2) {
		for (DTriangle triangle : triangles) {
			triangle.flipped = false;
		}
	}
	
	private void recursiveFlipTriangles(DTriangle tr1, DTriangle tr2) {
		// identify common edge
		List<Point> commonPoints = Lists.newArrayList();
		if(tr1.a.sameAs(tr2.a)
				||tr1.a.sameAs(tr2.b)
				||tr1.a.sameAs(tr2.c)){
			
		}
	}
	
	private void addExternalPoint(Point p) {
		// TODO Auto-generated method stub
		
	}
	public List<Polygon> getTriangles(){
		return Lists.transform(triangles, new Function<DTriangle, Polygon>(){

			@Override
			public Polygon apply(DTriangle tri) {
				return tri.poly;
			}
		});
	}
}