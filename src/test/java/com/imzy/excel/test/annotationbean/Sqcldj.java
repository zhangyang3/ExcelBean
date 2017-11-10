package com.imzy.excel.test.annotationbean;

import java.util.List;

import com.imzy.excel.annotations.Cell;
import com.imzy.excel.enums.CellType;
import com.imzy.excel.processer.exist.SimpleHorizontalExistProcessor;
import com.imzy.excel.test.bean.processor.position.SqcldjHoriPositionProcessor;
import com.imzy.excel.test.bean.processor.position.SqcldjXmPositionProcessor;

import lombok.Data;

/**
 * 
 * @author yangzhang7
 *
 */
@Data
public class Sqcldj {

	@Cell(positionProcessor = SqcldjXmPositionProcessor.class)
	private String xm;
	@Cell(startX = 'd', endX = 'f', startY = 2, endY = 2)
	private String sxmc;
	@Cell(startX = 'd', endX = 'f', startY = 3, endY = 3)
	private String sxlb;
	@Cell(startX = 'd', endX = 'f', startY = 4, endY = 4)
	private String xhmc;
	@Cell(positionProcessor = SqcldjHoriPositionProcessor.class, cellType = CellType.HORIZONTAL, existProcessor = SimpleHorizontalExistProcessor.class)
	private List<SqcldjHori> sqcldjHoriList;
}
