package com.dp.vo.credit;

public class PlayerCreditVO
{
	public static final int TONG_CREDIT = 100;//������������
	private Integer pcid;// ��ɫ������ID(����)
	private Integer ppk;// ��ҽ�ɫID
	private Integer pcount;// ��������
	private String creditname;// ��������
	private String creditdisplay;// ��������

	public Integer getPcid()
	{
		return pcid;
	}

	public void setPcid(Integer pcid)
	{
		this.pcid = pcid;
	}

	public Integer getPpk()
	{
		return ppk;
	}

	public void setPpk(Integer ppk)
	{
		this.ppk = ppk;
	}

	public String getCreditname()
	{
		return creditname;
	}

	public void setCreditname(String creditname)
	{
		this.creditname = creditname;
	}

	public String getCreditdisplay()
	{
		return creditdisplay;
	}

	public void setCreditdisplay(String creditdisplay)
	{
		this.creditdisplay = creditdisplay;
	}

	public Integer getPcount()
	{
		return pcount;
	}

	public void setPcount(Integer pcount)
	{
		this.pcount = pcount;
	}
}
