package com.imzy.excel.support;

/**
 * 应用常量
 * @author yangzhang7
 *
 */
public interface ExcelBeanConst {

	/** xml文件*/
	public interface XmlFile {
		/** 节点*/
		public interface Node {
			String EXCEL = "excel";
			String SHEET = "sheet";
			String CELL = "cell";
			String VALIDATOR = "validator";
			String CONVERTOR = "convertor";
		}

		/** 属性*/
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
			String START_COLUMN = "startColumn";
		}
	}

	/** 后缀*/
	public interface Suffix {

		String XLS = "xls";
		String XLSX = "xlsx";
	}

	public interface Number {
		/** "-1" */
		String NEGATIVE_STR = "-1";
		/** "0"*/
		String ZERO_STR = "0";
		/** "1"*/
		String ONE_STR = "1";
		/** "2"*/
		String TWO_STR = "2";
		/** "3"*/
		String THREE_STR = "3";
		/** "4"*/
		String FOUR_STR = "4";
		/** "5"*/
		String FIVE_STR = "5";

		/** -1*/
		int NEGATIVE_INT = -1;
		/** 0*/
		int ZERO_INT = 0;
		/** 1*/
		int ONE_INT = 1;
		/** 2*/
		int TWO_INT = 2;
		/** 3*/
		int THREE_INT = 3;
		/** 4*/
		int FOUR_INT = 4;
		/** 5*/
		int FIVE_INT = 5;
		/** 6*/
		int SIX_INT = 6;
	}
}
