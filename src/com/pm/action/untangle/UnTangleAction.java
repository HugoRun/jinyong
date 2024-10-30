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

	// �鿴��ǰ��ҽ�������
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		UntangLeDao untangdao = new UntangLeDao();
		// ���ظý���
		request.getSession().setAttribute("backtype", "n1");
		List<PartInfoVO> list = untangdao.getPaiMingList();
		request.setAttribute("paiminglist", list);
		request.setAttribute("unType", "��������<br/>�������þ��������������");
		request.setAttribute("number", "n1");
		return mapping.findForward("paiminglist");
	}

	// �鿴��ǰ��ҵĲƸ�����
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		UntangLeDao untangdao = new UntangLeDao();
		// ���ظý���
		request.getSession().setAttribute("backtype", "n2");
		List<PartInfoVO> list = untangdao.getMoneyPaiMingList();
		request.setAttribute("paiminglist", list);
		request.setAttribute("unType", "�Ƹ�����");
		request.setAttribute("number", "n2");
		return mapping.findForward("paiminglist");
	}

	// �鿴��ǰ��ҵĲƸ�����
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("number", request.getParameter("number"));
		return mapping.findForward("paimingview");
	}

	// �鿴���������
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

	/** ������Ϣ */

	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		InstanceOutService is = new InstanceOutService();
		List<InstanceOutVO> list = is.getInstanceOut();
		request.setAttribute("outlist", list);
		return mapping.findForward("instance_view");
	}

	/** ɱ������* */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		UntangLeDao untangdao = new UntangLeDao();
		// ���ظý���
		request.getSession().setAttribute("backtype", "n7");
		List<PartInfoVO> list = untangdao.getKillRank();
		request.setAttribute("paiminglist", list);
		request.setAttribute("unType", "ɱ�˰�<br/>ʮ��ɱһ��,ǧ�ﲻ����!�˰��¼ɱ����������Ķ���.");
		request.setAttribute("number", "n7");
		return mapping.findForward("paiminglist");
	}
}
