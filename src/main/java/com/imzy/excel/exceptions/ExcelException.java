package com.imzy.excel.exceptions;

/**
 * excel总异常
 * @author yangzhang7
 *
 */
public class ExcelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1840934084229497072L;

	public ExcelException() {
		super();
	}

	public ExcelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExcelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcelException(String message) {
		super(message);
	}

	public ExcelException(Throwable cause) {
		super(cause);
	}

}
