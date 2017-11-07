package com.iflytek.sgy.excel.validator;

import com.iflytek.sgy.excel.exceptions.ExcelException;

/**
 * 校验器工厂
 * @author yangzhang7
 *
 */
public class ValidatableFactory {

	/**
	 * 构建校验器
	 * @param validatorClass 校验器类型
	 * @return
	 */
	public static Validatable buildValidator(Class<? extends Validatable> validatorClass) {
		Validatable validator = null;

		try {
			validator = validatorClass.newInstance();
		} catch (InstantiationException e) {
			throw new ExcelException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new ExcelException(e.getMessage(), e);
		}

		return validator;
	}
}
