package com.iflytek.sgy.excel.test;

import com.alibaba.fastjson.JSONObject;
import com.iflytek.sgy.excel.parser.ExcelImporter;

/**
 * 
 * @author yangzhang7
 *
 */
public class Test1 {

	public static void main(String[] args) {

		Object parse = ExcelImporter.getInstance().write("D:\\附件4政务服务事项实施清单要素梳理表(1).xlsx",
				"C:\\Users\\zy\\workspace\\ztfw\\FlyExcel\\src\\main\\java\\config.xml");
		System.out.println(JSONObject.toJSONString(parse));

		parse = ExcelImporter.getInstance().write("D:\\附件4政务服务事项实施清单要素梳理表(1).xlsx",
				com.iflytek.sgy.excel.test.annotationbean.ExcelBean.class);
		System.out.println(JSONObject.toJSONString(parse));
	}
}
