package com.imzy.excel.processer.validator;

import com.imzy.excel.annotations.ProcessorDescription;

/**
 * 电话校验器
 * @author yangzhang7
 *
 */
@ProcessorDescription(description = "电话校验器")
public class PhoneValidatorProcessor extends RegularValidateProcessor {

	@Override
	public boolean validate(String value, String[] validatorParam) {
		return super.validate(value, new String[] {
				"(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$" });
	}
}
