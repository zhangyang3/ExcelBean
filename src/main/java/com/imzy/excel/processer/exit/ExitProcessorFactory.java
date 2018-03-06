package com.imzy.excel.processer.exit;

import java.util.HashMap;
import java.util.Map;

import com.imzy.excel.processer.ExitProcessor;
import com.imzy.excel.util.BeanUtils;

/**
 * 退出处理器工厂
 * @author yangzhang7
 *
 */
public class ExitProcessorFactory {

	private static Map<Class<?>, ExitProcessor> existProcessorMap = new HashMap<Class<?>, ExitProcessor>();

	/**
	 * 获取退出处理器
	 * @param existProcessorClass
	 * @return
	 */
	public static ExitProcessor getExistProcessor(Class<? extends ExitProcessor> existProcessorClass) {
		ExitProcessor existProcessor = existProcessorMap.get(existProcessorClass);
		if (null == existProcessor) {
			existProcessor = BeanUtils.getBean(existProcessorClass);
			existProcessorMap.put(existProcessorClass, existProcessor);
		}

		return existProcessor;
	}
}
