package com.pm.vo.mail;

public class MailBonusVO
{
	// id
	private int id;
	// ���PPK
	private int p_pk;
	// �ʼ�ID
	private int mail_id;
	// ��������
	private String bonus;
	// �Ƿ���ȡ
	private int is_have;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getP_pk()
	{
		return p_pk;
	}

	public void setP_pk(int p_pk)
	{
		this.p_pk = p_pk;
	}

	public int getMail_id()
	{
		return mail_id;
	}

	public void setMail_id(int mail_id)
	{
		this.mail_id = mail_id;
	}

	public String getBonus()
	{
		return bonus;
	}

	public void setBonus(String bonus)
	{
		this.bonus = bonus;
	}

	public int getIs_have()
	{
		return is_have;
	}

	public void setIs_have(int is_have)
	{
		this.is_have = is_have;
	}
}
