package com.imzy.excel.processer.validator;

import com.imzy.excel.util.StringUtils;

/**
 * 长度校验器
 * @author yangzhang7
 *
 */
public class LengthValidateProcessor extends RegularValidateProcessor {

	@Override
	public boolean validate(String value, String validatorParam) {
		String[] split = validatorParam.split("-");
		String regex = StringUtils.EMPTY;

		if (validatorParam.contains("-")) {
			if (split.length == 1) {
				regex = "^.{" + split[0] + ",}$";
			} else {
				regex = "^.{" + split[0] + "," + split[1] + "}$";
			}
		} else {
			regex = "^.{" + split[0] + "}$";
		}

		return super.validate(value, regex);
	}

}
