package com.iflytek.sgy.excel.configbean;

import java.util.List;

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

	List<SheetConfigBean> sheetConfigBeanList;
}
