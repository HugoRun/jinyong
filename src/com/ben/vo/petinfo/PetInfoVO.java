/**
 * 
 */
package com.ben.vo.petinfo;

/**
 * @author 侯浩军
 * 
 * 10:56:18 AM
 */
public class PetInfoVO
{
	/** ID */
	private int petPk;
	/** 角色id，负数时表示该宠物在仓库里 */
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
	private int petExp;
	/** 当前经验经验 */
	private int petBenExp;
	/** 下级经验达到下一级需要的经验 */
	private int petXiaExp;
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
	/** 这个宠物最多可以学习多少个技能 */
	private int skillControl;
	/** 宠物被初始化次数 */
	private int petInitNum;

	private int petType;

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

	public int getPetBenExp()
	{
		return petBenExp;
	}

	public void setPetBenExp(int petBenExp)
	{
		this.petBenExp = petBenExp;
	}

	public int getSkillControl()
	{
		return skillControl;
	}

	public void setSkillControl(int skillControl)
	{
		this.skillControl = skillControl;
	}

	public int getLongeNumberOk()
	{
		return longeNumberOk;
	}

	public void setLongeNumberOk(int longeNumberOk)
	{
		this.longeNumberOk = longeNumberOk;
	}

	public int getLongeNumber()
	{
		return longeNumber;
	}

	public void setLongeNumber(int longeNumber)
	{
		this.longeNumber = longeNumber;
	}

	public int getPetLonge()
	{
		return petLonge;
	}

	public void setPetLonge(int petLonge)
	{
		this.petLonge = petLonge;
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

		/*String suffix = ExchangeUtil.getSuffixOfNPCNameByWxAndType(petWx,
				petType);
		suffix = StringUtil.gbToISO(suffix);

		if (petSkillOne != 0 || petSkillTwo != 0 || petSkillThree != 0
				|| petSkillFour != 0 || petSkillFive != 0)
		{
			suffix = suffix + "*";
		}

		petName = petName + suffix;*/
		return petName;
	}

	public String getBasicPetName()
	{
		return petName;
	}

	public void setPetName(String petName)
	{
		this.petName = petName;
	}

	public String getPetNickname()
	{

		/*String suffix = ExchangeUtil.getSuffixOfNPCNameByWxAndType(petWx,
				petType);
		suffix = StringUtil.gbToISO(suffix);
		if (petSkillOne != 0 || petSkillTwo != 0 || petSkillThree != 0
				|| petSkillFour != 0 || petSkillFive != 0)
		{
			suffix = suffix + "*";+ suffix
		}*/
		return petNickname ;
	}

	public String getBasciPetNickname()
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

	public int getPetExp()
	{
		return petExp;
	}

	public void setPetExp(int petExp)
	{
		this.petExp = petExp;
	}

	public int getPetXiaExp()
	{
		return petXiaExp;
	}

	public void setPetXiaExp(int petXiaExp)
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
