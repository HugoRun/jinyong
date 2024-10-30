package com.ls.pub.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.ls.iface.function.Probability;


/**
 * ����:��ѧ������
 * @author ��˧
 * 8:47:25 AM
 */
public class MathUtil {
	
	//��ͨ���ʷ�ĸ100
	public static final int DENOMINATOR=100;
	
	//������ʷ�ĸ1����
	public static final int DROPDENOMINATOR=1000000;
	
	public static Logger logger =  Logger.getLogger("log.service");
	
	/**
	 * �õ�oldValue����rate�ٷֱȺ��ֵ
	 * @param oldValue
	 * @param rate		rateΪ�ٷֱȵķ���
	 * @return				����double���͵�ֵ
	 */
	public static double getDoubleValueByAddRate( double oldValue,int rate )
	{
		return oldValue + oldValue*rate/100;
	}
	
	/**
	 * �õ�oldValue����rate�ٷֱȺ��ֵ(��������)
	 * @param oldValue
	 * @param rate		rateΪ�ٷֱȵķ���
	 * @return			����int���͵�ֵ
	 */
	public static int getIntegetValueByAddRate(double oldValue,int rate)
	{
		double result = getDoubleValueByAddRate(oldValue,rate);
		return (int) Math.round(result);
	}
	
	/**
	 * x,y֮����������,����x��y;x<y
	 */
	public static int getRandomBetweenXY(int x, int y) {

		//logger.debug("x="+x+";y="+y);
		y = y + 1;//y+1��֤��ȡ����ֵ����y
		return (int) (x + Math.random() * (y - x));
	}

	/**
	 * x,y֮����������,����x��y;x<y
	 */
	public static double getRandomDoubleXY(double x, double y) {

		//logger.debug("x="+x+";y="+y);
		//y = y + 1;//y+1��֤��ȡ����ֵ����y
		return (x + Math.random()* (y - x));
	}
	/**
	 * �ж��Ƿ�(����/��ĸ)�ĸ��ʳ��� isAppearByPercentage
	 * 
	 * @numerator  ���� PercentageRandomByParam
	 * denominator ��ĸ
	 * @return boolean
	 */
	public static boolean isAppearByPercentage(int numerator,int denominator) {
		
		logger.debug("����:"+numerator+";��ĸ:" + denominator);
		int random = (int) (Math.random() * denominator);
		
		logger.debug("�������������:"+random);
		if ( numerator>=denominator || (0 <= random && random < numerator)) {
			return true;
		}
		return false;
	}
	
	/**
	 * �ж��Ƿ�(����/��ĸ)�ĸ��ʳ��� isAppearByPercentage
	 * ��ĸΪ100
	 * @numerator  ���� PercentageRandomByParam
	 * @return boolean
	 */
	public static boolean isAppearByPercentage(int numerator) {
		
		logger.debug("����:"+numerator+";��ĸ:" + DENOMINATOR);
		int random = (int) (Math.random() * DENOMINATOR);
		
		logger.debug("�������������:"+random);
		if (0 <= random && random < numerator) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * �ж��Ƿ�(����/��ĸ)�ĸ��ʳ���
	 * 
	 * @numerator  ����
	 * denominator ��ĸ
	 * @return boolean
	 */
	public static boolean isAppearByPercentage(float numerator,int denominator) {
		
		logger.debug("����:"+numerator+";��ĸ:" + denominator);
		float random = (float) (Math.random() * denominator);
		
		logger.debug("�������������:"+random);
		if (0 <= random && random < numerator) {
			return true;
		}
		return false;
	}
	/**
	 * �ж��Ƿ񰴷���/��ĸ�ĸ��ʳ���
	 * 
	 * @numerator  ����
	 * denominator ��ĸ
	 * @return boolean
	 */
	public static boolean PercentageRandomByParamdouble(double numerator,int denominator) {
		
		logger.debug("����:"+numerator+";��ĸ:" + denominator);
		double random = (Math.random() * denominator);
		
		logger.debug("�������������:"+random);
		if (0 <= random && random < numerator) {
			return true;
		}
		return false;
	}
	/**
	 * ���ַ�����ȡ��һ�������
	 * �ַ�����:1,5;��ȡ��1��5֮����������Ҫ��ǰ������Ⱥ������С
	 * @param str
	 * @return
	 */
	public static int getValueByStr(String str)
	{
		if( str==null )
		{
			return 0;
		}
		logger.debug("������Χ�ַ���:"+str);
		int result = -1;
		String temp[] = str.split(",");
		if( temp.length != 2 )
		{
			logger.debug("�ַ�����ʽ�����ַ���Ϊ:"+str);
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
			logger.debug("�ַ�����ʽ�����ַ���Ϊ:"+str);
			return result;
		}
		result = MathUtil.getRandomBetweenXY(x, y);
		logger.debug("���ַ�����ȡ��һ�������:"+result);
		return result;
	}
	
	
	/**
	 * probalilityEntitysΪһ�����ʵ�壬���и������Ϊ100,�����ǵĸ���ֵȡ��һ��
	 * @param probalilityEntitys 
	 * ��12.18���޸�, ���˸��ʸ���Ϊ�ɲ���probalilityv�����ƣ�
	 * @return
	 */
	public static Probability getRandomEntityFromList( List probalilityEntitys,int probalility )
	{
		if( probalilityEntitys==null || probalilityEntitys.size()==0 )
		{
			logger.debug("listΪ��");
			return null;
		}
		Probability probalilityEntity = null;
		int random = (int) (Math.random() * probalility);
		logger.debug("���������:"+random);
		int min_limit = 0;
		int max_limit = 0;
		
		for( int i=0;i<probalilityEntitys.size();i++ )
		{
			probalilityEntity = (Probability)probalilityEntitys.get(i);
			
			min_limit = max_limit;
			max_limit = max_limit + probalilityEntity.getProbability();
			logger.debug("��ǰ��Χ,����:"+min_limit+";����:"+max_limit);
			if( random>min_limit && random <= max_limit )
			{
				return probalilityEntity;
			}
		}
		
		return probalilityEntity;
	}
	
	/**
	 * values��ֵ�б�probalilitys�Ǹ��ʲ��ձ�
	 * @param values               
	 * @param probalilitys
	 * @return
	 */
	public static String getRandomValueByProbalilitys( String[] values, String[] probalilitys )
	{
		if( values==null || probalilitys==null )
		{
			logger.debug("����Ϊ��");
			return null;
		}
		
		if( values.length <=0 || probalilitys.length <= 0 || values.length!=probalilitys.length )
		{
			logger.debug("��������");
			return null;
		}
		
		int probalility = 0;
		int random = (int) (Math.random() * 100);
		logger.debug("���������:"+random);
		int min_limit = 0;
		int max_limit = 0;
		
		for( int i=0;i<probalilitys.length;i++ )
		{
			probalility = Integer.parseInt(probalilitys[i]);
			
			min_limit = max_limit;
			max_limit = max_limit + probalility;
			logger.debug("��ǰ��Χ,����:"+min_limit+";����:"+max_limit);
			if( random>min_limit && random<max_limit )
			{
				return values[i];
			}
		}
		
		return null;
	}
	/**
	 * ȡ��һ��1��100���������
	 * @numerator  100
	 * @return int
	 */
	public static int getRandomNum() {
		
		logger.debug("�õ�һ��1-100���������");
		int random = (int) (Math.random() * DENOMINATOR);
		
		logger.debug("�������������:"+random);
		return random;
	}
	
	public static void main(String[] args) {
		
		////System.out.print(MathUtil.getValueByStr("2,4"));
	}
	
}
