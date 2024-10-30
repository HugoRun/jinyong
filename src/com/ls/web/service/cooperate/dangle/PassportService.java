package com.ls.web.service.cooperate.dangle;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ben.jms.JmsUtil;
import com.ben.vo.logininfo.LoginInfoVO;
import com.ls.ben.dao.cooparate.dangle.PassportDao;
import com.ls.ben.dao.register.RegisterDao;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.config.GameConfig;
import com.ls.pub.config.sky.ConfigOfSky;
import com.ls.pub.constant.Channel;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.pub.util.http.HttpRequester;
import com.ls.pub.util.http.HttpRespons;
import com.ls.pub.util.http.parseContent.ParseNormalContent;
import com.ls.web.service.security.SecurityService;

/**
 * @author ls ����:�����û�ͨ��֤���� Jan 10, 2009
 */
public class PassportService
{
	Logger logger = Logger.getLogger(PassportService.class);

	/**
	 * ��½url
	 */
	// static String LOGIN_URL = "http://jy1.downjoy.com/dl/login.do";
	// static String LOGIN_URL = "http://61.168.44.14:28081/gp/dl/login.do";
	/**
	 * ���ַ����md5��Կ
	 */
	static String MERCHANT_KEY = "xWx86231";
	
	/** ����������½�����ĵ�һ������* */
	public PassportVO loginFromYoule(String youle_uid, String login_ip)
	{
		PassportVO passport = null;
		int u_pk = -1;
		passport = getPassportInfoByUserID(youle_uid, Channel.WANXIANG);
		if (passport == null)// ������û�
		{
			u_pk = addNewUser(youle_uid, youle_uid,Channel.WANXIANG, login_ip);
			passport = new PassportVO();
			passport.setUPk(u_pk);
		}
		else
		{
			u_pk = passport.getUPk();
			RegisterDao dao = new RegisterDao();
			dao.updateIp(u_pk, login_ip);
		}
		passport.setUserId(youle_uid);
		return passport;
	}


	/**
	 * �û��ӵ��ֵ�½����Ϸƽ̨
	 * 
	 * @param user_id
	 * @param user_name
	 * @param received_verify_string
	 * @param timestamp
	 * @param encrypt_type
	 * @return u_pk
	 */
	public int loginFromDangLe(String user_id, String user_name,
			String received_verify_string, String timestamp,
			String encrypt_type, String ip)
	{
		String login_url = GameConfig.getUrlOfLoginServer() + "/dl/login.do";

		String verify_string = login_url + "?userid=" + user_id + "&username="
				+ user_name + "&merchant-key=" + MERCHANT_KEY
				+ "&encrypt-type=" + encrypt_type + "&timestamp=" + timestamp;

		logger.info("verify_string:" + verify_string);

		verify_string = MD5Util.md5Hex(verify_string);

		RegisterDao dao = new RegisterDao();

		int u_pk = -1;

		if (verify_string.equals(received_verify_string)) // ��֤�ɹ�
		{
			logger.info("##############�����û���֤�ɹ�##################");
			PassportVO passport = getPassportInfoByUserID(user_id,
					Channel.DANGLE);
			if (passport == null)// ������û�
			{
				u_pk = addNewUser(user_id, user_name, Channel.DANGLE, ip);
			}
			else
			{
				u_pk = passport.getUPk();
				dao.updateIp(u_pk, ip);
			}
		}
		else
		{
			logger.info("##############�����û���֤ʧ��##################");
		}

		return u_pk;
	}

	/**
	 * ��������½
	 */
	public PassportVO loginFromTiao(String user_name,
			String received_verify_string, String login_ip)
	{
		PassportVO passport = null;

		if (SecurityService.validateName(user_name, received_verify_string))
		{
			int u_pk = -1;
			passport = getPassportInfoByUserID(user_name, Channel.TIAO);
			if (passport == null)// ������û�
			{
				u_pk = addNewUser(user_name, user_name, Channel.TIAO, login_ip);
				passport = new PassportVO();
				passport.setUPk(u_pk);
			}
			else
			{
				u_pk = passport.getUPk();
				RegisterDao dao = new RegisterDao();
				dao.updateIp(u_pk, login_ip);
			}
			passport.setUserId(user_name);
		}

		return passport;
	}

