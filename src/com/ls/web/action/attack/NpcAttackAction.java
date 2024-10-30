package com.ls.web.action.attack;

import com.ben.dao.pet.PetDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.vo.pet.pet.PetVO;
import com.ben.vo.petinfo.PetInfoVO;
import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.attack.FightList;
import com.ls.ben.vo.info.effect.PropUseEffect;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.PetNameBean;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.Shortcut;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.util.MathUtil;
import com.ls.web.action.ActionBase;
import com.ls.web.action.shortcut.AttackShortcutAction;
import com.ls.web.service.attack.AttackService;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.prop.PropUseService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.player.FightService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.player.ShortcutService;
import com.ls.web.service.skill.SkillService;
import com.lw.service.specialprop.SpecialPropService;
import com.pm.dao.systemInfo.SysInfoDao;
import com.pm.service.pic.PicService;
import com.pm.vo.sysInfo.SystemInfoVO;
import com.web.jieyi.util.Constant;
import com.web.service.petservice.HhjPetService;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 打怪
 */
public class NpcAttackAction extends ActionBase {
    Logger logger = Logger.getLogger("log.action");


    //战斗页面
    public ActionForward n2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String pPk = (String) request.getSession().getAttribute("pPk");


        Object[] attacklist = null;
        List<NpcFighter> npcs = null;
        // 得到所有主动攻击的npc
        AttacckCache attacckCache = new AttacckCache();
        attacklist = attacckCache.getByPPkd(Integer.parseInt(pPk));

        logger.debug("attacklist=" + attacklist);

