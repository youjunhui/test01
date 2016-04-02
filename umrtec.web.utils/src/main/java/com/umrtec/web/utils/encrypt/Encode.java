/*
 * 文 件 名  :  Encode.java
 * 描    述    :  &lt;描述&gt;
 * 创建人    :  
 * 创建时间:  下午3:19:06
 */
package com.umrtec.web.utils.encrypt;

import java.security.MessageDigest;

/**
 * 密码加密工具类
 * 
 * @author 
 * @version [版本号, 2013-7-30 下午3:19:06]
 */
public class Encode {

	public static String encode(String str, String algorithm) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest code = MessageDigest.getInstance(algorithm);
			code.update(str.getBytes());
			byte[] bs = code.digest();
			for (int i = 0; i < bs.length; i++) {
				int v = bs[i] & 0xFF;
				if (v < 16) {
					sb.append(0);
				}
				sb.append(Integer.toHexString(v));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString().toUpperCase();
	}

	public static String encodeByMD5(String str) {
		return encode(str, "MD5");
	}
	
	public static void main(String[] args) {
		System.out.println(encodeByMD5("1"));
	}
}
