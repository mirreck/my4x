package net.my4x.utils.file;

import java.io.File;

public enum FileType {
	XLS, XLSX, CSV;
	public static FileType fromFile(final File file) {
		final String extention = file.getName().substring(file.getName().lastIndexOf('.') + 1);
		for (final FileType fileType : FileType.values()) {
			if (fileType.name().equals(extention.toUpperCase())) {
				return fileType;
			}
		}
		throw new IllegalArgumentException("Type not found");
	}
}
