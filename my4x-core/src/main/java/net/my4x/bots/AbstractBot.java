package net.my4x.bots;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractBot<T, U> implements Bot<T, U> {

	protected T input;

	private final List<Bot<U, ?>> ensembleBots = Lists.newArrayList();

	@Override
	public Bot<T, U> withInput(final T input) {
		this.input = input;
		return this;
	}

	protected abstract U work(final T src);

	@Override
	public Bot<T, U> giveTo(final Bot<U, ?> next) {
		ensembleBots.add(next);
		return this;
	}

	@Override
	public U go() {
		final U result = this.work(input);
		for (final Bot<U, ?> bot : ensembleBots) {
			bot.withInput(result);
			bot.go();
		}
		return result;

	}

}
