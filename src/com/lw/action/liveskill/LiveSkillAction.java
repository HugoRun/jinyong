package com.lw.action.liveskill;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.staticcache.skill.SkillCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.skill.SkillVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.lw.service.skill.LiveSkillService;

public class LiveSkillAction extends DispatchAction
{
	/** ������б� */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		int type = Integer.parseInt(request.getParameter("type"));
		LiveSkillService se = new LiveSkillService();
		List<SkillVO> liveSkill = se.getLiveSkill();
		request.setAttribute("p_pk", roleInfo.getBasicInfo().getPPk());
		request.setAttribute("skills", liveSkill);
		request.setAttribute("type", type + "");
		return mapping.findForward("liveskill_list");
	}

	/** ���������б� */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		int type = Integer.parseInt(request.getParameter("type"));
		int s_type = Integer.parseInt(request.getParameter("s_type"));
		LiveSkillService se = new LiveSkillService();
		List playerLiveSkill = se.getPlayerLiveSkill(roleInfo.getBasicInfo().getPPk());
		if (playerLiveSkill.size() == 0)
		{
			request.setAttribute("display", "��û�������κ������!");
			return mapping.findForward("display");
		}
		else
		{
			request.setAttribute("p_pk", roleInfo.getBasicInfo().getPPk() + "");
			request.setAttribute("skills", playerLiveSkill);
			request.setAttribute("type", type + "");
			request.setAttribute("s_type", s_type + "");
			return mapping.findForward("liveskill_list");
		}
	}

	/** ѧϰ����� */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String sk_id = request.getParameter("sk_id");
		int money = Integer.parseInt(request.getParameter("money"));
		LiveSkillService se = new LiveSkillService();
		if (se.isHaveLiveSkill(roleInfo.getBasicInfo().getPPk()) == false)
		{
			request.setAttribute("display", "�Բ���,���Ѿ�ѧϰ��2�������!");
			return mapping.findForward("display");
		}
		else
			if (se.isHaveThisLiveSkill(roleInfo.getBasicInfo().getPPk(), sk_id) == false)
			{
				request.setAttribute("display", "�Բ���,���Ѿ�ѧϰ���ü���!");
				return mapping.findForward("display");
			}
			else
			{
				PartInfoDao partInfoDao = new PartInfoDao();
				PartInfoVO partInfoVO = partInfoDao.getPartInfoByID(Integer
						.valueOf(roleInfo.getBasicInfo().getPPk()));
				long copper = Long.valueOf(partInfoVO.getPCopper());
				if (copper < money)
				{
					request.setAttribute("display", "�Բ���,�����ϵ���������!");
					return mapping.findForward("display");
				}
				else
				{
					SkillDao dao = new SkillDao();
					SkillCache skillCaChe = new SkillCache();		
					String name = skillCaChe.getNameById(Integer.parseInt(sk_id));
					se.studyLiveSkill(roleInfo.getBasicInfo().getPPk(), Integer
							.parseInt(sk_id), money);
					request.setAttribute("display", "���Ѿ�ѧ��" + name);
					return mapping.findForward("display");
				}
			}
	}

	/** ��������� */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int s_pk = Integer.parseInt(request.getParameter("s_pk"));
		String sk_name = request.getParameter("sk_name");
		LiveSkillService se = new LiveSkillService();
		se.delLiveSkill(s_pk);
		request.setAttribute("display", "���Ѿ�������" + sk_name + "�����!");
		return mapping.findForward("display");
	}

	/** ѧϰ�������ܵ�ȷ�� */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		int type = Integer.parseInt(request.getParameter("type"));
		int s_type = Integer.parseInt(request.getParameter("s_type"));
		if (type == 1)
		{
			LiveSkillService se = new LiveSkillService();
			int sk_id = se.getSkillID(s_type);
			SkillDao dao = new SkillDao();
			SkillCache skillCaChe = new SkillCache();		
			String sk_name = skillCaChe.getNameById(sk_id);
			
			request.setAttribute("display", "��Ҫѧϰ" + sk_name
					+ "�������ѧϰ�˼����軨��������5");
			request.setAttribute("p_pk", roleInfo.getBasicInfo().getPPk() + "");
			request.setAttribute("id", sk_id + "");
			request.setAttribute("type", type + "");
			request.setAttribute("sk_name", sk_name + "");
			request.setAttribute("s_type", s_type + "");
			return mapping.findForward("submit");
		}
		else
		{
			int s_pk = Integer.parseInt(request.getParameter("s_pk"));
			String sk_name = request.getParameter("sk_name");
			request.setAttribute("display", "��ȷ��Ҫ������" + sk_name + "��?�����󲻿ɻָ�!");
			request.setAttribute("p_pk", roleInfo.getBasicInfo().getPPk() + "");
			request.setAttribute("id", s_pk + "");
			request.setAttribute("type", type + "");
			request.setAttribute("sk_name", sk_name + "");
			request.setAttribute("s_type", s_type + "");
			return mapping.findForward("submit");
		}
	}
}
