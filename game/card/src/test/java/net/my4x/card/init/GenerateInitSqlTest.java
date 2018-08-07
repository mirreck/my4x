package net.my4x.card.init;

import org.junit.Ignore;
import org.junit.Test;

public class GenerateInitSqlTest {

	@Test
	@Ignore("Deprecated with java 8...")
	public void test() {
		GenerateInitSql.run("d:/PROJETS/PERSO/my4x/my4x-card/src/main/resources/init",
				"d:/PROJETS/PERSO/my4x/my4x-card/src/generated/resources/init");
	}

}