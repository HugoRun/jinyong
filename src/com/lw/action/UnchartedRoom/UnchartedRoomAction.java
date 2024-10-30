package com.lw.action.UnchartedRoom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.player.RoleService;
import com.lw.service.UnchartedRoom.UnchartedRoomService;
import com.pm.service.systemInfo.SystemInfoService;

public class UnchartedRoomAction extends DispatchAction
{

	// ��ҵ����ȡ���� Ȼ���ж�����Ƿ����ʸ�����ȡ�Ľ���
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		MenuService ms = new MenuService();
		OperateMenuVO vo = new OperateMenuVO();
		if (menu_id == null || menu_id.equals("") || menu_id.equals("null"))
		{
			request.setAttribute("display", "����ϵGM!");
			return mapping.findForward("display");
		}
		else
		{
			vo = ms.getMenuById(Integer.parseInt(menu_id));
		}

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		UnchartedRoomService rs = new UnchartedRoomService();
		String prop_id = vo.getMenuOperate1();
		if (prop_id == null || prop_id.equals("") || prop_id.equals("null"))
		{
			request.setAttribute("display", "����ϵGM��ȡ������Ϣ!");
			return mapping.findForward("display");
		}
		boolean have = rs.ifPlayerHaveGetPrize();
		if (have == true)
		{
			// ��ȡ�����˳�
			GoodsService gs = new GoodsService();
			gs.putGoodsToWrap(roleInfo.getBasicInfo().getPPk(), Integer
					.parseInt(prop_id), 4, 1);
			rs.updateOfflinePlayerUnchartedRoomState(roleInfo);
			SystemInfoService systemInfoService = new SystemInfoService();
			systemInfoService.insertSystemInfoBySystem(roleInfo.getBasicInfo().getName()+"������ѹȺ��,���ջ�����ؾ��еı���!");
			request.setAttribute("display", "��ϲ��������ؾ��еı���!");
			return mapping.findForward("display");
		}
		else
		{
			// ����ȡ����
			request.setAttribute("display", "�ؾ��л�������̽����,���޷���ñ���!");
			return mapping.findForward("display");
		}
	}
}
