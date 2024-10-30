package com.ben.jms;

import java.io.Serializable;
import java.util.Date;

import com.ben.shitu.model.DateUtil;

public class ChongzhiMessage implements Serializable
{
	private static final long serialVersionUID = 1111113L;
	private String super_qudao;
	private String qudao;
	private String fenqu;
	private String userid;
	private String phone;
	private int money;
	private int shen;//神州行
	private int yi;//骏网一卡通支付
	private int zheng;//盛大卡支付
	private String chong_time;
	public ChongzhiMessage()
	{
		super();
		this.chong_time = DateUtil.getDate(new Date());
	}
	public String getSuper_qudao()
	{
		return super_qudao;
	}
	public void setSuper_qudao(String super_qudao)
	{
		this.super_qudao = super_qudao;
	}
	public String getQudao()
	{
		return qudao;
	}
	public void setQudao(String qudao)
	{
		this.qudao = qudao;
	}
	public String getFenqu()
	{
		return fenqu;
	}
	public void setFenqu(String fenqu)
	{
		this.fenqu = fenqu;
	}
	public String getUserid()
	{
		return userid;
	}
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public int getMoney()
	{
		return money;
	}
	public void setMoney(int money)
	{
		this.money = money;
	}
	public String getChong_time()
	{
		return chong_time;
	}
	public void setChong_time(String chong_time)
	{
		this.chong_time = chong_time;
	}
	public int getShen()
	{
		return shen;
	}
	public void setShen(int shen)
	{
		this.shen = shen;
	}
	public int getYi()
	{
		return yi;
	}
	public void setYi(int yi)
	{
		this.yi = yi;
	}
	public int getZheng()
	{
		return zheng;
	}
	public void setZheng(int zheng)
	{
		this.zheng = zheng;
	}
}
