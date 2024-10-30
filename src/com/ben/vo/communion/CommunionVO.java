/**
 * 
 */
package com.ben.vo.communion;

import java.util.HashMap;

/**
 * @author 侯浩军
 * 
 * 7:33:53 PM
 */
public class CommunionVO
{
	/** 公共频道id */
	private int cPk;
	/** 发送角色id */
	private int pPk;
	/** 发送角色名称 */
	private String pName;
	/** 接收角色id */
	private int pPkBy;
	/** 接收角色名称 */
	private String pNameBy;
	/** 所属帮会 */
	private int cBang;
	/** 所属组队 */
	private int cDui;
	/** 所属阵营 */
	private int cZhen;
	/** 聊天类容 */
	private String cTitle;
	/** 类型 */
	private int cType;
	/** 创建时间 */
	private String createTime;

	public int getCPk()
	{
		return cPk;
	}

	public void setCPk(int pk)
	{
		cPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public String getPName()
	{
		return pName;
	}

	public void setPName(String name)
	{
		pName = name;
	}

	public int getPPkBy()
	{
		return pPkBy;
	}

	public void setPPkBy(int pkBy)
	{
		pPkBy = pkBy;
	}

	public String getPNameBy()
	{
		return pNameBy;
	}

	public void setPNameBy(String nameBy)
	{
		pNameBy = nameBy;
	}

	public int getCBang()
	{
		return cBang;
	}

	public void setCBang(int bang)
	{
		cBang = bang;
	}

	public int getCDui()
	{
		return cDui;
	}

	public void setCDui(int dui)
	{
		cDui = dui;
	}

	public int getCZhen()
	{
		return cZhen;
	}

	public void setCZhen(int zhen)
	{
		cZhen = zhen;
	}

	public String getCTitle()
	{
		return cTitle;
	}

	public void setCTitle(String title)
	{
		cTitle = title;
	}

	public int getCType()
	{
		return cType;
	}

	public void setCType(int type)
	{
		cType = type;
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
