package com.ls.ben.cache.staticcache.map;

import java.util.HashMap;

import com.ls.ben.cache.CacheBase;
import com.ls.ben.vo.map.MapVO;

public class MapCache extends CacheBase
{
	public static String MAP_BY_ID = "map_by_id";
	
	/**
	 * ͨ��id�õ�map��Ϣ
	 * @param scene_id
	 * @return
	 */
	public static MapVO getById( String map_id )
	{
		logger.debug("ͨ��id�õ�map��Ϣ:map_id="+map_id);
		MapVO map_info = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, MAP_BY_ID);
		map_info = (MapVO)result.get(map_id);
		logger.debug("map:"+map_info);
		return map_info;
	}
	
	/**
	 * �õ�����map��Ϣ
	 * @param scene_id
	 * @return
	 */
	public HashMap<String,MapVO> getMapList()
	{
		HashMap<String,MapVO> result = getElementValue(STATIC_CACHE_NAME, MAP_BY_ID);
		return result;
	}
}
