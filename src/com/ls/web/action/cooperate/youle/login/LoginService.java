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
	 * 悠乐渠道需要登陆和下线的时候 同步给他们玩家的状态和在线时间 消费的时候同步给他们消费数据
	 */

	/** ****登陆的时候给渠道同步的数据**** */
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
			System.out.println("登陆状态同步返回码为：" + code);
		}
		catch (IOException e)
		{
			System.out.println("登陆状态同步异常........");
		}
	}

	/** ******下线的时候给渠道同步的数据***** */
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
			System.out.println("玩家下线同步返回码为：" + code);
		}
		catch (IOException e)
		{
			System.out.println("玩家下线同步异常......");
		}

	}

	/** *****玩家消费的时候同步给渠道的数据***** */
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
			System.out.println("玩家消费同步返回码为：" + code);
		}
		catch (IOException e)
		{
			System.out.println("消费同步异常......");
		}

	}

	/*********玩家充值同步信息******* */
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
			System.out.println("玩家充值同步返回码为：" + code);
		}
		catch (IOException e)
		{
			System.out.println("玩家充值同步异常......");
		}

	}

	/** *****给渠道同步玩家等级信息******* */
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
			System.out.println("玩家等级同步返回码为：" + code);
			
		}
		catch (IOException e)
		{
			System.out.println("同步玩家等级异常.....");
		}
	}
}
