package com.iflytek.sgy.excel.parser.sheet;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 坐标，包含2个坐标
 * @author yangzhang7
 *
 */
@Data
@AllArgsConstructor
public class Point {

	private Character startX;

	private Integer startY;

	private Character endX;

	private Integer endY;

}
