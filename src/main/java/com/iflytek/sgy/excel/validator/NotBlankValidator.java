package com.iflytek.sgy.excel.validator;

import org.apache.commons.lang3.StringUtils;

/**
 * 非空校验器
 * @author yangzhang7
 *
 */
public class NotBlankValidator implements Validatable {

	@Override
	public boolean validate(String value, String validatorParam) {
		return StringUtils.isNotBlank(value);
	}

}
