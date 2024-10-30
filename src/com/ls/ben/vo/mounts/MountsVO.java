package com.ls.ben.vo.mounts;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;

/**
 * 坐骑实体类  对应u_mounts_table表
 * @author Thomas.lei
 *
 */
public class MountsVO
{
	/***坐骑ID***/
	private int id;
	/***坐骑名称***/
	private String name;
	/***坐骑等级***/
	private int level;
	/***坐骑类型1走兽2飞禽3鳞甲***/
	private int type;
	/***可传送次数***/
	private int carryNum1;
	/***超过可传送次数需要的付费***/
	private int overPay1;
	/***可传送次数***/
	private int carryNum2;
	/***超过免费传送次数每次的付费***/
	private int overPay2;
	/***升到本级需要的钱数***/
	private int uplevelPay;
	/***图片***/
	private String image;
	/***坐骑描述***/
	private String display;
	/***坐骑的功能描述，就是可传送的场景次数等***/
	private String functionDisplay;
	/***坐骑的buff效果***/
	private String buff;
	/***坐骑的卖出价格***/
	private int sentPrice;
	/***坐骑可升级到下一级的ID，目前为2个，用逗号隔开，随机生成***/
	private String nextLevelID;
	/***坐骑可升到最高的等级限制***/
	private int hightLevel;
	/**得到坐骑图片**/
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
