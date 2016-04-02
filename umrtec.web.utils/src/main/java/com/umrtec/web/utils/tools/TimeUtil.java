/*
 * 文 件 名  :  TimeUtil.java
 * 描    述    :  <描述>
 * 创建人    :  
 * 创建时间:  下午5:40:50
 */
package com.umrtec.web.utils.tools;

/**
 * @author 
 *
 */
public class TimeUtil 
{
	/**
	 * 将秒格式化为时间 00:00:00
	 * */
    public static String formatSeconds(int seconds)
    {
    	if(seconds < 60)
    	{
    		return "00:00:"+seconds;
    	}else if(seconds < 60*60){
    		int minutes = seconds/60;
    		int second = seconds%60;
    		return "00:"+formatPart(minutes)+":"+formatPart(second);
    	}else if(seconds < 24*60*60){
    		int hours = seconds/(60*60);
    		int minutes = (seconds - hours*60*60)/60;
    		int second = (seconds - hours*60*60)%60;
    		return formatPart(hours)+":"+formatPart(minutes)+":"+formatPart(second);
    	}else{
    		return "";	
    	}
        
    }
    
    public static String formatPart(int num)
    {
    	if(num < 10)
    	{
    		return "0"+num;
    	}else{
    		return num+"";
    	}
    }
    
    public static void main(String[] args)
    {
    	System.out.println(formatSeconds(23));
    	System.out.println(formatSeconds(23+60*3));
    	System.out.println(formatSeconds(23+60*60+60*56));
    }
}
