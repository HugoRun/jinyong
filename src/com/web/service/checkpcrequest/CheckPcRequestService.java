/**
 * 
 */
package com.web.service.checkpcrequest;

import java.util.List;

import org.apache.log4j.Logger;

import com.ben.dao.checkpcrequest.CheckPcRequestDAO;
import com.ben.dao.logininfo.LoginInfoDAO;
import com.ben.vo.checkpcrequest.CheckPcRequestVO;
import com.ls.ben.dao.system.ExceptionUserLogDao;
import com.ls.pub.config.GameConfig;

/**
 * @author ��ƾ� ����PC�����û�����
 */
public class CheckPcRequestService
{
	Logger logger = Logger.getLogger("log.service");

	public String isLoginException(String name, String u_pk, String ip, String userAgent)
	{
		String hint = null;
		// �����ж�ID��������ֹ������û�йر�
		if (GameConfig.isForbidPcWhiteListSwitch())// û�йر�
		{
			if (!isLoginInfoName(name))// �����ж����ID�Ƿ��ڰ������� ����TURE��ʾ��ID��������
			{
				return "�����ǰ�������������ȷ������½!";
			}
		}
		// �����쳣 �������3�ε�
		if (GameConfig.isDealExceptionUserSwitch())
		{
			ExceptionUserLogDao exceptionUserLogDao = new ExceptionUserLogDao();
			if (exceptionUserLogDao.isHave(u_pk + ""))
			{// �м�¼���Ҳ��ٰ�����ID��
				hint = "���ʺ������쳣��1���Ӻ�����µ�½��";
				return hint;
			}
		}
		// �������ж�IP�Ƿ���IP�������� �����жϿ����Ƿ��
		if (GameConfig.isForbidPcBlackListSwitch()){// û�йر�
			CheckPcRequestDAO checkPcRequestDAO = new CheckPcRequestDAO();
			if (checkPcRequestDAO.isCheckPcBlackList(ip)){// IP ������ ����TRUE�ں�������
				hint = "�Բ�������IP��ַ�����ƶ��ֻ�IP(" + ip + ")�����ܾ����ʣ���ʹ���ֻ�����Ϸ��";
				return hint;
			}
		}
		// �ж�UA�Ƿ�����
		if (GameConfig.isForbidPcUASwitch()){
			if(CheckPcRequest(userAgent) ==  false){
				hint = "�Բ��������ʵ�������������ֻ�����������ܾ����ʣ���ʹ���ֻ�����Ϸ��";
				return hint;
			}	
		}
		return null;
	}

	/**
	 * �ж��Ƿ����ֻ��û���½���ǵ����û���½
	 * 
	 * @return
	 */
	public boolean CheckPcRequest(String userAgent)
	{
		if (userAgent == null || "".equals(userAgent))
		{
			return true;
		}
		String info = userAgent.toUpperCase();
		
		if( info.indexOf("NT") > -1)//��windows�û�
		{
			if (info.indexOf("MSIE") > -1)// ����IE
			{
				logger.info("����IE");
				return false;
			}
			else
			if (info.indexOf("FIREFOX") > -1)// ����Firefox
			{
				logger.info("����Firefox ");
				return false;
			}
			else
			if (info.indexOf("OPERA") > -1 && info.indexOf("NT") > -1)// ����Opera
			{
				logger.info("����Opera");
				return false;
			}
			else
			if (info.indexOf("SAFARI") > -1)// ����Safari
			{
				logger.info("����Safari");
				return false;
			}
			else
			if (info.indexOf("CHROME") > -1)// ����Chrome
			{
				logger.info("����Chrome");
				return false;
			}
			else
			if (info.indexOf("NAVIGATOR") > -1)// ����Navigator
			{
				logger.info("����Navigator");
				return false;
			}
			else
			if (info.indexOf("GOOGLEBOT") > -1)// ����Googlebot
			{
				logger.info("����Googlebot");
				return false;
			}
			else
			if (info.indexOf("MSNBOT") > -1)// ����MSNBOT
			{
				logger.info("����MSNBOT");
				return false;
		    }
			else
			if (info.indexOf("YAHOO") > -1)// ����YAHOO
			{
				logger.info("����YAHOO");
				return false;
			}
		}
		return true;
	}

	/**
	 * ��ѯ�����ʱ������غ�����IP
	 * 
	 * @param ip
	 * @return
	 */
	public boolean contains(String ip)
	{
		CheckPcRequestDAO checkPcRequestDAO = new CheckPcRequestDAO();

		boolean blackList = checkPcRequestDAO.isCheckPcBlackList(ip);// ������
		boolean whiteList = isCheckPcWhiteList(ip);// ������
		// �ں�����
		if (blackList)
		{
			logger.info("�ں�����");
			return false;
		}
		// û���ں�����Ҳû�ڰ�����
		if (blackList == false && whiteList == false)
		{
			logger.info("û���ں�����Ҳû�ڰ�����");
			return true;
		}
		// û���ں�����ȴ�ڰ�����
		if (blackList == false && whiteList == true)
		{
			logger.info("û���ں�����ȴ�ڰ�����");
			return true;
		}
		return false;
	}

	/**
	 * �Ƿ��ڰ��������� FALSE ˵��û���ڰ�����
	 * 
	 * @param ip
	 * @return
	 */
	public boolean isCheckPcWhiteList(String ip)
	{
		CheckPcRequestDAO checkPcRequestDAO = new CheckPcRequestDAO();
		List list = checkPcRequestDAO.isCheckPcWhiteList();// ��ѯ������
		if (list != null && list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				CheckPcRequestVO vo = (CheckPcRequestVO) list.get(i);
				if (betweenIP(vo.getIpBegin(), vo.getIpEnd(), ip) == false)
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * �ж��Ƿ��ڰ�����
	 * 
	 * @param name
	 * @return
	 */
	public boolean isLoginInfoName(String name)
	{
		LoginInfoDAO loginDao = new LoginInfoDAO();
		return loginDao.isLoginInfoName(name);
	}

	/**
	 * �ж�Ip��
	 * 
	 * @param start
	 * @param end
	 * @param current
	 * @return
	 */
	public boolean betweenIP(String start, String end, String current)
	{
		boolean result = false;
		start = start.replaceAll("(^|\\.)(\\d)(\\.|$)", "$100$2$3");
		start = start.replaceAll("(^|\\.)(\\d)(\\.|$)", "$100$2$3");
		start = start.replaceAll("(^|\\.)(\\d{2})(\\.|$)", "$10$2$3");
		start = start.replaceAll("(^|\\.)(\\d{2})(\\.|$)", "$10$2$3");

		end = end.replaceAll("(^|\\.)(\\d)(\\.|$)", "$100$2$3");
		end = end.replaceAll("(^|\\.)(\\d)(\\.|$)", "$100$2$3");
		end = end.replaceAll("(^|\\.)(\\d{2})(\\.|$)", "$10$2$3");
		end = end.replaceAll("(^|\\.)(\\d{2})(\\.|$)", "$10$2$3");

		current = current.replaceAll("(^|\\.)(\\d)(\\.|$)", "$100$2$3");
		current = current.replaceAll("(^|\\.)(\\d)(\\.|$)", "$100$2$3");
		current = current.replaceAll("(^|\\.)(\\d{2})(\\.|$)", "$10$2$3");
		current = current.replaceAll("(^|\\.)(\\d{2})(\\.|$)", "$10$2$3");

		if ((current.compareTo(start) >= 0) && (current.compareTo(end) <= 0))
		{
			result = true;
		}
		return result;
	}
}
