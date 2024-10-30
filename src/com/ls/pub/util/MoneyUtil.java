package com.ls.pub.util;

import com.ls.pub.config.GameConfig;

/**
 * ����:��Ǯ��������
 * @author ��˧
 * 10:56:58 AM
 */
public class MoneyUtil
{
	/**
	 * ��ת����ϵ��
	 */
	public static final int MODULUS = 100;
	/**
	 * ����װ���������ַ���
	 * @param copper
	 * @return
	 */
	public static String changeCopperToStr(int copper)
	{
		return copper+GameConfig.getMoneyUnitName();
	}
	
	/**
	 * ����װ���������ַ���
	 * @param copper
	 * @return
	 */
	public static String changeCopperToStr(long copper)
	{
		/*String silver_str = "";
		String copper_str = "";
		if( copper<=0 )
		{
			return "0��";
		}
			
		long silver = copper/MODULUS;
		if( silver>0 )
		{
			silver_str = silver+"��";
		}
		copper = copper%MODULUS;
		if( copper>0 )
		{
			copper_str = copper+"��";
		}*/
		
		return copper+GameConfig.getMoneyUnitName();
	}
	
	/**
	 * ����װ���������ַ���
	 * @param copper
	 * @return
	 */
	public static String changeCopperToStr(String copper_str)
	{
		if( copper_str==null )
		{
			return "copperΪ��";
		}
		long copper = 0 ;
		try {
			copper = Long.parseLong(copper_str.trim());
		} catch (NumberFormatException e) {
			
			return "copper�ַ�����ʽ����";
		}
		
		return changeCopperToStr(copper);
	}
	
	/**
	 * ������װ������
	 * @param silver
	 * @param copper
	 * @return
	 */
	public static long changeSilverAndCopperToCopper( int silver,int copper ) 
	{
		return silver*MODULUS+copper;
	}
	
	/**
	 * ��֤��Ǯ�Ƿ��ǺϷ��ַ���:
	 * �����ַ���Ϊ���Ǹ�����
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