	/**
	 * ��֤��Ҵ�sky������½�����ĺϷ���
	 * 
	 * @param channel_params
	 *            �����������Ĳ���
	 * @return u_pk
	 */
	public PassportVO loginFromSky(String ssid, String login_ip)
	{
		PassportVO passport = null;
		/**
		 * ��½��֤url
		 */
		String validate_url = ConfigOfSky.getUrlOfAuthSSID();

		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("utf-8");

		HttpRespons response = null;
		try
		{
			response = request.sendGet(validate_url + "?ssid=" + ssid);// �ύ��֤����
		}
		catch (IOException e)
		{
			logger.debug("˼��ssid��֤�쳣");
			e.printStackTrace();
			return passport;
		}

		if (response.getCode() != 200)// ��ssid��֤����ʧ��
		{
			logger.debug("˼��ssid��֤����ʧ�ܣ���Ӧ����Ϊ��" + response.getCode());
			return passport;
		}

		ParseNormalContent parseContent = new ParseNormalContent();
		Map<String, String> result = parseContent.parse(response.getContent());// �õ����������Ӧ���

		if (result.get("result").equals("0"))// ��ʾssid��֤�ɹ�
		{
			String skyid = result.get("skyid");
			String nickname = result.get("nickname");

			logger.info("##############˼���û���֤�ɹ�##################");
			int u_pk = -1;
			passport = getPassportInfoByUserID(skyid, Channel.SKY);
			if (passport == null)// ������û�
			{
				u_pk = addNewUser(skyid, nickname, Channel.SKY, login_ip);
				passport = new PassportVO();
				passport.setUPk(u_pk);
			}
			else
			{
				u_pk = passport.getUPk();
				RegisterDao dao = new RegisterDao();
				dao.updateIp(u_pk, login_ip);
			}
			passport.setUserId(skyid);
		}
		else
		{
			logger.debug("˼��ssid��֤ʧ�ܣ�ssid��" + ssid + "��Ч");
			return passport;
		}

		return passport;
	}

	/** ���˵�½����* */
	public PassportVO loginFromSina(String sina_uid, String login_ip,String wm)
	{
		PassportVO passport = null;
		int u_pk = -1;
		passport = getPassportInfoByUserID(sina_uid, Channel.SINA);
		if (passport == null)// ������û�
		{
			u_pk = addNewUser(sina_uid, sina_uid, "",Channel.SINA, login_ip,wm,wm);
			passport = new PassportVO();
			passport.setUPk(u_pk);
		}
		else
		{
			u_pk = passport.getUPk();
			RegisterDao dao = new RegisterDao();
			dao.updateIp(u_pk, login_ip);
		}
		passport.setUserId(sina_uid);
		return passport;
	}

	/** ����������½����* */
	public PassportVO loginFromTele(String tele_uid, String login_ip)
	{
		PassportVO passport = null;
		int u_pk = -1;
		passport = getPassportInfoByUserID(tele_uid, Channel.TELE);
		if (passport == null)// ������û�
		{
			u_pk = addNewUser(tele_uid, tele_uid,Channel.TELE, login_ip);
			passport = new PassportVO();
			passport.setUPk(u_pk);
		}
		else
		{
			u_pk = passport.getUPk();
			RegisterDao dao = new RegisterDao();
			dao.updateIp(u_pk, login_ip);
		}
		passport.setUserId(tele_uid);
		return passport;
	}
	/** JUU��½����* */
	public PassportVO loginFromJuu(String account, String time, String sign,
			String login_ip)
	{
		PassportVO passport = null;
		int u_pk = -1;

		if (account == null || time == null || sign == null)
		{
		}
		else
		{
			String key = "3IOJ3934KJ3493KJ94K";
			String sign_bak = MD5Util.md5Hex(account + time + key);
			if (sign.equals(sign_bak))
			{
				passport = getPassportInfoByUserID(account, Channel.JUU);
				if (passport == null)// ������û�
				{
					u_pk = addNewUser(account, account, Channel.JUU, login_ip);
					passport = new PassportVO();
					passport.setUPk(u_pk);
				}
				else
				{
					u_pk = passport.getUPk();
					RegisterDao dao = new RegisterDao();
					dao.updateIp(u_pk, login_ip);
				}
				passport.setUserId(account);
			}
		}
		return passport;
	}
	
