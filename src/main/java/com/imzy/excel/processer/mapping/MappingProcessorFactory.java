package com.imzy.excel.processer.mapping;

import com.imzy.excel.processer.MappingProcessor;
import com.imzy.excel.util.BeanUtils;

/**
 * 映射器工厂
 * @author yangzhang7
 *
 */
public class MappingProcessorFactory {

	/**
	 * 构建映射器
	 * @param mappingProcessorClass 映射处理器class类型
	 * @return
	 */
	public static MappingProcessor buildMappingProcessor(Class<? extends MappingProcessor> mappingProcessorClass) {
		MappingProcessor mappingProcessor = null;

		if (null != mappingProcessorClass) {
			mappingProcessor = BeanUtils.getBean(mappingProcessorClass);
		}

		return mappingProcessor;
	}
}
