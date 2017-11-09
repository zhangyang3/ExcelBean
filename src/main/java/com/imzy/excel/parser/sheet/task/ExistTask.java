package com.imzy.excel.parser.sheet.task;

import com.imzy.excel.parser.sheet.Point;
import com.imzy.excel.processer.ExistProcessor;

/**
 * 退出任务
 * @author yangzhang7
 *
 */
public interface ExistTask {

	/**
	 * 做退出
	 * @param existProcessorClass 退出处理器
	 * @param point 当前单元格坐标
	 * @param value 当前单元格值
	 * @param regionValue 区域值
	 * @return 
	 */
	boolean doExist(Class<? extends ExistProcessor> existProcessorClass, Point point, String value,
			String[][] regionValue);

}
