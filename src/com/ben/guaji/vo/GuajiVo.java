package com.ben.guaji.vo;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class GuajiVo implements Comparable<GuajiVo>, Serializable
{
	//id
	private long id;
	// �һ���ɫid
	private int p_pk;
	// ����id
	private int npc_id;
	// �һ�ʱ��(����)
	private int time;
	// �һ���ʽ
	private int guaji_type;
	// �һ�ѡ������Ʒ
	private Set<GoodVo> good = new TreeSet<GoodVo>();
	// �һ����װ��Ʒ��ѡ��,0û�����ã�1Ϊȫ����2Ϊ�ţ�3Ϊ����4Ϊ��
	private int level;
	//��ʼʱ��
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
