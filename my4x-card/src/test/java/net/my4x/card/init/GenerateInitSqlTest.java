package net.my4x.card.init;

import org.junit.Test;

public class GenerateInitSqlTest {

	@Test
	public void test() {
		GenerateInitSql.run("d:/PROJETS/PERSO/my4x/my4x-card/src/main/resources/init",
				"d:/PROJETS/PERSO/my4x/my4x-card/src/generated/resources/init");
	}

}