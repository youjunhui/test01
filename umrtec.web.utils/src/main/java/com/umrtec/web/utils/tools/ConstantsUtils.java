/*
 * 文 件 名  :  ConstantsUtils.java
 * 描    述    :  <描述>
 * 创建人    :  
 * 创建时间:  下午4:52:24
 */
package com.umrtec.web.utils.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 基础应用平台工具类
 * 
 * @author 
 * @version [版本号, 2013-6-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConstantsUtils {
	/***
	 * 判断集合是否为空
	 * 
	 * @author 
	 * @param List
	 * @return [参数说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	public static <T> boolean isEmpty(List<T> list) {
		return (null == list) || (list.size() == 0);
	}

	/***
	 * 判断集合是否不为空
	 * 
	 * @author 
	 * @param List
	 * @return [参数说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	public static <T> boolean isNotEmpty(List<T> list) {
		return !isEmpty(list);
	}

	/***
	 * 判断集合是否为空
	 * 
	 * @author 
	 * @param List
	 * @return [参数说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	public static <T> boolean isEmpty(Set<T> list) {
		return (null == list) || (list.size() == 0);
	}

	/***
	 * 判断集合是否不为空
	 * 
	 * @author 
	 * @param List
	 * @return [参数说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	public static <T> boolean isNotEmpty(Set<T> list) {
		return !isEmpty(list);
	}

	/**
	 * 
	 * 判断map集合是否为空
	 * 
	 * @author 
	 * @param map
	 * @return [参数说明]
	 * @return boolean [返回类型说明]
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (null == map) || (map.size() == 0);
	}

	/**
	 * 
	 * 判断map集合是否不为空
	 * 
	 * @author 
	 * @param map
	 * @return [参数说明]
	 * @return boolean [返回类型说明]
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !(isEmpty(map));
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @author 
	 * @param str
	 * @return [参数说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	public static boolean isNotBank(String str) {
		if (null != str && !StringUtils.isBlank(str))
			return true;
		return false;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @author 
	 * @param str
	 * @return [参数说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	public static boolean isBank(String str) {
		return !isNotBank(str);
	}

	/**
	 * 将以","号分隔开的整形字符串转换为一个List<Long>集合返回 如传入:"1,2,4,53,22"
	 * 
	 * @author 
	 * @param ids
	 * @return [参数说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	public static List<Long> string2LongList(String ids) {
		List<Long> idList = new ArrayList<Long>();
		if (isNotBank(ids)) {
			String[] strArray = ids.split(",");
			for (String str : strArray) {
				if (isNotBank(str)) {
					idList.add(Long.valueOf(str));
				}
			}
		}
		return idList;
	}
	
	/**
	 * 将以","号分隔开的整形字符串转换为一个List<Long>集合返回 如传入:"1,2,4,53,22"
	 * 
	 * @author 
	 * @param ids
	 * @return [参数说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	public static List<String> string2StringList(String ids) {
		List<String> idList = new ArrayList<String>();
		if (isNotBank(ids)) {
			String[] strArray = ids.split(",");
			for (String str : strArray) {
				if (isNotBank(str)) {
					idList.add(str);
				}
			}
		}
		return idList;
	}

	/**
	 * 判断一个字符是否是字母
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChar(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
	}

	/**
	 * 判断一个数字是否是数字
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}

	/**
	 * 将List中的元素拼接成字符串，拼接符为split
	 * 
	 * @param list
	 * @param split
	 * @return
	 */
	public static <T> String list2String(List<T> list, String split) {
		if (isEmpty(list))
			return "";
		StringBuffer sb = new StringBuffer();
		for (T t : list) {
			sb.append("'"+t.toString() + "'");
			sb.append(split);
		}
		return sb.substring(0, sb.length() - split.length());
	}
}
