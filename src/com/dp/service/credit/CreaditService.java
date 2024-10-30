package com.dp.service.credit;

import java.util.List;

import com.dp.vo.credit.PlayerCreditVO;

public interface CreaditService
{
	public List<PlayerCreditVO> getPlayerCredit(Integer ppk);//��ѯ��ҽ�ɫ������
	public PlayerCreditVO getPcvDisplay(Integer pcid);	//��������ID��ѯ��������
	public Integer checkHaveCondition(Integer ppk,Integer cid,Integer ncount);//�����Ƿ�߱��һ�����
	public void  subtractCredit(Integer ppk,Integer cid,Integer ncount);//�������ĵ�����
	public void  addPlayerCredit(Integer ppk,Integer cid,Integer ncount);//����������
	public Integer checkHonorCondition(Integer ppk,Integer excount);//�ж����������Ƿ�����
	public void  subtractHonor(Integer ppk,Integer excount);//�������ĵ�����ֵ
	public void  addPlayerHonor(Integer ppk,Integer excount);//����������ֵ
	public Integer getRoleTpk(Integer ppk);//��ȡ��ɫ�İ��ID
}
