package com.iflytek.sgy.excel.parser.sheet.task;

import com.iflytek.sgy.excel.configbean.CellConfigBean;
import com.iflytek.sgy.excel.exceptions.ValidateExcelException;

/**
 * 校验任务
 * @author yangzhang7
 *
 */
public interface ValidateTask {

	/**
	 * 做校验
	 * @param cellConfigBean
	 * @param value 待校验的值
	 * @throws ValidateExcelException
	 */
	void doValidate(CellConfigBean cellConfigBean, String value) throws ValidateExcelException;
}
