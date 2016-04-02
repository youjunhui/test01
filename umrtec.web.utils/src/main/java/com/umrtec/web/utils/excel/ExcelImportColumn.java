package com.umrtec.web.utils.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导入实体bean属性注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface ExcelImportColumn {

	/**
	 * 列号
	 * @return
	 */
	public int column();
	
	public boolean notNull() default false;
}