	/**�������½����**/
	public PassportVO loginFromDjw(String user_id, String timestamp, String receive_verify_string,
			String login_ip)
	{
		PassportVO passport = null;
		int u_pk = -1;

		if (user_id == null || timestamp == null || receive_verify_string == null)
		{
			logger.info("error:null");
		}
		else
		{
			String key = "eielwek9nea2oll1";// ������������Կ
			String merchant_id = "oneshow";// �̻����
			String game_id = "1";// ��Ϸ�ɣġ�Ĭ��Ϊ��
			String sign = "merchant.enter?"+"user-id="+user_id+
					"&timestamp="+timestamp+"&merchant-id="+merchant_id+"&merchant-key="+key;
			String verify_string = MD5Util.md5Hex(sign);
			if (verify_string.equals(receive_verify_string))
			{
				logger.info("##############������û���֤�ɹ�##################");
				passport = getPassportInfoByUserID(user_id, 99);
				if (passport == null)// ������û�
				{
					u_pk = addNewUser(user_id, user_id,"",99, login_ip,"1","djw");
					passport = new PassportVO();
					passport.setUPk(u_pk);
				}
				else
				{
					u_pk = passport.getUPk();
					RegisterDao dao = new RegisterDao();
					dao.updateIp(u_pk, login_ip);
				}
				passport.setUserId(user_id);
			}
			else
			{
				logger.info("##############������û���֤ʧ��##################");
			}			
		}
		return passport;
	}
	
	
	/** ��������½����* */
	public PassportVO loginFromAir(String userId, String login_ip)
	{
		PassportVO passport = null;
		int u_pk = -1;
		passport = getPassportInfoByUserID(userId, Channel.AIR);
		if (passport == null)// ������û�
		{
			u_pk = addNewUser(userId, userId, Channel.AIR, login_ip);
			passport = new PassportVO();
			passport.setUPk(u_pk);
		}
		else
		{
			u_pk = passport.getUPk();
			RegisterDao dao = new RegisterDao();
			dao.updateIp(u_pk, login_ip);
		}
		passport.setUserId(userId);
		return passport;
	}
	
	/** ��������½����* */
	public PassportVO loginFromTxw(String userId, String login_ip)
	{
		PassportVO passport = null;
		int u_pk = -1;
		passport = getPassportInfoByUserID(userId, Channel.TXW);
		if (passport == null)// ������û�
		{
			u_pk = addNewUser(userId, userId, Channel.TXW, login_ip);
			passport = new PassportVO();
			passport.setUPk(u_pk);
		}
		else
		{
			u_pk = passport.getUPk();
			RegisterDao dao = new RegisterDao();
			dao.updateIp(u_pk, login_ip);
		}
		passport.setUserId(userId);
		return passport;
	}
	
	/** ����ɵ�½����* */
	public PassportVO loginFromYouvb(String youvb_id, String login_ip)
	{
		PassportVO passport = null;
		int u_pk = -1;
		passport = getPassportInfoByUserID(youvb_id, Channel.YOUVB);
		if (passport == null)// ������û�
		{
			u_pk = addNewUser(youvb_id, youvb_id, Channel.YOUVB, login_ip);
			passport = new PassportVO();
			passport.setUPk(u_pk);
		}
		else
		{
			u_pk = passport.getUPk();
			RegisterDao dao = new RegisterDao();
			dao.updateIp(u_pk, login_ip);
		}
		passport.setUserId(youvb_id);
		return passport;
	}

