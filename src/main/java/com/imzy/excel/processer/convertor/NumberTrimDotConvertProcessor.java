package com.imzy.excel.processer.convertor;

import com.imzy.excel.processer.ConvertProcessor;

/**
 * 小数去点
 * @author yangzhang7
 *
 */
public class NumberTrimDotConvertProcessor implements ConvertProcessor {

	@Override
	public String convert(String value, String[] convertorParam) {
		if (value.contains(".")) {
			return value.substring(0, value.indexOf("."));
		} else {
			return value;
		}
	}

}
