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
 * ���ܣ�˼��k�����Ѵ���
 * @author ls
 * Jun 19, 2009
 * 11:20:15 AM
 */
public class BillService {
	
	Logger logger = Logger.getLogger("log.pay");
	/**
	 * �ύ��������
	 * @param ssid
	 * @param skyid
	 * @param kbamt             ���ѽ��
	 */
	public Map<String,String> pay(String uPk,String ssid,String skyid,String kbamt,int p_pk)
	{
		Map<String,String> pay_results = new HashMap<String,String>();
		
		/**
		 * �۳�k�������url
		 */
		String validate_url = ConfigOfSky.getUrlOfPayKB();
		
		int pay_record_id = addPayRecord(skyid, kbamt,p_pk);//������Ѽ�¼
		
		String billid = this.createBilld(pay_record_id);
		
		Map<String, String> params = new HashMap<String, String>();//�����ύ����
		params.put("ssid", ssid);
		params.put("skyid", skyid);
		params.put("billid", billid +"");
		params.put("kbamt", kbamt);
		
		HttpRespons response = null;
		 HttpRequester request = new HttpRequester(); 
         request.setDefaultContentEncoding("utf-8");
         
         
         try {
			response = request.sendGet(validate_url, params);//�ύ�۷�����
			
		} catch (IOException e) {
			logger.debug("˼������k�������쳣");
			e.printStackTrace();
			return pay_results;
		}   
		
		if( response.getCode()!=200 )//�۷�����ʧ��
		{
			logger.debug("˼������k������ʧ�ܣ���Ӧ����Ϊ��"+response.getCode());
			return pay_results;
		}
		
		try
		{
			ParseNormalContent parseContent = new ParseNormalContent();
			pay_results = parseContent.parse(response.getContent());//�õ����������Ӧ���
			
			String respones_result = pay_results.get("result");//��Ӧ���
			
			//�ɹ��۷ѵĴ���
			String skybillid1 = "skybillid1";
			String skybillid2 = "skybillid2";
			String balance = "balance";
			if( respones_result.equals("0") )//��ʾ�۷ѳɹ�
			{
				//�ɹ��۷ѵĴ���
				skybillid1 = pay_results.get("skybillid1");
				skybillid2 = pay_results.get("skybillid2");
				balance = pay_results.get("balance");
				
				//����ҳ�Ԫ��
				EconomyService economyService = new EconomyService();
				
				int u_pk = Integer.parseInt(uPk);
				//���������Ԫ��
				int yb_num = Integer.parseInt(kbamt);//1KB���1��Ԫ��
				int jf_num = yb_num*GameConfig.getJifenNum();//1KB���1������

				economyService.addYuanbao(p_pk,u_pk, yb_num,"chongzhi");
				economyService.addJifen(u_pk,jf_num);//���ӻ��֣�ÿ�ɹ��һ�1KB���1������
				
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",u_pk);//ͳ�Ƴ�ֵ�˴�

			}
			else if( respones_result.equals("2") )
			{
				//�����Լ���Ӫ��ϵͳtimeout
				//��������п����Ѿ��ۿ�ɹ��� Ϊ�˴���ͻ�Ͷ�ߣ� ��һ��Ҫ����ʽ���[skyBillID1��skyBillID2]��¼�����ݿ⡣ Balance�ֶβ�����
				skybillid1 = pay_results.get("skybillid1");
				skybillid2 = pay_results.get("skybillid2");
			}
			else if( respones_result.equals("3") )
			{
				//����
				//��ʾ��ҵ���balance�ֶΣ��� ������ҳ�ֵ��
				balance = pay_results.get("balance");
			}
			updatePayRecord( pay_record_id,billid, skybillid1, skybillid2, balance, respones_result);//�������Ѽ�¼
		}
		catch (Exception e)
		{
			updatePayRecord( pay_record_id,billid, "fail", "fail", "fail", "fail");//�������Ѽ�¼
		}
		return pay_results;
	}

