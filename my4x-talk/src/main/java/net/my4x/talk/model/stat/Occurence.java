package net.my4x.talk.model.stat;

public class Occurence<T> {
	int occurence = 0;
	T obj;

	public Occurence(final T obj) {
		this.occurence = 1;
		this.obj = obj;
	}

	public Occurence(final int occurence, final T obj) {
		this.occurence = occurence;
		this.obj = obj;
	}

	public void inc() {
		occurence++;
	}

	@Override
	public String toString() {
		return "[" + obj + "=>" + occurence + "]";
	}

}
