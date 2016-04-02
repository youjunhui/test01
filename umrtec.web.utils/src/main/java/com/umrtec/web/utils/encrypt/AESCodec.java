/*
 * 文 件 名  :  AESCodec.java
 * 描    述    :  &lt;描述&gt;
 * 创建人    :  
 * 创建时间:   下午6:22:35
 */
package com.umrtec.web.utils.encrypt;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * AES对称加密算法 
 * @author  
 * @version  [版本号, 2013-9-11 下午6:22:35]
 */
public class AESCodec {

	//密钥算法  
    public static final String KEY_ALGORITHM = "AES";  
      
    //加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式  
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";  
      
    /** 
     * 生成密钥 
     */  
    public static String initkey() throws Exception{  
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM); //实例化密钥生成器  
        kg.init(128);                                              //初始化密钥生成器:AES要求密钥长度为128,192,256位  
        SecretKey secretKey = kg.generateKey();                    //生成密钥  
        String key = Base64.encodeBase64String(secretKey.getEncoded());  //获取二进制密钥编码形式  
        return key.replace("/", "+cnit765+"); //对/进行转义
    }  
      
      
    /** 
     * 转换密钥 
     */  
    public static Key toKey(byte[] key) throws Exception{  
        return new SecretKeySpec(key, KEY_ALGORITHM);  
    }  
      
      
    /** 
     * 加密数据 
     * @param data 待加密数据 
     * @param key  密钥 
     * @return 加密后的数据 
     * */  
    public static String encrypt(String data, String key) throws Exception{
    	key = key.replace("+cnit765+", "/");
        Key k = toKey(Base64.decodeBase64(key));                           //还原密钥  
        //使用PKCS7Padding填充方式,这里就得这么写了(即调用BouncyCastle组件实现)  
        //Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);              //实例化Cipher对象，它用于完成实际的加密操作  
        cipher.init(Cipher.ENCRYPT_MODE, k);                               //初始化Cipher对象，设置为加密模式  
        String encryptdata = Base64.encodeBase64String(cipher.doFinal(data.getBytes())); //执行加密操作。加密后的结果通常都会用Base64编码进行传输  ;
        return encryptdata.replace("/", "+cnit765+");
    }  
      
      
    /** 
     * 解密数据 
     * @param data 待解密数据 
     * @param key  密钥 
     * @return 解密后的数据 
     * */  
    public static String decrypt(String data, String key) throws Exception{
    	data = data.replace("+cnit765+", "/");
    	key = key.replace("+cnit765+", "/");  //进行反转义
        Key k = toKey(Base64.decodeBase64(key));  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
        cipher.init(Cipher.DECRYPT_MODE, k);                          //初始化Cipher对象，设置为解密模式  
        return new String(cipher.doFinal(Base64.decodeBase64(data))); //执行解密操作  
    }  
      
      
    public static void main(String[] args) throws Exception {  
        Set<String> set = new HashSet<String>();
        Date begin = new Date();
    	for(int n = 0 ; n < 1000000 ; ++n){
        String source = n+"";  
        //System.out.println("原文：" + source);  
          
        String key = initkey();  
        //System.out.println("密钥：" + key);  
          
        String encryptData = encrypt(source, key);  
        System.out.println("加密：" + encryptData);  
        
        String result = key+encryptData;
        for(int i = 0 ; i < result.length() ; ++i){
        	set.add(result.charAt(i)+"");
        }
        
    	}
    	Date end = new Date();
    	System.out.println((end.getTime() - begin.getTime())/1000 + "s");
    	Iterator<String> it = set.iterator();
    	while(it.hasNext()){
    		System.out.print(it.next());
    	}
        //String decryptData = decrypt(encryptData, key);  
        //System.out.println("解密: " + decryptData);  
    }  
	
}
