package com.imzy.excel.parser.sheet.task;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.exceptions.ValidateExcelException;
import com.imzy.excel.parser.sheet.ExcelPoint;

/**
 * 校验任务
 * @author yangzhang7
 *
 */
public interface ValidateTask {

	/**
	 * 做校验
	 * @param cellConfigBean 配置bean
	 * @param value 待校验的值
	 * @param point 当前值坐标
	 * @throws ValidateExcelException
	 */
	void doValidate(CellConfigBean cellConfigBean, String value, ExcelPoint point) throws ValidateExcelException;
}
