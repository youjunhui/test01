/*
 * 文 件 名  :  StringUtil.java
 * 描    述    :  <描述>
 * 创建人    :  
 * 创建时间:  下午5:05:39
 */
package com.umrtec.web.utils.tools;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author 
 *
 */
public class StringUtil 
{
	/**
	 * 判断是否为空（包含两种情况 1：null,2:空串）
	 * */
    public static boolean isNotEmpty(String str)
    {
    	if(str == null)
    	{
    		return false;
    	}else{
    		if(str.equals(""))
    		{
    			return false;
    		}else{
    			return true;
    		}
    	}
    }
    
    public static boolean isEmpty(String str){
    	return !(isNotEmpty(str));
    }
    
    /**
     * 将分辨率（width*height）转换成一个整数
     * 高16位是由width组成
     * 低16位是由height组成
     * */
    public static int convertResolutionToNumber(String resolution)
    {
    	if(resolution.indexOf("*") != -1)
    	{
    		String[] strs = resolution.split("\\*");
    		int width = Integer.parseInt(strs[0]);
    		int height = Integer.parseInt(strs[1]);
    		return IntegerUtil.convertResolution(width, height);
    	}else{
    		return 0;
    	}
    }
    
    /**
	 * 
	 * 格式化文件大小，B,KB,MB,GB
	 * @param fileSize
	 * @return [参数说明]
	 * @return String 
	 * @exception throws [违例类型] [违例说明]
	 */
	public static String formatFileSize(long fileSize) {
		BigDecimal unit = new BigDecimal(1024);
		BigDecimal target = new BigDecimal(fileSize);
		
		if(fileSize < 1024L){
			return fileSize+"B";
		}else if(fileSize < 1024L*1024L){
			BigDecimal result = target.divide(unit);
			return result.setScale(2 , BigDecimal.ROUND_HALF_UP).toString() +"KB";
		}else if(fileSize < 1024L*1024L*1024L){
			BigDecimal result = target.divide(unit.multiply(unit));
			return result.setScale(2 , BigDecimal.ROUND_HALF_UP).toString() +"MB";
		}else if(fileSize < 1024L*1024L*1024L*1024L){
			BigDecimal result = target.divide(unit.multiply(unit).multiply(unit));
			return result.setScale(2 , BigDecimal.ROUND_HALF_UP).toString() +"GB";
		}else if(fileSize < 1024L*1024L*1024L*1024L*1024L){
			BigDecimal result = target.divide(unit.multiply(unit).multiply(unit).multiply(unit));
			return result.setScale(2 , BigDecimal.ROUND_HALF_UP).toString() +"TB";
		}else if(fileSize < 1024L*1024L*1024L*1024L*1024L*1024L){
			BigDecimal result = target.divide(unit.multiply(unit).multiply(unit).multiply(unit).multiply(unit));
			return result.setScale(2 , BigDecimal.ROUND_HALF_UP).toString() +"PB";
		}else{
			return "too large";
		}
		
	}

	/**
	 * 产生随机N位密码
	 * 返回的是源码，不是加密后的
	 * @return [参数说明]
	 * @return String 
	 * @exception throws [违例类型] [违例说明]
	 */
    public static String generateRandomPassword(int passwordLength){
    	Random random = new Random();
    	String  source = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijklmnpqrstuvwxyz!@#$%^&*";
    	int length = source.length();
    	StringBuffer result = new StringBuffer();
    	for(int i = 0 ; i < passwordLength ; ++i){
    		int code = random.nextInt(length - 1);
    		result.append(source.charAt(code));
    	}
    	
    	return result.toString();
    }
	
    /**
	 * 产生随机N位数字密码
	 * 返回的是源码，不是加密后的
	 * @return [参数说明]
	 * @return String 
	 * @exception throws [违例类型] [违例说明]
	 */
    public static String generateRandomNumberPassword(int passwordLength){
    	Random random = new Random();
    	String  source = "1234567890";
    	int length = source.length();
    	StringBuffer result = new StringBuffer();
    	for(int i = 0 ; i < passwordLength ; ++i){
    		int code = random.nextInt(length - 1);
    		result.append(source.charAt(code));
    	}
    	
    	return result.toString();
    }
    
    public static void main(String[] args)
    {
    	for(int i = 0 ; i < 1000 ; ++i){
    		System.out.println(generateRandomNumberPassword(6));
    	}
    	
    }
    
}
