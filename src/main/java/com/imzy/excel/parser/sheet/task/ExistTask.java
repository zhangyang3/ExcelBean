package com.imzy.excel.parser.sheet.task;

import java.util.List;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.parser.sheet.ExcelPoint;
import com.imzy.excel.processer.ExitProcessor;

/**
 * 退出任务
 * @author yangzhang7
 *
 */
public interface ExistTask {

	/**
	 * 做退出
	 * @param cellConfigBeanList
	 * @param cellConfigBean
	 * @param existProcessorClass 退出处理器
	 * @param point 当前单元格坐标
	 * @param value 当前单元格值
	 * @param regionValue 区域值
	 * @return 
	 */
	boolean doExist(List<CellConfigBean> cellConfigBeanList, CellConfigBean cellConfigBean,
			Class<? extends ExitProcessor> existProcessorClass, ExcelPoint point, String value, String[][] regionValue);

}
