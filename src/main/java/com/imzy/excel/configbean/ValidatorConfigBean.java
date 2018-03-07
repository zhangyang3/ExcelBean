package com.imzy.excel.configbean;

import com.imzy.excel.processer.ValidateProcessor;

import lombok.Data;

/**
 * validator配置bean
 * @author yangzhang7
 *
 */
@Data
public class ValidatorConfigBean {

	Class<? extends ValidateProcessor> type;

	String[] param;
}
