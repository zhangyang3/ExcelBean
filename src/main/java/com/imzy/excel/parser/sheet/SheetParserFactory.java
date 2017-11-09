package com.imzy.excel.parser.sheet;

import com.imzy.excel.enums.SheetType;

/**
 * sheet解析器工厂
 * @author yangzhang7
 *
 */
public class SheetParserFactory {

	/**
	 * 构建sheet解析器
	 * @param sheetType sheet类型
	 * @return
	 */
	public static SheetParser buildSheetParser(SheetType sheetType) {
		SheetParser sheetParser = null;
		if (SheetType.HORIZONTAL.equals(sheetType)) {
			sheetParser = new HorizontalSheetParser();
		} else if (SheetType.VERTICAL.equals(sheetType)) {
			sheetParser = new VerticalSheetParser();
		} else if (SheetType.MIXED.equals(sheetType)) {
			sheetParser = new MixedSheetParser();
		}

		return sheetParser;
	}
}
