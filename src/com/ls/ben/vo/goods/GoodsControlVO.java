package com.ls.ben.vo.goods;

/**
 * ����:��Ʒ���Ʋ��������壬��������ȷ�ϣ��Ƿ񱣻����Ƿ�ʰȡ�󶨣��Ƿ�װ����
 * @author ��˧
 * Oct 16, 2008  11:25:55 AM
 */
public class GoodsControlVO 
{
	private int id;
	private int bonding;
	private int protect;
	private int isReconfirmed;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getBonding()
	{
		return bonding;
	}
	public void setBonding(int bonding)
	{
		this.bonding = bonding;
	}
	public int getProtect()
	{
		return protect;
	}
	public void setProtect(int protect)
	{
		this.protect = protect;
	}
	public int getIsReconfirmed()
	{
		return isReconfirmed;
	}
	public void setIsReconfirmed(int isReconfirmed)
	{
		this.isReconfirmed = isReconfirmed;
	}
}