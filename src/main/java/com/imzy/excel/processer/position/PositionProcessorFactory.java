package com.imzy.excel.processer.position;

import java.util.HashMap;
import java.util.Map;

import com.imzy.excel.processer.PositionProcessor;
import com.imzy.excel.util.BeanUtils;

/**
 * 位置处理器工厂
 * @author yangzhang7
 *
 */
public class PositionProcessorFactory {

	private static Map<Class<?>, PositionProcessor> positionProcessorMap = new HashMap<Class<?>, PositionProcessor>();

	/**
	 * 获取位置处理器
	 * @param clazz
	 * @return
	 */
	public static PositionProcessor getPositionProcessor(Class<? extends PositionProcessor> positionProcessorClass) {
		PositionProcessor positionProcessor = positionProcessorMap.get(positionProcessorClass);
		if (null == positionProcessor) {
			positionProcessor = BeanUtils.getBean(positionProcessorClass);
			positionProcessorMap.put(positionProcessorClass, positionProcessor);
		}

		return positionProcessor;
	}
}
