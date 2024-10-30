package com.ls.ben.vo.info.skill;

import org.apache.log4j.Logger;

import com.ls.pub.util.MathUtil;

public class SkillVO
{
	Logger logger = Logger.getLogger(SkillVO.class);

	/** ���ܱ�ʶID */
	private int skId;
	/** �������� */
	private String skName;
	/** �������� �Լ��ܵ����� */
	private String skDisplay;
	/** �������� ����=1������=0 2Ϊ��ѧ��ڤ�� 3Ϊ��ѧ������ 4Ϊ����ȭ */
	private int skType;
	/** ���Ķ��� ����hp��mp */
	private String skExpend;
	/** ������ֵ ʹ�ü������Ķ������ֵ */
	private int skUsecondition;

	/** �˺�ֵ ���˺�������˺�������˺� */
	private int skDamageDi;
	/** �˺�ֵ ���˺�������˺�������˺� */
	private int skDamageGao;
	/** �����˺�ֵ�����ȡ������С�˺�������˺�֮�� */
	private int skDamage;
	/** ʹ�÷�Χ ���ö���ĸ��� */
	private int skArea;

	/** ������ ���ܵı������� */
	private String skBaolv;
	/** ���μ��� ���ܵĻ��μ��� */
	private int skYun;
	/** ���λغ� */
	private int skYunBout;
	/** buffЧ�� buffЧ��id */
	private int skBuff;
	/** buffʹ�ü��� */
	private int skBuffProbability;
	/** ��ȴʱ�� �ٴ�ʹ�øü��ܵļ��ʱ�� */
	private int skLqtime;

	/** ****�������ӳ�ϵ��****** */

	private double skGjMultiple;
	/** ****�������ӳ�ϵ��****** */

	private double skFyMultiple;
	/** ****HP�ӳ�ϵ��****** */

	private double skHpMultiple;
	/** ****MP�ӳ�ϵ��****** */

	private double skMpMultiple;
	/** ****�����ʼӳ�ϵ��****** */

	private double skBjMultiple;
	/** ****������������ֵ****** */

	private int skGjAdd;
	/** ****������������ֵ****** */

	private int skFyAdd;
	/** ****HP�ӳɸ�����ֵ****** */

	private int skHpAdd;
	/** ****MP�ӳɸ�����ֵ****** */

	private int skMpAdd;
	/** ������ */
	private int skGroup;
	/** ���������� */
	private int skSleight;
	/** ������һ�������� */
	private int skNextSleight;

	
	
	
	
	public int getSkNextSleight()
	{
		return skNextSleight;
	}

	public void setSkNextSleight(int skNextSleight)
	{
		this.skNextSleight = skNextSleight;
	}

	public int getSkGjAdd()
	{
		return skGjAdd;
	}

	public void setSkGjAdd(int skGjAdd)
	{
		this.skGjAdd = skGjAdd;
	}

	public int getSkFyAdd()
	{
		return skFyAdd;
	}

	public void setSkFyAdd(int skFyAdd)
	{
		this.skFyAdd = skFyAdd;
	}

	public int getSkHpAdd()
	{
		return skHpAdd;
	}

	public void setSkHpAdd(int skHpAdd)
	{
		this.skHpAdd = skHpAdd;
	}

	public int getSkMpAdd()
	{
		return skMpAdd;
	}

	public void setSkMpAdd(int skMpAdd)
	{
		this.skMpAdd = skMpAdd;
	}

	public double getSkGjMultiple()
	{
		return skGjMultiple;
	}

	public void setSkGjMultiple(double skGjMultiple)
	{
		this.skGjMultiple = skGjMultiple;
	}

	public double getSkFyMultiple()
	{
		return skFyMultiple;
	}

	public void setSkFyMultiple(double skFyMultiple)
	{
		this.skFyMultiple = skFyMultiple;
	}

	public double getSkHpMultiple()
	{
		return skHpMultiple;
	}

	public void setSkHpMultiple(double skHpMultiple)
	{
		this.skHpMultiple = skHpMultiple;
	}

	public double getSkMpMultiple()
	{
		return skMpMultiple;
	}

	public void setSkMpMultiple(double skMpMultiple)
	{
		this.skMpMultiple = skMpMultiple;
	}

	public double getSkBjMultiple()
	{
		return skBjMultiple;
	}

	public void setSkBjMultiple(double skBjMultiple)
	{
		this.skBjMultiple = skBjMultiple;
	}

	public int getSkId()
	{
		return skId;
	}

	public void setSkId(int skId)
	{
		this.skId = skId;
	}

	public String getSkName()
	{
		return skName;
	}

	public void setSkName(String skName)
	{
		this.skName = skName;
	}

	public String getSkDisplay()
	{
		return skDisplay;
	}

	public void setSkDisplay(String skDisplay)
	{
		this.skDisplay = skDisplay;
	}

	public int getSkType()
	{
		return skType;
	}

	public void setSkType(int skType)
	{
		this.skType = skType;
	}

	public String getSkExpend()
	{
		return skExpend;
	}

	public void setSkExpend(String skExpend)
	{
		this.skExpend = skExpend;
	}

	public int getSkUsecondition()
	{
		return skUsecondition;
	}

	public void setSkUsecondition(int skUsecondition)
	{
		this.skUsecondition = skUsecondition;
	}

	public int getSkDamageDi()
	{
		return skDamageDi;
	}

	public void setSkDamageDi(int skDamageDi)
	{
		this.skDamageDi = skDamageDi;
	}

	public int getSkDamageGao()
	{
		return skDamageGao;
	}

	public void setSkDamageGao(int skDamageGao)
	{
		this.skDamageGao = skDamageGao;
	}

	public int getSkArea()
	{
		return skArea;
	}

	public void setSkArea(int skArea)
	{
		this.skArea = skArea;
	}


	public int getSkYunBout()
	{
		return skYunBout;
	}

	public void setSkYunBout(int skYunBout)
	{
		this.skYunBout = skYunBout;
	}

	public int getSkBuffProbability()
	{
		return skBuffProbability;
	}

	public void setSkBuffProbability(int skBuffProbability)
	{
		this.skBuffProbability = skBuffProbability;
	}


	public String getSkBaolv()
	{
		return skBaolv;
	}

	public void setSkBaolv(String skBaolv)
	{
		this.skBaolv = skBaolv;
	}

	public int getSkYun()
	{
		return skYun;
	}

	public void setSkYun(int skYun)
	{
		this.skYun = skYun;
	}

	public int getSkBuff()
	{
		return skBuff;
	}

	public void setSkBuff(int skBuff)
	{
		this.skBuff = skBuff;
	}

	public int getSkLqtime()
	{
		return skLqtime;
	}

	public void setSkLqtime(int skLqtime)
	{
		this.skLqtime = skLqtime;
	}

	public int getSkDamage()
	{
		if (skDamageDi == 0 || skDamageGao == 0)
		{
			logger.debug("�����˺�ֵδ����ֵ");
			return -1;
		}
		skDamage = MathUtil.getRandomBetweenXY(skDamageDi, skDamageGao);
		return skDamage;
	}

	public int getSkGroup()
	{
		return skGroup;
	}

	public void setSkGroup(int skGroup)
	{
		this.skGroup = skGroup;
	}

	public int getSkSleight()
	{
		return skSleight;
	}

	public void setSkSleight(int skSleight)
	{
		this.skSleight = skSleight;
	}

}
