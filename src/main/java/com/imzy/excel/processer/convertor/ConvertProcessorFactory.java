package com.imzy.excel.processer.convertor;

import java.util.HashMap;
import java.util.Map;

import com.imzy.excel.processer.ConvertProcessor;
import com.imzy.excel.util.BeanUtils;

/**
 * 转换器工厂
 * @author yangzhang7
 *
 */
public class ConvertProcessorFactory {

	private static Map<Class<?>, ConvertProcessor> convertProcessorMap = new HashMap<Class<?>, ConvertProcessor>();

	/**
	 * 构建转换器
	 * @param validatorClass 转换器类型
	 * @return
	 */
	public static ConvertProcessor getConvertorProcessor(Class<? extends ConvertProcessor> convertorClass) {
		ConvertProcessor convertProcessor = null;

		if (null != convertorClass) {
			convertProcessor = convertProcessorMap.get(convertorClass);
			if (null == convertProcessor) {
				convertProcessor = BeanUtils.getBean(convertorClass);
				convertProcessorMap.put(convertorClass, convertProcessor);
			}
		}
		return convertProcessor;
	}
}
