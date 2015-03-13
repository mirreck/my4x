package net.my4x.talk.model;

import java.io.IOException;

import org.junit.Test;

public class LanguageTest {

	@Test
	public void test() throws IOException {
		final Language l = new Language();
		l.init(this.getClass().getResourceAsStream("sample.txt"));

		System.out.println("" + l.getCharOccurence());

		System.out.println("" + l.getWordOccurence());
	}

}
