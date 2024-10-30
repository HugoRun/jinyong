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
 * ���ܣ��̳��߼�����
 * @author ls
 * May 12, 2009
 * 1:52:39 PM
 */
public class MallService
{
	Logger logger = Logger.getLogger("log.pay");
	/**
	 * �������vip�ȼ���
	 * �޻�Ա���װ���������ã�
	 * ��Ѫ��Ա�����Ļ�Ա���ã�
	 * �����Ա���ԺյĹ������ã�
	 * ������Ա�����ϵı������ã�
	 * @return
	 */
	public String getRoleTitleByVIPLevel(int vip_level)
	{
		String role_title = "";
		
		if( vip_level==1 )
		{
			return role_title = "���Ļ�Ա";
		}
		else if( vip_level==2 )
		{
			return role_title = "�ԺյĹ���";
		}
		else if( vip_level==3 )
		{
			return role_title = "���ϵı���";
		}

		return role_title;
	}
	
	/**
	 * �����̳������͵õ��̳�������
	 */
	public String getShopTitleByType(String type)
	{
		String title = "";
		
		if( type.equals("1") )
		{
			title = "�������Ա��";
		}
		else if( type.equals("2") )
		{
			title = "�����������";
		}
		else if( type.equals("3") )
		{
			title = "������Կ�ס�";
		}
		else if( type.equals("4") )
		{
			title = "��������ߡ�";
		}
		else if( type.equals("5") )
		{
			title = "������������";
		}
		else if( type.equals("6") )
		{
			title = "���������ߡ�";
		}
		else if( type.equals("7") )
		{
			title = "��װ��������";
		}
		else if( type.equals("8") )
		{
			title = "��װ���㻯��";
		}
		else if( type.equals("9") )
		{
			title = "�����＼�ܡ�";
		}
		else if( type.equals("10") )
		{
			title = "���������ɡ�";
		}
		else if( type.equals("11") )
		{
			title = "������ߡ�";
		}
		else if( type.equals("12") )
		{
			title = "����Ὠ�衿";
		}
		return title;
	}
	
