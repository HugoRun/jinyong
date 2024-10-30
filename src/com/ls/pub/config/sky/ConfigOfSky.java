/**
 * 
 */
package com.ls.pub.config.sky;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * 功能：思凯通道配置文件
 * @author ls
 * Jun 27, 2009
 * 2:26:32 PM
 */
public class ConfigOfSky
{
	/*http://localhost:40021/authssid?ssid=<ssid>
	http://localhost:40022/paykb?
	而充值的是http://211.155.236.18:8081/pc?*/
	
	private static PropertiesConfiguration  config = null;
	static
	{
		try
		{
			config = new PropertiesConfiguration("sky.properties");
			config.setReloadingStrategy(new FileChangedReloadingStrategy());
		}
		catch (ConfigurationException e)
		{
			e.printStackTrace();
		} 
	}
	
	/**
	 * 得到认证ssid是否有效的url地址
	 * @return
	 */
	public static String getUrlOfAuthSSID()
	{
		return config.getString("auth_ssid_url"); 
	}
	
	/**
	 * 得到消耗K币url地址
	 * @return
	 */
	public static String getUrlOfPayKB()
	{
		return config.getString("pay_kb_url"); 
	}
	
	/**
	 * 得到充值K币的url地址
	 * @return
	 */
	public static String getUrlOfChongzhiKB()
	{
		return config.getString("chongzhi_kb_url"); 
	}
	
	public static void main(String[] args) 
	{
		String temp = ConfigOfSky.getUrlOfPayKB();
		System.out.print("consume_kb_url:"+temp);
	}
}
