package com.ls.pub.util.school;

import org.apache.log4j.Logger;


/**
 * ����:�������ɵ���ع���
 * @author ��˧
 * 7:18:37 AM
 */
public class ShaolinUtil
{
	/**
	 * ���ֳɳ�ϵ��
	 */
	public static final double COEFFICIENT=1.2;
	
	public static Logger logger =  Logger.getLogger(ShaolinUtil.class);

	
	/**
	 * ͨ����ʽ�õ�����  ������=Power(����,0.4)*50-(60/�ȼ�)*16-�����ȼ�-30��*���ȼ�-30��*ϵ��/100��+�ȼ�*ϵ����
	 * @return
	 */
	public int getGJByExpressions(int force,int level)
	{
		logger.info( "������"+ force + "���ȼ���:"+ level);
		
		double gj = 0;
		
		gj = Math.pow(force, 0.4)*50 - (60/level)*16 - ( (level-30)* (level-30)*COEFFICIENT/100 ) + level*COEFFICIENT;
		
		logger.info("ͨ����ʽ�õ��Ĺ�������:"+ (int)gj);
		
		return (int)gj;
	}
	
	/**
	 *  ���� = Power(����,0.4)*50-(60*ϵ��/�ȼ�)*16-�����ȼ�-30��*���ȼ�-30��*ϵ��/100����
	 */
	public int getFYByExpressions(int agile,int level)
	{
		logger.info( "������"+ agile + "���ȼ���:"+ level);
		
		double fy = 0;
		
		fy = Math.pow(agile, 0.4)*50 - (60/level)*16 - ( (level-30)* (level-30)*COEFFICIENT/100 );
		
		logger.info("ͨ����ʽ�õ��ķ�����:"+ (int)fy);
		
		return (int)fy;
	}
	
	/**
	 * Ѫ�� = 180+��10*����ǰ����-11����+��30+��2*����ǰ����-11������*6*��(1-�ȼ�/60)+ϵ����
	 */
	public int getHPByExpressions( int level)
	{
		logger.info( "�ȼ���:"+ level);
		double hp = 0;
		
		hp = 180+(10*(level-11)) +(30+(2*(level-11)))*6*((1-level/60)+COEFFICIENT);
		
		logger.info("ͨ����ʽ�õ���hp��:"+ (int)hp);
		
		return (int)hp;
	}
	
	/**
	 * ���� = ��ʼ����+��ÿ����������*����ǰ����-11����+��ÿ����������*��ǰ�ȼ�*ϵ����
	 */
	public int getMPByExpressions( int savvy ,int level)
	{
		logger.info( "������"+ savvy + "���ȼ���:"+ level);
		double mp = 0;
		
		//mp = 
		
		logger.info("ͨ����ʽ�õ���mp��:"+ (int)mp);
		
		return (int)mp;
	}
}
