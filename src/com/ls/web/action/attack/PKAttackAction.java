package com.ls.web.action.attack;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.vo.info.effect.PropUseEffect;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.Shortcut;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.ActionBase;
import com.ls.web.action.shortcut.AttackShortcutAction;
import com.ls.web.service.attack.AttackService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.prop.PropUseService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.skill.SkillService;
import com.ls.web.service.system.UMsgService;
import com.pm.service.systemInfo.SystemInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class PKAttackAction extends ActionBase {

    // 得到当前地点遇到的所有玩家列表
    public ActionForward n1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleEntity roleInfo = this.getRoleEntity(request);
        PlayerService playerService = new PlayerService();

        List<RoleEntity> players = playerService.getPlayersByView(roleInfo);
        playerService.addUserStateFlag(players);


        QueryPage item_page = new QueryPage(this.getPageNo(request), players);
        item_page.setURL(response, "/pk.do?cmd=n1");

        request.setAttribute("scene", roleInfo.getBasicInfo().getSceneInfo());
        request.setAttribute("item_page", item_page);
        request.setAttribute("me", roleInfo);
        return mapping.findForward("playerlist");
    }

    /*
    public void qianmianDoing(HttpServletRequest request, Fighter win,
            Fighter fail, SceneVO scene, HttpServletResponse response)
    {

        if (win != null && fail != null)
        {
            if (win.getPPk() == LangjunConstants.LANGJUN_PPK
                    || win.getPPk() == LangjunConstants.XIANHAI_LANGJUN)
            {
                LangjunConstants.NAME_SET.add(win.getPPk());
            }
            if (fail.getPPk() == LangjunConstants.LANGJUN_PPK
                    || fail.getPPk() == LangjunConstants.XIANHAI_LANGJUN)
            {
                LangjunConstants.NAME_SET.add(fail.getPPk());
            }
        }
        if (win != null && win.getPPk() == LangjunConstants.LANGJUN_PPK
                || win.getPPk() == LangjunConstants.XIANHAI_LANGJUN)
        {
            RoleEntity win1 = new RoleCache()
                    .getByPpk(LangjunConstants.LANGJUN_PPK);
            if (win1 != null)
            {
                win1.getStateInfo().setCurState(PlayerState.GENERAL);
            }
        }
        if (win != null && fail != null
                && fail.getPPk() == LangjunConstants.LANGJUN_PPK)
        {
            RoleEntity failre = new RoleService().getRoleInfoById(fail.getPPk()
                    + "");
            new SystemInfoService().insertSystemInfoBySystem("某位大侠于"
                    + scene.getMap().getMapName() + "的" + scene.getSceneName()
                    + "将【千面郎君】暂时击退，【千面郎君】已经从"
                    + failre.getBasicInfo().getRealName() + "的身上离开，再次藏匿到人群之中！");
            MailInfoService mailInfoService = new MailInfoService();
            UMsgService uMsgService = new UMsgService();
            // 如果失败的是千面郎君
            // 胜利的人成为千面郎君
            new LangjunUtil().becameLangjun(request, win.getPPk(), false);
            int mailId1 = mailInfoService.insertMailReturnId(win.getPPk(),
                    "千面郎君奖励", "aa");
            String help2 = "<anchor><go method=\"post\" href=\""
                    + response.encodeURL(GameConfig.getContextPath()
                            + "/langjun.do") + "\">"
                    + "<postfield name=\"cmd\" value=\"n33\" />"
                    + "<postfield name=\"mailId\" value=\"" + mailId1 + "\" />"
                    + " </go>" + "领取【千面郎君奖励】</anchor><br/>";
            mailInfoService.updateMail(mailId1,
                    "恭喜您打败了【千面郎君】,获得活动奖励礼包一个,请查收.<br/>" + help2);
            UMessageInfoVO uif = new UMessageInfoVO();
            uif.setCreateTime(new Date());
            uif.setMsgPriority(PopUpMsgType.LANGJUN_FIRST);
            uif.setMsgType(PopUpMsgType.LANGJUN);
            uif.setPPk(fail.getPPk());
            uif.setMsgOperate1("【千面郎君】从你身上逃脱了！");
            uMsgService.sendPopUpMsg(uif);
            new RankService().updateAdd(win.getPPk(), "killlang", 1);
        }
        if (win != null && fail != null
                && fail.getPPk() == LangjunConstants.XIANHAI_LANGJUN)
        {
            RoleEntity re1 = new RoleCache()
                    .getByPpk(LangjunConstants.XIANHAI_LANGJUN);
            if (re1 != null)
            {
                re1.getBasicInfo().setTemp_Name(
                        re1.getBasicInfo().getRealName());
            }
            LangjunConstants.XIANHAI_LANGJUN = 0;
            UMsgService uMsgService = new UMsgService();
            UMessageInfoVO uif = new UMessageInfoVO();
            uif.setCreateTime(new Date());
            uif.setMsgPriority(PopUpMsgType.LANGJUN_FIRST);
            uif.setMsgType(PopUpMsgType.LANGJUN);
            uif.setPPk(win.getPPk());
            uif.setMsgOperate1("很遗憾，您杀死的是【千面郎君】的替身，因此无法获得奖励……");
            uMsgService.sendPopUpMsg(uif);
        }
    }
    */

    // 迷宫中掉落秘境地图
    private boolean dropMiJing(RoleEntity re, Fighter win, Fighter fail) {
        if (re.getBasicInfo().getSceneInfo().getMap().getMapType() == MapType.COMPASS) {
            int result = new GoodsService().updateMiJingOwner(fail.getPPk(), win.getPPk());
            if (result > 0) {
                UMessageInfoVO uif = new UMessageInfoVO();
                uif.setCreateTime(new Date());
                uif.setMsgPriority(PopUpMsgType.LANGJUN_FIRST);
                uif.setMsgType(PopUpMsgType.LANGJUN);
                uif.setPPk(fail.getPPk());
                uif.setMsgOperate1("你的秘境地图被" + win.getPName() + "抢走了……！");
                UMsgService uMsgService = new UMsgService();
                uMsgService.sendPopUpMsg(uif);
                new SystemInfoService().insertSystemInfoBySystem(fail.getPName() + "被" + win.getPName() + "于" + re.getBasicInfo().getSceneInfo().getSceneName() + "杀死，秘境地图落到了" + win.getPName() + "的手中！");
            }
            return result > 0;
        }
        return false;
    }

    // 攻击其他玩家
    public ActionForward n2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleEntity me = this.getRoleEntity(request);
        RoleEntity other = me.getPKState().getOtherOnFight();
        if (other == null) {
            // 如无攻击对手则返回游戏场景
            return this.returnScene(request, response);
        }
        // 设置相关消息
        this.setSysInfo(request, me);
        String sPk = (String) request.getAttribute("sPk");
        PlayerService playerService = new PlayerService();
        SkillService skillService = new SkillService();
        // 主动攻击
        Fighter playerA = playerService.getFighterByPpk(me.getPPk());
        // 被动攻击
        Fighter playerB = playerService.getFighterByPpk(other.getPPk());

        String contentdisplay_bak = playerA.getContentDisplay().replace(me.getName(), "您");
        playerA.setContentDisplay(contentdisplay_bak);
        // 如果是技能攻击就判断是否可用，如果可用就直接扣除MP
        if (sPk != null) {
            playerService.loadPlayerSkill(playerA, Integer.parseInt(sPk));
            String skill_hint = skillService.isUsabled(playerA, playerA.getSkill());
            if (skill_hint != null) {
                this.setHint(request, skill_hint);
                return mapping.findForward("skillhint");
            }
        }
        AttackService attackService = new AttackService();
        String unAttack = (String) request.getAttribute("unAttack");
        // 攻击对方
        attackService.attackPlayer(playerA, playerB, unAttack);
        /************PK完一个回合后需要做的处理*************/
        if (playerA.isDead() || playerB.isDead()) {
            // 处理PK死亡后的结果
            AttackService.processPKOver(playerA, playerB, request);
            if (playerA.isDead()) {
                me.getPKState().notifySelfDead(other, playerB.getKillDisplay());
                return mapping.findForward("dead_prop");
            } else if (playerB.isDead()) {
                me.getPKState().notifySelfKill(other, playerB.getKillDisplay());
                return mapping.findForward("win");
            }
        } else {
            request.setAttribute("playerA", playerA);
            request.setAttribute("playerB", playerB);
            return mapping.findForward("attack");
        }
        return null;
    }

    // 进入战斗的页面
    public ActionForward n3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String other_ppk = request.getParameter("bPpk");

        RoleEntity me = this.getRoleEntity(request);
        RoleEntity other = RoleService.getRoleInfoById(other_ppk);

        String hint = me.getPKState().startAttack(other);//设置PK攻击状态

        if (hint != null) {
            this.setHint(request, hint);
            return mapping.findForward("return_hint");
        }

        return this.n7(mapping, form, request, response);
    }

    //通知别人被打死
    public ActionForward notifyOtherDead(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        UMessageInfoVO u_msg_info = (UMessageInfoVO) request.getAttribute("uMsgInfo");//弹出式消息
        if (u_msg_info == null) {
            return super.returnScene(request, response);
        }

        request.setAttribute("over_display", u_msg_info.getMsgOperate1());
        return mapping.findForward("over");
    }

    //通知自己被打死
    public ActionForward notifySelfDead(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        UMessageInfoVO u_msg_info = (UMessageInfoVO) request.getAttribute("uMsgInfo");//弹出式消息
        if (u_msg_info == null) {
            return super.returnScene(request, response);
        }

        Fighter playerA = new Fighter();
        playerA.appendKillDisplay(u_msg_info.getMsgOperate1());
        request.setAttribute("player", playerA);
        request.setAttribute("pk", "pk");
        return mapping.findForward("dead_prop");
    }

    //通知杀死对方
    public ActionForward notifyKillOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        UMessageInfoVO u_msg_info = (UMessageInfoVO) request.getAttribute("uMsgInfo");//弹出式消息
        if (u_msg_info == null) {
            return super.returnScene(request, response);
        }

        RoleEntity me = this.getRoleEntity(request);

        me.getStateInfo().setCurState(PlayerState.EXTRA);

        String dead_ppk = u_msg_info.getMsgOperate2();// 死者
        String me_ppk = me.getPPk() + "";// 凶手

        request.setAttribute("over_display", u_msg_info.getMsgOperate1());
        request.setAttribute("a_p_pk", dead_ppk);
        request.setAttribute("b_p_pk", me_ppk);
        request.setAttribute("type", 2);//2为胜利者
        return mapping.findForward("over");
    }

    // 显示对方详情
    public ActionForward n5(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleEntity me = this.getRoleEntity(request);
        String bPpk = request.getParameter("bPpk");

        RoleEntity other = RoleCache.getByPpk(bPpk);

        request.setAttribute("other", other);
        request.setAttribute("me", me);
        return mapping.findForward("other_display");
    }

    // 快捷键操作
    public ActionForward n6(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleEntity me = this.getRoleEntity(request);
        String sc_pk = request.getParameter("sc_pk");
        ShortcutVO shortcut = me.getRoleShortCutInfo().getShortByScPk(Integer.parseInt(sc_pk));

        if (shortcut.getScType() == Shortcut.ATTACK)// 普通攻击
        {
            return n2(mapping, form, request, response);
        } else if (shortcut.getScType() == Shortcut.SKILL)// 技能攻击
        {
            request.setAttribute("sPk", shortcut.getOperateId() + "");
            return n2(mapping, form, request, response);
        } else if (shortcut.getScType() == Shortcut.LOOKINFO)// 查看对方详情
        {
            return this.n5(mapping, form, request, response);
        } else if (shortcut.getScType() == Shortcut.DEFAULT || shortcut.getScType() == Shortcut.ATTACKPROP)// 设置快捷键
        {
            request.getSession().setAttribute("retrun_url", "/pk.do?cmd=n7");
            request.setAttribute("pk", "pk");
            return new AttackShortcutAction().n3(mapping, form, request, response);
        } else if (shortcut.getScType() == Shortcut.CURE)// 药品快捷键
        {
            // 判断pk是是否可以使用药品
            if (me.getBasicInfo().getXuanyunhuihe() > 0) {
                this.setHint(request, "您出现眩晕回合!还有" + me.getBasicInfo().getXuanyunhuihe() + "个回合不能服用药物");
                me.getBasicInfo().setXuanyunhuihe(me.getBasicInfo().getXuanyunhuihe() - 1);
                return mapping.findForward("skillhint");
            }
            PropUseService propService = new PropUseService();
            PropUseEffect propUseEffect = propService.usePropByPropID(me, shortcut.getOperateId(), 1, request, response);// 使用药品
            request.setAttribute("propUseEffect", propUseEffect);
            request.setAttribute("unAttack", "unAttack");
            return n11(mapping, form, request, response);
        }

        return null;
    }

    // 显示通知攻击页面
    public ActionForward n7(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleEntity me = this.getRoleEntity(request);

        //设置相关消息
        this.setSysInfo(request, me);

        me.getStateInfo().setCurState(PlayerState.PKFIGHT);

        RoleEntity other = me.getPKState().getOtherOnFight();

        PlayerService playerService = new PlayerService();

        Fighter playerA = playerService.getFighterByPpk(me.getPPk());// 主动攻击
        Fighter playerB = playerService.getFighterByPpk(other.getPPk());// 被动攻击

        request.setAttribute("playerA", playerA);
        request.setAttribute("playerB", playerB);
        request.setAttribute("me", me);
        return mapping.findForward("display");
    }

    /*// PK捡取装备
    public ActionForward n8(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    {
        RoleEntity roleInfo = this.getRoleEntity(request);

        String p_pk = request.getParameter("p_pk");
        String goods_id = request.getParameter("goods_id");
        String goods_type = request.getParameter("goods_type");
        String goods_num = request.getParameter("goods_num");
        String goods_name = request.getParameter("goods_name");
        String dp_pk = request.getParameter("dp_pk");
        DropgoodsPkDAO dropgoodsPkDAO = new DropgoodsPkDAO();
        DropgoodsPkVO dropgoodsPkVO = dropgoodsPkDAO.getDropGoodsPKByDppk(dp_pk);

        String a_p_pk = request.getParameter("a_p_pk");
        String b_p_pk = request.getParameter("b_p_pk");

        GoodsService goodsService = new GoodsService();
        String hint = null;
        if (Integer.parseInt(p_pk) == roleInfo.getPPk())
        {
            hint = "您不能捡取您自己掉的物品";
            request.setAttribute("hint", hint);
            return mapping.findForward("pickup");
        }

        List list = null;//掉落物品列表
        // 如果掉落是装备的话 那么执行这个方法
        if (dropgoodsPkVO.getGoodsType() != GoodsType.PROP)
        {
            //判断包裹格数
            if (goodsService.isEnoughWrapSpace( roleInfo.getPPk(), 1))
            {
                //更改所有人
                EquipService equipService = new EquipService();
                equipService.updateOwner( roleInfo,dropgoodsPkVO.getGoodsId());

                // 清除刚才捡取的物品
                dropgoodsPkDAO.getgetDropGoodsPKDelete(Integer.parseInt(dp_pk));
                hint = "您捡取了" + goods_name;
                list = dropgoodsPkDAO.getDropGoodsPKList(Integer.parseInt(p_pk), roleInfo.getPPk());

            }
            else
            {
                list = dropgoodsPkDAO.getDropGoodsPKList(Integer.parseInt(p_pk), roleInfo.getPPk());
                hint = "您的包裹格数不够";
            }

        }
        else
        {
            int d = goodsService.putGoodsToWrap(roleInfo.getPPk(), Integer.parseInt(goods_id), Integer.parseInt(goods_type),
                    Integer.valueOf(dropgoodsPkVO.getGoodsQuality()), Integer.parseInt(goods_num));
            if (d == -1)
            {
                list = dropgoodsPkDAO.getDropGoodsPKList(Integer.parseInt(p_pk), roleInfo.getPPk());
                hint = "您的包裹格数不够";
            }
            else
            {
                // 清除刚才捡取的物品
                dropgoodsPkDAO.getgetDropGoodsPKDelete(Integer.parseInt(dp_pk));
                hint = "您捡取了" + goods_name;
                list = dropgoodsPkDAO.getDropGoodsPKList(Integer.parseInt(p_pk), roleInfo.getPPk());
            }
        }
        request.setAttribute("a_p_pk", a_p_pk);
        request.setAttribute("b_p_pk", b_p_pk);
        request.setAttribute("list", list);
        request.setAttribute("hint", hint);
        return mapping.findForward("pickup");
    }*/

    // PK胜利
    public ActionForward n9(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleEntity me = this.getRoleEntity(request);

        String type = request.getParameter("type");    // 3是失败者，2是胜利者

        if (!StringUtils.isEmpty(type)) {
            // 如果为失败者，就应该回城
            if (Integer.parseInt(type) == 3) {
                // 得到死亡复活点
                RoomService roomService = new RoomService();
                int resurrection_point = roomService.getResurrectionPoint(me);
                me.getBasicInfo().updateSceneId(resurrection_point + "");
            }

        }
        me.getStateInfo().setCurState(PlayerState.GENERAL);// 复位玩家状态
        return super.dispath(request, response, "/pubbuckaction.do?type=1");
    }


    // 显示通知攻击页面,不进入攻击状态.
    public ActionForward n11(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleEntity me = this.getRoleEntity(request);

        String bPpk = request.getParameter("bPpk");

        PlayerService playerService = new PlayerService();

        if (me.getBasicInfo().getXuanyunhuihe() > 0) {
            return n2(mapping, form, request, response);
        }
        Fighter playerA = playerService.getFighterByPpk(me.getPPk());// 主动攻击
        Fighter playerB = playerService.getFighterByPpk(Integer.parseInt(bPpk));// 被动攻击

        request.setAttribute("playerA", playerA);
        request.setAttribute("playerB", playerB);
        request.setAttribute("me", me);
        return mapping.findForward("display");
    }

    /**
     * 设置系统消息
     *
     * @param request request
     * @param me      me
     */
    private void setSysInfo(HttpServletRequest request, RoleEntity me) {
        request.setAttribute("me", me);
        request.setAttribute("sys_info_list", me.getPKState().getSysInfoList());
        request.setAttribute("attack_warning", me.getPKState().getWarningDes());
    }
}