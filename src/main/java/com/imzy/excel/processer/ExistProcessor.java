package com.imzy.excel.processer;

import java.util.List;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.parser.sheet.Point;

/**
 * 退出处理器
 * @author yangzhang7
 *
 */
public interface ExistProcessor {

	/**
	 * 退出
	 * @param cellConfigBean 
	 * @param cellConfigBeanList 
	 * @param point 当前单元格坐标
	 * @param regionValue 区域值
	 * @param value 当前单元格值
	 * @return
	 */
	boolean exist(List<CellConfigBean> cellConfigBeanList, CellConfigBean cellConfigBean, Point point,
			String[][] regionValue, String value);

}
