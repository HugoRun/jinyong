package com.lw.vo.player;

import java.util.Date;

public class PlayerStatisticsVO
{
	// ����
	private int id;
	// ppk
	private int upk;
	// ppk
	private int ppk;
	// �ȼ�
	private int grade;
	// ����ʱ��
	private int onlinetime;
	// ����
	private String p_date;
	// ʱ��
	private String p_time;
	// �ϴε�¼ʱ��
	private Date logintimeold;
	// ���ε�¼ʱ��
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