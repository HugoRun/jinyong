package com.ls.ben.vo.cooperate.sky.bill;

public class UPayRecordVO
{
	/*
	 * ������Ѽ�¼
	 * @param skyid
	 * @param billd
	 * @param kbamt
	 * @result:0
	 * @Skybillid1 �� skybillid2�� �ǳ�������ҵĶ�����ˮ�š� �뱣�浽���Ѽ�¼���Ա��ڶ���
	 * @balance:0     �û���ǰ�˻��е����
	 */
	private int billd;//��¼����
	private String skyid;
	private int kbamt;
	private String result;
	private String skybillid1;
	private String skybillid2;
	private int balance;
	public int getBilld()
	{
		return billd;
	}
	public void setBilld(int billd)
	{
		this.billd = billd;
	}
	public String getSkyid()
	{
		return skyid;
	}
	public void setSkyid(String skyid)
	{
		this.skyid = skyid;
	}
	public int getKbamt()
	{
		return kbamt;
	}
	public void setKbamt(int kbamt)
	{
		this.kbamt = kbamt;
	}
	public String getResult()
	{
		return result;
	}
	public void setResult(String result)
	{
		this.result = result;
	}
	public String getSkybillid1()
	{
		return skybillid1;
	}
	public void setSkybillid1(String skybillid1)
	{
		this.skybillid1 = skybillid1;
	}
	public String getSkybillid2()
	{
		return skybillid2;
	}
	public void setSkybillid2(String skybillid2)
	{
		this.skybillid2 = skybillid2;
	}
	public int getBalance()
	{
		return balance;
	}
	public void setBalance(int balance)
	{
		this.balance = balance;
	}
	
}
