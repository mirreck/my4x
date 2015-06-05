package org.my4x.utilities.svg;


public class SVGPathBuilder  extends AbstractSVGBuilder<SVGPathBuilder> {

	private int[] points;
	
	public SVGPathBuilder(String id) {
		super(id);
	}

	@Override
	public String build() {
		// <path id="clipPathDoorShape"
		// d="M-1000,-1000L1000,-1000L1000,1000L-1000,1000ZM19,81L81,81L91,39A80,80 0 0,0 9,39L19,81L0,81L0,100L100,100L100,81L0,81Z"
		// style="stroke:black;fill:none"/>
		StringBuilder sb = new StringBuilder("<path ");
		applyId(sb);
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
		applyStyle(sb);
		sb.append("/>");
		return sb.toString();
	}
	public SVGPathBuilder withPoints(int... points) {
		this.points = points;
		return this;
	}

}
