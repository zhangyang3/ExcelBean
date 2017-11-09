package com.imzy.excel.support;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.imzy.excel.configbean.ExcelConfigBean;

/**
 * threadlocal帮助类
 * @author yangzhang7
 *
 */
public class ThreadLocalHelper {

	private static ThreadLocal<Workbook> currentWorkbook = new ThreadLocal<Workbook>();

	private static ThreadLocal<Sheet> currentSheet = new ThreadLocal<Sheet>();

	private static ThreadLocal<ExcelConfigBean> currentExcelConfigBean = new ThreadLocal<ExcelConfigBean>();

	public static void clearCurrentExcelConfigBean() {
		currentExcelConfigBean.remove();
	}

	public static void setCurrentExcelConfigBean(ExcelConfigBean excelConfigBean) {
		currentExcelConfigBean.set(excelConfigBean);
	}

	public static ExcelConfigBean getCurrentExcelConfigBean() {
		return currentExcelConfigBean.get();
	}

	public static Sheet getCurrentSheet() {
		return currentSheet.get();
	}

	public static Workbook getCurrentWorkbook() {
		return currentWorkbook.get();
	}

	public static void setCurrentSheet(Sheet sheet) {
		currentSheet.set(sheet);
	}

	public static void setCurrentWorkbook(Workbook workbook) {
		currentWorkbook.set(workbook);
	}

	public static void clearCurrentSheet() {
		currentSheet.remove();
	}

	public static void clearCurrentWorkbook() {
		currentWorkbook.remove();
	}
}
