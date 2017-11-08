package com.imzy.excel.exceptions;

/**
 * 注释配置异常
 * @author yangzhang7
 *
 */
public class XmlConfigExcelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1840934084229497072L;

	public XmlConfigExcelException() {
		super();
	}

	public XmlConfigExcelException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public XmlConfigExcelException(String message, Throwable cause) {
		super(message, cause);
	}

	public XmlConfigExcelException(String message) {
		super(message);
	}

	public XmlConfigExcelException(Throwable cause) {
		super(cause);
	}

}
