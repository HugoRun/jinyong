package com.lw.vo.systemnotify;

import java.util.Date;

public class SystemNotifyVO
{
	// id
	private int id;
	// �������
	private String notifytitle;
	// ��������
	private String notifycontent;
	// ����
	private int ordernum;
	// �Ƿ�����
	private int isonline;
	// ��������ʱ��
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
