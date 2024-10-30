package com.ls.web.service.player;

import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.dynamic.manual.view.ViewCache;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.buff.BuffType;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.util.DateUtil;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.skill.SkillService;
import com.lw.dao.laborage.PlayerLaborageDao;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class PlayerService {

    Logger logger = Logger.getLogger("log.service");


    /**
     * 得到用户的角色列表
     */
    public List<PartInfoVO> getRoleList(String u_pk) {
        PartInfoDao dao = new PartInfoDao();
        checkDeleteStateRoles(u_pk);
        return dao.getPartTypeList(u_pk);
    }

    /**
     * 得到本周在线时间
     */
    public int getOnlineTimeInThisWeek(String pPk) {
        PlayerLaborageDao pldao = new PlayerLaborageDao();
        return pldao.getPlayerOnlineTimeNOW(Integer.parseInt(pPk));
    }

    /**
     * 检查处于删除缓冲的角色是否超过缓冲期，如果超过则把角色删除 原先1为正常，0为正在删除，-1 为已经删除
     * 现在改为0为正常，1为正在删除，-1为已经删除
     *
     * @param p_pk
     * @param delete_flag
     * @return
     */
    private void checkDeleteStateRoles(String u_pk) {
        PartInfoDao partInfoDao = new PartInfoDao();
        List<PartInfoVO> role_list = partInfoDao.getDeleteStateRoles(u_pk);
        if (role_list != null && role_list.size() != 0) {
            for (PartInfoVO role : role_list) {
                {
                    if (DateUtil.isOverdue(role.getDeleteTime(), DateUtil.DAY))// 判断是否超过删除缓冲时间（当前缓冲时间为1天）
                        updateDeleteFlag(role.getPPk(), -1);// 如果超时则删除
                }
            }
        }
    }

    /**
     * 设置角色的删除状态
     *
     * @param p_pk
     * @param delete_flag
     * @return
     */
    public void updateDeleteFlag(int p_pk, int delete_flag) {
        PartInfoDao partInfoDao = new PartInfoDao();
        partInfoDao.updateDeleteFlag(p_pk, delete_flag);
    }


    /**
     * 玩家死亡初始化复活地点,但是将视野更新为无
     *
     * @param player
     * @return
     */
    public PartInfoVO initPositionWithOutView(PartInfoVO player) {
        if (player == null) {
            logger.info("初始化玩家地点:玩家为空无效！");
        }
        RoleEntity roleEntity = RoleCache.getByPpk(player.getPPk());

        // 对人物死亡后的某些事进行处理, 并且不太容易拿到其他地方去的
        dealWithDead(player);

        RoomService roomService = new RoomService();
        // 得到死亡复活点
        int resurrection_point = roomService.getResurrectionPoint(roleEntity);
        // 更新map_id
        player.setPMap(resurrection_point + "");

        updateSceneAndView(player.getPPk(), resurrection_point);
        if (roleEntity != null) {
            ViewCache viewCache = new ViewCache();
            viewCache.remove(roleEntity.getStateInfo().getView(), roleEntity);
        }

        return player;
    }

    /**
     * 对人物死亡后的某些事进行处理, 并且不太容易拿到其他地方去的
     */
    private void dealWithDead(PartInfoVO player) {

        RoomService roomService = new RoomService();
        SceneVO sceneVO = roomService.getById(player.getPMap());
        MapVO mapVO = sceneVO.getMap();
        if (mapVO.getMapType() == MapType.TONGBATTLE) {
        }
    }

    /**
     * 给玩家装载技能详细信息
     *
     * @param sk_id
     * @return
     */
    public PlayerSkillVO loadPlayerSkill(Fighter player, int s_pk) {
        if (player == null) {
            return null;
        }
        if (s_pk == -1) {
            return null;
        }
        if (player.getSkill() != null) {
            return player.getSkill();
        }

        SkillService skillService = new SkillService();

        PlayerSkillVO playerSkill = null;

        playerSkill = skillService.getSkillInfo(player.getPPk(), s_pk);

        // 得到技能详细信息
        SkillDao skillDao = new SkillDao();
        skillDao.loadPlayerSkillDetailByInside(playerSkill);
        // 给玩家装载技能详细信息
        player.setSkill(playerSkill);
        return playerSkill;
    }


    /**
     * 通过缓存加载玩家相关属性（各种附加效果）
     */
    private String loadPlayerPropertyByCache(PartInfoVO player, RoleEntity role_info) {
        GroupService groupService = new GroupService();
        BuffEffectService buffEffectService = new BuffEffectService();
        StringBuffer effect_describe = new StringBuffer();
        SkillService skillService = new SkillService();
        MyService myService = new MyServiceImpl();

        //加载装备效果
        role_info.getEquipOnBody().loadEquipProterty(player);

        //加载坐骑效果
        role_info.getMountSet().loadPropertys(player);

        // 加载组队效果
        groupService.loadGroupEffect(player);

        // 加载玩家buff效果
        effect_describe.append(buffEffectService.loadBuffEffectOfPlayer(player));

        // 加载被动技能效果
        skillService.loadPassiveSkillEffectByCache(player, role_info);

        // 加载夫妻组队气血增加
        myService.addBloodMax(player);

        //加载称号效果 以后有要加载的往上添加
        role_info.getTitleSet().loadProperty(player);
        return effect_describe.toString();
    }

    /**
     * 通过id 得到玩家信息
     *
     * @param p_pk
     * @return
     */
    public Fighter getFighterByPpk(int p_pk) {
        Fighter player = new Fighter();
        this.loadFighterByPpk(player, p_pk);
        return player;
    }

    /**
     * 通过id 加载玩家信息
     *
     * @param p_pk
     * @return
     */
    public String loadFighterByPpk(PartInfoVO player, int p_pk) {
        logger.info("加载玩家基本信息.............");
        RoleEntity role_info = RoleService.getRoleInfoById(p_pk + "");

        if (role_info != null) loadFighterByCache(player, role_info);
        player.setContentDisplay(role_info.getBasicInfo().getMenpaiskilldisplay());
        return loadPlayerPropertyByCache(player, role_info);
    }

    /**
     * 通过内存加载玩家信息
     *
     * @param p_pk
     * @return
     */
    private void loadFighterByCache(PartInfoVO player, RoleEntity role_info) {
        if (player == null) {
            player = new PartInfoVO();
        }
        BasicInfo basic_info = role_info.getBasicInfo();
        logger.info("从内存中加载玩家基本信息.............");
        loadPartInfoByBasicInfo(player, basic_info);
        logger.info("从内存加载玩家附加信息.............");
    }

    /**
     * 通过id得到角色信息
     *
     * @param p_pk
     * @return
     */
    public PartInfoVO getPlayerByPpk(int p_pk) {
        RoleEntity role_info = RoleService.getRoleInfoById(p_pk + "");
        return getPlayerByRoleInfo(role_info);
    }

    /**
     * 通过id得到角色信息
     *
     * @param p_pk
     * @return
     */
    public PartInfoVO getPlayerByRoleInfo(RoleEntity roleInfo) {
        PartInfoVO player = getPlayerBasicInfo(roleInfo.getPPk());
        loadPlayerPropertyByCache(player, roleInfo);
        return player;
    }

    /**
     * 通过id得到角色基本信息
     *
     * @param p_pk
     * @return
     */
    public PartInfoVO getPlayerBasicInfo(int p_pk) {
        RoleEntity role_info = RoleService.getRoleInfoById(p_pk + "");
        return getPlayerBasicInfoByCache(role_info);
    }


    /**
     * 通过内存得到玩家的基本信息
     *
     * @param role_info
     * @return
     */
    private PartInfoVO getPlayerBasicInfoByCache(RoleEntity role_info) {
        if (role_info == null) {
            return null;
        }
        BasicInfo basic_info = role_info.getBasicInfo();
        PartInfoVO part_info = new PartInfoVO();

        loadPartInfoByBasicInfo(part_info, basic_info);

        return part_info;
    }

    /**
     * 通过BasicInfo加载partInfo信息
     */
    private void loadPartInfoByBasicInfo(PartInfoVO player, BasicInfo basic_info) {
        if (player == null) {
            player = new PartInfoVO();
        }
        if (basic_info == null) {
            return;
        }

        player.setUPk(basic_info.getUPk());
        player.setPPk(basic_info.getPPk());
        player.setPName(basic_info.getName());
        player.setPSex(basic_info.getSex());
        player.setPHarness(basic_info.getMarried());

        player.setPUpHp(basic_info.getUpHp());
        player.setPUpMp(basic_info.getUpMp());

        player.setPHp(basic_info.getHp());
        player.setPMp(basic_info.getMp());

        player.setPGj(basic_info.getBasicGj());
        player.setPFy(basic_info.getBasicFy());

        player.setPGrade(basic_info.getGrade());
        player.setPExperience(basic_info.getCurExp());
        player.setPBjExperience(basic_info.getPreGradeExp());
        player.setPXiaExperience(basic_info.getNextGradeExp());

        player.setPCopper(basic_info.getCopper() + "");

        player.setPPks(basic_info.getPkSwitch());
        player.setPPkValue(basic_info.getEvilValue());
        player.setPkChangeTime(basic_info.getPkChangeTime());

        player.setPMap(basic_info.getSceneId());

        player.setPWrapContent(basic_info.getWrapContent());

        player.setDropMultiple(basic_info.getMultipleDamage());
        player.setTe_level(basic_info.getTe_level());
        player.setChuangong(basic_info.getChuangong());
        player.setPRace(basic_info.getPRace());
        player.setPlayer_state_by_new(basic_info.getPlayer_state_by_new());
    }


    /**
     * 根据附加效果类型，更新玩家对应的属性值
     *
     * @param player
     * @param append_attribute_type 附加属性类型
     * @param append_value          附加属性值
     * @param append_mode           附加方式，1表示增加，2表示减少
     */
    public void updateAttribteOfPlayer(PartInfoVO player, int append_attribute_type, int append_value, int append_mode) {
        if (player == null) {
            logger.info("参数错误");
            return;
        }

        if (append_mode == 2) {
            // debuff
            append_value = -append_value;
        }
        switch (append_attribute_type) {
            case BuffType.CHANGER_HP: {
                int change_value = append_value;
                player.setPHp(player.getPHp() + change_value);
                break;
            }
            case BuffType.CHANGER_MP: {
                int change_value = append_value;
                player.setPMp(player.getPMp() + change_value);
                break;
            }
            case BuffType.ATTACK: {
                int change_value = append_value;
                player.setPGj(player.getPGj() + change_value);
                break;
            }
            case BuffType.DEFENCE: {
                int change_value = append_value;
                player.setPFy(player.getPFy() + change_value);
                break;
            }
            case BuffType.HP_UP: {
                int change_value = append_value;
                player.setPUpHp(player.getPUpHp() + change_value);
                break;
            }
            case BuffType.MP_UP: {
                int change_value = append_value;
                player.setPUpMp(player.getPUpMp() + change_value);
                break;
            }
            // *********************五行攻击的buff
            case BuffType.JIN_ATTACK: {
                int change_value = append_value;
                player.getWx().setJinGj(player.getWx().getJinGj() + change_value);
                break;
            }
            case BuffType.MU_ATTACK: {
                int change_value = append_value;
                player.getWx().setMuGj(player.getWx().getMuGj() + change_value);
                break;
            }
            case BuffType.SHUI_ATTACK: {
                int change_value = append_value;
                player.getWx().setShuiGj(player.getWx().getShuiGj() + change_value);
                break;
            }
            case BuffType.HUO_ATTACK: {
                int change_value = append_value;
                player.getWx().setHuoGj(player.getWx().getHuoGj() + change_value);
                break;
            }
            case BuffType.TU_ATTACK: {
                int change_value = append_value;
                player.getWx().setTuGj(player.getWx().getTuGj() + change_value);
                break;
            }
            // ************************************五行防御的buff
            case BuffType.JIN_DEFENCE: {
                int change_value = append_value;
                player.getWx().setJinFy(player.getWx().getJinFy() + change_value);
                break;
            }
            case BuffType.MU_DEFENCE: {
                int change_value = append_value;
                player.getWx().setMuFy(player.getWx().getMuFy() + change_value);
                break;
            }
            case BuffType.SHUI_DEFENCE: {
                int change_value = append_value;
                player.getWx().setShuiFy(player.getWx().getShuiFy() + change_value);
                break;
            }
            case BuffType.HUO_DEFENCE: {
                int change_value = append_value;
                player.getWx().setHuoFy(player.getWx().getHuoFy() + change_value);
                break;
            }
            case BuffType.TU_DEFENCE: {
                int change_value = append_value;
                player.getWx().setTuFy(player.getWx().getTuFy() + change_value);
                break;
            }

            case BuffType.ADD_CATCH_PROBABILITY:// 增加捕获概率
            {
                int change_value = (append_value);
                player.setAppendCatchProbability(player.getAppendCatchProbability() + change_value);
                break;
            }
            case BuffType.ADD_DROP_PROBABILITY:// 增加掉落概率
            {
                int change_value = (append_value);
                player.setAppendDropProbability(player.getAppendDropProbability() + change_value);
                break;
            }
            case BuffType.ADD_EXP:// 经验加成
            {
                int change_value = (append_value);
                player.setAppendExp(player.getAppendExp() + change_value);
                break;
            }
            case BuffType.ADD_NONSUCH_PROBABILITY:// 增加极品装备掉落概率
            {
                int change_value = (append_value);
                player.setAppendNonsuchProbability(player.getAppendNonsuchProbability() + change_value);
                break;
            }

            case BuffType.IMMUNITY_POISON:// **是否免疫中毒
            {
                if (append_value == 1) {
                    player.setImmunityPoison(true);
                }
                break;
            }
            case BuffType.IMMUNITY__DIZZY:// **是否免疫击晕
            {
                if (append_value == 1) {
                    player.setImmunityDizzy(true);
                }
                break;
            }
            case BuffType.CHANGER_BJ:// 暴击率加成
            {
                int change_value = append_value;
                player.setDropMultiple(player.getDropMultiple() + change_value);
                break;
            }
        }
    }

    /**
     * 玩家离线处理
     *
     * @param p_pk
     */
    /*
     * public void outLine(RoleEntity roleEntity) { GroupService groupService =
     * new GroupService(); GroupNotifyService groupNotifyService = new
     * GroupNotifyService(); FieldService fieldService = new FieldService();
     * NpcService npcService = new NpcService(); int pPk =
     * roleEntity.getBasicInfo().getPPk();
     *
     *
     * npcService.clearJobs(pPk);//清除掉落临时表数据
     * groupService.leaveGroup(roleEntity); // 队伍解散
     * groupNotifyService.clareNotify(pPk);// 清除组队通知
     * fieldService.leaveField(pPk,roleEntity.getBasicInfo().getUPk()+"");
     * //如果玩家在战场，退出 }
     */

    /**
     * 检查角色状态
     *
     * @param p_pk   被检查的玩家id
     * @param action 当前将要执行的行为
     * @return
     */
    public String checkRoleState(int p_pk, int action) {
        PlayerState playerState = new PlayerState();

        RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");

        String hint = null;
        if (roleInfo == null || !roleInfo.isOnline()) {
            return hint = "玩家不在线";
        }
        int cur_state = roleInfo.getStateInfo().getCurState();// 玩家当前状态

        // 现在执行的是交易状态 就是A请求交易B
        if (action == PlayerState.TRADE) {
            // 获取B的当前状态
            if (cur_state != PlayerState.GENERAL) {// 只能在平常状态下进行交易
                hint = "对方正在" + playerState.returnStateName(cur_state) + ",请稍候再进行" + playerState.returnStateName(action);
                return hint;
            }
        }
        // 现在执行组队状态 就是A请求B
        if (action == PlayerState.GROUP) {
            // 获取B的当前状态
            if (cur_state != PlayerState.GENERAL) {// 只能在平常状态下进行组队
                hint = "对方正在" + playerState.returnStateName(cur_state) + ",请稍候再进行" + playerState.returnStateName(action);
                return hint;
            }
        }
        // 现在执行PK状态 就是A请求B
        if (action == PlayerState.PKFIGHT) {
            // 获取B的当前状态 //不在论坛，不在交易，不在组队，不在离线,不在捡取物品保护的状态下才可以进行请求PK
            if (cur_state == PlayerState.TRADE || cur_state == PlayerState.GROUP || cur_state == PlayerState.OUTLINE || cur_state == PlayerState.PKFIGHT || cur_state == PlayerState.FORUM || cur_state == PlayerState.EXTRA) {
                hint = "对方正在" + playerState.returnStateName(cur_state) + ",请稍候再进行" + playerState.returnStateName(action);
                return hint;
            }
        }

        // 现在执行PK状态 就是A请求B
        if (action == PlayerState.PKFIGHT) {
            return "对方正在战斗,请稍候再进行" + playerState.returnStateName(action);
        }

        // 现在私聊状态 就是A请求B
        if (action == PlayerState.TALK) {
            // 获取B的当前状态 //不在交易，不在组队，不在离线的状态下才可以进行请求PK
            if (cur_state != PlayerState.GENERAL) {
                // hint =
                // "对方正在"+playerState.returnStateName(cur_state)+",请稍候再进行"+playerState.returnStateName(nonceState);
                hint = "对方正在" + playerState.returnStateName(cur_state);
                return hint;
            }
        }

        String scene_id = roleInfo.getBasicInfo().getSceneId();
        SceneVO sceneVO = SceneCache.getById(scene_id);
        MapVO mapVO = sceneVO.getMap();
        if (mapVO.getMapType() == MapType.TIANGUAN) {
            hint = "您正在挑战天关,不可申请组队!";
            return hint;
        }

        return hint;
    }

    /**
     * 检查角色状态
     *
     * @param p_pk   被检查的玩家id
     * @param action 当前将要执行的行为
     * @return
     */
    public String isRoleState(int p_pk, int type) {
        RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
        String hint = null;
        if (roleInfo == null) {
            if (type == 1) {// 交易
                return hint = "交易超时，交易被取消。";
            }
            if (type == 2) {// 申请组队
                return hint = "对方不在线。";
            }
        } else {// 主要是做看对方玩家某些互动开关状态
            if (type == 1) {// 交易
                if (roleInfo.getSettingInfo().getDealControl() == -1) {
                    return hint = "对方玩家的交易开关是关闭的！";
                }
            }
            SceneVO sceneVO = roleInfo.getBasicInfo().getSceneInfo();
            MapVO mapVO = sceneVO.getMap();
            if (mapVO.getMapType() == MapType.TIANGUAN) {
                hint = "您正在挑战天关,不可申请组队!";
                return hint;
            }
        }
        return hint;
    }

    /**
     * 清除临时表数据
     *
     * @param p_pk
     * @param attack_state all:表示清除所有临时表npc;zd:表示只清除主动npc
     */
    public void clearTempData(int p_pk, String attack_state) {
        RoleEntity role_info = RoleService.getRoleInfoById(p_pk + "");
        // 清除之前的经验和钱
        role_info.getDropSet().clearExpAndMoney();
        // 清除之前的掉落物
        role_info.getDropSet().clearDropItem();

        AttacckCache attacckCache = new AttacckCache();

        List<NpcFighter> zd_npcs = attacckCache.getZDAttackNpc(p_pk, AttacckCache.ZDATTACKNPC);
        if (zd_npcs != null) {
            // logger.debug("清除" + zd_npcs.size() + "个主动NPC");
            zd_npcs.clear();// 清除主动npc
        }

        if (attack_state.equals("all")) {
            List<NpcFighter> bd_npcs = attacckCache.getZDAttackNpc(p_pk, AttacckCache.BDATTACKNPC);
            if (bd_npcs != null) {
                // logger.debug("清除" + bd_npcs.size() + "个被动NPC");
                bd_npcs.clear();// 清除被动npc
            }
        }
    }

    /**
     * 更新玩家场景和视野
     */
    public void updateSceneAndView(int p_pk, int new_scene_id) {
        ViewCache viewCahce = new ViewCache();

        RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");

        if (roleInfo != null) {
            SceneVO new_scene = SceneCache.getById(new_scene_id + "");
            String hint = new_scene.isEntered(roleInfo);
            //判断是否可以进入该区域
            if (hint != null) {
                return;
            }

            roleInfo.getBasicInfo().updateSceneId(new_scene_id + "");// 更新玩家scene_id

            String old_view = roleInfo.getStateInfo().getView();
            String new_view = null;

            new_view = getCurrentView(roleInfo.getBasicInfo().getPPk(), new_scene_id);

            viewCahce.remove(old_view, roleInfo);// 移除玩家之前的视野
            roleInfo.getStateInfo().setView(new_view);// 更新玩家视野
            viewCahce.put(new_view, roleInfo);// 更新玩家视野
        }
    }


    /**
     * 得到视野内的玩家列表字符串 大于15个字，则只显示15字加。。。。。
     */
    public String getPlayerListStrByScene(RoleEntity roleInfo) {
        String result = null;

        ViewCache viewCache = new ViewCache();

        StringBuffer player_list_str = new StringBuffer();

        String cur_view = null;

        cur_view = roleInfo.getStateInfo().getView();

        LinkedHashSet<RoleEntity> role_list = viewCache.getRolesByView(cur_view);
        PlayerService ps = new PlayerService();
        if (role_list.size() > 1) {
            Iterator list = role_list.iterator();
            RoleEntity otherRoleInfo = null;
            int i = 1;
            while (list.hasNext()) {
                otherRoleInfo = (RoleEntity) list.next();
                PartInfoVO partInfo = ps.getPlayerByRoleInfo(otherRoleInfo);
                /***************不显示自己*******/
                if (otherRoleInfo.equals(roleInfo)) {
                    continue;
                }
                /*******判断不在线玩家不显示******/
                if (i == 1) {
                    player_list_str.append(otherRoleInfo.getBasicInfo().getName());
                } else {
                    player_list_str.append(",").append(otherRoleInfo.getBasicInfo().getName());
                }
                i++;
            }
            if (player_list_str.length() >= 15) {
                result = player_list_str.substring(0, 15) + "....";
            } else {
                result = player_list_str.toString();
            }
        }

        return result;
    }

    /**
     * 得到当前视野
     *
     * @param scene_id
     * @return
     */
    public String getCurrentView(int p_pk, int scene_id) {
        int map_type = -1;
        String view = null;

        RoomService roomService = new RoomService();
        GroupService groupService = new GroupService();

        map_type = roomService.getMapType(scene_id);
        SceneVO scene = roomService.getById(scene_id + "");

        view = scene.getSceneKen();

        if (map_type == MapType.INSTANCE) {
            int caption_pk = groupService.getCaptionPk(p_pk);
            view = view + "_group" + caption_pk;
        }
        if (map_type == MapType.TIANGUAN) {
            view = view + "_" + p_pk;
        }
        return view;
    }

    /**
     * 得到玩家所有同一视野的玩家
     * @param roleInfo roleInfo
     * @return List<RoleEntity>
     */
    public List<RoleEntity> getPlayersByView(RoleEntity roleInfo) {
        ViewCache viewCache = new ViewCache();
        String curView = roleInfo.getStateInfo().getView();
        LinkedHashSet<RoleEntity> roleList = viewCache.getRolesByView(curView);
        LinkedHashSet<RoleEntity> newRoleList = (LinkedHashSet<RoleEntity>) roleList.clone();
        newRoleList.remove(roleInfo);
        return new ArrayList<>(newRoleList);
    }

    /**
     * 给玩家的不同状态下增加不同的标记
     *
     * @param players
     */
    public void addUserStateFlag(List<RoleEntity> players) {
        RoleEntity roleEntity = null;
        for (int i = 0; i < players.size(); i++) {
            roleEntity = players.get(i);
            if (roleEntity.getStateInfo().getGroupInfo() != null) {
                roleEntity.getBasicInfo().setDisplayName(roleEntity.getBasicInfo().getName() + "*");
            } else if (roleEntity.getStateInfo().getCurState() == PlayerState.PKFIGHT) {
                roleEntity.getBasicInfo().setDisplayName("×" + roleEntity.getBasicInfo().getName());
            } else {
                roleEntity.getBasicInfo().setDisplayName(roleEntity.getBasicInfo().getName());
            }
        }
    }

}
