package com.imzy.excel.parser.sheet.task;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.exceptions.ConvertExcelException;
import com.imzy.excel.parser.sheet.ExcelPoint;

/**
 * 转换任务
 * @author yangzhang7
 *
 */
public interface ConvertTask {

	/**
	 * 做转化
	 * @param cellConfigBean 配置bean
	 * @param value 待转化的值
	 * @param point 当前值坐标
	 * @return 转换后的值
	 * @throws ConvertExcelException
	 */
	String doConvert(CellConfigBean cellConfigBean, String value, ExcelPoint point) throws ConvertExcelException;
}
