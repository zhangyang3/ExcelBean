package com.imzy.excel.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * annotation工具类
 * @author yangzhang7
 *
 */
public class AnnotationUtil {

	/**
	 * 将annotation的值转换为map
	 * @param bean annotation对象
	 * @param clazz annotaiton的Class
	 * @return
	 */
	public static <T> Map<String, Object> annotaiton2Map(T bean, Class<T> clazz) {
		Map<String, Object> resultMap = new HashMap<String, Object>(16);

		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			field.setAccessible(true);

			try {
				Object value = field.get(bean);
				resultMap.put(field.getName(), value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return resultMap;
	}
}
