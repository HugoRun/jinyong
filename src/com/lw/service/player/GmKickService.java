package com.lw.service.player;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.login.LoginService;

public class GmKickService
{
	/** GM踢人流程 */
	public String kickPlayer(String p_name)
	{
		// 得到玩家的PPK
		PartInfoDao partInfoDao = new PartInfoDao();
		
		int p_pk = partInfoDao.getByName(p_name);
		if (p_pk == -1 || p_pk == 0)
		{
			return "不存在该玩家";
		}
		else
		{
			RoleEntity roleInfo = RoleCache.getByPpk(p_pk);
			if (roleInfo == null|| roleInfo.isOnline()==false)
			{
				return "该玩家不在线";
			}
			else
			{
				LoginService loginService = new LoginService();
				loginService.loginoutRole(p_pk + "");
				RoleEntity roleInfo1 = RoleCache.getByPpk(p_pk);
				if (roleInfo1 == null|| roleInfo1.isOnline()==false)
				{
					return "该玩家已经被踢下线";
				}
				else
				{
					return "请重新再试";
				}
			}
		}
	}
}
