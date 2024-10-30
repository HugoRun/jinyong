package com.pm.vo.question;

public class QuestionVO
{

	 /**  用户答题积分表id */
	public int id;		
  	/**  个人角色id   */
  	public int p_pk;			
	/**  用户积分  **/
	public int integral;				
  	/**  连续正确次数 **/
  	public int conunite_win;			
  	/**  月份  */
  	public String mouth;
  	/**   每月答完十道题的天数  **/
  	public int conunite_day;			
  	/**  最后答题时间 */
  	public String last_time;
  	
  	/**  用户名字  */
  	public String pName;
  	
  	
	public String getPName()
	{
		return pName;
	}
	public void setPName(String name)
	{
		pName = name;
	}
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
	public int getIntegral()
	{
		return integral;
	}
	public void setIntegral(int integral)
	{
		this.integral = integral;
	}
	public int getConunite_win()
	{
		return conunite_win;
	}
	public void setConunite_win(int conunite_win)
	{
		this.conunite_win = conunite_win;
	}
	public String getMouth()
	{
		return mouth;
	}
	public void setMouth(String mouth)
	{
		this.mouth = mouth;
	}
	public int getConunite_day()
	{
		return conunite_day;
	}
	public void setConunite_day(int conunite_day)
	{
		this.conunite_day = conunite_day;
	}
	public String getLast_time()
	{
		return last_time;
	}
	public void setLast_time(String last_time)
	{
		this.last_time = last_time;
	}			
	
}
