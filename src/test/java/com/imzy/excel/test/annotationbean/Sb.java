package com.imzy.excel.test.annotationbean;

import com.imzy.excel.annotations.Cell;

import lombok.Data;

@Data
public class Sb {

	@Cell(startY = 1, endY = 1)
	private String hjmc;
	@Cell(startY = 2, endY = 2)
	private String hjsj;
	@Cell(startY = 3, endY = 3)
	private String sdyj;
}
