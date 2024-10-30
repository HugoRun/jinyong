package com.lw.vo.lotterynew;

public class LotteryVO
{
	// ID
	private int id;
	// 彩票期号
	private String lottery_date;
	// 彩票内容
	private String lottery_content;
	// 销售总额
	private long lottery_all_yb;
	// 玩家领取奖励的数量
	private long lottery_catch_yb;
	// 中奖人数
	private String lottery_catch_player;
	// 开奖时间
	private String lottery_create_time;
	// 奖池奖励内容
	private long sys_lottery_yb;
	// 追加奖励内容
	private String sys_lottery_bonus;
	// 税金
	private int sys_lottery_tax;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

	public long getLottery_all_yb()
	{
		return lottery_all_yb;
	}

	public void setLottery_all_yb(long lottery_all_yb)
	{
		this.lottery_all_yb = lottery_all_yb;
	}

	public long getLottery_catch_yb()
	{
		return lottery_catch_yb;
	}

	public void setLottery_catch_yb(long lottery_catch_yb)
	{
		this.lottery_catch_yb = lottery_catch_yb;
	}

	public String getLottery_catch_player()
	{
		return lottery_catch_player;
	}

	public void setLottery_catch_player(String lottery_catch_player)
	{
		this.lottery_catch_player = lottery_catch_player;
	}

	public String getLottery_create_time()
	{
		return lottery_create_time;
	}

	public void setLottery_create_time(String lottery_create_time)
	{
		this.lottery_create_time = lottery_create_time;
	}

	public long getSys_lottery_yb()
	{
		return sys_lottery_yb;
	}

	public void setSys_lottery_yb(long sys_lottery_yb)
	{
		this.sys_lottery_yb = sys_lottery_yb;
	}

	public String getSys_lottery_bonus()
	{
		return sys_lottery_bonus;
	}

	public void setSys_lottery_bonus(String sys_lottery_bonus)
	{
		this.sys_lottery_bonus = sys_lottery_bonus;
	}

	public int getSys_lottery_tax()
	{
		return sys_lottery_tax;
	}

	public void setSys_lottery_tax(int sys_lottery_tax)
	{
		this.sys_lottery_tax = sys_lottery_tax;
	}
}
