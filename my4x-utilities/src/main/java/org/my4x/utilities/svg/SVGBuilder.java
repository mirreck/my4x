package org.my4x.utilities.svg;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class SVGBuilder {
	
	private List<SVGElementBuilder> elements = new ArrayList<>();
	
	public SVGBuilder withElement(SVGElementBuilder pb){
		elements.add(pb);
		return this;
	}
	
	public SVGWriter build(){
		String svg = getTemplate();
		StringBuilder defs = new StringBuilder();
		svg = svg.replaceAll("\\{\\{defs\\}\\}", defs.toString());
		
		StringBuilder content = new StringBuilder();
		for (SVGElementBuilder elt : elements) {
			content.append(elt.build());
		}
		svg = svg.replaceAll("\\{\\{content\\}\\}", content.toString());
		return new SVGWriter(svg);
	}
	
	private String getTemplate() {
		 InputStream is = SVGBuilder.class.getResourceAsStream("/template.svg");
	     try {
			return IOUtils.toString(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static SVGBuilder svg(){
		return new SVGBuilder();
	}
	public static SVGPathBuilder path(){
		return new SVGPathBuilder(null);
	}
	public static SVGPathBuilder path(String id){
		return new SVGPathBuilder(id);
	}
	public static SVGCircleBuilder circle(String id){
		return new SVGCircleBuilder(id);
	}
	public static SVGLineBuilder line(String id){
		return new SVGLineBuilder(id);
	}
	
}
