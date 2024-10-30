package com.ben.rank.model;

import java.util.HashMap;
import java.util.Map;

import com.ls.pub.config.GameConfig;

public class RankConstants
{
	public static Map<String, Integer> FILED_TYPE = new HashMap<String, Integer>();

	//���й�������
	public static Map<String,String> DETAIL = new HashMap<String, String>();
	//�����е�һ������
	public static Map<String,String> FIRST_DES = new HashMap<String, String>();
	
	//���˰�
	public final static String DENGJI = "r_p_exp";//�ȼ�
	public final static String KILL = "r_kill";//ɱ�ְ�
	public final static String DEAR = "r_dear";//����
	public final static String EVIL = "r_evil";//���˰�
	public final static String GLORY = "r_glory";//������
	public final static String MONEY = "r_money";//������
	public final static String YUANBAO = "r_yuanbao";//������
	public final static String CREDIT = "r_credit";//������
	public final static String VIP = "r_vip";//����
	public final static String BANGKILL = "r_bangkill";//��Ѫ��
	public final static String KILLNPC = "r_killnpc";//ɱ�ְ�
	public final static String WEI_TASK = "r_wei_task";//������
	public final static String DEAD = "r_dead";//������
	public final static String KILLBOSS = "r_killboss";//��ɱ��
	public final static String ZHONG = "r_zhong";//�����
	public final static String SALE = "r_sale";//�����
	public final static String YI = "r_yi";//������
	public final static String MENG = "r_meng";//�ͽ���
	public final static String SHENG = "r_sheng";//����ʥ��
	public final static String BOYI = "r_boyi";//���İ�
	public final static String LOST = "r_lost";//�Թ����а�
	public final static String KILLLANG = "r_killlang";//ɱ��ǧ���ɾ�
	public final static String LANGJUN = "r_langjun";//��Ϊǧ���ɾ�
	public final static String ANS = "r_ans";//�����
	public final static String OPEN = "r_open";//�����
	
	//�����
	public final static String CHONGWU = "p_chongwu";//����
	public final static String PET = "p_pet";//���
	//���ɰ�
	public final static String F_PRESTIGE = "f_prestige";//����������
	public final static String F_ZHANLI = "f_zhanli";//����ս����
	public final static String F_RICH = "f_rich";//���ɲƸ���
	//װ����
	public final static String SHENBING = "e_shenbing";//�����
	public final static String XIANJIA = "e_xianjia";//�ɼװ�
	public final static String FABAO = "e_fabao";//������
	public final static String ZUOQI = "e_zuoqi";//�����

