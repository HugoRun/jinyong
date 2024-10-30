/**
 * 
 */
package com.pm.vo.tongsiege;

/**
 * 奖金发放记录
 * @author zhangjj
 *
 */
public class TongMoneyInfoVO
{
	/** 攻城战个人信息记录ID  */
	public int infoId;
	/***  个人pPk   ****/		
	public int pPk;
	/***  帮派ID **/		
	public int 	tongId;
		/**     是否那会标志,1为未拿,2为已经拿回    **/	
	public int 	backType;
		/***   金钱数额  ****/	
	public int 	moneyNum;
		/**    奖金发放时间   ****/	
	public String 	sendtime;
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
		public int getTongId()
		{
			return tongId;
		}
		public void setTongId(int tongId)
		{
			this.tongId = tongId;
		}
		public int getBackType()
		{
			return backType;
		}
		public void setBackType(int backType)
		{
			this.backType = backType;
		}
		public int getMoneyNum()
		{
			return moneyNum;
		}
		public void setMoneyNum(int moneyNum)
		{
			this.moneyNum = moneyNum;
		}
		public String getSendtime()
		{
			return sendtime;
		}
		public void setSendtime(String sendtime)
		{
			this.sendtime = sendtime;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
