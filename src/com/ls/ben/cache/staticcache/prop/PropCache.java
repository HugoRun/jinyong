package com.ls.ben.cache.staticcache.prop;

import java.util.HashMap;

import com.ls.ben.cache.CacheBase;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.web.service.log.DataErrorLog;

/**
 * 通过该类来获得道具
 * @author Administrator
 *
 */
public class PropCache  extends CacheBase
{

	public static String PROP_BY_ID = "prop_by_id";
	
    /**
     * 通过id得到 道具信息
     */
    public static PropVO getPropById(int prop_id)
    {
    	if (prop_id == 0) {
    		return null;
    	}
    	HashMap<Integer,PropVO> prop_skill = (HashMap<Integer,PropVO>)getElementValue(STATIC_CACHE_NAME, PROP_BY_ID);
    	
    	PropVO prop = prop_skill.get(prop_id);
    	if( prop==null )
    	{
    		PropDao propDao = new PropDao();
    		prop = propDao.getById(prop_id);
    		if( prop!=null )
    		{
    			prop_skill.put(prop.getPropID(), prop);
    		}
    		else
    		{
    			DataErrorLog.debugData("PropCache.getPropById：无该道具,prop_id="+prop_id);
    		}
    	}
    	return prop;
    }
    
    /**
     * 通过id得到 道具名称
     */
    public static String getPropNameById(int prop_id)
    {
    	if (prop_id == 0) {
    		return null;
    	}
    	logger.debug("得到一个道具信息");
    	PropVO propVO = null;
    	HashMap<Integer,PropVO> prop_skill = (HashMap<Integer,PropVO>)getElementValue(STATIC_CACHE_NAME, PROP_BY_ID);
    	
    	propVO = prop_skill.get(prop_id);
    	if (propVO != null) {
    		return propVO.getPropName();
    	}
    	logger.debug("propVO:"+propVO);	
    	return null;
    }
    
    /**
     * 通过id得到 道具储存位置
     */
    public int getPropPositionById(int prop_id)
    {
    	if (prop_id == 0) {
    		return 0;
    	}
    	logger.debug("得到一个道具信息");
    	PropVO propVO = null;
    	HashMap<Integer,PropVO> prop_skill = (HashMap<Integer,PropVO>)getElementValue(STATIC_CACHE_NAME, PROP_BY_ID);
    	
    	propVO = prop_skill.get(prop_id);
    	if (propVO != null) {
    		return propVO.getPropPosition();
    	}
    	logger.debug("propVO:"+propVO);	
    	return 0;
    }
    
    /**
     * 通过id得到 道具重叠数量
     */
    public int getPropAccumulate(int prop_id)
    {
    	if (prop_id == 0) {
    		return 0;
    	}
    	logger.debug("得到一个道具信息");
    	PropVO propVO = null;
    	HashMap<Integer,PropVO> prop_skill = (HashMap<Integer,PropVO>)getElementValue(STATIC_CACHE_NAME, PROP_BY_ID);
    	
    	propVO = prop_skill.get(prop_id);
    	if (propVO != null) {
    		return propVO.getPropAccumulate();
    	}
    	logger.debug("propVO:"+propVO);	
    	return 0;
    }
    
    /**
     * 通过id得到 道具拍卖场位置
     */
    public int getPropAuctionPositionById(int prop_id)
    {
    	if (prop_id == 0) {
    		return 0;
    	}
    	logger.debug("得到一个道具信息");
    	PropVO propVO = null;
    	HashMap<Integer,PropVO> prop_skill = (HashMap<Integer,PropVO>)getElementValue(STATIC_CACHE_NAME, PROP_BY_ID);
    	
    	propVO = prop_skill.get(prop_id);
    	if (propVO != null) {
    		return propVO.getPropAuctionPosition();
    	}
    	logger.debug("propVO:"+propVO);	
    	return 0;
    }
    
    /**
	 * 重新加在一条道具数据
	 */
	public void reloadOneProp(PropVO propVO)
	{
		if (propVO != null)
		{
			HashMap result = getElementValue(STATIC_CACHE_NAME, PROP_BY_ID);
			result.remove(propVO.getPropID()+"");
			result.put(propVO.getPropID()+"", propVO);
		}
	}
}
