package com.ben.pk.active;

import java.util.Date;

/**
 * 
 * @author thomas.lei
 * ���ܣ�PK�������װ��
 * 27/04/10 PM
 *
 */
public class PKActiveRegist
{
	private int id;
	private int roleID;//��ɫID
	private int roleLevel;//��ɫ�ȼ�
	private String roleName;//��ɫ����
	private Date registTime;//����ʱ��
	private int isWin;//�Ƿ�ʤ��
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getRoleID()
	{
		return roleID;
	}
	public void setRoleID(int roleID)
	{
		this.roleID = roleID;
	}
	public int getRoleLevel()
	{
		return roleLevel;
	}
	public void setRoleLevel(int roleLevel)
	{
		this.roleLevel = roleLevel;
	}
	public String getRoleName()
	{
		return roleName;
	}
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	public Date getRegistTime()
	{
		return registTime;
	}
	public void setRoleTime(Date roleTime)
	{
		this.registTime = roleTime;
	}
	public int getIsWin()
	{
		return isWin;
	}
	public void setIsWin(int isWin)
	{
		this.isWin = isWin;
	}

}
