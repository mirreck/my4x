package org.my4x.utilities.svg;

import static org.my4x.utilities.svg.SVGBuilder.path;
import static org.my4x.utilities.svg.SVGBuilder.svg;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class SVGBuilderTest {

	@Test
	public void test() {
		String svg = svg()
			.withElement(
				path("path1")
				.withPoints(10,10,20,20)
				.withFill("none")
				.withStroke("black"))
			.build()
			.toString();
		Assertions.assertThat(svg).isNotEmpty();
	}

}
