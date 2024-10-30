package com.ls.ben.cache.staticcache.honour;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.ben.dao.honour.TitleDAO;
import com.ben.vo.honour.TitleVO;
import com.ls.ben.cache.CacheBase;
import com.ls.web.service.log.DataErrorLog;

/**
 *	称号缓存
 */
public class TitleCache extends CacheBase
{
	public static String HONOUR_BY_ID = "honour_by_id";
	
	/**
	 * 通过id得到称信息
	 * @param scene_id
	 * @return
	 */
	public static TitleVO getById(String id)
	{
		if( StringUtils.isEmpty(id))
		{
			return null;
		}
		
		HashMap result = getElementValue(STATIC_CACHE_NAME, HONOUR_BY_ID);
		TitleVO title = (TitleVO)result.get(id);
		if(title==null){
			title = new TitleDAO().getById(id);
			if( title!=null )
			{
				result.put(title.getId()+"", title);
			}
			else
			{
				DataErrorLog.debugData("TitleCache.getById:无该称号，id="+id);
			}
		}
		return title;
	}
	
	public static TitleVO getById(int id)
	{
		return getById(id+"");
	}
}