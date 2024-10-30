package com.ben.tiaozhan;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiaozhanConstant
{
	public static Map<Integer, Date> TIAOZHAN_TIME = new HashMap<Integer, Date>();//挑战时间的集合

	public final static int OVER_TIME = 1;// 超时时间，单位为分钟
	
	public static Map<Integer,Integer> TIAOZHAN = new HashMap<Integer, Integer>();//挑战双方的集合
	
	public final static int TIAOZHAN_SCENE = 11227;//挑战的场景
	
	public final static int TIAOZHAN_RETURN_SCENE = 210;//挑战结束返回的场景
}
