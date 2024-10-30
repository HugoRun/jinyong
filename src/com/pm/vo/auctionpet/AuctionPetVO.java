/**
 * 
 */
package com.pm.vo.auctionpet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangjj
 *
 */
public class AuctionPetVO
{

	/** 拍卖宠物id */
	private int auctionId;
	/** 拍卖宠物状态 */
	private int auctionStatus;
	/** 宠物拍卖金钱 */
	private int petPrice;
	/** 宠物拍卖时间 */
	private String petAuctionTime;
	/** 角色宠物ID */
	private int petPk;
	/** 角色id */
	private int pPk;
	/** 对应pet表里的id */
	private int petId;
	/** 宠物名称 */
	private String petName;
	/** 宠物昵称 */
	private String petNickname;
	/** 等级 */
	private int petGrade;
	/** 经验 */
	private String petExp;
	/**当前经验经验*/
	private String petBenExp;
	/** 下级经验达到下一级需要的经验 */
	private String petXiaExp;
	/** 最小攻击 */
	private int petGjXiao;
	/** 最大攻击 */
	private int petGjDa;
	/** 卖出价格 */
	private int petSale;
	/** 宠物图片 */
	private String petImg;
	/** 宠物成长率” */
	private double petGrow;
	/** 五行属性金=1，木=2，水=3，火=4，土=5 */
	private int petWx;
	/** 五行属性值 */
	private int petWxValue;
	/** 技能1 可学习的技能id */
	private int petSkillOne;
	/** 技能2 可学习的技能id */
	private int petSkillTwo;
	/** 技能3 可学习的技能id */
	private int petSkillThree;
	/** 技能4 可学习的技能id */
	private int petSkillFour;
	/** 技能5 可学习的技能id */
	private int petSkillFive;
	/** 寿命* */
	private int petLife;
	/** 升级 是否可自然升级 */
	private int petIsAutoGrow;

	/** 是否在身上:1表示在战斗状态，0表示否 */
	private int petIsBring;
	/** 疲劳度0-100,出战状态下增加疲劳度，一个小时加10点 */
	private int petFatigue;
	/** 宠物寿命 */
	private int petLonge;
	/** 增加寿命道具使用次数 */
	private int longeNumber;
	/** 寿命道具已经使用次数 */
	private int longeNumberOk;
	/**这个宠物最多可以学习多少个技能*/
	private int skillControl;
	/** 宠物类型 */
	private int petType;
	/** 宠物已经被初始化多少次 */
	private int petInitNum;
	/** 宠物暴击率 */
	private double petViolenceDrop;
	
	
	public double getPetViolenceDrop()
	{
		return petViolenceDrop;
	}
	public void setPetViolenceDrop(double petViolenceDrop)
	{
		this.petViolenceDrop = petViolenceDrop;
	}
	public int getAuctionId()
	{
		return auctionId;
	}
	public void setAuctionId(int auctionId)
	{
		this.auctionId = auctionId;
	}
	public int getAuctionStatus()
	{
		return auctionStatus;
	}
	public void setAuctionStatus(int auctionStatus)
	{
		this.auctionStatus = auctionStatus;
	}
	public int getPetPrice()
	{
		return petPrice;
	}
	public void setPetPrice(int petPrice)
	{
		this.petPrice = petPrice;
	}
	
