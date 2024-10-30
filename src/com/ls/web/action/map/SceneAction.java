package com.ls.web.action.map;

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
import com.ls.web.service.room.RoomService;
import com.ls.web.service.wml.main.MainPageWmlService;
import com.pm.service.horta.HortaService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pm.vo.horta.HortaVO;
import com.pm.vo.sysInfo.SystemInfoVO;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * 功能：场景action
 *
 * @author ls Apr 19, 2009 1:27:01 PM
 */
public class SceneAction extends ActionBase {
    Logger logger = Logger.getLogger("log.action");

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleEntity roleInfo = this.getRoleEntity(request);
        int p_pk = roleInfo.getPPk();
        GuajiService gs = new GuajiService();
        try {
            if (gs.findByPpk(p_pk) != null) {
                return mapping.findForward("auto");
            }
        } catch (SQLException e) {

            logger.error(e.getMessage());
        }
        String hint = (String) request.getAttribute("hint");
        String mountsHint = (String) request.getAttribute("mountsHint");
        String isRefurbish = request.getParameter("isRefurbish");// 是否刷新，非空表示刷新
        String way = request.getParameter("way");// 行走方向
        RoomService roomService = new RoomService();
        PlayerService playerServie = new PlayerService();

        int old_scene_id = Integer.parseInt(roleInfo.getBasicInfo().getSceneId());
        int new_scene_id = roomService.getSceneByWay(old_scene_id, way);

        playerServie.updateSceneAndView(p_pk, new_scene_id);// 更新视野和scene_id

        NpcService npcService = new NpcService();
        RefurbishService refurbishService = new RefurbishService();

        if (isRefurbish != null)// 刷新npc
        {
            refurbishService.refurbishNPC(p_pk, new_scene_id);
        }

        if (npcService.isHaveAttackNPC(roleInfo))// 判断是否有主动攻击的npc
        {
            return mapping.findForward("fight_scene");// 跳转到战斗页面
        }

        roleInfo.getStateInfo().setCurState(PlayerState.GENERAL);// 复位玩家状态

        SystemInfoService systemInfo = new SystemInfoService();
        MenuService menuService = new MenuService();
        MainPageWmlService mainPageWmlService = new MainPageWmlService();

        String intimateHint = mainPageWmlService.getIntimateHintWml(roleInfo);// 武林小贴士
        String scene_walk_info = mainPageWmlService.getWalkWml(roleInfo, request, response);// 走地图的脚本
        String mail_hint = mainPageWmlService.getMailHint(roleInfo);// 新邮件提示
        /*********有新的奖励提醒********/
        HortaService hortaService = new HortaService();
        List<HortaVO> horList = hortaService.getMainList();
        String hortHint = hortaService.checkHasNew(horList, roleInfo);
        /*******************************/
        List npcs = npcService.getCurrentNPCs(p_pk, new_scene_id);// 得到刷新出的非主动攻击npc列表

        List<SystemInfoVO> sysInfolist = systemInfo.getSystemInfoByPPk(roleInfo);// 系统消息

        String player_list_str = playerServie.getPlayerListStrByScene(roleInfo);// 视野内的玩家列表

        /** *******刷新菜单******** */
        List<OperateMenuVO> menus = menuService.menuRefurbish(roleInfo);

        InstanceService is = new InstanceService();
        // 副本信息显示
        String tianguanhint = is.getTianGuanDisplay(roleInfo);

        request.setAttribute("hint", hint);
        request.setAttribute("mountsHint", mountsHint);
        request.setAttribute("tianguanhint", tianguanhint);// 副本提示信息
        request.setAttribute("intimateHint", intimateHint);// 小贴士
        request.setAttribute("fRecruit", FactionRecruit.getInstance());// 帮派招募信息
        request.setAttribute("mail_hint", mail_hint);
        request.setAttribute("hortHint", hortHint);
        request.setAttribute("chatHint", request.getParameter("chatHint"));
        request.setAttribute("chatChannel", request.getParameter("chatChannel"));

        request.setAttribute("sysInfo", sysInfolist);// 系统消息
        request.setAttribute("player_list_str", player_list_str);// 玩家列表
        request.setAttribute("menus", menus);// 菜单列表
        request.setAttribute("npcs", npcs);// 怪物列表

        request.setAttribute("roleInfo", roleInfo);
        request.setAttribute("settingInfo", roleInfo.getSettingInfo());// 角色设置
        request.setAttribute("scene", roleInfo.getBasicInfo().getSceneInfo());
        request.setAttribute("scene_walk_info", scene_walk_info);
        request.setAttribute("new_scene_id", new_scene_id);
        return mapping.findForward("success");
    }

}
