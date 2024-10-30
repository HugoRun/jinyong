package com.dp.action.creditaction;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dp.dao.credit.CreditProce;
import com.dp.service.credit.CreaditService;
import com.dp.vo.credit.PlayerCreditVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;

public class CreditAction extends DispatchAction
{

	/***************************************************************************
	 * 查询玩家的角色所对应的声望
	 **************************************************************************/
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		List<PlayerCreditVO> pcvlist = null;
		CreaditService service = new CreditProce();
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		Integer ppk = roleInfo.getBasicInfo().getPPk();
		if (pcvlist == null)
		{
			pcvlist = service.getPlayerCredit(ppk);
		}
		String ppname = roleInfo.getBasicInfo().getName();
		request.setAttribute("ppname", ppname);
		request.setAttribute("pcvlist", pcvlist);
		return mapping.findForward("shengwangview");
	}

	/***************************************************************************
	 * 根据ID查询声望描述
	 **************************************************************************/
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		List<PlayerCreditVO> pcvlist = null;
		CreaditService service = new CreditProce();
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		Integer ppk = roleInfo.getBasicInfo().getPPk();
		if (pcvlist == null)
		{
			pcvlist = service.getPlayerCredit(ppk);
		}

		Integer pcid = Integer.parseInt(request.getParameter("cid"));
		if (pcvlist != null)
		{
			for (Iterator<PlayerCreditVO> iterator = pcvlist.iterator(); iterator
					.hasNext();)
			{
				PlayerCreditVO pcv = iterator.next();
				if (pcv.getPcid() == pcid || pcv.getPcid().equals(pcid))
				{
					request.setAttribute("pcvo", pcv);
					break;
				}
			}
		}
		else
		{
			PlayerCreditVO pcv = service.getPcvDisplay(pcid);
			request.setAttribute("pcvo", pcv);
		}
		return mapping.findForward("displayview");
	}
}
