package com.ls.ben.vo.info.skill;

import org.apache.log4j.Logger;

import com.ls.pub.util.MathUtil;

public class SkillVO
{
	Logger logger = Logger.getLogger(SkillVO.class);

	/** 技能标识ID */
	private int skId;
	/** 技能名称 */
	private String skName;
	/** 技能描述 对技能的描述 */
	private String skDisplay;
	/** 技能类型 主动=1，被动=0 2为绝学玄冥神功 3为绝学嫁衣神功 4为七伤拳 */
	private int skType;
	/** 消耗对象 消耗hp或mp */
	private String skExpend;
	/** 消耗数值 使用技能消耗对象的数值 */
	private int skUsecondition;

	/** 伤害值 有伤害的最低伤害和最高伤害 */
	private int skDamageDi;
	/** 伤害值 有伤害的最低伤害和最高伤害 */
	private int skDamageGao;
	/** 技能伤害值，随机取得在最小伤害和最大伤害之间 */
	private int skDamage;
	/** 使用范围 作用对象的个数 */
	private int skArea;

	/** 暴击率 技能的暴击几率 */
	private String skBaolv;
	/** 击晕几率 技能的击晕几率 */
	private int skYun;
	/** 击晕回合 */
	private int skYunBout;
	/** buff效果 buff效果id */
	private int skBuff;
	/** buff使用几率 */
	private int skBuffProbability;
	/** 冷却时间 再次使用该技能的间隔时间 */
	private int skLqtime;

	/** ****攻击力加成系数****** */

	private double skGjMultiple;
	/** ****防御力加成系数****** */

	private double skFyMultiple;
	/** ****HP加成系数****** */

	private double skHpMultiple;
	/** ****MP加成系数****** */

	private double skMpMultiple;
	/** ****暴击率加成系数****** */

	private double skBjMultiple;
	/** ****攻击力附加数值****** */

	private int skGjAdd;
	/** ****防御力附加数值****** */

	private int skFyAdd;
	/** ****HP加成附加数值****** */

	private int skHpAdd;
	/** ****MP加成附加数值****** */

	private int skMpAdd;
	/** 技能组 */
	private int skGroup;
	/** 本级熟练度 */
	private int skSleight;
	/** 升到下一级熟练度 */
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
			logger.debug("技能伤害值未被赋值");
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
