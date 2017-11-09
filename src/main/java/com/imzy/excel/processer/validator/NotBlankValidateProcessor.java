package com.imzy.excel.processer.validator;

import org.apache.commons.lang3.StringUtils;

import com.imzy.excel.processer.ValidateProcessor;

/**
 * 非空校验器
 * @author yangzhang7
 *
 */
public class NotBlankValidateProcessor implements ValidateProcessor {

	@Override
	public boolean validate(String value, String validatorParam) {
		return StringUtils.isNotBlank(value);
	}

}
