/**
 * 
 */
package com.pm.vo.tongsiege;

/**
 * ���ɹ���ս
 * @author zhangjj
 * @version 1.0
 *
 */
public class TongSiegeBattleVO
{
	/** ����սID,������ĳ�����еĹ���  */
	public int siegeId;
	/***  ����ս�ɣ� **/
	public String siegeName;
	/*** ���ɹ���ս����Ӧ��map_type  **/	
	public int mapId	;
	/**   Ӱ��ĵ�ͼMAP_ID,�ڴ�ID �ڵ������ܴ˳��й�Ͻ,�����һ��, �Զ������� ***/	
	public String	affectMapId ;
	/*****   �˹���ս�������������˰��ˮƽ,��0.01��0.1֮��   *****/	
	public int 	tax	;
	/**      �˹���ս�������������˰��      **/	
	public int 	taxMoney;
	/**     ��ȥʱ�ĵص� ***/
	public int outScene;
	/***  �����,��һ��Ϊ���Ƿ������,�ڶ���Ϊ�سǷ������  ***/
	public String reliveScene;
	
	
	
	
	public String getReliveScene()
	{
		return reliveScene;
	}
	public void setReliveScene(String reliveScene)
	{
		this.reliveScene = reliveScene;
	}
	public String getSiegeName()
	{
		return siegeName;
	}
	public void setSiegeName(String siegeName)
	{
		this.siegeName = siegeName;
	}
	public int getOutScene()
	{
		return outScene;
	}
	public void setOutScene(int outScene)
	{
		this.outScene = outScene;
	}
	public int getSiegeId()
	{
		return siegeId;
	}
	public void setSiegeId(int siegeId)
	{
		this.siegeId = siegeId;
	}
	public int getMapId()
	{
		return mapId;
	}
	public void setMapId(int mapId)
	{
		this.mapId = mapId;
	}
	public String getAffectMapId()
	{
		return affectMapId;
	}
	public void setAffectMapId(String affectMapId)
	{
		this.affectMapId = affectMapId;
	}
	public int getTax()
	{
		return tax;
	}
	public void setTax(int tax)
	{
		this.tax = tax;
	}
	public int getTaxMoney()
	{
		return taxMoney;
	}
	public void setTaxMoney(int taxMoney)
	{
		this.taxMoney = taxMoney;
	}
	
	
	
	

}
