package com.dp.dao.credit;

import java.util.ArrayList;
import java.util.List;

import com.dp.service.credit.CreaditService;
import com.dp.vo.credit.PlayerCreditVO;

public class CreditProce implements CreaditService
{
	/**
	 * 查询玩家角色所拥有的声望
	 */
	public List<PlayerCreditVO> getPlayerCredit(Integer ppk)
	{
 		CreditProceDAO creditProceDAO = new CreditProceDAO();
		List list = creditProceDAO.getPlayerCredit(ppk);
		List arrList = new ArrayList();
		if(list != null && list.size() > 0){
			for(int i = 0 ; i < list.size() ; i++){
				PlayerCreditVO vo = (PlayerCreditVO)list.get(i);
				PlayerCreditVO backvo = creditProceDAO.getPcvDisplay(vo.getPcid());
				if(backvo == null){
					continue;
				}
				vo.setCreditname(backvo.getCreditname());
				vo.setCreditdisplay(backvo.getCreditdisplay());
				arrList.add(vo);
			}
		}
		return arrList;
	}

	/***************************************************************************
	 * 根据声望ID查询声望
	 **************************************************************************/
	public PlayerCreditVO getPcvDisplay(Integer pcid)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.getPcvDisplay(pcid);
	}

	/***************************************************************************
	 * 判断玩家是否有条件兑换物品
	 **************************************************************************/
	public Integer checkHaveCondition(Integer ppk, Integer cid, Integer ncount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.checkHaveCondition(ppk, cid, ncount);
	}

	/***************************************************************************
	 * 兑换成功后减掉玩家的声望
	 **************************************************************************/
	public void subtractCredit(Integer ppk, Integer cid, Integer ncount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		creditProceDAO.subtractCredit(ppk, cid, ncount);
	}

	/**
	 * 添加玩家角色声望
	 */
	public void addPlayerCredit(Integer ppk, Integer cid, Integer ncount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		creditProceDAO.addPlayerCredit(ppk, cid, ncount);
	}
	/**
	 * 删除声望
	 * @param ppk
	 * @param cid
	 */
	public void deletePlayerCredit(int ppk,int cid){
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		creditProceDAO.deletePlayerCredit(ppk, cid);
	}
	/***************************************************************************
	 * 判断玩家是否有该声望
	 **************************************************************************/
	public Integer checkPlayerHasTheCredit(Integer ppk, Integer cid)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.checkPlayerHasTheCredit(ppk, cid);
	}

	/***************************************************************************
	 * 判断荣誉条件是否满足
	 **************************************************************************/
	public Integer checkHonorCondition(Integer ppk, Integer excount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.checkHonorCondition(ppk, excount);
	}

	/**
	 * 添加玩家的荣誉值
	 */
	public void addPlayerHonor(Integer ppk, Integer excount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		creditProceDAO.addPlayerHonor(ppk, excount);
	}

	/**
	 * 减掉玩家的荣誉值
	 */
	public void subtractHonor(Integer ppk, Integer excount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		creditProceDAO.subtractHonor(ppk, excount);
	}

	/***************************************************************************
	 * 获取角色的帮会ID
	 **************************************************************************/
	public Integer getRoleTpk(Integer ppk)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.getRoleTpk(ppk);
	}

	/***************************************************************************
	 * 判断该角色是否有荣誉
	 **************************************************************************/
	public Integer checkRoleHaveTheHoner(Integer ppk)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.checkRoleHaveTheHoner(ppk);
	}

	/***************************************************************************
	 * 查询该角色的角色名
	 **************************************************************************/
	public String getTheRoleName(Integer ppk)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.getTheRoleName(ppk);
	}
}