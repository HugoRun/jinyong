package com.ls.pub.util.school;

import org.apache.log4j.Logger;


/**
 * 功能:少林门派的相关工具
 * @author 刘帅
 * 7:18:37 AM
 */
public class ShaolinUtil
{
	/**
	 * 少林成长系数
	 */
	public static final double COEFFICIENT=1.2;
	
	public static Logger logger =  Logger.getLogger(ShaolinUtil.class);

	
	/**
	 * 通过公式得到攻击  攻击力=Power(力量,0.4)*50-(60/等级)*16-（（等级-30）*（等级-30）*系数/100）+等级*系数；
	 * @return
	 */
	public int getGJByExpressions(int force,int level)
	{
		logger.info( "力量是"+ force + "；等级是:"+ level);
		
		double gj = 0;
		
		gj = Math.pow(force, 0.4)*50 - (60/level)*16 - ( (level-30)* (level-30)*COEFFICIENT/100 ) + level*COEFFICIENT;
		
		logger.info("通过公式得到的攻击力是:"+ (int)gj);
		
		return (int)gj;
	}
	
	/**
	 *  防御 = Power(敏捷,0.4)*50-(60*系数/等级)*16-（（等级-30）*（等级-30）*系数/100）；
	 */
	public int getFYByExpressions(int agile,int level)
	{
		logger.info( "敏捷是"+ agile + "；等级是:"+ level);
		
		double fy = 0;
		
		fy = Math.pow(agile, 0.4)*50 - (60/level)*16 - ( (level-30)* (level-30)*COEFFICIENT/100 );
		
		logger.info("通过公式得到的防御是:"+ (int)fy);
		
		return (int)fy;
	}
	
	/**
	 * 血气 = 180+（10*（当前级别-11））+（30+（2*（当前级别-11）））*6*（(1-等级/60)+系数）
	 */
	public int getHPByExpressions( int level)
	{
		logger.info( "等级是:"+ level);
		double hp = 0;
		
		hp = 180+(10*(level-11)) +(30+(2*(level-11)))*6*((1-level/60)+COEFFICIENT);
		
		logger.info("通过公式得到的hp是:"+ (int)hp);
		
		return (int)hp;
	}
	
	/**
	 * 内力 = 初始内力+（每级增加内力*（当前级别-11））+（每级增加悟性*当前等级*系数）
	 */
	public int getMPByExpressions( int savvy ,int level)
	{
		logger.info( "悟性是"+ savvy + "；等级是:"+ level);
		double mp = 0;
		
		//mp = 
		
		logger.info("通过公式得到的mp是:"+ (int)mp);
		
		return (int)mp;
	}
}
