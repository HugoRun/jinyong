package com.dp.service.credit;

import java.util.List;

import com.dp.vo.credit.PlayerCreditVO;

public interface CreaditService
{
	public List<PlayerCreditVO> getPlayerCredit(Integer ppk);//查询玩家角色的声望
	public PlayerCreditVO getPcvDisplay(Integer pcid);	//根据声望ID查询声望描述
	public Integer checkHaveCondition(Integer ppk,Integer cid,Integer ncount);//声望是否具备兑换条件
	public void  subtractCredit(Integer ppk,Integer cid,Integer ncount);//减掉消耗的声望
	public void  addPlayerCredit(Integer ppk,Integer cid,Integer ncount);//添加玩家声望
	public Integer checkHonorCondition(Integer ppk,Integer excount);//判断荣誉条件是否满足
	public void  subtractHonor(Integer ppk,Integer excount);//减掉消耗的荣誉值
	public void  addPlayerHonor(Integer ppk,Integer excount);//添加玩家荣誉值
	public Integer getRoleTpk(Integer ppk);//获取角色的帮会ID
}
