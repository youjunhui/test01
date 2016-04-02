/*
 * 文 件 名  :  CryptoUtil.java
 * 描    述    :  <描述>
 * 创建人    :  
 * 创建时间:  下午3:09:11
 */
package com.umrtec.web.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import com.umrtec.web.utils.domain.GlobalStatic;


/**
 * 加密码工具类
 * 
 * @author 
 * @version [版本号, 2013-7-1]
 */
public class CryptoUtil {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static byte[] MD5Encode(byte[] origin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(origin);
		} catch (Exception ex) {
			throw new RuntimeException("MD5! Encode Error", ex);
		}
	}

	/**
	 * MD5加密,无密钥
	 * 
	 * @author 
	 * @param origin
	 * @return [参数说明]
	 * @return String [返回类型说明]
	 */
	public static String md5Encode(String origin) {
		try {
			byte[] input = origin.getBytes("UTF-8");
			byte[] md5Hash = MD5Encode(input);
			return byteArrayToHexString(md5Hash);
		} catch (Exception e) {
			throw new RuntimeException("MD5! Encode Error", e);
		}
	}

	/**
	 * MD5加密,密钥
	 * 
	 * @author 
	 * @param origin
	 * @param key
	 * @return [参数说明]
	 * @return String [返回类型说明]
	 */
	public static String md5Encode(String origin, String key) {
		try {
			if (StringUtils.isEmpty(key)) {
				key = GlobalStatic.SPRING_ENCRYPT_KEY;
			}
			origin = origin + key;
			byte[] input = origin.getBytes("UTF-8");
			byte[] md5Hash = MD5Encode(input);
			return byteArrayToHexString(md5Hash);
		} catch (Exception e) {
			throw new RuntimeException("MD5! Encode Error", e);
		}
	}

	/**
	 * AES加密码
	 * 
	 * @author 
	 * @param origin
	 * @param key
	 * @return [参数说明]
	 * @return String [返回类型说明]
	 */
	public static String aesEncrypt(String origin, String key) {
		try {
			SecretKey secretKey = getSecretKey(key);
			SecretKeySpec keySpec = getSecretKeySpec(secretKey);

			// 创建密码器
			Cipher cipher = Cipher.getInstance("AES");

			// 初始化
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);

			// 加密
			byte[] result = cipher.doFinal(origin.getBytes("UTF-8"));

			// 将密文转换成十六进制的字符串
			return byteArrayToHexString(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字节数组转成十六进制的字段串
	 * 
	 * @author 
	 * @param buf
	 * @return [参数说明]
	 * @return String [返回类型说明]
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * AES解密
	 * 
	 * @author 
	 * @param origin
	 * @param key
	 * @return [参数说明]
	 * @return String [返回类型说明]
	 */
	public static String aesDecrypt(String origin, String key) {
		try {

			SecretKey secretKey = getSecretKey(key);
			SecretKeySpec keySpec = getSecretKeySpec(secretKey);

			// 创建密码器
			Cipher cipher = Cipher.getInstance("AES");

			// 初始化
			cipher.init(Cipher.DECRYPT_MODE, keySpec);

			// 将十六进制转成二进制数组，并解密
			byte[] result = cipher.doFinal(parseHexStr2Byte(origin));
			// 生成字符串
			return new String(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 2012-9-22 上午08:37:40
	 * 
	 * @author
	 * @param secretKey
	 * @return
	 */
	private static SecretKeySpec getSecretKeySpec(SecretKey secretKey) {
		byte[] keyEncoded = secretKey.getEncoded();
		SecretKeySpec keySpec = new SecretKeySpec(keyEncoded, "AES");
		return keySpec;
	}

	/**
	 * 2012-9-22 上午08:37:15
	 * 
	 * @author
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static SecretKey getSecretKey(String key) throws NoSuchAlgorithmException {
		KeyGenerator kGen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(key.getBytes());
		kGen.init(128, secureRandom);
		SecretKey secretKey = kGen.generateKey();
		return secretKey;
	}

	/**
	 * 将二进制转成16进制的字符串，所有字母大写
	 * 
	 * @author 
	 * @param b
	 * @return [参数说明]
	 * @return String [返回类型说明]
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString().toUpperCase();
	}

	/**
	 * 将字节转换成16进制
	 * 
	 * <br>
	 * 2012-9-21 下午08:56:33
	 * 
	 * @author
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 将十六进制的字符串转成二进制数组 <br>
	 * 2012-9-22 上午08:29:43
	 * 
	 * @author 王东松
	 * @param hexStr
	 *            16进制字符串
	 * @return 二进制数组
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) {
		// System.out.println(md5Encode("111111"));
		String pass = aesEncrypt("LyKj2015Fly", "wbaobei");
		System.out.println(pass);
		String pass2 = aesDecrypt("8AAE2B80171E32BCAB98F798C13F88EB", "wbaobei");
		System.out.println(pass2);
		
		// System.out.println(aesDecrypt("289458530E9CE02266D5B01D3FBEB3DE", "vispractice"));
	}
}
