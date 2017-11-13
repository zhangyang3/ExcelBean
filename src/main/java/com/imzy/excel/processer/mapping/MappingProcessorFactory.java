package com.imzy.excel.processer.mapping;

import java.util.HashMap;
import java.util.Map;

import com.imzy.excel.processer.MappingProcessor;
import com.imzy.excel.util.BeanUtils;

/**
 * 映射器工厂
 * @author yangzhang7
 *
 */
public class MappingProcessorFactory {

	private static Map<Class<?>, MappingProcessor> mappingProcessorMap = new HashMap<Class<?>, MappingProcessor>();

	/**
	 * 构建映射器
	 * @param mappingProcessorClass 映射处理器class类型
	 * @return
	 */
	public static MappingProcessor buildMappingProcessor(Class<? extends MappingProcessor> mappingProcessorClass) {
		MappingProcessor mappingProcessor = null;

		if (null != mappingProcessorClass) {
			mappingProcessor = mappingProcessorMap.get(mappingProcessorClass);
			if (null == mappingProcessor) {
				mappingProcessor = BeanUtils.getBean(mappingProcessorClass);
				mappingProcessorMap.put(mappingProcessorClass, mappingProcessor);
			}

		}

		return mappingProcessor;
	}
}
