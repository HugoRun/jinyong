package com.lw.vo.player;

import java.util.Date;

public class PlayerStatisticsVO
{
	// 主键
	private int id;
	// ppk
	private int upk;
	// ppk
	private int ppk;
	// 等级
	private int grade;
	// 在线时间
	private int onlinetime;
	// 日期
	private String p_date;
	// 时间
	private String p_time;
	// 上次登录时间
	private Date logintimeold;
	// 本次登录时间
	private Date logintime;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getUpk()
	{
		return upk;
	}

	public void setUpk(int upk)
	{
		this.upk = upk;
	}

	public int getPpk()
	{
		return ppk;
	}

	public void setPpk(int ppk)
	{
		this.ppk = ppk;
	}

	public int getGrade()
	{
		return grade;
	}

	public void setGrade(int grade)
	{
		this.grade = grade;
	}

	public int getOnlinetime()
	{
		return onlinetime;
	}

	public void setOnlinetime(int onlinetime)
	{
		this.onlinetime = onlinetime;
	}

	public String getP_date()
	{
		return p_date;
	}

	public void setP_date(String p_date)
	{
		this.p_date = p_date;
	}

	public String getP_time()
	{
		return p_time;
	}

	public void setP_time(String p_time)
	{
		this.p_time = p_time;
	}

	public Date getLogintimeold()
	{
		return logintimeold;
	}

	public void setLogintimeold(Date logintimeold)
	{
		this.logintimeold = logintimeold;
	}

	public Date getLogintime()
	{
		return logintime;
	}

	public void setLogintime(Date logintime)
	{
		this.logintime = logintime;
	}

}