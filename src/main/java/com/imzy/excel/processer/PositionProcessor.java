package com.imzy.excel.processer;

/**
 * 位置处理器
 * @author yangzhang7
 *
 */
public interface PositionProcessor {

	/** 开始x坐标*/
	Character getStartX();

	/** 结束x坐标*/
	Character getEndX();

	/** 开始y坐标*/
	Integer getStartY();

	/** 结束y坐标*/
	Integer getEndY();

}
