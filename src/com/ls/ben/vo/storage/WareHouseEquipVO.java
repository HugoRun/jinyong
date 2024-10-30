/**
 * 
 */
package com.ls.ben.vo.storage;

/**
 * @author zhangjj
 *
 */
public class WareHouseEquipVO
{
	/** ��ɫװ���ֿ�� */
	private int wPk;
	/** ��ɫid */
	private int pPk;
	/** ��Ʒ��ر����� */
	private int tableType;
	/** ��Ʒ���� */
	private int goodsType;
	/** ��ƷID */
	private int wId;
	/** ��Ʒ���� */
	private String wName;
	/** �;� */
	private int wDurability;
	/** �;����� */
	private int wDuraConsume;
	/** �� */
	private int wBonding;
	/** ����������С���� */
	private int wFyXiao;
	/** �������������� */
	private int wFyDa;
	/** ����������С���� */
	private int wGjXiao;
	/** ����������󹥻� */
	private int wGjDa;
	/** ����������Ѫ */
	private int wHp;
	/** �������Է��� */
	private int wMp;
	/** �������Խ������ */
	private int wJinFy;
	/** ��������ľ������ */
	private int wMuFy;
	/** ��������ˮ������ */
	private int wShuiFy;
	/** �������Ի������ */
	private int wHuoFy;
	/** ���������������� */
	private int wTuFy;
	/** �������Խ𹥻��� */
	private int wJinGj;
	/** ��������ľ������ */
	private int wMuGj;
	/** ��������ˮ������ */
	private int wShuiGj;
	/** �������Ի𹥻��� */
	private int wHuoGj;
	/** ���������������� */
	private int wTuGj;
	/** �Ƿ�װ�� 0 û�� 1��װ���� */
	private int wType;
	
	/**Ʒ��*/
	private int wQuality;
	
	/** ��װid */
	private int suitId;
	/**װ����������*/
	private int wWxType;
	/**����buff�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч*/
	private int   wBuffIsEffected;
	
	
	/**����*/
	private int wProtect;
	/**����ȷ��*/
	private int wIsreconfirm;

	/**������Ǯ*/
	private int wPrice;
	
	/** װ���㻯������������  **/
	private String enchantType;
	
	
	/** װ���㻯������������  **/
	private int enchantValue;
	
	/** ׷�ӵ�Ѫ�� */
	private int wZjHp;
	/** ׷�ӵ����� */
	private int wZjMp;
	/** ׷�ӵĹ��� **/
	private int wZjWxGj;
	/**  ׷�ӵķ��� **/
	private int wZjWxFy;
	
	/** װ���ĵȼ� */
	private int wZbGrade;
	/** ����ʱ�� */
	private String createTime;
	
	/** װ������ʹ�� */
	private int pPoss;
	/**����󶨴���*/
	private int wBondingNum;
	
	/**��������**/
	private int specialcontent;
	
	public int getWBondingNum()
	{
		return wBondingNum;
	}

	public void setWBondingNum(int bondingNum)
	{
		wBondingNum = bondingNum;
	}

	public int getPPoss()
	{
		return pPoss;
	}

	public void setPPoss(int poss)
	{
		pPoss = poss;
	}

	public int getWZbGrade()
	{
		return wZbGrade;
	}

	public void setWZbGrade(int zbGrade)
	{
		wZbGrade = zbGrade;
	}

	public int getWPk()
	{
		return wPk;
	}

