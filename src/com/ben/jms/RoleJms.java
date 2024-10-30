package com.ben.jms;

import java.io.Serializable;
import java.util.Date;

import com.ben.shitu.model.DateUtil;

public class RoleJms implements Serializable
{
	private static final long serialVersionUID = 1111111L;
	private String super_qudao;
	private String qudao;
	private String fenqu;
	private String userid;
	private String name;
	private int level;
	private String reg_time;
	private String last_login_time;
	private int caozuo = 0;//0为添加，1为修改等级,2修改最后登录时间
	public int getCaozuo()
	{
		return caozuo;
	}
	public void setCaozuo(int caozuo)
	{
		this.caozuo = caozuo;
	}
	public RoleJms(){
		super();
		this.last_login_time  = this.reg_time = DateUtil.getDate(new Date());
	}
	public String getReg_time()
	{
		return reg_time;
	}
	public void setReg_time(String reg_time)
	{
		this.reg_time = reg_time;
	}
	public static long getSerialVersionUID()
	{
		return serialVersionUID;
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
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getLevel()
	{
		return level;
	}
	public void setLevel(int level)
	{
		this.level = level;
	}
	public String getLast_login_time()
	{
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time)
	{
		this.last_login_time = last_login_time;
	}
	
}
