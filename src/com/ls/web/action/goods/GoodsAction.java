package com.ls.web.action.goods;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.dao.task.UTaskDAO;
import com.ls.ben.vo.info.attack.DropExpMoneyVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.web.service.cooperate.sky.BillService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;
import com.lw.service.player.PlayerPropGroupService;
import com.web.service.task.TaskPageService;

/**
 * ������Ʒʰȡ
 */
public class GoodsAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");

	// ������Ʒ
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String drop_pk = request.getParameter("drop_pk");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		String p_pk = roleInfo.getBasicInfo().getPPk() + "";

		if (p_pk == null || drop_pk == null)
		{
			// //System.out.println("�������ݴ���");
		}

		
		String goods_name =roleInfo.getDropSet().getItemNameById(Integer.parseInt(drop_pk));
		
		int DropGoodPk = roleInfo.getDropSet().getItemIdById(Integer.parseInt(drop_pk));

		GoodsService goodsService = new GoodsService();
		int result = goodsService.pickupGoods(Integer.parseInt(p_pk), Integer
				.parseInt(drop_pk), 1,response,request);
		String hint = "";
		if (result != -1)
		{
			logger.info("�ɹ��������");
			TaskPageService taskPageService = new TaskPageService();
			List<DropGoodsVO> dropgoods = roleInfo.getDropSet().getList();
			if (roleInfo.getTaskInfo().getTaskId() != -1
					&& roleInfo.getTaskInfo().getTaskPoint() != null
					&& !roleInfo.getTaskInfo().getTaskPoint().equals("")
					&& roleInfo.getTaskInfo().getTaskMenu() != -1)
			{
				/** ����ID */
				int tPk = roleInfo.getTaskInfo().getTaskId();
				/** �����м��DI */
				String taskPorint = roleInfo.getTaskInfo().getTaskPoint();
				/** �˵�ID */
				UTaskDAO uTaskDAO = new UTaskDAO();
				int menuID = roleInfo.getTaskInfo().getTaskMenu();
				String tPoint[] = taskPorint.split(",");
				String ddc11 = "";
				for (int i = 0; i < tPoint.length; i++)
				{
					String ddc = "";
					if (Integer.parseInt(tPoint[i]) != menuID)
					{
						ddc = tPoint[i] + ",";
						ddc11 += ddc;
					}
				}
				roleInfo.getTaskInfo().setTaskPoint(ddc11);
				CurTaskInfo curTaskInfo = (CurTaskInfo) roleInfo.getTaskInfo()
						.getCurTaskList().getById(tPk + "");
				curTaskInfo.updatePoint(ddc11);
			}
			String ss = taskPageService.Find(Integer.parseInt(p_pk),DropGoodPk, 1, roleInfo);
			request.setAttribute("ss", ss);
			request.setAttribute("goods_name", goods_name);
			request.setAttribute("dropgoods", dropgoods);
			return mapping.findForward("list");
		}
		else
		{
			hint = "���İ�����������,�޷�ʰȡ.�Ƿ����������.���ڴ��۳���!";
			request.setAttribute("display", hint);
			return mapping.findForward("hint");
		}
	}

	// ������Ʒ�б���Ʒ
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

		// �õ�������
		List<DropGoodsVO> dropgoods = roleInfo.getDropSet().getList();

		request.setAttribute("dropgoods", dropgoods);
		return mapping.findForward("list");
	}

	// ����,���������
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String p_pk = roleInfo.getBasicInfo().getPPk() + "";
		if (p_pk == null)
		{
			// //System.out.println("�������ݴ���");
		}

		roleInfo.getDropSet().clearDropItem();
		try
		{
			if (roleInfo.getTaskInfo().getTaskId() != -1
					&& roleInfo.getTaskInfo().getTaskPoint() != null
					&& !roleInfo.getTaskInfo().getTaskPoint().equals("")
					&& roleInfo.getTaskInfo().getTaskMenu() != -1)
			{
				int tPk = roleInfo.getTaskInfo().getTaskId();// ����ID
				String taskPorint = roleInfo.getTaskInfo().getTaskPoint();// �����м��DI
				UTaskDAO uTaskDAO = new UTaskDAO();
				int menuID = roleInfo.getTaskInfo().getTaskMenu();// �˵�ID
				String tPoint[] = taskPorint.split(",");
				String ddc11 = "";
				for (int i = 0; i < tPoint.length; i++)
				{
					String ddc = "";
					if (Integer.parseInt(tPoint[i]) != menuID)
					{
						ddc = tPoint[i] + ",";
						ddc11 += ddc;
					}
				}
				roleInfo.getTaskInfo().setTaskPoint(ddc11);
				CurTaskInfo curTaskInfo = (CurTaskInfo) roleInfo.getTaskInfo()
						.getCurTaskList().getById(tPk + "");
				curTaskInfo.updatePoint(ddc11);
			}
			request.getRequestDispatcher(
					"/pubbuckaction.do?chair=" + request.getParameter("chair"))
					.forward(request, response);

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

	// ��Ʒ�б�
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());


		DropExpMoneyVO dropExpMoney = roleInfo.getDropSet().getExpAndMoney();
		roleInfo.getDropSet().clearExpAndMoney();

		// �õ�������
		List<DropGoodsVO> dropgoods =  roleInfo.getDropSet().getList();

		request.setAttribute("dropgoods", dropgoods);
		request.setAttribute("dropExpMoney", dropExpMoney);
		return mapping.findForward("box_list");
	}

	// ȫ��ʰȡ������Ʒ
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();

		if (roleInfo == null)
		{
			logger.info("�������ݴ���");
		}

		GoodsService goodsService = new GoodsService();
		int result = goodsService.pickupAllGoods(pPk);

		String hint = "";

		if (result != -1)
		{
			logger.info("�ɹ��������");
			List<DropGoodsVO> dropgoods = roleInfo.getDropSet().getList();
			if (dropgoods.size() == 0)
			{

				if (roleInfo.getTaskInfo().getTaskId() != -1
						&& roleInfo.getTaskInfo().getTaskPoint() != null
						&& !roleInfo.getTaskInfo().getTaskPoint().equals("")
						&& roleInfo.getTaskInfo().getTaskMenu() != -1)
				{
					int tPk = roleInfo.getTaskInfo().getTaskId();// ����ID
					String taskPorint = roleInfo.getTaskInfo().getTaskPoint();// �����м��DI
					int menuID = roleInfo.getTaskInfo().getTaskMenu();// �˵�ID
					UTaskDAO uTaskDAO = new UTaskDAO();
					String tPoint[] = taskPorint.split(",");
					String ddc11 = "";
					for (int i = 0; i < tPoint.length; i++)
					{
						String ddc = "";
						if (Integer.parseInt(tPoint[i]) != menuID)
						{
							ddc = tPoint[i] + ",";
							ddc11 += ddc;
						}
					}
					roleInfo.getTaskInfo().setTaskPoint(ddc11);
					CurTaskInfo curTaskInfo = (CurTaskInfo) roleInfo
							.getTaskInfo().getCurTaskList().getById(tPk + "");
					curTaskInfo.updatePoint(ddc11);
				}
				try
				{
					request.getRequestDispatcher(
							"/pubbuckaction.do?chair="
									+ request.getParameter("chair")).forward(
							request, response);
				}
				catch (ServletException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

			return mapping.findForward("list");
		}
		else
		{
			hint = "���İ�����������,�޷�ʰȡ.�Ƿ����������.���ڴ��۳���!";
			request.setAttribute("display", hint);
			return mapping.findForward("hint");
		}

	}

	// ��ȡʱ�����Ӱ���
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		PlayerPropGroupService playerPropGroupService = new PlayerPropGroupService();
		String display = playerPropGroupService
				.getPlayerPropGroupInfo(roleInfo);
		if (display == null)
		{
			request.setAttribute("display", "������Ʒ��λ�Ѿ�Ϊ�����!<br/>");
			return mapping.findForward("display");
		}
		else
		{
			request.setAttribute("display", display);
			return mapping.findForward("submit");
		}
	}

	// �����������
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		PlayerPropGroupService playerPropGroupService = new PlayerPropGroupService();
		String display = null;
		if(GameConfig.getChannelId()==Channel.TELE)
		{
			display=playerPropGroupService.buyPropGroup(request, roleInfo);
		}
		else
		{
			display=playerPropGroupService.buyPropGroup(roleInfo);
		}
		if (display == null)
		{
			if(GameConfig.getChannelId() == Channel.SKY){
				request.setAttribute("uPk", roleInfo.getBasicInfo().getUPk());
				return mapping.findForward("chongzhi");
			}else{
				request.setAttribute("display", "����"+GameConfig.getYuanbaoName()+"��������,����������ʰȡ!");
				return mapping.findForward("display");
			}
		}
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}

	// ��ֵ���������
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String uPk = (String) request.getSession().getAttribute("uPk");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String kbamt_str = request.getParameter("kbamt");// �û��ύ�۷ѽ��

		ValidateService validateService = new ValidateService();
		String hint = validateService
				.validateNonZeroNegativeIntegers(kbamt_str);

		if (hint != null)
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("chongzhi");
		}

		int kbamt = Integer.parseInt(kbamt_str.trim());

		if (kbamt > 500)
		{
			hint = "ÿ�ζһ�KB���������ó���500KB,�ɽ��ж�ζһ�";
			request.setAttribute("hint", hint);
			return mapping.findForward("chongzhidisplay");
		}

		HttpSession session = request.getSession();
		String ssid = (String) session.getAttribute("ssid");
		String skyid = (String) session.getAttribute("skyid");

		BillService billService = new BillService();
		Map<String, String> pay_results = billService.pay(uPk, ssid, skyid,
				kbamt + "",roleInfo.getBasicInfo().getPPk());

		String result = pay_results.get("result");
		if (result != null && result.equals("0"))
		{
			EconomyService economyService = new EconomyService();
			long yuanbao = economyService.getYuanbao(Integer.parseInt(uPk));
			// �һ��ɹ�,�������50����Ԫ������Ŀǰ�����С�Ԫ������120��
			hint = "�һ��ɹ�,�������" + kbamt + "����"+GameConfig.getYuanbaoName()+"��,Ŀǰ�����С�"+GameConfig.getYuanbaoName()+"����" + yuanbao + "��";
			PlayerPropGroupService playerPropGroupService = new PlayerPropGroupService();
			String display = null;
			if(GameConfig.getChannelId()==Channel.TELE)
			{
				display=playerPropGroupService.buyPropGroup(request, roleInfo);
			}
			else
			{
				display=playerPropGroupService.buyPropGroup(roleInfo);
			}
			if(display == null){
				display = "��ֵ�ɹ�,����"+GameConfig.getYuanbaoName()+"���㹺������������ֵ!";
			}
			request.setAttribute("hint", display);
			return mapping.findForward("chongzhidisplay");
		}
		else
		{
			hint = billService.getPayHintByResult(pay_results);
			request.setAttribute("hint", hint);
			return mapping.findForward("chongzhidisplay");
		}
	}
}