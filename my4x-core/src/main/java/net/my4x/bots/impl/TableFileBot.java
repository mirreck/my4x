package net.my4x.bots.impl;

import java.io.File;

import net.my4x.bots.AbstractBot;
import net.my4x.bots.Bot;
import net.my4x.utils.file.CsvIterator;
import net.my4x.utils.file.FileType;
import net.my4x.utils.file.XlsIterator;

public class TableFileBot extends AbstractBot<File, TableData> {

	@Override
	public TableFileBot giveTo(final Bot<TableData, ?> next) {
		return (TableFileBot) super.giveTo(next);
	}

	@Override
	protected TableData work(final File src) {

		final FileType type = FileType.fromFile(src);
		switch (type) {
		case XLS:
		case XLSX:
			return new TableData(tableName(src.getName()), new XlsIterator(src).list());
		case CSV:
			return new TableData(tableName(src.getName()), new CsvIterator(src).list());
		default:
			throw new IllegalArgumentException("Not a table type");
		}
	}

	private String tableName(final String name) {
		return name.substring(0, name.lastIndexOf('.'));
	}
}
