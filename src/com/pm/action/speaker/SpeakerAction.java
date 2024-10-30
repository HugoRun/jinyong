package com.pm.action.speaker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;

import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.RoleService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pub.ben.info.Expression;

public class SpeakerAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");
	
	// 准备发言
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String w_type = (String)request.getAttribute("w_type");
		String pg_pk = (String)request.getAttribute("pg_pk");
		String goods_id = (String)request.getAttribute("goods_id");
		String goods_type = (String)request.getAttribute("goods_type");
		
		
		return mapping.findForward("start_speak");
	}
	
	// 准备发言
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		String content = request.getParameter("content");
		logger.info("content="+content);
		
		String pg_pk = request.getParameter("pg_pk");
		
		if(content.length() >= 20) {
			String hint = "您输入的字数太多了,请在20个字以内!";
			request.setAttribute("hint", hint);
			request.setAttribute("pg_pk", pg_pk);
			
			return mapping.findForward("start_speak");
		}else {
			int i = Expression.hasPublish(content);
			if(i == -1) {
				String hint = "您输入的字中还有非法字符,请重新输入!";
				request.setAttribute("hint", hint);
				request.setAttribute("pg_pk", pg_pk);
				
				return mapping.findForward("start_speak");
			} else {
				PlayerPropGroupDao ppdao = new PlayerPropGroupDao();
				PlayerPropGroupVO ppgvo = ppdao.getByPgPk(Integer.valueOf(pg_pk));
				
				MessageResources resources = getResources(request);
				//公共发言间隔
				String speakerCommTime = resources.getMessage("speakerCommTime");
				
				logger.info("speakerCommTime="+speakerCommTime);
				TimeControlService timecontrol = new TimeControlService();
				
				if(timecontrol.isUseableWithNum(p_pk, ppgvo.getPropId(), 1, Integer.valueOf(speakerCommTime)) )	{	
					
					SystemInfoService infoService = new SystemInfoService();
					infoService.insertSystemInfoBySpeaker(content,p_pk);
					
					GoodsService goodsService = new GoodsService();
					goodsService.removeProps(p_pk, ppgvo.getPropId(), 1,GameLogManager.R_USE);
					timecontrol.updateControlInfo(p_pk, ppgvo.getPropId(), 1);
					
					return mapping.findForward("walk");
					
				} else {
					String hint = "对不起，连续两次使用该类道具必须间隔1分钟!";
					request.setAttribute("hint", hint);
					request.setAttribute("pg_pk", pg_pk);
					
					return mapping.findForward("start_speak");
				}
			}
		}
		//String hint = "很奇怪,我也很奇怪您为什么能到这个页面来,如果您真的看到了,请报告客服人员,谢谢!";
		//request.setAttribute("hint", hint);
		//request.setAttribute("pg_pk", pg_pk);
		//return mapping.findForward("sussenno"); 
		
	}

}
