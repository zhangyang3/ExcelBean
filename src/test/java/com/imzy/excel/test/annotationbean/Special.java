package com.imzy.excel.test.annotationbean;

import com.imzy.excel.annotations.Cell;
import com.imzy.excel.annotations.Validator;
import com.imzy.excel.processer.validator.NotBlankValidateProcessor;
import com.imzy.excel.processer.validator.RegularValidateProcessor;
import com.imzy.excel.test.bean.processor.mapping.SxlxMappingProcess;

import lombok.Data;

/**
 * 
 * @author yangzhang7
 *
 */
@Data
public class Special {
	@Cell(startX = 'a', endX = 'a')
	private String no;
	@Cell(startX = 'b', endX = 'b', validators = { @Validator(type = NotBlankValidateProcessor.class),
			@Validator(type = RegularValidateProcessor.class, param = "^A$") })
	private String type;
	@Cell(startX = 'c', endX = 'c', mappingProcessor = SxlxMappingProcess.class)
	private String name;
	@Cell(startX = 'd', endX = 'd')
	private String timeLimit;
	@Cell(startX = 'e', endX = 'e')
	private String according;
}
