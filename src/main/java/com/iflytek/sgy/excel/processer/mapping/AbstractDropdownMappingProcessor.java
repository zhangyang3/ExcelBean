package com.iflytek.sgy.excel.processer.mapping;

import java.util.Map;

/**
 * 抽象下拉框映射处理器
 * @author yangzhang7
 *
 */
public abstract class AbstractDropdownMappingProcessor extends SingleStringMappingProcessor {

	/**
	 * 设置映射字典值<br>
	 * key为A-1，value为字典值
	 * @return
	 */
	protected abstract Map<String, String> initMapping();

	@Override
	public String mappingValue(String[][] regionValue) {
		String mappingValue = super.mappingValue(regionValue);

		Map<String, String> initMapping = initMapping();

		return initMapping.get(mappingValue);
	}
}
