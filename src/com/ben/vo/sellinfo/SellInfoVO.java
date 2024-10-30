/**
 * 
 */
package com.ben.vo.sellinfo;

import com.ls.ben.dao.info.partinfo.PlayerEquipDao;

/**
 * @author 侯浩军
 * 
 * 2:21:45 PM
 */
public class SellInfoVO
{
	public static int SELLMONEY = 1;// 1金钱交易
	public static int SELLPROP = 2;// 2 道具交易
	public static int SELLARM = 3;// 3 装备交易
	public static int ZENGSONGPROP = 4;//4赠送道具
	public static int ZENGSONGARM = 5;//5赠送装备
	/** id */
	private int sPk;
	/** 发出请求角色id */
	private int pPk;
	/** 被请求角色id */
	private int pByPk;
	/** 发出请求要交易的物品 */
	private int sWuping;
	/** 发出请求要物品类型 */
	private int sWpType;
	/** 发出请求要物品的数量 */
	private int sWpNumber;
	/** 发出请求要物品的价格的银子 */
	private long sWpSilverMoney;
	/** 发出请求要物品的价格的铜钱 */
	private int sWpCopperMoney;
	/** 交易方式 1金钱交易 2 道具交易 3 装备交易 */
	private int sellMode;
	/** 创建时间 */
	private String createTime;

	/**
	 * 得到物品名称
	 * @return
	 */
	public String getWupingName()
	{
		if( sWpType==SELLARM)
		{
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			return playerEquipDao.getNameById(sWuping);
		}
		return "";
	}
	
	public int getSPk()
	{
		return sPk;
	}

	public void setSPk(int pk)
	{
		sPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getPByPk()
	{
		return pByPk;
	}

	public void setPByPk(int byPk)
	{
		pByPk = byPk;
	}

	public int getSWuping()
	{
		return sWuping;
	}

	public void setSWuping(int wuping)
	{
		sWuping = wuping;
	}

	public int getSWpType()
	{
		return sWpType;
	}

	public void setSWpType(int wpType)
	{
		sWpType = wpType;
	}

	public int getSWpNumber()
	{
		return sWpNumber;
	}

	public void setSWpNumber(int wpNumber)
	{
		sWpNumber = wpNumber;
	}

	public long getSWpSilverMoney()
	{
		return sWpSilverMoney;
	}

	public void setSWpSilverMoney(long wpSilverMoney)
	{
		sWpSilverMoney = wpSilverMoney;
	}

	public int getSellMode()
	{
		return sellMode;
	}

	public void setSellMode(int sellMode)
	{
		this.sellMode = sellMode;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
}
