package com.ls.web.service.mall;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ls.ben.dao.mall.CommodityDao;
import com.ls.ben.dao.mall.MallLogDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.mall.CommodityVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.model.vip.Vip;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.config.tele.ConfigOfTele;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.http.HttpRequester;
import com.ls.pub.util.http.HttpRespons;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.validate.ValidateService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;

/**
 * 功能：商城逻辑管理
 * @author ls
 * May 12, 2009
 * 1:52:39 PM
 */
public class MallService
{
	Logger logger = Logger.getLogger("log.pay");
	/**
	 * 根据玩家vip等级的
	 * 无会员：亲爱的玩家您好！
	 * 铁血会员：尊贵的会员您好！
	 * 贵宾会员：显赫的贵族您好！
	 * 帝王会员：无上的陛下您好！
	 * @return
	 */
	public String getRoleTitleByVIPLevel(int vip_level)
	{
		String role_title = "";
		
		if( vip_level==1 )
		{
			return role_title = "尊贵的会员";
		}
		else if( vip_level==2 )
		{
			return role_title = "显赫的贵族";
		}
		else if( vip_level==3 )
		{
			return role_title = "无上的陛下";
		}

		return role_title;
	}
	
	/**
	 * 根据商城栏类型得到商城栏标题
	 */
	public String getShopTitleByType(String type)
	{
		String title = "";
		
		if( type.equals("1") )
		{
			title = "【购买会员】";
		}
		else if( type.equals("2") )
		{
			title = "【促销礼包】";
		}
		else if( type.equals("3") )
		{
			title = "【宝箱钥匙】";
		}
		else if( type.equals("4") )
		{
			title = "【增益道具】";
		}
		else if( type.equals("5") )
		{
			title = "【升级掉宝】";
		}
		else if( type.equals("6") )
		{
			title = "【辅助道具】";
		}
		else if( type.equals("7") )
		{
			title = "【装备升级】";
		}
		else if( type.equals("8") )
		{
			title = "【装备点化】";
		}
		else if( type.equals("9") )
		{
			title = "【宠物技能】";
		}
		else if( type.equals("10") )
		{
			title = "【宠物养成】";
		}
		else if( type.equals("11") )
		{
			title = "【活动道具】";
		}
		else if( type.equals("12") )
		{
			title = "【帮会建设】";
		}
		return title;
	}
	
