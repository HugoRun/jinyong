package com.ls.pub.config.tele;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class ConfigOfTele
{
	/**
	 * 电信的消费代码配置
	 */
	private static PropertiesConfiguration  config = null;
	static
	{
		try
		{
			config = new PropertiesConfiguration("tele.properties");
			config.setReloadingStrategy(new FileChangedReloadingStrategy());
		}
		catch (ConfigurationException e)
		{
			e.printStackTrace();
		} 
	}
	/**
	 * 获取道具消费代码  电信专用
	 */
	public static String getPropCode(String propId)
	{
		return config.getString(propId);
	}
}
