package com.iflytek.sgy.excel.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则校验器
 * @author yangzhang7
 *
 */
public class RegularValidator implements Validatable {

	@Override
	public boolean validate(String value, String validatorParam) {
		Pattern pattern = Pattern.compile(validatorParam);
		Matcher matcher = pattern.matcher(value);

		return matcher.matches();
	}

}
