package org.my4x.utilities.xml;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;
import org.w3c.dom.Document;

public class XmlUtilsTest {

	@Test
	public void testParseString() {
		Document document = XmlUtils.parseString("<xml>sample</xml>");
		assertThat(document).isNotNull();
		assertThat(document.getFirstChild().getNodeName()).isEqualTo("xml");
		assertThat(document.getFirstChild().getTextContent()).isEqualTo("sample");
	}

	@Test
	public void testPrettyXml() {
		String xmlAsString = "<root><xml>sample</xml></root>";
		String xmlFormated = XmlUtils.prettyXml(xmlAsString);
		assertThat(xmlFormated).isNotNull();
		assertThat(xmlFormated).isNotEqualTo(xmlAsString);
	}

}
