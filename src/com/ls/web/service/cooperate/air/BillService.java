package com.ls.web.service.cooperate.air;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ls.ben.dao.cooparate.sky.UPayRecordDao;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.StatisticsType;
import com.ls.web.service.player.EconomyService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;

public class BillService
{

	Logger logger = Logger.getLogger("log.pay");

//	/**
//	 * 需要POST提交的参数
//	 * @param role_info
//	 * @param user_name
//	 * @param amount
//	 * @return
//	 */
//	public String getPayToken(RoleEntity role_info, String user_name,
//			String amount)
//	{
//		if (!MoneyUtil.validateMoneyStr(amount))
//		{
//			return null;
//		}
//
//		if (user_name == null || user_name.trim().equals(""))
//		{
//			return null;
//		}
//		if (amount == null || amount.trim().equals(""))
//		{
//			return null;
//		}
//
//		String url = "http://kong.net/apps/service/pay";
//
//		logger.debug("url：" + url + ";user_name=" + user_name + ";Amount="
//				+ amount);
//
//		UPayRecordDao uPayRecordDao = new UPayRecordDao();
//		int pay_record_id = uPayRecordDao.insert(user_name, amount, role_info
//				.getBasicInfo().getPPk());
//		PayRegOrderResponseImpl preg = new PayRegOrderResponseImpl();
//		String token = preg.getToken();
//		if (!token.equals(""))
//		{
//			logger.info("11111111");
//		}
//
//		return token;
//	}

	/**
	 * 充值元宝
	 * 
	 * @param u_pk
	 * @param p_pk
	 * @param amount
	 * @return 返回充值提示
	 */
	public String chongzhiYuanbao(int u_pk, int p_pk, int orderId, int amount)
	{
		/*String hint = null;
		String sessionKey = "";
		String apiKey = "ix2yRf3hzwwXa67CuChsQ9DvUROwXLZO";
		String apiSecret = "YCJNv4i9r6VaRSd25ytfJWDW5LjTwHqY";
		AirImpl ai = new AirImpl(apiKey, apiSecret, sessionKey);
		
		// 如果充值完成
		if (ai.pay_isCompleted(orderId))
		{
			EconomyService economyService = new EconomyService();

			int yb_num = amount;// 1K金获得1个元宝
			int jf_num = yb_num * GameConfig.getJifenNum();// 1K金获得1个积分
			// 给玩家增加元宝
			economyService.addYuanbao(p_pk, u_pk, yb_num, "chongzhi");
			// 给玩家增加积分 每成功兑换1KB获得1个积分
			economyService.addJifen(u_pk, jf_num);

			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",
					u_pk);// 统计充值人次
			long yuanbao_total = economyService.getYuanbao(u_pk);
			return hint = "兑换成功,您获得了" + amount + "个【元宝】,目前您共有【元宝】×"
					+ yuanbao_total + "!";

		}*/
		return "兑换失败！";

	}
	
	/**
	 * 添加消费记录
	 * @param skyid
	 * @param kbamt
	 * @param p_pk
	 * @return 条数
	 */
	public int addPayRecord(String skyid,String kbamt,int p_pk)
	{
		UPayRecordDao uPayRecordDao = new UPayRecordDao();
		return uPayRecordDao.insert(skyid, kbamt,p_pk);
	}
	
	/**
	 * 根据token返回充值提示
	 * @return
	 */
	public String getPayHintByToken(Map<String,String> token)
	{
		return null;
	}

	// public static void main( String[] agrs )
	// {
	// BillService air = new BillService();
	//		
	// System.out.print("消费结果="+air.pay(null, null, null));
	//	}
}
