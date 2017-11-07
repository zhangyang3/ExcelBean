package com.imzy.excel.configbean;

import java.util.List;

import com.imzy.excel.enums.SheetType;

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

	Integer order;

	Integer startLine;

	List<CellConfigBean> cellConfigBeanList;

}
