package org.my4x.utilities.geometry.delaunay;

import java.util.List;

import org.my4x.utilities.geometry.GeometryUtils;
import org.my4x.utilities.geometry.Point;
import org.my4x.utilities.geometry.Polygon;

import com.google.common.collect.Lists;

public class DTriangle{
	public final Point a,b,c;
	public DTriangle ab, bc, ac;
	public DTriangle getAb() {
		return ab;
	}
	public void setAb(DTriangle ab) {
		this.ab = ab;
	}
	public DTriangle getBc() {
		return bc;
	}
	public void setBc(DTriangle bc) {
		this.bc = bc;
	}
	public DTriangle getAc() {
		return ac;
	}
	public void setAc(DTriangle ac) {
		this.ac = ac;
	}
	public final Polygon poly;
	public boolean flipped;
	public DTriangle(Point a, Point b, Point c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.poly = new Polygon().addPoint(a).addPoint(b).addPoint(c);
	}
	public boolean contains(Point p){
		return GeometryUtils.pointInsidePolygon(p,poly);
	}
	public List<DTriangle> split(Point p){
		DTriangle tri1 = new DTriangle(a, b, p);
		DTriangle tri2 = new DTriangle(a, p, c);
		DTriangle tri3 = new DTriangle(p, b, c);
		tri1.setAc(tri2);
		tri2.setAb(tri1);
		tri1.setBc(tri3);
		tri3.setAb(tri1);
		tri2.setBc(tri3);
		tri3.setAc(tri2);
		return Lists.newArrayList(
				tri1,
				tri2,
				tri3);
	}
}