package com.ls.web.service.cooperate.tiao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ls.ben.dao.cooparate.sky.UPayRecordDao;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.pub.util.http.HttpRequester;
import com.ls.pub.util.http.HttpRespons;
import com.ls.web.service.player.EconomyService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;

public class BillService
{
	private static String MERCHANT_KEY = "oneshow123";
	
	Logger logger = Logger.getLogger("log.pay");
	/**
	 * 	需Post提交以下参数:
		Name=用户名(not null)
		Amount=消费游戏币(not null)(需>0)
		GameID=游戏标志串(not null)
		Key="游戏密匙"+"-"+"用户名"+”-“+游戏币数额(md5加密传递)
		Comment=购买备注(null)
	 * @return
	 */
	public String pay(RoleEntity role_info ,String user_name,String amount)
	{
		if( !MoneyUtil.validateMoneyStr(amount))
		{
			return null;
		}
		
		if(user_name==null || user_name.trim().equals("") )
		{
			return null;
		}
		if(amount==null || amount.trim().equals("") )
		{
			return null;
		}

		String result = "兑换失败请重试";
		
		String game_id = "0DA03D28D25F4E278D3DEEC5CFDF3E32";
		
		String key = MD5Util.md5Hex(MERCHANT_KEY+"-"+user_name+"-"+amount);
		
		String url = "http://wap.wanba.cn/Interface/UserConsume.aspx";
		
		logger.debug("url："+url+";user_name="+user_name+";Amount="+amount);
		
		UPayRecordDao uPayRecordDao = new UPayRecordDao();
		int pay_record_id = uPayRecordDao.insert(user_name, amount,role_info.getBasicInfo().getPPk());
		
		
		Map<String,String> params  =  new HashMap<String,String>();
		params.put("Name", user_name);
		params.put("Amount", amount);
		params.put("GameID", game_id);
		params.put("Key", key);
		
		
		 HttpRequester request = new HttpRequester(); 
         request.setDefaultContentEncoding("utf-8");
         
         HttpRespons response = null;
         try {
			response = request.sendPost(url,params);//提交验证请求
		} catch (IOException e) {
			e.printStackTrace();
			return result;
		}   
		
		if( response.getCode()!=200 )
		{
			logger.debug("请求失败，响应代码为："+response.getCode());
			return result;
		}
		
		try {
			Document document = DocumentHelper.parseText(response.getContent());
			Element root = document.getRootElement();
			
			Element resultElm = root.element("Result");
			
			if( resultElm.getText().equals("True") )
			{
				result = chongzhiYuanbao(role_info.getBasicInfo().getUPk(), role_info.getBasicInfo().getPPk(), Integer.parseInt(amount));
				uPayRecordDao.update(pay_record_id,"biillid", "skybillid1", "skybillid2", "-1", "success");
				return result;
			}
			else
			{
				Element infoElm = root.element("Info");
				result = ""+URLDecoder.decode(infoElm.getText(),"utf-8");  
				uPayRecordDao.update(pay_record_id,"biillid", "skybillid1", "skybillid2", "-1", result);
				return result;
			}
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 充值元宝
	 * @param u_pk
	 * @param p_pk
	 * @param amount
	 * @return                返回充值提示
	 */
	private String chongzhiYuanbao(int u_pk,int p_pk ,int amount)
	{
		String hint = null;
		
		//给玩家充元宝
		EconomyService economyService = new EconomyService();
		
		//给玩家增加元宝
		int yb_num = amount;//1KB获得1个元宝
		int jf_num = yb_num*GameConfig.getJifenNum();//1KB获得1个积分

		economyService.addYuanbao(p_pk,u_pk, yb_num,"chongzhi");
		economyService.addJifen(u_pk,jf_num);//增加积分：每成功兑换1KB获得1个积分
		
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",u_pk);//统计充值人次
		
		long yuanbao_total = economyService.getYuanbao(u_pk);
		
		return hint = "兑换成功,您获得了"+amount+"个【"+GameConfig.getYuanbaoName()+"】,目前您共有【"+GameConfig.getYuanbaoName()+"】×"+yuanbao_total+"!";
	}
	
	
	public static void main( String[] agrs )
	{ 
		BillService tTiaoLoginService = new BillService();
		
//		System.out.print("消费结果="+tTiaoLoginService.pay("devil","1"));
	}
}
