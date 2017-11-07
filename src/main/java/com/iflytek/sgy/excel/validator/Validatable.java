package com.iflytek.sgy.excel.validator;

/**
 * 校验器
 * @author yangzhang7
 *
 */
public interface Validatable {

	/**
	 * 校验
	 * @param value 待校验的值
	 * @param validatorParam 校验器参数
	 * @return
	 */
	boolean validate(String value, String validatorParam);
}
