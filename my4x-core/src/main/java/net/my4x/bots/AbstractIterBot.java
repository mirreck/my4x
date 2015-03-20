package net.my4x.bots;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractIterBot<T, U> extends AbstractBot<T, List<U>> implements ListBot<T, U> {

	private final List<Bot<U, ?>> unitBots = Lists.newArrayList();

	@Override
	public Bot<T, List<U>> giveEachItemTo(final Bot<U, ?> next) {
		unitBots.add(next);
		return this;
	}

	@Override
	public List<U> go() {
		final List<U> result = super.go();
		for (final U item : result) {
			for (final Bot<U, ?> bot : unitBots) {
				bot.withInput(item);
				bot.go();
			}
		}
		return result;
	}
}
