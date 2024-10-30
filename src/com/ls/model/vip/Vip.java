package com.ls.model.vip;

import com.ls.ben.vo.info.partinfo.PartInfoVO;

/**
 * @author ls
 * 会员信息
 */
public class Vip
{
	private int level = 1;
	private String name="普通成员";
	private String des="";
	private int discount = 100;
	private int salary=0;//工资
	private String hint = "";
	private int appendRate=0;//属性追加百分比
	
	public Vip(int level) throws Exception
	{
		this.level = level;
		switch(level)
		{
			case VipManager.LEVEL_1:
				name = "洪荒会员";
				discount = 90;
				salary = 188;
				hint = "恭喜成为"+name;
				appendRate = 5;
				break;
			case VipManager.LEVEL_2:
				name = "鸿蒙会员";
				discount = 80;
				salary = 888;
				hint = "恭喜成为"+name;
				appendRate = 15;
				break;
			default:
				throw new Exception("非法等级");
		}
	}

	/**
	 * 给玩家加载会员附加属性
	 * @param player
	 */
	public void loadPropertys( PartInfoVO player )
	{
		if( player==null )
		{
			return;
		}
		
		player.setGjSubjoin(player.getGjSubjoin() + this.appendRate);
    	player.setFySubjoin(player.getFySubjoin() + this.appendRate);
		
		player.setPUpHp(player.getPUpHp()+(int)Math.round(player.getPUpHp()*this.appendRate/100));
		player.setPUpMp(player.getPUpMp()+(int)Math.round(player.getPUpMp()*this.appendRate/100));
		player.setDropMultiple(player.getDropMultiple() + player.getDropMultiple()*this.appendRate/100);
		
	}
	
	public int getLevel()
	{
		return level;
	}

	public String getName()
	{
		return name;
	}

	public String getDes()
	{
		return des;
	}

	public int getDiscount()
	{
		return discount;
	}

	public int getSalary()
	{
		return salary;
	}

	public String getHint()
	{
		return hint;
	}

}
