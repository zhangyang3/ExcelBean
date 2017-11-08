package com.imzy.excel.parser.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.imzy.excel.configbean.CellConfigBean;
import com.imzy.excel.configbean.ExcelConfigBean;
import com.imzy.excel.configbean.SheetConfigBean;
import com.imzy.excel.configbean.ValidatorConfigBean;
import com.imzy.excel.enums.SheetType;
import com.imzy.excel.exceptions.XmlConfigExcelException;
import com.imzy.excel.processer.ExistProcessor;
import com.imzy.excel.processer.MappingProcessor;
import com.imzy.excel.processer.PositionProcessor;
import com.imzy.excel.processer.mapping.SingleStringMappingProcessor;
import com.imzy.excel.support.ThreadLocalHelper;
import com.imzy.excel.validator.Validatable;

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

			List<Element> elements = root.elements("excel");
			Element excelElement = null;
			for (Element element : elements) {
				if (StringUtils.equals(configName, element.attributeValue("name"))) {
					excelElement = element;
					break;
				}
			}
			// 解析excel节点
			ExcelConfigBean excelConfigBean = parseExcelNode(excelElement);
			ThreadLocalHelper.setCurrentExcelConfigBean(excelConfigBean);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private ExcelConfigBean parseExcelNode(Element excelElement) {
		ExcelConfigBean excelConfigBean = null;

		try {
			if (null != excelElement) {
				excelConfigBean = new ExcelConfigBean();
				String name = excelElement.attributeValue("name");
				String className = excelElement.attributeValue("class");
				excelConfigBean.setClazz(Class.forName(className));
				excelConfigBean.setName(name);

				List<SheetConfigBean> sheetConfigBeanList = parseSheetNode(excelElement.elements("sheet"));

				excelConfigBean.setSheetConfigBeanList(sheetConfigBeanList);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
			String fieldName = element.attributeValue("fieldName");
			String name = element.attributeValue("name");
			String type = element.attributeValue("type");
			String startLine = element.attributeValue("startLine");
			String existProcessor = element.attributeValue("existProcessor");
			SheetConfigBean sheetConfigBean = new SheetConfigBean();
			sheetConfigBean.setFieldName(fieldName);
			sheetConfigBean.setName(name);
			SheetType sheetType = SheetType.valueOf(type.toUpperCase());
			sheetConfigBean.setType(sheetType);

			if (SheetType.HORIZONTAL.equals(sheetType)) {
				if (StringUtils.isNotBlank(existProcessor)) {
					try {
						sheetConfigBean
								.setExistProcessor((Class<? extends ExistProcessor>) Class.forName(existProcessor));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					throw new XmlConfigExcelException("横表模式必须配置existProcessor");
				}
			}

			if (StringUtils.isNotBlank(startLine)) {
				sheetConfigBean.setStartLine(Integer.parseInt(startLine.trim()));
			}

			List<CellConfigBean> cellConfigBeanList = parseCellNode(element.elements("cell"));
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
				String name = element.attributeValue("name");
				String fieldName = element.attributeValue("fieldName");
				String startX = element.attributeValue("startX");
				String endX = element.attributeValue("endX");
				String startY = element.attributeValue("startY");
				String endY = element.attributeValue("endY");
				String mappingProcessor = element.attributeValue("mappingProcessor");
				String positionProcessor = element.attributeValue("positionProcessor");
				CellConfigBean cellConfigBean = new CellConfigBean();
				cellConfigBean.setName(name);
				cellConfigBean.setFieldName(fieldName);
				if (StringUtils.isNotBlank(startX)) {
					cellConfigBean.setStartX(startX.charAt(0));
				}
				if (StringUtils.isNotBlank(endX)) {
					cellConfigBean.setEndX(endX.charAt(0));
				}
				if (StringUtils.isNotBlank(startY)) {
					cellConfigBean.setStartY(Integer.parseInt(startY.trim()));
				}
				if (StringUtils.isNotBlank(endY)) {
					cellConfigBean.setEndY(Integer.parseInt(endY.trim()));
				}
				if (StringUtils.isNotBlank(mappingProcessor)) {
					cellConfigBean.setMappingProcessor(
							(Class<? extends MappingProcessor>) Class.forName(mappingProcessor.trim()));
				} else {
					cellConfigBean.setMappingProcessor(SingleStringMappingProcessor.class);
				}
				if (StringUtils.isNotBlank(positionProcessor)) {
					cellConfigBean.setPositionProcessor(
							(Class<? extends PositionProcessor>) Class.forName(positionProcessor.trim()));
				}

				List<ValidatorConfigBean> validatorBeanConfigList = parseCellNode(element.element("validators"));
				cellConfigBean.setValidatorConfigBeanList(validatorBeanConfigList);
				cellConfigBeanList.add(cellConfigBean);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return cellConfigBeanList;
	}

	/**
	 * 解析validator节点
	 * @param validatorsElement
	 * @return
	 */
	private List<ValidatorConfigBean> parseCellNode(Element validatorsElement) {
		List<ValidatorConfigBean> validatorConfigBeanList = new ArrayList<ValidatorConfigBean>();
		try {
			if (null != validatorsElement) {
				List<Element> elements = validatorsElement.elements("validator");
				for (Element element : elements) {
					String type = element.attributeValue("type");
					String param = element.attributeValue("param");
					ValidatorConfigBean validatorConfigBean = new ValidatorConfigBean();
					validatorConfigBean.setType((Class<? extends Validatable>) Class.forName(type));
					validatorConfigBean.setParam(param);
					validatorConfigBeanList.add(validatorConfigBean);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return validatorConfigBeanList;
	}
}
