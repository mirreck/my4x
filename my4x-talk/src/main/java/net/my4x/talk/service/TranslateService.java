package net.my4x.talk.service;

import java.util.Random;

import net.my4x.talk.model.Language;

public class TranslateService {

	private static Long seed_salt = 19997630275L;

	public String translate(final Language fromLang, final Language toLang, final String fromText) {
		final StringBuilder sb = new StringBuilder();
		int wordStart = 0, wordEnd = 0;
		for (int i = 0; i < fromText.length(); i++) {
			if (isPunctuation(fromText.charAt(i))) {
				sb.append(translateWord(fromLang, toLang, fromText.substring(wordStart, wordEnd)));
				sb.append(fromText.charAt(i));
				wordStart = i + 1;
				wordEnd = i + 1;
			} else {
				wordEnd++;
			}
		}

		return sb.toString();
	}

	private final String chars = "abcdefghijklmnopqrstuv";

	private Object translateWord(final Language fromLang, final Language toLang, final String word) {
		final Random rnd = new Random(seed_salt * word.hashCode());

		// the word will be streched to the adequate size :
		final int newSize = 10;
		final String modifiedWord = strech(word, newSize);
		// each letter will be based on previous and following letter...

		// then each letter is replaced by a letter from the target language
		return chars.substring(0, word.length());
	}

	private String strech(final String word, final int newSize) {
		if (word.length() == newSize) {
			return word;
		} else {

		}
		return null;
	}

	private String cutWord(final String word, final int pos) {
		return word.substring(Math.max(pos - 1, 0), Math.min(pos + 1, word.length()));
	}

	private boolean isPunctuation(final char charAt) {
		return " 	.,:;!'".indexOf("" + charAt) >= 0;
	}
}
