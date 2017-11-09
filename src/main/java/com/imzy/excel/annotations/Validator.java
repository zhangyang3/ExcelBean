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

	Class<? extends ValidateProcessor> type();

	String param() default "";
}
