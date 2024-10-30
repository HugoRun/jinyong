package com.lw.action.menpaicontest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.player.RoleService;
import com.lw.dao.menpaicontest.MenpaiContestDAO;
import com.lw.service.menpaicontest.MenpaiContestService;
import com.lw.vo.menpaicontest.MenpaiContestPlayerVO;

public class MenpaiContestAction extends DispatchAction
{
	public ActionForward rank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		MenuService ms = new MenuService();
		OperateMenuVO vo = new OperateMenuVO();
		int pageNo = 0;
		String menpai = "";
		if (menu_id == null || menu_id.equals("") || menu_id.equals("null"))
		{
			request.setAttribute("display", "请联系GM!");
			return mapping.findForward("display");
		}
		else
		{
			vo = ms.getMenuById(Integer.parseInt(menu_id));
		}
		if (request.getParameter("pageNo") != null
				&& request.getParameter("pageNo").equals("")
				&& request.getParameter("pageNo").equals("null"))
		{
			String page_no = request.getParameter("pageNo");
			pageNo = Integer.parseInt(page_no);
		}
		String p_type = vo.getMenuOperate1();
		if (p_type.equals("1"))
		{
			menpai = "明教";
		}
		if (p_type.equals("2"))
		{
			menpai = "丐帮";
		}
		if (p_type.equals("3"))
		{
			menpai = "少林";
		}
		MenpaiContestService menpaiContestService = new MenpaiContestService();
		int num = menpaiContestService.selectPlayerRankDataNum(p_type);
		int pagenum = num / 7 + (num % 7 == 0 ? 0 : 1);
		List<MenpaiContestPlayerVO> list = menpaiContestService
				.getPlayerRankList(pageNo, p_type);

		request.setAttribute("list", list);
		request.setAttribute("menpai", menpai);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("pagenum", pagenum);
		return mapping.findForward("menpai_rank_display");
	}

	public ActionForward get(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		MenuService ms = new MenuService();
		OperateMenuVO ovo = new OperateMenuVO();
		String display = "";
		if (menu_id == null || menu_id.equals("") || menu_id.equals("null"))
		{
			display = "数据错误请联系GM!";
		}
		else
		{
			ovo = ms.getMenuById(Integer.parseInt(menu_id));
			String p_type_bak = ovo.getMenuOperate1();
			RoleService roleService = new RoleService();
			RoleEntity role_info = roleService.getRoleInfoBySession(request
					.getSession());
			MenpaiContestDAO dao = new MenpaiContestDAO();
			MenpaiContestPlayerVO vo = dao.selectPlayerData(role_info
					.getBasicInfo().getPPk());
			if (vo != null)
			{
				MenpaiContestService menpaiContestService = new MenpaiContestService();
				if (vo.getP_type() != 0 && p_type_bak.equals(vo.getP_type()+""))
				{
					if (vo.getWin_state() == 3)
					{
						int x = menpaiContestService.updatePlayerGetPrizeData(
								role_info, vo.getP_type());
						if (x != -1)
						{
							dao.updatePlayerRankState(role_info.getBasicInfo()
									.getPPk(), 5);
							display = "恭喜您获得大弟子奖励!";
						}
						else
						{
							display = "请您整理包裹后再来领取!";
						}
					}
					else
					{
						display = "您没有领取奖励的资格,或者您已经领取过奖励!";
					}

				}
				else
				{
					display = "门派不符!";
				}
			}
			else
			{
				display = "数据错误请联系GM!";
			}
		}
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}
}
