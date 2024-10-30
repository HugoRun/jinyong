package com.web.service.friend;

import java.util.List;

import org.apache.log4j.Logger;

import com.ben.dao.friend.BlacklistDAO;
import com.ben.vo.friend.BlacklistVO;
import com.ls.web.service.npc.NpcService;

/**
 * ����
 * 
 * @author ��ƾ� 11:13:44 AM
 */
public class BlacklistService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ���Ӻ�����
	 */
	public String addblacklist(int pPk, String pByPk, String pByName,
			String time)
	{
		try
		{
			BlacklistDAO blacklistDAO = new BlacklistDAO();
			blacklistDAO.blacklistAdd(pPk, pByPk, pByName, time);
			logger.info("�� " + pByName + "��Ϊ��������");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �жϸ�����Ƿ��Ѿ����Լ��ĺ�����
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
	 * �жϸ�����Ƿ��Ѿ��Ǵﵽ50����������
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
	 * ɾ��������
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
	 * ����List
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
	 * �����Ƿ����Լ��ĺ�������
	 * 
	 * @param pPk
	 *            ���PK 
	 * @param blp_pk
	 *            ���������PK
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
	
	/**���ѷ�ҳ*/
	public List<BlacklistVO> listpage(int pPk,int page,int perpagenum){
		BlacklistDAO blacklistDAO = new BlacklistDAO();
		List<BlacklistVO> list = blacklistDAO.getBlacklistListPage(pPk, page, perpagenum);
		return list;
	}
}
