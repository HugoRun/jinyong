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
 * ����:�û���ֵ����
 * Jan 10, 2009
 */
public class BillService
{
	Logger logger = Logger.getLogger("log.pay");
	
	
	/**
	 * �õ���ֵ��¼
	 */
	public UAccountRecordVO getAccountRecord( int record_id)
	{
		UAccountRecordDao accRecordDao = new UAccountRecordDao();
		return accRecordDao.getById(record_id);
	}
	
	/**
	 * �û�������ֵ����
	 * @param code            ����
	 * @param channel         ��ֵ����
	 */
	public int account( UAccountRecordVO account_record )
	{
		if( account_record==null )
		{
			logger.info("�û���ֵʱ����������account_recordΪ��");
		}
		
		logger.info("��ҳɹ��ύ��ֵ���󣡳�ֵͨ����"+account_record.getChannel()+";u_pk:"+account_record.getUPk()+";p_pk:"+account_record.getPPk()+";��ֵ���Ϊ:"+account_record.getMoney()+";���ţ�"+account_record.getCode());
		
		UAccountRecordDao accRecordDao = new UAccountRecordDao();
		
		//���ӳ�ֵ��־
		return accRecordDao.insert(account_record);
	}
	/**
	 * �û���ֵ�ɹ�֪ͨ
	 * @param code            ����
	 * @param channel         ��ֵ����
	 * @return                ֪ͨ�ɹ�����true
	 */
	public boolean  accountSuccessNotify( UAccountRecordVO accountRecord )
	{
		if( accountRecord==null )
		{
			logger.info("��ҳ�ֵ��¼Ϊ��");
			return false;
		}
		if( accountRecord.getAccountState().indexOf("callback result") !=-1 )
		{
			logger.info("����Ѿ��õ�callback,���ٴ�������(u_pk��"+accountRecord.getUPk()+";p_pk:"+accountRecord.getPPk()+"),�ѳ�ֵ�ɹ�,��ֵ���Ϊ��"+accountRecord.getMoney());
			return false;
		}
		int money = accountRecord.getMoney();
		
		logger.info("��ҳ�ֵ�ɹ���u_pk:"+accountRecord.getUPk()+";��ֵ���Ϊ:"+money);
		
		MailInfoService mailInfoService = new MailInfoService();
		EconomyService economyService = new EconomyService();
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		
		//���³�ֵ��¼״̬
		updateState(accountRecord.getId(), "callback result:pay_success");
		//���������Ԫ��
		int yb_num = accountRecord.getMoney()*100;//1Ԫ���10��Ԫ��
		int jf_num = yb_num*GameConfig.getJifenNum();//1Ԫ���1������
		economyService.addYuanbao(accountRecord.getPPk(),accountRecord.getUPk(), yb_num,"chongzhi");
		economyService.addJifen(accountRecord.getUPk(),jf_num);//���ӻ��֣�ÿ�ɹ���ֵ1�����=1����
		/**��ֵ���ͳ��**/
		gsss.addPropNum(0, StatisticsType.YUANBAO, yb_num,StatisticsType.DEDAO, "chongzhi", accountRecord.getPPk());
		/**��ֵ����ͳ��**/
		gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",accountRecord.getPPk());//ͳ�Ƴ�ֵ�˴�
		//���ʼ�
		String title = "��ֵ�ɹ�";
		String content = "";
		String channel_display = getChannelDisplay(accountRecord.getChannel());
		String time_str = DateUtil.getCurrentTimeStr();
		content = "����"+time_str+"��ֵ"+money+"Ԫ"+channel_display+"��ֵ����ֵ�ɹ�����á�"+GameConfig.getYuanbaoName()+"����"+yb_num+"��";
		logger.info("�ʼ����⣺"+title);
		logger.info("�ʼ����ݣ�"+content);
		mailInfoService.sendMailBySystem(accountRecord.getPPk(), title, content);
		JmsUtil.chongzhi(accountRecord.getPPk(),accountRecord.getMoney(),accountRecord.getChannel());
		return true;
	}
	/**
     * �û���ֵʧ��֪ͨ
     * @param accountRecord            ����
     * @param error_code               �������
     */
	public void accountFailNotify( UAccountRecordVO accountRecord,String error_code )
	{
		if( accountRecord==null )
		{
			logger.info("��ҳ�ֵ��¼Ϊ��");
			return;
		}
		
		if( accountRecord.getAccountState().indexOf("callback result") !=-1 )
		{
			logger.info("��ֵʧ�ܣ�ԭ��"+error_code);
			logger.info("����Ѿ��õ�callback,���ٴ�������(u_pk��"+accountRecord.getUPk()+";p_pk:"+accountRecord.getPPk()+"),�ѳ�ֵ�ɹ�,��ֵ���Ϊ��"+accountRecord.getMoney());
			return;
		}
		logger.info("��ҳ�ֵʧ�ܣ�u_pk:"+accountRecord.getUPk());
		
		MailInfoService mailInfoService = new MailInfoService();
		//���³�ֵ��¼״̬
		updateState(accountRecord.getId(), "callback result:�������-"+error_code);
		
		//���ʼ�
		String title = "��ֵʧ��";
		String content = "";
		String channel_display = getChannelDisplay(accountRecord.getChannel());
		int money = accountRecord.getMoney();
		String time_str = DateUtil.getCurrentTimeStr();
		content = "����"+time_str+"��ֵ"+money+"Ԫ"+channel_display+"��ֵ����ֵʧ��";
		logger.info("�ʼ����⣺"+title);
		logger.info("�ʼ����ݣ�"+content);
		mailInfoService.sendMailBySystem(accountRecord.getPPk(), title, content);
	}
	
