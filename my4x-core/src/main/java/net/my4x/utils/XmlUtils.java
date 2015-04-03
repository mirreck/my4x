package net.my4x.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlUtils {

	public static Document parseString(final String xml) {
		try {
			return parseStream(new ByteArrayInputStream(xml.getBytes("utf-8")));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Document parseStream(final InputStream xmlis) {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(xmlis));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String prettyXml(final String xml) {

		try {
			final Document document = parseString(xml);
			cleanDocument(document);

			return toString(document, true, false);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String toString(final Document document, final boolean indent, final boolean omitXmlDeclaration)
			throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		final Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitXmlDeclaration ? "yes" : "no");
		transformer.setOutputProperty(OutputKeys.INDENT, indent ? "yes" : "no");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		final StringWriter stringWriter = new StringWriter();
		final StreamResult streamResult = new StreamResult(stringWriter);

		transformer.transform(new DOMSource(document), streamResult);

		return stringWriter.toString();
	}

	private static void cleanDocument(final Document document) throws XPathExpressionException {
		document.getDocumentElement().normalize();
		removeEmptyTextNodes(document);
	}

	private static void removeEmptyTextNodes(final Document document) throws XPathExpressionException {
		final XPath xPath = XPathFactory.newInstance().newXPath();
		final NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']", document,
				XPathConstants.NODESET);

		for (int i = 0; i < nodeList.getLength(); ++i) {
			final Node node = nodeList.item(i);
			node.getParentNode().removeChild(node);
		}
	}
}
