/*
 * 文 件 名  :  AlgorithmUtil.java
 * 描    述    :  <描述>
 * 创建人    :  youjunhui modify third
 * 创建时间:  上午8:19:42
 */
package com.umrtec.web.utils.tools;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author youjunhui
 * 
 */

public class AlgorithmUtil {

	/**
	 * (通用算法) 递归算法: 将list转换为树形json串 
	 * treeID : 获取以treeID为父节点的树形结构 
	 * list : 数据 
	 * map : map.put("id","自己类中定义的对应属性") map.put("text","自己类中定义的对应属性") map.put("parentID","自己类中定义的对应属性") 
	 * result : 返回的结果
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String recursionTree(long treeID, List list, Map<String, String> map, StringBuffer result) {
		try {
			if (list == null) {
				return "";
			} else {
				for (int i = 0; i < list.size(); ++i) {
					Object obj = list.get(i);
					Class cla = obj.getClass();

					String propertyParentID = map.get("parentID");
					Method method = cla.getMethod(getPropertyMethod(propertyParentID));
					Long propertyParentIDValue = (Long) method.invoke(obj);

					String propertyID = map.get("id");
					Method methodID = cla.getMethod(getPropertyMethod(propertyID));
					Long propertyIDValue = (Long) methodID.invoke(obj);

					String propertyText = map.get("text");
					Method methodText = cla.getMethod(getPropertyMethod(propertyText));
					String text = (String) methodText.invoke(obj);

					if (propertyParentIDValue == treeID) {
						int count = 0; // 查看以deviceId为parentID的记录有多少条
						for (int n = 0; n < list.size(); ++n) {
							Object objInner = list.get(n);
							Long parentID = (Long) method.invoke(objInner);

							if (parentID == propertyIDValue) {
								count++;
							}
						}
						result.append("{");
						result.append("\"id\":" + propertyIDValue + ",");
						if (count == 0) {
							result.append("\"text\":" + "\"" + text + "\"");
						} else {
							result.append("\"text\":" + "\"" + text + "\",");
							result.append("\"state\":" + "\"closed\"" + ",");
							result.append("\"children\":[");
							recursionTree(propertyIDValue, list, map, result);
							result.append("]");
						}
						result.append("}");
					}
				}
				return result.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * (通用算法) 递归算法: 将list转换为树形json串 treeID : 获取以treeID为父节点的树形结构 list : 数据 map : map.put("id","自己类中定义的对应属性") map.put("text","自己类中定义的对应属性") map.put("parentID","自己类中定义的对应属性") result : 返回的结果
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String recursionTree(int treeID, List list, Map<String, String> map, StringBuffer result , boolean flag) {
		try {
			if (list == null) {
				return "";
			} else {
				for (int i = 0; i < list.size(); ++i) {
					Object obj = list.get(i);
					Class cla = obj.getClass();

					String propertyParentID = map.get("parentID");
					Method method = cla.getMethod(getPropertyMethod(propertyParentID));
					Integer propertyParentIDValue = (Integer) method.invoke(obj);

					String propertyID = map.get("id");
					Method methodID = cla.getMethod(getPropertyMethod(propertyID));
					Integer propertyIDValue = (Integer) methodID.invoke(obj);

					String propertyText = map.get("text");
					Method methodText = cla.getMethod(getPropertyMethod(propertyText));
					String text = (String) methodText.invoke(obj);

					if (propertyParentIDValue == treeID) {
						int count = 0; // 查看以deviceId为parentID的记录有多少条
						for (int n = 0; n < list.size(); ++n) {
							Object objInner = list.get(n);
							Integer parentID = (Integer) method.invoke(objInner);

							if (parentID == propertyIDValue) {
								count++;
							}
						}
						result.append("{");
						result.append("\"id\":" + propertyIDValue + ",");
						if (count == 0) {
							result.append("\"text\":" + "\"" + text + "\"");
						} else {
							result.append("\"text\":" + "\"" + text + "\",");
							result.append("\"state\":" + "\"closed\"" + ",");
							result.append("\"children\":[");
							recursionTree(propertyIDValue, list, map, result , true);
							result.append("]");
						}
						result.append("}");
					}
				}
				return result.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 获取属性的get方法
	 * */
	public static String getPropertyMethod(String property) {
		if (property == null) {
			return "";
		} else {
			if (property.equals("")) {
				return "";
			} else {
				char firstChar = property.charAt(0);
				char firstUpperChar = new String(firstChar + "").toUpperCase().charAt(0);
				String result = "get" + firstUpperChar + property.substring(1);
				return result;
			}
		}
	}

}
