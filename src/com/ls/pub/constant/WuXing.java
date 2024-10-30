/**
 * 
 */
package com.ls.pub.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * ����:
 * @author ��˧
 *
 * 11:15:15 AM
 */
public class WuXing {
	public static final int JIN = 1;
	public static final int MU = 2;
	public static final int SHUI = 3;
	public static final int HUO = 4;
	public static final int TU = 5;
	public static final int SHEN = 6;
	public static final Map<Integer,Integer> wx_map= new HashMap<Integer,Integer>(5);
	
	static
	{
		wx_map.put(JIN, JIN);
		wx_map.put(MU, MU);
		wx_map.put(SHUI, SHUI);
		wx_map.put(HUO, HUO);
		wx_map.put(TU, TU);
	}
	
	public static String getWxStr(int wx)
	{
		if( wx==JIN )
		{
			return "��";
		}
		else if( wx==MU )
		{
			return "ľ";
		}
		else if( wx==SHUI )
		{
			return "ˮ";
		}
		else if( wx==HUO )
		{
			return "��";
		}
		else if( wx==TU )
		{
			return "��";
		}
		else if( wx==SHEN )
		{
			return "��";
		}
		return "��";
	}
}
