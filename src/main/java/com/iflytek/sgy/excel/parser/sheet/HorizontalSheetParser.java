package com.iflytek.sgy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iflytek.sgy.excel.configbean.CellConfigBean;
import com.iflytek.sgy.excel.configbean.SheetConfigBean;
import com.iflytek.sgy.excel.exceptions.ExcelException;
import com.iflytek.sgy.excel.exceptions.ExitHorizontalExcelException;
import com.iflytek.sgy.excel.processer.MappingProcessor;
import com.iflytek.sgy.excel.processer.mapping.MappingProcessorFactory;
import com.iflytek.sgy.excel.support.ConfigBeanHelper;
import com.iflytek.sgy.excel.support.ThreadLocalHelper;

/**
 * 水平sheet解析器
 * @author yangzhang7
 *
 */
public class HorizontalSheetParser extends BaseSheetParser {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T parse(Field field, Class<T> clazz) {
		// 获取开始行数
		SheetConfigBean sheetConfigBean = ConfigBeanHelper.getSheetConfigBean(field.getName());
		int startLine = sheetConfigBean.getStartLine();

		// 获取泛型的class
		Type tClazz = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

		// 获取列表数据
		List list = new ArrayList();
		org.apache.poi.ss.usermodel.Sheet currentSheet = ThreadLocalHelper.getCurrentSheet();
		int physicalNumberOfRows = currentSheet.getPhysicalNumberOfRows();
		for (int i = startLine; i < physicalNumberOfRows; i++) {
			try {
				// 添加每行值
				Object tClazzObject = buildObject(field, (Class) tClazz, i);
				list.add(tClazzObject);
			} catch (ExitHorizontalExcelException e) {
				// 如果行结束，跳出循环
				break;
			}
		}

		return (T) list;
	}

	/**
	 * 生成每一个泛型对象
	 * @param clazz
	 * @param y
	 * @return
	 */
	private <T> T buildObject(Field field, Class<T> clazz, int y) {

		T newInstance = null;
		try {
			newInstance = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		// 获取excel下面的cell列表
		List<CellConfigBean> cellConfigBeanList = ConfigBeanHelper
				.getCellConfigBeanListBySheetFieldName(field.getName());
		for (CellConfigBean cellConfigBean : cellConfigBeanList) {
			// 获取坐标点
			Point point = getPoint(cellConfigBean, y);

			// 获取区域值
			String[][] regionValue = getRegionValue(point);

			// 获取映射处理器
			MappingProcessor mappingProcessor = MappingProcessorFactory
					.buildMappingProcessor(cellConfigBean.getMappingProcessor());
			// 获取处理结果
			String value = mappingProcessor.mappingValue(regionValue);
			// 做校验
			doValidate(cellConfigBean, value);

			// 如果a列没有数据，则认为行扫描结束
			if (Character.toLowerCase(point.getStartX()) == 'a' && StringUtils.isBlank(value)) {
				throw new ExitHorizontalExcelException();
			}

			try {
				Field f = clazz.getDeclaredField(cellConfigBean.getFieldName());
				f.setAccessible(true);

				f.set(newInstance, value);
			} catch (Exception e) {
				throw new ExcelException(e.getMessage(), e);
			}
		}

		return newInstance;
	}

}
