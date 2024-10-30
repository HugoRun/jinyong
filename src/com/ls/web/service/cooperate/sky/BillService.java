/**
 * 
 */
package com.ls.web.service.cooperate.sky;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ls.ben.dao.cooparate.sky.UPayRecordDao;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.model.log.GameLogManager;
import com.ls.pub.config.GameConfig;
import com.ls.pub.config.sky.ConfigOfSky;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.http.HttpRequester;
import com.ls.pub.util.http.HttpRespons;
import com.ls.pub.util.http.parseContent.ParseNormalContent;
import com.ls.web.service.cooperate.dangle.PassportService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.EconomyService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;


/**
 * 功能：思凯k币消费处理
 * @author ls
 * Jun 19, 2009
 * 11:20:15 AM
 */
public class BillService {
	
	Logger logger = Logger.getLogger("log.pay");
	/**
	 * 提交消费请求
	 * @param ssid
	 * @param skyid
	 * @param kbamt             消费金额
	 */
	public Map<String,String> pay(String uPk,String ssid,String skyid,String kbamt,int p_pk)
	{
		Map<String,String> pay_results = new HashMap<String,String>();
		
		/**
		 * 扣除k币请求的url
		 */
		String validate_url = ConfigOfSky.getUrlOfPayKB();
		
		int pay_record_id = addPayRecord(skyid, kbamt,p_pk);//添加消费记录
		
		String billid = this.createBilld(pay_record_id);
		
		Map<String, String> params = new HashMap<String, String>();//配置提交参数
		params.put("ssid", ssid);
		params.put("skyid", skyid);
		params.put("billid", billid +"");
		params.put("kbamt", kbamt);
		
		HttpRespons response = null;
		 HttpRequester request = new HttpRequester(); 
         request.setDefaultContentEncoding("utf-8");
         
         
         try {
			response = request.sendGet(validate_url, params);//提交扣费请求
			
		} catch (IOException e) {
			logger.debug("思凯消费k币请求异常");
			e.printStackTrace();
			return pay_results;
		}   
		
		if( response.getCode()!=200 )//扣费请求失败
		{
			logger.debug("思凯消费k币请求失败，响应代码为："+response.getCode());
			return pay_results;
		}
		
		try
		{
			ParseNormalContent parseContent = new ParseNormalContent();
			pay_results = parseContent.parse(response.getContent());//得到解析后的响应结果
			
			String respones_result = pay_results.get("result");//响应结果
			
			//成功扣费的处理
			String skybillid1 = "skybillid1";
			String skybillid2 = "skybillid2";
			String balance = "balance";
			if( respones_result.equals("0") )//表示扣费成功
			{
				//成功扣费的处理
				skybillid1 = pay_results.get("skybillid1");
				skybillid2 = pay_results.get("skybillid2");
				balance = pay_results.get("balance");
				
				//给玩家充元宝
				EconomyService economyService = new EconomyService();
				
				int u_pk = Integer.parseInt(uPk);
				//给玩家增加元宝
				int yb_num = Integer.parseInt(kbamt);//1KB获得1个元宝
				int jf_num = yb_num*GameConfig.getJifenNum();//1KB获得1个积分

				economyService.addYuanbao(p_pk,u_pk, yb_num,"chongzhi");
				economyService.addJifen(u_pk,jf_num);//增加积分：每成功兑换1KB获得1个积分
				
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",u_pk);//统计充值人次

			}
			else if( respones_result.equals("2") )
			{
				//超大自己的营帐系统timeout
				//这种情况有可能已经扣款成功。 为了处理客户投诉， 请一定要将这笔交易[skyBillID1和skyBillID2]记录到数据库。 Balance字段不出现
				skybillid1 = pay_results.get("skybillid1");
				skybillid2 = pay_results.get("skybillid2");
			}
			else if( respones_result.equals("3") )
			{
				//余额不足
				//显示玩家的余额（balance字段）， 引导玩家充值。
				balance = pay_results.get("balance");
			}
			updatePayRecord( pay_record_id,billid, skybillid1, skybillid2, balance, respones_result);//更新消费记录
		}
		catch (Exception e)
		{
			updatePayRecord( pay_record_id,billid, "fail", "fail", "fail", "fail");//更新消费记录
		}
		return pay_results;
	}

