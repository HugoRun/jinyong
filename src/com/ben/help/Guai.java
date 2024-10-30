package com.ben.help;

public class Guai
{
	private int guai_id;
	private String name;
	private int level;
	private String scene_name;
	private int dropImprot;
	
	/**
	 * 得到描述
	 * @return
	 */
	public String getDes()
	{
		StringBuffer sb = new StringBuffer();
		if( dropImprot==3 || dropImprot==5 )//表示宝箱掉落
		{
			sb.append(this.name);
		}
		else
		{
			sb.append(this.name).append("(").append(level).append("级,").append(scene_name).append(")");
		}
		
		return sb.toString();
	}
	
	public int getGuai_id()
	{
		return guai_id;
	}
	public void setGuai_id(int guai_id)
	{
		this.guai_id = guai_id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getLevel()
	{
		return level;
	}
	public void setLevel(int level)
	{
		this.level = level;
	}
	public String getScene_name()
	{
		return scene_name;
	}
	public void setScene_name(String scene_name)
	{
		this.scene_name = scene_name;
	}

	public int getDropImprot()
	{
		return dropImprot;
	}

	public void setDropImprot(int dropImprot)
	{
		this.dropImprot = dropImprot;
	}
}
