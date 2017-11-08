package com.imzy.excel.parser.sheet;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.ValidatorConfigBean;
import com.imzy.excel.exceptions.ExcelException;
import com.imzy.excel.exceptions.ExitHorizontalExcelException;
import com.imzy.excel.exceptions.ValidateExcelException;
import com.imzy.excel.parser.sheet.task.CommonTask;
import com.imzy.excel.processer.ExistProcessor;
import com.imzy.excel.processer.MappingProcessor;
import com.imzy.excel.processer.PositionProcessor;
import com.imzy.excel.processer.mapping.MappingProcessorFactory;
import com.imzy.excel.support.ThreadLocalHelper;
import com.imzy.excel.util.BeanUtils;
import com.imzy.excel.util.SheetUtils;
import com.imzy.excel.validator.Validatable;
import com.imzy.excel.validator.ValidatableFactory;

/**
 * 基础sheet解析器
 * @author yangzhang7
 *
 */
public abstract class BaseSheetParser implements SheetParser, CommonTask {

	/**
	 * 构建bean
	 * @param clazz 待构建bean的类型
	 * @param cellConfigBeanList 待构建bean中的cell配置列表
	 * @param sheetConfigBean 带构建bean的sheet配置
	 * @return
	 */
	protected <T> T buildBean(Class<T> clazz, List<CellConfigBean> cellConfigBeanList,
			Class<? extends ExistProcessor> existProcessorClass) {

		// 反射一个bean
		T newInstance = null;
		try {
			newInstance = clazz.newInstance();
		} catch (Exception e) {
			throw new ExcelException(e.getMessage(), e);
		}

		for (CellConfigBean cellConfigBean : cellConfigBeanList) {
			// 获取坐标点
			Point point = getPoint(cellConfigBean, ThreadLocalHelper.getCurrentHorizontalY());

			// 获取区域值
			String[][] regionValue = getRegionValue(point);

			// 获取映射处理器
			MappingProcessor mappingProcessor = MappingProcessorFactory
					.buildMappingProcessor(cellConfigBean.getMappingProcessor());
			// 获取处理结果
			String value = mappingProcessor.mappingValue(regionValue);
			// 做校验
			doValidate(cellConfigBean, value);

			// 退出处理器
			if (null != existProcessorClass && !ExistProcessor.class.equals(existProcessorClass)) {
				ExistProcessor existProcessor = BeanUtils.getBean(existProcessorClass);
				if (existProcessor.exist(point, regionValue, value)) {
					throw new ExitHorizontalExcelException();
				}
			}

			try {
				Field cellField = clazz.getDeclaredField(cellConfigBean.getFieldName());
				cellField.setAccessible(true);

				cellField.set(newInstance, value);
			} catch (Exception e) {
				throw new ExcelException(e.getMessage(), e);
			}
		}
		return newInstance;
	}

	/**
	 * 获取区域值
	 * @param point 坐标点
	 * @return
	 */
	protected String[][] getRegionValue(Point point) {
		// 初始化参数
		char startX = point.getStartX();
		char endX = point.getEndX();
		int startY = point.getStartY();
		int endY = point.getEndY();
		Sheet currentSheet = ThreadLocalHelper.getCurrentSheet();

		int arrayY = endY - startY + 1;
		int arrayX = Character.toLowerCase(endX) - Character.toLowerCase(startX) + 1;
		String[][] result = new String[arrayY][arrayX];

		for (int i = 0; i < arrayY; i++) {
			Row row = currentSheet.getRow(Character.toLowerCase(startY) - 1 + i);
			for (int j = 0; j < arrayX; j++) {
				org.apache.poi.ss.usermodel.Cell cell = row.getCell(Character.toLowerCase(startX) - 'a' + j);
				result[i][j] = SheetUtils.getCellValue(cell);
			}
		}

		return result;
	}

	/**
	 * 从regionValue删选数据
	 * @param point
	 * @param regionValue
	 * @return
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
			String[] row = regionValue[Character.toLowerCase(startY) - 1 + i];
			for (int j = 0; j < arrayX; j++) {
				result[i][j] = row[Character.toLowerCase(startX) - 'a' + j];
			}
		}

		return result;
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
	 * @param y  threadlocal中存的当前y
	 * @return
	 */
	protected Point getPoint(CellConfigBean cellConfigBean, Integer y) {
		Character startX = getStartX(cellConfigBean);
		Character endX = getEndX(cellConfigBean);
		Integer startY = -1, endY = -1;
		if (null == y) {
			startY = getStartY(cellConfigBean);
			endY = getEndY(cellConfigBean);
		} else {
			startY = y;
			endY = y;
		}
		return new Point(startX, startY, endX, endY);
	}

	@Override
	public void doValidate(CellConfigBean cellConfigBean, String value) throws ValidateExcelException {
		List<ValidatorConfigBean> validatorBeanConfigList = cellConfigBean.getValidatorConfigBeanList();
		for (ValidatorConfigBean validatorConfigBean : validatorBeanConfigList) {
			// 校验器class类型
			Class<? extends Validatable> validatorClass = validatorConfigBean.getType();
			// 校验器参数
			String param = validatorConfigBean.getParam();
			Validatable buildValidator = ValidatableFactory.buildValidator(validatorClass);

			if (!buildValidator.validate(value, param)) {
				throw new ValidateExcelException(value + "不通过" + validatorClass);
			}
		}
	}

}
