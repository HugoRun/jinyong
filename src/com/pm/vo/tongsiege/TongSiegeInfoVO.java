/**
 * 
 */
package com.pm.vo.tongsiege;

/**
 * ���ɹ���ս������Ϣ��
 * @author zhangjj
 *
 */
public class TongSiegeInfoVO
{

	/** ����ս������Ϣ��¼ID  */
	public int infoId;
	/***  ����pPk   ****/
	public int pPk ;
	/**    �ڵڶ��׶����Ĵ���   **/
	public int deadNum;
	/**    �������޴��� ***/
	public int deadIimit;
	
	/*** �μ�����,1Ϊ���˲�ս,2Ϊ���ɲ�ս  ****/	
	public int attackType;
	/**   ս������,1Ϊ����,2Ϊ�س�   ****/	
	public int 	joinType;
	/**    ����ID  */
	public int 	tongId;
	/**    ս��id  **/	
	public int 	siegeId	;
	/***  ս��������  **/		
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
