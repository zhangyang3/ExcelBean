package com.imzy.excel.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.imzy.excel.exceptions.ExcelException;
import com.imzy.excel.parser.config.AnnotationConfigParser;
import com.imzy.excel.parser.config.XmlConfigParser;
import com.imzy.excel.support.ExcelBeanConst.Suffix;
import com.imzy.excel.support.ThreadLocalHelper;
import com.imzy.excel.util.StringUtils;

/**
 * excel导入器
 * @author yangzhang7
 *
 */
public class ExcelImporter {
	private static Logger logger = LoggerFactory.getLogger(ExcelImporter.class);

	private static ExcelImporter excelImporter = new ExcelImporter();

	private ExcelImporter() {
	}

	public static ExcelImporter getInstance() {
		return excelImporter;
	}

	/**
	 * 生成configBean
	 * @param configPath xml方式下，xml配置文件
	 * @param clazz annotation方式下，待解析的class
	 */
	public void initConfigBean(String configPath, Class<?> clazz) {
		if (StringUtils.isNotBlank(configPath)) {
			XmlConfigParser.getInstance().parse(configPath);
		}
		if (null != clazz) {
			AnnotationConfigParser.getInstance().parse(clazz);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(JSONObject.toJSONString(ThreadLocalHelper.getCurrentExcelConfigBean()));
		}
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
	@SuppressWarnings("unchecked")
	public <T> T write(String url, Class<T> clazz) throws ExcelException {
		initConfigBean(null, clazz);
		return (T) write(new File(url));
	}

	private Object write(File file) throws ExcelException {

		if (file.isDirectory() || !file.exists()) {
			throw new ExcelException("excel文件不存在");
		}
		if (!file.getName().endsWith(Suffix.XLS) && !file.getName().endsWith(Suffix.XLSX)) {
			throw new ExcelException("文件不是excel");
		}

		Object result = null;
		FileInputStream is = null;
		Workbook workbook = null;

		try {
			is = new FileInputStream(file);
			if (file.getName().endsWith(Suffix.XLS)) {
				POIFSFileSystem fs = new POIFSFileSystem(is);
				workbook = new HSSFWorkbook(fs);
			} else {
				workbook = new XSSFWorkbook(is);
			}
			// 设置当前工作簿
			ThreadLocalHelper.setCurrentWorkbookName(file.getName());
			ThreadLocalHelper.setCurrentWorkbook(workbook);

			// 将excel转过bean
			ExcelParser excelParser = new ExcelParser();
			result = excelParser.parse();
		} catch (ExcelException e) {
			throw e;
		} catch (Exception e) {
			throw new ExcelException(e.getMessage()).setCommonErrorBean(e.getMessage());
		} finally {
			// 清理线程变量
			ThreadLocalHelper.clearCurrentWorkbookName();
			ThreadLocalHelper.clearCurrentWorkbook();
			ThreadLocalHelper.clearCurrentSheet();
			ThreadLocalHelper.clearCurrentExcelConfigBean();

			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new ExcelException(e.getMessage()).setCommonErrorBean(e.getMessage());
				}
			}
		}

		return result;
	}
}
