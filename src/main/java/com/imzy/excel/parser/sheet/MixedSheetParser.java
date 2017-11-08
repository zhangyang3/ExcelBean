package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;

/**
 * 混合sheet解析器
 * @author yangzhang7
 *
 */
public class MixedSheetParser extends VerticalSheetParser {

	@Override
	public <T> T parse(Field field, Class<T> clazz) {
		// T parse = super.parse(field, clazz);

		return null;
	}

}
