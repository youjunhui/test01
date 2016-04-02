/*
 * 文 件 名  :  IntegerUtil.java
 * 描    述    :  <描述>
 * 创建人    :  
 * 创建时间:  下午1:48:45
 */
package com.umrtec.web.utils.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 *
 */
public class IntegerUtil 
{
	/**
	 * 将width和height转换为一个整数
	 * 高16位为宽，低16位为高
	 * */
	public static int convertResolution(int width , int height)
	{
		String widthBinary = Integer.toBinaryString(width);
		//低位需要补零
		String heightBinary = appendZero(Integer.toBinaryString(height));
		String resultTemp = widthBinary + heightBinary;
		return convertBinartStrToInt(resultTemp);
	}
	
	/**
	 * 将一个数字分拆为两个数字,
	 * 高16为组成一个数字，低16位组成一个数字
	 * return : int[]. int[0]是有高位生成，int[1]是由低位生成
	 * */
	public static int[] splitNumber(int num)
	{
		int[] nums = new int[2];
		String binary = Integer.toBinaryString(num);
		if(binary.length() < 16)
		{
			nums[0] = 0;
			nums[1] = num;
			return nums;
		}else if(binary.length() <= 32){
			String childFirst = binary.substring(0 , binary.length() - 16);
			String childSecond = binary.substring(binary.length() - 16 + 1);
			nums[0] = convertBinartStrToInt(childFirst);
			nums[1] = convertBinartStrToInt(childSecond);
			return nums;
		}else{
			return null;
		}
	}
	
	/**
	 * 将一个数字拆分成字符串的形式 例如 1024*768
	 * 高16位组成前面一个数字，低16位组成后面一个数字
	 * */
	public static String splitNumberToString(Integer num)
	{
		int[] nums = splitNumber(num);
		return nums[0]+"*"+nums[1];
	}
	
	/**
	 * 将二进制的字符串转换为数字
	 * */
	public static int convertBinartStrToInt(String binary)
	{
		int result = 0;
		
		for(int i = binary.length() -1 , j =0  ; i >= 0 ; --i , ++j)
		{
		    char c = binary.charAt(i);
		    int num = c - 48;
		    result += num*Math.pow(2 , j); 
		}
		
		return result;
	}
	
	/**
	 * 补零
	 * 若二进制不满16位，则在前面补零
	 * */
	public static String appendZero(String binary)
	{
		if(StringUtil.isNotEmpty(binary))
		{
			if(binary.length() < 16)
			{
			    StringBuffer temp = new StringBuffer();
			    for(int i = 0 ; i < (16 - binary.length()) ; ++i)
			    {
			    	temp.append("0");
			    }
			    
			    temp.append(binary);
			    return temp.toString();
			}else{
				if(binary.length() == 16)
				{
					return binary;
				}else{
					return "too large";
				}
			}
		}else{
			return "";
		}
	}
	
	/**
	 * 获取两个List<Integer> 中不同的部分
	 * 参数说名：
	 * target是source的子集
	 * */
	public static List<Long> getDifferentPart(List<Long> source , List<Long> target)
	{
		List<Long> result = new ArrayList<Long>();
		
		if(source == null)
		{
			return result;
		}else{
			for(int i = 0 ; i < source.size() ; ++i)
			{
				long so = source.get(i);
				boolean flag = false;
				
				if(target == null)
				{
					return result;
				}else{
					
					for(int n = 0 ; n < target.size() ; ++n)
					{
						if(so == target.get(n))
						{
						    flag = true;
						    break;
						}
					}
					
					if(!flag)
					{
						result.add(so);
					}
				}
				
			}
		}
		
		return result;
	}
    
	/**
	 * 将数据库中存放的分别率转换为width*height
	 * */
	public static String convertconvertResolutionToStr(int resolution)
	{
		int[] rs = splitNumber(resolution);
		return rs[0]+"*"+rs[1];
	}
	
	public static void main(String[] args)
	{
		//640*480
		int width = 1024;
		int height = 768;
		int num = convertResolution(width , height);
		System.out.println("num is ......"+num + "------ " + IntegerUtil.splitNumberToString(num));
		int[] rs =  splitNumber(num);
		System.out.println(rs[0] +  "  " + rs[1]);
		
	}
	
}
