package org.my4x.utilities.svg.builder;


public class SVGPolygonBuilder  extends AbstractSVGBuilder<SVGPolygonBuilder> {

	private static final String POLYGON = "polygon";
	private int[] points;
	
	public SVGPolygonBuilder(String id) {
		super(id);
	}

	@Override
	public String build() {
		// <polygon points="20,20 40,25 60,40 80,120 120,140 200,180"
		// style="stroke:black;fill:none"/>
		StringBuilder sb = new StringBuilder("<" + tagName() + " ");
		applyId(sb);
		sb.append("points=\"");
		for (int i = 0; i < points.length; i+=2) {
			if(i>0){
				sb.append(" ");
			}
			sb.append(points[i]);
			sb.append(",");
			sb.append(points[i+1]);
		}
		sb.append("\" ");
		applyStyle(sb);
		sb.append("/>");
		return sb.toString();
	}

	protected String tagName() {
		return POLYGON;
	}
	public SVGPolygonBuilder withPoints(int... points) {
		this.points = points;
		return this;
	}

}
