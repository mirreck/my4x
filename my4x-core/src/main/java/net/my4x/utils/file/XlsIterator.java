package net.my4x.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Lists;

public class XlsIterator {
	private final File source;

	public enum XlsType {
		XLS, XLSX
	}

	public XlsIterator(final File source) {
		super();
		this.source = source;
	}

	public List<String[]> list() {

		final List<String[]> result = Lists.newArrayList();
		FileInputStream is = null;
		try {
			is = new FileInputStream(source);
			Workbook workbook = null;
			switch (type(source)) {
			case XLS:
				workbook = new HSSFWorkbook(is);
				break;
			default:
				workbook = new XSSFWorkbook(is);
				break;
			}
			final Sheet sheet = workbook.getSheetAt(0);
			final Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				final Row row = rowIterator.next();
				result.add(mapRow(row));

			}

		} catch (final IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return result;
	}

	private String[] mapRow(final Row row) {

		final String[] res = new String[row.getRowNum()];
		for (int i = 0; i < res.length; i++) {
			res[i] = trimCell(row, i);
		}

		return res;
	}

	private String trimCell(final Row row, final int index) {
		final Cell cell = row.getCell(index);
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return "";
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue().trim();
		case Cell.CELL_TYPE_NUMERIC:
			final double d = cell.getNumericCellValue();
			// test if a date!
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// format in form of M/D/YY
				final Date date = HSSFDateUtil.getJavaDate(d);
				return date.toString();
			}

			return Double.toString(cell.getNumericCellValue());
		default:
			return "";
		}

	}

	private XlsType type(final File file) {
		if (file.getName().endsWith(".xlsx")) {
			return XlsType.XLSX;
		}
		return XlsType.XLS;
	}
}