	/**
	 * ���������˺�
	 * 
	 * @param user_name
	 * @param user_pwd
	 * @param login_ip
	 * @return
	 */
	public int loginFromGame(String user_name, String user_pwd,
			String login_ip, String qudao, String super_qudao)
	{
		int u_pk = -1;
		PassportVO passport = getPassportInfoByUserID(user_name,
				Channel.WANXIANG);
		if (passport == null)
		{
			u_pk = addNewUser(user_name, user_name, user_pwd, Channel.WANXIANG,
					login_ip, super_qudao, qudao);
			// ����ע���ɫ
			if (u_pk != -1)
			{
				JmsUtil.sendJmsRole(super_qudao, qudao, user_name, null, 1);
			}
		}
		else
		{
			RegisterDao dao = new RegisterDao();
			u_pk = passport.getUPk();
			dao.updateIp(u_pk, login_ip);
		}
		return u_pk;
	}

	public LoginInfoVO findByUpk(int uPk)
	{
		return new RegisterDao().findByUpk(uPk);
	}

	public void updateQudao(int uPk, String super_qudao, String qudao)
	{
		new RegisterDao().updateQudao(uPk, super_qudao, qudao);
	}

	/**
	 * ƽ̨��֤��½
	 * 
	 * @param channel
	 * @param product
	 * @param timestamp
	 * @param loginType
	 * @param name
	 * @param received_verify_string
	 * @param key
	 * @param ip
	 * @return
	 */
	public int loginFromPingTai(String channel, String product,
			String timestamp, String loginType, String name,
			String received_verify_string, String key, String ip)
	{
		String verify_string = "channel=" + channel + "&username=" + name
				+ "&key=" + key + "&timestamp=" + timestamp + "&product="
				+ product;

		logger.info("verify_string:" + verify_string);
		verify_string = MD5Util.md5Hex(verify_string);

		RegisterDao dao = new RegisterDao();

		int u_pk = -1;
		if (verify_string.equals(received_verify_string)) // ��֤�ɹ�
		{
			logger.info("##############ƽ̨��֤��½��֤�ɹ�##################");
			PassportVO passport = getPassportInfoByUserName(name, Integer
					.parseInt(channel));
			if (passport == null)// ������û�
			{
				u_pk = addNewUser(null, name, Integer.parseInt(channel), ip);
			}
			else
			{
				u_pk = passport.getUPk();
				dao.updateIp(u_pk, ip);
			}
		}
		else
		{
			logger.info("##############�����û���֤ʧ��##################");
		}

		return u_pk;
	}

	/**
	 * �û��ӽ�������½����Ϸƽ̨
	 * 
	 * @param user_id
	 * @param user_name
	 * @return
	 */
	public int loginFromJY(String user_id, String user_name, String ip)
	{
		RegisterDao dao = new RegisterDao();

		int u_pk = -1;

		if (user_id == null || user_name == null)
		{
			logger.info("��Ч������֤ʧ��");
			return u_pk;
		}

		PassportVO passport = getPassportInfoByUserID(user_id,
				Channel.JINYINDAO);
		if (passport == null)// ������û�
		{
			u_pk = addNewUser(user_id, user_name, Channel.JINYINDAO, ip);
		}
		else
		{
			u_pk = passport.getUPk();
			dao.updateIp(u_pk, ip);
		}
		return u_pk;
	}

	/**
	 * �û���TOMƽ̨������Ϸ
	 * 
	 * @param lid
	 *            TOMƽ̨������ʾ
	 * @param name
	 *            ��½�û���
	 * @param paw
	 *            ��½����
	 * @param ip
	 *            ��½IP
	 * @return
	 */
	public int loginFromTOM(String lid, String name, String pwass, String ip)
	{
		RegisterDao dao = new RegisterDao();

		int u_pk = -1;

		if (lid == null || name == null)
		{
			logger.info("��Ч������֤ʧ��");
			return u_pk;
		}

		PassportVO passport = getPassportInfoByUserName(name, Channel.TOM);
		if (passport == null)// ������û�
		{
			u_pk = addNewUser(lid, name, pwass, Channel.TOM, ip, null, null);
		}
		else
		{
			u_pk = passport.getUPk();
			dao.updateIp(u_pk, ip);
		}
		return u_pk;
	}

	/**
	 * ������û�
	 */
	public int addNewUser(String user_id, String user_name, int channel_id,
			String ip)
	{
		RegisterDao registerDao = new RegisterDao();
		PassportDao passportDao = new PassportDao();

		int u_pk = registerDao.addUser(user_name, ip);
		if (u_pk != -1)// ����ʧ��
		{
			passportDao.addNewPassport(user_id, user_name, u_pk, channel_id);
		}
		return u_pk;
	}

