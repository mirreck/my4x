package net.my4x.card.init;

import net.my4x.tasks.GenerateSqlTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateInitSql {

	private static final Logger LOG = LoggerFactory.getLogger(GenerateInitSql.class.getName());

	public static void main(final String[] args) {
		LOG.error("<--> GenerateInitSql");
		if (args == null || args.length != 2) {
			LOG.error("usage GenerateInitSql [src] [dst]");
			System.exit(1);
		}
		LOG.error("<--> source : {}", args[0]);
		LOG.error("<--> destination : {}", args[1]);
		run(args[0], args[1]);
	}

	public static void run(final String src, final String dst) {
		new GenerateSqlTask(src, dst).generate();
	}
}
