package com.imzy.excel.support;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.ExcelConfigBean;
import com.imzy.excel.configbean.SheetConfigBean;
import com.imzy.excel.enums.CellType;

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
	 * 获取某个sheet字段中CellType值为CellType.SINGLEVALUE的cellConfigBean列表
	 * @param sheetFieldName
	 * @return
	 */
	public static List<CellConfigBean> getSignleValueCellConfigBeanListBySheetFieldName(String sheetFieldName) {
		return getSomeCellConfigBeanListBySheetFieldNameAndCellType(sheetFieldName, CellType.SINGLEVALUE);
	}

	/**
	 * 获取某个sheet字段中CellType值为CellType.HORIZONTAL的cellConfigBean列表
	 * @param sheetFieldName
	 * @return
	 */
	public static List<CellConfigBean> getHorizontalCellConfigBeanListBySheetFieldName(String sheetFieldName) {
		return getSomeCellConfigBeanListBySheetFieldNameAndCellType(sheetFieldName, CellType.HORIZONTAL);
	}

	@SuppressWarnings("unchecked")
	private static List<CellConfigBean> getSomeCellConfigBeanListBySheetFieldNameAndCellType(String sheetFieldName,
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
