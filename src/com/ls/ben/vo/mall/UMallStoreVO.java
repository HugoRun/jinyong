package com.ls.ben.vo.mall;

/**
 * ���ܣ��ٱ���
 * @author ls
 * May 12, 2009
 * 3:57:47 PM
 */
public class UMallStoreVO
{
	/*1��	���id(u_pk)
	2��	��Ʒid
	3��	��Ʒ����*/
	
	private int uPk;
	private int propId;
	private String propName;
	private int commodityNum;
	public int getUPk()
	{
		return uPk;
	}
	public void setUPk(int pk)
	{
		uPk = pk;
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
		return propName;
	}
	public void setPropName(String propName)
	{
		this.propName = propName;
	}
	public int getCommodityNum()
	{
		return commodityNum;
	}
	public void setCommodityNum(int commodityNum)
	{
		this.commodityNum = commodityNum;
	}
}
