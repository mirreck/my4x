package net.my4x.tasks;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class RuntimeCompilerTask {
	public static void main(final String[] args) {
		final JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
		final StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null);
	}
}
