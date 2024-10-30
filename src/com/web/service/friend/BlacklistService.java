package com.web.service.friend;

import java.util.List;

import org.apache.log4j.Logger;

import com.ben.dao.friend.BlacklistDAO;
import com.ben.vo.friend.BlacklistVO;
import com.ls.web.service.npc.NpcService;

/**
 * 好友
 * 
 * @author 侯浩军 11:13:44 AM
 */
public class BlacklistService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * 增加黑名单
	 */
	public String addblacklist(int pPk, String pByPk, String pByName,
			String time)
	{
		try
		{
			BlacklistDAO blacklistDAO = new BlacklistDAO();
			blacklistDAO.blacklistAdd(pPk, pByPk, pByName, time);
			logger.info("将 " + pByName + "加为黑名单了");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断该玩家是否已经是自己的黑名单
	 */
	public boolean whetherblacklist(int pPk, String pByPk)
	{
		try
		{
			BlacklistDAO blacklistDAO = new BlacklistDAO();
			if (blacklistDAO.whetherblacklist(pPk, pByPk))
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 判断该玩家是否已经是达到50个黑名单了
	 */
	public boolean blacklistupperlimit(int pPk)
	{
		try
		{
			BlacklistDAO blacklistDAO = new BlacklistDAO();
			List<BlacklistVO> list = blacklistDAO.getBlacklistList(pPk);
			if (list != null && list.size() != 0)
			{
				if (list.size() == 5)
				{
					return false;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 删除黑名单
	 */
	public String deleteblacklist(int pPk, String pByPk)
	{
		try
		{
			BlacklistDAO blacklistDAO = new BlacklistDAO();
			blacklistDAO.getDeleteBlacklist(pPk, pByPk);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 好友List
	 */
	public List<BlacklistVO> listblacklist(int pPk)
	{
		try
		{
			BlacklistDAO blacklistDAO = new BlacklistDAO();
			List<BlacklistVO> list = blacklistDAO.getBlacklistList(pPk);
			if (list != null)
			{
				return list;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回是否在自己的黑名单中
	 * 
	 * @param pPk
	 *            玩家PK 
	 * @param blp_pk
	 *            黑名单玩家PK
	 * @return
	 */
	public int isBlacklist(int pPk, int blp_pk)
	{
		BlacklistDAO blacklistDAO = new BlacklistDAO();
		if(blacklistDAO.isBlacklist(pPk, blp_pk)){
			return 1;
		}if(blacklistDAO.isBlacklist(blp_pk, pPk)){
			return 2;
		}
		return 0;
	} 
	
	/**好友分页*/
	public List<BlacklistVO> listpage(int pPk,int page,int perpagenum){
		BlacklistDAO blacklistDAO = new BlacklistDAO();
		List<BlacklistVO> list = blacklistDAO.getBlacklistListPage(pPk, page, perpagenum);
		return list;
	}
}
