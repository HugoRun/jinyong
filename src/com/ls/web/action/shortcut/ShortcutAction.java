/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.ls.web.action.shortcut;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.model.property.RoleShortCutInfo;
import com.ls.model.property.RoleSkillInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Wrap;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.player.ShortcutService;

/** 
 * 快捷键
 */
public class ShortcutAction extends DispatchAction {
	Logger logger =  Logger.getLogger("log.action");

	
	/**
	 * 快捷键列表
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pPk = (String)request.getSession().getAttribute("pPk");
		if( pPk==null )
		{	
			logger.debug("p_pk为空");
		}
		
		ShortcutService shortcutService = new ShortcutService();
		List<ShortcutVO> shortcuts = shortcutService.getByPpk(Integer.parseInt(pPk));
		
		request.setAttribute("p_pk", pPk);
		request.setAttribute("shortcuts", shortcuts);
		return mapping.findForward("list");
	}
	

	/**
	 * 更改快捷键
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pPk = (String)request.getSession().getAttribute("pPk");
		String sc_pk = request.getParameter("sc_pk");
		String type = request.getParameter("type");
		
		
		
		String operate_id = request.getParameter("operate_id");
		if( pPk==null )
		{	
			logger.debug("p_pk为空");
		}
		if( operate_id==null )
		{
			operate_id = "0";
		}
		ShortcutService shortcutService = new ShortcutService();
		shortcutService.updateShortcut(Integer.parseInt(sc_pk), Integer.parseInt(type),Integer.parseInt(operate_id),pPk);
		
		List<ShortcutVO> shortcuts = shortcutService.getByPpk(Integer.parseInt(pPk));
		
		
		
		request.setAttribute("p_pk", pPk);
		request.setAttribute("shortcuts", shortcuts);
		HttpSession session = request.getSession();
		String return_url = (String)session.getAttribute("retrun_url");
		session.removeAttribute("retrun_url");
		if( return_url!=null )
		{
			try {
				request.getRequestDispatcher(return_url).forward(request, response); 
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch (ServletException e)
			{
				e.printStackTrace();
			}
			
			return null;
		}
		try
		{
			request.getRequestDispatcher("/pubaction.do?chair="+ request.getParameter("chair")).forward(request, response);
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  显示快捷键种类
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pPk = (String)request.getSession().getAttribute("pPk");
		String sc_pk = request.getParameter("sc_pk");//快捷键pk
		
		
		if( pPk==null )
		{	
			logger.debug("p_pk为空");
			pPk = (String)request.getAttribute("p_pk");
		}
		if( sc_pk==null  )
		{
			logger.debug("s_pk为空");
		}
		
		
		request.setAttribute("p_pk", pPk);
		request.setAttribute("sc_pk", sc_pk);
		return mapping.findForward("list_type");
	}
	
	/**
	 *  清空快捷键
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pPk = (String)request.getSession().getAttribute("pPk");
		if( pPk==null )
		{	
			logger.debug("p_pk为空");
		} 
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		roleInfo.getRoleShortCutInfo().clearShortcut();
		
		ShortcutService shortcutService = new ShortcutService();
		List<ShortcutVO> shortcuts = shortcutService.getByPpk(Integer.parseInt(pPk));
		 
		request.setAttribute("p_pk", pPk);
		request.setAttribute("shortcuts", shortcuts);
		return mapping.findForward("list");
	}
	
	/**
	 *  技能列表
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String sc_pk = request.getParameter("sc_pk");
		boolean flag = false;
		String pPk = (String)request.getSession().getAttribute("pPk");
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);		
		if( pPk==null )
		{	
			logger.debug("p_pk为空");
		}  				
		PlayerSkillVO playerSkillVO = null;
		RoleSkillInfo useSkill = roleEntity.getRoleSkillInfo();
		List<PlayerSkillVO> skills = useSkill.getSkillList();
		
		for ( int i = skills.size() -1; i>= 0;i--) {
			playerSkillVO = skills.get(i);
			logger.info("skId="+playerSkillVO.getSkId());
			if (playerSkillVO.getSkId() == 1) {
				flag = true;
				skills.remove(i);
			}
			if (playerSkillVO.getSkType() == 0)
			{
				flag = true;
				skills.remove(i);
			}
			if (playerSkillVO.getSkType() == 2)
			{
				flag = true;
				skills.remove(i);
			}
			if (playerSkillVO.getSkType() == 3)
			{
				flag = true;
				skills.remove(i);
			}
		}
		
		
		String isCatchPet = null;
		//SkillService skillService = new SkillService();
		if(flag)
		{
			isCatchPet = "有捕捉宠物技能";
		}
		request.setAttribute("isCatchPet", isCatchPet);
		request.setAttribute("p_pk", pPk);
		request.setAttribute("sc_pk", sc_pk);
		request.setAttribute("skills", skills);
		return mapping.findForward("list_skill");
	}
	
	/**
	 *  药品列表
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String pPk = (String)request.getSession().getAttribute("pPk");
		String sc_pk = request.getParameter("sc_pk");
		
		
		if( pPk==null )
		{	
			logger.debug("p_pk为空");
		}
		
		
		
		GoodsService goodsService = new GoodsService();
		
		List cures = goodsService.getDisdinctAndBattleUsableProps(Integer.parseInt(pPk), Wrap.CURE);
		
		request.setAttribute("p_pk", pPk);
		request.setAttribute("sc_pk", sc_pk);
		request.setAttribute("cures", cures);
		return mapping.findForward("list_cure");
	}
	
	/**
	 * 快捷键列表
	 * 此列表是专门用来接收玩家学习技能后跳转的
	 * 
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pPk = (String)request.getSession().getAttribute("pPk");
		String skill_id = request.getParameter("skill_id");
		if( pPk==null )
		{	
			logger.debug("p_pk为空");
		}	
		if( skill_id==null )
		{	
			logger.debug("skill_id为空");
		}
		request.setAttribute("skill_id", skill_id);
		
		ShortcutService shortcutService = new ShortcutService();
		List<ShortcutVO> shortcuts = shortcutService.getByPpk(Integer.parseInt(pPk));
		
		request.setAttribute("p_pk", pPk);
		request.setAttribute("shortcuts", shortcuts);
		return mapping.findForward("skill_shortcut");
	}
	
}