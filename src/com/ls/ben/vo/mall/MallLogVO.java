package com.ls.ben.vo.mall;

import java.util.Date;

/**
 * ���ܣ��̳Ǽ�¼
 * @author ls
 * May 12, 2009
 * 3:53:50 PM
 */
public class MallLogVO
{
	/*1��	���id(u_pk)
	2��	��ɫ����
	3��	��¼����
	4��	����ʱ��*/
	private int uPk;
	private String roleName;
	private String log;
	private Date datetime;
	
	public int getUPk()
	{
		return uPk;
	}
	public void setUPk(int pk)
	{
		uPk = pk;
	}
	public String getRoleName()
	{
		return roleName;
	}
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	public String getLog()
	{
		return log;
	}
	public void setLog(String log)
	{
		this.log = log;
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