	/**
	 * �õ���ֵͨ������
	 */
	private String getChannelDisplay(String channel)
	{
		if( channel==null )
		{
			return "";
		}
		if(channel.equals("SZX"))
		{
			return "�ƶ�������";
		}
		else if(channel.equals("SNDACARD"))
		{
			return "ʢ��";
		}
		else if(channel.equals("jun"))
		{
			return "����";
		}
		else if(channel.equals("SZF_DIANXIN"))
		{
			return "����";
		}
		else if(channel.equals("SZF_LIANTONG"))
		{
			return "��ͨ";
		}
		else if(channel.equals("SZF_SZX"))
		{
			return "������";
		}
		else
		{
			return "";
		}
		
	}
	
	/**
	 * �����û���ֵ״̬
	 */
	public void updateState( int id,String state )
	{
		UAccountRecordDao accRecordDao = new UAccountRecordDao();
		accRecordDao.update(id, state);
	}
	
	
	/**
	 * �õ���ֵ�ɹ��Ľű�
	 * @return
	 */
	public String getSuccessHint()
	{
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("���Ѿ������Ϊ��Ϸ��ֵ����ز�������ֵ��ɺ���Ҫ�ȴ������Ӳ���ȷ���Ƿ��ֵ�ɹ����������ڼ�����Ϸ�����ǽ��ڼ����Ӻ���ȷ���ʼ����ý�ɫ�������У�<br/>");
		return resultWml.toString();
	}
	/**
	 * �õ�Tom��ֵ�ɹ��Ľű�
	 * @return
	 */
	public String getTomSuccessHint()
	{
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("��ֵ�ɹ�!<br/>�ͷ�����021-28901353<br/>");
		return resultWml.toString();
	}
	/**
	 * �õ���ֵʧ�ܵĽű�
	 * @return
	 */
	public String getFailHint(String errerCode)
	{
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("�Բ����������˴���Ŀ��Ż������룡<br/>");  
		resultWml.append("���������ԭ������ǣ�<br/>");  
		resultWml.append("1.ѡ���˴���ĳ�ֵ�����������ֵ���������ֵ������ƥ���<br/>");  
		resultWml.append("2.��С��������ʺŻ����룬����������һ��<br/>");  
		resultWml.append("3.ѡ�������ֵ����ƥ��ĳ�ֵ��������ѡ����Ӧ���ĳ�ֵ����<br/>");  
		return resultWml.toString();
	}
	/**
	 * �õ���ֵʧ�ܵĽű�
	 * @return
	 */
	public String getTomFailHint(String errerCode)
	{
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("�Բ��𣬳�ֵʧ�ܣ�<br/>");  
		resultWml.append("���������ԭ������ǣ�<br/>");  
		resultWml.append("1.�����ֻ����Ѳ�����γ�ֵ����ķ���<br/>�ͷ�����021-28901353<br/>");
		return resultWml.toString();
	}
	
	//��ֵͨ�ýӿ�
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
		// ��ֵ�ɹ� ��¼
		int money = getYuanbaoNum(gameid, pay_money);
		accountRecord.setMoney(money);
		accountRecord.setUPk(passport.getUPk());
		accountRecord.setChannel(GameConfig.getChannelId()+"");
		accountRecord.setAccountState("success");
		accountRecord.setCode(orderid);
		accRecordDao.insert(accountRecord);
		// ���������Ԫ��
		int yb_num = money;// 1Ԫ���100��Ԫ��
		int jf_num = yb_num * GameConfig.getJifenNum();// 1Ԫ���1������

		economyService.addYuanbao(accountRecord.getPPk(), accountRecord
				.getUPk(), yb_num, "chongzhi");
		economyService.addJifen(accountRecord.getUPk(), jf_num);// ���ӻ��֣�ÿ�ɹ���ֵ1�����=1����

		gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",
				accountRecord.getPPk());// ͳ�Ƴ�ֵ�˴�
		// ���ʼ�
		String title = "��ֵ�ɹ�";
		String content = "";
		String time_str = DateUtil.getCurrentTimeStr();
		content = "����" + time_str + "��ֵ" + accountRecord.getMoney()
				+ "Ԫ��ֵ�ɹ�����á�"+GameConfig.getYuanbaoName()+"����" + yb_num + "��";
		logger.info("�ʼ����⣺" + title);
		logger.info("�ʼ����ݣ�" + content);
		mailInfoService.sendMailBySystem(accountRecord.getPPk(), title,
				content);

		gsss.addPropNum(0, StatisticsType.RMB, accountRecord.getMoney(),
				StatisticsType.DEDAO, Channel.JUU + "", accountRecord
						.getPPk());// ͳ��RMB
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
