package com.imzy.excel.processer.validator;

import com.imzy.excel.annotations.ProcessorDescription;

/**
 * 邮箱校验器
 * @author yangzhang7
 *
 */
@ProcessorDescription(description = "邮箱校验器")
public class EmailValidatorProcessor extends RegularValidateProcessor {

	@Override
	public boolean validate(String value, String[] validatorParam) {
		return super.validate(value,
				new String[] { "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$" });
	}
}
