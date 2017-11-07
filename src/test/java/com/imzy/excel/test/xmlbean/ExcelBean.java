package com.imzy.excel.test.xmlbean;

import java.util.List;

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
}