	/**
	 * 得到首页打折商品
	 */
	public List<CommodityVO> getDiscountCommodityListOfMainPage()
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getDiscountCommodityListOfMainPage();
	}
	
	/**
	 * 得到更多打折商品
	 */
	public QueryPage getDiscountCommodityList(int page_no)
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getDiscountCommodityList(page_no);
	}
	
	
	/**
	 * 商品详情
	 */
	public CommodityVO getCommodityInfo( String c_id )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getCommodity(c_id);
	}
	/**
	 * 从推荐商品中随机得到一个推荐商品
	 *//*
	public CommodityVO getRandomHotCommodity()
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getRandomHotCommodity();
	}*/
	
	/**
	 * 通过道具ID得到商品详情
	 */
	public CommodityVO getPropCommodityInfo( String prop_id )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getPropCommodity(prop_id);
	}
	
	/**
	 * 商品道具详情
	 */
	public PropVO getPropInfo( String c_id )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getProp(c_id);
	}
	
	/**
	 * 得到玩家的商城记录
	 */
	public QueryPage getLogList(int u_pk,int page_no)
	{
		MallLogDao mallLogDao = new MallLogDao();
		return mallLogDao.getLogList(u_pk, page_no);
	}
	
	/**
	 * 记录商城日志
	 */
	public void recordLog(RoleEntity role_info,String mall_log,String propName,int propNum,int propPrice,int buyType)
	{
		int u_pk = role_info.getBasicInfo().getUPk();
		String role_name = role_info.getBasicInfo().getName();
		MallLogDao mallLogDao = new MallLogDao();
		mallLogDao.insert(u_pk, role_name,mall_log,propName,propNum,propPrice,buyType);
	}
	/**
	 * 直接购买
	 * @param role_info
	 * @param commodity
	 * @param sell_num_str
	 * @return
	 */
	public String buy(RoleEntity role_info,CommodityVO commodity,String sell_num_str)
	{
		return this.buy(role_info, commodity, sell_num_str, 1);
	}
	/**
	 * 购买商城道具
	 * @param u_pk
	 * @param commodity
	 * @param sell_num
	 * @param buy_type				1为正常购买,2为购买但不给玩家物品.
	 * 
	 * @return                     返回为null表示成功，非空为失败原因
	 */
	public String buy(RoleEntity role_info,CommodityVO commodity,String sell_num_str,int buy_type)
	{
		String hint = null;
		
		int u_pk = role_info.getBasicInfo().getUPk();
		int p_pk = role_info.getBasicInfo().getPPk();
		String role_name = role_info.getBasicInfo().getName();
		
		CommodityDao commodityDao = new CommodityDao();
		
		EconomyService economyService = new EconomyService(); 
		GoodsService goodService = new GoodsService();
		
		hint = validateBuy(role_info,commodity,sell_num_str);//判断是否有足够的钱
		
		if( hint !=null )
		{
			return hint;
		}
		
		int sell_num = Integer.parseInt(sell_num_str.trim());
		
		
		if( buy_type == 1 && goodService.putPropToWrap(p_pk, commodity.getPropId(), sell_num,GameLogManager.G_MALL)==-1 )//把商品放入包裹
		{
			hint = "包裹空间不足";
			return hint;
		}
		
    		
		int user_discount = 100;//vip折扣
		
		Vip role_vip = role_info.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			user_discount = role_vip.getDiscount();
		}
		
		int need_num = commodity.getCurPrice(user_discount)*sell_num;//需要消耗的数量
		
		String buy_log = null;
		
		
		if( commodity.getBuyMode()==1)//是用元宝来购买的
		{
			economyService.spendYuanbao(u_pk,need_num);//消耗元宝
			buy_log =DateUtil.getTodayStr()+","+role_name+""+GameConfig.getYuanbaoName()+""+need_num+"购买"+commodity.getPropName()+"×"+sell_num+"";
			// 执行统计道具销售
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(0, 9, need_num,StatisticsType.XIAOSHOU, StatisticsType.BUY, u_pk);
		}
		else if( commodity.getBuyMode()==2)//是用积分来购买的
		{
			economyService.spendJifen(u_pk,need_num);//消耗积分
			buy_log =DateUtil.getTodayStr()+","+role_name+"积分购买"+commodity.getPropName()+"×"+sell_num+"";
		}

		commodityDao.addSellNum(commodity.getId(), sell_num);//增加商品卖出数量
		recordLog(role_info, buy_log,commodity.getPropName(),sell_num,need_num,commodity.getBuyMode());//记录购买日志
		return hint;
	}
	
	/**
	 * 购买商城道具,但是什么都不给玩家,现在专用于玩家死亡后买九转,
	 * @param u_pk
	 * @param commodity
	 * @param sell_num
	 * @return                     需要消耗的数量
	 *
	public int buyWithoutGiveGoods(RoleEntity role_info,CommodityVO commodity,int sell_num)
	{
		int u_pk = role_info.getBasicInfo().getUPk();
		String role_name = role_info.getBasicInfo().getName();
		
		CommodityDao commodityDao = new CommodityDao();
		
		int user_discount = 100;
		
		int need_num = commodity.getCurPrice(user_discount)*sell_num;//需要消耗的数量
		
		String buy_log = null;
		
		EconomyService economyService = new EconomyService(); 
		if( commodity.getBuyMode()==1)//元宝商品
		{
			economyService.spendYuanbao(u_pk,need_num);//消耗元宝
			buy_log =DateUtil.getTodayStr()+","+role_name+"元宝"+need_num+"购买"+commodity.getPropName()+"×"+sell_num+"";
			// 执行统计道具销售
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(0, 9, need_num,
					StatisticsType.XIAOSHOU, StatisticsType.BUY, u_pk);
		}
		else if( commodity.getBuyMode()==2)//积分商品
		{
			economyService.spendJifen(u_pk,need_num);//消耗积分
			buy_log =DateUtil.getTodayStr()+","+role_name+"积分购买"+commodity.getPropName()+"×"+sell_num+"";
		}

		commodityDao.addSellNum(commodity.getId(), sell_num);//增加商品卖出数量
		recordLog(role_info, buy_log);//记录购买日志
		
		return need_num;
	}*/
	
	
	
	
	/**
	 * 得到商品列表
	 */
	public QueryPage getCommodityListByType(String type,int page_no )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getYuanBaoCommodityList(type, page_no);
	}
	
	
	/**
	 * 得到热销商品列表
	 */
	public QueryPage getHotSellCommodityList(int page_no )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getHotSellCommodityList(page_no);
	}
	/**
	 * 得到会员商品列表
	 */
	public QueryPage getVIPCommodityList(int page_no )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getVIPCommodityList(page_no);
	}
	
	/**
	 * 得到首页热销商品列表
	 */
	public List<CommodityVO> getNewSellCommodityListOfMainPage()
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getNewSellCommodityListOfMainPage();
	}
	
	/**
	 * 得到首页热销商品列表
	 */
	public List<CommodityVO> getHotSellCommodityListOfMainPage()
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getHotSellCommodityListOfMainPage();
	}
	
	/**
	 * 得到积分商品列表
	 */
	public QueryPage getJifenCommodityList(int buy_mode,String type,int page_no)
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getJiFenCommodityList(buy_mode,type,page_no);
	}
	
	/**
	 * 得到首页积分商品列表
	 */
	public List<CommodityVO> getJifenCommodityListOfMainPage()
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getJiFenCommodityListOfMainPage();
	}
	
	/**
	 * 验证是否可以购买,判断积分或元宝是否够
	 */
	public String validateBuy(RoleEntity role_info,CommodityVO commodity,String sell_num)
	{
		String hint = null;
		
		int u_pk = role_info.getBasicInfo().getUPk();
		
		EconomyService economyService = new EconomyService(); 
		
		hint = ValidateService.validateNonZeroNegativeIntegers(sell_num);
		
		if( hint!=null )
		{
			return hint;
		}
		
		//todo
		//验证包裹个数是否够
		
		int user_discount = 100;
		
		Vip role_vip = role_info.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			user_discount = role_vip.getDiscount();
		}
		
		int total_money = commodity.getCurPrice(user_discount)*Integer.parseInt(sell_num);//需要花费的元宝或积分的数量
		
		long my_money = 0;//玩家现有的元宝或积分的数量
		
		if( commodity.getBuyMode()==1)//元宝商品
		{
			my_money = economyService.getYuanbao(u_pk);
		}
		else if( commodity.getBuyMode()==2)//积分商品
		{
			my_money = economyService.getJifen(u_pk);
		}
		
		if( my_money<total_money)
		{
			return hint = "金额不足";
		}
		
		return hint;
	}
	/**
	 * 购买商城道具  专门为电信
	 * @param u_pk
	 * @param commodity
	 * @param sell_num
	 * @param buy_type				
	 * 
	 * @return    
	 */
	public String buyForTelecom(HttpServletRequest request,RoleEntity role_info,CommodityVO commodity,String sell_num_str,int buy_type,String c_id)
	{
		String hint = null;
		
		int u_pk = role_info.getBasicInfo().getUPk();
		int p_pk = role_info.getBasicInfo().getPPk();
		String role_name = role_info.getBasicInfo().getName();
		CommodityDao commodityDao = new CommodityDao();
		EconomyService economyService = new EconomyService(); 
		GoodsService goodService = new GoodsService();
		int sell_num = Integer.parseInt(sell_num_str.trim());
		/*******判断包裹空间是否足够********/
		if( buy_type == 1 && role_info.getBasicInfo().getWrapSpare()<=sell_num)
		{
			hint = "包裹空间不足";
			return hint;
		}
		/********去电信平台扣费购买道具********/
		if(ConfigOfTele.getPropCode(c_id)==null)
		{
			hint="现在不可以购买该道具!";
			return hint;
		}
		String status=buyPropPost(request,c_id,sell_num_str);
		/*****0表示扣费成功1表示扣费失败******/
		if(status!=null)
		{
			if("0".equals(status))
			{
				goodService.putPropToWrap(p_pk, commodity.getPropId(), sell_num,GameLogManager.G_MALL);
			}
			else
			{
				hint="购买失败，请查询您的点数是否足够";
				return hint;
			}
		}
		else
		{
			hint="电信平台扣费错误!请联系GM";
			return hint;
		}
		int need_num = commodity.getCurPrice(100)*sell_num;//需要消耗的数量
		String buy_log = null;
		if( commodity.getBuyMode()==1)//元宝商品
		{
			economyService.spendYuanbao(u_pk,need_num);//消耗元宝
			buy_log =DateUtil.getTodayStr()+","+role_name+""+GameConfig.getYuanbaoName()+""+need_num+"购买"+commodity.getPropName()+"×"+sell_num+"";
			// 执行统计道具销售
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(0, 9, need_num,StatisticsType.XIAOSHOU, StatisticsType.BUY, u_pk);
		}
		if(commodity.getIsHot() > 0){
			commodityDao.addSellNumByHot(commodity.getId(), sell_num);//增加商品卖出数量
		}else{
			commodityDao.addSellNum(commodity.getId(), sell_num);//增加商品卖出数量
		}
		recordLog(role_info, buy_log,commodity.getPropName(),Integer.parseInt(sell_num_str),commodity.getOriginalPrice(),buy_type);//记录购买日志
		return hint;
	}
	/**
	 * 电信专用 其他消费
	 * 直接扣费不给玩家东东
	 */
	public String consumeForTele(HttpServletRequest request,RoleEntity role_info,String c_id,String sell_num_str)
	{
		String hint = null;
		/********去电信平台消费********/
		if(ConfigOfTele.getPropCode(c_id)==null)
		{
			hint="没有此消费代码！请联系ＧＭ.";
			return hint;
		}
		String status=buyPropPost(request,c_id,sell_num_str);
		/*****0表示扣费成功1表示扣费失败******/
		if(status!=null)
		{
			if("0".equals(status))
			{
				System.out.println("电信平台消费成功！");
			}
			else
			{
				hint="扣费失败，请查询您的点数是否足够";
				return hint;
			}
		}
		else
		{
			hint="电信平台扣费错误!请联系GM";
			return hint;
		}
		return hint;
	}
	/**
	 * 向电信发送post请求购买道具
	 */
	public String buyPropPost(HttpServletRequest request,String c_id,String sell_num_str)
	{
		String custId=(String)request.getSession().getAttribute("teleid");
		String channelId=(String)request.getSession().getAttribute("channel_id");
		String netElementId="888999";
		String cpId=(String)request.getSession().getAttribute("cpId");
		String cpProductId=(String)request.getSession().getAttribute("cpProductId");
		String versionId="1_1_2";
		String consumeCode=ConfigOfTele.getPropCode(c_id);
		String transID=cpId+getDateStr()+"827315";
		/****封装Post参数****/
		Map<String, String> params=new HashMap<String, String>();
		params.put("msgType", "OrderGamePropsReq");
		params.put("netElementId",netElementId);
		params.put("custId", custId);
		params.put("cpId", cpId);
		params.put("cpProductId", cpProductId);
		params.put("consumeCode", consumeCode);
		params.put("channelId", channelId);
		params.put("transID", transID);
		params.put("versionId", versionId);
		params.put("toolCounts", sell_num_str);
		HttpRequester requester = new HttpRequester();
		HttpRespons responses = null;
		String respones_result = null;
		String status=null;
		try
		{
			logger.info("发送购买道具请求开始.....");
			responses=requester.sendPostTele("http://202.102.39.11:9088/gameinterface/OrderGameProps",params);
			logger.info("发送购买道具请求结束.....");
			try
			{
				Document document = DocumentHelper.parseText(responses.getContent());
				Element root = document.getRootElement();
				Element resultElm = root.element("hRet");
				Element resultElm1 = root.element("status");
				respones_result=resultElm.getText();
				logger.info("respones_result************"+respones_result);
				status=resultElm.getText();
				logger.info("status*******************"+status);
			}
			catch (DocumentException e)
			{
				logger.info("文档解析错误....");
			}
		}
		catch (IOException e)
		{
			logger.info("发送购买道具请求错误.....");
		}
		return respones_result;
	}
	/**
	 * 电信玩家查询剩余点数
	 */
	public String serchPoint(String url,Map<String, String> params)
	{
		HttpRequester requester = new HttpRequester();
		HttpRespons responses = null;
		String hRet=null;
		String point=null;
		try
		{
			logger.info("发送查询剩余点数请求开始.....");
			responses=requester.sendPostTele(url,params);
			logger.info("发送查询剩余点数请求开始.....");
			try
			{
				Document document = DocumentHelper.parseText(responses.getContent());
				Element root = document.getRootElement();
				Element resultElm = root.element("hRet");
				Element resultElm1 = root.element("point");
				hRet=resultElm.getText();
				logger.info("hRet************"+hRet);
				point=resultElm1.getText();
				logger.info("point*******************"+point);
			}
			catch (DocumentException e)
			{
				logger.info("文档解析错误....");
			}
		}
		catch (IOException e)
		{
			logger.info("发送查询剩余点数请求错误.....");
		}
		/*****返回1表示查询失败成功则返回剩余点数****/
		if(hRet!=null&&"0".equals(hRet)&&point!=null)
		{
			return point;
		}
		else
		{
			return "1";
		}
	}
	/****得到时间的流水字符串***/
	public static String getDateStr()
	{
		String todayStr = null;
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		todayStr= df.format(date.getTime());
		return todayStr;
	}
	
}
