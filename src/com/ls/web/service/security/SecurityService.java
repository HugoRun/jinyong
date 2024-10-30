package com.ls.web.service.security;

import com.ls.pub.util.encrypt.MD5Util;


public class SecurityService {
	
	private static String MERCHANT_KEY = "oneshow123";
	
	/**
	 * 用户名加密
	 */
	public static String getVerifyString(String user_name)
	{
		String verify_string = "";
		
		verify_string = MD5Util.md5Hex(user_name+"+"+MERCHANT_KEY);
		
		return verify_string;
	}
	
	/**
	 * 用户解密认证
	 */
	public  static  boolean validateName(String user_name ,String received_verify_string)
	{
		String verify_string=getVerifyString(user_name);
		
		if( verify_string!=null && verify_string.equals(received_verify_string))
		{
			return true;
		}
		
		return false;
	}

}