	/**
	 * 添加消费记录
	 * @param skyid
	 * @param kbamt
	 * @result:0
	 * @Skybillid1 和 skybillid2： 是超级大玩家的对账流水号。 请保存到消费记录中以便于对账
	 * @balance:0     用户当前账户中的余额
	 * @return
	 */
	public int addPayRecord( String skyid,String kbamt,int p_pk )
	{
		UPayRecordDao uPayRecordDao = new UPayRecordDao();
		return uPayRecordDao.insert(skyid, kbamt,p_pk);
	}
	
	/**
	 * 添加消费记录
	 * @param skyid
	 * @param billid
	 * @param kbamt
	 * @result:0
	 * @Skybillid1 和 skybillid2： 是超级大玩家的对账流水号。 请保存到消费记录中以便于对账
	 * @balance:0     用户当前账户中的余额
	 * @return
	 */
	public void updatePayRecord( int id,String billid,String skybillid1,String skybillid2,String balance,String respones_result )
	{
		UPayRecordDao uPayRecordDao = new UPayRecordDao();
		uPayRecordDao.update(id,billid, skybillid1, skybillid2, balance, respones_result);
	}
	
	
	/**
	 * 根据响应结果，给玩家相应的提示
	 * @param pay_results          响应结果
	 * @return
	 */
	public String getPayHintByResult( Map<String,String> pay_results )
	{
		String hint = "";
		
		if( pay_results==null )
		{
			return hint = "充值失败,请重试!";
		}
		
		String respones_result = pay_results.get("result");
		
		if( respones_result==null )
		{
			return hint = "充值失败,请重试!";
		}
		
		//扣款成功
		if( respones_result.equals("0"))
		{
			hint = "充值成功";
		}
		//认证失败:Ssid 无效或者和skyid不匹配,应该提示玩家重新登陆超大
		//其他字段（skybillid1，skybillid2，balance）不出现。
		else if( respones_result.equals("1") )
		{
			hint = "请重新在超级大玩家登陆";
		}
		//超大自己的营帐系统timeout
		//这种情况有可能已经扣款成功。 为了处理客户投诉， 请一定要将这笔交易[skyBillID1和skyBillID2]记录到数据库。 Balance字段不出现
		else if( respones_result.equals("2") )
		{
			
		}
		//余额不足
		//显示玩家的余额（balance字段）， 引导玩家充值。
		else if( respones_result.equals("3") )
		{
			String balance = pay_results.get("balance");
			//余额不足！您只有10K币，请充值后再尝试兑换！
			hint = "余额不足!您只有"+balance+"K币,请充值后再尝试兑换!";
		}
		//: billID 重复， 其他字段无效
		else if( respones_result.equals("6") )
		{
			hint = "充值失败,请重试!";
		}
		//其他值：  属于内部错误， 只需要提示操作失败， errorcode显示给用户用于反馈就可以了。  [其他字段不出现]
		else
		{
			hint = "充值失败,错误代码:"+respones_result+",请重试!";
		}
		
		return hint;
	}
	
	/**
	 * 生成billd,生成规则：分区id(1位)+s+pay_record_id(3位)+s+时间（毫秒数）（13位）,总长19位
	 * @return
	 */
	public String createBilld( int pay_record_id )
	{
		StringBuffer result = new StringBuffer();
		
		result.append(GameConfig.getAreaId()+"s").append(pay_record_id%1000+"s").append(System.currentTimeMillis());
		
		return result.toString();
	}
	/**
	 * 充值奖励  在百宝囊中放充值奖励
	 * @param yb_num元宝数量
	 */
	public String chongZhiJiangLi(int p_pk,int yb_num){
		String hint = "";
		String begintime = GameConfig.getChongzhijiangliBeginTime();
		String endtime = GameConfig.getChongzhijiangliEndTime();
		Date nowTime = new Date();
		Date begin = DateUtil.strToDate(begintime);
		Date end = DateUtil.strToDate(endtime); 
		if (nowTime.after(begin) && nowTime.before(end))
		{
			if(yb_num == 2000)
			{
				//充值2000送钱袋
				GoodsService goodsService = new GoodsService();
				String prop_id = GameConfig.getChongzhijiangliCommodityinfoId().trim();//发放的道具id
				goodsService.putPropToWrap(p_pk, Integer.parseInt(prop_id), 1,GameLogManager.G_SYSTEM);//放入包裹
				hint = begintime+"—"+endtime+",每充值2000"+GameConfig.getYuanbaoName()+"即可获得1888银两的钱袋(钱袋将放入包裹的商城栏,如果包裹格数不够导致未发放成功,官方不予补偿)";
			}
		}
		return hint;
	}
	
