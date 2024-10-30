package com.lw.action.synthesize;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.lw.dao.synthesize.SynthesizeDao;
import com.lw.service.skill.LiveSkillService;
import com.lw.service.synthesize.SynthesizeService;
import com.lw.vo.synthesize.SynthesizeVO;

public class SynthesizeAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");

	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int s_type = Integer.parseInt(request.getParameter("s_type"));
		request.setAttribute("s_type", s_type + "");
		return mapping.findForward("synthesize_menu");
	}

	/** 判断玩家是否有该生活技能 有则列出技能的配方表 没有提示玩家 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		int s_type = Integer.parseInt(request.getParameter("s_type"));
		LiveSkillService lss = new LiveSkillService();
		int thispage;
		if (request.getParameter("thispage") == null)
		{
			thispage = 1;
		}
		else
		{
			String page_no = request.getParameter("thispage");
			thispage = Integer.parseInt(page_no);
		}
		if (lss.isHaveSkillType(roleInfo.getBasicInfo().getPPk(), s_type) == false)
		{
			request.setAttribute("display", "您没有掌握该生活技能!");
			return mapping.findForward("display");
		}
		else
		{
			SynthesizeService ss = new SynthesizeService();
			int s_level = lss.getPlayerLiveSkillLevel(roleInfo.getBasicInfo().getPPk(),
					s_type);
			int num = ss.getSynthesizeProp(s_type, s_level).size();
			int pagenum = num / 7 + (num % 7 == 0 ? 0 : 1);
			List list = ss.getSynthesizePropList(s_type, s_level, thispage, 7);
			List<String> namelist = ss.getSynthesizePropInfoList(s_type,
					s_level, thispage, 7);
			request.setAttribute("p_pk", roleInfo.getBasicInfo().getPPk() + "");
			request.setAttribute("s_type", s_type + "");
			request.setAttribute("list", list);
			request.setAttribute("namelist", namelist);
			request.setAttribute("thispage", thispage);
			request.setAttribute("pagenum", pagenum);
			return mapping.findForward("synthesize_list");

		}
	}

	/** 兑换物品的显示 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
//		int p_pk = Integer.parseInt(request.getParameter("p_pk"));
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		int s_id = Integer.parseInt(request.getParameter("s_id"));
		String name = request.getParameter("name");
		int minsleight = Integer.parseInt(request
				.getParameter("minsleight"));
		int maxsleight = Integer.parseInt(request
				.getParameter("maxsleight"));
		int s_type = Integer.parseInt(request.getParameter("s_type"));

		LiveSkillService lss = new LiveSkillService();
		SynthesizeService ss = new SynthesizeService();
		SynthesizeDao dao = new SynthesizeDao();

		int sk_group = lss.getSkillGroup(s_type);
		int playersleight = lss.getPlayerSleight(p_pk, sk_group);

		if (playersleight < minsleight)
		{
			request.setAttribute("display", "您的熟练度没有达到要求!");
			return mapping.findForward("display");
		}
		else
			if (lss.isPlayerHaveSynthesizeBook(p_pk, s_id) == false)
			{
				request.setAttribute("display", "您还没有学习该配方!");
				return mapping.findForward("display");
			}
			else
			{
				SynthesizeVO vo = dao.getSynthesize(s_id);
				String propinfo = dao.getPropInfo(name);
				List proplist = ss.getSynthesizeList(vo);
				List playerproplist = ss.getPlayerPropList(p_pk, vo);
				int num = ss.getNum(p_pk, vo);
				request.setAttribute("proplist", proplist);
				request.setAttribute("name", name);
				request.setAttribute("propinfo", propinfo);
				request.setAttribute("playerproplist", playerproplist);
				request.setAttribute("minsleight", minsleight);
				request.setAttribute("maxsleight", maxsleight);
				request.setAttribute("p_pk", p_pk);
				request.setAttribute("s_id", s_id);
				request.setAttribute("s_type", s_type);
				request.setAttribute("num", num);
				return mapping.findForward("synthesize_submit");
			}
	}

	/** 兑换物品 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String num = request.getParameter("num");
		if (num.equals(""))
		{
			request.setAttribute("display", "输入错误!");
			return mapping.findForward("display");
		}
		int s_id = Integer.parseInt(request.getParameter("s_id"));
		String pPk = request.getParameter("p_pk");
		int s_type = Integer.parseInt(request.getParameter("s_type"));
		int maxsleight = Integer.parseInt(request
				.getParameter("maxsleight"));

		SynthesizeService ss = new SynthesizeService();
		String address = request.getParameter("address");
		logger.info("address=" + address + " ,exchange_nums=" + num);

		SynthesizeDao dao = new SynthesizeDao();
		SynthesizeVO SynthesizeVO = dao.getSynthesize(s_id);

		// 判断是否有足够的原材料
		String result = ss.getPPkHasGoods(pPk, address, SynthesizeVO, Integer
				.parseInt(num));
		logger.info("原材料是否足够: " + result);
		String[] results = result.split(",");
		if (Integer.valueOf(results[0]) == -1)
		{
			String resultWml = results[1];
			request.setAttribute("display", resultWml);
			return mapping.findForward("display");
		}
		else
			if (Integer.valueOf(results[0]) == 1)
			{
				// 判断是否有足够的空间来存放装备道具或金钱
				String hasWareSpare = ss.getHasWareSpare(pPk, SynthesizeVO,
						address, Integer.parseInt(num));
				logger.info("是否有足够空格数 :" + hasWareSpare);
				String hasWareSpares = hasWareSpare.split(",")[0];
				if (Integer.valueOf(hasWareSpares) == 1)
				{
					String resultWml = ss.exchangeGoods(pPk, SynthesizeVO,
							address, Integer.parseInt(num));
					LiveSkillService lss = new LiveSkillService();
					int sleight = ss.getSynthesizeSleight(s_id)
							* Integer.parseInt(num);
					int sk_group = lss.getSkillGroup(s_type);
					lss.updatePlayerSleight(Integer.parseInt(pPk), sk_group,
							sleight, maxsleight);
					request.setAttribute("display", resultWml);
					return mapping.findForward("display");
				}
				else
				{
					String resultWml = hasWareSpare.split(",")[1];
					request.setAttribute("display", resultWml);
					return mapping.findForward("display");
				}
			}

		return mapping.findForward("display");
	}

}
