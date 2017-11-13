package com.imzy.excel.processer.mapping;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 抽象下拉框映射处理器
 * @author yangzhang7
 *
 */
public abstract class AbstractDropdownMappingProcessor extends SingleStringMappingProcessor {

	/**
	 * 设置映射字典值<br>
	 * @return
	 */
	protected abstract Map<String, String> initMapping();

	@Override
	public String mappingValue(String[][] regionValue) {
		String mappingValue = super.mappingValue(regionValue);

		Map<String, String> initMapping = initMapping();

		if (initMapping.containsKey(mappingValue)) {
			return initMapping.get(mappingValue);
		} else {
			return StringUtils.EMPTY;
		}
	}
}
