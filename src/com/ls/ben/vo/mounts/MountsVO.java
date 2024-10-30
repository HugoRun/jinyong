package com.ls.ben.vo.mounts;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;

/**
 * ����ʵ����  ��Ӧu_mounts_table��
 * @author Thomas.lei
 *
 */
public class MountsVO
{
	/***����ID***/
	private int id;
	/***��������***/
	private String name;
	/***����ȼ�***/
	private int level;
	/***��������1����2����3�ۼ�***/
	private int type;
	/***�ɴ��ʹ���***/
	private int carryNum1;
	/***�����ɴ��ʹ�����Ҫ�ĸ���***/
	private int overPay1;
	/***�ɴ��ʹ���***/
	private int carryNum2;
	/***������Ѵ��ʹ���ÿ�εĸ���***/
	private int overPay2;
	/***����������Ҫ��Ǯ��***/
	private int uplevelPay;
	/***ͼƬ***/
	private String image;
	/***��������***/
	private String display;
	/***����Ĺ������������ǿɴ��͵ĳ���������***/
	private String functionDisplay;
	/***�����buffЧ��***/
	private String buff;
	/***����������۸�***/
	private int sentPrice;
	/***�������������һ����ID��ĿǰΪ2�����ö��Ÿ������������***/
	private String nextLevelID;
	/***�����������ߵĵȼ�����***/
	private int hightLevel;
	/**�õ�����ͼƬ**/
	public  String getPicStr(RoleEntity roleEntity)
	{
		if(roleEntity==null)
		{
			return "";
		}
		StringBuffer returnstr = new StringBuffer();;
		if(this.image!=null&&!this.image.equals("")&&roleEntity.getSettingInfo().getPetPic()!=-1){
			returnstr.append("<img alt='mounts' src='").append(GameConfig.getGameUrl()).append("/image/mount/").append(this.image).append(".png").append("' /> ");
		}
		return returnstr.toString();
	}
	public int getSentPrice()
	{
		return sentPrice;
	}
	public void setSentPrice(int sentPrice)
	{
		this.sentPrice = sentPrice;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
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
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public int getCarryNum1()
	{
		return carryNum1;
	}
	public void setCarryNum1(int carryNum1)
	{
		this.carryNum1 = carryNum1;
	}
	public int getOverPay1()
	{
		return overPay1;
	}
	public void setOverPay1(int overPay1)
	{
		this.overPay1 = overPay1;
	}
	public int getCarryNum2()
	{
		return carryNum2;
	}
	public void setCarryNum2(int carryNum2)
	{
		this.carryNum2 = carryNum2;
	}
	public int getOverPay2()
	{
		return overPay2;
	}
	public void setOverPay2(int overPay2)
	{
		this.overPay2 = overPay2;
	}
	public int getUplevelPay()
	{
		return uplevelPay;
	}
	public void setUplevelPay(int uplevelPay)
	{
		this.uplevelPay = uplevelPay;
	}
	public String getImage()
	{
		return image;
	}
	public void setImage(String image)
	{
		this.image = image;
	}
	public String getDisplay()
	{
		return display;
	}
	public void setDisplay(String display)
	{
		this.display = display;
	}
	public String getFunctionDisplay()
	{
		return functionDisplay;
	}
	public void setFunctionDisplay(String functionDisplay)
	{
		this.functionDisplay = functionDisplay;
	}
	public String getBuff()
	{
		return buff;
	}
	public void setBuff(String buff)
	{
		this.buff = buff;
	}
	public String getNextLevelID()
	{
		return nextLevelID;
	}
	public void setNextLevelID(String nextLevelID)
	{
		this.nextLevelID = nextLevelID;
	}
	public int getHightLevel()
	{
		return hightLevel;
	}
	public void setHightLevel(int hightLevel)
	{
		this.hightLevel = hightLevel;
	}

}
