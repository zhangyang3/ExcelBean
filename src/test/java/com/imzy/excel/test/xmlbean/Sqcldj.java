package com.imzy.excel.test.xmlbean;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author yangzhang7
 *
 */
@Data
public class Sqcldj {

	private String xm;
	private String sxmc;
	private String sxlb;
	private String xhmc;
	private List<SqcldjHori> sqcldjHoriList;
}
