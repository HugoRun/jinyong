package com.ls.pub.config.tele;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class ConfigOfTele
{
	/**
	 * ���ŵ����Ѵ�������
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
	 * ��ȡ�������Ѵ���  ����ר��
	 */
	public static String getPropCode(String propId)
	{
		return config.getString(propId);
	}
}
