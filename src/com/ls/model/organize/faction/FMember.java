package com.ls.model.organize.faction;

/**
 * @author ls
 * ���ɳ�Ա�İ��������Ϣ
 */
public class FMember
{
	private int pPk;
	private int fId;
	private String name;
	private String nickname;
	private int contribute;//���ɹ���
	private int job;//ְ��
	
	public int getPPk()
	{
		return pPk;
	}
	public int getFId()
	{
		return fId;
	}
	public String getName()
	{
		return name;
	}
	public String getNickname()
	{
		return nickname;
	}
	public int getContribute()
	{
		return contribute;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public void setFId(int id)
	{
		fId = id;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	public void setContribute(int contribute)
	{
		this.contribute = contribute;
	}
	public int getJob()
	{
		return job;
	}
	public void setJob(int job)
	{
		this.job = job;
	}
}
