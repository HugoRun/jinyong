package com.lw.vo.lotterynew;

public class LotteryOutPrintVO
{
	// 玩家PPK
	private int p_pk;
	// 彩票期数
	private String lottery_date;
	// 玩家彩票号码
	private String player_lottery;
	// 系统号码
	private String sys_lottery;
	// 玩家获得的奖励
	private long bonus;

	public int getP_pk()
	{
		return p_pk;
	}

	public void setP_pk(int p_pk)
	{
		this.p_pk = p_pk;
	}

	public String getPlayer_lottery()
	{
		return player_lottery;
	}

	public void setPlayer_lottery(String player_lottery)
	{
		this.player_lottery = player_lottery;
	}

	public String getSys_lottery()
	{
		return sys_lottery;
	}

	public void setSys_lottery(String sys_lottery)
	{
		this.sys_lottery = sys_lottery;
	}

	public long getBonus()
	{
		return bonus;
	}

	public void setBonus(long bonus)
	{
		this.bonus = bonus;
	}

	public String getLottery_date()
	{
		return lottery_date;
	}

	public void setLottery_date(String lottery_date)
	{
		this.lottery_date = lottery_date;
	}
}
