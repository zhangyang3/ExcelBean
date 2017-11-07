package com.imzy.excel.processer.mapping;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.imzy.excel.processer.MappingProcessor;
import com.imzy.excel.support.ThreadLocalHelper;
import com.imzy.excel.util.SheetUtils;

/**
 * 多选框映射处理器
 * @author yangzhang7
 *
 */
public abstract class AbstractCheckboxMappingProcessor implements MappingProcessor {

	/**
	 * 设置映射字典值<br>
	 * key为A-1，value为字典值
	 * @return
	 */
	protected abstract Map<String, String> initMapping();

	/**
	 * 设置映射sheet的名称
	 * @return
	 */
	protected abstract String initMappingSheetName();

	@Override
	public String mappingValue(String[][] regionValue) {
		StringBuilder result = new StringBuilder(100);

		Map<String, String> initMapping = initMapping();
		String initMappingSheetName = initMappingSheetName();

		Workbook currentWorkbook = ThreadLocalHelper.getCurrentWorkbook();
		Sheet sheet = currentWorkbook.getSheet(initMappingSheetName);

		for (Entry<String, String> entry : initMapping.entrySet()) {
			String key = entry.getKey();
			String[] split = key.split("-");
			String value = SheetUtils.getCellValue(sheet, split[0], split[1]);
			if (StringUtils.equalsIgnoreCase("TRUE", value)) {
				result.append(entry.getValue()).append(",");
			}
		}

		return result.substring(0, result.length() - 1);
	}

}
