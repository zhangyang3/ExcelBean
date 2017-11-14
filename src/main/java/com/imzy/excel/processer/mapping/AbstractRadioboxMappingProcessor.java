package com.imzy.excel.processer.mapping;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.imzy.excel.processer.MappingProcessor;
import com.imzy.excel.support.ThreadLocalHelper;
import com.imzy.excel.util.SheetUtils;

/**
 * 抽象单选框映射处理器
 * @author yangzhang7
 *
 */
public abstract class AbstractRadioboxMappingProcessor implements MappingProcessor {

	/**
	 * 设置映射字典值<br>
	 * @return
	 */
	protected abstract Map<String, String> initMapping();

	/**
	 * 设置映射sheet的名称
	 * @return
	 */
	protected abstract String initMappingSheetName();

	/**
	 * 获取单选框坐标
	 * @return
	 */
	protected abstract Entry<String, String> getPosition();

	@Override
	public String mappingValue(String[][] regionValue) {
		Map<String, String> initMapping = initMapping();
		String initMappingSheetName = initMappingSheetName();

		Workbook currentWorkbook = ThreadLocalHelper.getCurrentWorkbook();
		Sheet sheet = currentWorkbook.getSheet(initMappingSheetName);

		String value = SheetUtils.getCellValue(sheet, getPosition().getKey(), getPosition().getValue());

		return initMapping.get(value);

	}

}
