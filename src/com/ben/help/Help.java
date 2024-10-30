package com.ben.help;

import java.io.Serializable;

import com.ben.shitu.model.Shitu;

public class Help implements Serializable, Comparable<Help>
{
	private int id;//�˵�id
	private int super_id;//������id
	private String name;//��������
	private String des;//��������
	private int shunxu;//����
	private int scene_id;//���͵ص�
	private int level_limit;//���͵ȼ�
	private int type;//�˵�����
	private String link_name;//��������
	private int task_men;//��������:�Ƿ��Ǳ����ɣ�0��Ҫ��1���̣�2ؤ�3����
	private String task_zu;//�����������
	
	public String getTask_zu()
	{
		return task_zu;
	}
	public void setTask_zu(String task_zu)
	{
		this.task_zu = task_zu;
	}
	public String getLink_name()
	{
		return link_name;
	}
	public void setLink_name(String link_name)
	{
		this.link_name = link_name;
	}
	public int getTask_men()
	{
		return task_men;
	}
	public void setTask_men(int task_men)
	{
		this.task_men = task_men;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getSuper_id()
	{
		return super_id;
	}
	public void setSuper_id(int super_id)
	{
		this.super_id = super_id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDes()
	{
		return des;
	}
	public void setDes(String des)
	{
		this.des = des;
	}
	public int getShunxu()
	{
		return shunxu;
	}
	public void setShunxu(int shunxu)
	{
		this.shunxu = shunxu;
	}
	public int getScene_id()
	{
		return scene_id;
	}
	public void setScene_id(int scene_id)
	{
		this.scene_id = scene_id;
	}
	public int getLevel_limit()
	{
		return level_limit;
	}
	public void setLevel_limit(int level_limit)
	{
		this.level_limit = level_limit;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((des == null) ? 0 : des.hashCode());
		result = prime * result + id;
		result = prime * result + level_limit;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + scene_id;
		result = prime * result + shunxu;
		result = prime * result + super_id;
		result = prime * result + type;
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Help other = (Help) obj;
		if (des == null)
		{
			if (other.des != null)
				return false;
		}
		else
			if (!des.equals(other.des))
				return false;
		if (id != other.id)
			return false;
		if (level_limit != other.level_limit)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else
			if (!name.equals(other.name))
				return false;
		if (scene_id != other.scene_id)
			return false;
		if (shunxu != other.shunxu)
			return false;
		if (super_id != other.super_id)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	public int compareTo(Help help)
	{
		if(help.getId()==this.id){
		return 0;
		}else
		return -1;
	}
}
