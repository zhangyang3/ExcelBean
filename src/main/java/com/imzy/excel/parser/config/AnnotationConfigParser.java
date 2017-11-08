package com.imzy.excel.parser.config;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.imzy.excel.annotations.Cell;
import com.imzy.excel.annotations.Excel;
import com.imzy.excel.annotations.Sheet;
import com.imzy.excel.annotations.Validator;
import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.ExcelConfigBean;
import com.imzy.excel.configbean.SheetConfigBean;
import com.imzy.excel.configbean.ValidatorConfigBean;
import com.imzy.excel.exceptions.AnnotationConfigExcelException;
import com.imzy.excel.support.ThreadLocalHelper;

/**
 * 注解配置解析器
 * @author yangzhang7
 *
 */
public class AnnotationConfigParser {

	private static AnnotationConfigParser annotationConfigParser = new AnnotationConfigParser();

	private AnnotationConfigParser() {
	}

	public static AnnotationConfigParser getInstance() {
		return annotationConfigParser;
	}

	public void parse(Class<?> excelClazz) {
		// 解析excel节点
		ExcelConfigBean excelConfigBean = parseExcelNode(excelClazz);
		ThreadLocalHelper.setCurrentExcelConfigBean(excelConfigBean);
	}

	private ExcelConfigBean parseExcelNode(Class<?> excelClazz) {
		Excel excelAnnotation = excelClazz.getAnnotation(Excel.class);
		if (null == excelAnnotation) {
			throw new AnnotationConfigExcelException(excelClazz + "未加注解" + Excel.class);
		}

		ExcelConfigBean excelConfigBean = new ExcelConfigBean();
		excelConfigBean.setName(excelAnnotation.name());
		excelConfigBean.setClazz(excelClazz);

		List<SheetConfigBean> sheetConfigBeanList = new ArrayList<SheetConfigBean>();
		Field[] declaredFields = excelClazz.getDeclaredFields();
		for (Field field : declaredFields) {
			Sheet sheetAnnotation = field.getAnnotation(Sheet.class);
			if (null != sheetAnnotation) {
				sheetConfigBeanList.add(parseSheetNode(field));
			}
		}
		excelConfigBean.setSheetConfigBeanList(sheetConfigBeanList);

		return excelConfigBean;
	}

	private SheetConfigBean parseSheetNode(Field sheetfield) {
		Sheet sheetAnnotation = sheetfield.getAnnotation(Sheet.class);

		SheetConfigBean sheetConfigBean = new SheetConfigBean();
		sheetConfigBean.setFieldName(sheetfield.getName());
		sheetConfigBean.setName(sheetAnnotation.name());
		sheetConfigBean.setOrder(sheetAnnotation.order());
		sheetConfigBean.setStartLine(sheetAnnotation.startLine());
		sheetConfigBean.setType(sheetAnnotation.type());

		List<CellConfigBean> cellConfigBeanList = new ArrayList<CellConfigBean>();
		Class<?> type = sheetfield.getType();
		// 如果type是集合类型，需要获取泛型的类型
		if (Collection.class.isAssignableFrom(type)) {
			type = (Class<?>) ((ParameterizedType) sheetfield.getGenericType()).getActualTypeArguments()[0];
		}

		Field[] declaredFields = type.getDeclaredFields();
		for (Field field : declaredFields) {
			Cell cellAnnotation = field.getAnnotation(Cell.class);
			if (null != cellAnnotation) {
				cellConfigBeanList.add(parseCellNode(field));
			}
		}

		sheetConfigBean.setCellConfigBeanList(cellConfigBeanList);
		return sheetConfigBean;
	}

	private CellConfigBean parseCellNode(Field cellField) {
		Cell cellAnnotation = cellField.getAnnotation(Cell.class);

		CellConfigBean cellConfigBean = new CellConfigBean();
		cellConfigBean.setEndX(cellAnnotation.endX());
		cellConfigBean.setEndY(cellAnnotation.endY());
		cellConfigBean.setStartX(cellAnnotation.startX());
		cellConfigBean.setStartY(cellAnnotation.startY());
		cellConfigBean.setPositionProcessor(cellAnnotation.positionProcessor());
		cellConfigBean.setFieldName(cellField.getName());
		cellConfigBean.setMappingProcessor(cellAnnotation.mappingProcessor());
		cellConfigBean.setName(cellAnnotation.name());

		List<ValidatorConfigBean> validatorBeanConfigList = parseValidatorNode(cellAnnotation.validators());
		cellConfigBean.setValidatorBeanConfigList(validatorBeanConfigList);
		return cellConfigBean;
	}

	private List<ValidatorConfigBean> parseValidatorNode(Validator[] validators) {
		List<ValidatorConfigBean> validatorConfigBeanList = new ArrayList<ValidatorConfigBean>();
		if (validators.length > 0) {
			for (Validator validator : validators) {
				ValidatorConfigBean validatorConfigBean = new ValidatorConfigBean();
				validatorConfigBean.setParam(validator.param());
				validatorConfigBean.setType(validator.type());
				validatorConfigBeanList.add(validatorConfigBean);
			}
		}

		return validatorConfigBeanList;
	}

}
