package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.exceptions.ExcelException;
import com.imzy.excel.exceptions.ExitHorizontalExcelException;
import com.imzy.excel.processer.ExistProcessor;
import com.imzy.excel.processer.MappingProcessor;
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

		// 获取excel下面的cell配置列表
		List<CellConfigBean> horizontalCellConfigBeanList = ConfigBeanHelper
				.getHorizontalCellConfigBeanListBySheetFieldName(field.getName());

		for (CellConfigBean cellConfigBean : horizontalCellConfigBeanList) {
			try {
				Field horizontalField = clazz.getDeclaredField(cellConfigBean.getFieldName());
				horizontalField.setAccessible(true);
				// 获取泛型的class
				Type tClazz = ((ParameterizedType) horizontalField.getGenericType()).getActualTypeArguments()[0];

				// 获取列表数据
				List<Object> list = new ArrayList<Object>();

				// 获取外部区域值
				Point point = getPoint(cellConfigBean, null);
				String[][] outerRegionValue = getRegionValue(point);
				for (int i = 0; i < outerRegionValue.length; i++) {
					try {
						Object buildBean = buildBean((Class<?>) tClazz, cellConfigBean.getCellConfigBeanList(),
								cellConfigBean.getExistProcessor(), outerRegionValue, i + 1);
						list.add(buildBean);
					} catch (ExitHorizontalExcelException e) {
						// 如果行结束，跳出循环
						break;
					}
				}
				horizontalField.set(parse, list);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return parse;
	}

	private <T> T buildBean(Class<T> clazz, List<CellConfigBean> cellConfigBeanList,
			Class<? extends ExistProcessor> existProcessorClass, String[][] outerRegionValue, int y) {

		// 反射一个bean
		T newInstance = null;
		try {
			newInstance = clazz.newInstance();
		} catch (Exception e) {
			throw new ExcelException(e.getMessage(), e);
		}

		for (CellConfigBean cellConfigBean : cellConfigBeanList) {
			// 获取坐标点
			Point point = getPoint(cellConfigBean, y);

			String[][] regionValue = getRegionValue(point, outerRegionValue);

			// 获取映射处理器
			MappingProcessor mappingProcessor = MappingProcessorFactory
					.buildMappingProcessor(cellConfigBean.getMappingProcessor());
			// 获取处理结果
			String value = mappingProcessor.mappingValue(regionValue);
			// 做校验
			doValidate(cellConfigBean, value);

			// 退出处理器
			if (null != existProcessorClass && !ExistProcessor.class.equals(existProcessorClass)) {
				ExistProcessor existProcessor = BeanUtils.getBean(existProcessorClass);
				if (existProcessor.exist(point, regionValue, value)) {
					throw new ExitHorizontalExcelException();
				}
			}

			try {
				Field cellField = clazz.getDeclaredField(cellConfigBean.getFieldName());
				cellField.setAccessible(true);

				cellField.set(newInstance, value);
			} catch (Exception e) {
				throw new ExcelException(e.getMessage(), e);
			}
		}
		return newInstance;
	}
}
