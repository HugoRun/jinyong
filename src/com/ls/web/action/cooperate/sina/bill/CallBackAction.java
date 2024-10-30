package com.ls.web.action.cooperate.sina.bill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.web.service.cooperate.sina.BillService;

public class CallBackAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.pay");

	// 新浪梦网充值回调
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String uid = request.getParameter("uid");
		String pid = request.getParameter("pid");
		String snum = request.getParameter("snum");
		String tm = request.getParameter("tm");
		String hs = request.getParameter("hs");
		String errcode = request.getParameter("errcode");

		if(uid == null || uid.equals("null")){
			request.setAttribute("resultWml", "errcode="+errcode+"&uid="+uid+"&pid="+pid+"&snum="+snum+"&hs="+hs+"&tm="+tm);
			return mapping.findForward("success");
		}
		
		if(pid == null || pid.equals("null")){
			request.setAttribute("resultWml", "errcode="+errcode+"&uid="+uid+"&pid="+pid+"&snum="+snum+"&hs="+hs+"&tm="+tm);
			return mapping.findForward("success");
		}
		
		if(snum == null || snum.equals("null")){
			request.setAttribute("resultWml", "errcode="+errcode+"&uid="+uid+"&pid="+pid+"&snum="+snum+"&hs="+hs+"&tm="+tm);
			return mapping.findForward("success");
		}
		
		if(tm == null || tm.equals("null")){
			request.setAttribute("resultWml", "errcode="+errcode+"&uid="+uid+"&pid="+pid+"&snum="+snum+"&hs="+hs+"&tm="+tm);
			return mapping.findForward("success");
		}
		
		if(hs == null || hs.equals("null")){
			request.setAttribute("resultWml", "errcode="+errcode+"&uid="+uid+"&pid="+pid+"&snum="+snum+"&hs="+hs+"&tm="+tm);
			return mapping.findForward("success");
		}
		
		if(errcode == null || errcode.equals("null")){
			request.setAttribute("resultWml", "errcode="+errcode+"&uid="+uid+"&pid="+pid+"&snum="+snum+"&hs="+hs+"&tm="+tm);
			return mapping.findForward("success");
		}
		
		logger.info("########新浪梦网充值回调########");
		logger.info("uid:" + uid);
		logger.info("pid:" + pid);
		logger.info("snum:" + snum);
		logger.info("tm:" + tm);
		logger.info("hs:" + hs);
		logger.info("errcode:" + errcode);
		
		BillService billService = new BillService();
		billService.payByYiDong(uid,errcode,snum,tm,hs);

		request.setAttribute("resultWml", "errcode="+errcode+"&uid="+uid+"&pid="+pid+"&snum="+snum+"&hs="+hs+"&tm="+tm);
		return mapping.findForward("success");
	}
}