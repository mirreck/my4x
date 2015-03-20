package net.my4x.bots.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import net.my4x.bots.AbstractIterBot;
import net.my4x.bots.Bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class FileScannerBot extends AbstractIterBot<File, File> {

	private static final Logger LOG = LoggerFactory.getLogger(FileScannerBot.class.getName());

	private boolean recursive = false;
	private List<String> accepted = Lists.newArrayList();

	public FileScannerBot recursive(final boolean recursive) {
		this.recursive = recursive;
		return this;
	}

	public FileScannerBot acceptFiles(final String... strings) {
		this.accepted = Arrays.asList(strings);
		return this;
	}

	@Override
	public FileScannerBot withInput(final File input) {
		return (FileScannerBot) super.withInput(input);
	}

	@Override
	public FileScannerBot giveEachItemTo(final Bot<File, ?> next) {
		return (FileScannerBot) super.giveEachItemTo(next);
	}

	@Override
	protected List<File> work(final File src) {
		final List<File> res = Lists.newArrayList();
		if (src == null || !src.exists()) {
			return res;
		}
		if (src.isFile()) {
			if (accept(src)) {
				res.add(src);
			}
		} else {
			// dir
			final File[] listFiles = src.listFiles();
			for (final File file : listFiles) {
				if (file.isFile() && accept(file)) {
					res.add(file);
				}
				if (recursive && file.isDirectory()) {
					res.addAll(work(file));
				}
			}
		}
		// LOG.error("list= {}", res);
		return res;
	}

	protected boolean accept(final File file) {

		final String extention = file.getName().substring(file.getName().lastIndexOf('.') + 1);
		// LOG.error("input= {} ext={}", file.getName(), extention);
		return accepted.contains(extention);

	}
}
