package com.lw.vo.lotterynew;

public class PlayerLotteryVO
{
	// ID
	private long id;
	// ppk
	private int p_pk;
	// ��Ʊ�ں�
	private String lottery_date;
	// ��Ʊ����
	private String lottery_content;
	// �����Ʊ��ע��
	private int lottery_zhu;
	// ��Ʊ�н��ȼ�
	private int lottery_bonus_lv;
	// ���Ʊʱ��
	private String lottery_time;
	// ��ȡ��������
	private long lottery_bonus;
	// ����Ƿ���ȡ����
	private int is_have;
	// ��ȡ������ʱ��
	private String have_time;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
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

	public String getLottery_date()
	{
		return lottery_date;
	}

	public void setLottery_date(String lottery_date)
	{
		this.lottery_date = lottery_date;
	}

	public String getLottery_content()
	{
		return lottery_content;
	}

	public void setLottery_content(String lottery_content)
	{
		this.lottery_content = lottery_content;
	}

	public int getLottery_zhu()
	{
		return lottery_zhu;
	}

	public void setLottery_zhu(int lottery_zhu)
	{
		this.lottery_zhu = lottery_zhu;
	}

	public int getLottery_bonus_lv()
	{
		return lottery_bonus_lv;
	}

	public void setLottery_bonus_lv(int lottery_bonus_lv)
	{
		this.lottery_bonus_lv = lottery_bonus_lv;
	}

	public String getLottery_time()
	{
		return lottery_time;
	}

	public void setLottery_time(String lottery_time)
	{
		this.lottery_time = lottery_time;
	}

	public long getLottery_bonus()
	{
		return lottery_bonus;
	}

	public void setLottery_bonus(long lottery_bonus)
	{
		this.lottery_bonus = lottery_bonus;
	}

	public int getIs_have()
	{
		return is_have;
	}

	public void setIs_have(int is_have)
	{
		this.is_have = is_have;
	}

	public String getHave_time()
	{
		return have_time;
	}

	public void setHave_time(String have_time)
	{
		this.have_time = have_time;
	}

}
