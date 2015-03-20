package net.my4x.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Lists;

public class CsvIterator {

	private static final String REGEX = "(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

	private static final String DEFAULT_SEPARATOR = ";";
	private final File source;

	private final String separator;

	public CsvIterator(final File source, final String separator) {
		super();
		this.source = source;
		this.separator = separator;
	}

	public CsvIterator(final File source) {
		super();
		this.source = source;
		this.separator = DEFAULT_SEPARATOR;
	}

	public List<String[]> list() {

		final List<String[]> result = Lists.newArrayList();
		FileInputStream is = null;
		try {
			is = new FileInputStream(source);
			final BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			while (in.ready()) {
				final String str = in.readLine();
				result.add(str.split(separator + REGEX));
			}
			in.close();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return result;
	}

}
