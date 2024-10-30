package com.web.jieyi.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.lw.vo.ttbox.TTBOXVO;

public class Constant
{
	public static Map<Integer,Integer> EXP_SHARE = new LinkedHashMap<Integer, Integer>();
	
	public static Map<Integer, JieyiVo> JIEYI_MAP = new HashMap<Integer, JieyiVo>();

	public static Map<Integer, List<PlayerPropGroupVO>> USER_GOOD = new HashMap<Integer, List<PlayerPropGroupVO>>();

	public static String JIEYI_IDS = "";
	
	public static String JIEHUN_IDS = "";

	// �����������ܶ�
	public final static int JIEYI_DEAR_NEED = 500;

	// ����������ܶ�
	public final static int JIEHUN_DEAR_NEED = 1500;

	// �����������
	public final static int JIEHUN_MONEY_NEED = 88800;

	public final static String CAN_NOT_USE_XINYINFU = "�Բ�������û�н�飬����ʹ����ӡ��";

	//����ָid
	public final static int MERRY_GIFT = 846;
	
	public final static String OTHER_MERRY_GIFT = "846,3028,4207,4208,2125,0,0";
	
	//���ƺ�
	public final static int MAN_HONOR = 30;
	
	public final static int WOMAN_HONOR = 31;
	
	public final static int EVERY_PAGE_COUNT = 7;
	
	public final static int INIT_LOVE_DEAR = 288;
	
	
	public final static int JIEYI_LEVEL_LIMIT = 20;
	
	public final static int JIEHUN_LEVEL_LIMIT = 20;
	
	//ǿ���������Ԫ��
	public final static int LIHUN_YUANBAO_COST = 1000;
	
	
	
	
	static{
		EXP_SHARE.put(18000, 200);
		EXP_SHARE.put(10000, 180);
		EXP_SHARE.put(6000, 160);
		EXP_SHARE.put(3000, 140);
		EXP_SHARE.put(1500, 120);
		EXP_SHARE.put(500, 100);
	}
	
	//�������������Ѫֵ
	public final static int FUQI_TEAM_ADD_PUPHP = 5;
	
	//������Ϣ����
	public static Map<Integer, List<UMessageInfoVO>> UMESSAGE = new LinkedHashMap<Integer, List<UMessageInfoVO>>(1500);
	//�ι���
	public static Map<Integer, Map<Integer, String>> SCRTCHTICKETMAP = new HashMap<Integer, Map<Integer, String>>();
	//½���±���
	public static Map<Integer, Map<Integer, TTBOXVO>> TTBOXMAP = new HashMap<Integer, Map<Integer, TTBOXVO>>();
	//����ת��
	public static Map<Integer, Map<Integer, TTBOXVO>> WXCONVERSION = new HashMap<Integer, Map<Integer, TTBOXVO>>();
	/*//u_pkp_pk
	public static Map<Integer, Integer> U_PK_P_PK = new HashMap<Integer, Integer>();*/
	//pet
	public static Map<Integer, NpcFighter> PETNPC = new HashMap<Integer, NpcFighter>();
	//menpainpc
	public static Map<Integer, Integer> MENPAINPC = new HashMap<Integer, Integer>();
	
}
