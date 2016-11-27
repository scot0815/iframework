package com.scot.iframework.timeTask.annotation;

import java.lang.annotation.*;

/**
 * 用于解释和说明类、方法、属性.
 * Created by shengke on 2016/11/25.
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Comment {

	/**
	 * 注解内容.
	 * @return	注解内容
	 */
	String value() default "";
}
