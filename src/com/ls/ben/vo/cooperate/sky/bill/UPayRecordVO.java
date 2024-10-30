package com.ls.ben.vo.cooperate.sky.bill;

public class UPayRecordVO
{
	/*
	 * 添加消费记录
	 * @param skyid
	 * @param billd
	 * @param kbamt
	 * @result:0
	 * @Skybillid1 和 skybillid2： 是超级大玩家的对账流水号。 请保存到消费记录中以便于对账
	 * @balance:0     用户当前账户中的余额
	 */
	private int billd;//记录主键
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
