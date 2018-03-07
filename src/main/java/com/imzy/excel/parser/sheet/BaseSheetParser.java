package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.util.List;

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
import com.imzy.excel.processer.ExitProcessor;
import com.imzy.excel.processer.PositionProcessor;
import com.imzy.excel.processer.ValidateProcessor;
import com.imzy.excel.processer.exit.ExitProcessorFactory;
import com.imzy.excel.processer.mapping.MappingProcessorFactory;
import com.imzy.excel.processer.position.PositionProcessorFactory;
import com.imzy.excel.processer.validator.ValidateProcessorFactory;
import com.imzy.excel.support.ThreadLocalHelper;
import com.imzy.excel.util.BeanUtils;
import com.imzy.excel.util.CollectionUtils;
import com.imzy.excel.util.SheetUtils;
import com.imzy.excel.util.StringUtils;

/**
 * 基础sheet解析器
 * @author yangzhang7
 *
 */
public abstract class BaseSheetParser implements SheetParser, CommonTask {
	private static Logger logger = LoggerFactory.getLogger(ExcelImporter.class);

	@Override
	public boolean doExist(List<CellConfigBean> cellConfigBeanList, CellConfigBean cellConfigBean,
			Class<? extends ExitProcessor> existProcessorClass, ExcelPoint point, String value,
			String[][] regionValue) {
		if (null != existProcessorClass && !ExitProcessor.class.equals(existProcessorClass)) {
			ExitProcessor existProcessor = ExitProcessorFactory.getExistProcessor(existProcessorClass);
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
				String[] param = validatorConfigBean.getParam();

				if (!ValidateProcessorFactory.getValidatorProcessor(validatorClass).validate(value, param)) {
					String errorReason = value + "不通过" + validatorClass;
					throw new ValidateExcelException(errorReason).setValidateErrorBean(
							String.valueOf(cellConfigBean.getStartX()), String.valueOf(cellConfigBean.getStartY()),
							errorReason);
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

		Class<? extends PositionProcessor> positionProcesserClass = cellConfigBean.getPositionProcessor();
		if (null != positionProcesserClass && !PositionProcessor.class.equals(positionProcesserClass)) {
			PositionProcessor positionProcesser = PositionProcessorFactory.getPositionProcessor(positionProcesserClass);
			endX = positionProcesser.getEndX(ThreadLocalHelper.getCurrentSheet());
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

		Class<? extends PositionProcessor> positionProcesserClass = cellConfigBean.getPositionProcessor();
		if (null != positionProcesserClass && !PositionProcessor.class.equals(positionProcesserClass)) {
			PositionProcessor positionProcesser = PositionProcessorFactory.getPositionProcessor(positionProcesserClass);
			endY = positionProcesser.getEndY(ThreadLocalHelper.getCurrentSheet());
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
	private ExcelPoint getPoint(CellConfigBean cellConfigBean, Integer y, Character x) {
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

		ExcelPoint point = new ExcelPoint(startX, startY, endX, endY);
		if (logger.isDebugEnabled()) {
			logger.debug(JSONObject.toJSONString(point));
		}
		return point;
	}

	/**
	 * 获取开始x坐标
	 * @param cellConfigBean
	 * @return
	 */
	private Character getStartX(CellConfigBean cellConfigBean) {
		Character startX = cellConfigBean.getStartX();

		Class<? extends PositionProcessor> positionProcesserClass = cellConfigBean.getPositionProcessor();
		if (null != positionProcesserClass && !PositionProcessor.class.equals(positionProcesserClass)) {
			PositionProcessor positionProcesser = PositionProcessorFactory.getPositionProcessor(positionProcesserClass);
			startX = positionProcesser.getStartX(ThreadLocalHelper.getCurrentSheet());
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

		Class<? extends PositionProcessor> positionProcesserClass = cellConfigBean.getPositionProcessor();
		if (null != positionProcesserClass && !PositionProcessor.class.equals(positionProcesserClass)) {
			PositionProcessor positionProcesser = PositionProcessorFactory.getPositionProcessor(positionProcesserClass);
			startY = positionProcesser.getStartY(ThreadLocalHelper.getCurrentSheet());
		}

		return startY;
	}

	protected <T> T buildBean(Class<T> clazz, List<CellConfigBean> singValueCellConfigBeanList) {
		return buildBean(clazz, singValueCellConfigBeanList, null, null, null);
	}

	protected <T> T buildBean(Class<T> clazz, List<CellConfigBean> singleValueCellConfigBeanList,
			Class<? extends ExitProcessor> existProcessorClass, Character x) {
		return buildBean(clazz, singleValueCellConfigBeanList, existProcessorClass, null, x);
	}

	protected <T> T buildBean(Class<T> clazz, List<CellConfigBean> singleValueCellConfigBeanList,
			Class<? extends ExitProcessor> existProcessorClass, Integer y) {
		return buildBean(clazz, singleValueCellConfigBeanList, existProcessorClass, y, null);
	}

	protected <T> T buildBean(Class<T> clazz, List<CellConfigBean> singleValueCellConfigBeanList,
			Class<? extends ExitProcessor> existProcessorClass, Integer y, Character x) {
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
			Class<? extends ExitProcessor> existProcessorClass, Integer y, Character x, String[][] filterRegionValue) {

		// 1.构建空对象
		T newInstance = BeanUtils.getBean(clazz);
		// 2.往空对象塞值
		for (CellConfigBean cellConfigBean : singleValueCellConfigBeanList) {
			// 获取坐标点
			ExcelPoint point = getPoint(cellConfigBean, y, x);
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
				throw new ExcelException(e.getMessage()).setCommonErrorBean(e.getMessage());
			}
		}
		return newInstance;
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
		ExcelPoint point = getPoint(cellConfigBean, y, x);
		return getRegionValue(point, regionValue);
	}

	/**
	 * 从regionValue筛选数据
	 * @param point 坐标点
	 * @param regionValue 待筛选的区域值
	 * @return 区域值
	 */
	protected String[][] getRegionValue(ExcelPoint point, String[][] regionValue) {
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
				Row row = ThreadLocalHelper.getCurrentSheet().getRow(startY - 1 + i);
				for (int j = 0; j < arrayX; j++) {
					if (row != null) {
						org.apache.poi.ss.usermodel.Cell cell = row.getCell(Character.toLowerCase(startX) - 'a' + j);
						result[i][j] = SheetUtils.getCellValue(cell);
					} else {
						result[i][j] = StringUtils.EMPTY;
					}
				}
			} else {
				String[] row = regionValue[startY - 1 + i];
				for (int j = 0; j < arrayX; j++) {
					result[i][j] = row[Character.toLowerCase(startX) - 'a' + j];
				}
			}
		}

		return result;
	}

}
