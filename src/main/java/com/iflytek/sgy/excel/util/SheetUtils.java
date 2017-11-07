package com.iflytek.sgy.excel.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.iflytek.sgy.excel.support.FlyExcelConst;

/**
 * sheet工具类
 * @author yangzhang7
 *
 */
public class SheetUtils {

	/**
	 * 获取cell值
	 * @param x
	 * @param y
	 * @return
	 */
	public static String getCellValue(Sheet sheet, String x, String y) {

		Row row = sheet.getRow(Integer.valueOf(y) - 1);
		Cell cell = row.getCell(getCellNum(x));

		return getCellValue(cell);
	}

	/**
	 * 获取列数值
	 * @param x
	 * @return
	 */
	public static int getCellNum(String x) {
		char[] split = x.toCharArray();
		if (split.length == 1) {
			char c1 = Character.toLowerCase(split[0]);
			return c1 - 'a';
		} else if (split.length == FlyExcelConst.COLUMN_LENGTH) {
			char c1 = Character.toLowerCase(split[0]);
			char c2 = Character.toLowerCase(split[1]);
			return (c1 - 'a') * 26 + (c2 - 'a');
		}

		return 0;
	}

	/**
	 * 获取cell的值
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String cellValue = StringUtils.EMPTY;
		int cellType = cell.getCellType();
		switch (cellType) {
		case Cell.CELL_TYPE_NUMERIC:
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		default:
		}
		return cellValue;
	}
}
