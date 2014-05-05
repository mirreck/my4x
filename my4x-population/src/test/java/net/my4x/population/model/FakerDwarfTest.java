package net.my4x.population.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class FakerDwarfTest {

	@Test
	public void test() {
		FakerDwarf faker = new FakerDwarf();
		faker.firstName(com.github.javafaker.domain.Gender.M);
	}

}
