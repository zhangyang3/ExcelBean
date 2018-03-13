package com.imzy.excel.processer.validator;

import com.imzy.excel.annotations.ProcessorDescription;
import com.imzy.excel.util.StringUtils;

/**
 * 长度校验器
 * @author yangzhang7
 *
 */
@ProcessorDescription(description = "长度校验器")
public class LengthValidateProcessor extends RegularValidateProcessor {

	@Override
	public boolean validate(String value, String[] validatorParam) {
		String regex = StringUtils.EMPTY;

		if (validatorParam.length == 1) {
			regex = "^.{" + validatorParam[0] + "}$";
		} else {
			if (StringUtils.isBlank(validatorParam[1])) {
				regex = "^.{" + validatorParam[0] + ",}$";
			} else {
				regex = "^.{" + validatorParam[0] + "," + validatorParam[1] + "}$";
			}
		}

		return super.validate(value, new String[] { regex });
	}

}
