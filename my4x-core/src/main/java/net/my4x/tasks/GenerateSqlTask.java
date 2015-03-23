package net.my4x.tasks;

import java.io.File;

import net.my4x.bots.impl.FileScannerBot;
import net.my4x.bots.impl.SqlScriptBot;
import net.my4x.bots.impl.TableFileBot;

public class GenerateSqlTask {

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
	}
}
