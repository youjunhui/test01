package com.umrtec.web.utils.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Excel实体bean属性注解
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ExcelColumn {

	public String name();// Excel列名

	public int width();// Excel列宽

	public int order() default 10;// Excel列ID(决定顺序)
}