	/**
	 * ������û�
	 */
	public int addNewUser(String user_id, String user_name, String pwass,
			int channel_id, String ip, String super_qudao, String qudao)
	{
		RegisterDao registerDao = new RegisterDao();
		PassportDao passportDao = new PassportDao();
		pwass = MD5Util.md5Hex(pwass);
		int u_pk = registerDao
				.addUser(user_name, pwass, ip, super_qudao, qudao);
		if (u_pk != -1)
		{
			passportDao.addNewPassport(user_id, user_name, u_pk, channel_id);
		}
		return u_pk;
	}

	/**
	 * ͨ����Ϸu_pk���õ��û�ͨ��֤��Ϣ
	 */
	public PassportVO getPassportInfoByUPk(int u_pk)
	{
		PassportVO passport = null;

		PassportDao passportDao = new PassportDao();

		passport = passportDao.getPassportByUPk(u_pk);

		return passport;
	}

	/**
	 * ͨ����Ϸu_pk���õ��û�ͨ��֤��Ϣ
	 */
	public PassportVO getPassportInfoByUPk(String u_pk_str)
	{
		int u_pk = -1;
		try
		{
			u_pk = Integer.parseInt(u_pk_str);
		}
		catch (Exception e)
		{
			logger.info("u_pk:" + u_pk_str + "��Ϊ�Ƿ�����");
			return null;
		}

		return getPassportInfoByUPk(u_pk);
	}

	/**
	 * ͨ��ͨ��֤�û�id���õ��û�ͨ��֤��Ϣ
	 */
	public PassportVO getPassportInfoByUserID(String user_id, int channel_id)
	{
		PassportVO passport = null;

		PassportDao passportDao = new PassportDao();

		passport = passportDao.getPassportByUserID(user_id, channel_id);

		return passport;
	}

	/**
	 * ͨ��ͨ��֤�û�id���õ��û�ͨ��֤��Ϣ
	 */
	public PassportVO getPassportInfoByUserName(String user_name, int channel_id)
	{
		PassportVO passport = null;

		PassportDao passportDao = new PassportDao();

		passport = passportDao.getPassportByUserName(user_name, channel_id);

		return passport;
	}

	/**
	 * �û�����Ϸƽ̨ע������ͨ��֤�˺�
	 * 
	 * @param user_id
	 */
	public void logoutToDangLe(int u_pk)
	{
		PassportDao passportDao = new PassportDao();
		// �û�ע��
		passportDao.updateState(u_pk, 2);
	}

	/*******************��Ϸͨ�ýӿ�***********************/
	public int loginall(String account,String md5string,String timestamp,String gameid,String login_ip)
	{
		String key = getMD5str(account, md5string, timestamp, gameid);
		if(!md5string.equals(key)){
			return -1;
		}
		int u_pk = -1;
		PassportVO passport = null;
		passport = getPassportInfoByUserID(account, Integer.parseInt(gameid));
		if (passport == null)// ������û�
		{
			u_pk = addNewUser(account, account, Integer.parseInt(gameid), login_ip);
			passport = new PassportVO();
			passport.setUPk(u_pk);
		}
		else
		{
			u_pk = passport.getUPk();
			RegisterDao dao = new RegisterDao();
			dao.updateIp(u_pk, login_ip);
		}
		passport.setUserId(account);
		return u_pk;
	}
	
	/***************�õ��ַ���****
	 * �˴�������� �û�����ID �� ��������KEY
	 * *********/
	private String getMD5str(String account,String md5string,String timestamp,String gameid){
		String key = "";
		if(Integer.parseInt(gameid) == Channel.GGW){
			key = Channel.GGW12KEY;
		}
		if(Integer.parseInt(gameid) == Channel.HZRD){
			key = Channel.HZRD13KEY;
		}
		String str_bak = account+gameid+timestamp+key;
		String str_md5 = MD5Util.md5Hex(str_bak);
		return str_md5;
	}
}
