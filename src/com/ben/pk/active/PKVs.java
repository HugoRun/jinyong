package com.ben.pk.active;

/**
 * 
 * @author thomas.lei
 * ���ܣ�PK�������Ӧ�ķ�װ��
 * 27/04/10 PM
 *
 */
public class PKVs
{
	private int id;
	private int roleAID;//��ɫA��ID
	private int roleBID;//��ɫB��ID
	private String roleAName;//��ɫA������
	private String roleBName;//��ɫB������
	private int winRoleID;//PK��ʤ����ID
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getRoleAID()
	{
		return roleAID;
	}
	public void setRoleAID(int roleAID)
	{
		this.roleAID = roleAID;
	}
	public int getRoleBID()
	{
		return roleBID;
	}
	public void setRoleBID(int roleBID)
	{
		this.roleBID = roleBID;
	}
	public String getRoleAName()
	{
		return roleAName;
	}
	public void setRoleAName(String roleAName)
	{
		this.roleAName = roleAName;
	}
	public String getRoleBName()
	{
		return roleBName;
	}
	public void setRoleBName(String roleBName)
	{
		this.roleBName = roleBName;
	}
	public int getWinRoleID()
	{
		return winRoleID;
	}
	public void setWinRoleID(int winRoleID)
	{
		this.winRoleID = winRoleID;
	}
}
