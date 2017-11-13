package com.imzy.excel.parser.sheet;

import java.util.HashMap;
import java.util.Map;

import com.imzy.excel.enums.SheetType;
import com.imzy.excel.util.BeanUtils;

/**
 * sheet解析器工厂
 * @author yangzhang7
 *
 */
public class SheetParserFactory {

	private static Map<Class<?>, SheetParser> sheetParserMap = new HashMap<Class<?>, SheetParser>();

	/**
	 * 获取sheet解析器
	 * @param sheetType sheet类型
	 * @return
	 */
	public static SheetParser getSheetParser(SheetType sheetType) {
		Class<? extends SheetParser> sheetParserClazz = null;
		if (SheetType.BASIC.equals(sheetType)) {
			sheetParserClazz = BasicSheetParser.class;
		} else if (SheetType.HORIZONTAL.equals(sheetType)) {
			sheetParserClazz = HorizontalSheetParser.class;
		} else if (SheetType.VERTICAL.equals(sheetType)) {
			sheetParserClazz = VerticalSheetParser.class;
		} else if (SheetType.MIXED.equals(sheetType)) {
			sheetParserClazz = MixedSheetParser.class;
		}

		return getSheetParser(sheetParserClazz);
	}

	/**
	 * 获取sheet解析器
	 * @param clazz sheet的class类型
	 * @return
	 */
	public static SheetParser getSheetParser(Class<? extends SheetParser> clazz) {
		SheetParser sheetParser = sheetParserMap.get(clazz);
		if (null == sheetParser) {
			sheetParser = BeanUtils.getBean(clazz);
			sheetParserMap.put(clazz, sheetParser);
		}

		return sheetParser;
	}
}
