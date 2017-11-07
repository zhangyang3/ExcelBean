package com.iflytek.sgy.excel.test.annotationbean;

import com.iflytek.sgy.excel.annotations.Cell;
import com.iflytek.sgy.excel.annotations.Validator;
import com.iflytek.sgy.excel.test.bean.processor.mapping.SxlxMappingProcess;
import com.iflytek.sgy.excel.validator.NotBlankValidator;
import com.iflytek.sgy.excel.validator.RegularValidator;

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
	@Cell(startX = 'b', endX = 'b', validators = { @Validator(type = NotBlankValidator.class),
			@Validator(type = RegularValidator.class, param = "^A$") })
	private String type;
	@Cell(startX = 'c', endX = 'c', mappingProcessor = SxlxMappingProcess.class)
	private String name;
	@Cell(startX = 'd', endX = 'd')
	private String timeLimit;
	@Cell(startX = 'e', endX = 'e')
	private String according;
}
