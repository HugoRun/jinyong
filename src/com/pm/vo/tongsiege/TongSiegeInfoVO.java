/**
 * 
 */
package com.pm.vo.tongsiege;

/**
 * 帮派攻城战个人信息表
 * @author zhangjj
 *
 */
public class TongSiegeInfoVO
{

	/** 攻城战个人信息记录ID  */
	public int infoId;
	/***  个人pPk   ****/
	public int pPk ;
	/**    在第二阶段死的次数   **/
	public int deadNum;
	/**    死亡极限次数 ***/
	public int deadIimit;
	
	/*** 参加类型,1为个人参战,2为帮派参战  ****/	
	public int attackType;
	/**   战斗类型,1为攻城,2为守城   ****/	
	public int 	joinType;
	/**    帮派ID  */
	public int 	tongId;
	/**    战场id  **/	
	public int 	siegeId	;
	/***  战场次序数  **/		
	public int 	siegeNumber;
		
		
		
	
	
	public int getAttackType()
	{
		return attackType;
	}
	public void setAttackType(int attackType)
	{
		this.attackType = attackType;
	}
	public int getJoinType()
	{
		return joinType;
	}
	public void setJoinType(int joinType)
	{
		this.joinType = joinType;
	}
	public int getTongId()
	{
		return tongId;
	}
	public void setTongId(int tongId)
	{
		this.tongId = tongId;
	}
	public int getSiegeId()
	{
		return siegeId;
	}
	public void setSiegeId(int siegeId)
	{
		this.siegeId = siegeId;
	}
	public int getSiegeNumber()
	{
		return siegeNumber;
	}
	public void setSiegeNumber(int siegeNumber)
	{
		this.siegeNumber = siegeNumber;
	}
	public int getInfoId()
	{
		return infoId;
	}
	public void setInfoId(int infoId)
	{
		this.infoId = infoId;
	}
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public int getDeadNum()
	{
		return deadNum;
	}
	public void setDeadNum(int deadNum)
	{
		this.deadNum = deadNum;
	}
	public int getDeadIimit()
	{
		return deadIimit;
	}
	public void setDeadIimit(int deadIimit)
	{
		this.deadIimit = deadIimit;
	}
		
		
	
	
	
}
