package net.my4x.card.service;

import net.my4x.card.TestConfig;
import net.my4x.card.model.Pack;
import net.my4x.card.model.PackType;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CardServiceImplTest {

	@Autowired
	CardService service;

	@Test
	public void test() {
		final Pack pack = service.generatePack(PackType.EXPLORER);
		Assertions.assertThat(pack).isNotNull();
		Assertions.assertThat(pack.getCards()).hasSize(1);
	}

}
