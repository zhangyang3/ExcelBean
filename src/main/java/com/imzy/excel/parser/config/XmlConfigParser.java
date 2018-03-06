package com.imzy.excel.parser.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.ExcelConfigBean;
import com.imzy.excel.configbean.SheetConfigBean;
import com.imzy.excel.configbean.ValidatorConfigBean;
import com.imzy.excel.enums.CellType;
import com.imzy.excel.enums.SheetType;
import com.imzy.excel.exceptions.XmlConfigExcelException;
import com.imzy.excel.processer.ExitProcessor;
import com.imzy.excel.processer.MappingProcessor;
import com.imzy.excel.processer.PositionProcessor;
import com.imzy.excel.processer.ValidateProcessor;
import com.imzy.excel.processer.mapping.SingleStringMappingProcessor;
import com.imzy.excel.support.ExcelBeanConst.XmlFile.Attribute;
import com.imzy.excel.support.ExcelBeanConst.XmlFile.Node;
import com.imzy.excel.support.ThreadLocalHelper;
import com.imzy.excel.util.StringUtils;

/**
 * xml配置解析器
 * @author yangzhang7
 *
 */
@SuppressWarnings("unchecked")
public class XmlConfigParser {

	private static XmlConfigParser xmlConfigParser = new XmlConfigParser();

	private XmlConfigParser() {
	}

	public static XmlConfigParser getInstance() {
		return xmlConfigParser;
	}

	public void parse(String excelConfigPath) {
		parse(excelConfigPath, "default");
	}

