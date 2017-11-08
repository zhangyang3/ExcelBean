package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.SheetConfigBean;
import com.imzy.excel.exceptions.ExitHorizontalExcelException;
import com.imzy.excel.support.ConfigBeanHelper;
import com.imzy.excel.support.ThreadLocalHelper;

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
				ThreadLocalHelper.setCurrentHorizontalY(i);
				// 添加每行值
				Object tClazzObject = buildObject(field, (Class) tClazz);
				list.add(tClazzObject);
			} catch (ExitHorizontalExcelException e) {
				// 如果行结束，跳出循环
				break;
			} finally {
				ThreadLocalHelper.clearCurrentHorizontalY();
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
	private <T> T buildObject(Field field, Class<T> clazz) {

		// 获取excel下面的cell配置列表
		List<CellConfigBean> cellConfigBeanList = ConfigBeanHelper
				.getCellConfigBeanListBySheetFieldName(field.getName());
		// 获取excel的配置
		SheetConfigBean sheetConfigBean = ConfigBeanHelper.getSheetConfigBean(field.getName());

		T buildBean = buildBean(clazz, cellConfigBeanList, sheetConfigBean);

		return buildBean;
	}

}