	public String getPetAuctionTime()
	{
		return petAuctionTime;
	}
	public void setPetAuctionTime(String petAuctionTime)
	{
		this.petAuctionTime = petAuctionTime;
	}
	public String getPetAuctionTime(String old) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt1 = null;
		try
		{
			dt1 = sf.parse(petAuctionTime);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		int index = sf.format(dt1).indexOf("-");
		return sf.format(dt1).substring(index+1);
	}
	public int getPetPk()
	{
		return petPk;
	}
	public void setPetPk(int petPk)
	{
		this.petPk = petPk;
	}
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public int getPetId()
	{
		return petId;
	}
	public void setPetId(int petId)
	{
		this.petId = petId;
	}
	public String getPetName()
	{
		return petName;
	}
	public void setPetName(String petName)
	{
		this.petName = petName;
	}
	public String getPetNickname()
	{
		return petNickname;
	}
	public void setPetNickname(String petNickname)
	{
		this.petNickname = petNickname;
	}
	public int getPetGrade()
	{
		return petGrade;
	}
	public void setPetGrade(int petGrade)
	{
		this.petGrade = petGrade;
	}
	public String getPetExp()
	{
		return petExp;
	}
	public void setPetExp(String petExp)
	{
		this.petExp = petExp;
	}
	public String getPetBenExp()
	{
		return petBenExp;
	}
	public void setPetBenExp(String petBenExp)
	{
		this.petBenExp = petBenExp;
	}
	public String getPetXiaExp()
	{
		return petXiaExp;
	}
	public void setPetXiaExp(String petXiaExp)
	{
		this.petXiaExp = petXiaExp;
	}
	public int getPetGjXiao()
	{
		return petGjXiao;
	}
	public void setPetGjXiao(int petGjXiao)
	{
		this.petGjXiao = petGjXiao;
	}
	public int getPetGjDa()
	{
		return petGjDa;
	}
	public void setPetGjDa(int petGjDa)
	{
		this.petGjDa = petGjDa;
	}
	public int getPetSale()
	{
		return petSale;
	}
	public void setPetSale(int petSale)
	{
		this.petSale = petSale;
	}
	public String getPetImg()
	{
		return petImg;
	}
	public void setPetImg(String petImg)
	{
		this.petImg = petImg;
	}
	public double getPetGrow()
	{
		return petGrow;
	}
	public void setPetGrow(double petGrow)
	{
		this.petGrow = petGrow;
	}
	public int getPetWx()
	{
		return petWx;
	}
	public String getZWPetWx()
	{
		String str = "";
		int petWx = getPetWx();
		switch(petWx){
			case 1:
				str = "金";
				break;
			case 2:
				str = "木";
				break;
			case 3:
				str = "水";
				break;
			case 4:
				str = "火";
				break;
			case 5:
				str = "土";
				break;
			case 0:
				str = "暂无五行";
				break;
		}
		return str;
	}
	public void setPetWx(int petWx)
	{
		this.petWx = petWx;
	}
	public int getPetWxValue()
	{
		return petWxValue;
	}
	public void setPetWxValue(int petWxValue)
	{
		this.petWxValue = petWxValue;
	}
	public int getPetSkillOne()
	{
		return petSkillOne;
	}
	public void setPetSkillOne(int petSkillOne)
	{
		this.petSkillOne = petSkillOne;
	}
	public int getPetSkillTwo()
	{
		return petSkillTwo;
	}
	public void setPetSkillTwo(int petSkillTwo)
	{
		this.petSkillTwo = petSkillTwo;
	}
	public int getPetSkillThree()
	{
		return petSkillThree;
	}
	public void setPetSkillThree(int petSkillThree)
	{
		this.petSkillThree = petSkillThree;
	}
	public int getPetSkillFour()
	{
		return petSkillFour;
	}
	public void setPetSkillFour(int petSkillFour)
	{
		this.petSkillFour = petSkillFour;
	}
	public int getPetSkillFive()
	{
		return petSkillFive;
	}
	public void setPetSkillFive(int petSkillFive)
	{
		this.petSkillFive = petSkillFive;
	}
	public int getPetLife()
	{
		return petLife;
	}
	public void setPetLife(int petLife)
	{
		this.petLife = petLife;
	}
	public int getPetType()
	{
		return petType;
	}
	public void setPetType(int petType)
	{
		this.petType = petType;
	}
	public int getPetIsBring()
	{
		return petIsBring;
	}
	public void setPetIsBring(int petIsBring)
	{
		this.petIsBring = petIsBring;
	}
	public int getPetFatigue()
	{
		return petFatigue;
	}
	public void setPetFatigue(int petFatigue)
	{
		this.petFatigue = petFatigue;
	}
	public int getPetLonge()
	{
		return petLonge;
	}
	public void setPetLonge(int petLonge)
	{
		this.petLonge = petLonge;
	}
	public int getLongeNumber()
	{
		return longeNumber;
	}
	public void setLongeNumber(int longeNumber)
	{
		this.longeNumber = longeNumber;
	}
	public int getLongeNumberOk()
	{
		return longeNumberOk;
	}
	public void setLongeNumberOk(int longeNumberOk)
	{
		this.longeNumberOk = longeNumberOk;
	}
	public int getSkillControl()
	{
		return skillControl;
	}
	public void setSkillControl(int skillControl)
	{
		this.skillControl = skillControl;
	}
	public int getPetIsAutoGrow()
	{
		return petIsAutoGrow;
	}
	public void setPetIsAutoGrow(int petIsAutoGrow)
	{
		this.petIsAutoGrow = petIsAutoGrow;
	}
	public int getPetInitNum()
	{
		return petInitNum;
	}
	public void setPetInitNum(int petInitNum)
	{
		this.petInitNum = petInitNum;
	}
	
}
