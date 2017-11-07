package com.imzy.excel.processer.mapping;

import org.apache.commons.lang3.StringUtils;

import com.imzy.excel.processer.MappingProcessor;

/**
 * 简单字符串映射器
 * @author yangzhang7
 *
 */
public class SingleStringMappingProcessor implements MappingProcessor {

	@Override
	public String mappingValue(String[][] regionValue) {
		for (String[] strings : regionValue) {
			for (String string : strings) {
				if (StringUtils.isNotBlank(string)) {
					return string;
				}
			}
		}

		return StringUtils.EMPTY;
	}

}
