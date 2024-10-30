package com.lw.vo.lottery;

public class LotteryInfoVO
{
	/** 奖励总金额 */
	private int lotteryBonus;
	/** 税率 */
	private int lotteryTax;
	/** 系统追加奖励类型 */
	private int sysBonusType;
	/** 系统追加奖励ID */
	private int sysBonusId;
	/** 系统追加奖励品质 */
	private int sysBonusIntro;
	/** 系统追加奖励数量 */
	private int sysBonusNum;
	/** 慈善奖金额 */
	private int sysCharityBonus;
	/** 每天中奖号码 */
	private String lotteryNumberPerDay;
	/** 系统补贴给玩家的奖金 */
	private int lotterySubjoin;
	/** 慈善中奖号码 */
	private String lotteryCharityNum;
	/** 中奖注数 */
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
