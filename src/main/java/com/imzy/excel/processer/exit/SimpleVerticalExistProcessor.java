package com.imzy.excel.processer.exit;

import java.util.List;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.parser.sheet.ExcelPoint;
import com.imzy.excel.processer.ExitProcessor;
import com.imzy.excel.util.CollectionUtils;
import com.imzy.excel.util.StringUtils;

/**
 * 简单竖表退出处理器
 * @author yangzhang7
 *
 */
public class SimpleVerticalExistProcessor implements ExitProcessor {

	/**
	 * 获取最小行y
	 * @param cellConfigBeanList
	 * @return
	 */
	private Integer getMinY(List<CellConfigBean> cellConfigBeanList) {
		Integer minY = 1;

		if (CollectionUtils.isNotEmpty(cellConfigBeanList)) {
			for (CellConfigBean cellConfigBean : cellConfigBeanList) {
				Integer startY = cellConfigBean.getStartY();
				if (null != startY && startY < minY) {
					minY = startY;
				}
			}
		}

		return minY;
	}

	@Override
	public boolean exist(List<CellConfigBean> cellConfigBeanList, CellConfigBean cellConfigBean, ExcelPoint point,
			String[][] regionValue, String value) {
		Integer minY = getMinY(cellConfigBeanList);

		// 如果a列没有数据，则认为行扫描结束
		return Character.toLowerCase(point.getStartY()) == minY && StringUtils.isBlank(value);
	}

}
