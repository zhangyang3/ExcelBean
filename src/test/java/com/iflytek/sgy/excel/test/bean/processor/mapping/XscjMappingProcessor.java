package com.iflytek.sgy.excel.test.bean.processor.mapping;

import java.util.HashMap;
import java.util.Map;

import com.iflytek.sgy.excel.processer.mapping.AbstractCheckboxMappingProcessor;

/**
 * 
 * @author yangzhang7
 *
 */
public class XscjMappingProcessor extends AbstractCheckboxMappingProcessor {

	@Override
	protected Map<String, String> initMapping() {
		Map<String, String> map = new HashMap<String, String>(16);

		map.put("b-2", "1");
		map.put("c-2", "2");
		map.put("d-2", "3");
		map.put("e-2", "4");
		map.put("f-2", "5");
		map.put("g-2", "6");

		return map;
	}

	@Override
	protected String initMappingSheetName() {
		return "该页不得更改";
	}

}
