package com.imzy.excel.processer;

/**
 * 值映射处理器
 * @author yangzhang7
 *
 */
public interface MappingProcessor {

	/**
	 * 映射值
	 * @param regionValue 区域值
	 * @return
	 */
	String mappingValue(String[][] regionValue);
}
