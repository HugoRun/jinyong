package com.ls.ben.vo.cooperate.bill;

import java.util.Date;

/**
 * @author ls
 * ����:u_account_record
 * Mar 13, 2009
 */
public class UAccountRecordVO
{
	private int id; /**ID*/ 
	private int uPk;/**�û��˺�*/
	private int pPk; /**��ֵʱ��½�Ľ�ɫ*/
	private String code;/**����*/
	private String pwd;/**����*/
	private int money; /**��ֵ��Ǯ��*/
	private String channel; /**��ֵ����*/
	private String accountState=""; /**��ֵ״̬*/
	private Date accountTime;/**��ֵʱ��*/
	private Date paymentTime; /**����ʱ��,���û�Ԫ������ʱ��*/
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getUPk()
	{
		return uPk;
	}
	public void setUPk(int pk)
	{
		uPk = pk;
	}
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getPwd()
	{
		return pwd;
	}
	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}
	public int getMoney()
	{
		return money;
	}
	public void setMoney(int money)
	{
		this.money = money;
	}
	public String getChannel()
	{
		return channel;
	}
	public void setChannel(String channel)
	{
		this.channel = channel;
	}
	public String getAccountState()
	{
		return accountState;
	}
	public void setAccountState(String accountState)
	{
		this.accountState = accountState;
	}
	public Date getAccountTime()
	{
		return accountTime;
	}
	public void setAccountTime(Date accountTime)
	{
		this.accountTime = accountTime;
	}
	public Date getPaymentTime()
	{
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime)
	{
		this.paymentTime = paymentTime;
	}
}
