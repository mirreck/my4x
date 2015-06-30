package org.my4x.utilities.svg.builder;



public class SVGCircleBuilder extends AbstractSVGBuilder<SVGCircleBuilder> {

	
	private int cx;
	private int cy;
	private int r;

	public SVGCircleBuilder(String id) {
		super(id);
	}

	@Override
	public String build() {
		//<circle cx="50" cy="50" r="40" stroke="black" stroke-width="3" fill="red" />
		
		StringBuilder sb = new StringBuilder("<circle ");
		applyId(sb);

		sb.append("cx=\"").append(cx).append("\" ");
		sb.append("cy=\"").append(cy).append("\" ");
		sb.append("r=\"").append(r).append("\" ");
		
		applyStyle(sb);
		sb.append("/>");
		return sb.toString();
	}

	public SVGCircleBuilder withCx(int cx) {
		this.cx = cx;
		return this;
	}
	
	public SVGCircleBuilder withCy(int cy) {
		this.cy = cy;
		return this;
	}
	
	public SVGCircleBuilder withR(int r) {
		this.r = r;
		return this;
	}
	public SVGCircleBuilder withCoords(int cx, int cy, int r) {
		this.cx = cx;
		this.cy = cy;
		this.r = r;
		return this;
	}

}
