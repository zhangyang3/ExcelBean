package com.iflytek.sgy.excel.test.annotationbean;

import java.util.List;

import com.iflytek.sgy.excel.annotations.Excel;
import com.iflytek.sgy.excel.annotations.Sheet;
import com.iflytek.sgy.excel.enums.SheetType;

import lombok.Data;

/**
 * 
 * @author yangzhang7
 *
 */
@Data
@Excel(name = "test")
public class ExcelBean {

	@Sheet(name = "实施清单要素", order = 1, type = SheetType.VERTICAL)
	private SsqdBasic ssqdBasic;

	@Sheet(name = "附表1特别程序要素", order = 2, type = SheetType.HORIZONTAL, startLine = 3)
	private List<Special> specialList;
}
