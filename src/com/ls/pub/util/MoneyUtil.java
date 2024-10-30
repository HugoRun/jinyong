package com.ls.pub.util;

import com.ls.pub.config.GameConfig;

/**
 * 功能:金钱帮助工具
 * @author 刘帅
 * 10:56:58 AM
 */
public class MoneyUtil
{
	/**
	 * 文转银的系数
	 */
	public static final int MODULUS = 100;
	/**
	 * 把文装换成银文字符串
	 * @param copper
	 * @return
	 */
	public static String changeCopperToStr(int copper)
	{
		return copper+GameConfig.getMoneyUnitName();
	}
	
	/**
	 * 把文装换成银文字符串
	 * @param copper
	 * @return
	 */
	public static String changeCopperToStr(long copper)
	{
		/*String silver_str = "";
		String copper_str = "";
		if( copper<=0 )
		{
			return "0文";
		}
			
		long silver = copper/MODULUS;
		if( silver>0 )
		{
			silver_str = silver+"两";
		}
		copper = copper%MODULUS;
		if( copper>0 )
		{
			copper_str = copper+"文";
		}*/
		
		return copper+GameConfig.getMoneyUnitName();
	}
	
	/**
	 * 把文装换成银文字符串
	 * @param copper
	 * @return
	 */
	public static String changeCopperToStr(String copper_str)
	{
		if( copper_str==null )
		{
			return "copper为空";
		}
		long copper = 0 ;
		try {
			copper = Long.parseLong(copper_str.trim());
		} catch (NumberFormatException e) {
			
			return "copper字符串格式错误";
		}
		
		return changeCopperToStr(copper);
	}
	
	/**
	 * 把银文装换成文
	 * @param silver
	 * @param copper
	 * @return
	 */
	public static long changeSilverAndCopperToCopper( int silver,int copper ) 
	{
		return silver*MODULUS+copper;
	}
	
	/**
	 * 验证金钱是否是合法字符串:
	 * 规则：字符串为，非负整数
	 */
	public static boolean validateMoneyStr(String money_str)
	{
		if( money_str==null )
		{
			return false;
		}
		
		int money = -1;
		
		try
		{
			money = Integer.parseInt(money_str);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			return false;
		}
		
		if( money<0 )
		{
			return false;
		}
		
		return true;
			
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(MoneyUtil.changeSilverAndCopperToCopper(2, 44));
		//System.out.println(MoneyUtil.changeCopperToStr(220227427));
	}

}
