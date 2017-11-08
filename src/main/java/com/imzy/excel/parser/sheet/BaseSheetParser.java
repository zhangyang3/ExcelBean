package com.imzy.excel.parser.sheet;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.ValidatorConfigBean;
import com.imzy.excel.exceptions.ValidateExcelException;
import com.imzy.excel.parser.sheet.task.CommonTask;
import com.imzy.excel.processer.PositionProcessor;
import com.imzy.excel.support.ThreadLocalHelper;
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
	 * 获取结束x坐标
	 * @param cellConfigBean
	 * @return
	 */
	private char getEndX(CellConfigBean cellConfigBean) {
		char endX = cellConfigBean.getEndX();
		Class<? extends PositionProcessor> positionProcesser = cellConfigBean.getPositionProcesser();
		if (null != positionProcesser && !PositionProcessor.class.equals(positionProcesser)) {
			try {
				PositionProcessor newInstance = positionProcesser.newInstance();
				endX = newInstance.getEndX();
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
	private char getStartX(CellConfigBean cellConfigBean) {
		char startX = cellConfigBean.getStartX();

		Class<? extends PositionProcessor> positionProcesser = cellConfigBean.getPositionProcesser();
		if (null != positionProcesser && !PositionProcessor.class.equals(positionProcesser)) {
			try {
				PositionProcessor newInstance = positionProcesser.newInstance();
				startX = newInstance.getStartX();
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
	private int getStartY(CellConfigBean cellConfigBean) {
		int startY = cellConfigBean.getStartY();

		Class<? extends PositionProcessor> positionProcesser = cellConfigBean.getPositionProcesser();
		if (null != positionProcesser && !PositionProcessor.class.equals(positionProcesser)) {
			try {
				PositionProcessor newInstance = positionProcesser.newInstance();
				startY = newInstance.getStartY();
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
	private int getEndY(CellConfigBean cellConfigBean) {
		int endY = cellConfigBean.getEndY();

		Class<? extends PositionProcessor> positionProcesser = cellConfigBean.getPositionProcesser();
		if (null != positionProcesser && !PositionProcessor.class.equals(positionProcesser)) {
			try {
				PositionProcessor newInstance = positionProcesser.newInstance();
				endY = newInstance.getEndY();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return endY;
	}

	protected Point getPoint(CellConfigBean cellConfigBean) {
		return getPoint(cellConfigBean, null);
	}

	protected Point getPoint(CellConfigBean cellConfigBean, Integer y) {
		char startX = getStartX(cellConfigBean);
		char endX = getEndX(cellConfigBean);
		int startY = -1, endY = -1;
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
		List<ValidatorConfigBean> validatorBeanConfigList = cellConfigBean.getValidatorBeanConfigList();
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
