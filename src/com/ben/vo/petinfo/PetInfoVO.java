/**
 * 
 */
package com.ben.vo.petinfo;

/**
 * @author ��ƾ�
 * 
 * 10:56:18 AM
 */
public class PetInfoVO
{
	/** ID */
	private int petPk;
	/** ��ɫid������ʱ��ʾ�ó����ڲֿ��� */
	private int pPk;
	/** ��Ӧpet�����id */
	private int petId;
	/** �������� */
	private String petName;
	/** �����ǳ� */
	private String petNickname;
	/** �ȼ� */
	private int petGrade;
	/** ���� */
	private int petExp;
	/** ��ǰ���龭�� */
	private int petBenExp;
	/** �¼�����ﵽ��һ����Ҫ�ľ��� */
	private int petXiaExp;
	/** ��С���� */
	private int petGjXiao;
	/** ��󹥻� */
	private int petGjDa;
	/** �����۸� */
	private int petSale;
	/** ����ͼƬ */
	private String petImg;
	/** ����ɳ��ʡ� */
	private double petGrow;
	/** �������Խ�=1��ľ=2��ˮ=3����=4����=5 */
	private int petWx;
	/** ��������ֵ */
	private int petWxValue;
	/** ����1 ��ѧϰ�ļ���id */
	private int petSkillOne;
	/** ����2 ��ѧϰ�ļ���id */
	private int petSkillTwo;
	/** ����3 ��ѧϰ�ļ���id */
	private int petSkillThree;
	/** ����4 ��ѧϰ�ļ���id */
	private int petSkillFour;
	/** ����5 ��ѧϰ�ļ���id */
	private int petSkillFive;
	/** ����* */
	private int petLife;
	/** ���� �Ƿ����Ȼ���� */
	private int petIsAutoGrow;

	/** �Ƿ�������:1��ʾ��ս��״̬��0��ʾ�� */
	private int petIsBring;
	/** ƣ�Ͷ�0-100,��ս״̬������ƣ�Ͷȣ�һ��Сʱ��10�� */
	private int petFatigue;
	/** �������� */
	private int petLonge;
	/** ������������ʹ�ô��� */
	private int longeNumber;
	/** ���������Ѿ�ʹ�ô��� */
	private int longeNumberOk;
	/** �������������ѧϰ���ٸ����� */
	private int skillControl;
	/** ���ﱻ��ʼ������ */
	private int petInitNum;

	private int petType;

	/** ���ﱩ���� */
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