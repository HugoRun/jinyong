package com.ls.web.action.cooperate.tele.login;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.util.http.HttpRequester;
import com.ls.pub.util.http.HttpRespons;
import com.ls.web.service.cooperate.dangle.PassportService;

public class LoginAction extends Action
{
	Logger logger = Logger.getLogger("log.service");
	/**
	 * 电信渠道登陆
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String key=request.getParameter("Md5code");
		String custId=request.getParameter("custID");
		String loginTime=request.getParameter("loginTime");
		String backUrl=request.getParameter("backUrl");
		String backNetUrl=request.getParameter("backNetUrl");
		String cpId="C09025";
		String cpProductId="200161253000";
		String channelId="MC099108";
		String packageID=request.getParameter("packageID");
		String netElementId="888999";
		String login_ip=request.getLocalAddr();
		HttpSession session = request.getSession();
		session.setAttribute("teleid",custId);
		session.setAttribute("channel_id", channelId);
		session.setAttribute("cpId",cpId);
		session.setAttribute("cpProductId", cpProductId);
		session.setAttribute("netElementId", netElementId);
		session.setAttribute("versionId", "1_1_2");
		System.out.println("cpId******************************************"+cpId);
		System.out.println("**********************************************"+key);
		/******没有key参数说明是第一次请求******/
		if(key==null || "".equals(key))
		{
			try
			{
				logger.debug("开始发送请求。。。。。。。。");
				response.sendRedirect("http://202.102.39.11:9088/gameinterface/LoginWapGame?netElementId=888999&cpId="+cpId+"&cpProductId="+cpProductId+"&channelId="+channelId+"&packageID="+packageID+"");
				logger.debug("发送请求结束。。。。。。。。");
			}
			catch (IOException e)
			{
				logger.debug("电信首次请求异常...");
			}
		}
		/******判断如果key和同步过来的key一样则登陆成功*****/
		else
		{
			String md5code=(String)request.getSession().getAttribute("md5code");
			PassportService ps=new PassportService();
			PassportVO pv= ps.loginFromTele(custId, login_ip);
			/**登陆验证**/
			logger.debug("md5code********************************"+md5code);
			if(key.equals(md5code))
			{
				if( pv ==null || pv.getUPk()==-1 )//登陆验证失败
				{
					logger.info("用户验证失败");
					return mapping.findForward("fail");
				} 
			}
			else
			{
				logger.debug("用户验证成功");
				System.out.println("************************************************************"+pv.getUPk());
				int uPk = pv.getUPk();//登陆验证成功
				session.setAttribute("uPk", uPk+"");
				session.setAttribute("user_name",pv.getUserName() );
				session.setAttribute("backUrl",backUrl);
				session.setAttribute("backNetUrl",backNetUrl);
				
				/************获得平台的精彩推荐内容*************/
				Map<String,String> recomm=getRecomm(netElementId,cpId,cpProductId,channelId,packageID);
				session.setAttribute("recomm",recomm);
				return mapping.findForward("success");
			}
		}
		return null;
	}
	
	/**
	 * 获得电信平台提供的精彩推荐内容
	 * @param netElementId
	 * @param cpId
	 * @param cpProductId
	 * @param channelId
	 * @param packageID
	 * @return
	 */
	private Map<String, String> getRecomm(String netElementId,String cpId,String cpProductId,String channelId,String packageID)
	{
		HttpRequester requester = new HttpRequester();
		Map<String, String> recomm=null;
		String url="http://202.102.39.11:9088/gameinterface/GetRecommList?netElementId="+netElementId+"&cpId="+cpId+"&cpProductId="+cpProductId+"&channelId="+channelId+"&packageID="+packageID+"";
		try
		{
			HttpRespons response= requester.sendGet(url);
			Document document = DocumentHelper.parseText(response.getContent());
			Element root = document.getRootElement();
			List<Element> list=root.elements();
			if(list!=null&&list.size()!=0)
			{
				recomm=new HashMap<String, String>();
				for (int i = 0; i <list.size(); i++)
				{
					Element element=list.get(i);
					Element resultElm = element.element("caption");
					Element resultElm1 = element.element("url");
					String caption=resultElm.getText();
					logger.info("精彩推荐业务名称************"+caption);
					String captionUrl=resultElm1.getText();
					logger.info("精彩推荐的URL*******************"+captionUrl);
					recomm.put(caption, captionUrl);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("....................发送精彩推荐请求失败!");
		}
		return recomm;
	}
}
