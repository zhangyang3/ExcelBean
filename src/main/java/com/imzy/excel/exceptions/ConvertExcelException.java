package com.imzy.excel.exceptions;

/**
 * 转换异常
 * @author yangzhang7
 *
 */
public class ConvertExcelException extends ExcelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1840934084229497072L;

	public ConvertExcelException() {
		super();
	}

	public ConvertExcelException(String message, ExcelException cause) {
		super(message, cause);
	}

	public ConvertExcelException(String message) {
		super(message);
	}

	public ConvertExcelException(Throwable cause) {
		super(cause);
	}

}
