package com.ben.lost;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.ls.ben.vo.menu.OperateMenuVO;

public class LostConstant
{
	public static Map<Integer, Compass> COMPASS_MAP = new HashMap<Integer, Compass>();

	public static Date LOST_END_TIME;

	public final static int LOST_RETURN_SCENE = 210;

	public final static Map<Integer, List<Integer>> JIANGLI = new HashMap<Integer, List<Integer>>();

	public static Map<Integer, OperateMenuVO> SHEARE_MENU = new HashMap<Integer, OperateMenuVO>();

	public static List<Integer> USE_SHEARE = new ArrayList<Integer>();

	public static int currentMenuId = 0;
	
	public static Map<Integer,List<Integer>> USE_OLD_XIANG = new HashMap<Integer, List<Integer>>();//�ƾ�����ʹ��
	
	public static Timer END_LOST_TIMER ;//�Թ�������ʱ��
	
	public static Timer LAST_FIVE_TIMER;//�Թ�����5���Ӷ�ʱ��
	
	public static int LAST_MIN = 5;//ʣ�༸����ʱ������ʱ��

}
