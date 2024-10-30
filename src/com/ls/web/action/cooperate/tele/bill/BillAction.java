package com.ls.web.action.cooperate.tele.bill;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.http.HttpRequester;
import com.ls.pub.util.http.HttpRespons;
import com.ls.pub.util.http.parseContent.ParseNormalContent;
import com.ls.web.service.cooperate.sky.BillService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;

public class BillAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.pay");

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,String> pay_results = new HashMap<String,String>();
		String money=request.getParameter("money");
		String custLabel=(String)request.getSession().getAttribute("teleid");
		String channelId=(String)request.getSession().getAttribute("channel_id");
		String netElementId="888999";
		String custIdType="3";
		String cpId=(String)request.getSession().getAttribute("cpId");
		String cpProductId=(String)request.getSession().getAttribute("cpProductId");
		String versionId="1_1_2";
		String transID=cpId+getDateStr()+"827315";
		BillService bs=new BillService();
		String uPk = (String)request.getSession().getAttribute("uPk");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		/***********验证session的有效性************/
		if(uPk==null)
		{
			logger.info("session监听器中的跳转到登陆界面时的uPk="+uPk+", roleInfo="+roleInfo);
			try
			{
				request.getRequestDispatcher("/jsp/out_page.jsp").forward(request, response);
				return null;
			}
			catch (Exception e)
			{
				logger.debug("uPk为null......");
			}
			
		}
		/**判断金额输入是否有误**/
		ValidateService validateService = new ValidateService();
		String hint = validateService.validateNonZeroNegativeIntegers(money);
		if(hint!=null)
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("index");
		}
		int pay_record_id =bs.addPayRecord(custLabel, money,Integer.parseInt(uPk));
		HttpRequester requester = new HttpRequester();
		Map<String, String> params=new HashMap<String, String>();
		params.put("msgType", "ChargeAddReq");
		params.put("netElementId",netElementId);
		params.put("custIdType",custIdType);
		params.put("custLabel", custLabel);
		params.put("channelId", channelId);
		params.put("cpId", cpId);
		params.put("cpProductId", cpProductId);
		params.put("transID", transID);
		params.put("versionId", versionId);
		params.put("money", money);
		HttpRespons responses = null;
		logger.info("*******充值金额******"+money);
		logger.info("*******用户编号******"+custLabel);
		try
		{
			logger.info("开始发送请求...");
			responses=requester.sendPostTele("http://202.102.39.11:9088/gameinterface/ChargeAddDirect",params);
			logger.info("结束发送请求..."+response);
			String respones_result = null;
			String status=null;
			String accountBalance=null;
			try
			{
				Document document = DocumentHelper.parseText(responses.getContent());
				Element root = document.getRootElement();
				Element resultElm = root.element("hRet");
				Element resultElm1 = root.element("status");
				Element resultElm2 = root.element("accountBalance");
				respones_result=resultElm.getText();
				logger.info("respones_result************"+respones_result);
				status=resultElm1.getText();
				logger.info("status*******************"+status);
				if(resultElm2!=null)
				{
					accountBalance=resultElm2.getText();
				}
				logger.info("accountBalance****************"+accountBalance);
			}
			catch (DocumentException e)
			{
				logger.debug("文档解析错误....");
			}
			/***0表示扣费成功1表示扣费失败***/
			if(respones_result==null)
			{
				request.setAttribute("hint", "平台返回为null");
				return mapping.findForward("index");
			}
			else if(respones_result.equals("0"))
			{
				logger.info("**********扣费成功********");
				bs.updatePayRecord( pay_record_id,transID, "","","","0");//更新消费记录
				//给玩家增加积分
				EconomyService economyService = new EconomyService();
				int u_pk = Integer.parseInt(uPk);
				//给玩家增加积分
				int yb_num = Integer.parseInt(money);
				int jf_num = yb_num*GameConfig.getJifenNum();//1KB获得1个积分
				economyService.addJifen(u_pk,jf_num);//增加积分：每成功兑换1KB获得1个积分
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(0, StatisticsType.PLAYER, 1, "player", "chongzhi",u_pk);//统计充值人次
				hint = "兑换成功,您获得了"+Integer.parseInt(money)*100+"个【积分】!";
				request.setAttribute("hint", hint);
				return mapping.findForward("success");
			}
			else if(respones_result.equals("1"))
			{
				System.out.println("********扣费失败***********");
				bs.updatePayRecord( pay_record_id,transID, "","","","1");
				hint = "充值失败请重新操作";
				request.setAttribute("hint", hint);
				return mapping.findForward("index");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/****得到时间的流水字符串***/
	private String getDateStr()
	{
		String todayStr = null;
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		todayStr= df.format(date.getTime());
		return todayStr;
	}
}
