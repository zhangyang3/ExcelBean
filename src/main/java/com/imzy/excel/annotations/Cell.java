package com.imzy.excel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.imzy.excel.enums.CellType;
import com.imzy.excel.processer.ExistProcessor;
import com.imzy.excel.processer.MappingProcessor;
import com.imzy.excel.processer.PositionProcessor;
import com.imzy.excel.processer.mapping.SingleStringMappingProcessor;

/**
 * cell注解
 * @author yangzhang7
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Cell {
	/** 单元格名称*/
	String name() default "";

	/** X开始*/
	char startX() default 0;

	/** X结束*/
	char endX() default 0;

	/** Y开始*/
	int startY() default -1;

	/** Y结束*/
	int endY() default -1;

	/** 值校验器*/
	Validator[] validators() default {};

	/** 
	 * 值映射器<br>
	 * 默认值：SingleStringMappingProcessor.class {@link com.imzy.excel.processer.mapping.SingleStringMappingProcessor}
	 */
	Class<? extends MappingProcessor> mappingProcessor() default SingleStringMappingProcessor.class;

	/**
	 * 单元格类型
	 * 默认值：CellType.SINGLEVALUE {@link com.imzy.excel.enums.CellType}
	 */
	CellType cellType() default CellType.SINGLEVALUE;

	/**
	 * 退出处理器<br>
	 * CellType为CellType.HORIZONTAL时，需配置
	 */
	Class<? extends ExistProcessor> existProcessor() default ExistProcessor.class;

	/**
	 * 坐标处理器
	 * 默认值：PositionProcessor.class {@link com.imzy.excel.processer.PositionProcessor}
	 */
	Class<? extends PositionProcessor> positionProcessor() default PositionProcessor.class;

}
