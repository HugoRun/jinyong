package com.lw.action.tianguan;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.pm.service.systemInfo.SystemInfoService;

public class TianGuanAction extends DispatchAction
{

	public ActionForward in(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request
				.getSession());
		MenuService ms = new MenuService();
		OperateMenuVO vo = new OperateMenuVO();
		if (menu_id == null || menu_id.equals("") || menu_id.equals("null"))
		{
			request.setAttribute("display", "请联系GM!");
			return mapping.findForward("display");
		}
		else
		{
			vo = ms.getMenuById(Integer.parseInt(menu_id));
		}
		String add_time = vo.getMenuOperate1();
		String[] npc_id = vo.getMenuOperate2().split("-");
		String[] next_npc_id = vo.getMenuOperate3().split("-");
		int scence_id = vo.getMenuOperate4();

		
		int group_member_num = 0;//玩家所在队伍人数
		GroupService  groupService = new GroupService();
		group_member_num = groupService.getGroupNumByMember(role_info.getBasicInfo().getPPk());
		if(group_member_num > 1){
			request.setAttribute("display", "组队状态不能进入挑战!");
			return mapping.findForward("display");
		}
		// 给玩家计时开始
		if (add_time.equals("1"))
		{
			// 等级限制
			if (role_info.getBasicInfo().getGrade() < Integer
					.parseInt(npc_id[0]))
			{
				request.setAttribute("display", "您的等级太低,请达到相应的等级在进入挑战!");
				return mapping.findForward("display");
			}
			else
			{
				String[] prop = npc_id[1].split(",");
				if(prop[0].equals("0")){
					role_info.getBasicInfo().setTianguan_time(new Date().getTime());
					role_info.getBasicInfo().setTianguan_npc(next_npc_id[0]);
					role_info.getBasicInfo().setTianguan_kill_num(0);
					role_info.getBasicInfo().updateSceneId(scence_id + "");
					SystemInfoService infoService = new SystemInfoService();
					infoService.insertSystemInfoBySystem(role_info.getBasicInfo()
							.getPPk(), "您的挑战之旅现在开始计时!");
					return mapping.findForward("return");
				}else{
					PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
					int tatol_prop_num = propGroupDao.getPropNumByByPropID(
							role_info.getBasicInfo().getPPk(), Integer
									.parseInt(prop[0]));
					if (tatol_prop_num == 0)
					{
						request.setAttribute("display", "您身上没有英雄帖,可以到商城中去购买!");
						return mapping.findForward("display");
					}
					else
					{
						if (tatol_prop_num < Integer.parseInt(prop[1]))
						{
							request
									.setAttribute("display",
											"您身上的英雄帖数量不够,可以到商城中去购买!");
							return mapping.findForward("display");
						}else{
							GoodsService gs = new GoodsService();
							gs.removeProps(role_info.getPPk(), Integer.parseInt(prop[0]), Integer.parseInt(prop[1]),GameLogManager.R_USE);
							role_info.getBasicInfo().setTianguan_time(new Date().getTime());
							role_info.getBasicInfo().setTianguan_npc(next_npc_id[0]);
							role_info.getBasicInfo().setTianguan_kill_num(0);
							role_info.getBasicInfo().updateSceneId(scence_id + "");
							SystemInfoService infoService = new SystemInfoService();
							infoService.insertSystemInfoBySystem(role_info.getBasicInfo()
									.getPPk(), "您的挑战之旅现在开始计时!");
							return mapping.findForward("return");
						}
					}
				}
			}
		}

		String scene_id = role_info.getBasicInfo().getSceneId();
		SceneCache sc = new SceneCache();
		SceneVO sceneVO = sc.getById(scene_id);
		MapVO mapVO = sceneVO.getMap();
		String[] condition = mapVO.getMapSkill().split(",");
		int time = Integer.parseInt(condition[3]);
		long now = new Date().getTime();
		long time_now = (now - role_info.getBasicInfo().getTianguan_time()) / 1000;// 秒
		long time_out = time * 60 - time_now;
		if (time_out != 0 && time_out < 1)
		{
			RoomService rs = new RoomService();
			int resurrection_point = rs.getResurrectionPoint(role_info);
			role_info.getBasicInfo().updateSceneId(resurrection_point + "");
			request.setAttribute("display", "以你的实力而言,能到这里已经是奇迹了,回去好好磨练下再来吧!");
			return mapping.findForward("display");
		}
		else
		{
			if (role_info.getBasicInfo().getTianguan_npc().equals(npc_id[0]))
			{
				// 判断玩家 是否够杀敌数
				if (role_info.getBasicInfo().getTianguan_kill_num() >= Integer
						.parseInt(npc_id[1]))
				{
					if (add_time.equals("2"))
					{
						role_info.getBasicInfo().setTianguan_time(0);
						role_info.getBasicInfo().setTianguan_npc("");
						role_info.getBasicInfo().setTianguan_kill_num(0);
						role_info.getBasicInfo().updateSceneId(scence_id + "");
						request.setAttribute("display",
								"你居然通过了,看来这对你不算是挑战,下次还有更难的试炼等着你!");
						return mapping.findForward("display");
					}
					else
					{
						role_info.getBasicInfo()
								.setTianguan_npc(next_npc_id[0]);
						role_info.getBasicInfo().setTianguan_kill_num(0);
						role_info.getBasicInfo().updateSceneId(scence_id + "");
						SystemInfoService infoService = new SystemInfoService();
						infoService.insertSystemInfoBySystem(role_info
								.getBasicInfo().getPPk(),
								"你居然通过了,侥幸而已下一关就没这么幸运了!");
						return mapping.findForward("return");
					}
				}
				else
				{
					request.setAttribute("display", "你还没有杀够怪呢!想偷懒?");
					return mapping.findForward("display");
				}
			}
			else
			{
				request.setAttribute("display", "请联系GM!");
				return mapping.findForward("display");
			}
		}
	}
}
