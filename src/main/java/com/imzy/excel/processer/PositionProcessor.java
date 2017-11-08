package com.imzy.excel.processer;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 位置处理器
 * @author yangzhang7
 *
 */
public interface PositionProcessor {

	/** 开始x坐标
	 * @param sheet */
	Character getStartX(Sheet sheet);

	/** 结束x坐标
	 * @param sheet */
	Character getEndX(Sheet sheet);

	/** 开始y坐标
	 * @param sheet */
	Integer getStartY(Sheet sheet);

	/** 结束y坐标
	 * @param sheet */
	Integer getEndY(Sheet sheet);

}
