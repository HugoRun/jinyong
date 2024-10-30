package com.ls.web.service.cooperate.sina;

import com.ls.ben.dao.cooparate.dangle.PassportDao;
import com.ls.ben.dao.cooparate.sky.UPayRecordDao;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.model.log.GameLogManager;
import com.ls.pub.config.GameConfig;
import com.ls.pub.config.sky.ConfigOfSky;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.pub.util.http.HttpRequester;
import com.ls.pub.util.http.HttpRespons;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.EconomyService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class BillService
{
	Logger logger = Logger.getLogger("log.pay");

	public Map<String, String> pay(String uPk, String ssid, String sina_uid,
			String sina_amt, int p_pk)
	{
		Map<String, String> pay_results = new HashMap<String, String>();

		String validate_url = ConfigOfSky.getUrlOfPayKB();

		int pay_record_id = addPayRecord(sina_uid, sina_amt, p_pk);

		String billid = createBilld(pay_record_id);

		HttpRespons response = null;
		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("gbk");
		Map<String, String> params = new HashMap<String, String>();
		Date date = new Date();
		String time = (date.getTime() / 1000L) + ".000";
		String hashStr = MD5Util.md5Hex("12" + sina_uid + sina_amt + pay_record_id
				+ time + "TY8U2Rx5rh5R6HJqm9O21peCM3sfw95X");
		params.put("uid", sina_uid);
		params.put("pid", "12");
		params.put("amount", sina_amt);
		params.put("snum", pay_record_id+"");
		params.put("tm", "time");
		params.put("hs", hashStr);
		params.put("cbUrl", GameConfig.getContextPath()+"/sina/callback.do");
		params.put("backUrl", GameConfig.getContextPath()+"/mall.do?cmd=n0");
		params.put("backTitle", "��ֵ�һ�");
		params.put("item", GameConfig.getYuanbaoName()+sina_amt);
		params.put("info", "info");
		try
		{
			response = request.sendPost(validate_url, params);
		}
		catch (IOException e)
		{
			this.logger.debug("�˻����������쳣");
			e.printStackTrace();
			return pay_results;
		}

		String result = response.getContent();
		pay_results = getchongzhi(result);

		String respones_result = (String) pay_results.get("statusCode");

		String skybillid1 = "skybillid1";
		String skybillid2 = "skybillid2";
		String balance = "balance";
		if (respones_result !=null && respones_result.equals("0000"))
		{
			balance = (String) pay_results.get("balance");

			EconomyService economyService = new EconomyService();

			int u_pk = Integer.parseInt(uPk);

			int yb_num = Integer.parseInt(sina_amt);
			int jf_num = yb_num * GameConfig.getJifenNum();

			economyService.addYuanbao(p_pk, u_pk, yb_num, "chongzhi");
			economyService.addJifen(u_pk, jf_num);

			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(0, 0, 1, "player", "chongzhi", u_pk);
			updatePayRecord(pay_record_id, billid, skybillid1, skybillid2,
					balance, respones_result);
		}
		else
		{
			skybillid1 = (String) pay_results.get("skybillid1");
			skybillid2 = (String) pay_results.get("skybillid2");
			updatePayRecord(pay_record_id, billid, skybillid1, skybillid2,
					balance, respones_result);
		}
		return pay_results;
	}

	public int addPayRecord(String skyid, String kbamt, int p_pk)
	{
		UPayRecordDao uPayRecordDao = new UPayRecordDao();
		return uPayRecordDao.insert(skyid, kbamt, p_pk);
	}

	private void updatePayRecord(int id, String billid, String skybillid1,
			String skybillid2, String balance, String respones_result)
	{
		UPayRecordDao uPayRecordDao = new UPayRecordDao();
		uPayRecordDao.update(id, billid, skybillid1, skybillid2, balance,
				respones_result);
	}

	public String getPayHintByResult(Map<String, String> pay_results)
	{
		String hint = "";

		if (pay_results == null)
		{
			return (hint = "��ֵʧ��,������!");
		}

		String respones_result = (String) pay_results.get("result");

		if (respones_result == null)
		{
			return (hint = "��ֵʧ��,������!");
		}

		if (respones_result.equals("0000"))
		{
			hint = "��ֵ�ɹ�";
		}
		else
			if (respones_result.equals("2002"))
			{
				hint = "�޴��ʺ�";
			}
			else
				if ((respones_result.equals("2004")))
				{
						hint = "����";
				}
				else
					{
					hint = "��ֵʧ��";
					}
		return hint;
	}

	public String createBilld(int pay_record_id)
	{
		StringBuffer result = new StringBuffer();

		result.append(GameConfig.getAreaId() + "s").append(
				(pay_record_id % 1000) + "s")
				.append(System.currentTimeMillis());

		return result.toString();
	}

	public String chongZhiJiangLi(int p_pk, int yb_num)
	{
		String hint = "";
		String begintime = GameConfig.getChongzhijiangliBeginTime();
		String endtime = GameConfig.getChongzhijiangliEndTime();
		Date nowTime = new Date();
		Date begin = DateUtil.strToDate(begintime);
		Date end = DateUtil.strToDate(endtime);
		if ((nowTime.after(begin)) && (nowTime.before(end)) && (yb_num == 2000))
		{
			GoodsService goodsService = new GoodsService();
			String prop_id = GameConfig.getChongzhijiangliCommodityinfoId()
					.trim();
			goodsService.putPropToWrap(p_pk, Integer.parseInt(prop_id), 1,GameLogManager.G_SYSTEM);
			hint = begintime
					+ "��"
					+ endtime
					+ ",ÿ��ֵ2000"+GameConfig.getYuanbaoName()+"���ɻ��1888������Ǯ��(Ǯ��������������̳���,�������������������δ���ųɹ�,�ٷ����貹��)";
		}

		return hint;
	}

	public static void main(String[] args)
	{
		BillService billService = new BillService();
		System.out.print("billd==" + billService.createBilld(4));
	}

	public HashMap<String, String> getchongzhi(String is)
	{
		HashMap<String, String> chongzhiout = new HashMap<String, String>();
		InputStream source = new ByteArrayInputStream(is.getBytes());
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			Document doc = dombuilder.parse(source);
			NodeList books = doc.getElementsByTagName("Paycenter");
			for (int i = 0; i < books.getLength(); ++i)
			{
				String mm = doc.getElementsByTagName("statusCode").item(i)
						.getFirstChild().getNodeValue();
				if ("0000".equals(mm))
				{
					chongzhiout.put("statusCode", doc.getElementsByTagName(
							"statusCode").item(i).getFirstChild()
							.getNodeValue());
					chongzhiout.put("mobile", doc
							.getElementsByTagName("mobile").item(i)
							.getFirstChild().getNodeValue());
					chongzhiout.put("shopId", doc
							.getElementsByTagName("shopId").item(i)
							.getFirstChild().getNodeValue());
					chongzhiout.put("serialNo", doc.getElementsByTagName(
							"serialNo").item(i).getFirstChild().getNodeValue());
					chongzhiout.put("hashStr", doc.getElementsByTagName(
							"hashStr").item(i).getFirstChild().getNodeValue());
					chongzhiout.put("balance", doc.getElementsByTagName(
							"balance").item(i).getFirstChild().getNodeValue());
				}
				else
				{
					chongzhiout.put("statusCode", doc.getElementsByTagName(
							"statusCode").item(i).getFirstChild()
							.getNodeValue());
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return chongzhiout;
	}

	public HashMap<String, String> getyue(String is)
	{
		HashMap<String, String> yueout = new HashMap<String, String>();
		InputStream source = new ByteArrayInputStream(is.getBytes());
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			Document doc = dombuilder.parse(source);
			NodeList books = doc.getElementsByTagName("Paycenter");
			for (int i = 0; i < books.getLength(); ++i)
			{
				String mm = doc.getElementsByTagName("statusCode").item(i)
						.getFirstChild().getNodeValue();
				if ("0000".equals(mm))
				{
					yueout.put("statusCode", doc.getElementsByTagName(
							"statusCode").item(i).getFirstChild()
							.getNodeValue());
					yueout.put("mobile", doc.getElementsByTagName("mobile")
							.item(i).getFirstChild().getNodeValue());
					yueout.put("balance", doc.getElementsByTagName("balance")
							.item(i).getFirstChild().getNodeValue());
					yueout.put("createtime", doc.getElementsByTagName(
							"createtime").item(i).getFirstChild()
							.getNodeValue());
					yueout.put("lastcharge", doc.getElementsByTagName(
							"lastcharge").item(i).getFirstChild()
							.getNodeValue());
					yueout.put("lastpay", doc.getElementsByTagName("lastpay")
							.item(i).getFirstChild().getNodeValue());
					yueout
							.put("maxcharge", doc.getElementsByTagName(
									"maxcharge").item(i).getFirstChild()
									.getNodeValue());
					yueout.put("maxpay", doc.getElementsByTagName("maxpay")
							.item(i).getFirstChild().getNodeValue());
					yueout.put("totalpaytoday", doc.getElementsByTagName(
							"totalpaytoday").item(i).getFirstChild()
							.getNodeValue());
				}
				else
				{
					yueout.put("statusCode", doc.getElementsByTagName(
							"statusCode").item(i).getFirstChild()
							.getNodeValue());
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return yueout;
	}
	
	// �����۷�
	public void payByYiDong(String uid,String errcode,String snum,String tm,String hs)
	{
		/**
		 * ��¼��Ϣ
		 */
		String skybillid1 = "skybillid1";
		String skybillid2 = "skybillid2";
		String balance = "balance";
		if (errcode == null || !errcode.equals("0"))
		{
			updatePayRecord(Integer.parseInt(snum), "sina", skybillid1, skybillid2,
					balance, errcode);// �������Ѽ�¼
		}
		else
		{
			PassportDao passportDao = new PassportDao();
			PassportVO passport = passportDao.getPassportByUserID(uid, Channel.SINA);
			
			// ����ҳ�Ԫ��
			EconomyService economyService = new EconomyService();
			int u_pk = passport.getUPk();
			// ���������Ԫ��
			UPayRecordDao uPayRecordDao = new UPayRecordDao();
			int yb_num = uPayRecordDao.getMoney(Integer.parseInt(snum))*100;// 1KB���1��Ԫ��
			int jf_num = yb_num * GameConfig.getJifenNum();// 1KB���1������

			economyService.addYuanbao(0, u_pk, yb_num, "chongzhi");
			economyService.addJifen(u_pk, jf_num);// ���ӻ��֣�ÿ�ɹ��һ�1KB���1������

			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",
					u_pk);// ͳ�Ƴ�ֵ�˴�
			updatePayRecord(Integer.parseInt(snum), "sina", "", "", "", errcode);// �������Ѽ�¼
		}
	}
}