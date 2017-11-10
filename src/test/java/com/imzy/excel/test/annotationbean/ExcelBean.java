package com.imzy.excel.test.annotationbean;

import java.util.List;

import com.imzy.excel.annotations.Excel;
import com.imzy.excel.annotations.Sheet;
import com.imzy.excel.enums.SheetType;
import com.imzy.excel.processer.exist.SimpleExistProcessor;

import lombok.Data;

/**
 * 
 * @author yangzhang7
 *
 */
@Data
@Excel(name = "test")
public class ExcelBean {

	@Sheet(name = "实施清单要素", type = SheetType.BASIC)
	private SsqdBasic ssqdBasic;

	@Sheet(name = "附表1特别程序要素", type = SheetType.HORIZONTAL, startLine = 3, existProcessor = SimpleExistProcessor.class)
	private List<Special> specialList;

	@Sheet(name = "申请材料清理情况登记表", type = SheetType.MIXED)
	private Sqcldj sqcldj;
}
