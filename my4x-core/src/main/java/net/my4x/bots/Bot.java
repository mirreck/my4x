package net.my4x.bots;

public interface Bot<T, U> {
	public Bot<T, U> withInput(final T obj);

	public Bot<T, U> giveTo(final Bot<U, ?> next);

	public U go();

}
