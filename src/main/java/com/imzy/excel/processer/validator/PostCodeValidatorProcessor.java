package com.imzy.excel.processer.validator;

import com.imzy.excel.annotations.ProcessorDescription;

/**
 * 邮编校验器
 * @author yangzhang7
 *
 */
@ProcessorDescription(description = "邮编校验器")
public class PostCodeValidatorProcessor extends RegularValidateProcessor {

	@Override
	public boolean validate(String value, String[] validatorParam) {
		return super.validate(value, new String[] { "^[1-9][0-9]{5}$" });
	}
}
