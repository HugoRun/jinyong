package com.ben.vo.task;

/**
 * ����:
 * @author ��ƾ� 10:58:10 AM
 */
public class TaskVO {
	public final static String taskZu = "sy_zhuxian,sx_zhuxian,st_zhuxian,yzs_zhuxian,jzz_zhuxian";
	/** �����ʶ */
	private int tId;
	/** ������ͬһϵ������ʹ��һ��������code */
	private String tZu;
	/** �����������ʾ�ڴ�����group�е�λ�� */
	private String tZuxl;
	/** ������ */
	private String tName;
	/** ������ȡ��С�ȼ� */
	private int tLevelXiao;
	/** ������ȡ���ȼ� */
	private int tLevelDa;
	/** ����ƺ����� */
	private String tSchool;
	/** �������� */
	private int tType;
	/** ��ʼ����԰� */
	private String tDisplay;
	/** ��ʼ������ʾ */
	private String tTishi;
	/**��ʼ������ʾ�Ĺؼ��֣������,�ŷָ�*/
	private String tKey;
	/**�ؼ������ڵĵ�ͼ�������,�ŷָ�*/
	private String tKeyValue;
	/** ����ʼ������� */
	private String tGeidj;
	/** ����ʼ����������� */
	private String tGeidjNumber;

	/** �м�� ���ж���м��Ӧ�ã����� */
	private String tPoint;
	/** ͨ���м������� */
	private String tZjms;
	/** ����ͨ���м������� */
	private String tBnzjms;
	/** ͨ���м����Ҫ����Ʒ */
	private String tZjdwp;
	/** ͨ���м����Ҫ��Ʒ������ */
	private int tZjdwpNumber;
	/** ͨ���м���Ƿ�ɾ���������0��ɾ��1ɾ�� */
	private int tDjsc;

	/** ͨ���м�������Ʒ */
	private String tMidstGs;

	/** ���������Ҫ���� */
	private String tGoods;
	/** ���������Ҫ�������� */
	private String tGoodsNumber;
	/** ���������Ҫװ�� */
	private String tGoodszb;
	/** ���������Ҫװ������ */
	private String tGoodszbNumber;

	/** ���������Ҫ��ɱ¾ */
	private String tKilling;
	/** ���������Ҫ��ɱ¾���� */
	private int tKillingNo;
	/** ��������Ǯ���� */
	private String tMoney;
	/** ��������齱�� */
	private String tExp;
	/** ������������������� */
	private int tSwType;
	/** ��������������� */
	private String tSw;
	/** ���ʽ��� */
	private String tEncouragement;
	/** ���ʽ��������� */
	private String tWncouragementNo;
	/** ��һ������ʼnpc��id */
	private int tXrwnpcId;
	/** ��һ������id */
	private int tNext;

	/** ���ʽ��� */
	private String tEncouragementZb;
	/** ���ʽ��������� */
	private String tEncouragementNoZb;
	/** �Ƿ��ѭ���������� 0 ������ѭ�� 1����ѭ�� */
	private int tCycle;
	
