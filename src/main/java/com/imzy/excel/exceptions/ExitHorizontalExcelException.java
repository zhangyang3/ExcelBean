package com.imzy.excel.exceptions;

/**
 * 退出横表异常
 * @author yangzhang7
 *
 */
public class ExitHorizontalExcelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1840934084229497072L;

	public ExitHorizontalExcelException() {
		super();
	}

	public ExitHorizontalExcelException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExitHorizontalExcelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExitHorizontalExcelException(String message) {
		super(message);
	}

	public ExitHorizontalExcelException(Throwable cause) {
		super(cause);
	}

}
