package org.my4x.utilities.svg;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class SVGCircleBuilderTest {

	@Test
	public void test() {
		String circleStr = new SVGCircleBuilder("circleid").withFill("red").withCoords(10,10,20).build();
		Assertions.assertThat(circleStr).isNotEmpty().isEqualTo("<circle id=\"circleid\" cx=\"10\" cy=\"10\" r=\"20\" style=\"fill:red;stroke:black\" />");
	}

}
