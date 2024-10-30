package com.ls.web.action.map;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.guaji.service.GuajiService;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.organize.faction.FactionRecruit;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.player.PlayerState;
import com.ls.web.action.ActionBase;
import com.ls.web.service.instance.InstanceService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.npc.RefurbishService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.wml.main.MainPageWmlService;
import com.pm.service.horta.HortaService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pm.vo.horta.HortaVO;
import com.pm.vo.sysInfo.SystemInfoVO;

/**
 * ���ܣ�����action
 * 
 * @author ls Apr 19, 2009 1:27:01 PM
 */
public class SceneAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		int p_pk = roleInfo.getPPk();
		GuajiService gs = new GuajiService();
		try
		{
			if (gs.findByPpk(p_pk) != null)
			{
				return mapping.findForward("auto");
			}
		}
		catch (SQLException e)
		{

			logger.error(e.getMessage());
		}
		String hint = (String) request.getAttribute("hint");
		String mountsHint = (String) request.getAttribute("mountsHint");
		String isRefurbish = request.getParameter("isRefurbish");// �Ƿ�ˢ�£��ǿձ�ʾˢ��
		String way = request.getParameter("way");// ���߷���
		RoomService roomService = new RoomService();
		PlayerService playerServie = new PlayerService();

		int old_scene_id = Integer.parseInt(roleInfo.getBasicInfo().getSceneId());
		int new_scene_id = roomService.getSceneByWay(old_scene_id, way);

		playerServie.updateSceneAndView(p_pk, new_scene_id);// ������Ұ��scene_id

		NpcService npcService = new NpcService();
		RefurbishService refurbishService = new RefurbishService();

		if (isRefurbish != null)// ˢ��npc
		{
			refurbishService.refurbishNPC(p_pk, new_scene_id);
		}

		if (npcService.isHaveAttackNPC(roleInfo))// �ж��Ƿ�������������npc
		{
			return mapping.findForward("fight_scene");// ��ת��ս��ҳ��
		}

		roleInfo.getStateInfo().setCurState(PlayerState.GENERAL);// ��λ���״̬

		SystemInfoService systemInfo = new SystemInfoService();
		MenuService menuService = new MenuService();
		MainPageWmlService mainPageWmlService = new MainPageWmlService();

		String intimateHint = mainPageWmlService.getIntimateHintWml(roleInfo);// ����С��ʿ
		String scene_walk_info = mainPageWmlService.getWalkWml(roleInfo,request, response);// �ߵ�ͼ�Ľű�
		String mail_hint = mainPageWmlService.getMailHint(roleInfo);// ���ʼ���ʾ
		/*********���µĽ�������********/
		HortaService hortaService = new HortaService();
		List<HortaVO> horList = hortaService.getMainList();
		String hortHint=hortaService.checkHasNew(horList,roleInfo);
		/*******************************/
		List npcs = npcService.getCurrentNPCs(p_pk, new_scene_id);// �õ�ˢ�³��ķ���������npc�б�

		List<SystemInfoVO> sysInfolist = systemInfo.getSystemInfoByPPk(roleInfo);// ϵͳ��Ϣ

		String player_list_str = playerServie.getPlayerListStrByScene(roleInfo);// ��Ұ�ڵ�����б�

		/** *******ˢ�²˵�******** */
		List<OperateMenuVO> menus = menuService.menuRefurbish(roleInfo);

		InstanceService is = new InstanceService();
		// ������Ϣ��ʾ
		String tianguanhint = is.getTianGuanDisplay(roleInfo);

		request.setAttribute("hint", hint);
		request.setAttribute("mountsHint", mountsHint);
		request.setAttribute("tianguanhint", tianguanhint);// ������ʾ��Ϣ
		request.setAttribute("intimateHint", intimateHint);// С��ʿ
		request.setAttribute("fRecruit", FactionRecruit.getInstance());// ������ļ��Ϣ
		request.setAttribute("mail_hint", mail_hint);
		request.setAttribute("hortHint",hortHint);
		request.setAttribute("chatHint", request.getParameter("chatHint"));
		request.setAttribute("chatChannel", request.getParameter("chatChannel"));

		request.setAttribute("sysInfo", sysInfolist);// ϵͳ��Ϣ
		request.setAttribute("player_list_str", player_list_str);// ����б�
		request.setAttribute("menus", menus);// �˵��б�
		request.setAttribute("npcs", npcs);// �����б�

		request.setAttribute("roleInfo", roleInfo);
		request.setAttribute("settingInfo", roleInfo.getSettingInfo());// ��ɫ����
		request.setAttribute("scene", roleInfo.getBasicInfo().getSceneInfo());
		request.setAttribute("scene_walk_info", scene_walk_info);
		request.setAttribute("new_scene_id", new_scene_id);
		return mapping.findForward("success");
	}

}
