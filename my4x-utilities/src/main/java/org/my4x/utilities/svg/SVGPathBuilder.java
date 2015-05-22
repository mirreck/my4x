package org.my4x.utilities.svg;

import org.apache.commons.lang.StringUtils;

public class SVGPathBuilder implements SVGElementBuilder {

	private static final String DEFAULT_STROKE = "black";
	private static final String DEFAULT_FILL = "none";
	private final String id;
	private String fill = DEFAULT_FILL;
	private String stroke = DEFAULT_STROKE;
	private int[] points;
	
	public SVGPathBuilder(String id) {
		super();
		this.id = id;
	}

	@Override
	public String build() {
		// <path id="clipPathDoorShape"
		// d="M-1000,-1000L1000,-1000L1000,1000L-1000,1000ZM19,81L81,81L91,39A80,80 0 0,0 9,39L19,81L0,81L0,100L100,100L100,81L0,81Z"
		// style="stroke:black;fill:none"/>
		StringBuilder sb = new StringBuilder("<path ");
		if (StringUtils.isNotBlank(id)) {
			sb.append("id=\"");
			sb.append(id);
			sb.append("\" ");
		}
		sb.append("d=\"M");
		for (int i = 0; i < points.length; i+=2) {
			if(i>0){
				sb.append("L");
			}
			sb.append(points[i]);
			sb.append(",");
			sb.append(points[i+1]);
		}
		sb.append("Z\" ");
		sb.append("style=\"fill:");
		sb.append(fill);
		sb.append(";stroke:");
		sb.append(stroke);
		sb.append("\" ");
		sb.append("/>");
		return sb.toString();
	}

	public SVGPathBuilder withFill(String fill) {
		this.fill = fill;
		return this;
	}

	public SVGPathBuilder withStroke(String stroke) {
		this.stroke = stroke;
		return this;
	}
	public SVGPathBuilder withPoints(int... points) {
		this.points = points;
		return this;
	}

}
