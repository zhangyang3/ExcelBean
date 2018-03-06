package com.imzy.excel.configbean;

import java.util.List;

import com.imzy.excel.enums.SheetType;
import com.imzy.excel.processer.ExitProcessor;

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

	Character startColumn;

	Class<? extends ExitProcessor> existProcessor;

	List<CellConfigBean> cellConfigBeanList;

}
