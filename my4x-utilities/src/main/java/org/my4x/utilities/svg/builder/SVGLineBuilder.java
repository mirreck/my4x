package org.my4x.utilities.svg.builder;


public class SVGLineBuilder  extends AbstractSVGBuilder<SVGLineBuilder> {

	private static final String POLYLINE = "polyline";
	private int[] points;
	
	public SVGLineBuilder(String id) {
		super(id);
	}

	@Override
	public String build() {
		// <polyline points="20,20 40,25 60,40 80,120 120,140 200,180"

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
		return POLYLINE;
	}
	public SVGLineBuilder withPoints(int... points) {
		this.points = points;
		return this;
	}

}
