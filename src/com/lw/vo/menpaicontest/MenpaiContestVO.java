package com.lw.vo.menpaicontest;

public class MenpaiContestVO
{
	private int id;
	/** ID* */
	private String time_week;
	/** *星期* */
	private int ready_hour;
	/** *准备时间 */
	private int ready_minute;
	/** *准备时间 */
	private int run_hour;
	/** *开始时间 */
	private int run_minute;
	/** *开始时间 */
	private int over_hour;
	/** *结束时间 */
	private int over_minute;
	/** ***全部结束时间*** */
	private int all_hour;
	private int all_minute;

	/** *结束时间 */
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTime_week()
	{
		return time_week;
	}

	public void setTime_week(String time_week)
	{
		this.time_week = time_week;
	}

	public int getReady_hour()
	{
		return ready_hour;
	}

	public void setReady_hour(int ready_hour)
	{
		this.ready_hour = ready_hour;
	}

	public int getReady_minute()
	{
		return ready_minute;
	}

	public void setReady_minute(int ready_minute)
	{
		this.ready_minute = ready_minute;
	}

	public int getRun_hour()
	{
		return run_hour;
	}

	public void setRun_hour(int run_hour)
	{
		this.run_hour = run_hour;
	}

	public int getRun_minute()
	{
		return run_minute;
	}

	public void setRun_minute(int run_minute)
	{
		this.run_minute = run_minute;
	}

	public int getOver_hour()
	{
		return over_hour;
	}

	public void setOver_hour(int over_hour)
	{
		this.over_hour = over_hour;
	}

	public int getOver_minute()
	{
		return over_minute;
	}

	public void setOver_minute(int over_minute)
	{
		this.over_minute = over_minute;
	}

	public int getAll_hour()
	{
		return all_hour;
	}

	public void setAll_hour(int all_hour)
	{
		this.all_hour = all_hour;
	}

	public int getAll_minute()
	{
		return all_minute;
	}

	public void setAll_minute(int all_minute)
	{
		this.all_minute = all_minute;
	}
}
