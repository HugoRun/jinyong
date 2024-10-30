package com.lw.vo.sinasys;

import java.util.Date;

public class SinaUserVO
{
	private Date date;
	private int p_pk;
	private int grade;
	private String wm;
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public int getP_pk()
	{
		return p_pk;
	}
	public void setP_pk(int p_pk)
	{
		this.p_pk = p_pk;
	}
	public int getGrade()
	{
		return grade;
	}
	public void setGrade(int grade)
	{
		this.grade = grade;
	}
	public String getWm()
	{
		return wm;
	}
	public void setWm(String wm)
	{
		this.wm = wm;
	}
}
