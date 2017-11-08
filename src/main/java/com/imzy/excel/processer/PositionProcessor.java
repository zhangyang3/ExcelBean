package com.imzy.excel.processer;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 位置处理器
 * @author yangzhang7
 *
 */
public interface PositionProcessor {

	/**
	 * 开始x坐标
	 * @param sheet
	 * @return
	 */
	Character getStartX(Sheet sheet);

	/**
	 * 结束x坐标
	 * @param sheet
	 * @return
	 */
	Character getEndX(Sheet sheet);

	/**
	 * 开始y坐标
	 * @param sheet
	 * @return
	 */
	Integer getStartY(Sheet sheet);

	/**
	 * 结束y坐标
	 * @param sheet
	 * @return
	 */
	Integer getEndY(Sheet sheet);

}
