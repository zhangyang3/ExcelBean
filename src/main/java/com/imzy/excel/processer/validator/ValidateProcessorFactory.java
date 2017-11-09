package com.imzy.excel.processer.validator;

import com.imzy.excel.processer.ValidateProcessor;
import com.imzy.excel.util.BeanUtils;

/**
 * 校验器工厂
 * @author yangzhang7
 *
 */
public class ValidateProcessorFactory {

	/**
	 * 构建校验器
	 * @param validatorClass 校验器类型
	 * @return
	 */
	public static ValidateProcessor buildValidator(Class<? extends ValidateProcessor> validatorClass) {
		ValidateProcessor validator = null;

		if (null != validatorClass) {
			validator = BeanUtils.getBean(validatorClass);
		}
		return validator;
	}
}
