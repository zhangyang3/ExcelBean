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

	/** 当前横标的y*/
	private static ThreadLocal<Integer> currentHorizontalY = new ThreadLocal<Integer>();

	public static void clearCurrentHorizontalY() {
		currentHorizontalY.remove();
	}

	public static void setCurrentHorizontalY(Integer y) {
		currentHorizontalY.set(y);
	}

	public static Integer getCurrentHorizontalY() {
		return currentHorizontalY.get();
	}

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
