package com.imzy.excel.test.annotationbean;

import com.imzy.excel.annotations.Cell;
import com.imzy.excel.annotations.Validator;
import com.imzy.excel.processer.validator.NotBlankValidateProcessor;
import com.imzy.excel.processer.validator.RegularValidateProcessor;
import com.imzy.excel.test.bean.processor.mapping.SxlxMappingProcess;
import com.imzy.excel.test.bean.processor.mapping.XscjMappingProcessor;
import com.imzy.excel.test.bean.processor.position.SsqdBasic47PositionProcessor;

import lombok.Data;

/**
 * 
 * @author yangzhang7
 *
 */
@Data
public class SsqdBasic {

	@Cell(startX = 'c', endX = 'd', startY = 4, endY = 4)
	private String basicCode;
	@Cell(startX = 'c', endX = 'd', startY = 5, endY = 5, mappingProcessor = SxlxMappingProcess.class)
	private String ssbm;
	@Cell(startX = 'c', endX = 'd', startY = 6, endY = 6, validators = { @Validator(type = NotBlankValidateProcessor.class),
			@Validator(type = RegularValidateProcessor.class, param = "^11$") })
	private String sxmc;
	@Cell(startX = 'd', endX = 'd', startY = 7, endY = 7)
	private String sxlx;
	@Cell(startX = 'c', endX = 'd', startY = 9, endY = 9, mappingProcessor = XscjMappingProcessor.class)
	private String xscj;
	@Cell(positionProcessor = SsqdBasic47PositionProcessor.class)
	private String xm;
}
