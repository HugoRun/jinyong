package com.ben.shitu.model;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.BasicInfo;
import com.ls.pub.config.GameConfig;

public class ShituConstant
{
	// 招徒所需等级
	public final static int ZHAOTU_LEVEL_LIMIT = 40;

	// 拜师最高等级
	public final static int BAISHI_LEVEL_LIMIT = 30;

	// 招徒类型
	public final static int ZHAOTU_TYPE = 0;

	// 拜师类型
	public final static int BAISHI_TYPE = 1;

	// 师父列表
	public static List<Shitu> TEACHER = new ArrayList<Shitu>();

	// 徒弟列表
	public static List<Shitu> STUDENT = new ArrayList<Shitu>();

	// 解除师徒关系师傅承受罪恶度
	public final static int TEA_ZUIE = 800;

	// 解除师徒关系徒弟承受罪恶度
	public final static int STU_ZUIE = 500;
	
	//师父传功获得声望的ID
	public final static int CREDIT_ID = 10;
	
	//师父传功获得声望的点数
	public final static int CREDIT_COUNT = 10;
	
	//徒弟出师师父获得声望的ID
	public final static int CHUSHI_CREDIT_ID = 10;
	
	//徒弟出师师父获得声望的点数
	public final static int CHUSHI_CREDIT_COUNT = 100;
	
	//师徒同时在线获得的额外经验奖励
	public final static int MORE_EXP = 120;
	
	//徒弟第一次加入门派师傅获得【元宝卷】的id
	public final static int YUANBAOQUAN_ID = 170;
	//徒弟第一次加入门派师傅获得【元宝卷】的数量
	public final static int YUANBAOQUAN_COUNT = 100;
	//师父获得出师大礼包ID
	public final static int TEA_DALIBAO = 848;
    //徒弟获得出师大礼包ID
	public final static int STU_DALIBAO = 848;
	//徒弟获得任务奖励的比率
	public final static int MORE_TASK = 150;
	
	//角色封顶等级
	public final static int MAX_LEVEL = GameConfig.getGradeUpperLimit();
	//角色封顶前一级升级经验
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

	// 根据出徒人数定义师傅称号
	public static String getTeLevel(int te_level)
	{
		String teLevel = "";
		if (te_level >= 2 && te_level < 5)
		{
			teLevel = "先生";
		}
		else
			if (te_level >= 5 && te_level < 9)
			{
				teLevel = "教授";
			}
			else
				if (te_level >= 9 && te_level < 15)
				{
					teLevel = "名师";
				}
				else
					if (te_level >= 15)
					{
						teLevel = "伯乐";
					}
		return teLevel;
	}
	
	// 根据出徒人数定义师傅称号
	public static String getTeLevel1(int te_level)
	{
		String teLevel = "";
		if (te_level >= 2 && te_level < 5)
		{
			teLevel = "(先生)";
		}
		else
			if (te_level >= 5 && te_level < 9)
			{
				teLevel = "(教授)";
			}
			else
				if (te_level >= 9 && te_level < 15)
				{
					teLevel = "(名师)";
				}
				else
					if (te_level >= 15)
					{
						teLevel = "(伯乐)";
					}
		return teLevel;
	}

	// 根据出徒人数定义师傅能收徒的数量
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

	// 师傅传功损失经验：师傅本级升级经验/25*（徒弟等级/师傅等级）
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
	
	
//	徒弟获得经验：师傅传功损失经验*0.85
	public static int getStuExp(int teaExp){
		int stuEXP = teaExp*85/100;
		return stuEXP>0?stuEXP:1;
	}
	
//  徒弟每升5级师父获得经验奖励
//	师傅当前等级升级经验/20*(徒弟当前等级/师傅当前等级)
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
	
//  徒弟每升5级师父获得经验奖励
//	获得银两奖励公式：5*徒弟等级
	public static int getTeaMoneyGet(int stu_grade){
		return stu_grade*500;
	}
	
//	徒弟出师师父可获得经验,师傅当前升级经验的10%
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
