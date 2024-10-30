package com.lw.service.player;

import java.util.Date;

import com.lw.dao.player.PlayerEnvelopPpkDao;

public class PlayerEnvelopService
{
	/**
	 * 查询玩家是否处在封号状态 如果有返回时间 如果没有 返回null
	 * 
	 * @param p_pk
	 *            玩家ID
	 * @return time null为没有 返回 时间的STRING类型
	 */
	public String getPlayerEnvelop(int p_pk)
	{
		String time = null;
		Date date = new Date();
		PlayerEnvelopPpkDao dao = new PlayerEnvelopPpkDao();
		if (dao.getPlayerFromEnvelop(p_pk) == true)
		{
			Date t1 = dao.getPlayerEnvelop(p_pk);
			long i = (t1.getTime() - date.getTime()) / 60000;
			if (i < 0)
			{
				dao.updatePlayerEnvelop(p_pk);
				return time;
			}
			else
			{
				if (i >= 1)
				{
					time = String.valueOf(i);
					return time;
				}
				else
				{
					time = "1";
					return time;
				}
			}
		}
		else
		{
			return time;
		}
	}

	/**
	 * 得到玩家是否被永久封号 返回true 为永久封号 false为没有
	 */
	public boolean getPlayerEnvelopForever(int p_pk)
	{
		PlayerEnvelopPpkDao dao = new PlayerEnvelopPpkDao();
		boolean x = dao.getPlayerEnvelopForever(p_pk);
		return x;
	}
}
