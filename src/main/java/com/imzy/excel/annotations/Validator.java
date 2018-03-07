package com.imzy.excel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.imzy.excel.processer.ValidateProcessor;

/**
 * 校验器注解
 * @author yangzhang7
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {
	/** 校验器类型 **/
	Class<? extends ValidateProcessor> type();

	/** 校验器参数 */
	String[] param() default "";
}
