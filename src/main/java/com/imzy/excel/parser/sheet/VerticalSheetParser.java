package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.util.List;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.SheetConfigBean;
import com.imzy.excel.support.ConfigBeanHelper;

/**
 * 垂直sheet解析器
 * @author yangzhang7
 *
 */
public class VerticalSheetParser extends BaseSheetParser {

	@Override
	public <T> T parse(Field field, Class<T> clazz) {

		// 获取excel下面的cell配置列表
		List<CellConfigBean> cellConfigBeanList = ConfigBeanHelper
				.getCellConfigBeanListBySheetFieldName(field.getName());
		// 获取excel的配置
		SheetConfigBean sheetConfigBean = ConfigBeanHelper.getSheetConfigBean(field.getName());

		// 构建bean
		T newInstance = buildBean(clazz, cellConfigBeanList, sheetConfigBean);

		return newInstance;
	}

}
