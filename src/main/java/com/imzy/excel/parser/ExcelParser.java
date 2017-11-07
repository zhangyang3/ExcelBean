package com.imzy.excel.parser;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.imzy.excel.configbean.SheetConfigBean;
import com.imzy.excel.enums.SheetType;
import com.imzy.excel.exceptions.ExcelException;
import com.imzy.excel.parser.sheet.SheetParser;
import com.imzy.excel.parser.sheet.SheetParserFactory;
import com.imzy.excel.support.ConfigBeanHelper;
import com.imzy.excel.support.ThreadLocalHelper;

/**
 * excel解析器
 * @author yangzhang7
 *
 */
public class ExcelParser {

	public Object parse() {
		// 线程中workbook
		Workbook wb = ThreadLocalHelper.getCurrentWorkbook();
		// 当前excel的类
		Class<?> excelClazz = ConfigBeanHelper.getExcelClass();

		// 1.构建excelBean
		Object excelBean = null;
		try {
			excelBean = excelClazz.newInstance();
		} catch (Exception e) {
			throw new ExcelException(e.getMessage(), e);
		}

		// 2.设置excelBean的字段值
		List<SheetConfigBean> sheetConfigBeanList = ConfigBeanHelper.getSheetConfigBeanList();
		for (SheetConfigBean sheetConfigBean : sheetConfigBeanList) {
			SheetType sheetType = sheetConfigBean.getType();
			try {
				Field excelFiled = excelClazz.getDeclaredField(sheetConfigBean.getFieldName());
				excelFiled.setAccessible(true);

				// 获取具体的sheet处理器
				SheetParser sheetParser = SheetParserFactory.buildSheetParser(sheetType);
				if (null == sheetParser) {
					throw new ExcelException(excelClazz.getName() + "字段" + excelFiled.getName() + "不支持");
				}

				// 获取具体的sheet页
				ThreadLocalHelper.setCurrentSheet(wb.getSheetAt((sheetConfigBean.getOrder() - 1)));
				// 获取sheet具体的class
				Class<?> excelFieldType = excelFiled.getType();
				// 校验sheet类型
				validateSheetType(excelFieldType, sheetType);

				Object bean = sheetParser.parse(excelFiled, excelFieldType);

				excelFiled.set(excelBean, bean);
			} catch (Exception e) {
				throw new ExcelException(e.getMessage(), e);
			} finally {
				ThreadLocalHelper.clearCurrentSheet();
			}
		}

		return excelBean;
	}

	/**
	 * 校验sheet类型
	 * @param type
	 * @param sheetType
	 */
	private void validateSheetType(Class<?> type, SheetType sheetType) {
		if (SheetType.HORIZONTAL.equals(sheetType)) {
			if (!Collection.class.isAssignableFrom(type)) {
				throw new ExcelException("SheetType.HORIZONTAL只能作用于Collection上");
			}
		} else {
			if (Collection.class.isAssignableFrom(type)) {
				throw new ExcelException("非SheetType.HORIZONTAL只能作用于非Collection上");

			}
		}
	}
}
