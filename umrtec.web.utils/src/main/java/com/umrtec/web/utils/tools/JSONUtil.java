/*
 * 文 件 名  :  JSONUtil.java
 * 描    述    :  &lt;描述&gt;
 * 创建人    :  
 * 创建时间:   上午9:58:55
 */
package com.umrtec.web.utils.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 
 * @version [版本号, 2013-8-20 上午9:58:55]
 */
public class JSONUtil {
    /**  
     * 设置日期转换格式  
     */  
    static {   
        //注册器   
        MorpherRegistry mr = JSONUtils.getMorpherRegistry();   
  
        //可转换的日期格式，即Json串中可以出现以下格式的日期与时间   
        DateMorpher dm = new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"});   
        mr.registerMorpher(dm);   
    }  
	
	/*
	 * 将List转换为json
	 */
	@SuppressWarnings("rawtypes")
	public static String fromArray(List list) {
		String result = JSONArray.fromObject(list).toString();
		return result;
	}

	/*
	 * 将Object转换为json
	 */
	public static String fromObject(Object object) {
//		String result = JSONObject.fromObject(object).toString();
		String result  = JSONObject.fromObject(object, configJson("yyyy-MM-dd HH:mm:ss")).toString();
		return result;
	}

	/**
	 * 
	 * 将Object转换为json 述>
	 * 
	 * @param object
	 * @param json格式化配置信息
	 * @return [参数说明]
	 * @return String
	 * @exception throws [违例类型] [违例说明]
	 */
	public static String fromObject(Object object, JsonConfig jsonConfig) {
		String result = JSONObject.fromObject(object, jsonConfig).toString();
		return result;
	}

