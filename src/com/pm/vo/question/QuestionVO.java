package com.pm.vo.question;

public class QuestionVO
{

	 /**  �û�������ֱ�id */
	public int id;		
  	/**  ���˽�ɫid   */
  	public int p_pk;			
	/**  �û�����  **/
	public int integral;				
  	/**  ������ȷ���� **/
  	public int conunite_win;			
  	/**  �·�  */
  	public String mouth;
  	/**   ÿ�´���ʮ���������  **/
  	public int conunite_day;			
  	/**  ������ʱ�� */
  	public String last_time;
  	
  	/**  �û�����  */
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
