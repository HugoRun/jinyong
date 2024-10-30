/**
 * 
 */
package com.ls.pub.config.sky;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * ���ܣ�˼��ͨ�������ļ�
 * @author ls
 * Jun 27, 2009
 * 2:26:32 PM
 */
public class ConfigOfSky
{
	/*http://localhost:40021/authssid?ssid=<ssid>
	http://localhost:40022/paykb?
	����ֵ����http://211.155.236.18:8081/pc?*/
	
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
	 * �õ���֤ssid�Ƿ���Ч��url��ַ
	 * @return
	 */
	public static String getUrlOfAuthSSID()
	{
		return config.getString("auth_ssid_url"); 
	}
	
	/**
	 * �õ�����K��url��ַ
	 * @return
	 */
	public static String getUrlOfPayKB()
	{
		return config.getString("pay_kb_url"); 
	}
	
	/**
	 * �õ���ֵK�ҵ�url��ַ
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