	// 普通类型的json转换成对象
	// 输入结果：json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){
	// return a; },array:[1,2]}
	// property=name
	// 输出结果： json
	public static String getValueByProperty(String json, String property) {

		try {
			JSONObject jsonObject = JSONObject.fromObject(json);
			// Object bean = JSONObject.toBean( jsonObject );
			return jsonObject.getString(property);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObjectJsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString,
				configJson("yyyy-MM-dd HH:mm:ss"));
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map getMapJson(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/**
	 * 从json数组中得到相应java数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArrayJson(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getListJson(String jsonString, Class pojoClass) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;
		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);

		}
		return list;

	}

	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Double[] getDoubleArrayJson(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);

		}
		return doubleArray;
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonStringJavaPOJO(Object javaObj) {
		JSONObject json;
		json = JSONObject
				.fromObject(javaObj, configJson("yyyy-MM-dd HH:mm:ss"));
		return json.toString();
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonStringJavaArray(Object javaObj) {
		JSONArray json;
		json = JSONArray.fromObject(javaObj, configJson("yyyy-MM-dd HH:mm:ss"));
		// json = JSONArray.fromObject(javaObj);
		return json.toString();
	}

	/**
	 * 将java对象转换成json字符串,并设定日期格式
	 * 
	 * @param javaObj
	 * @param dataFormat
	 * @return
	 */
	public static String getJsonStringJavaPOJO(Object javaObj, String dataFormat) {
		JSONObject json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json.toString();
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonStringJavaArray(Object javaObj,
			String dataFormat) {
		JSONArray json;
		json = JSONArray.fromObject(javaObj, configJson(dataFormat));
		return json.toString();
	}

	/**
	 * * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成方法存根
		// System.out.println(getJsonStringJavaPOJO(new GetDepartment(),null));
		// String
		// str="{'departmentCode':'201中医科','departmentName':'','departmentType':'','ghf':'','ghs':'','je':'','registerFlow':'','yhs':''}";
		// GetDepartment
		// obj=(GetDepartment)getObjectJsonString(str,GetDepartment.class);
		// System.out.println("=="+obj.getDepartmentCode());
		// ListVisitRec lis=new ListVisitRec();
		// lis.setPatientID("1111");
		// List<JZItem>Items=new ArrayList<JZItem>();
		// JZItem jz=new JZItem();
		// Items.add(jz);
		// jz.setBZ("666");
		// jz.setCFID("3018");
		// jz=new JZItem();
		// Items.add(jz);
		// jz.setBZ("777");
		// jz.setCFID("3019");
		//
		// System.out.println(getJsonStringJavaPOJO(jz,null));
		// String
		// str="{'errorMsg':'','items':[{'BZ':'777','CFID':'3019','CYBQDM':'','CYZD':'','GMSFHM':'','JZLB':'','JZRQ':'','JZYYBH':'','LXDH':'','YLFYZE':'','YYBH':'','regID':'','treaID':''}],'patientID':'1111','registerArea':'','resultCode':'','userID':''}";
		// // String
		// str="{'departmentCode':'201中医科','departmentName':'','departmentType':'','ghf':'','ghs':'','je':'','registerFlow':'','yhs':''}";
		// ListVisitRec
		// obj=(ListVisitRec)getObjectJsonString(str,ListVisitRec.class);
		// System.out.println("=="+obj.getPatientID());
		// YBKVo
		// obj=(YBKVo)getObjectJsonString("{'ok':false,'msg':'abd','data':1111}",YBKVo.class);
		// System.out.println(obj.getOk()+"   ==  "+obj.getData());

		// String json =
		// "{'fuserid':9722771,'fphonemodel':'HTC ONE M7','fphoneostype':1,'fphoneos':'android','fappcode':1,'fappname':'患者手机客户端','finterfacecode':'1003','finterfacename':'手机注册','fusetime':'2013-10-22 15:13:43','fphonenumber':'158123456789','fequipmentid':'HU-10398199-1','fhospitalid':966728,'fcardtypecode':'1','fcardtypename':'诊疗卡','fcardno':'123123123','fname':'Jack Chen','fidnumer':'47781289376178299283','femail':'22321321@qq.com','fmodifytime':'2013-10-22 15:13:43'}";
		// Tpreuser vo = (Tpreuser) getObjectJsonString(json, Tpreuser.class);
		// Tuserpatient obj1 = ((Tuserpatient) getObjectJsonString(json,
		// Tuserpatient.class));
		// obj1.initID();
		// Tprepatientcard obj2 = (Tprepatientcard) getObjectJsonString(json,
		// Tprepatientcard.class);
		// obj2.initID();
		// System.out.println();
		//
		// Tprenoticemain notice = new Tprenoticemain(2121l, "jack chen",
		// 1988271l, "123123", "8727632", "REG877128", "1", "09", "外科", "09",
		// "外科","二楼", "1", "提醒","请到外科2诊室就诊", "1", "ADC", new Date(), new Date(),
		// new Date(), 2, 2, 12313l);
		//
		// // OutputParam obj = new OutputParam("1", "成功", "123123123");
		// String json = getJsonStringJavaPOJO(notice);
		// System.out.println(json);
		//
		// String ss =
		// "{\"fuserid\":\"\",\"result\":\"100\",\"resultinfo\":\"成功\"}";
		// getObjectJsonString(ss, OutputParam.class);

		String tt = "[{'detail':[],'fagaintime':null,'fage':'23岁 ','fapplytime':'2013-10-30 05:42:08','fbarcodeno':'123123','fcardno':'100289772','fcheckdoctorcode':'03','fcheckdoctorname':'赵检查员','fcombinecode':'98772mz','fcombinename':'血常规三项','fdeptcode':'09','fdeptname':'内科','fdiagnosecode':'32123','fdiagnosename':'干嘛','fdoctorcode':'98','fdoctorname':'王医生','fgettime':'2013-10-30 05:42:08','fisaccept':null,'fisagain':null,'fisread':null,'flisdetptcode':'201','flisdetptname':'检验科','flisdoctorcode':'72','flisdoctorname':'刘检验员','flismainid':'123123','fname':'张三','fpatientid':7682817,'fpatienttypecode':'1','fpatienttypename':'病人类型','freadtime':null,'fregmainid':'reg87281901','freportdoctorcode':'21','freportdoctorname':'李报告员','freportinfo':'检验报告最终评语','freportno':'23211','freportremark':'检验报告备注','freporttime':'2013-10-30 05:42:08','fsampleno':'23123','fsampleplace':'07','fsamplestate':'标本状态名称','fsampletime':'2013-10-30 05:42:08','fsampletypecode':'01','fsampletypename':'血清','fsavetime':null,'fseqno':'123123','fsexcode':'1','fsexname':'男','fuserid':null}]";
		// List<Tprelisreportmain> list = getListJson(tt,
		// Tprelisreportmain.class);
		// System.out.println(list.size());
	}

	/**
	 * JSON 时间解析器具
	 * 
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));
		jsonConfig.registerDefaultValueProcessor(Integer.class,
				new DefaultValueProcessor() {
					public Object getDefaultValue(Class type) {
						return null;
					}
				});
		jsonConfig.registerDefaultValueProcessor(Double.class,
				new DefaultValueProcessor() {
					public Object getDefaultValue(Class type) {
						return null;
					}
				});
		jsonConfig.registerDefaultValueProcessor(Long.class,
				new DefaultValueProcessor() {
					public Object getDefaultValue(Class type) {
						return null;
					}
				});

		return jsonConfig;
	}

	/**
	 * 
	 * @param excludes
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));
		return jsonConfig;
	}

	static class DateJsonValueProcessor implements JsonValueProcessor {
		public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
		private DateFormat dateFormat;

		/**
		 * 构造方法.
		 * 
		 * @param datePattern
		 *            日期格式
		 */
		public DateJsonValueProcessor(String datePattern) {
			if (null == datePattern)
				dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
			else
				dateFormat = new SimpleDateFormat(datePattern);
		}

		/*
		 * （非 Javadoc）
		 * 
		 * @see
		 * net.sf.json.processors.JsonValueProcessor#processArrayValue(java.
		 * lang.Object, net.sf.json.JsonConfig)
		 */
		public Object processArrayValue(Object arg0, JsonConfig arg1) {
			// TODO 自动生成方法存根
			return process(arg0);
		}

		/*
		 * （非 Javadoc）
		 * 
		 * @see
		 * net.sf.json.processors.JsonValueProcessor#processObjectValue(java
		 * .lang.String, java.lang.Object, net.sf.json.JsonConfig)
		 */
		public Object processObjectValue(String arg0, Object arg1,
				JsonConfig arg2) {
			// TODO 自动生成方法存根
			return process(arg1);
		}

		private Object process(Object value) {
			if (value != null)
				return dateFormat.format((Date) value);
			else
				return value;
		}
	}

}
