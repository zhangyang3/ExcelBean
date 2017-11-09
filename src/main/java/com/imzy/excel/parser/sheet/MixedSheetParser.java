package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.enums.CellType;
import com.imzy.excel.exceptions.ExcelException;
import com.imzy.excel.exceptions.ExitHorizontalExcelException;
import com.imzy.excel.processer.ExistProcessor;
import com.imzy.excel.processer.mapping.MappingProcessorFactory;
import com.imzy.excel.support.ConfigBeanHelper;
import com.imzy.excel.util.BeanUtils;

/**
 * 混合sheet解析器
 * @author yangzhang7
 *
 */
public class MixedSheetParser extends VerticalSheetParser {

	@Override
	public <T> T parse(Field field, Class<T> clazz) {
		T parse = super.parse(field, clazz);

		// 获取excel下面的CellType为HORIZONTAL的cell配置列表
		List<CellConfigBean> horizontalCellConfigBeanList = ConfigBeanHelper
				.getSomeCellConfigBeanListBySheetFieldNameAndCellType(field.getName(), CellType.HORIZONTAL);

		for (CellConfigBean cellConfigBean : horizontalCellConfigBeanList) {
			try {
				// 获取HORIZONTAL标注的字段
				Field horizontalField = clazz.getDeclaredField(cellConfigBean.getFieldName());
				// 获取HORIZONTAL标注的字段的集合中泛型
				Type genericType = BeanUtils.getGenericType(horizontalField);

				List<Object> list = buildBeanList(cellConfigBean, (Class<?>) genericType);
				BeanUtils.setValue(parse, horizontalField, list);
			} catch (Exception e) {
				throw new ExcelException(e.getMessage(), e);
			}
		}

		return parse;
	}

	/**
	 * 构建bean列表
	 * @param cellConfigBean
	 * @param genericTypeClass
	 * @return
	 */
	private List<Object> buildBeanList(CellConfigBean cellConfigBean, Class<?> genericTypeClass) {
		List<Object> list = new ArrayList<Object>();

		// 获取外部区域值
		String[][] outerRegionValue = getRegionValue(cellConfigBean, null);

		for (int i = 0; i < outerRegionValue.length; i++) {
			try {
				Object buildBean = buildBean(genericTypeClass, cellConfigBean.getCellConfigBeanList(),
						cellConfigBean.getExistProcessor(), outerRegionValue, i + 1);
				list.add(buildBean);
			} catch (ExitHorizontalExcelException e) {
				// 如果行结束，跳出循环
				break;
			}
		}
		return list;
	}

	/**
	 * 构建bean
	 * @param clazz 待构建bean的class
	 * @param cellConfigBeanList 待构建bean中的cell配置列表
	 * @param existProcessorClass 退出处理器
	 * @param outerRegionValue 外部区域值
	 * @param y y坐标，从1开始
	 * @return
	 */
	private <T> T buildBean(Class<T> clazz, List<CellConfigBean> cellConfigBeanList,
			Class<? extends ExistProcessor> existProcessorClass, String[][] outerRegionValue, int y) {
		// 1.构建空对象
		T newInstance = BeanUtils.getBean(clazz);
		// 2.往空对象塞值
		for (CellConfigBean cellConfigBean : cellConfigBeanList) {
			// 获取坐标点
			Point point = getPoint(cellConfigBean, y);
			// 获取区域值
			String[][] regionValue = getRegionValue(point, outerRegionValue);

			// 获取映射值
			String value = MappingProcessorFactory.buildMappingProcessor(cellConfigBean.getMappingProcessor())
					.mappingValue(regionValue);
			// 做校验
			doValidate(cellConfigBean, value);
			// 做退出
			if (doExist(existProcessorClass, point, value, regionValue)) {
				throw new ExitHorizontalExcelException();
			}

			try {
				Field cellField = clazz.getDeclaredField(cellConfigBean.getFieldName());
				BeanUtils.setValue(newInstance, cellField, value);
			} catch (Exception e) {
				throw new ExcelException(e.getMessage(), e);
			}
		}
		return newInstance;
	}

}
