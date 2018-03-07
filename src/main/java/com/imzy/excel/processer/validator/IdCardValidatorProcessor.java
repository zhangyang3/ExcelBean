package com.imzy.excel.processer.validator;

/**
 * 身份证校验器
 * @author yangzhang7
 *
 */
public class IdCardValidatorProcessor extends RegularValidateProcessor {

	@Override
	public boolean validate(String value, String[] validatorParam) {
		return super.validate(value, new String[] {
				"^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$" });
	}
}
