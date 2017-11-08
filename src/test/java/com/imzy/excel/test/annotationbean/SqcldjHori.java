package com.imzy.excel.test.annotationbean;

import com.imzy.excel.annotations.Cell;

import lombok.Data;

@Data
public class SqcldjHori {

	@Cell(startX = 'a', endX = 'a')
	private String xh;
	@Cell(startX = 'b', endX = 'b')
	private String mc;
	@Cell(startX = 'c', endX = 'c')
	private String yjly;
	@Cell(startX = 'd', endX = 'd')
	private String jy;
	@Cell(startX = 'e', endX = 'e')
	private String qljy;
}
