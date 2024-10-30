package com.ls.web.service.goods.prop;

import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.model.user.RoleEntity;

/**
 * @author ls
 * ������ز���
 */
public class PropService
{
	/**
	 * �ж�ָ���ĵ����ߵ������Ƿ��㹻
	 */
	public boolean isEnoughProp( RoleEntity roleInfo,int prop_id,int need_num )
	{
		if( roleInfo==null )
		{
			return false;
		}
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		int cur_num = playerPropGroupDao.getPropNumByByPropID(roleInfo.getPPk(),prop_id);//���в��ϵ�����
		if( cur_num<need_num)
		{
			return false;
		}
		
		return true;
	}
}
