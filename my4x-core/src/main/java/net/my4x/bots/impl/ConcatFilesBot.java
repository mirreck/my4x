package net.my4x.bots.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.my4x.bots.AbstractBot;
import net.my4x.bots.Bot;
import net.my4x.tasks.GenerateSqlTask;

import org.apache.commons.io.IOUtils;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ConcatFilesBot extends AbstractBot<List<File>, File> implements Bot<List<File>, File> {

	private static final Logger LOG = LoggerFactory.getLogger(GenerateSqlTask.class.getName());

	private final File targetFile;

	private List<String> processOrder = Lists.newArrayList();

	public ConcatFilesBot(final File target) {
		this.targetFile = target;
	}

	@Override
	protected File work(final List<File> src) {

		final Map<String, File> fileMap = fileListToMap(src);

		FileOutputStream fos = null;
		try {
			targetFile.getParentFile().mkdirs();
			targetFile.createNewFile();
			fos = new FileOutputStream(targetFile);
			// in dependency order
			for (final String name : this.processOrder) {
				final String key = name + ".sql";
				final File file = fileMap.get(key);
				if (file != null) {
					LOG.info("append in order : {}", key);
					appendFile(fos, file);
					fileMap.remove(key);
				}
			}
			// remaining files
			for (final File file : fileMap.values()) {
				appendFile(fos, file);
			}
			fos.flush();
			fos.close();

		} catch (final Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(fos);
		}

		return targetFile;
	}

	private void appendFile(final FileOutputStream fos, final File file) throws FileNotFoundException, IOException {
		final FileInputStream fis = new FileInputStream(file);
		IOUtils.copy(fis, fos);
		fis.close();
		IOUtils.closeQuietly(fis);
	}

	private Map<String, File> fileListToMap(final List<File> src) {
		final Map<String, File> res = new HashMap<String, File>();
		for (final File file : src) {
			res.put(file.getName(), file);
		}
		return res;
	}

	public Bot<List<File>, ?> withDependencies(final File file) {
		try {
			final Object yaml = Yaml.load(file);
			if (!(yaml instanceof Map<?, ?>)) {
				LOG.info("dependencies not well formed", yaml.getClass());
			}
			final Map<String, List<String>> conf = (Map<String, List<String>>) yaml;
			processOrder = topologicalSort(conf);
			LOG.info("dependencies (sorted) = {}", processOrder);

		} catch (final FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	private List<String> topologicalSort(final Map<String, List<String>> dependencies) {
		final Set<String> itemset = Sets.newHashSet();
		for (final String key : dependencies.keySet()) {
			final List<String> deps = dependencies.get(key);
			itemset.addAll(deps);
			itemset.add(key);
		}
		final List<String> result = Lists.newArrayList();
		final List<String> visited = Lists.newArrayList();
		for (final String item : itemset) {
			recursiveAdd(item, result, dependencies, visited);
		}

		return result;
	}

	private void recursiveAdd(final String item, final List<String> result,
			final Map<String, List<String>> dependencies, final List<String> visited) {
		if (result.indexOf(item) >= 0) {
			return;
		}
		if (visited.indexOf(item) >= 0) {
			return;
		}
		visited.add(item); // avoid circular dependencies
		final List<String> list = dependencies.get(item);
		if (list != null) {
			for (final String child : list) {
				recursiveAdd(child, result, dependencies, visited);
			}
		}
		result.add(item);
	}
}
