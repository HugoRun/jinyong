package com.lw.vo.systemnotify;

import java.util.Date;

public class SystemNotifyVO
{
	// id
	private int id;
	// 公告标题
	private String notifytitle;
	// 公告内容
	private String notifycontent;
	// 排序
	private int ordernum;
	// 是否上线
	private int isonline;
	// 建立公告时间
	private Date datetime;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getNotifytitle()
	{
		return notifytitle;
	}
	public void setNotifytitle(String notifytitle)
	{
		this.notifytitle = notifytitle;
	}
	public String getNotifycontent()
	{
		return notifycontent;
	}
	public void setNotifycontent(String notifycontent)
	{
		this.notifycontent = notifycontent;
	}
	public int getOrdernum()
	{
		return ordernum;
	}
	public void setOrdernum(int ordernum)
	{
		this.ordernum = ordernum;
	}
	public int getIsonline()
	{
		return isonline;
	}
	public void setIsonline(int isonline)
	{
		this.isonline = isonline;
	}
	public Date getDatetime()
	{
		return datetime;
	}
	public void setDatetime(Date datetime)
	{
		this.datetime = datetime;
	}
}
