/**
 * 
 */
package com.pm.vo.tongsiege;

/**
 * ���𷢷ż�¼
 * @author zhangjj
 *
 */
public class TongMoneyInfoVO
{
	/** ����ս������Ϣ��¼ID  */
	public int infoId;
	/***  ����pPk   ****/		
	public int pPk;
	/***  ����ID **/		
	public int 	tongId;
		/**     �Ƿ��ǻ��־,1Ϊδ��,2Ϊ�Ѿ��û�    **/	
	public int 	backType;
		/***   ��Ǯ����  ****/	
	public int 	moneyNum;
		/**    ���𷢷�ʱ��   ****/	
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
