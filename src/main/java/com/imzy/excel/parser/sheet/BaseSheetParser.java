package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.ValidatorConfigBean;
import com.imzy.excel.exceptions.ExcelException;
import com.imzy.excel.exceptions.ExitHorizontalExcelException;
import com.imzy.excel.exceptions.ValidateExcelException;
import com.imzy.excel.parser.ExcelImporter;
import com.imzy.excel.parser.sheet.task.CommonTask;
import com.imzy.excel.processer.ExistProcessor;
import com.imzy.excel.processer.PositionProcessor;
import com.imzy.excel.processer.ValidateProcessor;
import com.imzy.excel.processer.mapping.MappingProcessorFactory;
import com.imzy.excel.processer.validator.ValidateProcessorFactory;
import com.imzy.excel.support.ThreadLocalHelper;
import com.imzy.excel.util.BeanUtils;
import com.imzy.excel.util.SheetUtils;

/**
 * 基础sheet解析器
 * @author yangzhang7
 *
 */
public abstract class BaseSheetParser implements SheetParser, CommonTask {
	private static Logger logger = LoggerFactory.getLogger(ExcelImporter.class);

	protected <T> T buildBean(Class<T> clazz, List<CellConfigBean> singValueCellConfigBeanList) {
		return buildBean(clazz, singValueCellConfigBeanList, null, null, null);
	}

	protected <T> T buildBean(Class<T> clazz, List<CellConfigBean> singleValueCellConfigBeanList,
			Class<? extends ExistProcessor> existProcessorClass, Integer y) {
		return buildBean(clazz, singleValueCellConfigBeanList, existProcessorClass, y, null);
	}

	protected <T> T buildBean(Class<T> clazz, List<CellConfigBean> singleValueCellConfigBeanList,
			Class<? extends ExistProcessor> existProcessorClass, Character x) {
		return buildBean(clazz, singleValueCellConfigBeanList, existProcessorClass, null, x);
	}

	protected <T> T buildBean(Class<T> clazz, List<CellConfigBean> singleValueCellConfigBeanList,
			Class<? extends ExistProcessor> existProcessorClass, Integer y, Character x) {
		return buildBean(clazz, singleValueCellConfigBeanList, existProcessorClass, y, x, null);
	}

	/**
	 * 构建bean
	 * @param clazz 待构建bean的类型
	 * @param singleValueCellConfigBeanList 待构建bean中的cell配置列表
	 * @param sheetConfigBean 带构建bean的sheet配置
	 * @param y y坐标
	 * @param x x坐标
	 * @param filterRegionValue 待筛选的区域值
	 * @return bean
	 */
	protected <T> T buildBean(Class<T> clazz, List<CellConfigBean> singleValueCellConfigBeanList,
			Class<? extends ExistProcessor> existProcessorClass, Integer y, Character x, String[][] filterRegionValue) {

		// 1.构建空对象
		T newInstance = BeanUtils.getBean(clazz);
		// 2.往空对象塞值
		for (CellConfigBean cellConfigBean : singleValueCellConfigBeanList) {
			// 获取坐标点
			Point point = getPoint(cellConfigBean, y, x);
			// 获取区域值
			String[][] regionValue = getRegionValue(point, filterRegionValue);

			// 获取映射值
			String value = MappingProcessorFactory.buildMappingProcessor(cellConfigBean.getMappingProcessor())
					.mappingValue(regionValue);
			// 做校验
			doValidate(cellConfigBean, value);
			// 做退出
			if (doExist(singleValueCellConfigBeanList, cellConfigBean, existProcessorClass, point, value,
					regionValue)) {
				throw new ExitHorizontalExcelException();
			}

			try {
				Field cellField = clazz.getDeclaredField(cellConfigBean.getFieldName());
				BeanUtils.setValue(newInstance, cellField, value);
			} catch (Exception e) {
				throw new ExcelException(e.getMessage(), e);
			}
		}
		return newInstance;
	}

	@Override
	public boolean doExist(List<CellConfigBean> cellConfigBeanList, CellConfigBean cellConfigBean,
			Class<? extends ExistProcessor> existProcessorClass, Point point, String value, String[][] regionValue) {
		if (null != existProcessorClass && !ExistProcessor.class.equals(existProcessorClass)) {
			ExistProcessor existProcessor = BeanUtils.getBean(existProcessorClass);

			return existProcessor.exist(cellConfigBeanList, cellConfigBean, point, regionValue, value);
		}

		return false;
	}

	@Override
	public void doValidate(CellConfigBean cellConfigBean, String value) throws ValidateExcelException {
		List<ValidatorConfigBean> validatorBeanConfigList = cellConfigBean.getValidatorConfigBeanList();

		if (CollectionUtils.isNotEmpty(validatorBeanConfigList)) {
			for (ValidatorConfigBean validatorConfigBean : validatorBeanConfigList) {
				// 校验器class类型
				Class<? extends ValidateProcessor> validatorClass = validatorConfigBean.getType();
				// 校验器参数
				String param = validatorConfigBean.getParam();

				if (!ValidateProcessorFactory.buildValidator(validatorClass).validate(value, param)) {
					throw new ValidateExcelException(value + "不通过" + validatorClass);
				}
			}
		}
	}

