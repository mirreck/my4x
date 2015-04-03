package net.my4x.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.my4x.utils.model.SVGFile;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.xml.sax.SAXException;

public class SVGUtils {

	public SVGFile parse(final InputStream input) {

		try {
			final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			return new SVGFile(dBuilder.parse(input));

		} catch (final ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (final SAXException e) {
			throw new RuntimeException(e);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static void svgToPng(final InputStream svgIn, final OutputStream pngOut) throws TranscoderException,
			IOException {
		final PNGTranscoder t = new PNGTranscoder();

		// Create the transcoder input.
		final TranscoderInput input = new TranscoderInput(svgIn);

		// Create the transcoder output.
		final TranscoderOutput output = new TranscoderOutput(pngOut);

		// Save the image.
		t.transcode(input, output);
	}

}
