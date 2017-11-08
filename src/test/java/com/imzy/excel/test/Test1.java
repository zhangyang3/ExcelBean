package com.imzy.excel.test;

import com.alibaba.fastjson.JSONObject;
import com.imzy.excel.parser.ExcelImporter;

/**
 * 
 * @author yangzhang7
 *
 */
public class Test1 {

	public static void main(String[] args) {
		xmlconfig();
		annotationconfig();
	}

	private static void xmlconfig() {
		Object parse = ExcelImporter.getInstance().write("D:\\附件4政务服务事项实施清单要素梳理表(1).xlsx",
				"C:\\Users\\zy\\workspace\\ztfw\\FlyExcel\\src\\main\\java\\config.xml");
		System.out.println(JSONObject.toJSONString(parse));

	}

	private static void annotationconfig() {
		Object parse2 = ExcelImporter.getInstance().write("D:\\附件4政务服务事项实施清单要素梳理表(1).xlsx",
				com.imzy.excel.test.annotationbean.ExcelBean.class);
		System.out.println(JSONObject.toJSONString(parse2));
	}
}
