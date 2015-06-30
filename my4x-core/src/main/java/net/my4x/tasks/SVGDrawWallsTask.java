package net.my4x.tasks;

import static org.my4x.utilities.svg.builder.SVGBuilder.*;
import static java.lang.Math.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.my4x.utilities.color.Color;
import org.my4x.utilities.color.ColorRange;
import org.my4x.utilities.geometry.GeometryUtils;
import org.my4x.utilities.geometry.Point;
import org.my4x.utilities.geometry.Polygon;
import org.my4x.utilities.geometry.Segment;
import org.my4x.utilities.svg.builder.SVGBuilder;
import org.my4x.utilities.svg.builder.SVGElementBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mirreck.RandomUtils;
import com.google.common.collect.Lists;

public class SVGDrawWallsTask {
	
	private static final int HEIGHT = 50;
	private static final int WIDTH = 40;
	private static final Logger LOG = LoggerFactory.getLogger(SVGDrawWallsTask.class.getName());
	
	

	
public static void main(String[] args) throws IOException {
	perform();
}


public static void perform() throws IOException {
	Random rnd = new Random();
	SVGBuilder builder = svg();
	//ColorRange range = addBricks(builder);
	
	List<Point> points = Lists.newArrayList();
	for (int i = 0; i < 50; i++) {
		points.add(new Point(RandomUtils.intInInterval(rnd,100, 500),RandomUtils.intInInterval(rnd,100, 500)));
	}
	
	points.add(new Point(100,100));
	points.add(new Point(100,150));
	points.add(new Point(150,100));
	points.add(new Point(150,150));
	int j = 0;
	for (Point point : points) {
		builder.withElement(circle("circle"+j++)
				.withFill("#FF0000")
				.withCoords(
				point.x, 
				point.y, 5));
	}
	addMinimalPolygon(builder, points);
	
//	for (int i = 0; i < 50; i++) {
//		builder.withElement(circle("circle"+i)
//				.withFill("#"+range.pickRgb().cssCode())
//				.withCoords(
//				RandomUtils.intInInterval(rnd,100, 500), 
//				RandomUtils.intInInterval(rnd,100, 500), 5));
//	}
	
	
	String svg = builder
			.withElement(
				path("path1")
				.withPoints(10,10,20,20)
				.withFill("none")
				.withStroke("black"))
			.build()
			.toString();
		
		LOG.info("svg content:"+ svg);
		File dir = new File("D:\\TEMP\\SVG");
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file =new File(dir,"test-walls.svg");
		FileWriter writer = new FileWriter(file);
		IOUtils.write(svg, writer);
		IOUtils.closeQuietly(writer);
		LOG.info("file:"+ file.getAbsolutePath());
}


private static ColorRange addBricks(SVGBuilder builder) {
	ColorRange range = new ColorRange(Color.rgbColor(255, 0, 0), Color.rgbColor(255, 100, 100));
	for (int i = 0; i < 5; i++) {
		builder.withElement(brick(100+(WIDTH+10)*i,100,WIDTH,HEIGHT, "#"+range.pickRgb().cssCode()));
		builder.withElement(brick((int)(100+(WIDTH+10)*(0.5+i)),100+HEIGHT+10,WIDTH,HEIGHT, "#"+range.pickRgb().cssCode()));
	}
	return range;
}


private static void addMinimalPolygon(SVGBuilder builder, List<Point> points) {
	// select min(x) as the first point
	Point p0 = minX(points);
	points.remove(p0);
	Polygon convexHull = GeometryUtils.convexHull(points);
	for (Segment seg : convexHull.edges()) {
		builder.withElement(line("line1").withPoints(seg.a.x,seg.a.y,seg.b.x, seg.b.y));
	}

}


private static Point nextPoint(Point p0, List<Point> points) {
	Point next = points.get(0);
	double minangle = PI;
	for (Point p1 : points) {
		double a1 = atan2(p1.x, p1.y);
		a1 = a1 > PI? a1- 2*PI:a1;
		double a2 = atan2(p0.x, p0.y);
		a1 = a2 > PI? a2- 2*PI:a2;
		double angle = a1 - a2;
		if(angle <minangle){
			minangle = angle;
			next = p1;
		}
	}

	return next;
}


private static Point minX(List<Point> points) {
	Point min = points.get(0);
	for (Point point : points) {
		if(point.x < min.x){
			min = point;
		}
	}
	return min;
}

private static Point maxX(List<Point> points) {
	Point max = points.get(0);
	for (Point point : points) {
		if(point.x > max.x){
			max = point;
		}
	}
	return max;
}


private static SVGElementBuilder brick(int x, int y, int l, int h, String color) {
	return path("brick1").withFill(color).withPoints(x,y,x+l,y,x+l,y+h,x,y+h);
}
}
