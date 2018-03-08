package com.imzy.excel.processer.mapping;

import java.util.Map;

import com.imzy.excel.util.StringUtils;

/**
 * 伪下拉框映射器
 * @author yangzhang7
 *
 */
public abstract class AbstractFakeDropdownMappingProcessor extends SingleStringMappingProcessor {
	/**
	 * 设置映射字典值<br>
	 * @return
	 */
	protected abstract Map<String, String> initMapping();

	@Override
	public String mappingValue(String[][] regionValue) {
		// 初始化下拉框值
		Map<String, String> initMapping = initMapping();
		// 分割值
		String mappingValue = super.mappingValue(regionValue);
		String[] mappingValueSplit = null;
		if (mappingValue.contains(",")) {
			mappingValueSplit = mappingValue.split(",");
		} else if (mappingValue.contains("、")) {
			mappingValueSplit = mappingValue.split("、");
		} else if (mappingValue.contains("，")) {
			mappingValueSplit = mappingValue.split("，");
		} else {
			mappingValueSplit = mappingValue.split(" ");
		}

		StringBuilder valueStringBuilder = new StringBuilder(StringUtils.EMPTY);
		boolean hasValue = false;
		for (int i = 0; i < mappingValueSplit.length; i++) {
			if (initMapping.containsKey(mappingValueSplit[i])) {
				if (hasValue) {
					valueStringBuilder.append(",");
				}
				valueStringBuilder.append(initMapping.get(mappingValueSplit[i]));
				hasValue = true;
			}
		}
		return valueStringBuilder.toString();
	}

}
