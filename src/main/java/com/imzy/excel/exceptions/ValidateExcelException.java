package com.imzy.excel.exceptions;

/**
 * 验证异常
 * @author yangzhang7
 *
 */
public class ValidateExcelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1840934084229497072L;

	public ValidateExcelException() {
		super();
	}

	public ValidateExcelException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ValidateExcelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidateExcelException(String message) {
		super(message);
	}

	public ValidateExcelException(Throwable cause) {
		super(cause);
	}

}
