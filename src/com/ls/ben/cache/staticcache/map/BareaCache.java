package com.ls.ben.cache.staticcache.map;

import java.util.HashMap;

import com.ls.ben.cache.CacheBase;
import com.ls.ben.vo.map.BareaVO;

public class BareaCache extends CacheBase
{
	public static String BAREA_BY_ID = "barea_by_id";
	
	/**
	 * ͨ��id�õ�barea��Ϣ
	 * @param scene_id
	 * @return
	 */
	public BareaVO getById( String barea_id )
	{
		logger.debug("ͨ��id�õ���ͼ��������Ϣ:barea_id="+barea_id);
		BareaVO barea_info = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, BAREA_BY_ID);
		barea_info = (BareaVO)result.get(barea_id);
		logger.debug("barea_id:"+barea_id);
		return barea_info;
	}
	
	/**
	 * �õ�����barea��Ϣ
	 * @param scene_id
	 * @return
	 */
	public HashMap<String,BareaVO> getBareaList()
	{
		HashMap<String,BareaVO> result = getElementValue(STATIC_CACHE_NAME, BAREA_BY_ID);
		return result;
	}
}
