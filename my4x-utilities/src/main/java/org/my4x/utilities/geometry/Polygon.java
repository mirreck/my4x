package org.my4x.utilities.geometry;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class Polygon {
	private List<Point> points = Lists.newArrayList();
	public Polygon addPoint(int x, int y){
		return addPoint(new Point(x,y));
	}
	public Polygon addPoint(Point p){
		this.points.add(p);
		return this;
	}
	public List<Point> getPoints() {
		return points;
	}
	public Iterable<Segment> edges() {
		return new Iterable<Segment>() {
			@Override
			public Iterator<Segment> iterator() {
				return new Iterator<Segment>() {
					int index = 0;
					@Override
					public Segment next() {
						Segment segment = (index == points.size()-1)?
							new Segment(points.get(index),points.get(0)):
							new Segment(points.get(index),points.get(index+1));
						index++;
						return segment;
					}
					@Override
					public boolean hasNext() {
						return index < points.size();
					}
				};
			}
		};
	}
	
}
