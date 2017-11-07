package com.iflytek.sgy.excel.parser.sheet;

import java.lang.reflect.Field;

/**
 * sheet解析器
 * @author yangzhang7
 *
 */
public interface SheetParser {

	/**
	 * 解析sheet，转化为type对应的对象
	 * @param field 字段
	 * @param clazz 转化后的类
	 * @return 转化后的对象
	 */
	<T> T parse(Field field, Class<T> clazz);

}
