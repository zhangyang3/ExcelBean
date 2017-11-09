package com.imzy.excel.processer;

/**
 * 校验器
 * @author yangzhang7
 *
 */
public interface ValidateProcessor {

	/**
	 * 校验
	 * @param value 待校验的值
	 * @param validatorParam 校验器参数
	 * @return false为不通过，true为通过
	 */
	boolean validate(String value, String validatorParam);
}
