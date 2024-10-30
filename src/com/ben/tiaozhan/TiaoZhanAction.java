package com.ben.tiaozhan;

import com.ben.shitu.model.DateUtil;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.menu.BaseAction;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.player.ShortcutService;
import com.ls.web.service.system.UMsgService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TiaoZhanAction extends BaseAction {
    private final PlayerService ps = new PlayerService();

    public ActionForward n1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        BasicInfo bi = getBasicInfo(request);
        request.setAttribute("pg_pk", request.getParameter("pg_pk") == null ? request.getAttribute("pg_pk") : request.getParameter("pg_pk"));
        String name = request.getParameter("name");
        if (name == null || "".equals(name.trim())) {
            setMessage(request, "请输入名称");
            return mapping.findForward("tiaozhan");
        }
        int ppk = roleService.getByName(name.trim());
        if (ppk == -1 || ppk == 0) {
            setMessage(request, "您目前无法对该玩家发起挑战。");
            return mapping.findForward("tiaozhan");
        }
        RoleEntity re = RoleService.getRoleInfoById(ppk + "");
        if (re == null) {
            setMessage(request, "您目前无法对该玩家发起挑战。");
            return mapping.findForward("tiaozhan");
        }
        if (re.getBasicInfo().getName().equalsIgnoreCase("gm")) {
            setMessage(request, "您目前无法对该玩家发起挑战。");
            return mapping.findForward("tiaozhan");
        }
        int re_ppk = re.getBasicInfo().getPPk();
        if (!(re.getStateInfo().getCurState() == PlayerState.GENERAL || re.getStateInfo().getCurState() == PlayerState.TRADE || re.getStateInfo().getCurState() == PlayerState.GROUP || re.getStateInfo().getCurState() == PlayerState.TALK)) {
            setMessage(request, "您目前无法对该玩家发起挑战。");
            return mapping.findForward("tiaozhan");
        }
        if (!(re.getBasicInfo().getSceneInfo().getMap().getMapType() == MapType.SAFE || re.getBasicInfo().getSceneInfo().getMap().getMapType() == MapType.DANGER)) {
            setMessage(request, "您目前无法对该玩家发起挑战。");
            return mapping.findForward("tiaozhan");
        }
        if (TiaozhanConstant.TIAOZHAN.containsKey(bi.getPPk())) {
            setMessage(request, "您目前无法对该玩家发起挑战。");
            return mapping.findForward("tiaozhan");
        }
        if (re_ppk == bi.getPPk()) {
            setMessage(request, "您不可以对自己发起挑战。");
            return mapping.findForward("tiaozhan");
        }
        if (TiaozhanConstant.TIAOZHAN.containsKey(re_ppk) || TiaozhanConstant.TIAOZHAN.containsValue(re_ppk)) {
            setMessage(request, "您目前无法对该玩家发起挑战。");
            return mapping.findForward("tiaozhan");
        }
        String pg_pk = request.getParameter("pg_pk");
        if (pg_pk != null && !"".equals(pg_pk.trim())) {
            PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
            PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk.trim()));
            if (propGroup != null) {
                if (TiaozhanConstant.TIAOZHAN.containsKey(re_ppk) || TiaozhanConstant.TIAOZHAN.containsValue(re_ppk)) {
                    setMessage(request, "您目前无法对该玩家发起挑战。");
                    return mapping.findForward("tiaozhan");
                }
                TiaozhanConstant.TIAOZHAN_TIME.put(re_ppk, new Date());
                TiaozhanConstant.TIAOZHAN.put(bi.getPPk(), re_ppk);
                TiaozhanConstant.TIAOZHAN.put(re_ppk, bi.getPPk());
                goodsService.removeProps(propGroup, 1);
                setMessage(request, "您已经成功向" + name.trim() + "发起了挑战！");
                UMsgService uMsgService = new UMsgService();
                UMessageInfoVO uif = new UMessageInfoVO();
                uif.setCreateTime(new Date());
                uif.setMsgPriority(PopUpMsgType.TIAOZHAN_FIRST);
                uif.setMsgType(PopUpMsgType.TIAOZHAN);
                uif.setPPk(re_ppk);
                uif.setResult(bi.getName());
                uif.setMsgOperate1(bi.getPPk() + "");
                uMsgService.sendPopUpMsg(uif);
                systemInfoService.insertSystemInfoBySystem(bi.getRealName() + "向" + name.trim() + "下了战书！不知" + name.trim() + "会做何应对！");

                return mapping.findForward(ERROR);
            } else {
                setMessage(request, "该道具不能使用");
                return mapping.findForward(ERROR);
            }
        } else {
            setMessage(request, "该道具不能使用");
            return mapping.findForward(ERROR);
        }
    }

    private String getSystemMessage(String name1, String name2) {
        Random r = new Random();
        int numm = r.nextInt(5);
        String mess = name1 + "被" + name2 + "的霸气所震，竟然没有胆量接受战书！";
        switch (numm) {
            case 0:
                break;
            case 1:
                mess = name1 + "被" + name2 + "的气势所压倒,居然悄悄的溜走了,留下的只有" + name2 + "鄙视的目光。";
                break;
            case 2:
                mess = name2 + "把胸脯拍得噼啪响，对" + name1 + "咆哮道：“飞毛腿了不起啊,逃的还真快,有种的你过来啊!”";
                break;
            case 3:
                mess = name2 + "对" + name1 + "逃去的身影咆哮道:\"胆小鬼,不要跑,我们大战三百回合\"。";
                break;
            case 4:
                mess = name2 + "对" + name1 + "摆开架式准备对战," + name1 + "突然消失了," + name2 + "感叹到\"" + name1 + "你怕死就不要来嘛,浪费时间\"。";
                break;
            default:
                break;
        }
        return mess;
    }

    public ActionForward n2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String caozuo = request.getParameter("caozuo");
        String tiao_ppk = request.getParameter("tiao_ppk");
        if (tiao_ppk == null || "".equals(tiao_ppk.trim())) {
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }

        RoleEntity reown = getRoleEntity(request);
        BasicInfo bi = reown.getBasicInfo();
        RoleEntity re = RoleService.getRoleInfoById(tiao_ppk);
        String tiao_name = roleService.getName(tiao_ppk)[0];
        if (re == null) {
            TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
            if (TiaozhanConstant.TIAOZHAN.containsKey(bi.getPPk())) {
                int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
                TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
                TiaozhanConstant.TIAOZHAN.remove(pk);
            }
            systemInfoService.insertSystemInfoBySystem(getSystemMessage(tiao_name, bi.getRealName()));
            setMessage(request, tiao_name + "被您的霸气所震，竟然没有胆量接受战书！");
            return mapping.findForward(ERROR);
        }
        if (caozuo == null || "".equals(caozuo.trim())) {
            TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
            int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
            TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
            TiaozhanConstant.TIAOZHAN.remove(pk);
            systemInfoService.insertSystemInfoBySystem(getSystemMessage(bi.getRealName(), tiao_name));
            return mapping.findForward("refurbish_scene");
        }
        Date date = TiaozhanConstant.TIAOZHAN_TIME.get(bi.getPPk());
        if (DateUtil.checkMin(date, TiaozhanConstant.OVER_TIME)) {
            setMessage(request, "由于您在1分钟内没有进行选择，因此被视为拒绝挑战");
            TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
            int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
            TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
            TiaozhanConstant.TIAOZHAN.remove(pk);
            systemInfoService.insertSystemInfoBySystem(getSystemMessage(bi.getRealName(), tiao_name));
            return mapping.findForward(ERROR);
        }
        int maptype = re.getBasicInfo().getSceneInfo().getMap().getMapType();
        if (maptype != MapType.SAFE && maptype != MapType.DANGER) {
            TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
            int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
            TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
            TiaozhanConstant.TIAOZHAN.remove(pk);
            systemInfoService.insertSystemInfoBySystem(getSystemMessage(tiao_name, bi.getRealName()));
            setMessage(request, tiao_name + "被您的霸气所震，竟然没有胆量接受战书！");
            return mapping.findForward(ERROR);
        }
        int state = re.getStateInfo().getCurState();
        if (!(state == PlayerState.GENERAL || state == PlayerState.TRADE || state == PlayerState.GROUP || state == PlayerState.TALK)) {
            TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
            int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
            TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
            TiaozhanConstant.TIAOZHAN.remove(pk);
            systemInfoService.insertSystemInfoBySystem(getSystemMessage(tiao_name, bi.getRealName()));
            setMessage(request, tiao_name + "被您的霸气所震，竟然没有胆量接受战书！");
            return mapping.findForward(ERROR);
        } else {
            int bppk = re.getBasicInfo().getPPk();
            // 进入擂台
            systemInfoService.insertSystemInfoBySystem(bi.getRealName() + "接受了" + tiao_name + "的挑战！双方站到了生死擂台上！");
            ps.updateSceneAndView(bppk, TiaozhanConstant.TIAOZHAN_SCENE);
            ps.updateSceneAndView(bi.getPPk(), TiaozhanConstant.TIAOZHAN_SCENE);
            ShortcutService shortcutService = new ShortcutService();

            reown.getPKState().startAttack(re);//攻击re

            Fighter playerA = new Fighter();// 主动攻击
            Fighter playerB = new Fighter();// 被动攻击
            ps.loadFighterByPpk(playerA, bi.getPPk());
            ps.loadFighterByPpk(playerB, bppk);

            List<ShortcutVO> shortcuts = shortcutService.getByPpk(bi.getPPk());

            request.setAttribute("playerA", playerA);
            request.setAttribute("playerB", playerB);
            request.setAttribute("shortcuts", shortcuts);
            request.setAttribute("tong", "0");
            // //System.out.println("Action N7 = -----
            // "+request.getParameter("chair"));
            String unAttack = (String) request.getAttribute("unAttack");
            request.setAttribute("unAttack", unAttack);
            request.setAttribute("reqchair", request.getParameter("chair"));
            return mapping.findForward("display");
        }
    }
}
