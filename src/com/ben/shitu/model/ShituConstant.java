package com.ben.shitu.model;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.BasicInfo;
import com.ls.pub.config.GameConfig;

public class ShituConstant
{
	// ��ͽ����ȼ�
	public final static int ZHAOTU_LEVEL_LIMIT = 40;

	// ��ʦ��ߵȼ�
	public final static int BAISHI_LEVEL_LIMIT = 30;

	// ��ͽ����
	public final static int ZHAOTU_TYPE = 0;

	// ��ʦ����
	public final static int BAISHI_TYPE = 1;

	// ʦ���б�
	public static List<Shitu> TEACHER = new ArrayList<Shitu>();

	// ͽ���б�
	public static List<Shitu> STUDENT = new ArrayList<Shitu>();

	// ���ʦͽ��ϵʦ����������
	public final static int TEA_ZUIE = 800;

	// ���ʦͽ��ϵͽ�ܳ�������
	public final static int STU_ZUIE = 500;
	
	//ʦ���������������ID
	public final static int CREDIT_ID = 10;
	
	//ʦ��������������ĵ���
	public final static int CREDIT_COUNT = 10;
	
	//ͽ�ܳ�ʦʦ�����������ID
	public final static int CHUSHI_CREDIT_ID = 10;
	
	//ͽ�ܳ�ʦʦ����������ĵ���
	public final static int CHUSHI_CREDIT_COUNT = 100;
	
	//ʦͽͬʱ���߻�õĶ��⾭�齱��
	public final static int MORE_EXP = 120;
	
	//ͽ�ܵ�һ�μ�������ʦ����á�Ԫ������id
	public final static int YUANBAOQUAN_ID = 170;
	//ͽ�ܵ�һ�μ�������ʦ����á�Ԫ����������
	public final static int YUANBAOQUAN_COUNT = 100;
	//ʦ����ó�ʦ�����ID
	public final static int TEA_DALIBAO = 848;
    //ͽ�ܻ�ó�ʦ�����ID
	public final static int STU_DALIBAO = 848;
	//ͽ�ܻ���������ı���
	public final static int MORE_TASK = 150;
	
	//��ɫ�ⶥ�ȼ�
	public final static int MAX_LEVEL = GameConfig.getGradeUpperLimit();
	//��ɫ�ⶥǰһ����������
	public final static int MAX_EXP = 50948130;
	

	public final static String SHOUTUSQL = "select count(*) as cou from shitu s where s.te_id = ? ";
	public final static String SHOUTUSQL1 = "select * from shitu s where s.stu_id = ?";
	public final static String SHOUTUSQL2 = "delete from shitu where stu_id = ?";
	public final static String SHOUTUSQL3 = "update shitu s set s.te_id = ?,s.te_name = ? , s.te_level = ? where s.id = ?";
	public final static String SHOUTUSQL4 = "delete from shitu where te_id = ? and stu_id = 0";
	public final static String BAISHISQL1 = "select count(*) as cou from shitu s where s.stu_id = ? and s.te_id !=0";
	public final static String BAISHISQL2 = "select count(*) as cou from shitu s where s.stu_id = ? and s.te_id = 0";
	public final static String BAISHISQL3 = "update shitu s set s.stu_id = ?,s.stu_name = ?,s.stu_level = ? where s.id = ?";
	public final static String BAISHISQL4 = "delete from shitu where stu_id = ? and te_id = 0";
	public final static String CHUANGONG = "update shitu s set s.chuangong = now() where s.id = ?";

	// ���ݳ�ͽ��������ʦ���ƺ�
	public static String getTeLevel(int te_level)
	{
		String teLevel = "";
		if (te_level >= 2 && te_level < 5)
		{
			teLevel = "����";
		}
		else
			if (te_level >= 5 && te_level < 9)
			{
				teLevel = "����";
			}
			else
				if (te_level >= 9 && te_level < 15)
				{
					teLevel = "��ʦ";
				}
				else
					if (te_level >= 15)
					{
						teLevel = "����";
					}
		return teLevel;
	}
	
	// ���ݳ�ͽ��������ʦ���ƺ�
	public static String getTeLevel1(int te_level)
	{
		String teLevel = "";
		if (te_level >= 2 && te_level < 5)
		{
			teLevel = "(����)";
		}
		else
			if (te_level >= 5 && te_level < 9)
			{
				teLevel = "(����)";
			}
			else
				if (te_level >= 9 && te_level < 15)
				{
					teLevel = "(��ʦ)";
				}
				else
					if (te_level >= 15)
					{
						teLevel = "(����)";
					}
		return teLevel;
	}

	// ���ݳ�ͽ��������ʦ������ͽ������
	public static int getCANRECCOUNT(int te_level)
	{
		int count = 1;
		if (te_level >= 2 && te_level < 5)
		{
			count = 2;
		}
		else
			if (te_level >= 5 && te_level < 9)
			{
				count = 3;
			}
			else
				if (te_level >= 9 && te_level < 15)
				{
					count = 4;
				}
				else
					if (te_level >= 15)
					{
						count = 5;
					}
		return count;
	}

	// ʦ��������ʧ���飺ʦ��������������/25*��ͽ�ܵȼ�/ʦ���ȼ���
	public static int getTeaEXP(BasicInfo bi, int stu_level)
	{
		if(bi.getGrade()==ShituConstant.MAX_LEVEL){
			return getTeaExpMax(stu_level);
		}
		int teaExp = 0;
		try
		{
			teaExp = (Integer.parseInt(bi.getNextGradeExp().trim())-Integer.parseInt(bi.getPreGradeExp().trim())) / 25
					* stu_level / (bi.getGrade()==0?1:bi.getGrade());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return teaExp;
		}
	}
	
	public static int getTeaExpMax(int stu_level){
		return ShituConstant.MAX_EXP/25*stu_level/ShituConstant.MAX_LEVEL;
	}
	
	
//	ͽ�ܻ�þ��飺ʦ��������ʧ����*0.85
	public static int getStuExp(int teaExp){
		int stuEXP = teaExp*85/100;
		return stuEXP>0?stuEXP:1;
	}
	
//  ͽ��ÿ��5��ʦ����þ��齱��
//	ʦ����ǰ�ȼ���������/20*(ͽ�ܵ�ǰ�ȼ�/ʦ����ǰ�ȼ�)
	public static long getTeaExpGet(int stu_grade,PartInfoVO pv){
		long teaExpGet = 0;
		try{
			teaExpGet =  Long.parseLong(pv.getPXiaExperience().trim())/20*stu_grade/(pv.getPGrade()==0?1:pv.getPGrade());
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return teaExpGet;
		}
	}
	
//  ͽ��ÿ��5��ʦ����þ��齱��
//	�������������ʽ��5*ͽ�ܵȼ�
	public static int getTeaMoneyGet(int stu_grade){
		return stu_grade*500;
	}
	
//	ͽ�ܳ�ʦʦ���ɻ�þ���,ʦ����ǰ���������10%
	public static long getChushiExp(PartInfoVO pv){
		long chushiExp = 0;
		try{
			chushiExp = (Long.parseLong(pv.getPXiaExperience().trim())-pv.getPBenJiExp())/10;
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return chushiExp;
		}
	}

}
