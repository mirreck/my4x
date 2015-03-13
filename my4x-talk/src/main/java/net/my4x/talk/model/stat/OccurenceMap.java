package net.my4x.talk.model.stat;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

public class OccurenceMap<T> extends HashMap<T, Occurence<T>> {

	private static final long serialVersionUID = 1L;

	public List<Occurence<T>> sortedList() {
		final List<Occurence<T>> list = Lists.newArrayList(this.values());
		Collections.sort(list, new Comparator<Occurence<?>>() {
			@Override
			public int compare(final Occurence<?> o1, final Occurence<?> o2) {
				return Integer.compare(o2.occurence, o1.occurence);
			}
		});
		return list;
	}

	public void addOccurence(final T value) {
		final Occurence<T> occurence = this.get(value);
		if (occurence == null) {
			this.put(value, new Occurence<T>(1, value));
		} else {
			occurence.inc();
		}
	}
}
