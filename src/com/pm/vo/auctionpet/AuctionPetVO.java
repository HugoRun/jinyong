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

	/** ��������id */
	private int auctionId;
	/** ��������״̬ */
	private int auctionStatus;
	/** ����������Ǯ */
	private int petPrice;
	/** ��������ʱ�� */
	private String petAuctionTime;
	/** ��ɫ����ID */
	private int petPk;
	/** ��ɫid */
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
	private String petExp;
	/**��ǰ���龭��*/
	private String petBenExp;
	/** �¼�����ﵽ��һ����Ҫ�ľ��� */
	private String petXiaExp;
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
	/**�������������ѧϰ���ٸ�����*/
	private int skillControl;
	/** �������� */
	private int petType;
	/** �����Ѿ�����ʼ�����ٴ� */
	private int petInitNum;
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
				str = "��";
				break;
			case 2:
				str = "ľ";
				break;
			case 3:
				str = "ˮ";
				break;
			case 4:
				str = "��";
				break;
			case 5:
				str = "��";
				break;
			case 0:
				str = "��������";
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
