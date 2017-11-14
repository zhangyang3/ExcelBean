package com.imzy.excel.support;

import java.util.List;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.ExcelConfigBean;
import com.imzy.excel.configbean.SheetConfigBean;
import com.imzy.excel.enums.CellType;
import com.imzy.excel.util.CollectionUtils;
import com.imzy.excel.util.CollectionUtils.Predicate;
import com.imzy.excel.util.StringUtils;

/**
 * 配置bean帮助类
 * @author yangzhang7
 *
 */
public class ConfigBeanHelper {

	/**
	 * 获取excel的类型
	 * @return
	 */
	public static Class<?> getExcelClass() {
		ExcelConfigBean currentExcelConfigBean = ThreadLocalHelper.getCurrentExcelConfigBean();
		return currentExcelConfigBean.getClazz();
	}

	/**
	 * 获取sheetConfigBean列表
	 * @return
	 */
	public static List<SheetConfigBean> getSheetConfigBeanList() {
		ExcelConfigBean currentExcelConfigBean = ThreadLocalHelper.getCurrentExcelConfigBean();
		return currentExcelConfigBean.getSheetConfigBeanList();
	}

	/**
	 * 获取某个sheet字段的sheetConfigBean
	 * @param sheetFieldName
	 * @return
	 */
	public static SheetConfigBean getSheetConfigBean(String sheetFieldName) {
		List<SheetConfigBean> sheetConfigBeanList = getSheetConfigBeanList();
		for (SheetConfigBean sheetConfigBean : sheetConfigBeanList) {
			if (StringUtils.equals(sheetFieldName, sheetConfigBean.getFieldName())) {
				return sheetConfigBean;
			}
		}

		return null;
	}

	/**
	 * 获取某个sheet字段中cellConfigBean列表
	 * @param sheetFieldName
	 * @param cellType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<CellConfigBean> getSomeCellConfigBeanListBySheetFieldNameAndCellType(String sheetFieldName,
			final CellType cellType) {
		List<SheetConfigBean> sheetConfigBeanList = getSheetConfigBeanList();
		for (SheetConfigBean sheetConfigBean : sheetConfigBeanList) {
			if (StringUtils.equals(sheetFieldName, sheetConfigBean.getFieldName())) {
				return (List<CellConfigBean>) CollectionUtils.select(sheetConfigBean.getCellConfigBeanList(),
						new Predicate() {
							@Override
							public boolean evaluate(Object object) {
								CellConfigBean cellConfigBean = (CellConfigBean) object;
								if (cellType.equals(cellConfigBean.getCellType())) {
									return true;
								} else {
									return false;
								}
							}
						});
			}
		}

		return null;
	}
}
