package com.imzy.excel.processer;

/**
 * 转化处理器
 * @author yangzhang7
 *
 */
public interface ConvertProcessor {

	/**
	 * 转换
	 * @param value 待转化的值
	 * @param convertorParam 校验器参数
	 * @return 转换后的值
	 */
	String convert(String value, String[] convertorParam);
}
