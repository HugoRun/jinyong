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
 * @author 侯浩军 屏蔽PC电脑用户访问
 */
public class CheckPcRequestService
{
	Logger logger = Logger.getLogger("log.service");

	public String isLoginException(String name, String u_pk, String ip, String userAgent)
	{
		String hint = null;
		// 首先判定ID白名单防止开关有没有关闭
		if (GameConfig.isForbidPcWhiteListSwitch())// 没有关闭
		{
			if (!isLoginInfoName(name))// 首先判断玩家ID是否在白名单中 返回TURE表示在ID白名单中
			{
				return "您不是白名单玩家请从正确渠道登陆!";
			}
		}
		// 连接异常 连续点击3次的
		if (GameConfig.isDealExceptionUserSwitch())
		{
			ExceptionUserLogDao exceptionUserLogDao = new ExceptionUserLogDao();
			if (exceptionUserLogDao.isHave(u_pk + ""))
			{// 有记录并且不再白名单ID的
				hint = "该帐号连接异常，1分钟后可重新登陆。";
				return hint;
			}
		}
		// 接下来判定IP是否在IP黑名单中 首先判断开关是否打开
		if (GameConfig.isForbidPcBlackListSwitch()){// 没有关闭
			CheckPcRequestDAO checkPcRequestDAO = new CheckPcRequestDAO();
			if (checkPcRequestDAO.isCheckPcBlackList(ip)){// IP 黑名单 返回TRUE在黑名单中
				hint = "对不起，您的IP地址并非移动手机IP(" + ip + ")而被拒绝访问，请使用手机玩游戏！";
				return hint;
			}
		}
		// 判断UA是否正常
		if (GameConfig.isForbidPcUASwitch()){
			if(CheckPcRequest(userAgent) ==  false){
				hint = "对不起，您访问的浏览器非正常手机浏览器而被拒绝访问，请使用手机玩游戏！";
				return hint;
			}	
		}
		return null;
	}

	/**
	 * 判断是否是手机用户登陆还是电脑用户登陆
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
		
		if( info.indexOf("NT") > -1)//是windows用户
		{
			if (info.indexOf("MSIE") > -1)// 屏蔽IE
			{
				logger.info("屏蔽IE");
				return false;
			}
			else
			if (info.indexOf("FIREFOX") > -1)// 屏蔽Firefox
			{
				logger.info("屏蔽Firefox ");
				return false;
			}
			else
			if (info.indexOf("OPERA") > -1 && info.indexOf("NT") > -1)// 屏蔽Opera
			{
				logger.info("屏蔽Opera");
				return false;
			}
			else
			if (info.indexOf("SAFARI") > -1)// 屏蔽Safari
			{
				logger.info("屏蔽Safari");
				return false;
			}
			else
			if (info.indexOf("CHROME") > -1)// 屏蔽Chrome
			{
				logger.info("屏蔽Chrome");
				return false;
			}
			else
			if (info.indexOf("NAVIGATOR") > -1)// 屏蔽Navigator
			{
				logger.info("屏蔽Navigator");
				return false;
			}
			else
			if (info.indexOf("GOOGLEBOT") > -1)// 屏蔽Googlebot
			{
				logger.info("屏蔽Googlebot");
				return false;
			}
			else
			if (info.indexOf("MSNBOT") > -1)// 屏蔽MSNBOT
			{
				logger.info("屏蔽MSNBOT");
				return false;
		    }
			else
			if (info.indexOf("YAHOO") > -1)// 屏蔽YAHOO
			{
				logger.info("屏蔽YAHOO");
				return false;
			}
		}
		return true;
	}

	/**
	 * 查询苦里边时候有相关黑名单IP
	 * 
	 * @param ip
	 * @return
	 */
	public boolean contains(String ip)
	{
		CheckPcRequestDAO checkPcRequestDAO = new CheckPcRequestDAO();

		boolean blackList = checkPcRequestDAO.isCheckPcBlackList(ip);// 黑名单
		boolean whiteList = isCheckPcWhiteList(ip);// 白名单
		// 在黑名单
		if (blackList)
		{
			logger.info("在黑名单");
			return false;
		}
		// 没有在黑名单也没在白名单
		if (blackList == false && whiteList == false)
		{
			logger.info("没有在黑名单也没在白名单");
			return true;
		}
		// 没有在黑名单却在白名单
		if (blackList == false && whiteList == true)
		{
			logger.info("没有在黑名单却在白名单");
			return true;
		}
		return false;
	}

	/**
	 * 是否在白名单返回 FALSE 说明没有在白名单
	 * 
	 * @param ip
	 * @return
	 */
	public boolean isCheckPcWhiteList(String ip)
	{
		CheckPcRequestDAO checkPcRequestDAO = new CheckPcRequestDAO();
		List list = checkPcRequestDAO.isCheckPcWhiteList();// 查询白名单
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
	 * 判定是否在白名单
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
	 * 判断Ip段
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
