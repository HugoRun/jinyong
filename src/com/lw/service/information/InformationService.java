package com.lw.service.information;

import com.lw.dao.information.InformationDAO;

public class InformationService
{
	/** 得到玩家的资格 看玩家是否有获得大礼包的资格 */
	public boolean getInformationBy50(int u_pk)
	{
		InformationDAO dao = new InformationDAO();
		if (dao.getInfotmationByUpk(u_pk, "50级") == null)
		{
			return true;
		}
		return false;
	}

	/** 得到玩家的资格 看玩家是否有获得大礼包的资格 */
	public boolean getInformationBy60(int u_pk)
	{
		InformationDAO dao = new InformationDAO();
		if (dao.getInfotmationByUpk(u_pk, "60级") == null)
		{
			return true;
		}
		return false;
	}

	/** 查看玩家帮会是否参加过活动 */
	public boolean getInformationByTong(int u_pk, String tongid)
	{
		InformationDAO dao = new InformationDAO();
		if (dao.getInfotmationByUpk(u_pk, tongid) == null)
		{
			return true;
		}
		return false;
	}

	/** 得到玩家帮会帮主的资格 看玩家是否有获得大礼包的资格 */
	public boolean getInformationByTong(int p_pk)
	{
		InformationDAO dao = new InformationDAO();
		if (dao.getInfotmationByTong(p_pk) != 0)
		{
			return true;
		}
		return false;
	}
}
