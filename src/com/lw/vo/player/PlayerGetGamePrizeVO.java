package com.lw.vo.player;

import java.util.Date;

public class PlayerGetGamePrizeVO
{
	// id
	private int id;
	// 奖励类型
	private String prizetype;
	// 奖励描述
	private String prizedisplay;
	// 账号
	private String passprot;
	// u_pk
	private int u_pk;
	// 玩家姓名
	private String p_name;
	// ppk
	private int p_pk;
	// 奖励内容
	private String prop;
	// type
	private int state;
	// 时间
	private Date createtime;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getU_pk()
	{
		return u_pk;
	}

	public void setU_pk(int u_pk)
	{
		this.u_pk = u_pk;
	}

	public String getProp()
	{
		return prop;
	}

	public void setProp(String prop)
	{
		this.prop = prop;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}

	public String getPrizetype()
	{
		return prizetype;
	}

	public void setPrizetype(String prizetype)
	{
		this.prizetype = prizetype;
	}

	public String getPrizedisplay()
	{
		return prizedisplay;
	}

	public void setPrizedisplay(String prizedisplay)
	{
		this.prizedisplay = prizedisplay;
	}

	public String getPassprot()
	{
		return passprot;
	}

	public void setPassprot(String passprot)
	{
		this.passprot = passprot;
	}

	public String getP_name()
	{
		return p_name;
	}

	public void setP_name(String p_name)
	{
		this.p_name = p_name;
	}

	public int getP_pk()
	{
		return p_pk;
	}

	public void setP_pk(int p_pk)
	{
		this.p_pk = p_pk;
	}
}
