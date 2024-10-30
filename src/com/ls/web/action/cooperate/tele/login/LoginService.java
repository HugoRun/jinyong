package com.ls.web.action.cooperate.tele.login;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ls.pub.util.http.HttpRequester;
import com.ls.pub.util.http.HttpRespons;

public class LoginService
{
	Logger logger = Logger.getLogger("log.pay");
	/*******��������ͬ����Ϣ******/
	public void loginOut(HttpSession session)
	{
		if(session==null)
		{
			return;
		}
		String netElementId=(String)session.getAttribute("netElementId");
		String cpId=(String)session.getAttribute("cpId");
		String cpProductId=(String)session.getAttribute("cpProductId");
		String channelId=(String)session.getAttribute("channelId");
		String custId=(String)session.getAttribute("custId");
		String transId=cpId+getDateStr()+"827315";
		String versionId=(String)session.getAttribute("versionId");
		Map<String, String> params=new HashMap<String, String>();
		params.put("msgType", "LogoutOnlieGameReq");
		params.put("netElementId",netElementId);
		params.put("custId",custId);
		params.put("channelId", channelId);
		params.put("cpId", cpId);
		params.put("cpProductId", cpProductId);
		params.put("transID", transId);
		params.put("versionId", versionId);
		HttpRequester requester = new HttpRequester();
		HttpRespons responses = null;
		try
		{
			logger.info("��ʼ��������...");
			responses=requester.sendPostTele("http://202.102.39.11:9088/gameinterface/LogoutOnlineGame",params);
			logger.info("������������...");
			Document document = DocumentHelper.parseText(responses.getContent());
			Element root = document.getRootElement();
			Element resultElm = root.element("hRet");
			String hint=resultElm.getText();
			if("0".equals(hint))
			{
				System.out.println("..........��������ͬ���ɹ�...........");
			}
			else
			{
				System.out.println("..........��������ͬ��ʧ��...........");
			}
		}
		catch (Exception e)
		{
			System.out.println(".........�����������ͬ���쳣.......");
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
