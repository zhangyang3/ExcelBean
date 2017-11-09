package com.imzy.excel.parser.sheet.task;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.exceptions.ValidateExcelException;

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
	 * @throws ValidateExcelException
	 */
	void doValidate(CellConfigBean cellConfigBean, String value) throws ValidateExcelException;
}
