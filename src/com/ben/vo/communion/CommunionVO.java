/**
 * 
 */
package com.ben.vo.communion;

import java.util.HashMap;

/**
 * @author ��ƾ�
 * 
 * 7:33:53 PM
 */
public class CommunionVO
{
	/** ����Ƶ��id */
	private int cPk;
	/** ���ͽ�ɫid */
	private int pPk;
	/** ���ͽ�ɫ���� */
	private String pName;
	/** ���ս�ɫid */
	private int pPkBy;
	/** ���ս�ɫ���� */
	private String pNameBy;
	/** ������� */
	private int cBang;
	/** ������� */
	private int cDui;
	/** ������Ӫ */
	private int cZhen;
	/** �������� */
	private String cTitle;
	/** ���� */
	private int cType;
	/** ����ʱ�� */
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
