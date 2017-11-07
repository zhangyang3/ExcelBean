package com.imzy.excel.test.bean.processor.mapping;

import java.util.HashMap;
import java.util.Map;

import com.imzy.excel.processer.mapping.AbstractDropdownMappingProcessor;

/**
 * 
 * @author yangzhang7
 *
 */
public class SxlxMappingProcess extends AbstractDropdownMappingProcessor {

	@Override
	protected Map<String, String> initMapping() {
		Map<String, String> map = new HashMap<String, String>(16);

		map.put("行政权力", "1");
		map.put("行政许可（行政审批）", "2");
		map.put("行政征收", "3");
		map.put("行政给付", "4");
		map.put("行政规划", "5");
		map.put("行政确认", "6");
		map.put("行政奖励", "7");
		map.put("其他事项", "8");
		map.put("行政处罚", "9");
		map.put("行政裁决", "10");
		map.put("行政强制", "11");

		return map;
	}

}
