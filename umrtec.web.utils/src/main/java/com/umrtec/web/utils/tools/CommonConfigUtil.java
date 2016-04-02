/*
 * 文 件 名  :  CommonConfigUtil.java
 * 描    述    :  <描述>
 * 创建人    :  
 * 创建时间:  下午4:10:52
 */
package com.umrtec.web.utils.tools;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author 
 * @version [版本号, 2013-7-1]
 */
public class CommonConfigUtil {
	// 对应资源文件commonconfig.properties
	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("commonconfig");

	/**
	 * 通过键获取值
	 * 
	 * @author 
	 * @param key
	 * @return [参数说明]
	 * @return String [返回类型说明]
	 */
	public static final String get(String key) {
		try {
			return bundle.getString(key);
		} catch (Exception e) {
			return null;
		}

	}
}
