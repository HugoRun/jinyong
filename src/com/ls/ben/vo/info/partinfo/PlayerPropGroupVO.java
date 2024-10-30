package com.ls.ben.vo.info.partinfo;

import java.util.Date;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;

/**
 * 功能:表u_wrapprop_info
 * 
 * @author 刘帅 1:45:03 PM
 */
public class PlayerPropGroupVO
{

	/** id */
	private int pgPk;
	/** 角色id */
	private int pPk;
	/** 道具在包袱里的分类 */
	private int pgType;
	/** 道具id */
	private int propId;
	/** 道具类型 */
	private int propType;

	private int propBonding;
	private int propProtect;
	private int propIsReconfirm;
	private int propUseControl;

	/** 道具数量 */
	private int propNum;
	/** 创建时间 */
	private Date createTime;

	/** 道具特殊字段1 */
	private String propOperate1;

	/**
	 * 是否可丢弃
	 * @return
	 */
	public boolean isThrowed()
	{
		if( propProtect==0&&propBonding==0 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 得到道具的信息
	 * @return
	 */
	public PropVO getPropInfo()
	{
		return PropCache.getPropById(this.propId);
	}
	
	public String getPropOperate1()
	{
		return propOperate1;
	}

	public void setPropOperate1(String propOperate1)
	{
		this.propOperate1 = propOperate1;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getPropId()
	{
		return propId;
	}

	public void setPropId(int propId)
	{
		this.propId = propId;
	}

	public String getPropName()
	{
		return this.getPropInfo().getPropName();
	}

	public int getPropNum()
	{
		return propNum;
	}

	public void setPropNum(int propNum)
	{
		this.propNum = propNum;
	}

	public int getPgPk()
	{
		return pgPk;
	}

	public void setPgPk(int pgPk)
	{
		this.pgPk = pgPk;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public int getPropType()
	{
		return propType;
	}

	public void setPropType(int propType)
	{
		this.propType = propType;
	}

	public int getPgType()
	{
		return pgType;
	}

	public void setPgType(int pgType)
	{
		this.pgType = pgType;
	}


	public int getPropPrice()
	{
		return this.getPropInfo().getPropSell();
	}


	public int getPropBonding()
	{
		return propBonding;
	}

	public void setPropBonding(int propBonding)
	{
		this.propBonding = propBonding;
	}

	public int getPropProtect()
	{
		return propProtect;
	}

	public void setPropProtect(int propProtect)
	{
		this.propProtect = propProtect;
	}

	public int getPropIsReconfirm()
	{
		return propIsReconfirm;
	}

	public void setPropIsReconfirm(int propIsReconfirm)
	{
		this.propIsReconfirm = propIsReconfirm;
	}

	public int getPropUseControl()
	{
		return propUseControl;
	}

	public void setPropUseControl(int propUseControl)
	{
		this.propUseControl = propUseControl;
	}
}
