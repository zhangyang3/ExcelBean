package com.imzy.excel.error;

import com.imzy.excel.enums.ErrorType;

import lombok.Data;

/**
 * 错误Bean
 * @author yangzhang7
 *
 */
@Data
public class ErrorBean {

	/** Excel名称*/
	private String excelName;
	/** Sheet名称*/
	private String sheetName;
	/** 行号*/
	private String lineNo;
	/** 列号*/
	private String columnNo;
	/** 错误类型*/
	private ErrorType errorType;
	/** 错误原因*/
	private String errorReason;
	/** 错误值*/
	private String value;
	/** 校验错误原因*/
	private String validateErrorReason;
}
