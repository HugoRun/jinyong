package com.pm.vo.tongsiege;

/**
 * ���ɹ��ǿ��Ʊ�
 * @author ZHANGJJ
 * @version 1.0
 */
public class TongSiegeControlVO
{
	/** ���ɹ���ս���Ʊ�ID  */
	public int controlId;
	/*** ����սID,������ĳ�����еĹ���  **/	
	public int siegeId	;
	/**   siege_id������İ�������Ҫ��ʼ���ǵڼ��ι���ս   ***/	
	public int 	siegeNumber;
	/**   �´ι���ս��ʼʱ��  */	
	public String 	siegeStartTime;
	/**   �´ι���ս������ֹʱ�� */	
	public String 	siegeSignEnd;
	/**    �˹���ս���ϴ�ʤ������ID   **/	
	public int 	lastWinTongid;
	/**     ��ǰ�׶�,1Ϊ���ǵ�һ�׶�,2Ϊ���ǵڶ��׶�,3Ϊ���ǽ����ȴ��´ο�ʼ   ***/	
	public int 	nowPhase	;
	public int getControlId()
	{
		return controlId;
	}
	public void setControlId(int controlId)
	{
		this.controlId = controlId;
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
	
	public String getSiegeStartTime()
	{
		return siegeStartTime;
	}
	public void setSiegeStartTime(String siegeStartTime)
	{
		this.siegeStartTime = siegeStartTime;
	}
	public String getSiegeSignEnd()
	{
		return siegeSignEnd;
	}
	public void setSiegeSignEnd(String siegeSignEnd)
	{
		this.siegeSignEnd = siegeSignEnd;
	}
	public int getLastWinTongid()
	{
		return lastWinTongid;
	}
	public void setLastWinTongid(int lastWinTongid)
	{
		this.lastWinTongid = lastWinTongid;
	}
	public int getNowPhase()
	{
		return nowPhase;
	}
	public void setNowPhase(int nowPhase)
	{
		this.nowPhase = nowPhase;
	}	
	
	
	

}
