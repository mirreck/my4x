package net.my4x.population.model;

import org.junit.Test;

public class FakerDwarfTest {

	@Test
	public void test() {
		FakerDwarf faker = new FakerDwarf();
		faker.firstName(com.github.mirreck.domain.Gender.M);
	}

}