	/**
	 * �õ���ҳ������Ʒ
	 */
	public List<CommodityVO> getDiscountCommodityListOfMainPage()
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getDiscountCommodityListOfMainPage();
	}
	
	/**
	 * �õ����������Ʒ
	 */
	public QueryPage getDiscountCommodityList(int page_no)
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getDiscountCommodityList(page_no);
	}
	
	
	/**
	 * ��Ʒ����
	 */
	public CommodityVO getCommodityInfo( String c_id )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getCommodity(c_id);
	}
	/**
	 * ���Ƽ���Ʒ������õ�һ���Ƽ���Ʒ
	 *//*
	public CommodityVO getRandomHotCommodity()
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getRandomHotCommodity();
	}*/
	
	/**
	 * ͨ������ID�õ���Ʒ����
	 */
	public CommodityVO getPropCommodityInfo( String prop_id )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getPropCommodity(prop_id);
	}
	
	/**
	 * ��Ʒ��������
	 */
	public PropVO getPropInfo( String c_id )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getProp(c_id);
	}
	
	/**
	 * �õ���ҵ��̳Ǽ�¼
	 */
	public QueryPage getLogList(int u_pk,int page_no)
	{
		MallLogDao mallLogDao = new MallLogDao();
		return mallLogDao.getLogList(u_pk, page_no);
	}
	
	/**
	 * ��¼�̳���־
	 */
	public void recordLog(RoleEntity role_info,String mall_log,String propName,int propNum,int propPrice,int buyType)
	{
		int u_pk = role_info.getBasicInfo().getUPk();
		String role_name = role_info.getBasicInfo().getName();
		MallLogDao mallLogDao = new MallLogDao();
		mallLogDao.insert(u_pk, role_name,mall_log,propName,propNum,propPrice,buyType);
	}
	/**
	 * ֱ�ӹ���
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
	 * �����̳ǵ���
	 * @param u_pk
	 * @param commodity
	 * @param sell_num
	 * @param buy_type				1Ϊ��������,2Ϊ���򵫲��������Ʒ.
	 * 
	 * @return                     ����Ϊnull��ʾ�ɹ����ǿ�Ϊʧ��ԭ��
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
		
		hint = validateBuy(role_info,commodity,sell_num_str);//�ж��Ƿ����㹻��Ǯ
		
		if( hint !=null )
		{
			return hint;
		}
		
		int sell_num = Integer.parseInt(sell_num_str.trim());
		
		
		if( buy_type == 1 && goodService.putPropToWrap(p_pk, commodity.getPropId(), sell_num,GameLogManager.G_MALL)==-1 )//����Ʒ�������
		{
			hint = "�����ռ䲻��";
			return hint;
		}
		
    		
		int user_discount = 100;//vip�ۿ�
		
		Vip role_vip = role_info.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			user_discount = role_vip.getDiscount();
		}
		
		int need_num = commodity.getCurPrice(user_discount)*sell_num;//��Ҫ���ĵ�����
		
		String buy_log = null;
		
		
		if( commodity.getBuyMode()==1)//����Ԫ���������
		{
			economyService.spendYuanbao(u_pk,need_num);//����Ԫ��
			buy_log =DateUtil.getTodayStr()+","+role_name+""+GameConfig.getYuanbaoName()+""+need_num+"����"+commodity.getPropName()+"��"+sell_num+"";
			// ִ��ͳ�Ƶ�������
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(0, 9, need_num,StatisticsType.XIAOSHOU, StatisticsType.BUY, u_pk);
		}
		else if( commodity.getBuyMode()==2)//���û����������
		{
			economyService.spendJifen(u_pk,need_num);//���Ļ���
			buy_log =DateUtil.getTodayStr()+","+role_name+"���ֹ���"+commodity.getPropName()+"��"+sell_num+"";
		}

		commodityDao.addSellNum(commodity.getId(), sell_num);//������Ʒ��������
		recordLog(role_info, buy_log,commodity.getPropName(),sell_num,need_num,commodity.getBuyMode());//��¼������־
		return hint;
	}
	
	/**
	 * �����̳ǵ���,����ʲô���������,����ר����������������ת,
	 * @param u_pk
	 * @param commodity
	 * @param sell_num
	 * @return                     ��Ҫ���ĵ�����
	 *
	public int buyWithoutGiveGoods(RoleEntity role_info,CommodityVO commodity,int sell_num)
	{
		int u_pk = role_info.getBasicInfo().getUPk();
		String role_name = role_info.getBasicInfo().getName();
		
		CommodityDao commodityDao = new CommodityDao();
		
		int user_discount = 100;
		
		int need_num = commodity.getCurPrice(user_discount)*sell_num;//��Ҫ���ĵ�����
		
		String buy_log = null;
		
		EconomyService economyService = new EconomyService(); 
		if( commodity.getBuyMode()==1)//Ԫ����Ʒ
		{
			economyService.spendYuanbao(u_pk,need_num);//����Ԫ��
			buy_log =DateUtil.getTodayStr()+","+role_name+"Ԫ��"+need_num+"����"+commodity.getPropName()+"��"+sell_num+"";
			// ִ��ͳ�Ƶ�������
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(0, 9, need_num,
					StatisticsType.XIAOSHOU, StatisticsType.BUY, u_pk);
		}
		else if( commodity.getBuyMode()==2)//������Ʒ
		{
			economyService.spendJifen(u_pk,need_num);//���Ļ���
			buy_log =DateUtil.getTodayStr()+","+role_name+"���ֹ���"+commodity.getPropName()+"��"+sell_num+"";
		}

		commodityDao.addSellNum(commodity.getId(), sell_num);//������Ʒ��������
		recordLog(role_info, buy_log);//��¼������־
		
		return need_num;
	}*/
	
	
	
	
	/**
	 * �õ���Ʒ�б�
	 */
	public QueryPage getCommodityListByType(String type,int page_no )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getYuanBaoCommodityList(type, page_no);
	}
	
	
	/**
	 * �õ�������Ʒ�б�
	 */
	public QueryPage getHotSellCommodityList(int page_no )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getHotSellCommodityList(page_no);
	}
	/**
	 * �õ���Ա��Ʒ�б�
	 */
	public QueryPage getVIPCommodityList(int page_no )
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getVIPCommodityList(page_no);
	}
	
	/**
	 * �õ���ҳ������Ʒ�б�
	 */
	public List<CommodityVO> getNewSellCommodityListOfMainPage()
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getNewSellCommodityListOfMainPage();
	}
	
	/**
	 * �õ���ҳ������Ʒ�б�
	 */
	public List<CommodityVO> getHotSellCommodityListOfMainPage()
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getHotSellCommodityListOfMainPage();
	}
	
	/**
	 * �õ�������Ʒ�б�
	 */
	public QueryPage getJifenCommodityList(int buy_mode,String type,int page_no)
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getJiFenCommodityList(buy_mode,type,page_no);
	}
	
	/**
	 * �õ���ҳ������Ʒ�б�
	 */
	public List<CommodityVO> getJifenCommodityListOfMainPage()
	{
		CommodityDao commodityDao = new CommodityDao();
		return commodityDao.getJiFenCommodityListOfMainPage();
	}
	
	/**
	 * ��֤�Ƿ���Թ���,�жϻ��ֻ�Ԫ���Ƿ�
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
		//��֤���������Ƿ�
		
		int user_discount = 100;
		
		Vip role_vip = role_info.getTitleSet().getVIP();
		if( role_vip!=null )
		{
			user_discount = role_vip.getDiscount();
		}
		
		int total_money = commodity.getCurPrice(user_discount)*Integer.parseInt(sell_num);//��Ҫ���ѵ�Ԫ������ֵ�����
		
		long my_money = 0;//������е�Ԫ������ֵ�����
		
		if( commodity.getBuyMode()==1)//Ԫ����Ʒ
		{
			my_money = economyService.getYuanbao(u_pk);
		}
		else if( commodity.getBuyMode()==2)//������Ʒ
		{
			my_money = economyService.getJifen(u_pk);
		}
		
		if( my_money<total_money)
		{
			return hint = "����";
		}
		
		return hint;
	}
	/**
	 * �����̳ǵ���  ר��Ϊ����
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
		/*******�жϰ����ռ��Ƿ��㹻********/
		if( buy_type == 1 && role_info.getBasicInfo().getWrapSpare()<=sell_num)
		{
			hint = "�����ռ䲻��";
			return hint;
		}
		/********ȥ����ƽ̨�۷ѹ������********/
		if(ConfigOfTele.getPropCode(c_id)==null)
		{
			hint="���ڲ����Թ���õ���!";
			return hint;
		}
		String status=buyPropPost(request,c_id,sell_num_str);
		/*****0��ʾ�۷ѳɹ�1��ʾ�۷�ʧ��******/
		if(status!=null)
		{
			if("0".equals(status))
			{
				goodService.putPropToWrap(p_pk, commodity.getPropId(), sell_num,GameLogManager.G_MALL);
			}
			else
			{
				hint="����ʧ�ܣ����ѯ���ĵ����Ƿ��㹻";
				return hint;
			}
		}
		else
		{
			hint="����ƽ̨�۷Ѵ���!����ϵGM";
			return hint;
		}
		int need_num = commodity.getCurPrice(100)*sell_num;//��Ҫ���ĵ�����
		String buy_log = null;
		if( commodity.getBuyMode()==1)//Ԫ����Ʒ
		{
			economyService.spendYuanbao(u_pk,need_num);//����Ԫ��
			buy_log =DateUtil.getTodayStr()+","+role_name+""+GameConfig.getYuanbaoName()+""+need_num+"����"+commodity.getPropName()+"��"+sell_num+"";
			// ִ��ͳ�Ƶ�������
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(0, 9, need_num,StatisticsType.XIAOSHOU, StatisticsType.BUY, u_pk);
		}
		if(commodity.getIsHot() > 0){
			commodityDao.addSellNumByHot(commodity.getId(), sell_num);//������Ʒ��������
		}else{
			commodityDao.addSellNum(commodity.getId(), sell_num);//������Ʒ��������
		}
		recordLog(role_info, buy_log,commodity.getPropName(),Integer.parseInt(sell_num_str),commodity.getOriginalPrice(),buy_type);//��¼������־
		return hint;
	}
	/**
	 * ����ר�� ��������
	 * ֱ�ӿ۷Ѳ�����Ҷ���
	 */
	public String consumeForTele(HttpServletRequest request,RoleEntity role_info,String c_id,String sell_num_str)
	{
		String hint = null;
		/********ȥ����ƽ̨����********/
		if(ConfigOfTele.getPropCode(c_id)==null)
		{
			hint="û�д����Ѵ��룡����ϵ�ǣ�.";
			return hint;
		}
		String status=buyPropPost(request,c_id,sell_num_str);
		/*****0��ʾ�۷ѳɹ�1��ʾ�۷�ʧ��******/
		if(status!=null)
		{
			if("0".equals(status))
			{
				System.out.println("����ƽ̨���ѳɹ���");
			}
			else
			{
				hint="�۷�ʧ�ܣ����ѯ���ĵ����Ƿ��㹻";
				return hint;
			}
		}
		else
		{
			hint="����ƽ̨�۷Ѵ���!����ϵGM";
			return hint;
		}
		return hint;
	}
	/**
	 * ����ŷ���post���������
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
		/****��װPost����****/
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
			logger.info("���͹����������ʼ.....");
			responses=requester.sendPostTele("http://202.102.39.11:9088/gameinterface/OrderGameProps",params);
			logger.info("���͹�������������.....");
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
				logger.info("�ĵ���������....");
			}
		}
		catch (IOException e)
		{
			logger.info("���͹�������������.....");
		}
		return respones_result;
	}
	/**
	 * ������Ҳ�ѯʣ�����
	 */
	public String serchPoint(String url,Map<String, String> params)
	{
		HttpRequester requester = new HttpRequester();
		HttpRespons responses = null;
		String hRet=null;
		String point=null;
		try
		{
			logger.info("���Ͳ�ѯʣ���������ʼ.....");
			responses=requester.sendPostTele(url,params);
			logger.info("���Ͳ�ѯʣ���������ʼ.....");
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
				logger.info("�ĵ���������....");
			}
		}
		catch (IOException e)
		{
			logger.info("���Ͳ�ѯʣ������������.....");
		}
		/*****����1��ʾ��ѯʧ�ܳɹ��򷵻�ʣ�����****/
		if(hRet!=null&&"0".equals(hRet)&&point!=null)
		{
			return point;
		}
		else
		{
			return "1";
		}
	}
	/****�õ�ʱ�����ˮ�ַ���***/
	public static String getDateStr()
	{
		String todayStr = null;
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		todayStr= df.format(date.getTime());
		return todayStr;
	}
	
}
