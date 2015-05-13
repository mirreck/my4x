package net.my4x.card.service;

import java.io.InputStream;

import net.my4x.card.CardConfig;
import net.my4x.card.TestConfig;
import net.my4x.card.model.Pack;
import net.my4x.card.model.PackType;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yaml.snakeyaml.Yaml;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CardConfig.class)
public class CardServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(CardServiceImplTest.class.getName());
	@Autowired
	CardService service;

	@Test
	public void test() {
		final Pack pack = service.generatePack(PackType.EXPLORER);
		Assertions.assertThat(pack).isNotNull();
		Assertions.assertThat(pack.getCards()).hasSize(3);

	}

	@Test
	public void testYaml() {
		final Yaml yaml = new Yaml();
		final InputStream stream = CardServiceImplTest.class.getClassLoader().getResourceAsStream("config.yaml");
		final Object obj = yaml.load(stream);
		LOG.debug(obj.toString());
	}
}
