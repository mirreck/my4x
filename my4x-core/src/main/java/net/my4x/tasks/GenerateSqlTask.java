package net.my4x.tasks;

import java.io.File;

import net.my4x.bots.impl.ConcatFilesBot;
import net.my4x.bots.impl.FileScannerBot;
import net.my4x.bots.impl.SqlScriptBot;
import net.my4x.bots.impl.TableFileBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateSqlTask {
	private static final Logger LOG = LoggerFactory.getLogger(GenerateSqlTask.class.getName());

	private final String srcDir;
	private final String targetDir;

	public GenerateSqlTask(final String srcDir, final String targetDir) {
		super();
		this.srcDir = srcDir;
		this.targetDir = targetDir;
	}

	public void generate() {
		new FileScannerBot() //
				.withInput(new File(srcDir)) //
				.recursive(true) //
				.acceptFiles("csv", "xls", "xlsx") //
				.giveEachItemTo( //
						new TableFileBot() //
								.giveTo(//
								new SqlScriptBot(new File(targetDir)))) //
				.go();
		new FileScannerBot() //
				.withInput(new File(targetDir)) //
				.recursive(true) //
				.acceptFiles("sql")//
				.giveTo(//
				new ConcatFilesBot(new File(targetDir + "/../final-sql.sql"))//
						.withDependencies(new File(srcDir + "/dependencies.yaml"))) //
				.go();
	}

}
