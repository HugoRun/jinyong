package com.dp.dao.credit;

import java.util.ArrayList;
import java.util.List;

import com.dp.service.credit.CreaditService;
import com.dp.vo.credit.PlayerCreditVO;

public class CreditProce implements CreaditService
{
	/**
	 * ��ѯ��ҽ�ɫ��ӵ�е�����
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
	 * ��������ID��ѯ����
	 **************************************************************************/
	public PlayerCreditVO getPcvDisplay(Integer pcid)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.getPcvDisplay(pcid);
	}

	/***************************************************************************
	 * �ж�����Ƿ��������һ���Ʒ
	 **************************************************************************/
	public Integer checkHaveCondition(Integer ppk, Integer cid, Integer ncount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.checkHaveCondition(ppk, cid, ncount);
	}

	/***************************************************************************
	 * �һ��ɹ��������ҵ�����
	 **************************************************************************/
	public void subtractCredit(Integer ppk, Integer cid, Integer ncount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		creditProceDAO.subtractCredit(ppk, cid, ncount);
	}

	/**
	 * �����ҽ�ɫ����
	 */
	public void addPlayerCredit(Integer ppk, Integer cid, Integer ncount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		creditProceDAO.addPlayerCredit(ppk, cid, ncount);
	}
	/**
	 * ɾ������
	 * @param ppk
	 * @param cid
	 */
	public void deletePlayerCredit(int ppk,int cid){
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		creditProceDAO.deletePlayerCredit(ppk, cid);
	}
	/***************************************************************************
	 * �ж�����Ƿ��и�����
	 **************************************************************************/
	public Integer checkPlayerHasTheCredit(Integer ppk, Integer cid)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.checkPlayerHasTheCredit(ppk, cid);
	}

	/***************************************************************************
	 * �ж����������Ƿ�����
	 **************************************************************************/
	public Integer checkHonorCondition(Integer ppk, Integer excount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.checkHonorCondition(ppk, excount);
	}

	/**
	 * �����ҵ�����ֵ
	 */
	public void addPlayerHonor(Integer ppk, Integer excount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		creditProceDAO.addPlayerHonor(ppk, excount);
	}

	/**
	 * ������ҵ�����ֵ
	 */
	public void subtractHonor(Integer ppk, Integer excount)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		creditProceDAO.subtractHonor(ppk, excount);
	}

	/***************************************************************************
	 * ��ȡ��ɫ�İ��ID
	 **************************************************************************/
	public Integer getRoleTpk(Integer ppk)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.getRoleTpk(ppk);
	}

	/***************************************************************************
	 * �жϸý�ɫ�Ƿ�������
	 **************************************************************************/
	public Integer checkRoleHaveTheHoner(Integer ppk)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.checkRoleHaveTheHoner(ppk);
	}

	/***************************************************************************
	 * ��ѯ�ý�ɫ�Ľ�ɫ��
	 **************************************************************************/
	public String getTheRoleName(Integer ppk)
	{
		CreditProceDAO creditProceDAO = new CreditProceDAO();
		return creditProceDAO.getTheRoleName(ppk);
	}
}