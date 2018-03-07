package com.imzy.excel.processer.validator;

/**
 * 邮编校验器
 * @author yangzhang7
 *
 */
public class PostCodeValidatorProcessor extends RegularValidateProcessor {

	@Override
	public boolean validate(String value, String[] validatorParam) {
		return super.validate(value, new String[] { "^[1-9][0-9]{5}$" });
	}
}
