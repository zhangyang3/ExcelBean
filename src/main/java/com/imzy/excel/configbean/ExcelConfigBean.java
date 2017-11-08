package com.imzy.excel.configbean;

import java.util.List;

import com.imzy.excel.processer.ExistProcessor;

import lombok.Data;

/**
 * excel配置bean
 * @author yangzhang7
 *
 */
@Data
public class ExcelConfigBean {

	Class<?> clazz;

	String name;

	Class<? extends ExistProcessor> existProcessor;

	List<SheetConfigBean> sheetConfigBeanList;
}