	/**
	 * 获取结束x坐标
	 * @param cellConfigBean
	 * @return
	 */
	private Character getEndX(CellConfigBean cellConfigBean) {
		Character endX = cellConfigBean.getEndX();
		Class<? extends PositionProcessor> positionProcesser = cellConfigBean.getPositionProcessor();
		if (null != positionProcesser && !PositionProcessor.class.equals(positionProcesser)) {
			try {
				PositionProcessor newInstance = positionProcesser.newInstance();
				endX = newInstance.getEndX(ThreadLocalHelper.getCurrentSheet());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return endX;
	}

	/**
	 * 获取结束y坐标
	 * @param cellConfigBean
	 * @return
	 */
	private Integer getEndY(CellConfigBean cellConfigBean) {
		Integer endY = cellConfigBean.getEndY();

		Class<? extends PositionProcessor> positionProcesser = cellConfigBean.getPositionProcessor();
		if (null != positionProcesser && !PositionProcessor.class.equals(positionProcesser)) {
			try {
				PositionProcessor newInstance = positionProcesser.newInstance();
				endY = newInstance.getEndY(ThreadLocalHelper.getCurrentSheet());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return endY;
	}

	/**
	 * 获取坐标
	 * @param cellConfigBean
	 * @param y y坐标
	 * @param x x坐标
	 * @return
	 */
	protected Point getPoint(CellConfigBean cellConfigBean, Integer y, Character x) {
		Character startX = 0, endX = 0;
		Integer startY = -1, endY = -1;

		if (null == x) {
			startX = getStartX(cellConfigBean);
			endX = getEndX(cellConfigBean);
		} else {
			startX = x;
			endX = x;
		}
		if (null == y) {
			startY = getStartY(cellConfigBean);
			endY = getEndY(cellConfigBean);
		} else {
			startY = y;
			endY = y;
		}

		Point point = new Point(startX, startY, endX, endY);
		if (logger.isDebugEnabled()) {
			logger.debug(JSONObject.toJSONString(point));
		}
		return point;
	}

	protected String[][] getRegionValue(CellConfigBean cellConfigBean) {
		return getRegionValue(cellConfigBean, null);
	}

	protected String[][] getRegionValue(CellConfigBean cellConfigBean, Integer y) {
		return getRegionValue(cellConfigBean, y, null);
	}

	protected String[][] getRegionValue(CellConfigBean cellConfigBean, Integer y, Character x) {
		return getRegionValue(cellConfigBean, y, x, null);
	}

	/**
	 * 获取区域值
	 * @param cellConfigBean 配置数据
	 * @param y y坐标
	 * @param x x坐标
	 * @param regionValue 待筛选的区域值
	 * @return 区域值
	 */
	protected String[][] getRegionValue(CellConfigBean cellConfigBean, Integer y, Character x, String[][] regionValue) {
		Point point = getPoint(cellConfigBean, y, x);
		return getRegionValue(point, regionValue);
	}

	/**
	 * 从regionValue筛选数据
	 * @param point 坐标点
	 * @param regionValue 待筛选的区域值
	 * @return 区域值
	 */
	protected String[][] getRegionValue(Point point, String[][] regionValue) {
		// 初始化参数
		char startX = point.getStartX();
		char endX = point.getEndX();
		int startY = point.getStartY();
		int endY = point.getEndY();

		int arrayY = endY - startY + 1;
		int arrayX = Character.toLowerCase(endX) - Character.toLowerCase(startX) + 1;
		String[][] result = new String[arrayY][arrayX];

		for (int i = 0; i < arrayY; i++) {
			// 如果待筛选区域值为null，则从当前sheet页取
			if (regionValue == null) {
				Row row = ThreadLocalHelper.getCurrentSheet().getRow(Character.toLowerCase(startY) - 1 + i);
				for (int j = 0; j < arrayX; j++) {
					org.apache.poi.ss.usermodel.Cell cell = row.getCell(Character.toLowerCase(startX) - 'a' + j);
					result[i][j] = SheetUtils.getCellValue(cell);
				}
			} else {
				String[] row = regionValue[Character.toLowerCase(startY) - 1 + i];
				for (int j = 0; j < arrayX; j++) {
					result[i][j] = row[Character.toLowerCase(startX) - 'a' + j];
				}
			}
		}

		return result;
	}

	/**
	 * 获取开始x坐标
	 * @param cellConfigBean
	 * @return
	 */
	private Character getStartX(CellConfigBean cellConfigBean) {
		Character startX = cellConfigBean.getStartX();

		Class<? extends PositionProcessor> positionProcesser = cellConfigBean.getPositionProcessor();
		if (null != positionProcesser && !PositionProcessor.class.equals(positionProcesser)) {
			try {
				PositionProcessor newInstance = positionProcesser.newInstance();

				startX = newInstance.getStartX(ThreadLocalHelper.getCurrentSheet());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return startX;
	}

	/**
	 * 获取开始y坐标
	 * @param cellConfigBean
	 * @return
	 */
	private Integer getStartY(CellConfigBean cellConfigBean) {
		Integer startY = cellConfigBean.getStartY();

		Class<? extends PositionProcessor> positionProcesser = cellConfigBean.getPositionProcessor();
		if (null != positionProcesser && !PositionProcessor.class.equals(positionProcesser)) {
			try {
				PositionProcessor newInstance = positionProcesser.newInstance();
				startY = newInstance.getStartY(ThreadLocalHelper.getCurrentSheet());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return startY;
	}

}
