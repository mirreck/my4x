package org.my4x.utilities.svg;

import static org.my4x.utilities.svg.builder.SVGBuilder.path;
import static org.my4x.utilities.svg.builder.SVGBuilder.svg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SVGBuilderTest {

	private static final Logger LOG = LoggerFactory.getLogger(SVGBuilderTest.class.getName());
	
	@Test
	public void test() throws IOException {
		String svg = svg()
			.withElement(
				path("path1")
				.withPoints(10,10,20,20)
				.withFill("none")
				.withStroke("black"))
			.build()
			.toString();
		Assertions.assertThat(svg).isNotEmpty();
		
		LOG.info("svg content:"+ svg);
		File dir = new File("D:\\TEMP\\SVG");
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file = File.createTempFile("test-generation-svg", ".svg",dir);
		FileWriter writer = new FileWriter(file);
		IOUtils.write(svg, writer);
		IOUtils.closeQuietly(writer);
		LOG.info("file:"+ file.getAbsolutePath());
	}

}
