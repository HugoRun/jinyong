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
import com.ls.model.property.RoleSkillInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.Wrap;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.player.ShortcutService;

/**
 * MyEclipse Struts Creation date: 08-15-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="cmd" validate="true"
 */
public class AttackShortcutAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");

	/**
	 * ��ݼ��б�
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = (String) request.getSession().getAttribute("pPk");
		String pk = request.getParameter("pk");

		request.setAttribute("shortType", request.getParameter("shortType"));
		request.setAttribute("aPpk", request.getParameter("aPpk"));
		request.setAttribute("bPpk", request.getParameter("bPpk"));

		ShortcutService shortcutService = new ShortcutService();
		List<ShortcutVO> shortcuts = shortcutService.getByPpkWithDeal(Integer.parseInt(p_pk));

		request.setAttribute("pk", pk);
		request.setAttribute("p_pk", p_pk);
		request.setAttribute("shortcuts", shortcuts);
		return mapping.findForward("list_a");
	}

	/**
	 * ���Ŀ�ݼ�
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = (String) request.getSession().getAttribute("pPk");
		String sc_pk = request.getParameter("sc_pk");
		String type = request.getParameter("type");
		String operate_id = request.getParameter("operate_id");
		String pk = request.getParameter("pk");
		request.setAttribute("pk", pk);
		request.setAttribute("aPpk", request.getParameter("aPpk"));
		request.setAttribute("bPpk", request.getParameter("bPpk"));

		request.setAttribute("shortType", request.getParameter("shortType"));

		if (operate_id == null)
		{
			operate_id = "0";
		}

		ShortcutService shortcutService = new ShortcutService();
		shortcutService.updateShortcut(Integer.parseInt(sc_pk), Integer
				.parseInt(type), Integer.parseInt(operate_id), p_pk);

		List<ShortcutVO> shortcuts = shortcutService.getByPpk(Integer
				.parseInt(p_pk));

		request.setAttribute("p_pk", p_pk);
		request.setAttribute("shortcuts", shortcuts);
		HttpSession session = request.getSession();
		String return_url = (String) session.getAttribute("retrun_url");
		session.removeAttribute("retrun_url");
		if (return_url != null)
		{
			try
			{
				request.getRequestDispatcher(return_url).forward(request,
						response);

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (ServletException e)
			{
				e.printStackTrace();
			}

			return null;
		}

		return mapping.findForward("list_a");
	}

	/**
	 * ��ʾ��ݼ�����
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = (String) request.getSession().getAttribute("pPk");
		String sc_pk = request.getParameter("sc_pk");// ��ݼ�pk
		String pk = (String) request.getAttribute("pk");
		if (pk == null)
		{
			pk = request.getParameter("pk");
		}
		request.setAttribute("aPpk", request.getParameter("aPpk"));
		request.setAttribute("bPpk", request.getParameter("bPpk"));

		request.setAttribute("shortType", request.getParameter("shortType"));

		if (p_pk == null)
		{
			p_pk = (String) request.getAttribute("p_pk");
		}

		request.setAttribute("pk", pk);
		request.setAttribute("p_pk", p_pk);
		request.setAttribute("sc_pk", sc_pk);
		return mapping.findForward("list_type_a");
	}

	/**
	 * ��տ�ݼ�
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = (String) request.getSession().getAttribute("pPk");
		String pk = request.getParameter("pk");

		request.setAttribute("aPpk", request.getParameter("aPpk"));
		request.setAttribute("bPpk", request.getParameter("bPpk"));

		request.setAttribute("shortType", request.getParameter("shortType"));

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		roleInfo.getRoleShortCutInfo().clearShortcut();

		ShortcutService shortcutService = new ShortcutService();
		List<ShortcutVO> shortcuts = shortcutService.getByPpk(Integer
				.parseInt(p_pk));

		request.setAttribute("pk", pk);
		request.setAttribute("p_pk", p_pk);
		request.setAttribute("shortcuts", shortcuts);
		return mapping.findForward("list_a");
	}

	/**
	 * �����б�
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = (String) request.getSession().getAttribute("pPk");
		String sc_pk = request.getParameter("sc_pk");
		String pk = request.getParameter("pk");
		request.setAttribute("pk", pk);

		boolean flag = false;
		request.setAttribute("aPpk", request.getParameter("aPpk"));
		request.setAttribute("bPpk", request.getParameter("bPpk"));

		request.setAttribute("shortType", request.getParameter("shortType"));

		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(p_pk);

		PlayerSkillVO playerSkillVO = null;
		RoleSkillInfo useSkill = roleEntity.getRoleSkillInfo();
		List<PlayerSkillVO> skills = useSkill.getSkillList();

		for (int i = skills.size() - 1; i >= 0; i--)
		{
			playerSkillVO = skills.get(i);
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
		if (flag)
		{
			isCatchPet = "�в�׽���＼��";
		}
		request.setAttribute("isCatchPet", isCatchPet);

		request.setAttribute("p_pk", p_pk);
		request.setAttribute("sc_pk", sc_pk);
		request.setAttribute("skills", skills);
		return mapping.findForward("list_skill_a");
	}

	/**
	 * ҩƷ�б�
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String p_pk = (String) request.getSession().getAttribute("pPk");
		String sc_pk = request.getParameter("sc_pk");
		String pk = request.getParameter("pk");
		request.setAttribute("pk", pk);

		request.setAttribute("shortType", request.getParameter("shortType"));

		request.setAttribute("aPpk", request.getParameter("aPpk"));
		request.setAttribute("bPpk", request.getParameter("bPpk"));

		GoodsService goodsService = new GoodsService();

		List cures = goodsService.getDisdinctAndBattleUsableProps(Integer
				.parseInt(p_pk), Wrap.CURE);

		request.setAttribute("p_pk", p_pk);
		request.setAttribute("sc_pk", sc_pk);
		request.setAttribute("cures", cures);
		return mapping.findForward("list_cure_a");
	}
	
	public ActionForward prop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String p_pk = (String) request.getSession().getAttribute("pPk");
		String sc_pk = request.getParameter("sc_pk");
		String pk = request.getParameter("pk");
		request.setAttribute("pk", pk);

		request.setAttribute("shortType", request.getParameter("shortType"));

		request.setAttribute("aPpk", request.getParameter("aPpk"));
		request.setAttribute("bPpk", request.getParameter("bPpk"));

		GoodsService goodsService = new GoodsService();

		List cures = goodsService.getDisdinctAttackPetProps(Integer
				.parseInt(p_pk), PropType.GETPETPROP);

		request.setAttribute("p_pk", p_pk);
		request.setAttribute("sc_pk", sc_pk);
		request.setAttribute("cures", cures);
		return mapping.findForward("list_pet_a");
	}

}