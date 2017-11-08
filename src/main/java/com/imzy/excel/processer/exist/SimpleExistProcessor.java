package com.imzy.excel.processer.exist;

import org.apache.commons.lang3.StringUtils;

import com.imzy.excel.parser.sheet.Point;
import com.imzy.excel.processer.ExistProcessor;

/**
 * 简单退出处理器
 * @author yangzhang7
 *
 */
public class SimpleExistProcessor implements ExistProcessor {

	@Override
	public boolean exist(Point point, String[][] regionValue, String value) {

		// 如果a列没有数据，则认为行扫描结束
		return Character.toLowerCase(point.getStartX()) == 'a' && StringUtils.isBlank(value);
	}

}
