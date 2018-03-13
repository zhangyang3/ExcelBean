package com.imzy.excel.util;

import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.imzy.excel.support.ExcelBeanConst.Number;

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

		if (cell == null) {
			return StringUtils.EMPTY;
		} else {
			return getCellValue(cell);
		}
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
		} else if (split.length == Number.TWO_INT) {
			char c1 = Character.toLowerCase(split[0]);
			char c2 = Character.toLowerCase(split[1]);
			return (c1 - 'a' + 1) * 26 + (c2 - 'a');
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

		if (cell != null) {
			int cellType = cell.getCellType();
			switch (cellType) {
			case Cell.CELL_TYPE_NUMERIC:
				DecimalFormat df = new DecimalFormat("0");
				cellValue = df.format(cell.getNumericCellValue());
				// cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = StringUtils.EMPTY;
				break;
			default:
			}
		}
		return cellValue;
	}
}
