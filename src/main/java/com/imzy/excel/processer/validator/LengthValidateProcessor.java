package com.imzy.excel.processer.validator;

import com.imzy.excel.annotations.ProcessorDescription;
import com.imzy.excel.processer.ValidateProcessor;
import com.imzy.excel.util.StringUtils;

/**
 * 长度校验器
 * @author yangzhang7
 *
 */
@ProcessorDescription(description = "长度校验器")
public class LengthValidateProcessor implements ValidateProcessor {

	@Override
	public boolean validate(String value, String[] validatorParam) {

		if (validatorParam.length == 1) {
			return value.length() == Integer.parseInt(validatorParam[0]);
		} else {
			if (StringUtils.isBlank(validatorParam[0]) && StringUtils.isNotBlank(validatorParam[1])) {
				return value.length() <= Integer.parseInt(validatorParam[1]);
			} else if (StringUtils.isBlank(validatorParam[1]) && StringUtils.isNotBlank(validatorParam[0])) {
				return value.length() >= Integer.parseInt(validatorParam[0]);
			} else {
				return value.length() >= Integer.parseInt(validatorParam[0])
						&& value.length() <= Integer.parseInt(validatorParam[1]);
			}
		}

	}

}
