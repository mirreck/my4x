package net.my4x.bots.impl;

import java.util.List;

public class TableData {

	private String name;
	private List<String[]> lines;

	public TableData(final String name, final List<String[]> lines) {
		super();
		this.name = name;
		this.lines = lines;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<String[]> getLines() {
		return lines;
	}

	public void setLines(final List<String[]> lines) {
		this.lines = lines;
	}
}
