package com.iflytek.sgy.excel.exceptions;

/**
 * 注释配置异常
 * @author yangzhang7
 *
 */
public class AnnotationConfigExcelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1840934084229497072L;

	public AnnotationConfigExcelException() {
		super();
	}

	public AnnotationConfigExcelException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AnnotationConfigExcelException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnnotationConfigExcelException(String message) {
		super(message);
	}

	public AnnotationConfigExcelException(Throwable cause) {
		super(cause);
	}

}
