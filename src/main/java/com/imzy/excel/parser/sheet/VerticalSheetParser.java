package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.util.List;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.exceptions.ExcelException;
import com.imzy.excel.processer.MappingProcessor;
import com.imzy.excel.processer.mapping.MappingProcessorFactory;
import com.imzy.excel.support.ConfigBeanHelper;

/**
 * 垂直sheet解析器
 * @author yangzhang7
 *
 */
public class VerticalSheetParser extends BaseSheetParser {

	@Override
	public <T> T parse(Field filed, Class<T> clazz) {

		T newInstance = null;
		try {
			newInstance = clazz.newInstance();
		} catch (Exception e) {
			throw new ExcelException(e.getMessage(), e);
		}

		// 获取excel下面的cell列表
		List<CellConfigBean> cellConfigBeanList = ConfigBeanHelper
				.getCellConfigBeanListBySheetFieldName(filed.getName());
		for (CellConfigBean cellConfigBean : cellConfigBeanList) {
			// 获取坐标点
			Point point = getPoint(cellConfigBean);

			// 获取区域值
			String[][] regionValue = getRegionValue(point);

			// 获取映射处理器
			MappingProcessor mappingProcessor = MappingProcessorFactory
					.buildMappingProcessor(cellConfigBean.getMappingProcessor());
			// 获取处理结果
			String value = mappingProcessor.mappingValue(regionValue);
			// 做校验
			doValidate(cellConfigBean, value);

			try {
				Field field = clazz.getDeclaredField(cellConfigBean.getFieldName());
				field.setAccessible(true);

				field.set(newInstance, value);
			} catch (Exception e) {
				throw new ExcelException(e.getMessage(), e);
			}
		}

		return newInstance;
	}

}
