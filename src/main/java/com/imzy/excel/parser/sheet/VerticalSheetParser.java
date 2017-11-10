package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.SheetConfigBean;
import com.imzy.excel.enums.CellType;
import com.imzy.excel.exceptions.ExitHorizontalExcelException;
import com.imzy.excel.support.ConfigBeanHelper;
import com.imzy.excel.util.BeanUtils;

/**
 * 水平sheet解析器
 * @author yangzhang7
 *
 */
public class VerticalSheetParser extends BaseSheetParser {
	private static Logger logger = LoggerFactory.getLogger(VerticalSheetParser.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T parse(Field field, Class<T> clazz) {
		// 获取开始行数
		SheetConfigBean sheetConfigBean = ConfigBeanHelper.getSheetConfigBean(field.getName());
		Character startColumn = sheetConfigBean.getStartColumn();

		// 获取泛型的class
		Type tClazz = BeanUtils.getGenericType(field);

		// 获取列表数据
		List list = new ArrayList();
		for (Character x = startColumn;; x++) {
			try {
				// 添加每行值
				Object tClazzObject = buildObject(field, (Class) tClazz, x);
				list.add(tClazzObject);
			} catch (ExitHorizontalExcelException e) {
				// 如果行结束，跳出循环
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(JSONObject.toJSONString(list));
		}

		return (T) list;
	}

	/**
	 * 生成每一个泛型对象
	 * @param field
	 * @param clazz
	 * @param x x坐标
	 * @return
	 */
	private <T> T buildObject(Field field, Class<T> clazz, Character x) {

		// 获取excel下面的cell配置列表
		List<CellConfigBean> singValueCellConfigBeanList = ConfigBeanHelper
				.getSomeCellConfigBeanListBySheetFieldNameAndCellType(field.getName(), CellType.SINGLEVALUE);
		// 获取excel的配置
		SheetConfigBean sheetConfigBean = ConfigBeanHelper.getSheetConfigBean(field.getName());

		T buildBean = buildBean(clazz, singValueCellConfigBeanList, sheetConfigBean.getExistProcessor(), null, x);

		return buildBean;
	}

}
