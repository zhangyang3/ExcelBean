package com.imzy.excel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.imzy.excel.processer.ConvertProcessor;

/**
 * 转换器注解
 * @author yangzhang7
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Convertor {
	/** 转换器类型 **/
	Class<? extends ConvertProcessor> type();

	/** 转换器参数 */
	String[] param() default "";
}
