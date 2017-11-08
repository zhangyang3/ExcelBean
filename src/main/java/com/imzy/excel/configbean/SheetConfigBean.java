package com.imzy.excel.configbean;

import java.util.List;

import com.imzy.excel.enums.SheetType;
import com.imzy.excel.processer.ExistProcessor;

import lombok.Data;

/**
 * sheet配置bean
 * @author yangzhang7
 *
 */
@Data
public class SheetConfigBean {

	String fieldName;

	String name;

	SheetType type;

	Integer startLine;

	Class<? extends ExistProcessor> existProcessor;

	List<CellConfigBean> cellConfigBeanList;

}
