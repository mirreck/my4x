package org.my4x.utilities.svg;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractSVGBuilder<T extends AbstractSVGBuilder> implements SVGElementBuilder {

	private static final String DEFAULT_STROKE = "black";
	private static final String DEFAULT_FILL = "none";
	protected final String id;
	protected String fill = DEFAULT_FILL;
	protected String stroke = DEFAULT_STROKE;

	public AbstractSVGBuilder(String id) {
		this.id = id;
	}

	public T withFill(String fill) {
		this.fill = fill;
		return (T) this;
	}

	public T withStroke(String stroke) {
		this.stroke = stroke;
		return (T)this;
	}
	
	protected void applyStyle(StringBuilder sb) {
		if (StringUtils.isNotBlank(fill) || StringUtils.isNotBlank(stroke)) {
			sb.append("style=\"");
			if (StringUtils.isNotBlank(fill)) {
				sb.append("fill:");
				sb.append(fill);
				sb.append(";");
			}
			if (StringUtils.isNotBlank(stroke)) {
				sb.append("stroke:");
				sb.append(stroke);
			}
			sb.append("\" ");
		}

	}

	protected void applyId(StringBuilder sb) {
		if (StringUtils.isNotBlank(id)) {
			sb.append("id=\"");
			sb.append(id);
			sb.append("\" ");
		}
	}

}
