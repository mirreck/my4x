package net.my4x.talk.model;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.List;

import net.my4x.talk.model.stat.Occurence;
import net.my4x.talk.model.stat.OccurenceMap;

import org.apache.commons.io.IOUtils;

public class Language {

	private final OccurenceMap<Character> charOccurence = new OccurenceMap<>();
	private final OccurenceMap<Integer> wordSizeOccurence = new OccurenceMap<>();

	private int averageSize = 1;

	public void init(final InputStream sampleText) throws IOException {
		final String text = IOUtils.toString(sampleText).replaceAll("\\s", " ");

		countLetters(Normalizer.normalize(text, Normalizer.Form.NFC).toLowerCase());
		// final boolean b = Pattern.matches("\\p{L}*", text);
		countWordSize(text);
	}

	private void countWordSize(final String text) {
		final String[] words = text.split("\\s");
		int sum = 0;
		for (final String word : words) {
			wordSizeOccurence.addOccurence(word.length());
			sum += word.length();
		}
		averageSize = sum / words.length;

	}

	private void countLetters(final String text) {
		for (int i = 0; i < text.length(); i++) {
			final char c = text.charAt(i);
			charOccurence.addOccurence(c);
		}
	}

	public List<Occurence<Character>> getCharOccurence() {
		return charOccurence.sortedList();
	}

	public List<Occurence<Integer>> getWordOccurence() {
		return wordSizeOccurence.sortedList();
	}
}
