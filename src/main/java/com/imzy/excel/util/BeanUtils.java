package com.imzy.excel.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BeanUtils {

	/**
	 * 构建bean
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		T newInstance = null;
		try {
			newInstance = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return newInstance;
	}

	/**
	 * 设置值
	 * @param object 待设置值的对象
	 * @param field 待设置值的字段
	 * @param value 值
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValue(Object object, Field field, Object value)
			throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		field.set(object, value);
	}

	/**
	 * 获取泛型
	 * @param field 待解析的字段
	 * @return
	 */
	public static Type getGenericType(Field field) {
		return ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

	}
}
