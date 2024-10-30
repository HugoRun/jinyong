package com.ls.web.action.cooperate.youle.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ls.pub.util.http.HttpRequester;
import com.ls.pub.util.http.HttpRespons;

public class LoginService
{

	/**
	 * ����������Ҫ��½�����ߵ�ʱ�� ͬ����������ҵ�״̬������ʱ�� ���ѵ�ʱ��ͬ����������������
	 */

	/** ****��½��ʱ�������ͬ��������**** */
	public void synchronousLoginState(String UserAccount, String OnlineStatus,
			String GameNO)
	{
		HttpRequester requester = new HttpRequester();
		HttpRespons responses = null;
		try
		{
			responses = requester
					.sendGet("http://out.channel.u6.cn/cp/ICPUserNowOnline.aspx?UserAccount="
							+UserAccount
							+"&OnlineStatus="
							+OnlineStatus
							+"&GameNO="+GameNO+"");
			String code = responses.getContent();
			System.out.println("��½״̬ͬ��������Ϊ��" + code);
		}
		catch (IOException e)
		{
			System.out.println("��½״̬ͬ���쳣........");
		}
	}

	/** ******���ߵ�ʱ�������ͬ��������***** */
	public void synchronousDownLine(HttpSession session, String onlineNo)
	{
		HttpRequester requester = new HttpRequester();
		HttpRespons responses = null;
		String ChannelID = (String) session.getAttribute("ChannelID");
		String MachineID = (String) session.getAttribute("MachineID");
		String ModelID = (String) session.getAttribute("ModelID");
		String PartnerID = (String) session.getAttribute("PartnerID");
		String UserAccount = (String) session.getAttribute("UserAccount");
		String GameNO = (String) session.getAttribute("GameNO");
		try
		{
			responses = requester
					.sendGet("http://out.channel.u6.cn/cp/ICPUserOnlineTime.aspx?ChannelID="
							+ChannelID
							+"&MachineID="
							+MachineID
							+"&ModelID="
							+ModelID
							+"&PartnerID="
							+PartnerID
							+"&UserAccount="
							+UserAccount
							+"&GameNO="
							+GameNO
							+"&OnlineLength="
							+onlineNo
							+"&OfflineTime=0");
			String code = responses.getContent();
			System.out.println("�������ͬ��������Ϊ��" + code);
		}
		catch (IOException e)
		{
			System.out.println("�������ͬ���쳣......");
		}

	}

	/** *****������ѵ�ʱ��ͬ��������������***** */
	public void synchronousConsume(HttpServletRequest request,
			String consumerCode, int consumerAmount, int ConsumerType)
	{
		HttpRequester requester = new HttpRequester();
		HttpRespons responses = null;
		HttpSession session = request.getSession();
		String ChannelID = (String) session.getAttribute("ChannelID");
		String MachineID = (String) session.getAttribute("MachineID");
		String ModelID = (String) session.getAttribute("ModelID");
		String PartnerID = (String) session.getAttribute("PartnerID");
		String UserAccount = (String) session.getAttribute("UserAccount");
		String GameNO = (String) session.getAttribute("GameNO");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String ConsumerTimes = sdf.format(new Date().getTime());
		String ConsumerTime="";
		try
		{
			ConsumerTime=URLEncoder.encode(ConsumerTimes,"utf8");
			responses = requester
					.sendGet("http://out.channel.u6.cn/cp/ICPConsume.aspx?ChannelID="
							+ChannelID
							+"&MachineID="
							+MachineID
							+"&ModelID="
							+ModelID
							+"&PartnerID="
							+PartnerID
							+"&UserAccount="
							+UserAccount
							+"&GameNO="
							+GameNO
							+"&ConsumerCode="
							+consumerCode
							+"&ConsumerAmount="
							+consumerAmount
							+"&ConsumerType="
							+ConsumerType
							+"&ConsumerTime="+ConsumerTime+"");
			String code = responses.getContent();
			System.out.println("�������ͬ��������Ϊ��" + code);
		}
		catch (IOException e)
		{
			System.out.println("����ͬ���쳣......");
		}

	}

	/*********��ҳ�ֵͬ����Ϣ******* */
	public void synchronousRecharge(HttpServletRequest request,
			String RechargeCode, String RechargeAmount,
			String RechargeVirtuaAmount, String RechargeType,
			String RechargeStatus)
	{
		HttpRequester requester = new HttpRequester();
		HttpRespons responses = null;
		HttpSession session = request.getSession();
		String ChannelID = (String) session.getAttribute("ChannelID");
		String MachineID = (String) session.getAttribute("MachineID");
		String PartnerID = (String) session.getAttribute("PartnerID");
		String UserAccount = (String) session.getAttribute("UserAccount");
		String GameNO = (String) session.getAttribute("GameNO");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String RechargeTimes = sdf.format(new Date().getTime());
		String RechargeTime="";
		try
		{
			RechargeTime=URLEncoder.encode(RechargeTimes,"utf8");
			responses = requester
					.sendGet("http://out.channel.u6.cn/cp/ICPRecharge.aspx?ChannelID="
							+ChannelID
							+"&MachineID="
							+MachineID
							+"&PartnerID="
							+PartnerID
							+"&UserAccount="
							+UserAccount
							+"&GameNO="
							+GameNO
							+"&RechargeCode="
							+RechargeCode
							+"&RechargeAmount="
							+RechargeAmount
							+"&RechargeVirtuaAmount="
							+RechargeVirtuaAmount
							+"&RechargeType="
							+RechargeType
							+"&RechargeStatus="
							+RechargeStatus
							+"&RechargeTime="+RechargeTime+"");
			String code = responses.getContent();
			System.out.println("��ҳ�ֵͬ��������Ϊ��" + code);
		}
		catch (IOException e)
		{
			System.out.println("��ҳ�ֵͬ���쳣......");
		}

	}

	/** *****������ͬ����ҵȼ���Ϣ******* */
	public void synchronousGradeInfo(HttpSession session, String GameLeval)
	{
		HttpRequester requester = new HttpRequester();
		HttpRespons responses = null;
		String PartnerID = (String) session.getAttribute("PartnerID");
		String UserAccount = (String) session.getAttribute("UserAccount");
		String GameNO = (String) session.getAttribute("GameNO");
		try
		{
			responses = requester
					.sendGet("http://out.channel.u6.cn/cp/ICPUserGameRole.aspx?PartnerID="
							+PartnerID
							+"&UserAccount="
							+UserAccount
							+"&GameNO="
							+GameNO
							+"&GameLeval="
							+GameLeval+"");
			String code = responses.getContent();
			System.out.println("��ҵȼ�ͬ��������Ϊ��" + code);
			
		}
		catch (IOException e)
		{
			System.out.println("ͬ����ҵȼ��쳣.....");
		}
	}
}
