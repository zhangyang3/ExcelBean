package com.imzy.excel.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.imzy.excel.exceptions.ExcelException;
import com.imzy.excel.parser.config.AnnotationConfigParser;
import com.imzy.excel.parser.config.XmlConfigParser;
import com.imzy.excel.support.ExcelBeanConst.Appendix;
import com.imzy.excel.support.ThreadLocalHelper;

/**
 * excel导入器
 * @author yangzhang7
 *
 */
public class ExcelImporter {

	private static ExcelImporter excelImporter = new ExcelImporter();

	private ExcelImporter() {
	}

	public static ExcelImporter getInstance() {
		return excelImporter;
	}

	/**
	 * 生成configBean
	 * @param configPath
	 * @param clazz
	 */
	public void initConfigBean(String configPath, Class<?> clazz) {
		if (StringUtils.isNoneBlank(configPath)) {
			XmlConfigParser.getInstance().parse(configPath);
		}
		if (null != clazz) {
			AnnotationConfigParser.getInstance().parse(clazz);
		}
		System.out.println(JSONObject.toJSONString(ThreadLocalHelper.getCurrentExcelConfigBean()));
	}

	/**
	 * xml方式
	 * @param url
	 * @param configPath
	 * @return
	 */
	public Object write(String url, String configPath) {
		initConfigBean(configPath, null);
		return write(new File(url));
	}

	/**
	 * 注解方式
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Object write(String url, Class<?> clazz) {
		initConfigBean(null, clazz);

		return write(new File(url));
	}

	private Object write(File file) {

		if (file.isDirectory() || !file.exists()) {
			throw new ExcelException("excel文件不存在");
		}
		if (!file.getName().endsWith(Appendix.XLS) && !file.getName().endsWith(Appendix.XLSX)) {
			throw new ExcelException("文件不是excel");
		}

		Object result = null;
		FileInputStream is = null;
		Workbook wb = null;

		try {
			is = new FileInputStream(file);
			if (file.getName().endsWith(Appendix.XLS)) {
				POIFSFileSystem fs = new POIFSFileSystem(is);
				wb = new HSSFWorkbook(fs);
			} else {
				wb = new XSSFWorkbook(is);
			}

			ThreadLocalHelper.setCurrentWorkbook(wb);
			// 将excel转过bean
			ExcelParser excelParser = new ExcelParser();
			result = excelParser.parse();
		} catch (Exception e) {
			throw new ExcelException(e.getMessage(), e);
		} finally {
			// 清理线程变量
			ThreadLocalHelper.clearCurrentWorkbook();
			ThreadLocalHelper.clearCurrentSheet();
			ThreadLocalHelper.clearCurrentExcelConfigBean();

			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new ExcelException(e.getMessage(), e);
				}
			}
		}

		return result;
	}
}
