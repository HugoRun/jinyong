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
	
	// ׼������
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String w_type = (String)request.getAttribute("w_type");
		String pg_pk = (String)request.getAttribute("pg_pk");
		String goods_id = (String)request.getAttribute("goods_id");
		String goods_type = (String)request.getAttribute("goods_type");
		
		
		return mapping.findForward("start_speak");
	}
	
	// ׼������
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
			String hint = "�����������̫����,����20��������!";
			request.setAttribute("hint", hint);
			request.setAttribute("pg_pk", pg_pk);
			
			return mapping.findForward("start_speak");
		}else {
			int i = Expression.hasPublish(content);
			if(i == -1) {
				String hint = "����������л��зǷ��ַ�,����������!";
				request.setAttribute("hint", hint);
				request.setAttribute("pg_pk", pg_pk);
				
				return mapping.findForward("start_speak");
			} else {
				PlayerPropGroupDao ppdao = new PlayerPropGroupDao();
				PlayerPropGroupVO ppgvo = ppdao.getByPgPk(Integer.valueOf(pg_pk));
				
				MessageResources resources = getResources(request);
				//�������Լ��
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
					String hint = "�Բ�����������ʹ�ø�����߱�����1����!";
					request.setAttribute("hint", hint);
					request.setAttribute("pg_pk", pg_pk);
					
					return mapping.findForward("start_speak");
				}
			}
		}
		//String hint = "�����,��Ҳ�������Ϊʲô�ܵ����ҳ����,�������Ŀ�����,�뱨��ͷ���Ա,лл!";
		//request.setAttribute("hint", hint);
		//request.setAttribute("pg_pk", pg_pk);
		//return mapping.findForward("sussenno"); 
		
	}

}
