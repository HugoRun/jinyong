/**
 * 
 */
package com.pm.vo.tongsiege;

/**
 * 帮派攻城战
 * @author zhangjj
 * @version 1.0
 *
 */
public class TongSiegeBattleVO
{
	/** 攻城战ID,代表着某个城市的攻城  */
	public int siegeId;
	/***  攻城战ＩＤ **/
	public String siegeName;
	/*** 帮派攻城战所对应的map_type  **/	
	public int mapId	;
	/**   影响的地图MAP_ID,在此ID 内的区域都受此城市管辖,如多于一个, 以逗号区分 ***/	
	public String	affectMapId ;
	/*****   此工程战场所代表区域的税率水平,在0.01到0.1之间   *****/	
	public int 	tax	;
	/**      此攻城战场所代表区域的税金      **/	
	public int 	taxMoney;
	/**     出去时的地点 ***/
	public int outScene;
	/***  复活点,第一个为攻城方复活点,第二个为守城方复活点  ***/
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
