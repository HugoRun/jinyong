package com.pub;

import com.ls.pub.util.DateUtil;

public class GameArgs
{
	/******pk����֮��ͨPK*******/
	public static final String PK_TYPE_PUTONG="0";
	/*****pk����֮ǿ��PK********/
	public static final String PK_TYPE_QIANGZHI="1";
	/*******pk����֮�PK********/
	public static final String PK_TYPE_ACTIVE="2";
	/*****pk���ؿ�********/
	public static final String PK_SWICH_OPEN="2";
	/*****pk���ع�********/
	public static final String PK_SWICH_CLOSE="1";
	/****pk�������伸��****/
	public static final int PK_DEAD_DROP_RATE=1000;
	public static final int YELLOW_NAME_VALUE=0;//����ֵ
	public static final int RED_NAME_VALUE=50;//����ֵ
	
	public static long CONSUME_TIME_UNIT = 5*DateUtil.MINUTE*1000;//�������ֵ�ĵ�λʱ�䣬5���ӣ���λ���룩
}
