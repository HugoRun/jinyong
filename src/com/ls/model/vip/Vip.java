package com.ls.model.vip;

import com.ls.ben.vo.info.partinfo.PartInfoVO;

/**
 * @author ls
 * ��Ա��Ϣ
 */
public class Vip
{
	private int level = 1;
	private String name="��ͨ��Ա";
	private String des="";
	private int discount = 100;
	private int salary=0;//����
	private String hint = "";
	private int appendRate=0;//����׷�Ӱٷֱ�
	
	public Vip(int level) throws Exception
	{
		this.level = level;
		switch(level)
		{
			case VipManager.LEVEL_1:
				name = "��Ļ�Ա";
				discount = 90;
				salary = 188;
				hint = "��ϲ��Ϊ"+name;
				appendRate = 5;
				break;
			case VipManager.LEVEL_2:
				name = "���ɻ�Ա";
				discount = 80;
				salary = 888;
				hint = "��ϲ��Ϊ"+name;
				appendRate = 15;
				break;
			default:
				throw new Exception("�Ƿ��ȼ�");
		}
	}

	/**
	 * ����Ҽ��ػ�Ա��������
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
