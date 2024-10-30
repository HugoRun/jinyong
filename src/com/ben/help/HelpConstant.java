package com.ben.help;

import java.util.ArrayList;
import java.util.List;

public class HelpConstant
{
	public static final int HUODONG = 1;// 今日活动
	public static final int LIANJI = 2;// 练级区域
	public static final int TASK = 3;// 任务领取
	public static final int FUBEN = 4;// 副本介绍
	public static final int SHENGWANG = 5;// 声望奖励
	public static final int XITONG = 6;// 系统介绍
	public static final int DIAOLUO = 7;//掉落查询
	public static final int GM = 8;//寻找GM
	
	public static final int EACH_LENGTH = 200;//每页显示的内容长度
	
	public static final String LEVEL_NOT_ALLOW = "您目前的等级还无法使用此处的传送功能";
	public static final String LEVEL_TSAK_NOT_ALLOW = "您目前的等级还无法领取此任务";
	public static final String TASK_NOT_ALLOW = "您已经完成该任务，因此无法再领取";
	public static final String TASK_MEN_NOT_ALLOW = "种族不符，无法领取此任务";
//	public static final String BACK_LINK = "<br/><anchor><go href=\"/help.do?cmd=n1\" method=\"get\"/>返回</anchor><br/>";
    public static final String SHAOLIN = "shaolin";
    public static final String GAIBANG = "gaibang";
    public static final String MINGJIAO = "mingjiao";
    
    public static final int COUNT = 5;//显示掉落怪物数
    public static final String TESHU = "商城";
    
    public final static List<Integer> SHOW = new ArrayList<Integer>();
    
    static {
    	SHOW.add(189);//黄金宝箱
    	SHOW.add(859);//金蛋
    }
    
    public final static Integer HUANGJINBAOXIANG = 189;//黄金宝箱
    
    public final static Integer JINDAN = 859;//金蛋
    



}
