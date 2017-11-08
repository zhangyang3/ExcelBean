package com.imzy.excel.test.annotationbean;

import com.imzy.excel.annotations.Cell;
import com.imzy.excel.test.bean.processor.position.SqcldjXmPositionProcessor;

import lombok.Data;

@Data
public class Sqcldj {

	@Cell(positionProcessor = SqcldjXmPositionProcessor.class)
	private String xm;

}
