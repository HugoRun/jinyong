package com.lw.vo.lottery;

public class PlayerLotteryInfoVO
{
	private int pPk;
	private int lotteryNum;
	private int lotteryPerMoney;
	private int lotteryWinNum;
	private int lotteryCatchMoney;
	private int lotteryPerBonus;
	private int lotteryBonusMultiple;
	private int lotteryCharity;
	private int lotteryAllBonus;

	public int getLotteryAllBonus()
	{
		return lotteryAllBonus;
	}

	public void setLotteryAllBonus(int lotteryAllBonus)
	{
		this.lotteryAllBonus = lotteryAllBonus;
	}

	public int getLotteryCharity()
	{
		return lotteryCharity;
	}

	public void setLotteryCharity(int lotteryCharity)
	{
		this.lotteryCharity = lotteryCharity;
	}

	public int getLotteryBonusMultiple()
	{
		return lotteryBonusMultiple;
	}

	public void setLotteryBonusMultiple(int lotteryBonusMultiple)
	{
		this.lotteryBonusMultiple = lotteryBonusMultiple;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getLotteryNum()
	{
		return lotteryNum;
	}

	public void setLotteryNum(int lotteryNum)
	{
		this.lotteryNum = lotteryNum;
	}

	public int getLotteryWinNum()
	{
		return lotteryWinNum;
	}

	public void setLotteryWinNum(int lotteryWinNum)
	{
		this.lotteryWinNum = lotteryWinNum;
	}

	public int getLotteryCatchMoney()
	{
		return lotteryCatchMoney;
	}

	public void setLotteryCatchMoney(int lotteryCatchMoney)
	{
		this.lotteryCatchMoney = lotteryCatchMoney;
	}

	public int getLotteryPerBonus()
	{
		return lotteryPerBonus;
	}

	public void setLotteryPerBonus(int lotteryPerBonus)
	{
		this.lotteryPerBonus = lotteryPerBonus;
	}

	public int getLotteryPerMoney()
	{
		return lotteryPerMoney;
	}

	public void setLotteryPerMoney(int lotteryPerMoney)
	{
		this.lotteryPerMoney = lotteryPerMoney;
	}
}
