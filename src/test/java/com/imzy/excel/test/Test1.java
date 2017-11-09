package com.imzy.excel.test;

import com.alibaba.fastjson.JSONObject;
import com.imzy.excel.parser.ExcelImporter;

/**
 * 
 * @author yangzhang7
 *
 */
public class Test1 {

	private static String filePath = "D:\\附件4政务服务事项实施清单要素梳理表(1).xlsx";

	public static void main(String[] args) {
		xmlconfig();
		annotationconfig();
	}

	private static void xmlconfig() {
		Object parse = ExcelImporter.getInstance().write(Test1.filePath,
				Test1.class.getResource("config.xml").getPath());
		System.out.println(JSONObject.toJSONString(parse));

	}

	private static void annotationconfig() {
		Object parse2 = ExcelImporter.getInstance().write(Test1.filePath,
				com.imzy.excel.test.annotationbean.ExcelBean.class);
		System.out.println(JSONObject.toJSONString(parse2));
	}
}
