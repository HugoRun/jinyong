package com.lw.vo.menpaicontest;

public class MenpaiContestPlayerVO
{
	private int id;
	/** *ID** */
	private int p_pk;
	/** ** */
	private String p_name;
	/** �������* */
	private int p_type;
	/** *��ҳ�ν* */
	private int into_state;
	/** ���ؾ��е�״̬** */
	private int win_state;
	/** ʤ��״̬* */
	private int kill_num;
	/** ɱ������* */
	private int win_num;

	/** ��ȡ����ӵĴ���* */
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
	public String getP_name()
	{
		return p_name;
	}
	public void setP_name(String p_name)
	{
		this.p_name = p_name;
	}
	public int getP_type()
	{
		return p_type;
	}
	public void setP_type(int p_type)
	{
		this.p_type = p_type;
	}
	public int getInto_state()
	{
		return into_state;
	}
	public void setInto_state(int into_state)
	{
		this.into_state = into_state;
	}
	public int getWin_state()
	{
		return win_state;
	}
	public void setWin_state(int win_state)
	{
		this.win_state = win_state;
	}
	public int getKill_num()
	{
		return kill_num;
	}
	public void setKill_num(int kill_num)
	{
		this.kill_num = kill_num;
	}
	public int getWin_num()
	{
		return win_num;
	}
	public void setWin_num(int win_num)
	{
		this.win_num = win_num;
	}
}
