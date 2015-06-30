package org.my4x.utilities.svg;

import static org.junit.Assert.*;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.my4x.utilities.svg.builder.SVGPathBuilder;

public class SVGPathBuilderTest {

	@Test
	public void test() {
		String pathStr = new SVGPathBuilder("pathid").withFill("red").withPoints(10,10,20,20).build();
		Assertions.assertThat(pathStr).isNotEmpty().isEqualTo("<path id=\"pathid\" d=\"M10,10L20,20Z\" style=\"fill:red;stroke:black\" />");
	}

}
