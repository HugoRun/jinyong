package com.lw.vo.lottery;

public class DrawALotteryVO
{
	// �齱����
	private int id;
	// �齱����
	private int type;
	// �齱����
	private String lotter_name;
	// �齱����
	private int draw_people;
	// �ȼ���Χ
	private String level_content;
	// ��Ʒ����
	private String bonus_content;
	// ʱ������
	private int timeType;
	// Сʱ
	private int timeHour;
	// ����
	private int timeminute;
	// ���� ���ܼ����ܼ�
	private String timeweek;
	// �Ƿ񱻼���
	private int isRun;

	public int getTimeType()
	{
		return timeType;
	}

	public void setTimeType(int timeType)
	{
		this.timeType = timeType;
	}

	public int getTimeHour()
	{
		return timeHour;
	}

	public void setTimeHour(int timeHour)
	{
		this.timeHour = timeHour;
	}

	public int getTimeminute()
	{
		return timeminute;
	}

	public void setTimeminute(int timeminute)
	{
		this.timeminute = timeminute;
	}

	public int getIsRun()
	{
		return isRun;
	}

	public void setIsRun(int isRun)
	{
		this.isRun = isRun;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getLotter_name()
	{
		return lotter_name;
	}

	public void setLotter_name(String lotter_name)
	{
		this.lotter_name = lotter_name;
	}

	public int getDraw_people()
	{
		return draw_people;
	}

	public void setDraw_people(int draw_people)
	{
		this.draw_people = draw_people;
	}

	public String getLevel_content()
	{
		return level_content;
	}

	public void setLevel_content(String level_content)
	{
		this.level_content = level_content;
	}

	public String getBonus_content()
	{
		return bonus_content;
	}

	public void setBonus_content(String bonus_content)
	{
		this.bonus_content = bonus_content;
	}

	public String getTimeweek()
	{
		return timeweek;
	}

	public void setTimeweek(String timeweek)
	{
		this.timeweek = timeweek;
	}

}
