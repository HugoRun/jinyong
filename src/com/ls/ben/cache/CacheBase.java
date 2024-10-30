/**
 * 
 */
package com.ls.ben.cache;

import java.util.HashMap;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * cache层的基类
 */
public class CacheBase	
{
	public static String STATIC_CACHE_NAME = "static_table_cache";//静态不变的信息缓存
	public static String DYNAMIC_MANUAL_CACHE = "dynamic_manual_cache";//手动改变的信息缓存
	
	protected static Logger logger = Logger.getLogger("log.cache");
	protected static CacheManager manager = CacheManager.create();	
	
	protected static HashMap getElementValue(String cache_name,String cache_key)
	{
		logger.debug("获取element值：cache_name="+cache_name+";cache_key="+cache_key);
		HashMap result = null;
		Cache cache = manager.getCache(cache_name);
		Element element = cache.get(cache_key);
		result = (HashMap)element.getValue();
		return result;
	}
	
	
	protected static List getElementValueUseList(String cache_name,String cache_key)
	{
		logger.debug("获取element值：cache_name="+cache_name+";cache_key="+cache_key);
		List result = null;
		Cache cache = manager.getCache(cache_name);
		Element element = cache.get(cache_key);
		result = (List)element.getValue();
		return result;
	}
}