	static
	{
		int i = 1;
		FILED_TYPE.put(DENGJI, i++);
		FILED_TYPE.put(KILL, i++);
		FILED_TYPE.put(DEAR, i++);
		FILED_TYPE.put(EVIL, i++);
		FILED_TYPE.put(GLORY, i++);
		FILED_TYPE.put(MONEY,i++);  //6
		FILED_TYPE.put(YUANBAO, i++);
		FILED_TYPE.put(CREDIT, i++);//8
		FILED_TYPE.put(OPEN, i++);
		FILED_TYPE.put(VIP, i++);//10
		FILED_TYPE.put(BANGKILL, i++);
		FILED_TYPE.put(KILLNPC, i++);
		FILED_TYPE.put(WEI_TASK, i++);
		FILED_TYPE.put(DEAD, i++);//14
		FILED_TYPE.put(KILLBOSS, i++);
		FILED_TYPE.put(ZHONG, i++);
		FILED_TYPE.put(SALE, i++);//17
		FILED_TYPE.put(YI, i++);
		FILED_TYPE.put(ANS, i++);
		FILED_TYPE.put(MENG, i++);
		FILED_TYPE.put(SHENBING, i++);
		FILED_TYPE.put(CHONGWU, i++);//22
		FILED_TYPE.put(PET, i++);//23
		FILED_TYPE.put(SHENG, i++);
		FILED_TYPE.put(BOYI, i++);
		FILED_TYPE.put(LOST, i++);
		FILED_TYPE.put(KILLLANG, i++);
		FILED_TYPE.put(LANGJUN, i++);
		FILED_TYPE.put(XIANJIA, 29);
		FILED_TYPE.put(FABAO, 30);
		FILED_TYPE.put(ZUOQI, 31);
		
		DETAIL.put(DENGJI,"���ȼ���*���ݵȼ��ߵͽ�������*��ÿ�����ã�");
		DETAIL.put(KILL, "��ս����*����ɱ����Ŀ��������*��ÿ�����ã�");
		DETAIL.put(DEAR, "�������*�������ܶȽ���˫���������*��ÿ�����ã�");
		DETAIL.put(EVIL, "��Ѫս��*�������ֵ�������е�Ѫս��*��ÿ�����ã�");
		DETAIL.put(GLORY, "��������*���ݻ�ɱ�Է���Ӫ����������������*��ÿ�����ã�");
		DETAIL.put(MONEY, "����ʯ��*����"+GameConfig.getMoneyUnitName()+"����������*");
		DETAIL.put(YUANBAO, "���ɾ���*����"+GameConfig.getYuanbaoName()+"���Ľ�������*��ÿ�����ã�");
		DETAIL.put(CREDIT, "��������*����ʦͽ��ϵ��������������*");
		
		DETAIL.put(VIP, "������*���ݻ�Ա�����ʱ����������*��ÿ�����ã�");
		DETAIL.put(BANGKILL, "����Ѫ��*���ݰ���ɱ�����ܺͽ�������*��ÿ�����ã�");
		DETAIL.put(KILLNPC, "��ɱ�ְ�*����ɱ�ֵ�����������*��ÿ�����ã�");
		DETAIL.put(WEI_TASK, "��������*���ݻ�õ�������������*");
		DETAIL.put(DEAD, "��������*���ݱ�ɱ���Ĵ�����������*��ÿ�����ã�");
		DETAIL.put(KILLBOSS,"����ɱ��*���ݻ�ɱ����Boss�ĵ�����������*��ÿ�����ã�");
		DETAIL.put(ZHONG, "�������*��������ʱ����������*��ÿ�����ã�");
		DETAIL.put(SALE, "�������*���������ɹ�������������*��ÿ�����ã�");
		DETAIL.put(YI, "��������*���������Ƚ���˫���������*��ÿ�����ã�");
		DETAIL.put(MENG, "���ͽ���*������ɸ����ĵ�����������*��ÿ�����ã�");
		DETAIL.put(SHENG,"������ʥ��*���ݾ���ֵ������ʱ�估����������������*");
		DETAIL.put(BOYI, "�����ʰ�*���ݲ�Ʊ�н�����������*");
		DETAIL.put(LOST, "��̽�հ�*���ݱ����ۼ�����Թ��Ĳ�����������*��ÿ�����ã�");
		DETAIL.put(KILLLANG, "���񲶰�*����ɱ����ǧ���ɾ�������������*");
		DETAIL.put(LANGJUN, "�������*���ݳɹ����׷ɱ������������*");
		
		//װ����
		DETAIL.put(SHENBING,"�������*���������Ĺ��������Թ����������е�*");
		FIRST_DES.put(SHENBING,"����������");
		DETAIL.put(FABAO,"��������*���ݷ����Ĺ������������Թ��������������е�*");
		FIRST_DES.put(FABAO,"������鱦��");
		DETAIL.put(XIANJIA,"���ɼװ�*���ݷ��ߵķ��������Է����������е�*");
		FIRST_DES.put(XIANJIA,"������ɼס�");
		DETAIL.put(ZUOQI,"�������*��������ĵȼ��������е�*");
		FIRST_DES.put(ZUOQI,"����������");
		
		//���ɰ�
		DETAIL.put(F_PRESTIGE,"��������*���ݰ�������Ķ��ٽ�������*");
		FIRST_DES.put(F_PRESTIGE, "�������ϡ�");
		DETAIL.put(F_ZHANLI,"��ս����*��������ĵȼ���ս������������������*");
		FIRST_DES.put(F_ZHANLI, "���̹��ϡ�");
		DETAIL.put( F_RICH,"���Ƹ���*���ݰ����ϵĶ��ٽ�������*");
		FIRST_DES.put( F_RICH, "������ϡ�");
		//���
		DETAIL.put(OPEN, "�������*���ݿ��ƽ���������������*��ÿ�����ã�");
		FIRST_DES.put(OPEN, "�︣�ɡ�");
		DETAIL.put(ANS, "�������*���ݴ�����ȷ������������*��ÿ�����ã�");
		FIRST_DES.put(ANS, "����ʦ��");
	}
	
	public static void main(String[] args)
	{
		for(String s : FILED_TYPE.keySet()){
			System.out.println(s +" : "+FILED_TYPE.get(s));
		}
	}
}
