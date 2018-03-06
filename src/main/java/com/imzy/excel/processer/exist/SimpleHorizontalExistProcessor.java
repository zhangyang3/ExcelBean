package com.imzy.excel.processer.exist;

import java.util.List;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.parser.sheet.ExcelPoint;
import com.imzy.excel.processer.ExitProcessor;
import com.imzy.excel.util.CollectionUtils;
import com.imzy.excel.util.StringUtils;

/**
 * 简单横表退出处理器
 * @author yangzhang7
 *
 */
public class SimpleHorizontalExistProcessor implements ExitProcessor {

	/**
	 * 获取退出列x
	 * @param cellConfigBeanList
	 * @return
	 */
	protected Character getExistX(List<CellConfigBean> cellConfigBeanList) {
		Character minX = 'a';

		if (CollectionUtils.isNotEmpty(cellConfigBeanList)) {
			for (CellConfigBean cellConfigBean : cellConfigBeanList) {
				Character startX = cellConfigBean.getStartX();
				if (null != startX && startX < minX) {
					minX = startX;
				}
			}
		}

		return minX;
	}

	@Override
	public boolean exist(List<CellConfigBean> cellConfigBeanList, CellConfigBean cellConfigBean, ExcelPoint point,
			String[][] regionValue, String value) {
		Character minX = getExistX(cellConfigBeanList);

		// 如果最小列没有数据，则认为行扫描结束
		return Character.toLowerCase(point.getStartX()) == minX && StringUtils.isBlank(value);
	}

}
