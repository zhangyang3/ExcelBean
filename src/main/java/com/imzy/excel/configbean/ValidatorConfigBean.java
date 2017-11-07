package com.imzy.excel.configbean;

import com.imzy.excel.validator.Validatable;

import lombok.Data;

/**
 * validator配置bean
 * @author yangzhang7
 *
 */
@Data
public class ValidatorConfigBean {

	Class<? extends Validatable> type;

	String param;
}
