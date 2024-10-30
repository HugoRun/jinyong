package com.ls.web.service.goods.prop;

import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.model.user.RoleEntity;

/**
 * @author ls
 * 道具相关操作
 */
public class PropService
{
	/**
	 * 判断指定的到道具的数量是否足够
	 */
	public boolean isEnoughProp( RoleEntity roleInfo,int prop_id,int need_num )
	{
		if( roleInfo==null )
		{
			return false;
		}
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		int cur_num = playerPropGroupDao.getPropNumByByPropID(roleInfo.getPPk(),prop_id);//现有材料的数量
		if( cur_num<need_num)
		{
			return false;
		}
		
		return true;
	}
}