	public void setWPk(int pk)
	{
		wPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getTableType()
	{
		return tableType;
	}

	public void setTableType(int tableType)
	{
		this.tableType = tableType;
	}

	public int getGoodsType()
	{
		return goodsType;
	}

	public void setGoodsType(int goodsType)
	{
		this.goodsType = goodsType;
	}

	public int getWId()
	{
		return wId;
	}

	public void setWId(int id)
	{
		wId = id;
	}

	public String getWName()
	{
		return wName;
	}

	public void setWName(String name)
	{
		wName = name;
	}

	public int getWDurability()
	{
		return wDurability;
	}

	public void setWDurability(int durability)
	{
		wDurability = durability;
	}

	public int getWDuraConsume()
	{
		return wDuraConsume;
	}

	public void setWDuraConsume(int duraConsume)
	{
		wDuraConsume = duraConsume;
	}

	public int getWBonding()
	{
		return wBonding;
	}

	public void setWBonding(int bonding)
	{
		wBonding = bonding;
	}

	public int getWFyXiao()
	{
		double fyXiao = wFyXiao*(1+0.025*wZbGrade);
		return (int)fyXiao;
	}
	
	public int getWFyXiaoYuan()
	{
		return wFyXiao;
	}

	public void setWFyXiao(int fyXiao)
	{
		wFyXiao = fyXiao;
	}

	public int getWFyDa()
	{
		double fyDa = wFyDa*(1+0.025*wZbGrade);
		return (int)fyDa;
	}
	
	public int getWFyDaYuan()
	{
		return wFyDa;
	}
	
	public void setWFyDa(int fyDa)
	{
		wFyDa = fyDa;
	}

	public int getWGjXiao()
	{
		double gjXiao = wGjXiao*(1+0.1*wZbGrade);
		return (int)gjXiao;
	}
	
	public int getWGjXiaoYuan()
	{
		return wGjXiao;
	}

	public void setWGjXiao(int gjXiao)
	{
		wGjXiao = gjXiao;
	}

	public int getWGjDa()
	{
		double gjDa = wGjDa*(1+0.1*wZbGrade);
		return (int)gjDa;
	}
	
	public int getWGjDaYuan()
	{
		return wGjDa;
	}

	public void setWGjDa(int gjDa)
	{
		wGjDa = gjDa;
	}

	public int getWHp()
	{
		return wHp;
	}

	public void setWHp(int hp)
	{
		wHp = hp;
	}

	public int getWMp()
	{
		return wMp;
	}

	public void setWMp(int mp)
	{
		wMp = mp;
	}

	public int getWJinFy()
	{
		return wJinFy;
	}

	public void setWJinFy(int jinFy)
	{
		wJinFy = jinFy;
	}

	public int getWMuFy()
	{
		return wMuFy;
	}

	public void setWMuFy(int muFy)
	{
		wMuFy = muFy;
	}

	public int getWShuiFy()
	{
		return wShuiFy;
	}

	public void setWShuiFy(int shuiFy)
	{
		wShuiFy = shuiFy;
	}

	public int getWHuoFy()
	{
		return wHuoFy;
	}

	public void setWHuoFy(int huoFy)
	{
		wHuoFy = huoFy;
	}

	public int getWTuFy()
	{
		return wTuFy;
	}

	public void setWTuFy(int tuFy)
	{
		wTuFy = tuFy;
	}

	public int getWJinGj()
	{
		return wJinGj;
	}

	public void setWJinGj(int jinGj)
	{
		wJinGj = jinGj;
	}

	public int getWMuGj()
	{
		return wMuGj;
	}

	public void setWMuGj(int muGj)
	{
		wMuGj = muGj;
	}

	public int getWShuiGj()
	{
		return wShuiGj;
	}

	public void setWShuiGj(int shuiGj)
	{
		wShuiGj = shuiGj;
	}

	public int getWHuoGj()
	{
		return wHuoGj;
	}

	public void setWHuoGj(int huoGj)
	{
		wHuoGj = huoGj;
	}

	public int getWTuGj()
	{
		return wTuGj;
	}

	public void setWTuGj(int tuGj)
	{
		wTuGj = tuGj;
	}

	public int getWType()
	{
		return wType;
	}

	public void setWType(int type)
	{
		wType = type;
	}

	public int getWQuality()
	{
		return wQuality;
	}

	public void setWQuality(int quality)
	{
		wQuality = quality;
	}

	public int getWWxType()
	{
		return wWxType;
	}

	public void setWWxType(int wxType)
	{
		wWxType = wxType;
	}

	public int getWBuffIsEffected()
	{
		return wBuffIsEffected;
	}

	public void setWBuffIsEffected(int buffIsEffected)
	{
		wBuffIsEffected = buffIsEffected;
	}

	public int getWProtect()
	{
		return wProtect;
	}

	public void setWProtect(int protect)
	{
		wProtect = protect;
	}

	public int getWIsreconfirm()
	{
		return wIsreconfirm;
	}

	public void setWIsreconfirm(int isreconfirm)
	{
		wIsreconfirm = isreconfirm;
	}

	public int getWPrice()
	{
		return wPrice;
	}

	public void setWPrice(int price)
	{
		wPrice = price;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public int getSuitId()
	{
		return suitId;
	}

	public void setSuitId(int suitId)
	{
		this.suitId = suitId;
	}

	

	public String getEnchantType()
	{
		return enchantType;
	}

	public void setEnchantType(String enchantType)
	{
		this.enchantType = enchantType;
	}

	public int getEnchantValue()
	{
		return enchantValue;
	}

	public void setEnchantValue(int enchantValue)
	{
		this.enchantValue = enchantValue;
	}

	public int getWZjHp()
	{
		return wZjHp;
	}

	public void setWZjHp(int zjHp)
	{
		wZjHp = zjHp;
	}

	public int getWZjMp()
	{
		return wZjMp;
	}

	public void setWZjMp(int zjMp)
	{
		wZjMp = zjMp;
	}



	public int getWZjWxGj()
	{
		return wZjWxGj;
	}

	public void setWZjWxGj(int zjWxGj)
	{
		wZjWxGj = zjWxGj;
	}

	public int getWZjWxFy()
	{
		return wZjWxFy;
	}

	public void setWZjWxFy(int zjWxFy)
	{
		wZjWxFy = zjWxFy;
	}

	public int getSpecialcontent()
	{
		return specialcontent;
	}

	public void setSpecialcontent(int specialcontent)
	{
		this.specialcontent = specialcontent;
	}

}
