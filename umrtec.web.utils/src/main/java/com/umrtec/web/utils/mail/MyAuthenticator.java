/*
 * 文 件 名  :  MyAuthenticator.java
 * 描    述    :  &lt;描述&gt;
 * 创建人    :  
 * 创建时间:   下午5:38:15
 */
package com.umrtec.web.utils.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author  
 * @version  [版本号, 2013-8-28 下午5:38:15]
 */
public class MyAuthenticator extends Authenticator{
	String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }
    
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }
    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
}
