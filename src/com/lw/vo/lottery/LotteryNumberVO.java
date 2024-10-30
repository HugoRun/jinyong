package com.lw.vo.lottery;

public class LotteryNumberVO
{
	private int lottery;
	private int pPk;
	private String lotteryNumber;
	private int lotteryType;
	private int playerAddMoney;

	public int getLottery()
	{
		return lottery;
	}

	public void setLottery(int lottery)
	{
		this.lottery = lottery;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public String getLotteryNumber()
	{
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber)
	{
		this.lotteryNumber = lotteryNumber;
	}

	public int getLotteryType()
	{
		return lotteryType;
	}

	public void setLotteryType(int lotteryType)
	{
		this.lotteryType = lotteryType;
	}

	public int getPlayerAddMoney()
	{
		return playerAddMoney;
	}

	public void setPlayerAddMoney(int playerAddMoney)
	{
		this.playerAddMoney = playerAddMoney;
	}
}
