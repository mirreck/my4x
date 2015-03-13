package net.my4x.talk.service;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class TranslateServiceTest {

	@Test
	public void test() {
		final TranslateService service = new TranslateService();
		final String translated = service.translate(null, null, "à l'approche de l'été, je me demande si. et aussi !");
		Assertions.assertThat(translated).isEqualTo("a a'abcdefgh ab a'abc, ab ab abcdefg ab. ab abcde !");
	}

}
