package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.enums.CellType;
import com.imzy.excel.support.ConfigBeanHelper;

/**
 * 一般sheet解析器
 * @author yangzhang7
 *
 */
public class BasicSheetParser extends BaseSheetParser {
	private static Logger logger = LoggerFactory.getLogger(BasicSheetParser.class);

	@Override
	public <T> T parse(Field field, Class<T> clazz) {

		// 获取excel下面的CellType为SINGLEVALUE的cell配置列表
		List<CellConfigBean> singValueCellConfigBeanList = ConfigBeanHelper
				.getSomeCellConfigBeanListBySheetFieldNameAndCellType(field.getName(), CellType.SINGLEVALUE);

		// 构建bean
		T newInstance = buildBean(clazz, singValueCellConfigBeanList);

		if (logger.isDebugEnabled()) {
			logger.debug(JSONObject.toJSONString(newInstance));
		}

		return newInstance;
	}

}
