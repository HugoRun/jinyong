package com.ls.web.service.cooperate.bill;

import org.apache.log4j.Logger;

import com.ben.jms.JmsUtil;
import com.ls.ben.dao.cooparate.bill.UAccountRecordDao;
import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.web.service.cooperate.dangle.PassportService;
import com.ls.web.service.player.EconomyService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.service.mail.MailInfoService;

/**
 * @author ls
 * 功能:用户充值管理
 * Jan 10, 2009
 */
public class BillService
{
	Logger logger = Logger.getLogger("log.pay");
	
	
	/**
	 * 得到充值记录
	 */
	public UAccountRecordVO getAccountRecord( int record_id)
	{
		UAccountRecordDao accRecordDao = new UAccountRecordDao();
		return accRecordDao.getById(record_id);
	}
	
	/**
	 * 用户发出充值请求
	 * @param code            卡号
	 * @param channel         充值渠道
	 */
	public int account( UAccountRecordVO account_record )
	{
		if( account_record==null )
		{
			logger.info("用户充值时：参数错误！account_record为空");
		}
		
		logger.info("玩家成功提交充值请求！充值通道："+account_record.getChannel()+";u_pk:"+account_record.getUPk()+";p_pk:"+account_record.getPPk()+";充值金额为:"+account_record.getMoney()+";卡号："+account_record.getCode());
		
		UAccountRecordDao accRecordDao = new UAccountRecordDao();
		
		//增加充值日志
		return accRecordDao.insert(account_record);
	}
	/**
	 * 用户充值成功通知
	 * @param code            卡号
	 * @param channel         充值渠道
	 * @return                通知成功返回true
	 */
	public boolean  accountSuccessNotify( UAccountRecordVO accountRecord )
	{
		if( accountRecord==null )
		{
			logger.info("玩家充值记录为空");
			return false;
		}
		if( accountRecord.getAccountState().indexOf("callback result") !=-1 )
		{
			logger.info("玩家已经得到callback,不再处理。。。(u_pk："+accountRecord.getUPk()+";p_pk:"+accountRecord.getPPk()+"),已充值成功,充值金额为："+accountRecord.getMoney());
			return false;
		}
		int money = accountRecord.getMoney();
		
		logger.info("玩家充值成功：u_pk:"+accountRecord.getUPk()+";充值金额为:"+money);
		
		MailInfoService mailInfoService = new MailInfoService();
		EconomyService economyService = new EconomyService();
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		
		//更新充值记录状态
		updateState(accountRecord.getId(), "callback result:pay_success");
		//给玩家增加元宝
		int yb_num = accountRecord.getMoney()*100;//1元获得10个元宝
		int jf_num = yb_num*GameConfig.getJifenNum();//1元获得1个积分
		economyService.addYuanbao(accountRecord.getPPk(),accountRecord.getUPk(), yb_num,"chongzhi");
		economyService.addJifen(accountRecord.getUPk(),jf_num);//增加积分：每成功充值1人民币=1积分
		/**充值金额统计**/
		gsss.addPropNum(0, StatisticsType.YUANBAO, yb_num,StatisticsType.DEDAO, "chongzhi", accountRecord.getPPk());
		/**充值人数统计**/
		gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",accountRecord.getPPk());//统计充值人次
		//发邮件
		String title = "充值成功";
		String content = "";
		String channel_display = getChannelDisplay(accountRecord.getChannel());
		String time_str = DateUtil.getCurrentTimeStr();
		content = "您于"+time_str+"充值"+money+"元"+channel_display+"充值卡充值成功，获得【"+GameConfig.getYuanbaoName()+"】×"+yb_num+"！";
		logger.info("邮件标题："+title);
		logger.info("邮件内容："+content);
		mailInfoService.sendMailBySystem(accountRecord.getPPk(), title, content);
		JmsUtil.chongzhi(accountRecord.getPPk(),accountRecord.getMoney(),accountRecord.getChannel());
		return true;
	}
	/**
     * 用户充值失败通知
     * @param accountRecord            卡号
     * @param error_code               错误代码
     */
	public void accountFailNotify( UAccountRecordVO accountRecord,String error_code )
	{
		if( accountRecord==null )
		{
			logger.info("玩家充值记录为空");
			return;
		}
		
		if( accountRecord.getAccountState().indexOf("callback result") !=-1 )
		{
			logger.info("充值失败，原因："+error_code);
			logger.info("玩家已经得到callback,不再处理。。。(u_pk："+accountRecord.getUPk()+";p_pk:"+accountRecord.getPPk()+"),已充值成功,充值金额为："+accountRecord.getMoney());
			return;
		}
		logger.info("玩家充值失败：u_pk:"+accountRecord.getUPk());
		
		MailInfoService mailInfoService = new MailInfoService();
		//更新充值记录状态
		updateState(accountRecord.getId(), "callback result:错误代码-"+error_code);
		
		//发邮件
		String title = "充值失败";
		String content = "";
		String channel_display = getChannelDisplay(accountRecord.getChannel());
		int money = accountRecord.getMoney();
		String time_str = DateUtil.getCurrentTimeStr();
		content = "您于"+time_str+"充值"+money+"元"+channel_display+"充值卡充值失败";
		logger.info("邮件标题："+title);
		logger.info("邮件内容："+content);
		mailInfoService.sendMailBySystem(accountRecord.getPPk(), title, content);
	}
	
