package net.my4x.bots;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugBot extends AbstractBot<File, Object> implements Bot<File, Object> {

	private static final Logger LOG = LoggerFactory.getLogger(DebugBot.class.getName());

	@Override
	public DebugBot withInput(final File input) {
		return (DebugBot) super.withInput(input);
	}

	@Override
	protected Object work(final File src) {
		LOG.error("input= {}", this.input);
		return null;
	}

}
