package com.iflytek.sgy.excel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.iflytek.sgy.excel.processer.MappingProcessor;
import com.iflytek.sgy.excel.processer.PositionProcessor;
import com.iflytek.sgy.excel.processer.mapping.SingleStringMappingProcessor;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Cell {
	/** 单元格名称*/
	String name() default "";

	/** X开始*/
	char startX() default 'a';

	/** X结束*/
	char endX() default 'a';

	/** Y开始*/
	int startY() default -1;

	/** Y结束*/
	int endY() default -1;

	/** 值校验器*/
	Validator[] validators() default {};

	/** 值映射器*/
	Class<? extends MappingProcessor> mappingProcessor() default SingleStringMappingProcessor.class;

	/** X开始坐标处理器*/
	Class<? extends PositionProcessor> startXPositionProcesser() default PositionProcessor.class;

	/** X结束坐标处理器*/
	Class<? extends PositionProcessor> endXPositionProcesser() default PositionProcessor.class;

	/** Y开始坐标处理器*/
	Class<? extends PositionProcessor> startYPositionProcesser() default PositionProcessor.class;

	/** Y结束坐标处理器*/
	Class<? extends PositionProcessor> endYPositionProcesser() default PositionProcessor.class;
}
