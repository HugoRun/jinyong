package com.lw.action.menpaicontest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.player.RoleService;
import com.lw.dao.menpaicontest.MenpaiContestDAO;
import com.lw.vo.menpaicontest.MenpaiNpcVO;
import com.web.jieyi.util.Constant;

public class MenpaiNpcAction extends DispatchAction
{
	// ����NPC
	public ActionForward attack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		try
		{
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
			String menpai = vo.getMenuOperate1();
			if(roleInfo.getBasicInfo().getGrade() < 80){
				request.setAttribute("display", "���ĵȼ�����80��������ս!");
				return mapping.findForward("display");
			}

			int group_member_num = 0;// ������ڶ�������
			GroupService groupService = new GroupService();
			group_member_num = groupService.getGroupNumByMember(roleInfo
					.getBasicInfo().getPPk());
			int ppk = Constant.MENPAINPC.get(1);
			if(ppk != 0){
				RoleEntity role = roleService.getRoleInfoById(ppk+"");
				if(role!=null){
					if(ppk != roleInfo.getBasicInfo().getPPk()){
						request.setAttribute("display", "�����������սNPC!");
						return mapping.findForward("display");
					}
				}else{
					Constant.MENPAINPC.put(1, 0);
				}
			}
			if (group_member_num > 1)
			{
				request.setAttribute("display", "���״̬���ܽ�����ս!");
				return mapping.findForward("display");
			}

			String[] prop = vo.getMenuOperate2().split(",");
			if (!prop[0].equals("0"))
			{
				PropVO propVO = PropCache.getPropById(Integer.parseInt(prop[0]));
				PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
				int tatol_prop_num = propGroupDao.getPropNumByByPropID(roleInfo
						.getBasicInfo().getPPk(), Integer.parseInt(prop[0]));
				if (tatol_prop_num == 0)
				{
					request.setAttribute("display", "������û��" + propVO.getPropName()
							+ ",���Ե��̳���ȥ����!");
					return mapping.findForward("display");
				}
				else
				{
					if (tatol_prop_num < Integer.parseInt(prop[1]))
					{
						request.setAttribute("display", "�����ϵ�"
								+ propVO.getPropName() + "��������,����ɴ���ػ���!");
						return mapping.findForward("display");
					}
					else
					{
						if (!menpai.equals(roleInfo.getBasicInfo().getPRace()+ ""))
						{
							request.setAttribute("display", "���ɲ���,������ս!");
							return mapping.findForward("display");
						}
						else
						{
							MenpaiContestDAO dao = new MenpaiContestDAO();
							MenpaiNpcVO menpaiNpcVO = dao.getPlayerMenpaiNpc(
									Integer.parseInt(menpai), vo.getMenuOperate4());
							Constant.MENPAINPC.put(1, roleInfo.getBasicInfo().getPPk());
							roleInfo.getBasicInfo().setMenpainpcstate(vo.getId());
							roleInfo.getBasicInfo().setMenpainpcid(vo.getMenuOperate4()+1);
							int scene_id = menpaiNpcVO.getScence_id();
							roleInfo.getBasicInfo().updateSceneId(scene_id + "");
							GoodsService gs = new GoodsService();
							gs.removeProps(roleInfo.getPPk(),Integer.parseInt(prop[0]), Integer.parseInt(prop[1]),GameLogManager.R_USE);
							return mapping.findForward("go");
						}
					}
				}
			}
			else
			{
				if (!menpai.equals(roleInfo.getBasicInfo().getPRace()+ ""))
				{
					request.setAttribute("display", "���ɲ���,������ս!");
					return mapping.findForward("display");
				}
				else
				{
					MenpaiContestDAO dao = new MenpaiContestDAO();
					MenpaiNpcVO menpaiNpcVO = dao.getPlayerMenpaiNpc(Integer
							.parseInt(menpai), vo.getMenuOperate4());
					int scene_id = menpaiNpcVO.getScence_id();
					roleInfo.getBasicInfo().updateSceneId(scene_id + "");
					Constant.MENPAINPC.put(1, roleInfo.getBasicInfo().getPPk());
					roleInfo.getBasicInfo().setMenpainpcstate(vo.getId());
					roleInfo.getBasicInfo().setMenpainpcid(vo.getMenuOperate4()+1);
					roleInfo.getBasicInfo().setMenpainpcstate(vo.getId());
					return mapping.findForward("go");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("display", "�������ڱչ�����,����������!");
			return mapping.findForward("display");
		}
	}

	// �鿴����
	public ActionForward rank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		try
		{
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
			String menpai = vo.getMenuOperate1();
			// ���� ȡ��������Ϣ
			request.setAttribute("display", "������δ����!");
			return mapping.findForward("display");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			request.setAttribute("display", "������δ����!");
			return mapping.findForward("display");
		}
	}
}
