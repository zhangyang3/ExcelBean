package com.imzy.excel.processer.validator;

import com.imzy.excel.annotations.ProcessorDescription;
import com.imzy.excel.processer.ValidateProcessor;
import com.imzy.excel.util.StringUtils;

/**
 * 非空校验器
 * @author yangzhang7
 *
 */
@ProcessorDescription(description = "非空校验器")
public class NotBlankValidateProcessor implements ValidateProcessor {

	@Override
	public boolean validate(String value, String[] validatorParam) {
		return StringUtils.isNotBlank(value);
	}

}
