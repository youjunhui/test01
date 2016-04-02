/*
 * 文 件 名  :  GlobalStatic.java
 * 描    述    :  <描述>
 * 创建人    :  
 * 创建时间:  下午3:37:55
 */
package com.umrtec.web.utils.domain;

import com.umrtec.web.utils.tools.CommonConfigUtil;
import com.umrtec.web.utils.tools.ConstantsUtils;

/**
 * 公共静态变量定义接口
 * 
 * @author 
 * @version [版本号, 2013-6-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface GlobalStatic {
	/**
	 * 查询数据库类型：QBC
	 */
	public final static int QUERY_TYPE_QBC = 1;

	/**
	 * 查询数据库类型：HQL
	 */
	public final static int QUERY_TYPE_HQL = 2;

	/**
	 * 查询数据库类型：原生SQL
	 */
	public final static int QUERY_TYPE_SQL = 3;

	/**
	 * 查询数据库类型：存储过程
	 */
	public final static int QUERY_PROC_SQL = 4;

	/**
	 * 存放userId的session的key
	 */
	public final static String USER_SESSION_ID = "userId";

	/**
	 * 存入loginUser用户信息的Session中的Key
	 */
	public final static String USER_SESSION_OBJECT = "loginUser";

	/**
	 * 存入loginUserPrivilege用户信息的Session中的Key入
	 */
	public final static String USER_SESSION_PRIVILEGE = "loginUserPrivilege";

	/**
	 * 记录启用标记，可标记数据记录存在
	 */
	public final static String ENABLE_FLAG = "Y";

	/**
	 * 记录不启用，可标记数据记录是软删除状态
	 */
	public final static String DISABLE_FLAG = "N";

	/**
	 * 标记记录有效
	 */
	public final static String VALIDITY_FLAG = "Y";

	/**
	 * 标记记录无效
	 */
	public final static String INVALIDITY_FLAG = "N";

	/**
	 * 字段：可用性
	 */
	public final static String FIELD_ENABLE_FLAG = "enableFlag";

	/**
	 * 字段：有效性
	 */
	public final static String FIELD_VALIDITY_FLAG = "validityFlag";

	/**
	 * 最后更新时间
	 */
	public final static String LAST_UPDATED_DATE = "lastUpdatedDate";

	/**
	 * 最后更新人
	 */
	public final static String LAST_UPDATED_BY = "lastUpdatedBy";

	/**
	 * 创建时间
	 */
	public final static String CREATED_DATE = "createdDate";

	/**
	 * 创建人
	 */
	public final static String CREATED_BY = "createdBy";

	/**
	 * 查询条件：当前页数的Map中的Key
	 */
	public static final String QUERY_CONDITION_CUR_PAGE = "curPage";

	/**
	 * 查询条件：每页记录条数的Map中的Key
	 */
	public static final String QUERY_CONDITION_PAGE_SIZE = "pageSize";

	/**
	 * 查询条件：当前页的记录数的Map中的Key
	 */
	public static final String QUERY_CONDITION_ROWS = "rows";

	/**
	 * 查询条件：当前页的Map中的Key
	 */
	public static final String QUERY_CONDITION_PAGE = "page";

	/**
	 * 查询条件：排序字段的Map中的Key
	 */
	public static final String QUERY_CONDITION_SORT = "sort";

	/**
	 * 查询条件：排序方式的Map中的Key
	 */
	public static final String QUERY_CONDITION_ORDER = "order";

	/**
	 * order by
	 */
	public static final String ORDER_BY = " order by ";

	/**
	 * 实体类对象
	 */
	public static final String CLASS_OBJECT = "classObject";

	/***
	 * 操作类型
	 */
	public static final String OPERATION_TYPE = "operationType";

	/**
	 * 是否有效 0:有效 1:无效
	 */
	public static final String STATUS_CODE = "statusCode";

	/**
	 * 软删除标记 0:正常状态 1:删除状态
	 */
	public static final String DELETE_FLAG = "deleteFlag";

	/**
	 * 树展开状态
	 */
	public static final String OPEN = "open";

	/**
	 * 父节点id
	 */
	public static final String PARENT_ID = "pId";

	/**
	 * 父节点名称
	 */
	public static final String PARENT_NAME = "pName";

	/**
	 * 主键id
	 */
	public static final String ID = "id";

	/***
	 * 常量值-1
	 */
	public static final int INT_VALUE_01 = -1;
	
	/***
	 * 常量值0
	 */
	public static final int INT_VALUE_0 = 0;

	/***
	 * 常量值1
	 */
	public static final int INT_VALUE_1 = 1;

	/***
	 * 常量值2
	 */
	public static final int INT_VALUE_2 = 2;

	/***
	 * 常量值3
	 */
	public static final int INT_VALUE_3 = 3;

	/**
	 * 默认的每页记录数
	 */
	public static final int PAGE_SIZE = 10;

	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	/**
	 * Spring加密属性文件的密钥
	 */
	public static final String SPRING_ENCRYPT_KEY = "wbaobei";

	/**
	 * 有异常时，给界面的提示信息
	 */
	public static final String SYSTEM_ERROR = "系统出错,请与管理员联系.";

	/**
	 * 系统默认语言
	 */
	static String DEFAULTLANGUAGE = "zh_CN";
	/**
	 * cookie保存时间 默认一天 60*60*24
	 */
	static int COOKIETIME = 86400;

	/**
	 * 数据库主键标识列存储过程名称
	 */
	public static final String ACT_PROC_GET_INDEX_PROC = "act_proc_get_index";

	/**
	 * 默认公司ID
	 */
	public static final int DEFAULT_COMPANYID_VALUE = 0;

	/**
	 * 错误界面逻辑视图
	 */
	public static final String ERROR_PAGE_STRING = "errorMsg";

	/**
	 * 错误界面信息提示
	 */
	public static final String ERROR_MSG = "非代理商无权操作此界面,请与管理员联系.";

	/**
	 * 日期格式
	 */
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 验证码
	 */
	public static final String RANDOM_VALIDATE_CODE_KEY = "RANDOMVALIDATECODEKEY";

	/**
	 * 项目根据经
	 */
	public static final String ROOT_PATH = CommonConfigUtil.get("rootPath");
	/**
	 * 支付名称
	 */
	public static final String PAYMENT_NAME = "线下汇款支付";
	/**
	 * 有效天数
	 */
	public static final int VALID_DAY = 370;
	/**
	 * 产品属性名称
	 */
	public static final String PROPERTY_NAME = "LicenseInfo";
	/**
	 * 部门类型
	 */
	public static final String D_TYPE = "dType";
	/***
	 * 常量值0
	 */
	public static final String STR_VALUE_0 = "0";

	/***
	 * 常量值1
	 */
	public static final String STR_VALUE_1 = "1";
	/**
	 * 代理商ID
	 */
	public static final Long AGENTID = ConstantsUtils.isBank(CommonConfigUtil
			.get("AgentID")) ? 0L : Long.parseLong(CommonConfigUtil
			.get("AgentID"));

	public static final int PROMOTION_CODE = 134217728;
	
	public static final String MATERIAL_URL_PREFIX = CommonConfigUtil.get("material.url.prefix");
}
