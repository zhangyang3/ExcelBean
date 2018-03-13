package com.imzy.excel.processer.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.imzy.excel.annotations.ProcessorDescription;
import com.imzy.excel.processer.ValidateProcessor;

/**
 * 正则校验器
 * @author yangzhang7
 *
 */
@ProcessorDescription(description = "正则校验器")
public class RegularValidateProcessor implements ValidateProcessor {

	@Override
	public boolean validate(String value, String[] validatorParam) {
		Pattern pattern = Pattern.compile(validatorParam[0]);
		Matcher matcher = pattern.matcher(value);

		return matcher.matches();
	}

}
