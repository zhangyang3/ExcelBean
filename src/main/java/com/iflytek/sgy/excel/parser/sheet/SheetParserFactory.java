package com.iflytek.sgy.excel.parser.sheet;

import com.iflytek.sgy.excel.enums.SheetType;

/**
 * sheet解析器工厂
 * @author yangzhang7
 *
 */
public class SheetParserFactory {

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
