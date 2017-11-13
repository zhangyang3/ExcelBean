package com.imzy.excel.processer.exist;

import java.util.HashMap;
import java.util.Map;

import com.imzy.excel.processer.ExistProcessor;
import com.imzy.excel.util.BeanUtils;

/**
 * 退出处理器工厂
 * @author yangzhang7
 *
 */
public class ExistProcessorFactory {

	private static Map<Class<?>, ExistProcessor> existProcessorMap = new HashMap<Class<?>, ExistProcessor>();

	/**
	 * 获取退出处理器
	 * @param existProcessorClass
	 * @return
	 */
	public static ExistProcessor getExistProcessor(Class<? extends ExistProcessor> existProcessorClass) {
		ExistProcessor existProcessor = existProcessorMap.get(existProcessorClass);
		if (null == existProcessor) {
			existProcessor = BeanUtils.getBean(existProcessorClass);
			existProcessorMap.put(existProcessorClass, existProcessor);
		}

		return existProcessor;
	}
}
