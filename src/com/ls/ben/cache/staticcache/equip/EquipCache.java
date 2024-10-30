package com.ls.ben.cache.staticcache.equip;

import java.util.HashMap;

import com.ls.ben.cache.CacheBase;
import com.ls.ben.dao.goods.equip.GameEquipDao;
import com.ls.model.equip.GameEquip;
import com.ls.web.service.log.DataErrorLog;

/**
 * @author ls
 * 装备缓存
 */
public class EquipCache extends CacheBase
{
	public static String EQUIP_BY_ID = "equip_by_id";
	
    /**
     * 通过id得到 装备信息
     * @param equip_id
     * @return
     */
    public static GameEquip getById(int equip_id)
    {
    	if (equip_id == 0) {
    		return null;
    	}
    	GameEquip equip = null;
    	HashMap<Integer,GameEquip> equip_list = (HashMap<Integer,GameEquip>)getElementValue(STATIC_CACHE_NAME, EQUIP_BY_ID);
    	
    	equip = equip_list.get(equip_id);	
    	if( equip==null )
    	{
    		GameEquipDao gameEquipDao = new GameEquipDao();
    		equip = gameEquipDao.getById(equip_id);
    		if( equip!=null )
    		{
    			equip_list.put(equip.getId(), equip);
    		}
    		else
    		{
    			DataErrorLog.debugData("EquipCache.getById：无该装备,equip_id="+equip_id);
    		}
    	}
    	return equip;
    }
    
    /**
     * 通过装备名字得到 装备信息
     * @param equip_name
     * @return
     */
    public GameEquip getByName(String equip_name)
    {
    	GameEquipDao gameEquipDao = new GameEquipDao();
    	int equip_id = gameEquipDao.getIdByName(equip_name);
    	return EquipCache.getById(equip_id);
    }
    
}
