package com.ben.guaji.vo;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class GuajiVo implements Comparable<GuajiVo>, Serializable
{
	//id
	private long id;
	// 挂机角色id
	private int p_pk;
	// 怪物id
	private int npc_id;
	// 挂机时间(分钟)
	private int time;
	// 挂机方式
	private int guaji_type;
	// 挂机选择获得物品
	private Set<GoodVo> good = new TreeSet<GoodVo>();
	// 挂机获得装备品质选择,0没有设置，1为全部，2为优，3为精，4为极
	private int level;
	//开始时间
	private String start_time;

	public String getStart_time()
	{
		return start_time;
	}
	public void setStart_time(String start_time)
	{
		this.start_time = start_time;
	}
	public GuajiVo(){
		super();
	}
	public GuajiVo(int p_pk, int npc_id)
	{
		super();
		this.p_pk = p_pk;
		this.npc_id = npc_id;
	}

	public int getP_pk()
	{
		return p_pk;
	}

	public void setP_pk(int p_pk)
	{
		this.p_pk = p_pk;
	}

	public int getNpc_id()
	{
		return npc_id;
	}

	public void setNpc_id(int npc_id)
	{
		this.npc_id = npc_id;
	}

	public int getTime()
	{
		return time;
	}

	public void setTime(int time)
	{
		this.time = time;
	}

	public int getGuaji_type()
	{
		return guaji_type;
	}

	public void setGuaji_type(int guaji_type)
	{
		this.guaji_type = guaji_type;
	}

	public Set<GoodVo> getGood()
	{
		return good;
	}

	public void setGood(Set<GoodVo> good)
	{
		this.good = good;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public void addGood(GoodVo good)
	{
		this.good.add(good);
	}

	public int compareTo(GuajiVo o)
	{
		if (o.getP_pk() == this.p_pk||o.getId()==this.id)
		{
			return 0;
		}
		return -1;
	}
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}

}
