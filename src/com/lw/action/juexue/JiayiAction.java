package com.lw.action.juexue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.lw.service.player.JueXueService;

public class JiayiAction extends DispatchAction
{
	// ʹ�ü����� ���ɵ���
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String sk_id_str = request.getParameter("sk_id");
		if (sk_id_str == null && sk_id_str.equals("")
				&& sk_id_str.equals("null"))
		{
			request.setAttribute("display", "���ܴ���");
			return mapping.findForward("display");
		}
		else
		{
			PropCache pc = new PropCache();
			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request
					.getSession());
			JueXueService juexue = new JueXueService();
			int trem = juexue.userSkillJiayi(roleInfo.getBasicInfo().getPPk(),
					Integer.parseInt(sk_id_str));
			if (trem == 1)
			{
				request.setAttribute("display", "�������Ѿ�ʹ�ù��þ�ѧ!");
				return mapping.findForward("display");
			}
			else
				if (trem == 2)
				{
					request
							.setAttribute("display",
									"�Բ���,����ǰ�������鲻�㵱ǰ�ȼ����������5%!");
					return mapping.findForward("display");
				}
				else
					if (trem == 3)
					{
						String name = pc.getPropNameById(GameConfig
								.getJuexuePropID());
						request.setAttribute("display", "�Բ���,��û��" + name + "!");
						request.setAttribute("goumai", "����" + name);
						request.setAttribute("sk_id", sk_id_str);
						return mapping.findForward("buy");
					}
					else
					{
						if (roleInfo.getBasicInfo().getWrapSpare() == 0)
						{
							request.setAttribute("display",
									"�Բ���,���İ���������Ԥ��һ���ո�!");
							return mapping.findForward("display");
						}
						else
						{
							// ɾ������
							GoodsService gs = new GoodsService();
							gs.removeProps(roleInfo.getPPk(), GameConfig.getJuexuePropID(), 1,GameLogManager.R_USE);
							// �õ�����
							int prop_id = juexue
									.getJiayiProducePropID(roleInfo);
							if (prop_id == 0)
							{
								request.setAttribute("display", "�Բ���,��Ʒ����!");
								return mapping.findForward("display");
							}
							gs.putGoodsToWrap(roleInfo.getBasicInfo().getPPk(),
									prop_id, 4, 1);
							// ʹ�ô���������
							TimeControlService timeControlService = new TimeControlService();
							timeControlService.updateControlInfo(roleInfo
									.getBasicInfo().getPPk(), Integer
									.parseInt(sk_id_str),
									TimeControlService.SKILL);
							long used_exp = (Long.parseLong(roleInfo
									.getBasicInfo().getNextGradeExp()) - Long
									.parseLong(roleInfo.getBasicInfo()
											.getPreGradeExp())) / 20;
							roleInfo.getBasicInfo().updateAddCurExp(
									-Integer.parseInt(used_exp + ""));
							String name1 = pc.getPropNameById(GameConfig
									.getJuexuePropID());
							String name2 = pc.getPropNameById(prop_id);
							request.setAttribute("display", "��������" + name1
									+ "��1�͵�ǰ�ȼ���������,�����" + name2 + "!");
							return mapping.findForward("display");
						}
					}
		}
	}

	// ���߲������ʾ ������ת����ҳ��
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		EconomyService economyService = new EconomyService();
		int u_pk = roleInfo.getBasicInfo().getUPk();
		long yuanbao = economyService.getYuanbao(u_pk);
		int useryuanbao = 250;
		if (yuanbao < useryuanbao)
		{
			request.setAttribute("display", "����"+GameConfig.getYuanbaoName()+"�������ֵ!");
			return mapping.findForward("display");
		}
		else
		{
			String sk_id_str = request.getParameter("sk_id");
			if (sk_id_str == null || sk_id_str.equals("")
					|| sk_id_str.equals("null"))
			{
				request.setAttribute("display", "���ܴ���");
				return mapping.findForward("display");
			}
			PropCache pc = new PropCache();
			String name = pc.getPropNameById(GameConfig.getJuexuePropID());
			GoodsService gs = new GoodsService();
			gs.putGoodsToWrap(roleInfo.getBasicInfo().getPPk(), GameConfig
					.getJuexuePropID(), 4, 1);
			economyService.spendYuanbao(u_pk, useryuanbao);
			request.setAttribute("display", "�㻨��"+GameConfig.getYuanbaoName()+"��" + useryuanbao + " ����"
					+ name + "��1!");
			request.setAttribute("sk_id", sk_id_str);
			return mapping.findForward("hint");
		}
	}

	// ���Ľ�β
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("information_menu");
	}
}
