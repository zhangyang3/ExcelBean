package com.imzy.excel.configbean;

import com.imzy.excel.processer.ConvertProcessor;

import lombok.Data;

/**
 * convertor配置bean
 * @author yangzhang7
 *
 */
@Data
public class ConvertorConfigBean {

	Class<? extends ConvertProcessor> type;

	String[] param;
}