	public static void main(String args[])
	{
		BillService billService = new BillService();
		System.out.print("billd=="+billService.createBilld(4));
	}
	
	public Map<String,String> OKpay(String uPk,String osid,String skyid,String amt,int p_pk)
	{
		Map<String,String> pay_results = new HashMap<String,String>();
		
		/**
		 * 扣除k币请求的url
		 */
		String validate_url = "http://wapok.cn/n_gamevoucher.php";
		
		int pay_record_id = addPayRecord(skyid, amt,p_pk);//添加消费记录
		
		String billid = this.createBilld(pay_record_id);
		
		Map<String, String> params = new HashMap<String, String>();//配置提交参数
		params.put("osid", osid);
		params.put("account", skyid);
		params.put("amt", amt);
		params.put("billid", billid);
		
		HttpRespons response = null;
		 HttpRequester request = new HttpRequester(); 
         request.setDefaultContentEncoding("utf-8");
         
         
         try {
			response = request.sendGet(validate_url, params);//提交扣费请求
			
		} catch (IOException e) {
			logger.debug("思凯消费k币请求异常");
			e.printStackTrace();
			return pay_results;
		}   
		
		try
		{
			ParseNormalContent parseContent = new ParseNormalContent();
			pay_results = parseContent.parse(response.getContent());//得到解析后的响应结果
			
			String respones_result = null;
			Set<String> keys = pay_results.keySet();
			for(String key:keys )
			{
				if( key.indexOf("result")!=-1 )
				{
					respones_result = pay_results.get(key);
				}
			}
			
			//成功扣费的处理
			String skybillid1 = "skybillid1";
			String skybillid2 = "skybillid2";
			String balance = "balance";
			if( respones_result.equals("10") )//表示扣费成功
			{
				//成功扣费的处理
				skybillid1 = pay_results.get("okbillid1");
				skybillid2 = pay_results.get("okbillid2");
				balance = pay_results.get("balance");
				
				//给玩家充元宝
				EconomyService economyService = new EconomyService();
				
				int u_pk = Integer.parseInt(uPk);
				//给玩家增加元宝
				int yb_num = Integer.parseInt(amt)*112;//1KB获得112个元宝
				int jf_num = yb_num*GameConfig.getJifenNum();//1KB获得1个积分

				economyService.addYuanbao(p_pk,u_pk, yb_num,"chongzhi");
				economyService.addJifen(u_pk,jf_num);//增加积分：每成功兑换1KB获得1个积分
				
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",u_pk);//统计充值人次

			}
			else{
				//余额不足
				//显示玩家的余额（balance字段）， 引导玩家充值。
				balance = pay_results.get("balance");
			}
			updatePayRecord( pay_record_id,billid, skybillid1, skybillid2, balance, respones_result);//更新消费记录
		}
		catch (Exception e)
		{
			updatePayRecord( pay_record_id,billid, "fail", "fail", "fail", "fail");//更新消费记录
		}
		return pay_results;
	}
	
	public String OKcallbackpay(String okid ,String okbillid1, String okbillid2 ,String oknum)
	{
		/**
		 * 扣除k币请求的url
		 */
		int pay_record_id = addPayRecord(okid, oknum,0);//添加消费记录
		
		String billid = this.createBilld(pay_record_id);
				
		EconomyService economyService = new EconomyService();
		PassportService passportService = new PassportService();
		PassportVO passport = passportService.getPassportInfoByUserName(okid, Channel.OKP);
		
		int yb_num = Integer.parseInt(oknum)*112;//1KB获得1个元宝
		int jf_num = yb_num*GameConfig.getJifenNum();//1KB获得1个积分
		economyService.addYuanbao(0,passport.getUPk(), yb_num,"chongzhi");
		economyService.addJifen(passport.getUPk(),jf_num);//增加积分：每成功兑换1KB获得1个积分
			
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",passport.getUPk());//统计充值人次
		updatePayRecord( pay_record_id,billid, okbillid1, okbillid2, "callback", "0");//更新消费记录
		return "success";
	}
}
