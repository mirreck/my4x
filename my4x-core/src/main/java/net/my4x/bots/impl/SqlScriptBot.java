package net.my4x.bots.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import net.my4x.bots.AbstractBot;
import net.my4x.bots.Bot;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

public class SqlScriptBot extends AbstractBot<TableData, List<String>> implements Bot<TableData, List<String>> {

	private final File targetDir;

	public SqlScriptBot(final File targetDir) {
		this.targetDir = targetDir;
	}

	@Override
	public List<String> go() {
		final List<String> lines = super.go();
		try {
			final File targetFile = new File(targetDir, this.input.getName() + ".sql");
			targetFile.getParentFile().mkdirs();
			targetFile.createNewFile();
			final FileOutputStream fos = new FileOutputStream(targetFile);
			IOUtils.write(StringUtils.join(lines, ";\n") + ";\n", fos);
			IOUtils.closeQuietly(fos);
			return lines;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected List<String> work(final TableData tdata) {
		final List<String[]> src = tdata.getLines();
		final String[] firstLine = src.get(0);
		final List<String> results = Lists.newArrayList();
		final String prefix = new StringBuilder("INSERT INTO ").append(tdata.getName()).append("(")
				.append(StringUtils.join(firstLine, ",")).append(") VALUES (\"").toString();
		for (int i = 1; i < src.size(); i++) {
			final StringBuilder sb = new StringBuilder(prefix);
			sb.append(StringUtils.join(src.get(i), "\",\""));
			sb.append("\")");
			results.add(sb.toString());
		}
		return results;
	}
}
