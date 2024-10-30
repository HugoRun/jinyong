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
	// 使用嫁衣神功 生成道具
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String sk_id_str = request.getParameter("sk_id");
		if (sk_id_str == null && sk_id_str.equals("")
				&& sk_id_str.equals("null"))
		{
			request.setAttribute("display", "技能错误");
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
				request.setAttribute("display", "您今天已经使用过该绝学!");
				return mapping.findForward("display");
			}
			else
				if (trem == 2)
				{
					request
							.setAttribute("display",
									"对不起,您当前升级经验不足当前等级升级经验的5%!");
					return mapping.findForward("display");
				}
				else
					if (trem == 3)
					{
						String name = pc.getPropNameById(GameConfig
								.getJuexuePropID());
						request.setAttribute("display", "对不起,您没有" + name + "!");
						request.setAttribute("goumai", "购买" + name);
						request.setAttribute("sk_id", sk_id_str);
						return mapping.findForward("buy");
					}
					else
					{
						if (roleInfo.getBasicInfo().getWrapSpare() == 0)
						{
							request.setAttribute("display",
									"对不起,您的包裹已满请预留一个空格!");
							return mapping.findForward("display");
						}
						else
						{
							// 删除道具
							GoodsService gs = new GoodsService();
							gs.removeProps(roleInfo.getPPk(), GameConfig.getJuexuePropID(), 1,GameLogManager.R_USE);
							// 得到道具
							int prop_id = juexue
									.getJiayiProducePropID(roleInfo);
							if (prop_id == 0)
							{
								request.setAttribute("display", "对不起,物品错误!");
								return mapping.findForward("display");
							}
							gs.putGoodsToWrap(roleInfo.getBasicInfo().getPPk(),
									prop_id, 4, 1);
							// 使用次数的增加
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
							request.setAttribute("display", "您消耗了" + name1
									+ "×1和当前等级经验若干,获得了" + name2 + "!");
							return mapping.findForward("display");
						}
					}
		}
	}

	// 道具不足给提示 并且跳转到该页面
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
			request.setAttribute("display", "您的"+GameConfig.getYuanbaoName()+"不足请充值!");
			return mapping.findForward("display");
		}
		else
		{
			String sk_id_str = request.getParameter("sk_id");
			if (sk_id_str == null || sk_id_str.equals("")
					|| sk_id_str.equals("null"))
			{
				request.setAttribute("display", "技能错误");
				return mapping.findForward("display");
			}
			PropCache pc = new PropCache();
			String name = pc.getPropNameById(GameConfig.getJuexuePropID());
			GoodsService gs = new GoodsService();
			gs.putGoodsToWrap(roleInfo.getBasicInfo().getPPk(), GameConfig
					.getJuexuePropID(), 4, 1);
			economyService.spendYuanbao(u_pk, useryuanbao);
			request.setAttribute("display", "你花费"+GameConfig.getYuanbaoName()+"×" + useryuanbao + " 购买"
					+ name + "×1!");
			request.setAttribute("sk_id", sk_id_str);
			return mapping.findForward("hint");
		}
	}

	// 最后的结尾
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("information_menu");
	}
}
