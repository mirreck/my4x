package org.my4x.utilities.svg;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SVGFile {
	final Document xmldoc;

	public SVGFile(final Document doc) {
		super();
		this.xmldoc = doc;
	}

	public SVGElementList elementsWithClass(final String className) {

		try {
			final XPath xPath = XPathFactory.newInstance().newXPath();
			final NodeList nodeList = (NodeList) xPath.evaluate("descendant-or-self::*[contains(@class,'" + className
					+ "')]", xmldoc, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); ++i) {
				final Node node = nodeList.item(i);
			}

			final SVGElementList res = new SVGElementList();
			return res;
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e);
		}

	}
}
