package com.lw.vo.lottery;

public class LotteryInfoVO
{
	/** �����ܽ�� */
	private int lotteryBonus;
	/** ˰�� */
	private int lotteryTax;
	/** ϵͳ׷�ӽ������� */
	private int sysBonusType;
	/** ϵͳ׷�ӽ���ID */
	private int sysBonusId;
	/** ϵͳ׷�ӽ���Ʒ�� */
	private int sysBonusIntro;
	/** ϵͳ׷�ӽ������� */
	private int sysBonusNum;
	/** ���ƽ���� */
	private int sysCharityBonus;
	/** ÿ���н����� */
	private String lotteryNumberPerDay;
	/** ϵͳ��������ҵĽ��� */
	private int lotterySubjoin;
	/** �����н����� */
	private String lotteryCharityNum;
	/** �н�ע�� */
	private int lotteryWinNum;

	public int getLotteryWinNum()
	{
		return lotteryWinNum;
	}

	public void setLotteryWinNum(int lotteryWinNum)
	{
		this.lotteryWinNum = lotteryWinNum;
	}

	public int getLotterySubjoin()
	{
		return lotterySubjoin;
	}

	public void setLotterySubjoin(int lotterySubjoin)
	{
		this.lotterySubjoin = lotterySubjoin;
	}

	public int getLotteryBonus()
	{
		return lotteryBonus;
	}

	public void setLotteryBonus(int lotteryBonus)
	{
		this.lotteryBonus = lotteryBonus;
	}

	public int getLotteryTax()
	{
		return lotteryTax;
	}

	public void setLotteryTax(int lotteryTax)
	{
		this.lotteryTax = lotteryTax;
	}

	public int getSysBonusType()
	{
		return sysBonusType;
	}

	public void setSysBonusType(int sysBonusType)
	{
		this.sysBonusType = sysBonusType;
	}

	public int getSysCharityBonus()
	{
		return sysCharityBonus;
	}

	public void setSysCharityBonus(int sysCharityBonus)
	{
		this.sysCharityBonus = sysCharityBonus;
	}

	public int getSysBonusNum()
	{
		return sysBonusNum;
	}

	public void setSysBonusNum(int sysBonusNum)
	{
		this.sysBonusNum = sysBonusNum;
	}

	public String getLotteryNumberPerDay()
	{
		return lotteryNumberPerDay;
	}

	public void setLotteryNumberPerDay(String lotteryNumberPerDay)
	{
		this.lotteryNumberPerDay = lotteryNumberPerDay;
	}

	public int getSysBonusId()
	{
		return sysBonusId;
	}

	public void setSysBonusId(int sysBonusId)
	{
		this.sysBonusId = sysBonusId;
	}

	public String getLotteryCharityNum()
	{
		return lotteryCharityNum;
	}

	public void setLotteryCharityNum(String lotteryCharityNum)
	{
		this.lotteryCharityNum = lotteryCharityNum;
	}

	public int getSysBonusIntro()
	{
		return sysBonusIntro;
	}

	public void setSysBonusIntro(int sysBonusIntro)
	{
		this.sysBonusIntro = sysBonusIntro;
	}
}
