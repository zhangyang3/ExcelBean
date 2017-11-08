package com.imzy.excel.util;

public class BeanUtils {

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
}
