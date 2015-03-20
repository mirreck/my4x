package net.my4x.bots;

import java.util.List;

public interface ListBot<T, U> extends Bot<T, List<U>> {
	public Bot<T, List<U>> giveEachItemTo(final Bot<U, ?> next);
}
