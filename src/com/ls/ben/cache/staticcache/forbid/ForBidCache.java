package com.ls.ben.cache.staticcache.forbid;

import java.util.HashMap;

import com.ls.ben.cache.CacheBase;

public class ForBidCache extends CacheBase
{
	public static String FORBID_BY_TYPE = "forbid_by_type";

	public static int FORBIDNAME = 1;
	public static int FORBIDCOMM = 2;
	/**
	 * ͨ�����͵õ���ֹ�ַ�����1Ϊȡ����ֹ��2Ϊ������ֹ
	 * @param scene_id
	 * @return
	 */
	public String getById( int forbid_type )
	{
		logger.debug("ͨ�����͵õ���ֹ�ַ���="+forbid_type);
		String typeForbid = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, FORBID_BY_TYPE);
		typeForbid = (String)result.get(forbid_type);
		//logger.debug("typeForbid:"+typeForbid);
		return typeForbid;
	}
}
