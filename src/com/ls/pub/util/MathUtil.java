package com.ls.pub.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.ls.iface.function.Probability;


/**
 * 功能:数学工具类
 * @author 刘帅
 * 8:47:25 AM
 */
public class MathUtil {
	
	//普通概率分母100
	public static final int DENOMINATOR=100;
	
	//掉落概率分母1百万
	public static final int DROPDENOMINATOR=1000000;
	
	public static Logger logger =  Logger.getLogger("log.service");
	
	/**
	 * 得到oldValue增加rate百分比后的值
	 * @param oldValue
	 * @param rate		rate为百分比的分子
	 * @return				返回double类型的值
	 */
	public static double getDoubleValueByAddRate( double oldValue,int rate )
	{
		return oldValue + oldValue*rate/100;
	}
	
	/**
	 * 得到oldValue增加rate百分比后的值(四舍五入)
	 * @param oldValue
	 * @param rate		rate为百分比的分子
	 * @return			返回int类型的值
	 */
	public static int getIntegetValueByAddRate(double oldValue,int rate)
	{
		double result = getDoubleValueByAddRate(oldValue,rate);
		return (int) Math.round(result);
	}
	
	/**
	 * x,y之间产生随机数,包括x和y;x<y
	 */
	public static int getRandomBetweenXY(int x, int y) {

		//logger.debug("x="+x+";y="+y);
		y = y + 1;//y+1保证所取的数值包括y
		return (int) (x + Math.random() * (y - x));
	}

	/**
	 * x,y之间产生随机数,包括x和y;x<y
	 */
	public static double getRandomDoubleXY(double x, double y) {

		//logger.debug("x="+x+";y="+y);
		//y = y + 1;//y+1保证所取的数值包括y
		return (x + Math.random()* (y - x));
	}
	/**
	 * 判断是否按(分子/分母)的概率出现 isAppearByPercentage
	 * 
	 * @numerator  分子 PercentageRandomByParam
	 * denominator 分母
	 * @return boolean
	 */
	public static boolean isAppearByPercentage(int numerator,int denominator) {
		
		logger.debug("分子:"+numerator+";分母:" + denominator);
		int random = (int) (Math.random() * denominator);
		
		logger.debug("产生的随机数是:"+random);
		if ( numerator>=denominator || (0 <= random && random < numerator)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否按(分子/分母)的概率出现 isAppearByPercentage
	 * 分母为100
	 * @numerator  分子 PercentageRandomByParam
	 * @return boolean
	 */
	public static boolean isAppearByPercentage(int numerator) {
		
		logger.debug("分子:"+numerator+";分母:" + DENOMINATOR);
		int random = (int) (Math.random() * DENOMINATOR);
		
		logger.debug("产生的随机数是:"+random);
		if (0 <= random && random < numerator) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 判断是否按(分子/分母)的概率出现
	 * 
	 * @numerator  分子
	 * denominator 分母
	 * @return boolean
	 */
	public static boolean isAppearByPercentage(float numerator,int denominator) {
		
		logger.debug("分子:"+numerator+";分母:" + denominator);
		float random = (float) (Math.random() * denominator);
		
		logger.debug("产生的随机数是:"+random);
		if (0 <= random && random < numerator) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是否按分子/分母的概率出现
	 * 
	 * @numerator  分子
	 * denominator 分母
	 * @return boolean
	 */
	public static boolean PercentageRandomByParamdouble(double numerator,int denominator) {
		
		logger.debug("分子:"+numerator+";分母:" + denominator);
		double random = (Math.random() * denominator);
		
		logger.debug("产生的随机数是:"+random);
		if (0 <= random && random < numerator) {
			return true;
		}
		return false;
	}
	/**
	 * 从字符串中取出一个随机数
	 * 字符串如:1,5;则取出1到5之间的随机数，要求前面的数比后面的数小
	 * @param str
	 * @return
	 */
	public static int getValueByStr(String str)
	{
		if( str==null )
		{
			return 0;
		}
		logger.debug("数量范围字符串:"+str);
		int result = -1;
		String temp[] = str.split(",");
		if( temp.length != 2 )
		{
			logger.debug("字符串格式错误，字符串为:"+str);
			return result;
		}
		int x,y;
		try
		{
			x = Integer.parseInt(temp[0]);
			y = Integer.parseInt(temp[1]);
		}
		catch(Exception e)
		{
			logger.debug("字符串格式错误，字符串为:"+str);
			return result;
		}
		result = MathUtil.getRandomBetweenXY(x, y);
		logger.debug("从字符串中取出一个随机数:"+result);
		return result;
	}
	
	
	/**
	 * probalilityEntitys为一组概率实体，搜有概率相加为100,按它们的概率值取出一个
	 * @param probalilityEntitys 
	 * （12.18号修改, 将此概率更改为由参数probalilityv来控制）
	 * @return
	 */
	public static Probability getRandomEntityFromList( List probalilityEntitys,int probalility )
	{
		if( probalilityEntitys==null || probalilityEntitys.size()==0 )
		{
			logger.debug("list为空");
			return null;
		}
		Probability probalilityEntity = null;
		int random = (int) (Math.random() * probalility);
		logger.debug("产生随机数:"+random);
		int min_limit = 0;
		int max_limit = 0;
		
		for( int i=0;i<probalilityEntitys.size();i++ )
		{
			probalilityEntity = (Probability)probalilityEntitys.get(i);
			
			min_limit = max_limit;
			max_limit = max_limit + probalilityEntity.getProbability();
			logger.debug("当前范围,下限:"+min_limit+";上限:"+max_limit);
			if( random>min_limit && random <= max_limit )
			{
				return probalilityEntity;
			}
		}
		
		return probalilityEntity;
	}
	
	/**
	 * values是值列表，probalilitys是概率参照表
	 * @param values               
	 * @param probalilitys
	 * @return
	 */
	public static String getRandomValueByProbalilitys( String[] values, String[] probalilitys )
	{
		if( values==null || probalilitys==null )
		{
			logger.debug("参数为空");
			return null;
		}
		
		if( values.length <=0 || probalilitys.length <= 0 || values.length!=probalilitys.length )
		{
			logger.debug("参数错误");
			return null;
		}
		
		int probalility = 0;
		int random = (int) (Math.random() * 100);
		logger.debug("产生随机数:"+random);
		int min_limit = 0;
		int max_limit = 0;
		
		for( int i=0;i<probalilitys.length;i++ )
		{
			probalility = Integer.parseInt(probalilitys[i]);
			
			min_limit = max_limit;
			max_limit = max_limit + probalility;
			logger.debug("当前范围,下限:"+min_limit+";上限:"+max_limit);
			if( random>min_limit && random<max_limit )
			{
				return values[i];
			}
		}
		
		return null;
	}
	/**
	 * 取出一个1—100的随机数字
	 * @numerator  100
	 * @return int
	 */
	public static int getRandomNum() {
		
		logger.debug("得到一个1-100的随机数字");
		int random = (int) (Math.random() * DENOMINATOR);
		
		logger.debug("产生的随机数是:"+random);
		return random;
	}
	
	public static void main(String[] args) {
		
		////System.out.print(MathUtil.getValueByStr("2,4"));
	}
	
}
