package com.iflytek.sgy.excel.test.annotationbean;

import com.iflytek.sgy.excel.annotations.Cell;
import com.iflytek.sgy.excel.annotations.Validator;
import com.iflytek.sgy.excel.test.bean.processor.mapping.SxlxMappingProcess;
import com.iflytek.sgy.excel.test.bean.processor.mapping.XscjMappingProcessor;
import com.iflytek.sgy.excel.validator.NotBlankValidator;
import com.iflytek.sgy.excel.validator.RegularValidator;

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
	@Cell(startX = 'c', endX = 'd', startY = 6, endY = 6, validators = { @Validator(type = NotBlankValidator.class),
			@Validator(type = RegularValidator.class, param = "^11$") })
	private String sxmc;
	@Cell(startX = 'd', endX = 'd', startY = 7, endY = 7)
	private String sxlx;
	@Cell(startX = 'c', endX = 'd', startY = 9, endY = 9, mappingProcessor = XscjMappingProcessor.class)
	private String xscj;
}
