/*
 * 文 件 名  :  JsonConfigUtil.java
 * 描    述    :  &lt;描述&gt;
 * 创建人    :  
 * 创建时间:   上午9:29:01
 */
package com.umrtec.web.utils.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 配置对象的默认json格式
 * 
 * @author 
 * @version [版本号, 2013-8-28 上午9:29:01]
 */
public class JSONConfigUtil {

	/**
	 * 替换java.util.Date的默认json格式
	 * */
	public static JsonConfig formatDate(final String pattern) {
		JsonConfig cfg = new JsonConfig();
		
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			
			private final String format = pattern;

			public Object processObjectValue(String key, Object value, JsonConfig arg2) {
				if (value == null)
					return "";
				if (value instanceof Date) {
					String str = new SimpleDateFormat(format).format((Date) value);
					return str;
				}
				return value.toString();
			}

			public Object processArrayValue(Object value, JsonConfig arg1) {
				return null;
			}
		});
		
		return cfg;
	}

}
