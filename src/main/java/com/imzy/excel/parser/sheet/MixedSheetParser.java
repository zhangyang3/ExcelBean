package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.enums.CellType;
import com.imzy.excel.exceptions.ExcelException;
import com.imzy.excel.exceptions.ExitHorizontalExcelException;
import com.imzy.excel.support.ConfigBeanHelper;
import com.imzy.excel.util.BeanUtils;

/**
 * 混合sheet解析器
 * @author yangzhang7
 *
 */
public class MixedSheetParser extends BaseSheetParser {
	private static Logger logger = LoggerFactory.getLogger(MixedSheetParser.class);

	private BasicSheetParser basicSheetParser = SheetParserFactory.getSheetParser(BasicSheetParser.class);

	@Override
	public <T> T parse(Field field, Class<T> clazz) {
		// 1.解析基本字段
		T newInstance = basicSheetParser.parse(field, clazz);
		// 2.解析横表字段
		parseHorizontal(field, clazz, newInstance);
		// 3.解析竖表字段
		parseVertical(field, clazz, newInstance);

		if (logger.isDebugEnabled()) {
			logger.debug(JSONObject.toJSONString(newInstance));
		}

		return newInstance;
	}

	/**
	 * 构建横表的bean列表
	 * @param cellConfigBean
	 * @param genericTypeClass
	 * @return
	 */
	private List<Object> buildHorizontalBeanList(CellConfigBean cellConfigBean, Class<?> genericTypeClass) {
		List<Object> list = new ArrayList<Object>();

		// 获取外部区域值
		String[][] outerRegionValue = getRegionValue(cellConfigBean);

		for (int i = 0; i < outerRegionValue.length; i++) {
			try {
				Object buildBean = buildBean(genericTypeClass, cellConfigBean.getCellConfigBeanList(),
						cellConfigBean.getExistProcessor(), i + 1, null, outerRegionValue);
				list.add(buildBean);
			} catch (ExitHorizontalExcelException e) {
				// 如果行结束，跳出循环
				break;
			}
		}
		return list;
	}

	/**
	 * 构建竖表的bean列表
	 * @param cellConfigBean
	 * @param genericTypeClass
	 * @return
	 */
	private List<Object> buildVerticalBeanList(CellConfigBean cellConfigBean, Class<?> genericTypeClass) {
		List<Object> list = new ArrayList<Object>();

		// 获取外部区域值
		String[][] outerRegionValue = getRegionValue(cellConfigBean);

		for (int i = 0; i < outerRegionValue[0].length; i++) {
			try {
				Object buildBean = buildBean(genericTypeClass, cellConfigBean.getCellConfigBeanList(),
						cellConfigBean.getExistProcessor(), null, (char) (i + 'a'), outerRegionValue);
				list.add(buildBean);
			} catch (ExitHorizontalExcelException e) {
				// 如果行结束，跳出循环
				break;
			}
		}
		return list;
	}

	/**
	 * 解析横表
	 * @param field
	 * @param clazz
	 * @param newInstance
	 */
	private <T> void parseHorizontal(Field field, Class<T> clazz, T newInstance) {
		// 获取excel下面的CellType为HORIZONTAL的cell配置列表
		List<CellConfigBean> horizontalCellConfigBeanList = ConfigBeanHelper
				.getSomeCellConfigBeanListBySheetFieldNameAndCellType(field.getName(), CellType.HORIZONTAL);

		for (CellConfigBean cellConfigBean : horizontalCellConfigBeanList) {
			try {
				// 获取HORIZONTAL标注的字段
				Field horizontalField = clazz.getDeclaredField(cellConfigBean.getFieldName());
				// 获取HORIZONTAL标注的字段的集合中泛型
				Type genericType = BeanUtils.getGenericType(horizontalField);

				List<Object> list = buildHorizontalBeanList(cellConfigBean, (Class<?>) genericType);
				BeanUtils.setValue(newInstance, horizontalField, list);
			} catch (Exception e) {
				throw new ExcelException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 解析竖表
	 * @param field
	 * @param clazz
	 * @param newInstance
	 */
	private <T> void parseVertical(Field field, Class<T> clazz, T newInstance) {
		// 获取excel下面的CellType为VERTICAL的cell配置列表
		List<CellConfigBean> vertitalCellConfigBeanList = ConfigBeanHelper
				.getSomeCellConfigBeanListBySheetFieldNameAndCellType(field.getName(), CellType.VERTICAL);

		for (CellConfigBean cellConfigBean : vertitalCellConfigBeanList) {
			try {
				// 获取VERTICAL标注的字段
				Field verticalField = clazz.getDeclaredField(cellConfigBean.getFieldName());
				// 获取VERTICAL标注的字段的集合中泛型
				Type genericType = BeanUtils.getGenericType(verticalField);

				List<Object> list = buildVerticalBeanList(cellConfigBean, (Class<?>) genericType);
				BeanUtils.setValue(newInstance, verticalField, list);
			} catch (Exception e) {
				throw new ExcelException(e.getMessage(), e);
			}
		}
	}

}