	/**
	 * ������Ѽ�¼
	 * @param skyid
	 * @param kbamt
	 * @result:0
	 * @Skybillid1 �� skybillid2�� �ǳ�������ҵĶ�����ˮ�š� �뱣�浽���Ѽ�¼���Ա��ڶ���
	 * @balance:0     �û���ǰ�˻��е����
	 * @return
	 */
	public int addPayRecord( String skyid,String kbamt,int p_pk )
	{
		UPayRecordDao uPayRecordDao = new UPayRecordDao();
		return uPayRecordDao.insert(skyid, kbamt,p_pk);
	}
	
	/**
	 * ������Ѽ�¼
	 * @param skyid
	 * @param billid
	 * @param kbamt
	 * @result:0
	 * @Skybillid1 �� skybillid2�� �ǳ�������ҵĶ�����ˮ�š� �뱣�浽���Ѽ�¼���Ա��ڶ���
	 * @balance:0     �û���ǰ�˻��е����
	 * @return
	 */
	public void updatePayRecord( int id,String billid,String skybillid1,String skybillid2,String balance,String respones_result )
	{
		UPayRecordDao uPayRecordDao = new UPayRecordDao();
		uPayRecordDao.update(id,billid, skybillid1, skybillid2, balance, respones_result);
	}
	
	
	/**
	 * ������Ӧ������������Ӧ����ʾ
	 * @param pay_results          ��Ӧ���
	 * @return
	 */
	public String getPayHintByResult( Map<String,String> pay_results )
	{
		String hint = "";
		
		if( pay_results==null )
		{
			return hint = "��ֵʧ��,������!";
		}
		
		String respones_result = pay_results.get("result");
		
		if( respones_result==null )
		{
			return hint = "��ֵʧ��,������!";
		}
		
		//�ۿ�ɹ�
		if( respones_result.equals("0"))
		{
			hint = "��ֵ�ɹ�";
		}
		//��֤ʧ��:Ssid ��Ч���ߺ�skyid��ƥ��,Ӧ����ʾ������µ�½����
		//�����ֶΣ�skybillid1��skybillid2��balance�������֡�
		else if( respones_result.equals("1") )
		{
			hint = "�������ڳ�������ҵ�½";
		}
		//�����Լ���Ӫ��ϵͳtimeout
		//��������п����Ѿ��ۿ�ɹ��� Ϊ�˴���ͻ�Ͷ�ߣ� ��һ��Ҫ����ʽ���[skyBillID1��skyBillID2]��¼�����ݿ⡣ Balance�ֶβ�����
		else if( respones_result.equals("2") )
		{
			
		}
		//����
		//��ʾ��ҵ���balance�ֶΣ��� ������ҳ�ֵ��
		else if( respones_result.equals("3") )
		{
			String balance = pay_results.get("balance");
			//���㣡��ֻ��10K�ң����ֵ���ٳ��Զһ���
			hint = "����!��ֻ��"+balance+"K��,���ֵ���ٳ��Զһ�!";
		}
		//: billID �ظ��� �����ֶ���Ч
		else if( respones_result.equals("6") )
		{
			hint = "��ֵʧ��,������!";
		}
		//����ֵ��  �����ڲ����� ֻ��Ҫ��ʾ����ʧ�ܣ� errorcode��ʾ���û����ڷ����Ϳ����ˡ�  [�����ֶβ�����]
		else
		{
			hint = "��ֵʧ��,�������:"+respones_result+",������!";
		}
		
		return hint;
	}
	
	/**
	 * ����billd,���ɹ��򣺷���id(1λ)+s+pay_record_id(3λ)+s+ʱ�䣨����������13λ��,�ܳ�19λ
	 * @return
	 */
	public String createBilld( int pay_record_id )
	{
		StringBuffer result = new StringBuffer();
		
		result.append(GameConfig.getAreaId()+"s").append(pay_record_id%1000+"s").append(System.currentTimeMillis());
		
		return result.toString();
	}
	/**
	 * ��ֵ����  �ڰٱ����зų�ֵ����
	 * @param yb_numԪ������
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
				//��ֵ2000��Ǯ��
				GoodsService goodsService = new GoodsService();
				String prop_id = GameConfig.getChongzhijiangliCommodityinfoId().trim();//���ŵĵ���id
				goodsService.putPropToWrap(p_pk, Integer.parseInt(prop_id), 1,GameLogManager.G_SYSTEM);//�������
				hint = begintime+"��"+endtime+",ÿ��ֵ2000"+GameConfig.getYuanbaoName()+"���ɻ��1888������Ǯ��(Ǯ��������������̳���,�������������������δ���ųɹ�,�ٷ����貹��)";
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
		 * �۳�k�������url
		 */
		String validate_url = "http://wapok.cn/n_gamevoucher.php";
		
