package com.imzy.excel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.imzy.excel.enums.SheetType;
import com.imzy.excel.processer.ExitProcessor;

/**
 * sheet注解
 * @author yangzhang7
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Sheet {
	/** sheet名称*/
	String name();

	/**
	 *  sheet类型 <br>
	 *  类型见{@link com.imzy.excel.enums.SheetType}
	 */
	SheetType type();

	/** 数据开始行数*/
	int startLine() default -1;

	/** 数据开始列数*/
	char startColumn() default 0;

	/** 
	 * 退出处理器
	 */
	Class<? extends ExitProcessor> exitProcessor() default ExitProcessor.class;
}