	/**
	 * �Ƿ������һ������
	 * @return
	 */
	public boolean isLast()
	{
		if( this.tId==this.tNext )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public String getTKey()
	{
		return tKey;
	}

	public void setTKey(String key)
	{
		tKey = key;
	}

	public String getTKeyValue()
	{
		return tKeyValue;
	}

	public void setTKeyValue(String keyValue)
	{
		tKeyValue = keyValue;
	}

	public int getTCycle()
	{
		return tCycle;
	}

	public void setTCycle(int cycle)
	{
		tCycle = cycle;
	}

	public String getTEncouragementZb()
	{
		return tEncouragementZb;
	}

	public void setTEncouragementZb(String encouragementZb)
	{
		tEncouragementZb = encouragementZb;
	}

	public String getTEncouragementNoZb()
	{
		return tEncouragementNoZb;
	}

	public void setTEncouragementNoZb(String encouragementNoZb)
	{
		tEncouragementNoZb = encouragementNoZb;
	}

	public String getTMidstGs()
	{
		return tMidstGs;
	}

	public void setTMidstGs(String midstGs)
	{
		tMidstGs = midstGs;
	}


	public int getTId()
	{
		return tId;
	}

	public String getTGeidj()
	{
		return tGeidj;
	}

	public void setTGeidj(String geidj)
	{
		tGeidj = geidj;
	}

	public String getTGeidjNumber()
	{
		return tGeidjNumber;
	}

	public void setTGeidjNumber(String geidjNumber)
	{
		tGeidjNumber = geidjNumber;
	}


	public String getTGoodsNumber()
	{
		return tGoodsNumber;
	}

	public void setTGoodsNumber(String goodsNumber)
	{
		tGoodsNumber = goodsNumber;
	}

	public String getTGoodszb()
	{
		return tGoodszb;
	}

	public void setTGoodszb(String goodszb)
	{
		tGoodszb = goodszb;
	}

	public String getTGoodszbNumber()
	{
		return tGoodszbNumber;
	}

	public void setTGoodszbNumber(String goodszbNumber)
	{
		tGoodszbNumber = goodszbNumber;
	}

	public void setTId(int id)
	{
		tId = id;
	}

	public String getTZu()
	{
		return tZu;
	}

	public void setTZu(String zu)
	{
		tZu = zu;
	}

	public String getTZuxl()
	{
		return tZuxl;
	}

	public void setTZuxl(String zuxl)
	{
		tZuxl = zuxl;
	}

	public String getTName()
	{
		return tName;
	}

	public void setTName(String name)
	{
		tName = name;
	}

	public int getTLevelXiao()
	{
		return tLevelXiao;
	}

	public void setTLevelXiao(int levelXiao)
	{
		tLevelXiao = levelXiao;
	}

	public int getTLevelDa()
	{
		return tLevelDa;
	}

	public void setTLevelDa(int levelDa)
	{
		tLevelDa = levelDa;
	}


	public String getTSchool()
	{
		return tSchool;
	}

	public void setTSchool(String school)
	{
		tSchool = school;
	}

	public int getTType()
	{
		return tType;
	}

	public void setTType(int type)
	{
		tType = type;
	}


	public String getTDisplay()
	{
		return tDisplay;
	}

	public void setTDisplay(String display)
	{
		tDisplay = display;
	}

	public String getTTishi()
	{
		return tTishi;
	}

	public void setTTishi(String tishi)
	{
		tTishi = tishi;
	}

	public String getTPoint()
	{
		return tPoint;
	}

	public void setTPoint(String point)
	{
		tPoint = point;
	}

	public String getTZjms()
	{
		return tZjms;
	}

	public void setTZjms(String zjms)
	{
		tZjms = zjms;
	}

	public String getTBnzjms()
	{
		return tBnzjms;
	}

	public void setTBnzjms(String bnzjms)
	{
		tBnzjms = bnzjms;
	}

	public String getTZjdwp()
	{
		return tZjdwp;
	}

	public void setTZjdwp(String zjdwp)
	{
		tZjdwp = zjdwp;
	}

	public int getTZjdwpNumber()
	{
		return tZjdwpNumber;
	}

	public void setTZjdwpNumber(int zjdwpNumber)
	{
		tZjdwpNumber = zjdwpNumber;
	}

	public int getTDjsc()
	{
		return tDjsc;
	}

	public void setTDjsc(int djsc)
	{
		tDjsc = djsc;
	}

	public String getTGoods()
	{
		return tGoods;
	}

	public void setTGoods(String goods)
	{
		tGoods = goods;
	}

	public String getTKilling()
	{
		return tKilling;
	}

	public void setTKilling(String killing)
	{
		tKilling = killing;
	}

	public int getTKillingNo()
	{
		return tKillingNo;
	}

	public void setTKillingNo(int killingNo)
	{
		tKillingNo = killingNo;
	}


	public String getTMoney()
	{
		return tMoney;
	}

	public void setTMoney(String money)
	{
		tMoney = money;
	}

	public String getTExp()
	{
		return tExp;
	}

	public void setTExp(String exp)
	{
		tExp = exp;
	}

	public int getTSwType()
	{
		return tSwType;
	}

	public void setTSwType(int swType)
	{
		tSwType = swType;
	}

	public String getTSw()
	{
		return tSw;
	}

	public void setTSw(String sw)
	{
		tSw = sw;
	}

	public String getTEncouragement()
	{
		return tEncouragement;
	}

	public void setTEncouragement(String encouragement)
	{
		tEncouragement = encouragement;
	}

	public String getTWncouragementNo()
	{
		return tWncouragementNo;
	}

	public void setTWncouragementNo(String wncouragementNo)
	{
		tWncouragementNo = wncouragementNo;
	}

	public int getTXrwnpcId()
	{
		return tXrwnpcId;
	}

	public void setTXrwnpcId(int xrwnpcId)
	{
		tXrwnpcId = xrwnpcId;
	}

	public int getTNext()
	{
		return tNext;
	}

	public void setTNext(int next)
	{
		tNext = next;
	}
}
