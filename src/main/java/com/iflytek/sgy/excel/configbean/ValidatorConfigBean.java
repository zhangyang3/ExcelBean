package com.iflytek.sgy.excel.configbean;

import com.iflytek.sgy.excel.validator.Validatable;

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
