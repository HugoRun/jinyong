package com.pm.action.untangle;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Equip;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.player.RoleService;
import com.lw.service.instance.InstanceOutService;
import com.lw.vo.instance.InstanceOutVO;
import com.pm.dao.untangle.UntangLeDao;

public class UnTangleAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.action");

	// 查看当前玩家江湖排名
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		UntangLeDao untangdao = new UntangLeDao();
		// 返回该界面
		request.getSession().setAttribute("backtype", "n1");
		List<PartInfoVO> list = untangdao.getPaiMingList();
		request.setAttribute("paiminglist", list);
		request.setAttribute("unType", "江湖排名<br/>玩家所获得经验和荣誉的排行");
		request.setAttribute("number", "n1");
		return mapping.findForward("paiminglist");
	}

	// 查看当前玩家的财富排名
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		UntangLeDao untangdao = new UntangLeDao();
		// 返回该界面
		request.getSession().setAttribute("backtype", "n2");
		List<PartInfoVO> list = untangdao.getMoneyPaiMingList();
		request.setAttribute("paiminglist", list);
		request.setAttribute("unType", "财富排名");
		request.setAttribute("number", "n2");
		return mapping.findForward("paiminglist");
	}

	// 查看当前玩家的财富排名
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("number", request.getParameter("number"));
		return mapping.findForward("paimingview");
	}

	// 查看神兵榜排名
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.getSession().setAttribute("backtype", "n4");
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		List equip_list = playerEquipDao.getRankList(Equip.WEAPON);
		request.setAttribute("number", "n4");
		request.setAttribute("equip_list", equip_list);
		return mapping.findForward("arm_ranking");
	}

	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService es = new EquipDisplayService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String display = es.getEquipDisplay(roleInfo, equip,true);

		request.setAttribute("display", display);
		request.setAttribute("number", request.getParameter("number"));
		return mapping.findForward("arm_view");
	}

	/** 副本信息 */

	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		InstanceOutService is = new InstanceOutService();
		List<InstanceOutVO> list = is.getInstanceOut();
		request.setAttribute("outlist", list);
		return mapping.findForward("instance_view");
	}

	/** 杀人排名* */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		UntangLeDao untangdao = new UntangLeDao();
		// 返回该界面
		request.getSession().setAttribute("backtype", "n7");
		List<PartInfoVO> list = untangdao.getKillRank();
		request.setAttribute("paiminglist", list);
		request.setAttribute("unType", "杀人榜<br/>十步杀一人,千里不留行!此榜记录杀死玩家数量的多少.");
		request.setAttribute("number", "n7");
		return mapping.findForward("paiminglist");
	}
}
