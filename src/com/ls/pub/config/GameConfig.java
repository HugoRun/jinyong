package com.ls.pub.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import com.ls.ben.cache.staticcache.expNpcDrop.ExpNpcDropCache;
import com.ls.pub.constant.Channel;

/**
 * 功能：游戏配置文件
 * 
 * @author ls Jun 27, 2009 4:40:51 PM
 */
public class GameConfig
{
	private static PropertiesConfiguration config = null;
	
	static
	{
		try
		{
			config = new PropertiesConfiguration("game.properties");
			config.setReloadingStrategy(new FileChangedReloadingStrategy());
		}
		catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
	}

	
	/**
	 * 得到游戏的绝对路径
	 * 
	 * @return
	 */
	public static String getGameUrl()
	{
		return config.getString("game_server_url");
	}

	/**
	 * 得到项目路径
	 * 
	 * @return
	 */
	public static String getContextPath()
	{
		if (Channel.AIR == getChannelId() || Channel.TXW == getChannelId())
		{
			return config.getString("context_path");
		}
		else
		{
			return "";
		}
	}
	
	public static String getChongzhiPath()
	{
		String context_path = config.getString("context_path");
		if (context_path != null)
		{
			return context_path;
		}
		else
		{
			return "";
		}
	}

	/**
	 * #防PC登陆白名单ID 1开 0关
	 * 
	 * @return
	 */
	public static boolean isForbidPcWhiteListSwitch()
	{
		if (config.getInt("forbid_pc_white_list_switch") == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * #防PC登陆黑名单IP 1开 0关
	 * 
	 * @return
	 */
	public static boolean isForbidPcBlackListSwitch()
	{
		if (config.getInt("forbid_pc_black_list_switch") == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 得到金钱单位名称
	 */
	public static String getMoneyUnitName()
	{
		return "灵石";
	}
	
	/**
	 * 获取元宝名称
	 */
	public static String getYuanbaoName(){
		if(config.containsKey("yuanbao_name")){
			return config.getString("yuanbao_name");
		}else if(getChannelId()==Channel.TELE){
			return "点数";
		}else
		{
			return "仙晶";
		}
	}
	/**
	 * 获取道具消费代码  电信专用
	 */
	public static String getPropCode(String propId)
	{
		return config.getString(propId);
	}
	
	/**
	 * 获取元宝券名称
	 */
	public static String getYuanbaoQuanName(){
		if(config.containsKey("yuanbao_quan")){
			return config.getString("yuanbao_quan");
		}else{
			return "元宝券";
		}
	}

	/**
	 * #防PC登陆UA 1开 0关
	 * 
	 * @return
	 */
	public static boolean isForbidPcUASwitch()
	{
		if (config.getInt("forbid_pc_ua_switch") == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * #是否对异常点击速度的玩家处理的开关 1开 0关
	 * 
	 * @return
	 */
	public static boolean isDealExceptionUserSwitch()
	{
		if (config.getInt("deal_exception_user_switch") == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 当前等级上限
	 * 
	 * @return
	 */
	public static int getGradeUpperLimit()
	{
		return config.getInt("grade_curren_limit");
	}

	/****
	 * 最高等级上限
	 */
	public static int getGradeUpperHighLimit()
	{
		return config.getInt("grade_upper_limit");
	}
	/**
	 * 公共聊天的等级限制
	 * 
	 * @return
	 */
	public static int getPublicChatGradeLimit()
	{
		return config.getInt("public_chat_grade_limit");
	}

	/**
	 * 游戏状态,更新服务器时用到的参数，1表示开服，2表示没开给用户提示游戏更新维护中的提示
	 * 
	 * @return
	 */
	public static int getGameState()
	{
		return config.getInt("game_state");
	}

	/**
	 * 得到游戏的人数上限
	 * 
	 * @return
	 */
	public static int getUserNumUpperLimit()
	{
		return config.getInt("user_num_upper_limit");
	}

	/**
	 * 得到游戏的名字
	 * 
	 * @return
	 */
	public static String getGameName()
	{
		return config.getString("game_name");
	}

	/**
	 * 得到游戏的channel_id
	 * 
	 * @return
	 */
	public static int getChannelId()
	{
		return config.getInt("channel_id");
	}

	/**
	 * 得到游戏分区id
	 * 
	 * @return
	 */
	public static String getAreaId()
	{
		return config.getString("area_id");
	}

	/**
	 * 得到游戏的url地址
	 * 
	 * @return
	 */
	public static String getUrlOfGame()
	{
		return config.getString("game_url");
	}

	/**
	 * 得到游戏的登陆平台的url地址
	 * 
	 * @return
	 */
	public static String getUrlOfLoginPlatform()
	{
		return config.getString("login_platform_url");
	}

	/**
	 * 连接超时的超链接提示
	 * 
	 * @return
	 */
	public static String getReturnZhuanquHint()
	{
		return config.getString("return_zhuanqu_hint");
	}

	/**
	 * 得到自己的专区地址
	 * 
	 * @return
	 */
	public static String getUrlOfLoginServer()
	{
		return config.getString("login_server_url");
	}

	/**
	 * 得到论坛的url地址
	 * 
	 * @return
	 */
	public static String getUrlOfForum()
	{
		return config.getString("forum_url");
	}

	/**
	 * jms服务是否开启 0关1开
	 */
	public static int getJmsSwitch()
	{
		return config.getInt("jms_switch");
	}

	public static boolean jmsIsOn()
	{
		if (getJmsSwitch() == 0)
		{
			return false;
		}
		return true;
	}

	/**
	 * jms服务器地址
	 */
	public static String getJmsUrl()
	{
		String url = config.getString("jms_url");
		return url == null || "".equals(url.trim()) ? "localhost" : url.trim();
	}

	/**
	 * 得到游戏专区的url地址
	 * 
	 * @return
	 */
	public static String getUrlOfZhuanqu()
	{
		return config.getString("zhuanqu_url");
	}

	/**
	 * 得到超时提示
	 * 
	 * @return
	 */
	public static String getTimeoutHint()
	{
		return config.getString("timeout_hint");
	}

	/**
	 * #充值奖励开始时间
	 * 
	 * @return
	 */
	public static String getChongzhijiangliBeginTime()
	{
		return config.getString("chongzhijiangli_begin_time");
	}

	/**
	 * #充值奖励结束时间
	 * 
	 * @return
	 */
	public static String getChongzhijiangliEndTime()
	{
		return config.getString("chongzhijiangli_end_time");
	}

	/**
	 * #充值奖励商城道具ID
	 * 
	 * @return
	 */
	public static String getChongzhijiangliCommodityinfoId()
	{
		String result = config.getString("chongzhijiangli_commodityinfo_id");
		return result;
	}

	/**
	 * #系统经验倍数 是否重载 0 默认不重载 1重载
	 * 
	 * @return
	 */
	public static void reloadSysExpMultiple()
	{
		int is_reload_exp = config.getInt("is_reload_exp");
		if (is_reload_exp == 1)
		{
			config.setAutoSave(true);
			config.setProperty("is_reload_exp", "0");
			ExpNpcDropCache expNpcDropCache = new ExpNpcDropCache();
			expNpcDropCache.reloadDrop();
		}
	}

	/**
	 * #系统掉宝倍数 是否重载 0 默认不重载 1重载
	 * 
	 * @return
	 */
	public static void reloadSysDropMultiple()
	{
		int is_reload_cimelia = config.getInt("is_reload_cimelia");
		if (is_reload_cimelia == 1)
		{
			config.setAutoSave(true);
			config.setProperty("is_reload_cimelia", "0");
			ExpNpcDropCache expNpcDropCache = new ExpNpcDropCache();
			expNpcDropCache.reloadDrop();
		}
	}

	/**
	 * 获取资源文件对象值
	 * 
	 * @param object
	 * @return
	 */
	public static String getPropertiesObject(String object)
	{
		return config.getString(object);
	}

	public static String[] getPropertiesObjectArray(String object)
	{
		return config.getStringArray(object);
	}

	/**
	 * 返回玩家等级是否在系统设定之内
	 * 
	 * @param rolegrade
	 * @param object
	 * @return
	 */
	public static int getGoUpGrade(int rolegrade, String object)
	{
		String[] grade = config.getStringArray(object);
		int digit = -1;
		for (int i = 0; i < grade.length; i++)
		{
			if (Integer.parseInt(grade[i]) == rolegrade)
			{
				return digit = i;
			}
		}
		return digit;
	}

	/**
	 * 获得副本否在系统设定之内
	 * 
	 * @param task_zu
	 * @param object
	 * @return
	 */
	public static int getTaskInstanceZu(String task_zu, String object)
	{
		String[] instancezu = config.getStringArray(object);
		int digit = -1;
		for (int i = 0; i < instancezu.length; i++)
		{
			if (task_zu.equals(instancezu[i]))
			{
				return digit = i;
			}
		}
		return digit;
	}

	/***************************************************************************
	 * I 副本菜单等级
	 */
	public static int getMenuInstance(String rolegrade, String object)
	{
		String[] instancegrade = config.getStringArray(object);// 取得等级
		int digit = -1;
		for (int i = 0; i < instancegrade.length; i++)
		{
			if (rolegrade.equals(instancegrade[i]))
			{
				return digit = i;
			}
		}
		return digit;
	}

	/**
	 * 获得道具
	 * 
	 * @param prop_id
	 * @param object
	 * @return
	 */
	public static int getAttainProp(int prop_id, String object)
	{
		String[] instancegrade = config.getStringArray(object);// 取得等级
		int digit = -1;
		for (int i = 0; i < instancegrade.length; i++)
		{
			if (prop_id == Integer.parseInt(instancegrade[i]))
			{
				return digit = i;
			}
		}
		return digit;
	}

	/**
	 * 使用道具
	 * 
	 * @param prop_id
	 * @param object
	 * @return
	 */
	public static int useProp(int prop_id, String object)
	{
		String[] instancegrade = config.getStringArray(object);// 取得等级
		int digit = -1;
		for (int i = 0; i < instancegrade.length; i++)
		{
			if (prop_id == Integer.parseInt(instancegrade[i]))
			{
				return digit = i;
			}
		}
		return digit;
	}

	public static void main(String args[])
	{
		// String begintime = GameConfig.getChongzhijiangliBeginTime();
		// String endtime = GameConfig.getChongzhijiangliEndTime();
		//
		// Date nowTime = new Date();
		// Date begin = DateUtil.strToDate(begintime);
		// Date end = DateUtil.strToDate(endtime);
		// if (nowTime.after(begin) && nowTime.before(end))
		// {
		// System.out.println("ssssssssssssss");
		// }
		// else
		// {
		// System.out.println("vvvvvvvvvvvvv");
		// }
		System.out.println(jmsIsOn());
	}

	// 绝学的配置
	public static int getJuexuePropID()
	{
		int juexue_prop_id = config.getInt("juexue_prop_id");
		return juexue_prop_id;
	}

	public static int getJuexuePropType()
	{
		int juexue_prop_type = config.getInt("juexue_prop_type");
		return juexue_prop_type;
	}

	public static String getRankPath()
	{
		return config.getString("rank_path");
	}

	// 得到随机数
	public static int getlotteryMinYB()
	{
		int lottery_min_yuanbao = config.getInt("lottery_min_yuanbao");
		return lottery_min_yuanbao;
	}

	// 得到随机数
	public static int getlotteryMaxYB()
	{
		int lottery_max_yuanbao = config.getInt("lottery_max_yuanbao");
		return lottery_max_yuanbao;
	}

	// 得到最大任务数量
	public static int getTaskMaxNum()
	{
		return 20;
	}

	public static int getJifenNum()
	{
		String jifen = config.getString("jifen_num");
		if (jifen != null)
		{
			return Integer.parseInt(jifen);
		}
		else
		{
			return 1;
		}
	}

	public static int getTongHonourId()
	{
		String tong_honour_id = config.getString("tong_honour_id");
		if (tong_honour_id != null)
		{
			return Integer.parseInt(tong_honour_id);
		}
		else
		{
			return 0;
		}
	}

	public static int getTongHonourIdLegender()
	{
		String tong_honour_id = config.getString("tong_honour_id_legender");
		if (tong_honour_id != null)
		{
			return Integer.parseInt(tong_honour_id);
		}
		else
		{
			return 0;
		}
	}

	public static String getShenzhoufuUrl()
	{
		return config.getString("shenzhoufu_url");
	}

	// 装备升级的最大系数
	public static int getZbGrade()
	{
		return 13;
	}

	public static int getPlayerGetOnlinePrizeSwitch()
	{
		String Player_OnlinePrize_Switch = config
				.getString("player_onlineprize_switch");
		if (Player_OnlinePrize_Switch != null)
		{
			return Integer.parseInt(Player_OnlinePrize_Switch);
		}
		else
		{
			return 0;
		}
	}

	public static int getPlayerGetOnlinePrizeProp()
	{
		String Player_OnlinePrize_prop = config
				.getString("player_onlineprize_prop");
		if (Player_OnlinePrize_prop != null)
		{
			return Integer.parseInt(Player_OnlinePrize_prop);
		}
		else
		{
			return 0;
		}
	}

	public static int getPlayerNum()
	{
		String player_num = config.getString("player_num");
		if (player_num != null)
		{
			return Integer.parseInt(player_num);
		}
		else
		{
			return 1;
		}
	}

	public static String getPlayerGetMenpaiContestProp(int type)
	{
		String Player_menpaicontest_prop = "";
		if (type == 1)
		{
			Player_menpaicontest_prop = config
					.getString("player_menpaicontest_prop_m");
			if (Player_menpaicontest_prop != null)
			{
				return Player_menpaicontest_prop;
			}
		}
		if (type == 2)
		{
			Player_menpaicontest_prop = config
					.getString("player_menpaicontest_prop_g");
			if (Player_menpaicontest_prop != null)
			{
				return Player_menpaicontest_prop;
			}
		}
		if (type == 3)
		{
			Player_menpaicontest_prop = config
					.getString("player_menpaicontest_prop_s");
			if (Player_menpaicontest_prop != null)
			{
				return Player_menpaicontest_prop;
			}
		}
		return Player_menpaicontest_prop;
	}

	public static String getPlayerGetMenpaiContestbulid(int type)
	{
		String player_menpaicontest_build = "";
		if (type == 1)
		{
			player_menpaicontest_build = config
					.getString("player_menpaicontest_build_m");
			if (player_menpaicontest_build != null)
			{
				return player_menpaicontest_build;
			}
		}
		if (type == 2)
		{
			player_menpaicontest_build = config
					.getString("player_menpaicontest_build_g");
			if (player_menpaicontest_build != null)
			{
				return player_menpaicontest_build;
			}
		}
		if (type == 3)
		{
			player_menpaicontest_build = config
					.getString("player_menpaicontest_build_s");
			if (player_menpaicontest_build != null)
			{
				return player_menpaicontest_build;
			}
		}
		return player_menpaicontest_build;
	}

}
