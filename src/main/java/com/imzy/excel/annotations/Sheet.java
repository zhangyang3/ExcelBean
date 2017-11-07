package com.imzy.excel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.imzy.excel.enums.SheetType;

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

	/** 顺序*/
	int order();

	/** 数据开始行数*/
	int startLine() default 1;
}
