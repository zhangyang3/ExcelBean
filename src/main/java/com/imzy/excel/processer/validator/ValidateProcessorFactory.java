package com.imzy.excel.processer.validator;

import java.util.HashMap;
import java.util.Map;

import com.imzy.excel.processer.ValidateProcessor;
import com.imzy.excel.util.BeanUtils;

/**
 * 校验器工厂
 * @author yangzhang7
 *
 */
public class ValidateProcessorFactory {

	private static Map<Class<?>, ValidateProcessor> validateProcessorMap = new HashMap<Class<?>, ValidateProcessor>();

	/**
	 * 构建校验器
	 * @param validatorClass 校验器类型
	 * @return
	 */
	public static ValidateProcessor getValidatorProcessor(Class<? extends ValidateProcessor> validatorClass) {
		ValidateProcessor validateProcessor = null;

		if (null != validatorClass) {
			validateProcessor = validateProcessorMap.get(validatorClass);
			if (null == validateProcessor) {
				validateProcessor = BeanUtils.getBean(validatorClass);
				validateProcessorMap.put(validatorClass, validateProcessor);
			}
		}
		return validateProcessor;
	}
}