        if (attacklist[0] != null) {

            npcs = (List<NpcFighter>) attacklist[0];
            PlayerService playerService = new PlayerService();
            Fighter player = new Fighter();
            playerService.loadFighterByPpk(player, Integer.parseInt(pPk));

            request.setAttribute("player", player);
            request.setAttribute("npcs", npcs);
            return mapping.findForward("display");
        } else {

            // 没有怪的时候， 返回主页面
            return mapping.findForward("fleeok");

        }
    }

    // 攻击npc，一个回合
    public ActionForward n3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String bout_type = (String) request.getAttribute("bout_type");
        RoleService roleService = new RoleService();
        RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());
        PlayerService playerService = new PlayerService();
        FightService fightService = new FightService();
        SkillService skillService = new SkillService();
        NpcService npcService = new NpcService();
        // 装载用户信息
        Fighter player = new Fighter();
        player.setAppendAttributeDescribe(playerService.loadFighterByPpk(player, roleEntity.getBasicInfo().getPPk()));
        // 装载 npc信息
        List<NpcFighter> npcs = npcService.getAttackNPCs(roleEntity.getBasicInfo().getPPk(), roleEntity.getBasicInfo().getSceneId());
        if (npcs.size() == 1) {
            NpcFighter nf = new NpcFighter();
            nf = npcs.get(0);
            if (nf.getNpcType() == NpcAttackVO.NIANSHOU) {
                try {
                    NpcFighter zdnpcs_bak = Constant.PETNPC.get(nf.getNpcID());
                    if (zdnpcs_bak == null) {
                        Constant.PETNPC.put(nf.getNpcID(), nf);
                    }
                    zdnpcs_bak = Constant.PETNPC.get(nf.getNpcID());
                    if (zdnpcs_bak.getNpcdeaddisplay() != null && !zdnpcs_bak.getNpcdeaddisplay().equals("")) {
                        request.setAttribute("display", zdnpcs_bak.getNpcdeaddisplay());
                        return mapping.findForward("display_bak");
                    }
                    npcs.remove(0);
                    npcs.add(0, zdnpcs_bak);
                } catch (Exception e) {
                    request.setAttribute("display", "无该NPC!");
                    return mapping.findForward("display_bak");
                }
            }
        }
        //已无npc,被别的玩家打死或重复刷新
        if (npcs == null || (npcs != null && npcs.size() == 0)) {
            logger.info("已无npc");
        } else {
            NpcVO vo = npcs.get(0);
            roleEntity.getBasicInfo().setAttack_npc(vo.getNpcID());
        }
        //获取有没有其他的系统消息，需要在战斗时显示的
        SysInfoDao sysInfoDao = new SysInfoDao();
        List<SystemInfoVO> sysInfoVO = sysInfoDao.selectSysInfo(roleEntity.getBasicInfo().getPPk(), 5, 1);
        request.setAttribute("sysInfoVO", sysInfoVO);
        //判断道具使用是否为0
        SpecialPropService spss = new SpecialPropService();
        String hint = spss.isUser(roleEntity);
        if (hint != null && !hint.equals("")) {
            request.setAttribute("hint", hint);
            request.setAttribute("p_pk", player.getPPk() + "");
            return mapping.findForward("skillhint");
        }
        if (bout_type.equals("普通攻击")) {
            //攻击npc
            fightService.attackNPC(player, npcs);
        } else if (bout_type.equals("技能攻击")) {
            String s_pk = (String) request.getAttribute("s_pk");
            if (s_pk == null) {
                // 没有发动仅能攻击
                s_pk = "-1";
            }
            // 判断是否是技能攻击
            if (!s_pk.equals("-1"))//技能攻击
            {
                playerService.loadPlayerSkill(player, Integer.parseInt(s_pk));
                String skill_hint = skillService.isUsabled(player, player.getSkill());
                if (skill_hint != null)  //判断技能是否能使用
                {
                    request.setAttribute("hint", skill_hint);
                    request.setAttribute("p_pk", player.getPPk() + "");
                    return mapping.findForward("skillhint");
                } else//成功发动技能攻击
                {
                    //如果技能可以使用则扣除MP
                    PlayerSkillVO playerSkill = player.getSkill();
                    player.setExpendMP(playerSkill.getSkUsecondition());
                    RoleEntity roleInfo = RoleService.getRoleInfoById(player.getPPk() + "");
                    roleInfo.getBasicInfo().updateMp(player.getPMp());
                    //攻击npc
                    fightService.attackNPC(player, npcs);
                }
            }
        } else if (bout_type.equals("逃跑")) {
            player.setSkillDisplay("逃跑,但失败了");
        } else if (bout_type.equals("不能逃跑")) {
            player.setSkillDisplay("逃跑,但您不能逃跑");
        }
        String s = (String) request.getSession().getAttribute("getKillDisplay");
        //npc攻击角色
        npcService.attackPlayer(npcs, player);
        /***************攻击完一个回合后需要做的处理************/
        if (player.isDead() || npcs.size() == 0) {
            AttackService.processAttackNPCOver(player, npcs, roleEntity, request);
            if (player.isDead())// 玩家被打死
            {
                return mapping.findForward("dead_prop");
            } else if (npcs.size() == 0)// 战斗胜利
            {
                return mapping.findForward("win");
            }
        } else// 继续攻击
        {
            ShortcutService shortcutService = new ShortcutService();
            List<ShortcutVO> shortcuts = shortcutService.getByPpk(roleEntity.getBasicInfo().getPPk());
            String displiey = (String) request.getSession().getAttribute("getKillDisplay");
            if (displiey == null) {
                displiey = "";
            }
            player.appendKillDisplay(displiey);
            request.getSession().setAttribute("getKillDisplay", player.getKillDisplay());
            SpecialPropService sps = new SpecialPropService();
            int[] hpmp = sps.getHpMpAdd(player, roleEntity);
            int hp = hpmp[0];
            int mp = hpmp[1];
            request.setAttribute("hp", "" + hp);
            request.setAttribute("mp", "" + mp);
            request.setAttribute("player", player);
            request.setAttribute("npcs", npcs);
            request.setAttribute("shortcuts", shortcuts);
            return mapping.findForward("attack");
        }
        return null;
    }

    // 进入战斗页面，显示详情

    public ActionForward n4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleEntity me = this.getRoleEntity(request);
        String nPk = request.getParameter("nPk");
        if (nPk == null) {
            logger.debug("空指针。。。。");
        }
        PlayerService playerService = new PlayerService();
        //*******************判断是否有人攻击
        if (me.getPKState().getOtherNum() > 0) {
            return super.dispath(request, response, "/pk.do?cmd=n7");
        }
        me.getStateInfo().setCurState(PlayerState.NPCFIGHT);
        ShortcutService shortcutService = new ShortcutService();
        NpcService npcService = new NpcService();
        // 装载用户信息
        Fighter player = new Fighter();
        player.setAppendAttributeDescribe(playerService.loadFighterByPpk(player, me.getBasicInfo().getPPk()));
        NpcFighter npcFighter = null;
        List<NpcFighter> bdnpcs = null;
        List<NpcFighter> zdnpcs = null;
        // 使当前npc处于战斗状态
        if (nPk != null) {

            // 得到所有主动攻击的npc
            Object[] attacklist = null;
            AttacckCache attacckCache = new AttacckCache();
            attacklist = attacckCache.getByPPkd(me.getBasicInfo().getPPk());
            bdnpcs = (List<NpcFighter>) attacklist[1];
            npcFighter = bdnpcs.get(Integer.parseInt(nPk));
            bdnpcs.remove(Integer.parseInt(nPk));
            zdnpcs = (List<NpcFighter>) attacklist[0];
            zdnpcs.add(npcFighter);

        } else {
            zdnpcs = npcService.getAttackNPCs(me.getBasicInfo().getPPk(), me.getBasicInfo().getSceneId());
        }
        if (zdnpcs.size() == 1) {
            NpcFighter nf = new NpcFighter();
            nf = zdnpcs.get(0);
            if (nf.getNpcType() == NpcAttackVO.NIANSHOU) {
                try {
                    NpcFighter zdnpcs_bak = Constant.PETNPC.get(nf.getNpcID());
                    if (zdnpcs_bak == null) {
                        Constant.PETNPC.put(nf.getNpcID(), nf);
                    }
                    zdnpcs_bak = Constant.PETNPC.get(nf.getNpcID());
                    if (zdnpcs_bak.getNpcdeaddisplay() != null && !zdnpcs_bak.getNpcdeaddisplay().equals("")) {
                        request.setAttribute("display", zdnpcs_bak.getNpcdeaddisplay());
                        return mapping.findForward("display_bak");
                    }
                    zdnpcs.remove(0);
                    zdnpcs.add(0, zdnpcs_bak);
                } catch (Exception e) {
                    request.setAttribute("display", "无该NPC!");
                    return mapping.findForward("display_bak");
                }
            }
        }
        // 快捷键类表
        List<ShortcutVO> shortcuts = shortcutService.getByPpk(me.getBasicInfo().getPPk());
        String displiey = (String) request.getSession().getAttribute("getKillDisplay");
        player.appendKillDisplay(displiey);
        request.setAttribute("player", player);
        request.setAttribute("npcs", zdnpcs);
        request.setAttribute("shortcuts", shortcuts);
        return mapping.findForward("display");
    }

    // 战斗胜利
    public ActionForward n5(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
        roleInfo.getStateInfo().setCurState(PlayerState.GENERAL);
        request.getSession().setAttribute("getKillDisplay", "");
        roleInfo.getDropSet().clearDropItem();
        return mapping.findForward("no_refurbish_scene");
    }

    // 快捷键操作
    public ActionForward n6(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String pPk = (String) request.getSession().getAttribute("pPk");
        RoleEntity me = this.getRoleEntity(request);

        //*******************判断是否有人攻击
        if (me.getPKState().getOtherNum() > 0) {
            return super.dispath(request, response, "/pk.do?cmd=n7");
        }

        String sc_pk = request.getParameter("sc_pk");
        String pk = request.getParameter("pk");
        if (sc_pk == null) {
            logger.debug("空指针。。。。");
        }
        String shortType = request.getParameter("shortType");
        request.setAttribute("shortType", request.getParameter("shortType"));
        ShortcutService shortcutService = new ShortcutService();
        ShortcutVO shortcut = shortcutService.getByScPk(Integer.parseInt(sc_pk), pPk);
        logger.debug("sc_type=" + shortcut.getScType());
        logger.debug("sc_name=" + shortcut.getScName());
        logger.debug("sc_operateId=" + shortcut.getOperateId());
        logger.debug("sc_objectId=" + shortcut.getObjectId());
        if (shortcut.getScType() == Shortcut.ATTACK)// 普通攻击
        {
            request.setAttribute("bout_type", "普通攻击");
            return n3(mapping, form, request, response);
        } else if (shortcut.getScType() == Shortcut.SKILL)// 技能攻击
        {
            request.setAttribute("bout_type", "技能攻击");
            request.setAttribute("s_pk", shortcut.getOperateId() + "");
            return n3(mapping, form, request, response);
        } else if (shortcut.getScType() == Shortcut.LOOKINFO)// 查看npc详情
        {
            return n7(mapping, form, request, response);
        } else if (shortcut.getScType() == Shortcut.DEFAULT)// 设置快捷键
        {
            AttackShortcutAction attackShortcutAction = new AttackShortcutAction();
            // 只有当不是在普通情况下进入快捷键设置的,才需要在session设置
            if (shortType == null || shortType.equals("")) {
                HttpSession session = request.getSession();
                session.setAttribute("retrun_url", "/attackNPC.do?cmd=n4&pPk=" + me.getBasicInfo().getPPk() + "&chair=" + request.getParameter("chair"));
            }
            request.setAttribute("pk", pk);
            return attackShortcutAction.n3(mapping, form, request, response);
        } else if (shortcut.getScType() == Shortcut.FLEE)// 逃跑
        {
            return n16(mapping, form, request, response);
        } else if (shortcut.getScType() == Shortcut.CURE)// 药品的使用
        {

            PropUseEffect propUseEffect = null;
            PropUseService propService = new PropUseService();
            propUseEffect = propService.usePropByPropID(me, shortcut.getOperateId(), 1, request, response);// 使用药品
            request.setAttribute("propUseEffect", propUseEffect);
            return n4(mapping, form, request, response);
        } else if (shortcut.getScType() == Shortcut.ATTACKPROP)// 药品的使用
        {
            request.getSession().setAttribute("prop_pet_id", shortcut.getOperateId());
            return propn8(mapping, form, request, response);
        }

        return null;
    }

    /**
     * 查看npc详情
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward n7(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

        String mapid = roleInfo.getBasicInfo().getSceneId();

        NpcService npcService = new NpcService();
        NpcAttackVO npc = npcService.getOneAttackNPCByPpk(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(mapid));
        npcService.loadNPCWx(npc);
        //获得五行攻击的显示
        String wx = npcService.getWxNum(npc.getNpcID());

        //获得npc图片信息
        PicService picService = new PicService();
        logger.info("pPk=" + roleInfo.getBasicInfo().getPPk() + " ,mapid=" + mapid);
        logger.info("npcId=" + npc.getNpcID());
        String npcPicStr = picService.getNpcPicStr(roleInfo, npc.getNpcID());
        request.setAttribute("npcPic", npcPicStr);

        request.setAttribute("npc", npc);
        request.setAttribute("wx", wx);
        return mapping.findForward("npc_display");
    }

    /**
     * 捕捉NPC
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward n8(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
        String p_pk = roleInfo.getBasicInfo().getPPk() + "";

        PetNameBean petNameBean = new PetNameBean();

        //取出当前攻击NPC的信息
        NpcService npcService = new NpcService();
        String mapid = roleInfo.getBasicInfo().getSceneId();

        Fighter player = new Fighter();
        PlayerService playerService = new PlayerService();
        player.setAppendAttributeDescribe(playerService.loadFighterByPpk(player, roleInfo.getBasicInfo().getPPk()));

        logger.info("mapid=" + mapid);
        NpcAttackVO npc = npcService.getOneAttackNPCByPpk(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(mapid));
        if (npc == null) {
            request.setAttribute("display", "无该NPC或NPC死亡被捕捉!");
            return mapping.findForward("display_bak");
        }
        npcService.loadNPCWx(npc);
        //取出角色的信息
        PetInfoDAO petInfoDAO = new PetInfoDAO();
        List<PetInfoVO> petInfoDAOlist = petInfoDAO.getPetInfoList(p_pk);
        logger.info("npc.getNpcId()=" + npc.getNpcID());
        PetDAO petDAO = new PetDAO();
        PetVO PetVO = petDAO.getPetView(npc.getNpcID());
        if (PetVO == null) {
            String petInfolist = "不是宠物，无法捕捉！";
            request.setAttribute("petInfolist", petInfolist);
            return n4(mapping, form, request, response);
        }


        int cur_mp = roleInfo.getBasicInfo().getMp();//当前打MP值
        if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
            int maxhp = player.getPMaxHp();
            int cur_hp = player.getPHp();
            if (maxhp != cur_hp) {
                String petInfolist = "您的血量不满";
                request.setAttribute("petInfolist", petInfolist);
                return n4(mapping, form, request, response);
            }
            maxhp = player.getPMaxMp();
            cur_hp = player.getPMp();
            if (maxhp != cur_hp) {
                String petInfolist = "您的内力不满";
                request.setAttribute("petInfolist", petInfolist);
                return n4(mapping, form, request, response);
            }
            NpcFighter zdnpcs_bak = Constant.PETNPC.get(npc.getNpcID());
            zdnpcs_bak.setNpccountnum(MathUtil.getRandomBetweenXY(3, 5));
            Constant.PETNPC.put(npc.getNpcID(), zdnpcs_bak);
        }
        int pp = npc.getLevel() + 10;
        if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
            pp = MathUtil.getRandomBetweenXY(10, 50) * cur_mp / 100;
        }
        if (cur_mp < pp) {
            String petInfolist = "您的内力不够";
            request.setAttribute("petInfolist", petInfolist);
            return n4(mapping, form, request, response);
        }


        if (petInfoDAOlist.size() > 5) {
            String petInfolist = "您不能携带更多宠物了！";
            request.setAttribute("petInfolist", petInfolist);
            return n4(mapping, form, request, response);
        } else {
            //将100%表示成分母分子格式在随机取出一个随机数
            float dd = (float) npc.getCurrentHP() / npc.getNpcHP();
            float ss = ((1 - dd) / 2) * 100;
            int role_grade = roleInfo.getBasicInfo().getGrade();
            float cc = (role_grade - npc.getLevel()) * 5 + ss;
            log.info("捕捉概率值:" + cc);
            //log.info("捕捉概率值:"+ss); (玩家等级 - NPC等级)*5*1+((1-NPC血量)/2)*100

            //判断百分几率
            HhjPetService petService = new HhjPetService();

            if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
                cc = 0;
            }

            if (cc > 100) {
                if (petService.PetSinewService(petNameBean, npc, p_pk)) {
                    npcService.capturePet(npc, mapid);//捕捉成功删除npc
                } else {
                    String notpet = "notpet";
                    request.setAttribute("notpet", notpet);
                    request.setAttribute("bout_type", "捕捉宠物");
                    return n3(mapping, form, request, response);
                }
            } else if (MathUtil.isAppearByPercentage(cc, 100)) {

                if (petService.PetSinewService(petNameBean, npc, p_pk)) {
                    if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
                        npcService.getNpcByNianshouBuzhuo(npc.getNpcID(), "被" + roleInfo.getBasicInfo().getName() + "捕捉了!");
                    }
                    npcService.capturePet(npc, mapid);//捕捉成功删除npc
                } else {
                    String notpet = "notpet";
                    request.setAttribute("notpet", notpet);
                    request.setAttribute("bout_type", "捕捉宠物");
                    return n3(mapping, form, request, response);
                }
            } else {
                String notpet = "notpet";
                request.setAttribute("notpet", notpet);
                request.setAttribute("pp", pp);
                request.setAttribute("bout_type", "捕捉宠物");
                return n3(mapping, form, request, response);
            }
        }

        //清除被捕获的npc
//		NpcAttackDao npcAttackDao = new NpcAttackDao();
//			npcAttackDao.deleteNpcByMapNpcPPk(npc.getPPk(),npc.getNpcID(),mapid+"");

        request.setAttribute("npcname", petNameBean.getPetName());
        request.setAttribute("p_pk", p_pk);
        request.setAttribute("npcId", npc.getNpcID() + "");

        return mapping.findForward("okpet");
    }


    /**
     * 捕捉NPC胜利
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward n9(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

        NpcService npcService = new NpcService();

        if (npcService.isHaveAttackNPC(roleInfo))//判断是否有战斗状态的npc
        {
            //有战斗状态的npc
            return n4(mapping, form, request, response);
        } else {
            //战斗胜利
            PlayerService playerService = new PlayerService();
            FightService fightService = new FightService();

            Fighter player = new Fighter();
            playerService.loadFighterByPpk(player, roleInfo.getBasicInfo().getPPk());

            FightList fightList = new FightList();
            fightService.playerWIN(player, fightList);

            request.setAttribute("player", player);
            request.setAttribute("fightList", fightList);
            return mapping.findForward("win");
        }
    }

    // 自动打怪快捷键技能列表
    public ActionForward n10(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

        ShortcutService shortcutService = new ShortcutService();
        List skills = shortcutService.getSkills(roleInfo.getBasicInfo().getPPk());
        request.setAttribute("skills", skills);
        return mapping.findForward("skilllist");
    }

    // 自动打怪快捷键技能列表
    public ActionForward n11(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
        String scType = request.getParameter("type");
        String operateId = request.getParameter("operate_id");
        String killName = request.getParameter("killName");

        request.setAttribute("pPk", roleInfo.getBasicInfo().getSceneId());
        request.setAttribute("scType", scType);
        request.setAttribute("operateId", operateId);
        request.setAttribute("killName", killName);
        return n12(mapping, form, request, response);
    }

    // 自动打怪快捷键技能设定
    public ActionForward n12(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

        String scType = (String) request.getAttribute("scType");
        String operateId = (String) request.getAttribute("operateId");

        roleInfo.getSettingInfo().getAutoAttackSetting().set(Integer.parseInt(scType), Integer.parseInt(operateId));
        try {
            request.getRequestDispatcher("/attackNPC.do?cmd=n4").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 自动打怪快捷键技能设定
    public ActionForward n13(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        RoleEntity me = this.getRoleEntity(request);

        //*******************判断是否有人攻击
        if (me.getPKState().getOtherNum() > 0) {
            return super.dispath(request, response, "/pk.do?cmd=n7");
        }


        int sctype = me.getSettingInfo().getAutoAttackSetting().getAttackType();
        int OperateId = me.getSettingInfo().getAutoAttackSetting().getUSkillId();
        if (sctype == Shortcut.ATTACK)// 普通攻击
        {
            request.setAttribute("bout_type", "普通攻击");
            return n3(mapping, form, request, response);
        } else if (sctype == Shortcut.SKILL)// 技能攻击
        {
            request.setAttribute("bout_type", "技能攻击");
            request.setAttribute("s_pk", OperateId + "");
            return n3(mapping, form, request, response);
        }
        return null;
    }

    // 自动打怪快捷键技能设定
    public ActionForward n14(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
        roleInfo.getSettingInfo().getAutoAttackSetting().init();
        try {
            request.getRequestDispatcher("/attackNPC.do?cmd=n4").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 选择是否使用免死道具
    public ActionForward n15(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

        int pPk = roleInfo.getBasicInfo().getPPk();
        //查看其是否有免死金牌,如果没有,即使到了这个action也没用
        PlayerPropGroupDao ppgdao = new PlayerPropGroupDao();
        int flag = ppgdao.getPropNumByPropType(pPk, PropType.EXONERATIVE);
        long dropedExperience = 0;
        String hint = "0";

        FightService fightService = new FightService();
        TimeControlService timeService = new TimeControlService();
        boolean flag1 = timeService.isUseableToTimeControl(pPk, PropType.OUTPUBLISH, TimeControlService.ANOTHERPROP);

        dropedExperience = roleInfo.getStateInfo().getDeadDropExp();
        PlayerService playerService = new PlayerService();
        playerService.clearTempData(pPk, "zd");

        //选择是否使用,为1是使用,为2是不使用
        String choice = request.getParameter("choice");
        if (choice.equals("2")) {
            //playerService.initPosition(infovo);
            if (flag1) {
                hint = "您丢失:经验" + dropedExperience + "点!";
            } else {
                hint = "免死符挽回经验" + roleInfo.getStateInfo().getShouldDropExperience() + "点!";
            }
        } else if (choice.equals("1")) {
            if (flag == 0) {
                try {
                    request.getRequestDispatcher("/attackMallAction.do?cmd=n1").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            } else {
                if (flag1) {
                    roleInfo.getBasicInfo().updateAddCurExp(dropedExperience);
                    hint = "您使用了【鸿蒙造化果】成功续命，挽回经验" + dropedExperience + "点!";
                } else {
                    hint = "您使用了【鸿蒙造化果】成功续命，挽回经验" + roleInfo.getStateInfo().getShouldDropExperience() + "点!";
                }

                int shouldScene = fightService.getShouldScene(roleInfo);
                roleInfo.getBasicInfo().updateSceneId(shouldScene + "");

                // 移除一个【鸿蒙造化果】(盒)
                PropDao propdao = new PropDao();
                int prop_id = propdao.getPropIdByType(PropType.EXONERATIVE);
                //清除主动攻击怪
                GoodsService goodsService = new GoodsService();
                goodsService.removeProps(pPk, prop_id, 1, GameLogManager.R_USE);
            }
        }
        roleInfo.getStateInfo().setDeadDropExp(0);
        roleInfo.getStateInfo().setShouldDropExperience(0);
        request.setAttribute("hint", hint);
        return mapping.findForward("prop_effect");
    }

    /**
     * 购买九转还魂完成后的 处理
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward n17(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
        int pPk = roleInfo.getBasicInfo().getPPk();

        String hint = "";
        long dropedExperience = 0;
        PlayerService playerService = new PlayerService();
        playerService.clearTempData(pPk, "zd");
        PartInfoDao infoDao = new PartInfoDao();
        PartInfoVO infovo = infoDao.getPartInfoByID(pPk);
        infovo.setPMap(roleInfo.getBasicInfo().getShouldScene() + "");
        String sussendString = (String) request.getSession().getAttribute("sussend");

        FightService fightService = new FightService();
        TimeControlService timeService = new TimeControlService();
        boolean flag1 = timeService.isUseableToTimeControl(pPk, PropType.OUTPUBLISH, TimeControlService.ANOTHERPROP);

        dropedExperience = roleInfo.getStateInfo().getDeadDropExp();

        if (sussendString != null && sussendString.equals("sussend")) {
            if (flag1) {
                roleInfo.getBasicInfo().updateAddCurExp(dropedExperience);
                hint = "您使用了【鸿蒙造化果】成功续命，挽回经验" + dropedExperience + "点!";
            } else {
                hint = "您使用了【鸿蒙造化果】成功续命，挽回经验" + roleInfo.getStateInfo().getShouldDropExperience() + "点!";
            }
            int shouldScene = fightService.getShouldScene(roleInfo);
            roleInfo.getBasicInfo().updateSceneId(shouldScene + "");
            //清除主动攻击怪
        } else {
            //playerService.initPosition(infovo);
            if (flag1) {
                hint = "您丢失:经验" + dropedExperience + "点!";
            } else {
                hint = "免死符挽回经验" + roleInfo.getStateInfo().getShouldDropExperience() + "点!";
            }
        }

        roleInfo.getStateInfo().setDeadDropExp(0);
        roleInfo.getStateInfo().setShouldDropExperience(0);
        request.getSession().removeAttribute("sussend");
        request.setAttribute("hint", hint);
        return mapping.findForward("prop_effect");
    }

    /**
     * 逃跑
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward n16(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
        int pPk = roleInfo.getBasicInfo().getPPk();

        //逃跑成功率=0.8-（npc等级-玩家等级）/50
        //取出当前攻击NPC的信息
        NpcService npcService = new NpcService();
        int mapid = Integer.parseInt(roleInfo.getBasicInfo().getSceneId());

        logger.info("mapid=" + mapid);
        NpcAttackVO npc = npcService.getOneAttackNPCByPpk(pPk, mapid);
        if (npc.getNpcType() == NpcAttackVO.CITYDOOR) {
            request.setAttribute("bout_type", "不能逃跑");
            return n3(mapping, form, request, response);
        }


        int partGrade = roleInfo.getBasicInfo().getGrade();//得到玩家等级

        int d_value = npc.getLevel() - partGrade;
        if (d_value < 0) {
            d_value = 0;
        }
        //修改逃跑概率 由0.8改成0.5
        double fleeprobability = 0.5 - d_value / 50;
        logger.info("玩家逃跑几率  ===  " + (fleeprobability * 100));
        if (!MathUtil.PercentageRandomByParamdouble((fleeprobability * 100), 100)) {//为TRUE 表示逃跑成功
            request.setAttribute("bout_type", "逃跑");
            return n3(mapping, form, request, response);
        }
        if (roleInfo.getTaskInfo().getTaskId() != -1 && roleInfo.getTaskInfo().getTaskPoint() != null && !roleInfo.getTaskInfo().getTaskPoint().equals("") && roleInfo.getTaskInfo().getTaskMenu() != -1) {
            roleInfo.getTaskInfo().setTaskId(-1);
            roleInfo.getTaskInfo().setTaskMenu(-1);
            roleInfo.getTaskInfo().setTaskPoint("");
        }
        //清除主动攻击怪
        PlayerService playerService = new PlayerService();
        playerService.clearTempData(pPk, "zd");
        request.getSession().setAttribute("getKillDisplay", "");
        return mapping.findForward("fleeok");
    }

    /**
     * 捕捉NPC
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward propn8(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        int prop_id = 0;
        String prop_pet_id = request.getSession().getAttribute("prop_pet_id").toString();
        if (prop_pet_id != null && !prop_pet_id.equals("") && !prop_pet_id.equals("null")) {
            prop_id = Integer.parseInt(prop_pet_id);
        }

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
        String p_pk = roleInfo.getBasicInfo().getPPk() + "";

        PetNameBean petNameBean = new PetNameBean();

        //取出当前攻击NPC的信息
        NpcService npcService = new NpcService();
        String mapid = roleInfo.getBasicInfo().getSceneId();

        Fighter player = new Fighter();
        PlayerService playerService = new PlayerService();
        player.setAppendAttributeDescribe(playerService.loadFighterByPpk(player, roleInfo.getBasicInfo().getPPk()));

        logger.info("mapid=" + mapid);
        NpcAttackVO npc = npcService.getOneAttackNPCByPpk(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(mapid));
        if (npc == null) {
            request.setAttribute("display", "无该NPC或NPC死亡被捕捉!");
            return mapping.findForward("display_bak");
        }
        npcService.loadNPCWx(npc);
        //取出角色的信息
        PetInfoDAO petInfoDAO = new PetInfoDAO();
        List<PetInfoVO> petInfoDAOlist = petInfoDAO.getPetInfoList(p_pk);
        logger.info("npc.getNpcId()=" + npc.getNpcID());
        PetDAO petDAO = new PetDAO();
        PetVO PetVO = petDAO.getPetView(npc.getNpcID());
        if (PetVO == null) {
            String petInfolist = "不是宠物，无法捕捉！";
            request.setAttribute("petInfolist", petInfolist);
            return n4(mapping, form, request, response);
        }


        int cur_mp = roleInfo.getBasicInfo().getMp();//当前打MP值
        if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
            int maxhp = player.getPMaxHp();
            int cur_hp = player.getPHp();
            if (maxhp != cur_hp) {
                String petInfolist = "您的血量不满";
                request.setAttribute("petInfolist", petInfolist);
                return n4(mapping, form, request, response);
            }
            maxhp = player.getPMaxMp();
            cur_hp = player.getPMp();
            if (maxhp != cur_hp) {
                String petInfolist = "您的内力不满";
                request.setAttribute("petInfolist", petInfolist);
                return n4(mapping, form, request, response);
            }
            NpcFighter zdnpcs_bak = Constant.PETNPC.get(npc.getNpcID());
            zdnpcs_bak.setNpccountnum(MathUtil.getRandomBetweenXY(5, 9));
            Constant.PETNPC.put(npc.getNpcID(), zdnpcs_bak);
        }
        int pp = npc.getLevel() + 10;
        if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
            pp = MathUtil.getRandomBetweenXY(10, 50) * cur_mp / 100;
        }
        if (cur_mp < pp) {
            String petInfolist = "您的内力不够";
            request.setAttribute("petInfolist", petInfolist);
            return n4(mapping, form, request, response);
        }


        if (petInfoDAOlist.size() > 5) {
            String petInfolist = "您不能携带更多宠物了！";
            request.setAttribute("petInfolist", petInfolist);
            return n4(mapping, form, request, response);
        } else {
            //将100%表示成分母分子格式在随机取出一个随机数
            float dd = (float) npc.getCurrentHP() / npc.getNpcHP();
            float ss = ((1 - dd) / 2) * 100;
            int role_grade = roleInfo.getBasicInfo().getGrade();
            float cc = (role_grade - npc.getLevel()) * 5 + ss;
            log.info("捕捉概率值:" + cc);
            //log.info("捕捉概率值:"+ss); (玩家等级 - NPC等级)*5*1+((1-NPC血量)/2)*100

            //判断百分几率
            HhjPetService petService = new HhjPetService();


            // 道具是否可以使用判断
            PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
            int tatol_prop_num = propGroupDao.getPropNumByByPropID(roleInfo.getBasicInfo().getPPk(), prop_id);
            PropCache pc = new PropCache();
            PropVO vo = PropCache.getPropById(prop_id);
            if (!npcService.getCatchPetByNpc(vo.getPropOperate1(), npc.getNpcID())) {
                String petInfolist = "您不能对" + npc.getNpcName() + "使用该道具!";
                request.setAttribute("petInfolist", petInfolist);
                return n4(mapping, form, request, response);
            }
            if (tatol_prop_num == 0) {
                String petInfolist = "您的包裹里没有该道具或已使用完!";
                request.setAttribute("petInfolist", petInfolist);
                return n4(mapping, form, request, response);
            } else {
                GoodsService gs = new GoodsService();
                gs.removeProps(roleInfo.getBasicInfo().getPPk(), prop_id, 1, GameLogManager.R_USE);
                roleInfo.getBasicInfo().setAddscratchticketnum(roleInfo.getBasicInfo().getAddscratchticketnum() - 1);
                roleInfo.getBasicInfo().setScratchticketnum(roleInfo.getBasicInfo().getScratchticketnum() + 1);
            }
            if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
                cc = 0;
            }
            String[] cc_bak = vo.getPropOperate2().split(",");
            if (dd > 0.25) {
                String notpet = "notpet";
                request.setAttribute("notpet", notpet);
                request.setAttribute("pp", pp);
                request.setAttribute("bout_type", "捕捉宠物");
                return n3(mapping, form, request, response);
            }
            if (cc > 100) {
                if (petService.PetSinewService(petNameBean, npc, p_pk)) {
                    npcService.capturePet(npc, mapid);//捕捉成功删除npc
                } else {
                    String notpet = "notpet";
                    request.setAttribute("notpet", notpet);
                    request.setAttribute("pp", pp);
                    request.setAttribute("bout_type", "捕捉宠物");
                    return n3(mapping, form, request, response);
                }
            } else if (MathUtil.isAppearByPercentage(Integer.parseInt(cc_bak[0]), Integer.parseInt(cc_bak[1]))) {

                if (petService.PetSinewService(petNameBean, npc, p_pk)) {
                    if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
                        npcService.getNpcByNianshouBuzhuo(npc.getNpcID(), "被" + roleInfo.getBasicInfo().getName() + "捕捉了!");
                    }
                    npcService.capturePet(npc, mapid);//捕捉成功删除npc
                } else {
                    String notpet = "notpet";
                    request.setAttribute("notpet", notpet);
                    request.setAttribute("pp", pp);
                    request.setAttribute("bout_type", "捕捉宠物");
                    return n3(mapping, form, request, response);
                }
            } else {
                String notpet = "notpet";
                request.setAttribute("notpet", notpet);
                request.setAttribute("pp", pp);
                request.setAttribute("bout_type", "捕捉宠物");
                return n3(mapping, form, request, response);
            }
        }

        //清除被捕获的npc
//		NpcAttackDao npcAttackDao = new NpcAttackDao();
//			npcAttackDao.deleteNpcByMapNpcPPk(npc.getPPk(),npc.getNpcID(),mapid+"");

        request.setAttribute("npcname", petNameBean.getPetName());
        request.setAttribute("p_pk", p_pk);
        request.setAttribute("npcId", npc.getNpcID() + "");

        return mapping.findForward("okpet");
    }

}