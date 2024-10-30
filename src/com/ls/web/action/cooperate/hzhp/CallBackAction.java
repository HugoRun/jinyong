package com.ls.web.action.cooperate.hzhp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.logininfo.LoginInfoDAO;
import com.ben.vo.logininfo.LoginInfoVO;
import com.ls.ben.dao.cooparate.bill.UAccountRecordDao;
import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.StatisticsType;
import com.ls.web.service.player.EconomyService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;

public class CallBackAction
{
	Logger logger = Logger.getLogger("log.pay");
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("##########杭州热点直接回调############");
		//pay_type
		String pay_type = request.getParameter("pay_type");
		//pay_id
		String pay_id = request.getParameter("pay_id");
		//user_id
		String user_id = request.getParameter("user_id");
		//pay_money
		String pay_money = request.getParameter("pay_money");
		//pay_time
		String pay_time = request.getParameter("pay_time");
		
		//查询是是否有该充值ID
		UAccountRecordDao dao  = new UAccountRecordDao();
		UAccountRecordVO vo = dao.getRecord(pay_id, Channel.AIR+"");
		if(vo == null){
			try
			{
				int yuanbao = Integer.parseInt(pay_money)*100;
				LoginInfoVO loginInfoVO = new LoginInfoVO();
				LoginInfoDAO loginInfodao = new LoginInfoDAO();
				loginInfoVO = loginInfodao.getUserInfoLoginName(user_id);
				if(loginInfoVO == null){
					//没有该用户
					request.setAttribute("resultWml", "01");
					return mapping.findForward("success");
				}else{
					//充值成功
					EconomyService es = new EconomyService();
					es.addYuanbao(loginInfoVO.getUPk(), yuanbao);
					es.addJifen(loginInfoVO.getUPk(), yuanbao*GameConfig.getJifenNum());
					UAccountRecordVO uAccountRecordVO = new UAccountRecordVO();
					uAccountRecordVO.setUPk(loginInfoVO.getUPk());
					uAccountRecordVO.setCode(pay_id);
					uAccountRecordVO.setMoney(Integer.parseInt(pay_money));
					uAccountRecordVO.setChannel(Channel.AIR+"");
					uAccountRecordVO.setAccountState("sucess");
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(0, StatisticsType.RMB, Integer.parseInt(pay_money),
							StatisticsType.DEDAO, "k金", loginInfoVO.getUPk());// 统计RMB
					request.setAttribute("resultWml", "00");
					return mapping.findForward("success");
				}
			}
			catch (Exception e)
			{
				request.setAttribute("resultWml", "02");
				return mapping.findForward("success");
			}
		}else{
			request.setAttribute("resultWml", "02");
			return mapping.findForward("success");
		}
	}
}
