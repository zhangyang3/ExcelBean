package com.imzy.excel.test.xmlbean;

import java.util.List;

import com.imzy.excel.test.annotationbean.Sb;
import com.imzy.excel.test.annotationbean.Sqcldj;

import lombok.Data;

/**
 * 
 * @author yangzhang7
 *
 */
@Data
public class ExcelBean {

	private SsqdBasic ssqdBasic;

	private List<Special> specialList;

	private Sqcldj sqcldj;

	private List<Sb> sbList;

}