	public void parse(String excelConfigPath, String configName) {
		File excelConfigFile = new File(excelConfigPath);

		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(excelConfigFile);
			Element root = document.getRootElement();

			List<Element> elements = root.elements(Node.EXCEL);
			Element excelElement = null;
			for (Element element : elements) {
				if (StringUtils.equals(configName, element.attributeValue(Attribute.NAME))) {
					excelElement = element;
					break;
				}
			}
			// 解析excel节点
			ExcelConfigBean excelConfigBean = parseExcelNode(excelElement);
			ThreadLocalHelper.setCurrentExcelConfigBean(excelConfigBean);
		} catch (DocumentException e) {
			throw new XmlConfigExcelException(e.getMessage()).setConfigErrorBean(e.getMessage());
		}

	}

	private ExcelConfigBean parseExcelNode(Element excelElement) {
		ExcelConfigBean excelConfigBean = null;

		try {
			if (null != excelElement) {
				excelConfigBean = new ExcelConfigBean();
				String name = excelElement.attributeValue(Attribute.NAME);
				String className = excelElement.attributeValue(Attribute.CLASS);
				excelConfigBean.setClazz(Class.forName(className.trim()));
				excelConfigBean.setName(name.trim());

				List<SheetConfigBean> sheetConfigBeanList = parseSheetNode(excelElement.elements(Node.SHEET));

				excelConfigBean.setSheetConfigBeanList(sheetConfigBeanList);
			}

		} catch (ClassNotFoundException e) {
			throw new XmlConfigExcelException(e.getMessage()).setConfigErrorBean(e.getMessage());
		}

		return excelConfigBean;
	}

	/**
	 * 解析sheet节点
	 * @param sheetElements
	 * @return
	 */
	private List<SheetConfigBean> parseSheetNode(List<Element> sheetElements) {
		List<SheetConfigBean> sheetConfigBeanList = new ArrayList<SheetConfigBean>();

		for (Element element : sheetElements) {
			String fieldName = element.attributeValue(Attribute.FIELD_NAME);
			String name = element.attributeValue(Attribute.NAME);
			String type = element.attributeValue(Attribute.TYPE);
			String startLine = element.attributeValue(Attribute.START_LINE);
			String startColumn = element.attributeValue(Attribute.START_COLUMN);
			String existProcessor = element.attributeValue(Attribute.EXIST_PROCESSOR);
			SheetConfigBean sheetConfigBean = new SheetConfigBean();
			sheetConfigBean.setFieldName(fieldName.trim());
			sheetConfigBean.setName(name.trim());
			SheetType sheetType = SheetType.valueOf(type.trim().toUpperCase());
			sheetConfigBean.setType(sheetType);

			if (SheetType.HORIZONTAL.equals(sheetType) || SheetType.VERTICAL.equals(sheetType)) {
				if (StringUtils.isNotBlank(existProcessor)) {
					try {
						sheetConfigBean.setExistProcessor(
								(Class<? extends ExitProcessor>) Class.forName(existProcessor.trim()));
					} catch (ClassNotFoundException e) {
						throw new XmlConfigExcelException(e.getMessage()).setConfigErrorBean(e.getMessage());
					}
				} else {
					throw new XmlConfigExcelException("横表或竖表模式必须配置existProcessor");
				}
			} else {
				sheetConfigBean.setExistProcessor(ExitProcessor.class);
			}
			sheetConfigBean.setStartColumn(StringUtils.isNotBlank(startColumn) ? startColumn.trim().charAt(0) : 0);
			sheetConfigBean.setStartLine(StringUtils.isNotBlank(startLine) ? Integer.parseInt(startLine.trim()) : -1);

			List<CellConfigBean> cellConfigBeanList = parseCellNode(element.elements(Node.CELL));
			sheetConfigBean.setCellConfigBeanList(cellConfigBeanList);
			sheetConfigBeanList.add(sheetConfigBean);
		}

		return sheetConfigBeanList;
	}

	/**
	 * 解析cell节点
	 * @param elements
	 * @return
	 */
	private List<CellConfigBean> parseCellNode(List<Element> elements) {
		List<CellConfigBean> cellConfigBeanList = new ArrayList<CellConfigBean>();

		try {
			for (Element element : elements) {
				String name = element.attributeValue(Attribute.NAME);
				String fieldName = element.attributeValue(Attribute.FIELD_NAME);
				String startX = element.attributeValue(Attribute.START_X);
				String endX = element.attributeValue(Attribute.END_X);
				String startY = element.attributeValue(Attribute.START_Y);
				String endY = element.attributeValue(Attribute.END_Y);
				String cellType = element.attributeValue(Attribute.CELL_TYPE);
				String mappingProcessor = element.attributeValue(Attribute.MAPPING_PROCESSOR);
				String positionProcessor = element.attributeValue(Attribute.POSITION_PROCESSOR);
				String existProcessor = element.attributeValue(Attribute.EXIST_PROCESSOR);
				CellConfigBean cellConfigBean = new CellConfigBean();
				cellConfigBean.setName(StringUtils.isNotBlank(name) ? name.trim() : StringUtils.EMPTY);
				cellConfigBean.setFieldName(fieldName.trim());
				cellConfigBean.setCellType(StringUtils.isNotBlank(cellType)
						? CellType.valueOf(cellType.trim().toUpperCase()) : CellType.SINGLEVALUE);
				cellConfigBean.setStartX(StringUtils.isNotBlank(startX) ? startX.trim().charAt(0) : 0);
				cellConfigBean.setEndX(StringUtils.isNotBlank(endX) ? endX.trim().charAt(0) : 0);
				cellConfigBean.setStartY(StringUtils.isNotBlank(startY) ? Integer.parseInt(startY.trim()) : -1);
				cellConfigBean.setEndY(StringUtils.isNotBlank(endY) ? Integer.parseInt(endY.trim()) : -1);
				cellConfigBean.setMappingProcessor(StringUtils.isNotBlank(mappingProcessor)
						? (Class<? extends MappingProcessor>) Class.forName(mappingProcessor.trim())
						: SingleStringMappingProcessor.class);
				cellConfigBean.setPositionProcessor(StringUtils.isNotBlank(positionProcessor)
						? (Class<? extends PositionProcessor>) Class.forName(positionProcessor.trim())
						: PositionProcessor.class);
				cellConfigBean.setExistProcessor(StringUtils.isNotBlank(existProcessor)
						? (Class<? extends ExitProcessor>) Class.forName(existProcessor.trim())
						: ExitProcessor.class);

				List<CellConfigBean> innercellConfigBeanList = new ArrayList<CellConfigBean>();
				if (!CellType.SINGLEVALUE.equals(cellConfigBean.getCellType())) {
					innercellConfigBeanList.addAll(parseCellNode(element.elements(Node.CELL)));
				}
				cellConfigBean.setCellConfigBeanList(innercellConfigBeanList);

				List<ValidatorConfigBean> validatorBeanConfigList = parseValidatorNode(
						element.elements(Node.VALIDATOR));
				cellConfigBean.setValidatorConfigBeanList(validatorBeanConfigList);
				cellConfigBeanList.add(cellConfigBean);
			}
		} catch (ClassNotFoundException e) {
			throw new XmlConfigExcelException(e.getMessage()).setConfigErrorBean(e.getMessage());
		}

		return cellConfigBeanList;
	}

	/**
	 * 解析validator节点
	 * @param validators
	 * @return
	 */
	private List<ValidatorConfigBean> parseValidatorNode(List<Element> validators) {
		List<ValidatorConfigBean> validatorConfigBeanList = new ArrayList<ValidatorConfigBean>();
		try {
			for (Element element : validators) {
				String type = element.attributeValue(Attribute.TYPE);
				String param = element.attributeValue(Attribute.PARAM);
				ValidatorConfigBean validatorConfigBean = new ValidatorConfigBean();
				validatorConfigBean.setType((Class<? extends ValidateProcessor>) Class.forName(type.trim()));
				validatorConfigBean.setParam(StringUtils.isNotBlank(param) ? param.trim() : StringUtils.EMPTY);
				validatorConfigBeanList.add(validatorConfigBean);
			}
		} catch (ClassNotFoundException e) {
			throw new XmlConfigExcelException(e.getMessage()).setConfigErrorBean(e.getMessage());
		}
		return validatorConfigBeanList;
	}
}