		int pay_record_id = addPayRecord(skyid, amt,p_pk);//������Ѽ�¼
		
		String billid = this.createBilld(pay_record_id);
		
		Map<String, String> params = new HashMap<String, String>();//�����ύ����
		params.put("osid", osid);
		params.put("account", skyid);
		params.put("amt", amt);
		params.put("billid", billid);
		
		HttpRespons response = null;
		 HttpRequester request = new HttpRequester(); 
         request.setDefaultContentEncoding("utf-8");
         
         
         try {
			response = request.sendGet(validate_url, params);//�ύ�۷�����
			
		} catch (IOException e) {
			logger.debug("˼������k�������쳣");
			e.printStackTrace();
			return pay_results;
		}   
		
		try
		{
			ParseNormalContent parseContent = new ParseNormalContent();
			pay_results = parseContent.parse(response.getContent());//�õ����������Ӧ���
			
			String respones_result = null;
			Set<String> keys = pay_results.keySet();
			for(String key:keys )
			{
				if( key.indexOf("result")!=-1 )
				{
					respones_result = pay_results.get(key);
				}
			}
			
			//�ɹ��۷ѵĴ���
			String skybillid1 = "skybillid1";
			String skybillid2 = "skybillid2";
			String balance = "balance";
			if( respones_result.equals("10") )//��ʾ�۷ѳɹ�
			{
				//�ɹ��۷ѵĴ���
				skybillid1 = pay_results.get("okbillid1");
				skybillid2 = pay_results.get("okbillid2");
				balance = pay_results.get("balance");
				
				//����ҳ�Ԫ��
				EconomyService economyService = new EconomyService();
				
				int u_pk = Integer.parseInt(uPk);
				//���������Ԫ��
				int yb_num = Integer.parseInt(amt)*112;//1KB���112��Ԫ��
				int jf_num = yb_num*GameConfig.getJifenNum();//1KB���1������

				economyService.addYuanbao(p_pk,u_pk, yb_num,"chongzhi");
				economyService.addJifen(u_pk,jf_num);//���ӻ��֣�ÿ�ɹ��һ�1KB���1������
				
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",u_pk);//ͳ�Ƴ�ֵ�˴�

			}
			else{
				//����
				//��ʾ��ҵ���balance�ֶΣ��� ������ҳ�ֵ��
				balance = pay_results.get("balance");
			}
			updatePayRecord( pay_record_id,billid, skybillid1, skybillid2, balance, respones_result);//�������Ѽ�¼
		}
		catch (Exception e)
		{
			updatePayRecord( pay_record_id,billid, "fail", "fail", "fail", "fail");//�������Ѽ�¼
		}
		return pay_results;
	}
	
	public String OKcallbackpay(String okid ,String okbillid1, String okbillid2 ,String oknum)
	{
		/**
		 * �۳�k�������url
		 */
		int pay_record_id = addPayRecord(okid, oknum,0);//������Ѽ�¼
		
		String billid = this.createBilld(pay_record_id);
				
		EconomyService economyService = new EconomyService();
		PassportService passportService = new PassportService();
		PassportVO passport = passportService.getPassportInfoByUserName(okid, Channel.OKP);
		
		int yb_num = Integer.parseInt(oknum)*112;//1KB���1��Ԫ��
		int jf_num = yb_num*GameConfig.getJifenNum();//1KB���1������
		economyService.addYuanbao(0,passport.getUPk(), yb_num,"chongzhi");
		economyService.addJifen(passport.getUPk(),jf_num);//���ӻ��֣�ÿ�ɹ��һ�1KB���1������
			
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",passport.getUPk());//ͳ�Ƴ�ֵ�˴�
		updatePayRecord( pay_record_id,billid, okbillid1, okbillid2, "callback", "0");//�������Ѽ�¼
		return "success";
	}
}
