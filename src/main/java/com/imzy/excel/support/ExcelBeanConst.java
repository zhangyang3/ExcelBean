package com.imzy.excel.support;

/**
 * 应用常量
 * @author yangzhang7
 *
 */
public interface ExcelBeanConst {

	public interface XmlFile {

		public interface Namespace {
			String EXCEL = "excel";
			String SHEET = "sheet";
			String CELL = "cell";
			String VALIDATOR = "validator";
		}

		public interface Attribute {
			String NAME = "name";
			String CLASS = "class";
			String TYPE = "type";
			String FIELD_NAME = "fieldName";
			String START_X = "startX";
			String END_X = "endX";
			String START_Y = "startY";
			String END_Y = "endY";
			String MAPPING_PROCESSOR = "mappingProcessor";
			String EXIST_PROCESSOR = "existProcessor";
			String POSITION_PROCESSOR = "positionProcessor";
			String CELL_TYPE = "cellType";
			String START_LINE = "startLine";
			String PARAM = "param";
		}
	}

	public interface Appendix {

		String XLS = "xls";
		String XLSX = "xlsx";

	}

	/** 列长度*/
	int COLUMN_LENGTH = 2;
}
