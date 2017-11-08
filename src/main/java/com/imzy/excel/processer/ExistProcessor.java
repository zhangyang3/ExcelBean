package com.imzy.excel.processer;

import com.imzy.excel.parser.sheet.Point;

/**
 * 退出处理器
 * @author yangzhang7
 *
 */
public interface ExistProcessor {

	boolean exist(Point point, String[][] regionValue, String value);

}