	/**
	 * 得到充值通道描述
	 */
	private String getChannelDisplay(String channel)
	{
		if( channel==null )
		{
			return "";
		}
		if(channel.equals("SZX"))
		{
			return "移动神州行";
		}
		else if(channel.equals("SNDACARD"))
		{
			return "盛大";
		}
		else if(channel.equals("jun"))
		{
			return "骏网";
		}
		else if(channel.equals("SZF_DIANXIN"))
		{
			return "电信";
		}
		else if(channel.equals("SZF_LIANTONG"))
		{
			return "联通";
		}
		else if(channel.equals("SZF_SZX"))
		{
			return "神州行";
		}
		else
		{
			return "";
		}
		
	}
	
	/**
	 * 更新用户充值状态
	 */
	public void updateState( int id,String state )
	{
		UAccountRecordDao accRecordDao = new UAccountRecordDao();
		accRecordDao.update(id, state);
	}
	
	
	/**
	 * 得到充值成功的脚本
	 * @return
	 */
	public String getSuccessHint()
	{
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("您已经完成了为游戏充值的相关操作，充值完成后需要等待几分钟才能确认是否充值成功，请您现在继续游戏，我们将在几分钟后发送确认邮件到该角色的邮箱中！<br/>");
		return resultWml.toString();
	}
	/**
	 * 得到Tom充值成功的脚本
	 * @return
	 */
	public String getTomSuccessHint()
	{
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("充值成功!<br/>客服热线021-28901353<br/>");
		return resultWml.toString();
	}
	/**
	 * 得到充值失败的脚本
	 * @return
	 */
	public String getFailHint(String errerCode)
	{
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("对不起，您输入了错误的卡号或者密码！<br/>");  
		resultWml.append("错误产生的原因可能是：<br/>");  
		resultWml.append("1.选择了错误的充值渠道，请检查充值卡类型与充值渠道是匹配的<br/>");  
		resultWml.append("2.不小心输错了帐号或密码，请重新输入一次<br/>");  
		resultWml.append("3.选择了与充值卡面额不匹配的充值渠道，请选择相应面额的充值渠道<br/>");  
		return resultWml.toString();
	}
	/**
	 * 得到充值失败的脚本
	 * @return
	 */
	public String getTomFailHint(String errerCode)
	{
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("对不起，充值失败！<br/>");  
		resultWml.append("错误产生的原因可能是：<br/>");  
		resultWml.append("1.您的手机话费不足这次充值所需的费用<br/>客服热线021-28901353<br/>");
		return resultWml.toString();
	}
	
	//充值通用接口
	public boolean chongzhibynormal(String account,String pay_money,String timestamp,String gameid,String MD5string,String gamepoint,String orderid){
		String key = "";
		key = getMD5str(account, timestamp, gameid, pay_money, gamepoint);
		if(MD5string.equals(key)){
			chongzhibynormal(account, pay_money, gameid,orderid);
			return true;
		}else{
			return false;
		}
	}
	
	private String getMD5str(String account,String timestamp,String gameid,String pay_money,String gamepoint){
		String key = "";
		if(Integer.parseInt(gameid) == Channel.GGW){
			key = Channel.GGW12KEY;
			String str_bak = account+timestamp+pay_money+gamepoint+key;
			String str_md5 = MD5Util.md5Hex(str_bak);
			return str_md5;
		}
		if(Integer.parseInt(gameid) == Channel.HZRD){
			key = Channel.HZRD13KEY;
			String str_bak = account+timestamp+pay_money+gamepoint+key;
			String str_md5 = MD5Util.md5Hex(str_bak);
			return str_md5;
		}
		return key;
	}
	
	private void chongzhibynormal(String account,String pay_money,String gameid,String orderid){
		UAccountRecordVO accountRecord = new UAccountRecordVO();
		UAccountRecordDao accRecordDao = new UAccountRecordDao();
		PassportService passportService = new PassportService();
		PassportVO passport = passportService.getPassportInfoByUserID(account,Integer.parseInt(gameid));
		EconomyService economyService = new EconomyService();
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		MailInfoService mailInfoService = new MailInfoService();
		// 充值成功 记录
		int money = getYuanbaoNum(gameid, pay_money);
		accountRecord.setMoney(money);
		accountRecord.setUPk(passport.getUPk());
		accountRecord.setChannel(GameConfig.getChannelId()+"");
		accountRecord.setAccountState("success");
		accountRecord.setCode(orderid);
		accRecordDao.insert(accountRecord);
		// 给玩家增加元宝
		int yb_num = money;// 1元获得100个元宝
		int jf_num = yb_num * GameConfig.getJifenNum();// 1元获得1个积分

		economyService.addYuanbao(accountRecord.getPPk(), accountRecord
				.getUPk(), yb_num, "chongzhi");
		economyService.addJifen(accountRecord.getUPk(), jf_num);// 增加积分：每成功充值1人民币=1积分

		gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",
				accountRecord.getPPk());// 统计充值人次
		// 发邮件
		String title = "充值成功";
		String content = "";
		String time_str = DateUtil.getCurrentTimeStr();
		content = "您于" + time_str + "充值" + accountRecord.getMoney()
				+ "元充值成功，获得【"+GameConfig.getYuanbaoName()+"】×" + yb_num + "！";
		logger.info("邮件标题：" + title);
		logger.info("邮件内容：" + content);
		mailInfoService.sendMailBySystem(accountRecord.getPPk(), title,
				content);

		gsss.addPropNum(0, StatisticsType.RMB, accountRecord.getMoney(),
				StatisticsType.DEDAO, Channel.JUU + "", accountRecord
						.getPPk());// 统计RMB
	}
	private int getYuanbaoNum(String gameid,String pay_money){
		int money = 0;
		if(Integer.parseInt(gameid) == Channel.GGW){
			Double m = (Double.parseDouble(pay_money)*(Channel.GGWM));
			money = m.intValue();
		}
		return money;
	}
	
}
